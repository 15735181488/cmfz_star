<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<!--页头-->
<div class="page-header" style="margin-top: -20px;">
    <h1>专辑管理</h1>
</div>
<script>
    $(function () {
        $("#bannerTable").jqGrid({
            styleUI:"Bootstrap",
            height:"380px",
            autowidth:true,//自动适应父容器
            url:"${app}/album/selectAll",
            datatype:"json",
            caption : "专辑列表",
            colNames:["编号","标题","封面","章节数","得分","简介","创建时间","作者"],
            pager:"#pager",
            rowNum:3,
            subGrid : true,
            rowList:[2,3,5,10,20],
            viewrecordes:true,
            editurl:"${app}/album/edit",
            colModel:[
                {name:"id",hidden:true},
                {name:"title",editable:true,align:"center"},
                {name:"cover",align:"center",editable:true,edittype:'file',
                    formatter:function (value,option,rows) {
                        return "<img style='width:100px;height:70px' src='${pageContext.request.contextPath}/album/img/"+rows.cover+"'>"}},
                {name:"count",align:"center"},
                {name:"score",align:"center",editable:true},
                {name:"brief",align:"center",editable:true},
                {name:"createDate",align:"center",editable:true,edittype:"date"},
                {name:"starId",editable:true,edittype:"select",editoptions:{
                        dataUrl:"${app}/star/findAll"//获取所有部门列表  html 必须select option ....</select>
                        //value:"男:男;女:女"  //书写本地数据
                    },formatter:function(value, options, row){
                        return row.star.nickname;
                    }
                },
            ],
            subGridRowExpanded : function(subgrid_id, id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id  +"' class='scroll'></table>" +
                    "<div id='" + pager_id + "' class='scroll'></div>");
                $("#" + subgrid_table_id).jqGrid(
                    {
                        url : "${pageContext.request.contextPath}/song/selectSongsByAlbumId?albumId=" + id,
                        datatype : "json",
                        colNames : [ '编号', '歌曲','歌手', '大小', '时长', '创建时间','在线播放'],
                        colModel : [
                            {name : "id",hidden:true},
                            {name : "name",align:"center",editable:true,edittype:'file'},
                            {name : "singer",align:"center",editable:true},
                            {name : "size",align:"center"},
                            {name : "duration",align:"center"},
                            {name : "createDate",align:"center"},
                            {name : "operation",width:300,formatter:function (value,option,rows) {
                                    return "<audio controls>\n" +
                                        "  <source src='${pageContext.request.contextPath}/album/music/"+rows.name+"' >\n" +
                                        "</audio>";
                                }}
                        ],
                        styleUI:"Bootstrap",
                        rowNum : 2,
                        pager : pager_id,
                        autowidth:true,
                        height : '100%',
                        editurl:"${app}/song/edit?albumId="+id
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit : false,
                        add : true,
                        del : false,
                        search:false
                    },{//控制修改

                    },{//控制添加
                        closeAfterAdd:true,
                        afterSubmit:function (response) {
                            var status=response.responseJSON.status;
                            if(status){
                                var cid=response.responseJSON.message;
                                $.ajaxFileUpload({
                                    url:"${app}/song/upload",
                                    type:"post",
                                    fileElementId:"name",
                                    data:{id:cid,albumId:id},
                                    success:function () {
                                        $("#"+subgrid_table_id).trigger("reloadGrid");
                                    }
                                })
                            }
                            return "xxx";
                        }
                    });
            },
        }).navGrid(
            "#pager",
            {edit:true,del:true,add:true},
            {
                //控制修改
                closeAfterEdit:true,
                beforeShowForm:function (fmt) {
                    fmt.find("#cover").attr("disabled",true);
                }
            }, //edit option
            {   closeAfterAdd:true,
                afterSubmit:function (data) {
                    console.log(data);
                    var status=data.responseJSON.status;
                    var id=data.responseJSON.message;
                    if(status){
                        $.ajaxFileUpload({
                            url:"${app}/album/upload",
                            type:"post",
                            fileElementId:"cover",
                            data:{id:id},
                            success:function (response) {
                                //自动刷新jqgrid
                                $("#bannerTable").trigger("reloadGrid");
                            }
                        });
                    }
                    return "xxx";//必须写返回值,否则执行完之后页面不会关闭
                }
            } //add option

        );
    })

    function showPicture(cellvalue){
        return "<img src='${pageContext.request.contextPath}/user/img/" +cellvalue + "' height='100' width='130'/>";
    }

</script>
<table id="bannerTable"></table>
<div id="pager"></div>