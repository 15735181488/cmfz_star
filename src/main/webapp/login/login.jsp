<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <title>Bootstrap Login Form Template</title>
    <!-- CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/form-elements.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="shortcut icon" href="assets/ico/favicon.png">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png">
    <script src="assets/js/jquery-2.2.1.min.js"></script>
    <%--<script src="../bootstrapgrid/jquery-3.4.1.min.js"></script>--%>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/jquery.backstretch.min.js"></script>
    <script src="assets/js/scripts.js"></script>
    <script src="assets/js/jquery.validate.min.js"></script>
    <style >
        body{background: url("${pageContext.request.contextPath}/login/assets/img/backgrounds/1.jpg")}

    </style>
    <script>
        /*更新验证码*/
        $(function () {
            $("#captchaImage").click(function () {
                $("#captchaImage").prop("src", "${pageContext.request.contextPath}/code/getCode?time=" + new Date().getTime());
            });
        });

        $(function () {
            $("#loginButtonId").click(function () {
                var username= $("#form-username").val();
                var password = $("#form-password").val();
                var inputCode = $("#form-code").val();
                if(username && password && inputCode){
                    $.ajax({
                        url: "${pageContext.request.contextPath}/admin/login",
                        type: "POST",
                        data: $("#loginForm").serialize(),
                        dataType: "json",
                        success: function (data) {
                            if(data.status){
                                location.href = "${pageContext.request.contextPath}/main.jsp"
                            }else{
                                $("#error-message").html("<font color='red'>"+data.message+"<font>");
                            }
                        }
                    })
                }else{
                    $("#error-message").html("<font style='color: red'>请输入完整信息<font>")
                }
            })
        })


        function openModal() {
            $("#admin-modal").modal("show");
        }

        $(function () {
            $("#sendMessage").click(function () {
                var phone = $("#admin-phone").val();
                $.ajax({
                    url: "${pageContext.request.contextPath}/admin/sendMessage",
                    type: "post",
                    data: {phone: phone},
                    dataType: "json"
                })
            })
        })

        $(function () {
            $("#save").click(function () {
                $.ajax({
                    url: "${pageContext.request.contextPath}/admin/save",
                    type: "post",
                    data: $("#admin-form").serialize(),
                    datatype: "json",
                    success: function (data) {
                        if (data.status) {
                            location.href = "${pageContext.request.contextPath}/login/login.jsp"
                        }
                    }
                })
            })
        })
    </script>
</head>
<body>


<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>CMFZ</strong> Login Form</h1>
                    <div class="description">
                        <p>
                            <a href="#"><strong>CMFZ</strong></a>
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-4 form-box">
                    <div class="form-top" style="width: 450px">
                        <div class="form-top-left">
                            <h3>Login to showall</h3>
                            <p>Enter your username and password :</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="form-bottom" style="width: 450px">
                        <form role="form" action="${pageContext.request.contextPath}/admin/login" method="post"
                              class="login-form" id="loginForm">
                            <span id="error-message">${requestScope.message}</span>
                            <span id="msgDiv"></span>
                            <div class="form-group">
                                <label class="sr-only" for="form-username">Username</label>
                                <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                                <input type="text" name="username" placeholder="请输入用户名..." style="width: 378px;height: 50px;"
                                       class="glyphicon glyphicon-user" aria-hidden="true" id="form-username" required>
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">Password</label>
                                <span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
                                <input type="password" name="password" placeholder="请输入密码..." style="width: 378px;height: 50px;"
                                       minlength="2" class="glyphicon glyphicon-lock" id="form-password" required>
                            </div>
                            <div class="form-group">
                                <span class="glyphicon glyphicon-king" aria-hidden="true"></span>
                                <input style="padding-left:20px; width: 260px;height: 50px;border:3px solid #ddd;border-radius: 4px;" type="test"
                                       placeholder="请输入验证码..." name="inputCode" class="glyphicon glyphicon-king" id="form-code" required>
                                <img id="captchaImage" style="height: 48px" class="captchaImage"
                                     src="${pageContext.request.contextPath}/code/getCode">
                            </div>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="button" style="width: 280px;border:1px solid #9d9d9d;border-radius: 4px;" id="loginButtonId" value="登录">
                            <a href="#" onclick="openModal()">去注册</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>



<div id="admin-modal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width: 683px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">管理员注册</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="admin-form">
                    <input type="hidden" name="id" id="admin-id">
                    <div class="form-group">
                        <label for="admin-username" class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-10">
                            <input type="text" name="username" class="form-control" id="admin-username" placeholder="请输入用户名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="admin-password" class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-10">
                            <input type="text" name="password" class="form-control" id="admin-password" placeholder="请输入密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="admin-nickname" class="col-sm-2 control-label">昵称</label>
                        <div class="col-sm-10">
                            <input type="text" name="nickname" class="form-control" id="admin-nickname" placeholder="请输入昵称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="admin-phone" class="col-sm-2 control-label">手机号</label>
                        <div class="col-sm-10">
                            <input type="text" name="phone" class="form-control" id="admin-phone" placeholder="请输入手机号">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="admin-inputCode" class="col-sm-2 control-label">验证码</label>
                        <div class="col-sm-10">
                            <input type="text" name="inputCode" style="width: 400px" id="admin-inputCode">
                            <input type="button" style="width: 100px" id="sendMessage" value="发送验证码"/>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="save" data-dismiss="modal">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

</body>
</html>



