<%--
  Created by IntelliJ IDEA.
  User: chl
  Date: 2021/1/22
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="script/jquery-1.7.2.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btn_ajax").click(function () {

                $.ajax({
                    url: "http://localhost:8080/crowdfunding/crowdTest",
                    type: "post",
                    dataType: "text",
                    success: function (msg) {
                        /* var tb="<table>";
                         tb+="<tr><th>用户名</th><th>密码</th></tr>";
                         tb+="<tr><th>"+msg.username+"</th><th>"+msg.password+"</th></tr>";
                         tb+="</table>" ;
                         $("div").append(tb);*/
                        alert('ddaaa')
                        $("#msg").html(msg);
                        // alert("ddd")
                        alert(msg);

                    }
                });
            });
        });
    </script>
</head>
<body>
<a href="http://localhost:8080/crowdfunding/crowdTest">ddd</a>
<input type="button" value="ddd" id="btn_ajax">
<a href="http://localhost:8080/crowdfunding/test">aaa</a>
</body>
</html>
