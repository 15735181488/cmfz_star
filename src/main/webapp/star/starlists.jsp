<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!--页头-->
<div class="page-header" style="margin-top: -20px;">
    <h1>明星管理</h1>
</div>
<script>
    $(function () {
        $("#bannerTable").jqGrid({
            styleUI:"Bootstrap",
            height:"380px",
            autowidth:true,//自动适应父容器
            url:"${app}/star/selectAll",
            datatype:"json",
            caption : "明星列表",
            colNames:["编号","艺名","真名","图片","性别","生日"],
            pager:"#pager",
            rowNum:3,
            subGrid : true,
            rowList:[2,3,5,10,20],
            viewrecordes:true,
            editurl:"${app}/star/edit",
            colModel:[
                {name:"id",hidden:true},
                {name:"nickname",editable:true,align:"center"},
                {name:"realname",editable:true,align:"center"},
                {name:"photo",align:"center",editable:true,edittype:'file',
                formatter:function (value,option,rows) {
                    return "<img style='width:100px;height:70px' src='${pageContext.request.contextPath}/star/img/"+rows.photo+"'>"}},
                {name:"sex",align:"center",editable:true,edittype:"select",editoptions:{value:"男:男;女:女"}},
                {name:"bir",align:"center",editable:true,edittype:"date"}
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
                        url : "${pageContext.request.contextPath}/user/selectUsersByStarId?starId=" + id,
                        datatype : "json",
                        colNames : [ '编号', '用户名', '昵称', '头像','电话', '性别','地址','签名' ],
                        colModel : [
                            {name : "id",hidden:true},
                            {name : "username"},
                            {name : "nickname"},
                            {name : "photo"},
                            {name : "phone"},
                            {name : "sex"},
                            {name : "province",align:"center",
                                formatter : function(value, options, rData){
                                    return value + " - "+rData['city'];
                                }},
                            {name : "sign"}
                        ],
                        styleUI:"Bootstrap",
                        rowNum : 2,
                        pager : pager_id,
                        autowidth:true,
                        height : '100%'
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit : false,
                        add : false,
                        del : false,
                        search:false
                    });
            },
        }).navGrid(
            "#pager",
            {edit:true,del:true,add:true},
            {
                //控制修改
                closeAfterEdit:true,
                beforeShowForm:function (fmt) {
                    fmt.find("#photo").attr("disabled",true);
                }
            }, //edit option
            {   closeAfterAdd:true,
                afterSubmit:function (data) {
                    console.log(data);
                    var status=data.responseJSON.status;
                    var id=data.responseJSON.message;
                    if(status){
                        $.ajaxFileUpload({
                            url:"${app}/star/upload",
                            type:"post",
                            fileElementId:"photo",
                            data:{id:id},
                            success:function (response) {
                                //自动刷新jqgrid
                                $("#bannerTable").trigger("reloadGrid");
                            }
                        });
                    }
                    return "xxx";//必须写返回值,否则执行完之后页面不会关闭
                }
            } ,//add option
            {}//del
        );
    })


</script>
<table id="bannerTable"></table>
<div id="pager"></div>