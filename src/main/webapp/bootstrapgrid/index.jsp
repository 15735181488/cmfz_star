<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" scope="page" var="app"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <%--引入bootstrap核心样式--%>
    <link rel="stylesheet" href="${app}/bootstrapgrid/bootstrap/css/bootstrap.min.css">
    <%--引入jqgrid核心基础样式--%>
    <link rel="stylesheet" href="${app}/bootstrapgrid/jqgrid/css/ui.jqgrid.css">
    <%--引入jqgrid的bootstra皮肤--%>
    <link rel="stylesheet" href="${app}/bootstrapgrid/jqgrid/css/ui.jqgrid-bootstrap.css">
    <%--引入jquery核心js--%>
    <script src="${app}/bootstrapgrid/jquery-3.4.1.min.js"></script>
    <%--引入boot核心js--%>
    <script src="${app}/bootstrapgrid/bootstrap/js/bootstrap.min.js"></script>
    <%--引入jqgrid核心js--%>
    <script src="${app}/bootstrapgrid/jqgrid/js/jquery.jqGrid.min.js"></script>
    <%--引入i18njs--%>
    <script src="${app}/bootstrapgrid/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script>
        $(function(){


            //创建jqgrid
            $("#empTable").jqGrid({
                styleUI:"Bootstrap",//使用bootstrap样式
                url:"${app}/emp/findAll",//请求数据
                datatype:"json",//指定请求数据格式 json格式
                colNames:["id","姓名","年龄","生日","邮箱","所属部门"],//用来指定显示表格列
                pager:"#pager",//指定分页工具栏
                rowNum:2,//每页展示2条
                rowList:[2,10,15,20,50],//下拉列表
                viewrecords:true,//显示总条数
                editurl:"${app}/emp/edit",//编辑时url(保存 删除 和 修改)
                colModel:[
                    {name:"id"},
                    {name:"name",editable:true},
                    {name:"age",editable:true},
                    {name:"bir",editable:true},
                    {name:"email",editable:true},
                    {name:"dept.id",editable:true,edittype:"select",editoptions:{
                            dataUrl:"${app}/dept/findAll",//获取所有部门列表  html 必须select option ....</select>
                            //value:"男:男;女:女"  //书写本地数据
                        },formatter:function(value,options,row){
                            return row.dept.name;
                        }
                    },
                ],//用来对当前列进行配置
            }).jqGrid("navGrid","#pager",{edit:true});
        });
    </script>

</head>
<body>


    <!--创建表格-->
    <table id="empTable"></table>

    <!--分页-->
    <div id="pager"></div>
</body>
</html>