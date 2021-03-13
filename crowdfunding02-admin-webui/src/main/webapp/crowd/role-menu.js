function generateTree() {
    $.ajax({
        "url":"http://localhost:8080/crowdfunding/admin/menu/get/tree.json",
        "type":"post",
        "dataType":"json",
        "success":function (response) {
            var result=response.result;
            if(result=="SUCCESS"){
                var setting={
                    "view":{
                        "addDiyDom":myAddDiyDom,
                        "addHoverDom":addHoverDom,
                        "removeHoverDom":removeHoverDom
                    },
                    "data":{
                        "key":{
                            "url":"noExist"
                        }
                    }
                };
                var zNodes=response.data;
                $.fn.zTree.init($("#treeDemo"),setting,zNodes);
            }
            if(result=="FAILED"){}
            layer.msg(response.message);
        }
    });
}
function myAddDiyDom(treeId,treeNode) {
    //treeId是整个树形结构的Ul标签的id
    // zTree 生成 id 的规则
    // 例子： treeDemo_7_ico
// 解析： ul 标签的 id_当前节点的序号_功能
// 提示： “ul 标签的 id_当前节点的序号” 部分可以通过访问 treeNode 的 tId 属性得到
// 根据 id 的生成规则拼接出来 span 标签的 id
    var spanId=treeNode.tId+"_ico";
    //根据控制图标的span标签的id来找到这个span标签
    //删除旧的标签添加新的标签
    $("#"+spanId)
        .removeClass()
        .addClass(treeNode.icon);
}
//在鼠标移入节点时添加按钮组
function addHoverDom(treeId,treeNode) {
    // 按钮组的标签结构： <span><a><i></i></a><a><i></i></a></span>
    // 按钮组出现的位置： 节点中 treeDemo_n_a 超链接的后面
    // 为了在需要移除按钮组的时候能够精确定位到按钮组所在 span， 需要给 span 设置有规律的 id

    var btnGroupId=treeNode.tId+"_btnGrp"
    //如果长度为零则不再添加
    if($("#"+btnGroupId).length>0){
        return ;
    }
   // 准备各个按钮的 HTML 标签
    var addBtn = "<a id='"+treeNode.id+"' class='addBtn btn btn-info dropdown-toggle btn-xs'style='margin-left:10px;padding-top:0px;' href='#' title='添加子节点'>&nbsp;&nbsp;" +
        "<i class='fa fa-fw fa-plus rbg '></i></a>";
    var removeBtn = "<a id='"+treeNode.id+"' class='removeBtn btn btn-info dropdown-toggle btn-xs'style='margin-left:10px;padding-top:0px;' href='#' title=' 删 除 节 点 '>&nbsp;&nbsp; " +
        "<i class='fa fa-fw fa-times rbg '></i></a>";
    var editBtn = "<a id='"+treeNode.id+"' class='editBtn btn btn-info dropdown-toggle btn-xs'style='margin-left:10px;padding-top:0px;' href='#' title=' 修 改 节 点 '>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";


    //获取当前节点的级别数据
    var level=treeNode.level;

    //声明变量存储拼装好的按钮代码
    var btnHTML="";
    //判断当前节点的级别
    if(level==0){
        // 级别为 0 时是根节点， 只能添加子节点
        btnHTML=addBtn;
    }
    if(level==1){
        // 级别为 1 时是分支节点， 可以添加子节点、 修改
        btnHTML=addBtn+" "+editBtn;
        // 获取当前节点的子节点数量
        var length=treeNode.children.length;
        if(length==0){
            btnHTML=btnHTML+""+removeBtn;
        }
    }
    if(level==2){
        // 级别为 2 时是叶子节点， 可以修改、 删除
        btnHTML=editBtn+" "+removeBtn;
    }
    //找到附着在按钮组的超链接
    var anchorId=treeNode.tId+"_a";
    //执行在超链接后面附加的span元素的操作
    $("#"+anchorId).after("<span id='"+btnGroupId+"'>"+btnHTML+"</span>")
}
//在鼠标离开节点时删除按钮组



function removeHoverDom(treeId,treeNode) {
    //拼接按钮组的ID
    var btnGroupId=treeNode.tId+"_btnGrp";
    $("#"+btnGroupId).remove();
}