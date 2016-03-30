<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Calendar" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources.pagecontent"/>

<%
    Calendar cal = Calendar.getInstance();
    cal.add(cal.DAY_OF_MONTH, 2);
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
%>

<html>
<head>
    <title><fmt:message key="label.new-flight"/></title>
    <script type="text/javascript" src="../../scripts/flight_script.js"></script>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script type="text/javascript" src="../../scripts/common/date.js"></script>
</head>
<div class="page-info" style="width: 100%; text-align: center">
    <h2><fmt:message key="label.flight-creating"/> </h2>
</div>

<div id="select-planes">
    <select id="planes-select" style="display: none">
        <c:forEach var="plane" items="${planes}" varStatus="status">
            <option value="${plane.planeId}" data-passengers="${plane.passengers}"
                    data-speed="${plane.speed}" data-range="${plane.range}"
                    data-location="${plane.location}" data-pilot="${plane.pilot}"
                    data-technic="${plane.technic}" data-stuart="${plane.stuart}"
                    data-free-from="${plane.freeFrom.getTimeInMillis()}">
                    ${plane.planeName}
            </option>
        </c:forEach>
    </select>
</div>


<div id="flightcreatingform">
    <form id="formFlightId" name="newflight" method="post" action="controller">
        <input type="hidden" name="command" value="createflight"/>

        <div class="inline">

            <h2 style="font-family: Arial; text-align: center"><fmt:message key="label.direct-flight"/></h2>

            <div class="input-holder">
                <span class="label"><fmt:message key="label.flight-from"/></span>
                    <span><select class="input-field" id="citiesFrom" name="citiesFrom" size="1"
                                  onChange="updatecities(this,'<fmt:message key='label.default'/>')"
                                  onclick="clearform()" style="width: 200px" required>
                        <option value="DEFAULT" selected><fmt:message key="label.default"/></option>
                        <c:forEach var="cityD" items="${cities}" varStatus="status">
                            <option value="${cityD.cityCode}" data-northL="${cityD.northLatitude}"
                                    data-eastL="${cityD.eastLongitude}">${cityD.cityName}</option>
                        </c:forEach>
                    </select></span>
            </div>

            <div class="input-holder">
                <span class="label"><fmt:message key="label.flight-to"/></span>
                    <span><select class="input-field" id="citiesTo" name="citiesTo"
                                  onchange="updatedist(this,'<fmt:message key='label.default'/>')"
                                  size="0" style="width: 200px" disabled required>
                    </select></span>
            </div>

            <div class="input-holder">
                <c:set var="defaultDate" value="<%=df.format(cal.getTime())%>"/>
                <span class="label"><fmt:message key="label.departure-date"/></span>
                    <span><input class="input-field" id="datePickerDate" type="date" name="date" min="${defaultDate}"
                                 value="${defaultDate}" onchange="enabledate('datePickerTime')" required></span>
            </div>

            <div class="input-holder">
                <span class="label"><fmt:message key="label.departure-time"/></span>
                    <span><input class="input-field" id="datePickerTime" type="time" name="timeDepart"
                                 onchange="createPlanesList('<fmt:message key='label.default'/>')" disabled
                                 required></span>
            </div>

            <div class="input-holder">
                <span class="label"><fmt:message key="label.planes-list"/></span>
                    <span><select class="input-field" id="planesId" name="planes" onchange="updateArrivingDate(this)"
                                  size="0" style="width: 200px" disabled required>
                    </select></span>
            </div>

            <div class="input-holder">
                <span class="label"><fmt:message key="label.arriving-time"/></span>
                <span><input class="input-field" id="arriveTime" type="text" name="timeArrive" readonly></span>
            </div>
            <div class="input-holder">
                <span class="label"><fmt:message key="label.flight-time"/></span>
                <span><input class="input-field" id="flTime" type="text" name="flightTime" readonly></span>
            </div>

            <div class="input-holder">
                <span class="label"><fmt:message key="label.flight-distance"/></span>
                <span><input class="input-field" id="dist" type="text" name="flightDistance" readonly/></span>
            </div>

        </div>
        <div class="inline" <%--id="back-flight"--%>>
            <h2 style="text-align: center"><fmt:message key="label.back-flight"/></h2>

            <div class="input-holder">
                <span class="label"><fmt:message key="label.flight-from"/></span>
                <span><input class="input-field" id="backDepartureCity"
                             type="text" name="backCityFrom" value="" readonly></span>
            </div>
            <div class="input-holder">
                <span class="label"><fmt:message key="label.flight-to"/></span>
                <input class="input-field" id="backArrivingCity" type="text" name="backCityTo" value="" readonly>
            </div>
            <div class="input-holder">
                <span class="label"><fmt:message key="text.plane"/> </span>
                <input class="input-field" id="backPlane" type="text" name="backPlane" value="" readonly>
            </div>
            <div class="input-holder">
                <span class="label"> <fmt:message key="label.departure-date"/></span>
                <input class="input-field" id="backDepartureDate" type="date" name="backDepDate"
                       onchange="enableTime()" disabled required>
            </div>

            <div class="input-holder">
                <span class="label"><fmt:message key="label.departure-time"/></span>
                <input class="input-field" id="backDepartureTime" type="time" name="backTimeDepart"
                       onchange="calculateArrivingTime(this)" disabled required>
            </div>

            <div class="input-holder">
                <span class="label"><fmt:message key="label.arriving-time"/></span>
                <input class="input-field" id="backArrive" type="text" name="timeBackArrive" readonly>

            </div>
        </div>
        <div id="buttons-hbox" class="buttons-field">
                <span><button type="submit" id="create-button" class="button" onclick="submitFlightsForm()"
                              disabled><fmt:message key="button.create"/></button></span>
                <span><button type="button" class="button"
                              onClick="history.go(-1);return true;"><fmt:message key="button.back"/></button></span>
        </div>
    </form>
</div>

</html>
