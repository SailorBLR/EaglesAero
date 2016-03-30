<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources.pagecontent"/>

<html>
<head>
    <title><fmt:message key="label.edit-form"/></title>
    <script> var message = "<fmt:message key='confirm.editing'/>";</script>
    <script> var loginError = "<fmt:message key='error.login-error'/>";</script>
    <script> var passwordError = "<fmt:message key='error.password-error'/>";</script>
    <script> var nameError = "<fmt:message key='error.name-error'/>";</script>
    <script type="text/javascript" src="../../scripts/validation_user_script.js"></script>
</head>

<div id="user-editing-form">
    <form id="edit" name="edit-user" method="POST" action="controller">
        <input type="hidden" name="command" value="edituser"/>
        <c:set var="user" value="${userId}"/>

        <h3 style="text-align: center"><fmt:message key="label.user-editing-form"/> </h3>

        <div id="input-zone">

            <div class="input-holder">
                <span class="label"><fmt:message key="label.id"/></span>
                <span><input class="input-field" type="number" name="id" value="${user.userId}" readonly/></span>
            </div>

            <div class="input-holder">
                <span class="label"><fmt:message key="label.login"/></span>
                <span><input class="input-field" id="login-input" type="text" name="login" pattern=""
                       value="${user.login}" required/></span>

                <p class="error" id="error-panel-login" style="text-decoration-color: #ff0903"></p>
            </div>

            <div class="input-holder">
                <span class="label"><fmt:message key="label.password"/></span>
                <span><input class="input-field" required id="password-input" type="password"
                             name="password" placeholder="*******" }></span>

                <p class="error" id="error-panel-password" style="text-decoration-color: #ff0903"></p>
            </div>
            <div class="input-holder">
                <span class="label"><fmt:message key="label.current-role"/></span>
                <span  class="input-field">${user.role}</span></div>
            <div class="input-holder">
                <span class="label"><fmt:message key="label.role"/></span>

                <span><select  class="input-field" name="role">

                    <option value="admin"><fmt:message key="text.administrator"/> </option>
                    <option value="dispatcher" selected><fmt:message key="text.dispatcher"/> </option>
                    <option value="hr-manager"><fmt:message key="text.hr-manager"/> </option>

                </select></span>
            </div>
            <div class="input-holder">
                <span class="label"><fmt:message key="label.name"/></span>
                <span><input class="input-field" id="name-input" type="text" name="name"
                             value="${user.name}" required></span>

                <p class="error" id="error-panel-name" style="text-decoration-color: #ff0903"></p>
            </div>
            <div class="input-holder">
                <span class="label"><fmt:message key="label.surname"/></span>
                <span><input class="input-field" id="surname-input" type="text" name="surname"
                             value="${user.surname}" required></span>

                <p class="error" id="error-panel-surname" style="text-decoration-color: #ff0903"></p>
            </div>

            <div class="input-holder">
                <span class="label"><fmt:message key="label.admin-password"/></span>
                <span><input class="input-field" id="adminPassword-input"
                             type="password" name="adminpassword" value="" required></span>

                <p class="error" id="error-panel-admin-password" style="text-decoration-color: #ff0903"></p>
            </div>
            <div>
                <input id="edit-button" class="button" type="button"
                       onclick="submitEditForm()" value="<fmt:message key="label.edit-user"/>"/>
                <button class="button" type="button"
                        onClick="history.go(-1);return true;"><fmt:message key="button.back"/></button>
            </div>
        </div>
    </form>
</div>

</html>
