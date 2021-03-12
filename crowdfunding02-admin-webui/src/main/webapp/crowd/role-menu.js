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