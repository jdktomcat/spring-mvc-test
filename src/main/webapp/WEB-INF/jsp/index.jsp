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
            $('#captcha-image').on('load', function () {
                var name = "cookie-uuid";
                var cookie = document.cookie;
                if (cookie) {
                    var cookies = cookie.split(";")
                    for (var i = 0; i < cookies.length; i++) {
                        var c = cookies[i].trim();
                        if (c.indexOf(name) == 0) {
                            $("#" + name).val(c.substring(name.length + 1, c.length));
                            break;
                        }
                    }
                }
            });
            $("#validButton").click(function () {
                var captcha = $("#captcha-code").val();
                var uuid = $("#cookie-uuid").val();
                $.ajax({
                    url: "${ctx}/mvc/captcha/valid?captcha=" + captcha + "&uuid=" + uuid,
                    cache: false,
                    type: "GET",
                    dataType: "json",
                    success: function (data) {
                        if (!data.result) {
                            alert(data.message + " " + data.now);
                        }
                    },
                    error: function () {
                    }
                });
            });
        });
    </script>
</head>
<body>
<div>
    <input type="hidden" id="cookie-uuid">
    code:<input type="text" id="captcha-code">
    <img src="${ctx}/mvc/captcha/image" id="captcha-image" width="150" height="42">
    <input type="button" id="validButton" value="验证">
</div>
</body>
</html>
