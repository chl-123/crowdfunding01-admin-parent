//执行分页生成页面效果，任何时候调用这个函数都会重新加载页面
function generatePage() {
    //获取页面数据
    var pageInfo=getPageInfoRemote();

    //填充表格
    fillTableBody(pageInfo);
}
//访问服务器端程序获取pageInfo数据
function getPageInfoRemote() {
    var ajaxResult=$.ajax(
        {
            "url":"http://localhost:8080/crowdfunding/admin/to/role/info.json",
            "type":"post",
            "data":{
                "pageNum":window.pageNum,
                "pageSize":window.pageSize,
                "keyword":window.keyword
            },
            "async":false,
            "dataType":"json"

        }
    )
    var statusCode=ajaxResult.status;
    if(statusCode!=200){
        layer.msg("失败!相应状态码="+statusCode+"说明信息="+ajaxResult.statusText);
        return null;
    }
    //如果相应状态码是200，说明请求处理成功，获取pageInfo
    var resultEntity=ajaxResult.responseJSON;

    //从resultEntit中获取result属性
    var result=resultEntity.result;
    //判断result是否成功
    if(result=="FAILED"){
        layer.msg(resultEntity.message);
        return null;
    }
    //确认result为成功后获取pageInfo
    var pageInfo=resultEntity.data;
    return pageInfo;

}
//填充表格
function fillTableBody(pageInfo) {


    //清除tbody中的旧内容
    $("#rolePageBody").empty();
    //判断pageInfo对象是否有效
    if(pageInfo==null|| pageInfo===undefined|| pageInfo.list==null||pageInfo.length==0){
        $("#rolePageBody").append("<tr><td colspan='4' align='center'>抱歉！没有查询到你搜索的数据</td><tr/>");
    }
    //使用pageInfo的list属性填充tbody
    for (var i = 0; i < pageInfo.list.length; i++) {
        var role=pageInfo.list[i];
        var roleId=role.id;
        var roleName=role.name;
        var numberTd="<td>"+(i+1)+"</td>";
        var checkboxTd="<td><input type='checkbox'></td>";
        var roleNameTd="<td>"+roleName+"</td>";
        var checkBtn = "<button type='button' class='btn btn-success btn-xs'>" +
            "<i class='glyphicon glyphicon-check'></i></button>";
        var pencilBtn = "<button type='button' class='btn btn-primary btn-xs'>" +
            "<i class='glyphicon glyphicon-pencil'></i></button>";
        var removeBtn = "<button type='button' class='btn btn-danger btn-xs'>" +
            "<i class='glyphicon glyphicon-remove'></i></button>";
        var buttonTd = "<td>"+checkBtn+" "+pencilBtn+" "+removeBtn+"</td>";
        var tr = "<tr>"+numberTd+checkboxTd+roleNameTd+buttonTd+"</tr>";
        $("#rolePageBody").append(tr);
    }

}
function generateNavigator() {

}
function paginationCallBack(pageIndex,jQuery) {

}