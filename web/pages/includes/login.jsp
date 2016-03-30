<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources.pagecontent"/>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>
        <fmt:message key="label.logtitle"/>
    </title>
    <script type="text/javascript" src="../../scripts/validation_user_script.js"></script>
    <script> var loginError = "<fmt:message key='error.login-error'/>";</script>
    <script> var passwordError = "<fmt:message key='error.password-error'/>";</script>
</head>
<div class="login-wrapper">
    <div id="login-form" class="login-form">
        <form class="input-zone" id="loginform" name="loginForm" method="post" action="controller">
            <input type="hidden" name="command" value="login"/>
            <c:set var="lastcommand" value="login" scope="session"/>
            <c:set var="lastQuery" value="command=login" scope="session"/>
            <div class="view-definition" style="text-align: center">
                <h4><fmt:message key="text.log-in"/></h4>
            </div>
            <div>
                <span class="label" style="font-weight: bold">*<fmt:message key="label.login"/></span>
            <span><input id="login-input" class="input-field" type="text"
                         name="login" min="4" max="20" value=""
                         autocomplete="on" required/></span>
            </div>
            <p class="error" id="error-panel-login" style="text-decoration-color: #ff0903"></p>

            <div>
                <span class="label" style="font-weight: bold">*<fmt:message key="label.password"/></span>
            <span><input class="input-field" id="password-input"
                         type="password" name="password" min="6" max="10"
                         value="" autocomplete="on" required/></span>
            </div>
            <p class="error" id="error-panel-password" style="text-decoration-color: #ff0903"></p>

            <div id="error-panel">
                <br/>
                <h4 style="color: red">${errorLoginPassMessage}</h4>
                <h4 style="color: red">${wrongAction}</h4>
                <h4 style="color: red">${nullPage}</h4>
                <br/>
            </div>
            <input class="button" id="login-button"
                   type="button" onclick="submitForm()" value="<fmt:message key="label.log-in"/>"/>
        </form>
    </div>
</div>

</html>

