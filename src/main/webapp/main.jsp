<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="app" value="${pageContext.request.contextPath}"></c:set>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>主页面</title>

    <%--引入bootstrap核心样式--%>
    <link rel="stylesheet" href="bootstrapgrid/bootstrap/css/bootstrap.min.css">
    <%--引入jqgrid核心基础样式--%>
    <link rel="stylesheet" href="bootstrapgrid/jqgrid/css/ui.jqgrid.css">
    <%--引入jqgrid的bootstra皮肤--%>
    <link rel="stylesheet" href="bootstrapgrid/jqgrid/css/ui.jqgrid-bootstrap.css">
    <%--引入jquery核心js--%>
    <script src="bootstrapgrid/jquery-3.4.1.min.js"></script>
    <%--<script src="statics/boot/js/jquery-3.3.1.min.js"></script>--%>
    <%--<script src="statics/boot/js/jquery-2.2.1.min.js"></script>--%>
    <%--引入boot核心js--%>
    <script src="bootstrapgrid/bootstrap/js/bootstrap.min.js"></script>
    <%--引入jqgrid核心js--%>
    <script src="bootstrapgrid/jqgrid/js/jquery.jqGrid.min.js"></script>
    <%--引入i18njs--%>
    <script src="bootstrapgrid/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <%--文件上传--%>
    <script src="statics/jqgrid/js/ajaxfileupload.js"></script>

    <script src="echarts/echarts.min.js"></script>

    <script charset="utf-8" src="kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="kindeditor/lang/zh-CN.js"></script>

</head>
<body>
<shiro:notAuthenticated>
    <h2>您好,请
        <a href="${pageContext.request.contextPath}/login/login.jsp">登录</a>
        ,登录之后浏览更多精彩内容</h2>
</shiro:notAuthenticated>
<shiro:authenticated>
    <shiro:hasRole name="admin">
        <%--导航条--%>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <!--导航标题-->
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">启明星后台管理系统
                        <small>v1.0</small>
                    </a>
                </div>
                <!--导航条内容-->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="#">欢迎:<shiro:principal/><font
                                color="aqua">${sessionScope.loginAdmin.username}</font></a></li>
                        <li><a href="${app}/admin/exit">退出登录</a></li>
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

        <!--轮播图面板-->
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="bannerPanel">
                <h4 class="panel-title">
                    <a role="button" data-toggle="collapse" data-parent="#accordion" href="#bannerLists"
                       aria-expanded="true" aria-controls="collapseOne">
                        轮播图管理
                    </a>
                </h4>
            </div>
            <div id="bannerLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                <div class="panel-body">
                    <ul class="list-group">
                        <li class="list-group-item"><a
                                href="javascript:$('#centerLayout').load('${app}/banner/bannerlists.jsp');" id="btn">所有轮播图</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <!--专辑面板-->
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="albumPanel">
                <h4 class="panel-title">
                    <a role="button" data-toggle="collapse" data-parent="#accordion" href="#albumLists"
                       aria-expanded="true" aria-controls="collapseOne">
                        专辑管理
                    </a>
                </h4>
            </div>
            <div id="albumLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                <div class="panel-body">
                    <ul class="list-group">
                        <li class="list-group-item"><a
                                href="javascript:$('#centerLayout').load('${app}/album/albumlists.jsp')">专辑列表</a></li>
                    </ul>
                </div>
            </div>
        </div>

        <!--文章面板-->
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="articalPanel">
                <h4 class="panel-title">
                    <a role="button" data-toggle="collapse" data-parent="#accordion" href="#articalLists"
                       aria-expanded="true" aria-controls="collapseOne">
                        文章管理
                    </a>
                </h4>
            </div>
            <div id="articalLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                <div class="panel-body">
                    <ul class="list-group">
                        <li class="list-group-item">
                            <a href="javascript:$('#centerLayout').load('${app}/article/articlelists.jsp')">文章列表</a>
                            <br><br>
                            <a href="javascript:$('#centerLayout').load('${app}/article/article-search.jsp')">文章搜索</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>


        <!--用户面板-->
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="userPanel">
                <h4 class="panel-title">
                    <a role="button" data-toggle="collapse" data-parent="#accordion" href="#userLists"
                       aria-expanded="true" aria-controls="collapseOne">
                        用户管理
                    </a>
                </h4>
            </div>
            <div id="userLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                <div class="panel-body">
                    <ul class="list-group">
                        <li class="list-group-item"><a
                                href="javascript:$('#centerLayout').load('${app}/user/userlists.jsp');">用户列表</a></li>
                        <li class="list-group-item"><a
                                href="javascript:$('#centerLayout').load('${app}/user/user-echarts.jsp');">用户注册趋势</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <!--明星面板-->
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="starPanel">
                <h4 class="panel-title">
                    <a role="button" data-toggle="collapse" data-parent="#accordion" href="#starLists"
                       aria-expanded="true" aria-controls="collapseOne">
                        明星管理
                    </a>
                </h4>
            </div>
            <div id="starLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                <div class="panel-body">
                    <ul class="list-group">
                        <li class="list-group-item"><a
                                href="javascript:$('#centerLayout').load('${app}/star/starlists.jsp');">明星列表</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </shiro:hasRole>
    <shiro:hasRole name="super">
        <!--管理员面板-->
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="adminPanel">
                <h4 class="panel-title">
                    <a role="button" data-toggle="collapse" data-parent="#accordion" href="#adminLists"
                       aria-expanded="true" aria-controls="collapseOne">
                        管理员管理
                    </a>
                </h4>
            </div>
            <div id="adminLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                <div class="panel-body">
                    <ul class="list-group">
                        <li class="list-group-item"><a href="">管理员列表</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </shiro:hasRole>

    </div>
    </div>
    <!--中心布局-->
    <div class="col-sm-10" id="centerLayout">

        <!--巨幕-->
        <div class="jumbotron">
            <p align="center"><font color="red">欢迎来到启明星后台管理系统!!!</font></p>
        </div>
        <img src="login/assets/img/backgrounds/3.jpg" width="1100px" height="380px">
        <center><p>启明星后台管理系统@百知教育 <fmt:formatDate value="<%=new Date()%>" pattern="yyyy.MM.dd"></fmt:formatDate>
        </p></center>
    </div>
    </div>
    </div>
</shiro:authenticated>
</body>
</html>