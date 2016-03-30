<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources.pagecontent"/>

<html>
<head>
    <title><fmt:message key="label.flight-team-creating"/></title>
    <script src="../../scripts/team_create_script.js"></script>

    <script type="text/javascript" src="../../scripts/common/hltable.js"></script>
    <script type="text/javascript" charset="utf8" src="../../scripts/common/jquery-1.12.1.min.js"></script>
    <script type="text/javascript" charset="utf8" src="../../scripts/common/jquery.dataTables.js"></script>
    <link rel="stylesheet" type="text/css" href="../../css/jquery.dataTables.css">

    <script>
        var pilots = "${pilot}";
        var stuarts = "${stuart}";
        var technics = "${technic}";

    </script>

</head>
<div class="page-info" style="width: 100%; text-align: center">
    <h2><fmt:message key="label.flight-team-creating"/> </h2>
</div>
<div id="table-data" style="display: none">
    <ul id="stuff-list">
        <c:forEach var="teammembers" items="${teamMembers}">
            <li id="inner-li" data-id="${teammembers.personId}"
                data-name="${teammembers.name}" data-surname="${teammembers.surname}"
                data-role="${teammembers.role}" data-qualification="${teammembers.qualification}">
            </li>
        </c:forEach>
    </ul>
</div>

<div class="info-zone">
    <div class="info-flight">
        <form name="flightdetails" method="GET" action="controller">
            <div class="input-holder">
                <span class="label"><fmt:message key="label.direct-flight"/></span>
                <span><input class="input-field" type="text" id="id-flight-value"
                             name="idvalue" value="${idvalue}" readonly></span>
            </div>

            <div class="input-holder">
                <span class="label"><fmt:message key="label.back-flight"/></span>
                <span><input class="input-field" type="text" id="id-backflight-value"
                             name="backFlight" value="${backFlight}" readonly></span>
            </div>

            <div class="input-holder">
                <span class="label"><fmt:message key="text.plane"/></span>
                <span><input class="input-field" type="text" id="plane" name="plane" value="${plane}" readonly></span>
            </div>
        </form>
    </div>

    <div class="info-person">
        <form name="team-member-details" method="GET" action="controller">

            <div class="input-holder">
                <span class="label"><fmt:message key="table.id"></fmt:message></span>
                <span><input class="input-field" type="text" id="id-value" name="idvalue" readonly></span>
            </div>

            <div class="input-holder">
                <span class="label"><fmt:message key="label.name"></fmt:message></span>
                <span><input class="input-field" type="text" id="name" name="flightfrom" readonly></span>
            </div>

            <div class="input-holder">
                <span class="label"><fmt:message key="label.surname"></fmt:message></span>
                <span><input class="input-field" type="text" id="surname" name="departure" readonly>.</span>
            </div>

            <div class="input-holder">
                <span class="label"><fmt:message key="label.role"/></span>
                <span><input class="input-field" type="text" id="role" name="plane" readonly></span>
            </div>

            <div class="input-holder">
                <span><input class="input-field" type="text" id="qualification" name="plane" readonly></span>
                <span style="float: left">
                    <fmt:message key="label.qualification"/></span>
            </div>
        </form>
        <div style="float: left; margin-bottom: 5px; margin-top: 20px">
            <button id="add-button" class="button"
                    onclick="insRow(pilots,technics,stuarts<%--${pilots},${technics},${stuarts}--%>)"
                    disabled><fmt:message key="button.add-to-race"/></button>
            <button id="clear-table" class="button" onclick="clearForm()" disabled><fmt:message
                    key="button.clear"/></button>
            <button id="back-button" class="button" onclick="history.go(-1);return true;"
                    type="button"><fmt:message key="button.back"/></button>
        </div>
    </div>
</div>


<div id="selects-zone" class="selects">
    <div class="input-holder" id="pilots-sp">
        <span class="label"><fmt:message key="label.pilots"/> </span>

    </div>
    <div class="input-holder" id="technic-sp">
        <span class="label"><fmt:message key="label.technics"/> </span>

    </div>
    <div class="input-holder" id="stuart-sp">
        <span class="label"><fmt:message key="label.stuarts"/> </span>

    </div>
</div>

<div class="table-zone">
    <form id="datatable">
        <input type="hidden" name="command" value="createteam">
        <input id="listId" type="hidden" name="listId">
        <input id="flightId" type="hidden" name="flightId">
        <input id="backflightId" type="hidden" name="backflight">
        <table id="persons" width="80%" cellpadding="0" cellspacing="0" border="0" class="dataTable">
            <thead>
            <tr>
                <th><fmt:message key="table.id"/></th>
                <th><fmt:message key="table.name"/></th>
                <th><fmt:message key="table.surname"/></th>
                <th><fmt:message key="table.role"/></th>
                <th><fmt:message key="table.class"/></th>
            </tr>
            </thead>
            <tfoot>
            <tr>
                <th><fmt:message key="table.id"/></th>
                <th><fmt:message key="table.name"/></th>
                <th><fmt:message key="table.surname"/></th>
                <th><fmt:message key="table.role"/></th>
                <th><fmt:message key="table.class"/></th>
            </tr>
            </tfoot>

            <tbody></tbody>

        </table>
        <script>selectFunction()</script>
        <script>highlightTableRows('persons', "hoverRow", "clickedRow")</script>
        <button type="submit" class="button" id="create-team" onclick="createTeam()"
                disabled style="width: 100%"><fmt:message key="button.create-team"/></button>
    </form>
</div>
</html>
