<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>

<script>
    $(function () {
        $("#article-show-table").jqGrid({
            url : "${app}/article/selectAll",
            datatype : "json",
            colNames : [ '编号', '标题', '作者', '简介', '内容','创建时间',"操作"],
            colModel : [
                {name : 'id',hidden:true},
                {name : 'title'},
                {name : 'author'},
                {name : 'brief'},
                {name : 'content',hidden:true},
                {name : 'createDate'},
                {name : 'operation',formatter:function (value,option,rows) {
                        return "<a class='btn btn-primary' onclick=\"openModal('edit','"+rows.id+"')\">修改</a>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;<a class='btn btn-danger' onclick=\"del('"+rows.id+"')\">删除</a>";
    }}
            ],
            styleUI:"Bootstrap",
            autowidth:true,
            height:"280px",
            rowNum : 4,
            rowList : [ 2, 3, 5,10 ],
            pager : '#article-page',
            viewrecords : true,
            caption : "所有文章展示",
        }).navGrid("#article-page", {edit : false,add : false,del : false,search:false});
    })

    function openModal(oper,id) {
        if("add"==oper){
            $("#article-id").val("");
            $("#article-title").val("");
            $("#article-author").val("");
            $("#article-brief").val("");
            KindEditor.html("#editor_id","");
        }
        if("edit"==oper){
            var article = $("#article-show-table").jqGrid("getRowData",id);
            $("#article-id").val(article.id);
            $("#article-title").val(article.title);
            $("#article-author").val(article.author);
            $("#article-brief").val(article.brief);
            KindEditor.html("#editor_id",article.content);
        }

        $("#article-modal").modal("show");
    }

    function save(){
        var id = $("#article-id").val();
        var data={};
        data.id= $.trim($("input[name=id]").val());
        data.title= $.trim($("input[name=title]").val());
        data.author= $.trim($("input[name=author]").val());
        data.brief= $.trim($("input[name=brief]").val());
        data.content =$(document.getElementsByTagName('iframe')[0].contentWindow.document.body).html();
        var url = "";
        if(id){
            url= "${app}/article/edit";
        }else{
            url = "${app}/article/add";
        }
        $.ajax({
            url:url,
            type:"post",
            //data:$("#article-form").serialize(),
            data:data,
            //data:{id:id,title:title,author:author,brief:brief,content:content,createDate:createDate},
            datatype:"json",
            success:function () {
                //自动刷新jqgrid
                $("#article-show-table").trigger("reloadGrid");
            }
        })
    }
    function del(id){
        $.ajax({
            url:"${app}/article/del?id="+id,
            datatype:"json",
            type:"post",
            success:function () {
                //自动刷新jqgrid
                $("#article-show-table").trigger("reloadGrid");
            }
        })
    }

    KindEditor.create('#editor_id',{
        width : '460px',
        //点击图片空间按钮时发送的请求
        fileManagerJson:"${pageContext.request.contextPath}/article/browse",
        //展示图片空间按钮
        allowFileManager:true,
        //上传图片所对应的方法
        uploadJson:"${pageContext.request.contextPath}/article/upload",
        //上传图片是图片的形参名称
        filePostName:"articleImg",
        afterBlur:function () {
            this.sync();
        }
    });


</script>

        <!--中心内容-->
        <div class="col-sm-10">
            <ul class="nav nav-tabs">
                <li role="presentation" class="active"><a href="#">所有文章</a></li>
                <li role="presentation"><a href="#" onclick="openModal('add','')">添加文章</a></li>
            </ul>
                <!--表格-->
            <table id="article-show-table"></table>
            <div id="article-page" style="height: 40px"></div>
        </div>


<div id="article-modal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width: 683px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">文章操作</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="article-form">
                    <input type="hidden" name="id" id="article-id">
                    <div class="form-group">
                        <label for="article-title" class="col-sm-2 control-label">文章标题</label>
                        <div class="col-sm-10">
                            <input type="text" name="title" class="form-control" id="article-title" placeholder="请输入文章标题">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="article-author" class="col-sm-2 control-label">文章作者</label>
                        <div class="col-sm-10">
                            <input type="text" name="author" class="form-control" id="article-author" placeholder="请输入文章作者">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="article-brief" class="col-sm-2 control-label">文章简介</label>
                        <div class="col-sm-10">
                            <input type="text" name="brief" class="form-control" id="article-brief" placeholder="请输入文章简介">
                        </div>
                    </div>
                    <textarea id="editor_id" name="content" style="width:700px;height:300px;"></textarea>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="save()" data-dismiss="modal">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

