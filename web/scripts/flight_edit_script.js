
function checkDate() {

    document.getElementById("new-date").disabled = false;
    var date = new Date(document.getElementById("direct-new-date").value);
    var dateMillisMin = date.getTime() + 86400000;
    var dateMillisMax = date.getTime() + 172800000;
    var dateNew = new Date(dateMillisMin);
    var dateFormatted = dateNew.toString("yyyy-MM-dd");
    document.getElementById("new-date").setAttribute("min", dateFormatted);
    dateNew = new Date(dateMillisMax);
    dateFormatted = dateNew.toString("yyyy-MM-dd");
    document.getElementById("new-date").setAttribute("max", dateFormatted);

    var departure = new Date(document.getElementById("direct-new-date").value.concat(" ")
        .concat(document.getElementById("direct-new-time").value));
    var timeDep = departure.getTime();
    var millisResult = departure.getTime() + flightTime;
    var arriving = new Date(millisResult);
    var out = arriving.toString("yyyy-MM-dd HH:mm");

    document.getElementById("direct-new-arr-date").value = out.toString("yyyy-MM-dd HH:mm");
    document.getElementById("directArrivingId").value = out.toString("yyyy-MM-dd HH:mm");
    document.getElementById("directDepartId").value = departure.toString("yyyy-MM-dd HH:mm");

}

function enableTimeDirect() {

    document.getElementById("direct-new-time").disabled = false;

}

function calculateMaxValue() {
    var date = new Date(document.getElementById("direct-old-dep-date").value);
    var dateMillis = date.getTime() + 86400000;
    var dateNew = new Date(dateMillis);
    var dateFormatted = dateNew.toString("yyyy-MM-dd");
    document.getElementById("direct-new-date").setAttribute("max", dateFormatted);

}

function enableTimeBack() {

    document.getElementById("new-time").disabled = false;

}

function calculateFlightTime(dateDeparture, dateArriving) {
    var departure = new Date(dateDeparture);
    var arriving = new Date(dateArriving);

    var diffMs = (arriving - departure); // milliseconds between now & Christmas
    var diffHrs = Math.floor((diffMs % 86400000) / 3600000); // hours
    var diffMins = Math.round(((diffMs % 86400000) % 3600000) / 60000); // minutes
    var flightTime = String(diffHrs).concat(":").concat(String(diffMins));

    return diffMs;
}

function calculateNewArrivingDate() {
    var newDate = document.getElementById("new-date").value;
    var newTime = document.getElementById("new-time").value;

    var newDateTimeString = newDate.concat(" ").concat(newTime);
    document.getElementById("backDepartId").value = newDateTimeString;
    var newDateTime = new Date(newDateTimeString);
    var arrivingInMs = newDateTime.getTime() + flightTime;
    var arrivingDate = new Date(arrivingInMs);
    var dateFormatted = arrivingDate.toString("yyyy-MM-dd HH:mm");
    document.getElementById("back-new-arr-date").value = dateFormatted;
    document.getElementById("backArrivingId").value = dateFormatted;
    document.getElementById("submit-btn").disabled = false;
    $('#submit-btn').removeClass('no-hover');
}

function submitForm() {

    var button = location.href = 'controller?command=editflight&directDeparture='
        .concat(document.getElementById("directDepartId").value).concat('&directArriving=')
        .concat(document.getElementById("directArrivingId").value).concat('&backDeparture=')
        .concat(document.getElementById("backDepartId").value).concat('&backArriving=')
        .concat(document.getElementById("backArrivingId").value).concat('&directFlightId=')
        .concat(document.getElementById("direct-flight-Id").value).concat('&backFlightId=')
        .concat(document.getElementById("back-flight-id").value);
    return button;
}

function cancelFlight() {
    var test = prompt(cancelMessage, '');
    if(validatePassword(test)){
        document.getElementById("direct-Id").value = document.getElementById("direct-flight-Id").value;
        document.getElementById("back-Id").value = document.getElementById("back-flight-id").value;
        document.getElementById("pass").value = test;

        document.forms["new-data-form"].submit();
    } else {
        alert(cancelErrorMessage)
    }
}

function validatePassword(password) {

    var usernameRegex = /^[a-zA-Z0-9]{4,10}$/;
    var validPassword = password.match(usernameRegex);
    if (validPassword == null) {

        return false;
    }
    return true;
}

