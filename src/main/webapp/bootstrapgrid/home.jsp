<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<c:set var="app" value="${pageContext.request.contextPath}"></c:set>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <!--当前页面更好支持移动端-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
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
        $(function () {

            /*$("#btn").click(function(){
               //修改中心布局的内容
                $("#centerLayout").load("./user/lists_s.jsp");//引入一张页面到当前页面中
            });*/

        })
    </script>
</head>
<body>
<!--导航条-->
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!--导航标题-->
        <div class="navbar-header">
            <a class="navbar-brand" href="#">员工管理系统 <small>v1.0</small></a>
        </div>

        <!--导航条内容-->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎:<font color="aqua">小黑</font></a></li>
                <li><a href="#">退出登录 <span class="glyphicon glyphicon-log-out"></span> </a></li>
            </ul>
        </div>
    </div>
</nav>
<!--页面主体内容-->
<div class="container-fluid">
    <!--row-->
    <div class="row">

        <!--菜单-->
        <div class="col-sm-2">

            <!--手风琴控件-->
            <div class="panel-group" id="accordion" >

                <!--面板-->
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="userPanel">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#userLists" aria-expanded="true" aria-controls="collapseOne">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <div id="userLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${app}/bootstrapgrid/user/lists_s.jsp');" id="btn">用户列表</a></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <!--类别面板-->
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="categoryPanel">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#categoryLists" aria-expanded="true" aria-controls="collapseOne">
                                类别管理
                            </a>
                        </h4>
                    </div>
                    <div id="categoryLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="javascript:$('#centerLayout').load(lists_s.jsp)">类别列表</a></li>
                                <li class="list-group-item"><a href="">类别添加</a></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <!--面板-->
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="bookPanel">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#bookLists" aria-expanded="true" aria-controls="collapseOne">
                                图书管理
                            </a>
                        </h4>
                    </div>
                    <div id="bookLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="">图书列表</a></li>
                                <li class="list-group-item"><a href="">图书添加</a></li>
                            </ul>
                        </div>
                    </div>
                </div>


                <!--面板-->
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="orderPanel">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#orderLists" aria-expanded="true" aria-controls="collapseOne">
                                订单管理
                            </a>
                        </h4>
                    </div>
                    <div id="orderLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="">订单列表</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--中心布局-->
        <div class="col-sm-10" id="centerLayout">

            <!--巨幕-->
            <div class="jumbotron">
                <h1>Hello, world!</h1>
                <p>This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information.</p>
                <p><a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a></p>
            </div>
            <!--警告框-->
            <div class="bs-example bs-example-standalone" data-example-id="dismissible-alert-js">
                <div class="alert alert-danger alert-dismissible fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                    <h4>网站程序漏洞,继续修复!</h4>
                    <p>Change this and that and try again. Duis mollis, est non commodo luctus, nisi erat porttitor ligula, eget lacinia odio sem nec elit. Cras mattis consectetur purus sit amet fermentum.</p>
                    <p>
                        <button type="button" class="btn btn-danger">立即修复</button>
                        <button type="button" class="btn btn-default">稍后处理</button>
                    </p>
                </div>
            </div>
            <!--面板-->
            <div class="panel panel-default">
                <div class="panel-heading">系统状态</div>
                <div class="panel-body">
                    <!--进度条-->
                    <label>内存使用率:(40%)</label>
                    <div class="progress">
                        <div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
                            <span class="sr-only">40% Complete (success)</span>
                        </div>
                    </div>
                    <label>数据库使用率:(20%)</label>
                    <div class="progress">
                        <div class="progress-bar progress-bar-info progress-bar-striped" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 20%">
                            <span class="sr-only">20% Complete</span>
                        </div>
                    </div>
                    <label>磁盘使用率:(60%)</label>
                    <div class="progress">
                        <div class="progress-bar progress-bar-warning progress-bar-striped" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%">
                            <span class="sr-only">60% Complete (warning)</span>
                        </div>
                    </div>
                    <label>CPU使用率:(80%)</label>
                    <div class="progress">
                        <div class="progress-bar progress-bar-danger progress-bar-striped" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 80%">
                            <span class="sr-only">80% Complete (danger)</span>
                        </div>
                    </div>

                </div>
            </div>

        </div>
    </div>
</div>
</body>
</html>