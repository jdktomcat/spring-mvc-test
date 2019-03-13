<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>captcha验证码</title>
    <script src="${ctx}/resources/js/module/jquery/jquery-2.2.3.min.js"></script>

    <script>
        $(document).ready(function () {
            $("#captcha-image").click(function () {
                this.src = "${ctx}/mvc/captcha/image";
            });
            $("#validButton").click(function () {
                var captcha = $("#captcha-code").val();
                $.ajax({
                    url: "${ctx}/mvc/captcha/valid?captcha" + captcha,
                    cache: false,
                    type: "GET",
                    dataType: "json",
                    success: function (data) {
                        alert(data)
                    },
                    error: function () {
                    }
                });
            });
        });
    </script>
</head>
<body>
<img src="${ctx}/mvc/captcha/image" id="captcha-image" width="100" height="20">
code:<input type="text" id="captcha-code">
<input type="button" id="validButton" value="验证">
</body>
</html>
