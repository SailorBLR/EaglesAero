<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources.pagecontent"/>

<html>
<head>
    <title><fmt:message key="label.hr"/></title>

    <script type="text/javascript" charset="utf8" src="../../scripts/common/jquery-1.12.1.min.js"></script>
    <script type="text/javascript" charset="utf8" src="../../scripts/common/jquery.dataTables.js"></script>
    <script type="text/javascript" charset="utf8" src="../../scripts/team_members_script.js"></script>
    <script type="text/javascript" charset="utf8" src="../../scripts/common/hltable.js"></script>

</head>

<div id="table-data" style="display: none">
    <ul id="stuff-list">
        <c:forEach var="teammembers" items="${flightcrew}">
            <li id="inner-li" data-id="${teammembers.personId}"
                data-name="${teammembers.name}" data-surname="${teammembers.surname}"
                data-role="${teammembers.role}" data-qualification="${teammembers.qualification}"
                data-dob="${teammembers.getBirthDay()}" data-status="${teammembers.status}">
            </li>
        </c:forEach>
    </ul>
</div>

<div class="page-info" style="width: 100%; text-align: center">
    <h2><fmt:message key="label.hr"/> </h2>
</div>

<div id="tablePersons" class="hover">

    <form name="tablePersonalForm" method="GET" action="controller">
        <input type="hidden" name="command" value="createform"/>
        <table id="persons">
            <thead>
            <tr>
                <th><fmt:message key="table.id"/></th>
                <th><fmt:message key="table.name"/></th>
                <th><fmt:message key="table.surname"/></th>
                <th><fmt:message key="table.occupation"/></th>
                <th><fmt:message key="table.class"/></th>
                <th><fmt:message key="table.date-of-birth"/></th>
                <th><fmt:message key="table.person-status"/></th>
            </tr>
            </thead>
            <tfoot>
            <tr>
                <th><fmt:message key="table.id"/></th>
                <th><fmt:message key="table.name"/></th>
                <th><fmt:message key="table.surname"/></th>
                <th><fmt:message key="table.occupation"/></th>
                <th><fmt:message key="table.class"/></th>
                <th><fmt:message key="table.date-of-birth"/></th>
                <th><fmt:message key="table.person-status"/></th>
            </tr>
            </tfoot>
            <tbody></tbody>

        </table>

        <script type="text/javascript">
            fill();
            selectFunction();
            highlightTableRows("persons", "hoverRow", "clickedRow")
        </script>

        <div id="error-panel">
            <br/>
            <h4 style="color: red">${personErrorMessage}</h4>
            <br/>
        </div>
        <div id="create-new-person">
            <input type="submit" class="button" value="<fmt:message key="button.add-new-person"/>"/>
        </div>
    </form>
</div>


<div class="popup-form-container" id="form-container" style="display: none">
    <div class="person-content">
        <div class="team-member-summary">
            <form name="team-mamber-details" method="GET" action="controller">
                <input type="hidden" id="id" name="personId">
                <input type="hidden" name="command" value="editpersonform">

                <h2 style="text-align: center"><fmt:message key="label.team-member-summary"/></h2>


                <div class="input-holder">
                    <span class="label"><fmt:message key="label.name"/></span>
                    <span><input class="input-field" type="text" id="nameId" name="name" readonly></span>
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.surname"/></span>
                    <span><input class="input-field" type="text" id="surnameId" name="surname" readonly></span>
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.role"/></span>
                    <span><input class="input-field" type="text" id="roleId" name="role" readonly></span>
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.qualification"/></span>
                    <span><input class="input-field" type="text" id="classId" name="class" readonly></span>
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.surname"/></span>
                    <span><input class="input-field" type="text" id="dobId" name="dob" readonly></span>
                </div>

                <div class="input-holder">
                    <span class="label"><fmt:message key="label.status"/></span>
                    <span><input class="input-field" type="text" id="statusId" name="status" readonly></span>
                </div>


                <div class="navi-bar">
               <span> <button class="button" type="button" onclick="hideEdit()">
                   <fmt:message key="button.back"/></button></span>
                    <span><input type="submit" class="button"
                                 value="<fmt:message key="button.edit"/>"/></span>
                </div>
            </form>
        </div>
    </div>
</div>
</html>