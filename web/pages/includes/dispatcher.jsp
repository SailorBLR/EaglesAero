<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources.pagecontent"/>

<html>
<head>
    <title><fmt:message key="label.dispatcher-page"/></title>
    <script type="text/javascript" charset="utf8" src="../../scripts/common/jquery-1.12.1.min.js"></script>
    <script type="text/javascript" charset="utf8" src="../../scripts/common/jquery.dataTables.js"></script>
    <script type="text/javascript" src="../../scripts/common/hltable.js"></script>
    <script type="text/javascript" src="../../scripts/flight_table_script.js"></script>

    <script>
        var statusTrue = "<fmt:message key='text.team-formed'/>";
        var statusFalse = "<fmt:message key='text.team-status'/>";

    </script>

</head>

<div id="flight-data" style="display: none" >
    <ul id="flight-list">
        <c:forEach var="flight" items="${flights}">
            <li id="inner-li" data-id="${flight.flightId}"
                data-departurePoint="${flight.flightFrom}" data-arrivingPoint="${flight.flightTo}"
                data-depTime="${flight.getFormattedDate(flight.departureTime)}"
                data-arrTime="${flight.getFormattedDate(flight.arrivingTime)}"
                data-status="${flight.status}" data-plane="${flight.plane}"
                data-distance="${flight.flightDistance}"
                data-time="${flight.flightTime}" data-teamStatus="${flight.flightTeam}"
                data-direction="${flight.direction}">
            </li>
        </c:forEach>
    </ul>
</div>

<div class="page-info" style="width: 100%; text-align: center">
    <h2><fmt:message key="label.dispatcher"/> </h2>
</div>
<div id="tableflightsForm">
    <form name="tableflightsForm" method="GET" action="controller">
        <input type="hidden" name="command" value="createflightform"/>

        <div id="tableflights">
            <table id="flights">
                <thead>
                <tr>
                    <th><fmt:message key="table.flight-id"/></th>
                    <th><fmt:message key="table.departure-point"/></th>
                    <th><fmt:message key="table.arriving-point"/></th>
                    <th><fmt:message key="table.departure-time"/></th>
                    <th><fmt:message key="table.arriving-time"/></th>
                    <th><fmt:message key="table.plane"/></th>
                    <th><fmt:message key="table.status"/></th>
                    <th><fmt:message key="table.flight-team-status"/></th>
                    <th><fmt:message key="table.direction"/></th>
                </tr>
                </thead>
                <tfoot>
                <tr>
                    <th><fmt:message key="table.flight-id"/></th>
                    <th><fmt:message key="table.departure-point"/></th>
                    <th><fmt:message key="table.arriving-point"/></th>
                    <th><fmt:message key="table.departure-time"/></th>
                    <th><fmt:message key="table.arriving-time"/></th>
                    <th><fmt:message key="table.plane"/></th>
                    <th><fmt:message key="table.status"/></th>
                    <th><fmt:message key="table.flight-team-status"/></th>
                    <th><fmt:message key="table.direction"/></th>
                </tr>
                </tfoot>
                <tbody></tbody>
            </table>
            <script type="text/javascript">
                fill();
                selectFunction();
                highlightTableRows("flights", "hoverRow", "clickedRow");
                highliteText();
            </script>
        </div>
        <hr/>
        <div id="buttons-hbox">
            <div id="add-flight-button">
                <button class="button" type="submit" value=""
                        name="add-new"><fmt:message key="button.add-new-flight"/></button>
            </div>
            <div id="refresh">
                <button class="button" id="refresh-button" type="button" onclick="refresh()">
                    <fmt:message key="button.refresh"/></button>
            </div>
        </div>
    </form>

</div>
<div id="error-panel">
    <h4 style="color: red">${errorMessage}</h4>
</div>
<div class="popup-form-container" id="form-container" onload="manageDirection()">
    <div class="popup-content">
        <div class="flight-summary">
            <div class="page-info" style="width: 100%; text-align: center">
                <h2><fmt:message key="label.flight-summary"/> </h2>
            </div>
            <form name="flightdetails" method="GET" action="controller">
                <input type="hidden" name="command" value="createteamform">
                <input id="back-id" type="text" name="backFlight" style="display: none">
                <input id="direct-id" type="text" name="directFlight" style="display: none">
                <input id="team-status" type="text" name="teamStatus" style="display: none">

                <p id="info-direction" style="display: none; font-size: 14px" class="error">
                    <fmt:message key="text.info-direction"/>
                </p>

                <p id="flight-number">
                    <fmt:message key="text.flight-number"/>
                    <input type="text" id="id-value" name="idvalue" class="input-field" readonly>
                </p>

                <p id="fromTo">
                    <fmt:message key="text.flight-from"/>
                    <input style="margin-right: 10%" type="text" id="flight-from" name="flightfrom" class="input-field" readonly>
                    <span class="input-field"><fmt:message key="text.to"/></span>
                    <input style="margin-right: 0" type="text" id="flight-to" name="flightto" class="input-field" readonly>
                </p>

                <p id="terms">
                    <fmt:message key="text.departure-date"/>
                    <input type="text" id="departure" name="departure" class="input-field" readonly>.
                    <span class="input-field"><fmt:message key="text.arriving"/></span>
                    <input type="text" id="arriving" name="arriving" class="input-field" readonly>.
                </p>

                <p id="plane-model">
                    <fmt:message key="text.plane"/>
                    <input type="text" id="plane" name="plane" class="input-field" readonly>
                </p>

                <div class="navi-bar">
                    <span style="margin-top: 5px"> <button class="button" type="button" onclick="hideEdit()">
                        <fmt:message key="button.back"/></button></span>
                    <span style="margin-top: 5px"><input id="create-button" type="submit" class="button"
                                 value="<fmt:message key="button.create-flight-team"/>"/></span>
                    <span style="margin-top: 5px"><input id="edit-team" class="button"   type="button" onclick="editTeam()"
                                 value="<fmt:message key="button.edit-team"/>"/></span>
                    <span style="margin-top: 5px"><input id="edit-button" class="button" type="button" onclick="getEdit()"
                                 value="<fmt:message key="button.edit-flight"/>"/></span>
                </div>
            </form>
        </div>
    </div>
</div>


</html>

