<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources.pagecontent"/>

<html>
<head>
    <title><fmt:message key="label.new-team-member"/></title>
    <script type="text/javascript" charset="utf8" src="../../scripts/team_members_create_script.js"></script>
</head>

<div class="page-info" style="width: 100%; text-align: center">
    <h2><fmt:message key="label.new-team-member"/> </h2>
</div>

<div id="person-creating-form">
    <form name="new-person" method="POST" action="controller">
        <input type="hidden" name="command" value="createperson"/>

        <div id="input-zone">

            <div id="input-name" class="input-holder">
                <span class="label">*<fmt:message key="label.name"/></span>
                <input class="input-field" type="text" name="name" value="" required/>
            </div>
            <div id="input-surname" class="input-holder">
                <span class="label">*<fmt:message key="label.surname"/></span>
                <input class="input-field" type="text" name="surname" value="" required>
            </div>
            <div id="input-city" class="input-holder">
                <span class="label">*<fmt:message key="label.city"/> </span>
                <span><select name="city" class="input-field" required>
                    <c:forEach var="cities" items="${cities}">
                       <option value="${cities.cityCode}">${cities.cityName}</option>
                    </c:forEach>
                </select>
                </span>
            </div>
            <div id="roleSelect" class="input-holder">
                <span class="label">*<fmt:message key="label.role"/></span>

                <span><select id="role-Select" name="role" class="input-field" onchange="qualiSelect()" required>

                    <option value="pilot"><fmt:message key="text.pilot"/> </option>
                    <option value="stuart" selected><fmt:message key="text.stuart"/> </option>
                    <option value="technic"><fmt:message key="text.technic"/> </option>

                </select></span>
            </div>
            <div id="qualification select-bar" class="input-holder">
                <span class="label">*<fmt:message key="label.qualification"/></span>

                <span><select id="quali" class="input-field" name="qualification" onchange="pilotQuali()" required>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="not specified"><fmt:message key="text.not-specified"/> </option>
                </select></span>
            </div>

            <div id="calendar" class="input-holder">
                <span class="label">*<fmt:message key="label.birthday"/></span>
                <span><input class="input-field" type="date" name="date-of-birth"
                             max="1990-01-01" min="1940-01-01" required></span>
            </div>
            <div id="buttons-hbox">
                    <input type="submit" class="editButton" value="<fmt:message key="button.create"/>"/>

                    <input type="button" class="editButton" value="<fmt:message key="button.back"/>"
                                 onClick="history.go(-1);return true;">
            </div>
        </div>
    </form>
</div>

</html>
