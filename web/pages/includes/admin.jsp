<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources.pagecontent"/>

<html>
<head>
    <title><fmt:message key="label.adminpage"/></title>
    <script type="text/javascript" src="../../scripts/admin_script.js"></script>
    <script type="text/javascript" charset="utf8" src="../../scripts/common/jquery-1.12.1.min.js"></script>
    <script type="text/javascript" charset="utf8" src="../../scripts/common/jquery.dataTables.js"></script>
    <script type="text/javascript" src="../../scripts/common/hltable.js"></script>

</head>
<div id="table-data" style="display: none">
    <ul id="users-list">
        <c:forEach var="user" items="${users}">
            <li id="inner-li" data-id="${user.userId}"
                data-name="${user.name}" data-surname="${user.surname}"
                data-role="${user.role}" data-login="${user.login}">
            </li>
        </c:forEach>
    </ul>
</div>
<div class="page-info" style="width: 100%; text-align: center">
    <h2><fmt:message key="label.user-management"/> </h2>
</div>
<div id="tableUsersForm">
    <form name="tableUsersForm" method="get" action="controller">
        <input type="hidden" name="command" value="createform"/>

        <div id="tableUsers">
            <table id="users">
                <thead>
                <tr>
                    <th><fmt:message key="table.id"/></th>
                    <th><fmt:message key="table.login"/></th>
                    <th><fmt:message key="table.role"/></th>
                    <th><fmt:message key="table.name"/></th>
                    <th><fmt:message key="table.surname"/></th>
                </tr>
                </thead>
                <tfoot>
                <tr>
                    <th><fmt:message key="table.id"/></th>
                    <th><fmt:message key="table.login"/></th>
                    <th><fmt:message key="table.role"/></th>
                    <th><fmt:message key="table.name"/></th>
                    <th><fmt:message key="table.surname"/></th>
                </tr>
                </tfoot>
                <tbody></tbody>

            </table>
            <script>
                selectFunction();
                highlightTableRows("users", "hoverRow", "clickedRow");
            </script>
        </div>
        <div id="error-panel" >
            <br/>
            <h4 style="color: red">${userErrorMessage}</h4>
            <br/>
        </div>

        <div id="create-new-user">
            <input type="submit" class="button" value="<fmt:message key="button.add-new-user"/>"/>
        </div>

    </form>
</div>

<div class="popup-form-container" id="form-container" onload="manageDirection()">
    <div class="user-popup-content">
        <div class="flight-summary">
            <form name="flightdetails" method="GET" action="controller">
                <input type="hidden" name="command" value="editform">

                <div class="input-holder">
                    <span class="label"><fmt:message key="table.id"/></span>
                    <input type="text" id="id-value" name="userId" class="input-field" readonly>
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.log-in"/></span>
                    <input type="text" id="user-login" name="login" class="input-field" readonly>
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.name"/></span>
                    <input type="text" id="user-name" name="name" class="input-field" readonly>
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.surname"/></span>
                    <input type="text" id="user-surname" name="surname" class="input-field" readonly>
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.role"/></span>
                    <input type="text" id="user-role" name="role" class="input-field" readonly>
                </div>

                <div class="navi-bar">
                    <span style="margin-top: 5px"> <button class="button" type="button" onclick="hideEdit()">
                        <fmt:message key="button.back"/></button></span>
                    <span style="margin-top: 5px"><input id="edit-user" class="button" type="button"
                                                         onclick="deleteUser()"
                                                         value="<fmt:message key="button.delete-user"/>"/></span>
                    <span style="margin-top: 5px"><input id="delete-button" type="submit" class="button"
                                                         value="<fmt:message key="button.edit"/>"/></span>

                </div>
            </form>
        </div>
    </div>
</div>

</html>
