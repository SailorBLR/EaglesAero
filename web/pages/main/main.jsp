<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="true" %>
<fmt:setBundle basename="resources.pagecontent"/>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="../../css/jquery.dataTables.css">
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <link href="../../css/style.css" rel="stylesheet" type="text/css">
</head>

<body>

<div class="container">
    <div class="header">
        <div style="display: block;" >
            <c:import url="../jspf/header.jsp"/>
        </div>

    </div>
    <div class="content">
        <c:import url="${include}"/>
    </div>
    <%--<div class="footer">
        <c:import url="../jspf/footer.jsp"/>;
    </div>--%>
</div>
</body>
</html>
