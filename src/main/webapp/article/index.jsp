<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script charset="utf-8" src="../kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="../kindeditor/lang/zh-CN.js"></script>
    <script>
        KindEditor.ready(function(K) {
            window.editor = K.create('#editor_id',{
                //width : '800px',
                //点击图片空间按钮时发送的请求
                fileManagerJson:"${pageContext.request.contextPath}/article/browse",
                //展示图片空间按钮
                allowFileManager:true,
                //上传图片所对应的方法
                uploadJson:"${pageContext.request.contextPath}/article/upload",
                //上传图片是图片的形参名称
                filePostName:"articleImg"
            });
        });

    </script>
</head>
<body>
    <textarea id="editor_id" name="content" style="width:700px;height:300px;">
    &lt;strong&gt;HTML内容&lt;/strong&gt;
    </textarea>
</body>
</html>