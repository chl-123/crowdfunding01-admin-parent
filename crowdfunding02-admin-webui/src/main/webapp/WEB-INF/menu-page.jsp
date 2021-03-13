<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp"%>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/role-menu.js"></script>
<script type="text/javascript" src="layer/layer.js"></script>
<script type="text/javascript">
	$(function () {
		generateTree();
		$("#treeDemo").on("click",".addBtn",function () {
			window.pid=this.id;
			$("#menuAddModal").modal("show");
			return false;
		});
		$("#menuSaveBtn").click(function () {
			//收集表单项中用户输入的数据
			var name=$.trim($("#menuAddModal [name=name]").val());
			var url=$.trim($("#menuAddModal [name=url]").val());
			// 单选按钮要定位到“被选中” 的那一个
			var icon=$("#menuAddModal [name=icon]:checked").val();
			$.ajax({
				"url":"http://localhost:8080/crowdfunding/admin/to/menu/save.json",
				"type":"post",
				"data":{
					"pid":window.pid,
					"name":name,
					"url":url,
					"icon":icon
				},
				"dataType":"json",
				"success":function (response) {
					var result=response.result;
					if(result=="SUCCESS"){
						layer.msg("操作成功");
						generatePage();
					}
					if(result=="FAILED"){
						layer.msg("操作失败"+response.message);
					}
				},
				"error":function (response) {
					layer.msg(response.status+" "+response.statusText);
				}
			});
			$("#menuAddModal").modal("hide");
			$("#menuResetBtn").click();
		})
		$("#treeDemo").on("click",".removeBtn",function () {
			// 将当前节点的 id 保存到全局变量
			window.id=this.id;


			$("#menuConfirmModal").modal("show");
			//获取zTreeObj对象
			var zTreeObj=$.fn.zTree.getZTreeObj("treeDemo");
			// 根据 id 属性查询节点对象
			// 用来搜索节点的属性名
			var key="id";
			//用来搜索节点的属性值
			var value=window.id;
			var currentNode=zTreeObj.getNodeByParam(key,value);
			$("#removeNodeSpan").html(" 【 <i class='"+currentNode.icon+"'></i>"+currentNode.name+"】 ");
			return false;
		});
		$("#confirmBtn").click(function () {
			$.ajax({
				"url":"http://localhost:8080/crowdfunding/admin/menu/to/remove.json",
				"type":"post",
				"data":{
					"id":window.id
				},
				"dataType":"json",
				"success":function (response) {
					var result=response.result;
					if(result=="SUCCESS"){
						layer.msg("操作成功");
						generateTree();
					}

					if(result=="FAILED"){
						layer.msg("操作失败!"+response.message);
					}
				},
				"error":function (response) {
					layer.msg(response.status+" "+response.statusText);
				}

			});
			$("#menuConfirmModal").modal("hide");
		});

		$("#treeDemo").on("click",".editBtn",function () {
			// 将当前节点的 id 保存到全局变量
			window.id=this.id;

			$("#menuEditModal").modal("show");
			// 获取 zTreeObj 对象
			var zTreeObj=$.fn.zTree.getZTreeObj("treeDemo");
			// 根据 id 属性查询节点对象
			// 用来搜索节点的属性名
			var key="id";

			//用来搜索节点的属性值
			var value=window.id;

			var currentNode=zTreeObj.getNodeByParam(key,value);

			//回显表单数据
			$("#menuEditModal [name=name]").val(currentNode.name);
			$("#menuEditModal [name=url]").val(currentNode.url);

			// 回显 radio 可以这样理解： 被选中的 radio 的 value 属性可以组成一个数组，
			// 然后再用这个数组设置回 radio， 就能够把对应的值选中

			$("#menuEditModal [name=icon]").val(currentNode.icon);

			return false;
		});
		$("#menuEditBtn").click(function () {
			//收集表单数据
			var name=$("#menuEditModal [name=name]").val();
			var url=$("#menuEditModal [name=url]").val();
			var icon=$("#menuEditModal [name=icon]:checked").val();

			$.ajax({
				"url":"http://localhost:8080/crowdfunding/admin/menu/to/update.json",
				"type":"post",
				"data":{
					"id":window.id,
					"name":name,
					"url":url,
					"icon":icon
				},
				"dataType":"json",
				"success":function (response) {
					var result=response.result;
					if(result=="SUCCESS"){
						layer.msg("操作成功");
						generatePage();
					}
					if(result=="FAILED"){
						layer.msg("操作失败"+response.message);
					}
				},
				"error":function (response) {
					layer.msg(response.status+" "+response.statusText);
				}
			});
			$("#menuEditModal").modal("hide");
		});
	});
</script>
<body>

	<%@ include file="/WEB-INF/include-nav.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="/WEB-INF/include-sidebar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

				<div class="panel panel-default">
					<div class="panel-heading">
						<i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
						<div style="float: right; cursor: pointer;" data-toggle="modal"
							data-target="#myModal">
							<i class="glyphicon glyphicon-question-sign"></i>
						</div>
					</div>
					<div class="panel-body">
						<!-- 这个ul标签是zTree动态生成的节点所依附的静态节点 -->
						<ul id="treeDemo" class="ztree"></ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/modal-menu-add.jsp" %>
	<%@include file="/WEB-INF/modal-menu-confirm.jsp" %>
	<%@include file="/WEB-INF/modal-menu-edit.jsp" %>
</body>
</html>