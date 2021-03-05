<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <base href="http://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/"/>
    <%@include file="/WEB-INF/include-head.jsp" %>
    <link rel="stylesheet" href="css/pagination.css"/>
    <script type="text/javascript" src="jquery/jquery.pagination.js"></script>
    <script type="text/javascript">
        $(function () {
            initPagination();
        });

        //生成页面导航条
        function initPagination() {
            //获取总记录数
            var totalRecord = "${requestScope.pageInfo.total}";
            //声明一个JSON对象存储Pagination要设置的属性
            var properties = {
                num_edge_entries: 1, //边缘页数
                num_display_entries: 2, //主体页数
                callback: pageselectCallback,
                items_per_page:${requestScope.pageInfo.pageSize},//每页显示页数
                current_page:${requestScope.pageInfo.pageNum - 1},//当前页
                prev_text: "上一页",
                next_text: "下一页"
            };
            //生成页码导航条
            $("#Pagination").pagination(totalRecord, properties)
        }

        //用户点击“1,2.3”这样的页码时调用这个函数实现页面跳转
        function pageselectCallback(pageIndex, JQuery) {
            var pageNum = pageIndex + 1;
            window.location.href = "admin/get/page.html?pageNum=" + pageNum + "&keyword=${param.keyword}";
            //由于每一个页码按钮都是超链接，所以在这个函数最后取消超链接的默认行为
            return false;
        }
    </script>
</head>

<body>
<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form action="http://localhost:8080/crowdfunding/admin/get/page.html" class="form-inline"
                          role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input name="keyword" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <%-- <button type="button" class="btn btn-primary" style="float:right;"
                             onclick="window.location.href='add.html'"><i class="glyphicon glyphicon-plus"></i> 新增
                     </button>--%>
                    <a href="admin/to/add/page.html" class="btn btn-primary" style="float: right;"><i
                            class="glyphicon glyphicon-plus"></i> 新增</a>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%--实现呈现表格--%>
                            <c:if test="${empty requestScope.pageInfo.list}">
                                <tr>
                                    <td colspan="6" align="center">抱歉！沒有查到你要的数据</td>
                                </tr>
                            </c:if>
                            <c:if test="${!empty requestScope.pageInfo.list}">
                                <c:forEach items="${requestScope.pageInfo.list}" var="admin" varStatus="myStatus">
                                    <tr>
                                        <td>${myStatus.count}</td>
                                        <td><input type="checkbox"></td>
                                        <td>${admin.loginAcct}</td>
                                        <td>${admin.userName}</td>
                                        <td>${admin.email}</td>
                                        <td>
                                            <button type="button" class="btn btn-success btn-xs"><i
                                                    class=" glyphicon glyphicon-check"></i></button>
                                                <%--<button type="button" class="btn btn-primary btn-xs"><i
                                                        class=" glyphicon glyphicon-pencil"></i></button>--%>
                                            <a href="http://localhost:8080/crowdfunding/admin/to/edit/page.html? adminId=${admin.id }&pageNum=${requestScope.pageInfo.pageNum }&keyword=${param.keyword }"
                                               class="btn btn-primary btn-xs"><i
                                                    class=" glyphicon glyphicon-pencil"></i></a>
                                                <%--<button type="button" class="btn btn-danger btn-xs"><i
                                                        class=" glyphicon glyphicon-remove"></i></button>--%>
                                            <a href="http://localhost:8080/crowdfunding/admin/remove/${admin.id}/${requestScope.pageInfo.pageNum}/${param.keyword}.html"
                                               class="btn btn-danger btn-xs"><i
                                                    class=" glyphicon glyphicon-remove"></i></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>

                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>