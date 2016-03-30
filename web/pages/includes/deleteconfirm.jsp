<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources.pagecontent"/>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>
        <fmt:message key="label.confirm-delete"/>
    </title>
    <script type="text/javascript" src="../../scripts/validation_user_script.js"></script>
    <script> var passwordError = "<fmt:message key='error.password-error'/>";</script>

</head>

<div id="delete-form">
    <form id="confirmform" name="loginForm" method="post" action="controller">
        <input type="hidden" name="command" value="deleteuser">
        <input type="hidden" name="userId" value="${userId}" readonly>

        <p><span><fmt:message key="text.confirm-delete"/></span><span> ${userName} </span> <span>?</span></p>

        <div class="input-holder">
            <span class="label"><fmt:message key="label.admin-password"/></span>
            <span><input class="input-field" id="password-input" type="password"
                         name="password" min="6" max="10" value="" required/></span>
            <p class="error" id="error-panel-password" style="text-decoration-color: #ff0903"></p>
        </div>
        <div id="back-button" style="width: 100%; align-content: center; margin-bottom: 5px">
            <input class="button" id="submit-button"
                   type="button" onclick="submitDelete()" value="<fmt:message key="button.delete-user"/>"/>


            <button type="button" class="button" style="margin-top: 5px"
                    onClick="history.go(-1);return true;"><fmt:message key="button.back"/>
            </button>

        </div>
    </form>
</div>

</html>
