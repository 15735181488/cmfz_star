<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!--页头-->
<div class="page-header" style="margin-top: -20px;">
    <h1>轮播图管理</h1>
</div>
<script>
    $(function () {
        $("#bannerTable").jqGrid({
            styleUI:"Bootstrap",
            height:"380px",
            autowidth:true,//自动适应父容器
            url:"${app}/banner/findAll",
            datatype:"json",
            caption : "轮播图列表",
            colNames:["编号","名称","图片","描述","状态","创建时间"],
            pager:"#pager",
            rowNum:3,
            rowList:[2,3,5,10,20],
            viewrecordes:true,
            editurl:"${app}/banner/edit",
            colModel:[
                {name:"id",hidden:true},
                {name:"name",editable:true,align:"center"},
                {name:"cover",align:"center",editable:true,formatter:showPicture,edittype:'file',
                    editoptions:{enctype:"multipart/form-data"}},
                {name:"description",align:"center",editable:true},
                {name:"status",align:"center",editable:true,edittype:"select",editoptions:{value:"正常:正常;冻结:冻结"}},
                {name:"createDate",align:"center"}
            ],
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
                            url:"${app}/banner/upload",
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
        return "<img src='${pageContext.request.contextPath}/banner/img/" +cellvalue + "' height='100' width='130'/>";
    }

</script>
<table id="bannerTable"></table>
<div id="pager"></div>