<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        $("#bannerTable").jqGrid({
            styleUI:"Bootstrap",
            height:"380px",
            autowidth:true,//自动适应父容器
            url:"${app}/user/selectAll",
            datatype:"json",
            caption : "用户列表",
            colNames:["编号","姓名","网名","性别","头像","地址","手机号","签名","明星"],
            pager:"#pager",
            rowNum:3,
            rowList:[2,3,5,10,20],
            viewrecordes:true,
            editurl:"${app}/user/edit",
            colModel:[
                {name:"id",hidden:true},
                {name:"username",editable:true,align:"center"},
                {name:"nickname",editable:true,align:"center"},
                {name:"sex",align:"center",editable:true,edittype:"select",editoptions:{value:"男:男;女:女"}},
                {name:"photo",align:"center",editable:true,edittype:'file',
                    formatter:function (value,option,rows) {
                        return "<img style='width:100px;height:70px' src='${pageContext.request.contextPath}/user/img/"+rows.photo+"'>"}},
                {name : "province",align:"center",
                    formatter : function(value, options, rData){
                        return value + " - "+rData['city'];
                    }},
                {name:"phone",align:"center",editable:true},
                {name:"sign",align:"center",editable:true},
                {name:"star.realname",align:"center"}
            ],
        }).navGrid(
            "#pager",
            {edit:false,del:false,add:false},
            {}, //edit option
            {} //add option

        );
    })


</script>
<div class="col-sm-10">
    <ul class="nav nav-tabs">
        <li role="presentation" class="active"><a href="#">所有用户</a></li>
        <li role="presentation"><a href="${app}/user/export" >导出用户</a></li>
    </ul>
    <table id="bannerTable"></table>
    <div id="pager"></div>
</div>