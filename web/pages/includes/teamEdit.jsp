<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources.pagecontent"/>

<html>
<head>
    <title><fmt:message key="label.edit-flight-team"/></title>
    <script src="../../scripts/team_edit_script.js"></script>

    <script type="text/javascript" charset="utf8" src="../../scripts/common/jquery-1.12.1.min.js"></script>
    <script type="text/javascript" charset="utf8" src="../../scripts/common/jquery.dataTables.js"></script>
    <script type="text/javascript" charset="utf8" src="../../scripts/common/hltable.js"></script>
    <script>
        var defaultOption = "<fmt:message key="text.not-specified"/>";
    </script>

</head>

<div id="table-data" style="display: none">
    <ul id="persons-list">
        <c:forEach var="teammembers" items="${flightTeam}">
            <li id="inner-li" data-id="${teammembers.personId}"
                data-name="${teammembers.name}" data-surname="${teammembers.surname}"
                data-role="${teammembers.role}" data-qualification="${teammembers.qualification}">
            </li>
        </c:forEach>
    </ul>
</div>
<div class="page-info" style="width: 100%; text-align: center">
    <h2><fmt:message key="label.edit-flight-team"/> </h2>
</div>
<div id="alldata" style="display: none">
    <ul id="available-persons">
        <c:forEach var="availableTeamMembers" items="${teamMembers}">
            <li id="inner-li" data-id="${availableTeamMembers.personId}"
                data-name="${availableTeamMembers.name}" data-surname="${availableTeamMembers.surname}"
                data-role="${availableTeamMembers.role}" data-qualification="${availableTeamMembers.qualification}">
            </li>
        </c:forEach>
    </ul>
</div>
<div>
    <script>
        var teamMembersList = document.getElementById("persons-list").getElementsByTagName('li');
        var dataSetTest = [];
        for (var i = 0; i < teamMembersList.length; i++) {
            dataSetTest.push([
                teamMembersList[i].getAttribute("data-id"),
                teamMembersList[i].getAttribute("data-name"),
                teamMembersList[i].getAttribute("data-surname"),
                teamMembersList[i].getAttribute("data-role"),
                teamMembersList[i].getAttribute("data-qualification")
            ]);
        }
        var availablePersonsList = document.getElementById("available-persons").getElementsByTagName('li');
        var availableTest = [];
        for (var i = 0; i < availablePersonsList.length; i++) {
            availableTest.push([
                availablePersonsList[i].getAttribute("data-id"),
                availablePersonsList[i].getAttribute("data-name"),
                availablePersonsList[i].getAttribute("data-surname"),
                availablePersonsList[i].getAttribute("data-role"),
                availablePersonsList[i].getAttribute("data-qualification")
            ]);
        }

    </script>
</div>

<div id="team-member-editing-form">

    <form name="tablePersonalForm" method="GET" action="controller">
        <input type="hidden" name="command" value="editteam"/>
        <input type="hidden" name="directId" value="${directFlight}"/>
        <input type="hidden" name="backId" value="${backFlight}"/>
        <input type="hidden" id="stuff-list" name="stuffList"/>

        <table id="persons">
            <thead>
            <tr>
                <th><fmt:message key="table.id"/></th>
                <th><fmt:message key="table.name"/></th>
                <th><fmt:message key="table.surname"/></th>
                <th><fmt:message key="table.occupation"/></th>
                <th><fmt:message key="table.class"/></th>
            </tr>
            </thead>
            <tfoot>
            <tr>
                <th><fmt:message key="table.id"/></th>
                <th><fmt:message key="table.name"/></th>
                <th><fmt:message key="table.surname"/></th>
                <th><fmt:message key="table.occupation"/></th>
                <th><fmt:message key="table.class"/></th>
            </tr>
            </tfoot>
            <tbody></tbody>

        </table>

        <script type="text/javascript">
            selectFunction();
            highlightTableRows("persons", "hoverRow", "clickedRow")
        </script>
        <div id="navi-pan">
            <button type="button" class="button"
                    onClick="history.go(-1);return true;"><fmt:message key="button.back"/></button>
            <button type="submit" class="button" onclick="editTeam()"
            ><fmt:message key="button.save-changes"/></button>

        </div>
    </form>
</div>

<div class="popup-form-container" id="form-container" onload="fill()">
    <div class="popup-edit-content">
        <div class="info">


            <form name="team-member-details" method="GET" action="controller">
                <input type="hidden" id="personToReplaceId" readonly>
                <div class="input-holder">
                    <select class="input-field" id="team-member" onchange="showDetails()"></select>
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="table.id"/></span>
                    <span><input class="input-field" type="text" id="id-value" name="idvalue" readonly></span>
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.name"/></span>
                    <input class="input-field" type="text" id="name" name="flightfrom" readonly>
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.surname"/></span>
                    <input class="input-field" type="text" id="surname" name="departure" readonly>.
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.role"/></span>
                    <input class="input-field" type="text" id="role" name="plane" readonly>
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.qualification"/></span>
                    <input class="input-field" type="text" id="qualification" name="plane" readonly>
                </div>
            </form>


            <div>
               <span> <button class="button" type="button" onclick="hideEdit()">
                   <fmt:message key="button.back"/></button></span>
                <span><button id="add-person" class="button" type="button" onclick="insertRow()">
                    <fmt:message key="button.add-to-race"/></button> </span>
            </div>
        </div>
    </div>
</div>
</html>