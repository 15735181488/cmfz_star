<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>用户注册</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        form{
            width:270px;
        }
        input{
            width: 70%;
            background: #eee;
        }
        input:focus{
            background: #fff;
        }
        form{
            padding: 0 12px 12px;
        }
        label{
            display: block;
            padding-bottom:12px;
        }
        #img-vcode{
            width: 56px;
            height: 21px;
            float:right;
            position: relative;
            top:2px;
            left:-6px
        }
        .label-text{
            width: 30%;
            float: left;
        }
    </style>
    <script src="../bootstrapgrid/jquery-3.4.1.min.js"></script>
    <script>

            function sendMessage(){
                var phone = $("#user-phone").val();
                $.ajax({
                    url: "${pageContext.request.contextPath}/user/sendMessage",
                    type: "POST",
                    async:true,
                    data:{phone:phone},
                    dataType: "json"
                })
            }


        $("#registButton").click(function () {
            $.ajax({
                url: "${pageontext.request.contextPath}/user/save",
                type: "POST",
                data: $("#inputForm").serialize(),
                dataType: "json",
                success: function (data) {
                    if(data.status){
                        location.href = "${pageContext.request.contextPath}/login/login.jsp"
                    }
                }
            })
        })

    </script>
</head>
<body>
    <div id="wrap">
        <div id="header">
            <div style="float: right;padding-top: 24px"></div>
            <h1>驰名法洲后台管理系统</h1>
        </div>
        <div id="header-bar"></div>
        <div id="content" style="height: 360px">
            <h2>用户注册</h2>
            <form action="" method="post" id="inputForm">
                <label>
                    <div class="label-text">用户名：</div>
                    <input type="text" name="username">
                </label>
                <label>
                    <div class="label-text">密&emsp;码：</div>
                    <input type="password" name="password">
                </label>
                <label>
                    <div class="label-text">昵称：</div>
                    <input type="text" name="nickname">
                </label>
                <label>
                    <div class="label-text">省：</div>
                    <input type="text" name="province">
                </label>
                <label>
                    <div class="label-text">市：</div>
                    <input type="text" name="city">
                </label>
                <label>
                    <div class="label-text" >手机号：</div>
                    <input type="text" name="phone" id="user-phone">
                </label>

                <label>
                    <input type="submit" style="width: 80px" onclick="sendMessage()" value="发送验证码"/>
                    <input type="text" name="inputCode" style="width: 150px">
                </label>
                <button type="submit" id="registButton">提 交</button>&emsp;

            </form>
        </div>

    </div>
</body>
</html>