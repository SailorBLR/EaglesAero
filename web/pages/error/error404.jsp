<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setBundle basename="resources.pagecontent"/>
<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title><fmt:message key="label.error-page"/> </title>
    <link href="../../css/style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="../../scripts/error_script.js"></script>
</head>
<body>
<div class="button-holder">
    <h1><fmt:message key="message.error.404"/> </h1>
    <button class="button" onclick="mainRedirect()"><fmt:message key="button.main"/> </button>
    <button type="button" class="button" style="margin-top: 5px"
            onClick="history.go(-1);return true;"><fmt:message key="button.back"/>
    </button>
</div>
</body>
</html>