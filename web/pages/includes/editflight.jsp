<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources.pagecontent"/>

<html>
<head>
    <title><fmt:message key="label.flight-edit-form"/></title>
    <script type="text/javascript" src="../../scripts/flight_edit_script.js"></script>
    <script type="text/javascript" src="../../scripts/common/date.js"></script>
    <c:set var="directFlight" value="${directFlight}" scope="page"/>
    <script>
        var flightTime = calculateFlightTime
        ("${directFlight.getFormattedDate(directFlight.departureTime)}",
                "${directFlight.getFormattedDate(directFlight.arrivingTime)}");
        var cancelErrorMessage = "<fmt:message key="error.password-error"/>";
        var cancelMessage = "<fmt:message key="text.cancel-message"/> "
    </script>
</head>
<div class="page-info" style="width: 100%; text-align: center">
    <h2><fmt:message key="label.flight-edit"/> </h2>
</div>
<div id="flights-content">
    <div id="separate-info">
        <div id="direct-flight">
            <h2 style="text-align: center"><fmt:message key="label.direct-flight"/></h2>

            <form>
                <c:set var="directFlight" value="${directFlight}" scope="page"/>
                <div class="input-holder">
                    <span class="label"><fmt:message key="label.flightId"/></span>
                    <span><input class="input-field" type="text" id="direct-flight-Id" name="dirId"
                                 value="${directFlight.flightId}" readonly></span>
                </div>
                <div class="input-holder">
                    <span class="label"><fmt:message key="label.flight-from"/></span>
                    <span><input class="input-field" type="text" id="depPointId" name="depPoint"
                                 value="${directFlight.flightFrom}" readonly></span>
                </div>
                <div class="input-holder">
                    <span class="label"><fmt:message key="label.flight-to"/></span>
                    <span><input class="input-field" type="text" id="arrPointId" name="arrPoint"
                                 value="${directFlight.flightTo}" readonly></span>
                </div>
                <div class="input-holder">
                    <span class="label"><fmt:message key="label.departure-date"/></span>
                    <span><input class="input-field" type="text" id="direct-old-dep-date"
                                 value="${directFlight.getDate(directFlight.departureTime)}" readonly></span>
                </div>
                <div class="input-holder">
                    <span class="label"><fmt:message key="label.new-date"/> </span>
                    <span><input class="input-field" type="date" id="direct-new-date" name="newDateDirect"
                                 min="${directFlight.getDate(directFlight.departureTime)}"
                                 onchange="enableTimeDirect()"></span>
                    <script>
                        calculateMaxValue();
                    </script>
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.departure-time"/> </span>
                    <span><input class="input-field" type="text" id="direct-old-dep-time"
                                 value="${directFlight.getFormattedTime(directFlight.departureTime)}" readonly></span>
                </div>
                <div class="input-holder">
                    <span class="label"><fmt:message key="label.new-time"/> </span>
                    <span><input class="input-field" type="time" id="direct-new-time" name="newTimeDirect"
                                 onchange="checkDate()" disabled></span>
                </div>


                <div class="input-holder">
                    <span class="label"><fmt:message key="label.arriving-time"/></span>
                    <span><input class="input-field" type="text" id="direct-old-arr-date"
                                 value="${directFlight.getFormattedDate(directFlight.arrivingTime)}" readonly></span>
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.new-arriving-time"/></span>
                    <span><input class="input-field" type="text" id="direct-new-arr-date"
                                 value="" readonly></span>
                </div>
            </form>

        </div>
        <div id="back-flight">
            <h2 style="text-align: center"><fmt:message key="label.back-flight"/></h2>

            <form>
                <c:set var="backFlight" value="${backFlight}"/>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.flightId"/></span>
                    <span><input class="input-field" type="text" id="back-flight-id" name="dirId"
                                 value="${backFlight.flightId}" readonly></span>
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.flight-from"/></span>
                    <span><input class="input-field" type="text" id="back-depPointId"
                                 value="${backFlight.flightFrom}" readonly></span>
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.flight-to"/></span>
                    <span><input class="input-field" type="text" id="back-arrPointId"
                                 value="${backFlight.flightTo}" readonly></span>
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.departure-date"/></span>
                    <span><input class="input-field" type="text" id="old-dep-date"
                                 value="${backFlight.getDate(backFlight.departureTime)}" readonly></span>
                </div>
                <div class="input-holder">
                    <span class="label"><fmt:message key="label.new-date"/> </span>
                    <span><input class="input-field" type="date" id="new-date" name="newDate"
                                 onchange="enableTimeBack()" disabled></span>
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.departure-time"/> </span>
                    <span><input class="input-field" type="text" id="old-dep-time"
                                 value="${backFlight.getFormattedTime(backFlight.departureTime)}" readonly></span>
                </div>
                <div class="input-holder">
                    <span class="label"><fmt:message key="label.new-time"/> </span>
                    <span><input class="input-field" type="time" id="new-time" name="newTime"
                                 onchange="calculateNewArrivingDate()" disabled></span>
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.arriving-time"/></span>
                    <span><input class="input-field" type="text" id="old-arr-date"
                                 value="${backFlight.getFormattedDate(backFlight.arrivingTime)}" readonly></span>
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.new-arriving-time"/></span>
                    <span><input class="input-field" type="text" id="back-new-arr-date"
                                 value="" readonly></span>
                </div>

            </form>
        </div>
    </div>
    <div id="navigation">
        <form id="edit-form" name="new-data-form" method="post" action="controller">
            <input type="hidden" name="command" value="cancelflight">
            <input type="hidden" id="directDepartId" name="directDeparture" value="">
            <input type="hidden" id="directArrivingId" name="directArriving" value="">
            <input type="hidden" id="backDepartId" name="backDeparture" value="">
            <input type="hidden" id="backArrivingId" name="backArriving" value="">
            <input type="hidden" id="direct-Id" name="directId">
            <input type="hidden" id="back-Id" name="backId">
            <input type="hidden" id="pass" name="password">
            <input type="hidden" id="team-status" name="teamStatus" value="${teamStatus}">

            <div class="btn-group">
                <button type="button" id="submit-btn" class="button" onclick="submitForm()"
                        disabled><fmt:message key="button.save-changes"/></button>
                <button type="button" class="button"
                        onClick="history.go(-1);return true;"><fmt:message key="button.back"/></button>
                <button type="button" class="button"
                        onClick="window.location.reload()"><fmt:message key='button.clear'/></button>
                <input type="button" class="button" onclick="cancelFlight()"
                       value="<fmt:message key="button.cancel-flight"/>"/>
            </div>
            <script>
                var create = $('#submit-btn');
                create.addClass('no-hover');
                create.disabled = true;
            </script>
        </form>


    </div>

</div>

</html>
