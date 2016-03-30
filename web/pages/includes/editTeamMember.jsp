<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources.pagecontent"/>

<html>
<head>
    <title><fmt:message key="label.edit-team-member"/></title>
    <script src="../../scripts/validation_team_member_script.js"></script>
    <script type="text/javascript" charset="utf8" src="../../scripts/common/jquery-1.12.1.min.js"></script>
    <script type="text/javascript" charset="utf8" src="../../scripts/common/jquery.dataTables.js"></script>

    <script type="text/javascript" charset="utf8" src="../../scripts/common/hltable.js"></script>

</head>

<div id="team-member-data" style="display: none">
    <ul id="flight-list">
        <c:forEach var="flight" items="${flights}" >
            <li id="inner-li" data-id="${flight.flightId}"
                data-departurePoint="${flight.flightFrom}" data-arrivingPoint="${flight.flightTo}"
                data-depTime="${flight.getFormattedDate(flight.departureTime)}"
                data-arrTime="${flight.getFormattedDate(flight.arrivingTime)}"
                data-status="${flight.status}">
            </li>
        </c:forEach>
    </ul>
</div>

<div class="page-info" style="width: 100%; text-align: center">
    <h2><fmt:message key="label.edit-team-member"/> </h2>
</div>

<div id="team-member-editing-form" onload="fill()">
    <form name="edit-team-member" method="GET" action="controller">
        <input type="hidden" name="command" value="editteammember"/>

        <c:set var="teamMember" value="${teamMember}" />

        <div id="input-zone">

            <div class="input-holder">
                <span class="label"><fmt:message key="label.id"/></span>
                <span><input class="input-field"
                             type="number" name="id" value="${teamMember.personId}" readonly/></span>
            </div>
            <div class="input-holder">
                <span class="label"><fmt:message key="label.name"/></span>
                <span><input class="input-field" type="text" name="name"
                       id="name-id" pattern="[A-ZА-ЯЁ]{1}[a-zа-яё]{3,20}" value="${teamMember.name}" required></span>
            </div>
            <div class="input-holder">
                <span class="label"><fmt:message key="label.surname"/></span>
                <span><input class="input-field" type="text" name="surname"
                       id="surname-id" pattern="[A-ZА-ЯЁ]{1}[a-zа-яё]{3,20}"
                             value="${teamMember.surname}" required></span>
            </div>

            <div class="input-holder">
                <span class="label"><fmt:message key="label.speciality"/></span>
                <input class="input-field" id="current-role" type="text" name="role"
                       value="${teamMember.role}" readonly>
            </div>

            <div class="input-holder">
                <span class="label"><fmt:message key="label.current-qualification"/></span>
                <span><input class="input-field" id="current-qualification" type="text" name="current"
                       value="${teamMember.qualification}" readonly></span>
            </div>
            <div class="input-holder" onload="qualiSelect()">
                <span class="label"><fmt:message key="label.qualification"/></span>

                <span><select class="input-field" name="qualificationSelect" id="qualiField"
                        onchange="qualiSelectDefault()">

                    <option value="0" selected></option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="not specified"><fmt:message key="text.not-specified"/></option>

                </select></span>
                <input type="hidden" id="hidden-qualification" name="qualification" value="${teamMember.qualification}">
            </div>

            <div class="input-holder">
                <span class="label"><fmt:message key="label.birthday"/></span>
                <span class="input-field">${teamMember.getBirthDay()}</span>
            </div>

            <input type="hidden" id="last-status" value="${teamMember.status}">
            <div class="input-holder">
                <span class="label"><fmt:message key="label.status"/></span>
                <input class="input-field" id="status" type="text" name="current-status"
                       value="${teamMember.status}" readonly>
            </div>
            <div id="button-area" style="position: relative; margin-top: 10px; margin-bottom: 10px">
                <button type="button" class="editButton" id="vocation-button"
                        onclick="vocation()"><fmt:message key="button.get-vocation"/></button>
                <button type="button" class="editButton" id="dismiss-button"
                        onclick="dismiss()"><fmt:message key="button.dismiss"/></button>
                <button type="button" class="editButton" id="abort-button"
                        onclick="resetForm()"><fmt:message key="button.back-to-work"/></button>
            </div>
            <div class="input-holder" id="password-area">
                <span class="label">*<fmt:message key="label.admin-password"/></span>
                <input class="input-field" type="password"
                       id="adminpassword-id" name="adminpassword" pattern="[a-zA-Z0-9]{6,10}" required>
            </div>

            <div id="navi-pan">
                <input type="submit" class="editButton"
                       value="<fmt:message key="label.edit-team-member"/>"/>

                <input type="button" id="show-history" class="editButton"
                       value="<fmt:message key='button.show-person-history'/>" onclick="displayEdit()">

                <button type="button" class="editButton"
                             onClick="history.go(-1);return true;"><fmt:message key="button.back"/></button>
            </div>
        </div>
    </form>
</div>


<div class="popup-form-container" id="form-container" style="display: none">
    <div class="popup-content">
        <div id="tablePersons" class="hover">

            <form name="tablePersonalForm" method="GET" action="controller">
                <table id="flights">
                    <thead>
                    <tr>
                        <th><fmt:message key="table.flight-id"/></th>
                        <th><fmt:message key="table.departure-point"/></th>
                        <th><fmt:message key="table.arriving-point"/></th>
                        <th><fmt:message key="table.departure-time"/></th>
                        <th><fmt:message key="table.arriving-time"/></th>
                        <th><fmt:message key="table.status"/></th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr>
                        <th><fmt:message key="table.flight-id"/></th>
                        <th><fmt:message key="table.departure-point"/></th>
                        <th><fmt:message key="table.arriving-point"/></th>
                        <th><fmt:message key="table.departure-time"/></th>
                        <th><fmt:message key="table.arriving-time"/></th>
                        <th><fmt:message key="table.status"/></th>
                    </tr>
                    </tfoot>
                    <tbody></tbody>

                </table>

                <div id="error-panel">
                    <br/>
                    ${personErrorMessage}
                    <br/>
                </div>
                <div id="create-new-person">
                    <input type="button" class="button" onclick="hideEdit()" value="<fmt:message key="button.back"/>"/>
                </div>
            </form>
        </div>
    </div>
</div>

</html>

