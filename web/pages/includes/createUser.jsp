
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources.pagecontent"/>

<html>
<head>
    <title><fmt:message key="label.new-user"/></title>
    <script> var loginError = "<fmt:message key='error.login-error'/>";</script>
    <script> var passwordError = "<fmt:message key='error.password-error'/>";</script>
    <script> var nameError = "<fmt:message key='error.name-error'/>";</script>
    <script type="text/javascript" src="../../scripts/validation_user_script.js"></script>
</head>
<div id="person-creating-form">
    <form id="createForm" name="new-person" method="POST" action="controller">
        <input type="hidden" name="command" value="createuser"/>

        <h3 style="text-align: center"><fmt:message key="label.user-creating-form"/> </h3>

        <div id="input-zone">

            <div class="input-holder">
                <span class="label">*<fmt:message key="label.login"/></span>
                <span><input class="input-field" id="login-input" type="text" name="login" value=""/></span>

                <p class="error" id="error-panel-login" style="text-decoration-color: #ff0903"></p>
            </div>

            <div class="input-holder">
                <span class="label">*<fmt:message key="label.password"/></span>
                <span><input class="input-field" id="password-input" type="password" name="password" value=""></span>

                <p class="error" id="error-panel-password" style="text-decoration-color: #ff0903"></p>
            </div>

            <div class="input-holder">
                <span class="label">*<fmt:message key="table.role"/></span>
                <span><select  class="input-field" name="role">
                    <option value="admin"><fmt:message key="text.administrator"/></option>
                    <option value="dispatcher" selected><fmt:message key="text.dispatcher"/></option>
                    <option value="hr-manager"><fmt:message key="text.hr-manager"/></option>
                </select></span>
            </div>

            <div class="input-holder">
                <span class="label">*<fmt:message key="label.name"/></span>
                <span><input  class="input-field" id="name-input" type="text" name="name" value=""></span>

                <p class="error" id="error-panel-name" style="text-decoration-color: #ff0903"></p>
            </div>

            <div class="input-holder">
                <span class="label">*<fmt:message key="label.surname"/></span>
                <span><input  class="input-field" id="surname-input" type="text" name="surname" value=""></span>

                <p class="error" id="error-panel-surname" style="text-decoration-color: #ff0903"></p>
            </div>

            <div id="buttons-hbox">
                <input type="button" class="button" onclick="submitCreate()"
                       value="<fmt:message key="button.create"/>"/>
                <button type="button" class="button" onClick="history.go(-1);return true;">
                    <fmt:message key="button.back"/></button>
            </div>
        </div>
    </form>
</div>

</html>
