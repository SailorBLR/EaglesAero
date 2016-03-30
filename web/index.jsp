<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 20.01.2016
  Time: 23:18
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<c:set var="locale" value="en_US" scope="session"/>
<c:set var="role" value="guest" scope="session"/>
<c:set var="login" value="Guest" scope="session"/>
<c:set var="include" value="/pages/includes/login.jsp" scope="session"/>

<head><title>Index</title></head>
<body>


<jsp:forward page="/pages/main/main.jsp"/>

</body>
</html>
