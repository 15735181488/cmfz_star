<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<h1>文章搜索</h1>
<script>
    $(function () {
        $("#article-search").click(function () {
            $.ajax({
                url: "${pageContext.request.contextPath}/article/search",
                type: "post",
                datatype: "json",
                data: "input=" + $("#article-input").val(),
                success: function (data) {
                    $("#article-search-show").empty();
                    $.each(data, function (i, article) {
                        var tr = $("<tr>" +
                            "<td>" + article.title + "</td>" +
                            "<td>" + article.author + "</td>" +
                            "<td>" + article.brief + "</td>" +
                            "<td><a class='btn btn-danger' onclick=\"openModal('" + article.id + "','" + article.title + "','" + article.author + "','" + article.brief + "','" + article.content + "')\">详情</a></td>" +
                            "</tr>");
                        $("#article-search-show").append(tr);
                    })
                }
            })
        })

    })

    KindEditor.create('#editor_id', {
        width: '460px',
        //点击图片空间按钮时发送的请求
        fileManagerJson: "${pageContext.request.contextPath}/article/browse",
        //展示图片空间按钮
        allowFileManager: true,
        //上传图片所对应的方法
        uploadJson: "${pageContext.request.contextPath}/article/upload",
        //上传图片是图片的形参名称
        filePostName: "articleImg",
        afterBlur: function () {
            this.sync();
        }
    });

    function openModal(id, title, author, brief, content) {
        $("#article-id").val(id);
        $("#article-title").val(title);
        $("#article-author").val(author);
        $("#article-brief").val(brief);
        KindEditor.html("#editor_id", content);
        $("#article-modal").modal("show");
    }

</script>

<div class="row">
    <div class="col-sm-4"></div>
    <div class="col-sm-4">
        <div class="input-group">
            <input type="text" class="form-control" id="article-input" placeholder="请输入关键字...">
            <span class="input-group-btn">
        <button class="btn btn-primary" type="button" id="article-search">百度一下</button>
      </span>
        </div><!-- /input-group -->
    </div>
    <div class="col-sm-4"></div>
    <br><br>
    <table class='table table-hover' id="article-search-show" border="1px" style="width: 500px" align="center"></table>

</div>


<div id="article-modal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width: 683px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">文章详情</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="article-form">
                    <input type="hidden" name="id" id="article-id">
                    <div class="form-group">
                        <label for="article-title" class="col-sm-2 control-label">文章标题</label>
                        <div class="col-sm-10">
                            <input type="text" name="title" class="form-control" id="article-title"
                                   placeholder="请输入文章标题">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="article-author" class="col-sm-2 control-label">文章作者</label>
                        <div class="col-sm-10">
                            <input type="text" name="author" class="form-control" id="article-author"
                                   placeholder="请输入文章作者">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="article-brief" class="col-sm-2 control-label">文章简介</label>
                        <div class="col-sm-10">
                            <input type="text" name="brief" class="form-control" id="article-brief"
                                   placeholder="请输入文章简介">
                        </div>
                    </div>
                    <textarea id="editor_id" name="content" style="width:700px;height:300px;"></textarea>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->