<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.get('locale')}" scope="session"/>
<fmt:setBundle basename="resources.pagecontent"/>

<html>
<head>
    <script src="../../scripts/header_script.js"></script>
    <script>
        var locale = "${sessionScope.get('locale')}";
    </script>
</head>
<div id="head" class="header">
    <div class="fltlft">
        <ctg:hello>${sessionScope.get('login')}</ctg:hello>
    </div>
    <div id="logout-area" class="logout">
        <a href="controller?command=logout"><fmt:message key="label.logout"/></a>
        <br/>
    </div>
    <div id="info-area" class="company-info">
        <h3><fmt:message key="text.eagles-aero"/> </h3>
    </div>
    <div id="select-bar" class="fltrt">
        <form id="languageForm" name="languagePanel" method="get" action="controller"
              onchange="submitLanguage(this)">
            <input type="hidden" name="command" value="locale"/>
            <input type="hidden" name="include" value="${include}">

            <div id="language-select">
                <select id="locale-select" name="new-locale" style="float: right">
                    <option value="en_US"><fmt:message key="label.language-en"/></option>
                    <option value="ru_RU"><fmt:message key="label.language-ru"/></option>

                </select>
            </div>
        </form>
    </div>


</div>
</html>

