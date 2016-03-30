/**
 * Created by Anton on 19.02.2016.
 */
$(document).ready(function () {
    var create = $('#create-button');
    create.addClass('no-hover');
    create.disabled = true;


});


function check(elem) {
    document.getElementById('citiesTo').disabled = !elem.selectedIndex;
}


function updatecities(city, defaultOption) {

    check(city);
    clearCitiesTo(defaultOption);

    var citiesList = document.getElementById("citiesFrom");
    var citiesTo = document.getElementById("citiesTo");

    var objects = [];
    var counter = 0;
    for (var i = 0, n = citiesList.options.length; i < n; i++) {

        if (citiesList.options[i].text &&
            (citiesList.options[i].text != citiesList.options[citiesList.selectedIndex].text) &&
            citiesList.options[i].text != defaultOption) {

            objects[counter] = {
                cityName: citiesList.options[i].innerHTML,
                cityCode: citiesList.options[i].value,
                northLat: citiesList.options[i].getAttribute("data-northL"),
                eastLon: citiesList.options[i].getAttribute("data-eastL")
            };
            counter++;
        }
    }

    for (i = 0; i < objects.length; i++) {
        var opt = document.createElement("option");
        opt.innerHTML = objects[i].cityName;
        opt.value = objects[i].cityCode;
        opt.setAttribute("data-northL", objects[i].northLat);
        opt.setAttribute("data-eastL", objects[i].eastLon);
        citiesTo.appendChild(opt);
    }

    document.getElementById("backDepartureCity").value = citiesList.options[citiesList.selectedIndex].innerHTML;

}
function clearCitiesTo(defaultOption) {
    $('#citiesTo')
        .find('option')
        .remove()
        .end()
        .append('<option value="whatever">' + defaultOption + '</option>')
        .val('whatever')
    ;
}

function updateArrivingDate(select) {

    document.getElementById("backPlane").value = select.options[select.selectedIndex].innerHTML;

    var depDate = new Date(document.getElementById("datePickerDate").value +
        " " + document.getElementById("datePickerTime").value).getTime();

    var calcFlightTime = parseInt(document.getElementById("dist").value) /
        parseInt(select.options[select.selectedIndex].getAttribute("data-speed"));

    calcFlightTime = parseFloat(calcFlightTime).toFixed(2);

    var flightHours = Math.floor(calcFlightTime);
    var flightMinutes = parseInt((calcFlightTime - Math.floor(calcFlightTime)) * 60);

    if (flightMinutes < 10)
        flightMinutes = '0' + flightMinutes;

    document.getElementById("flTime").value = flightHours + ":" + flightMinutes;


    var decimal = (parseFloat(calcFlightTime) - Math.floor(parseFloat(calcFlightTime))) * 60 * 60000;
    var solid = Math.floor(parseFloat(calcFlightTime)) * 60 * 60 * 1000;
    var arrTime = parseInt(depDate) + parseInt(solid) + parseInt(decimal);
    var arrDate = new Date(arrTime);
    var inputDate = document.getElementById("arriveTime").value = formatDate(arrDate);
    inputDate.disabled = false;

}

function updatedist(cityTo, defaultOption) {

    document.getElementById("backArrivingCity").value = cityTo.options[cityTo.selectedIndex].innerHTML;

    if (cityTo.options[cityTo.selectedIndex].innerHTML != defaultOption) {
        var cityFrom = document.getElementById("citiesFrom");
        var cityFromSelect = cityFrom.options[cityFrom.selectedIndex];
        var northLFrom = parseFloat(cityFromSelect.getAttribute("data-northL")) * Math.PI / 180;
        var eastLFrom = parseFloat(cityFromSelect.getAttribute("data-eastL")) * Math.PI / 180;
        var northLTo = parseFloat(cityTo.options[cityTo.selectedIndex].getAttribute("data-northL")) * Math.PI / 180;
        var eastLTo = parseFloat(cityTo.options[cityTo.selectedIndex].getAttribute("data-eastL")) * Math.PI / 180;
        var cos = Math.sin(northLFrom) * Math.sin(northLTo) + (Math.cos(northLFrom) * Math.cos(northLTo) *
            Math.cos(eastLFrom - eastLTo));
        var dist = Math.acos(Math.abs(cos)) * 6371;

        document.getElementById("dist").value = parseInt(dist);
    }
}

function deleteplanes() {

    $('#planesId')
        .find('option')
        .remove()
        .end()
        .val('whatever')
    ;
}


function clearform() {
    deleteplanes();
    document.getElementById('dist').value = "";

}

function enabledate(element) {
    document.getElementById(element).disabled = false;
    document.getElementById("backDepartureDate").disabled = false;

    document.getElementById("backDepartureDate").setAttribute("min", formatBackDeparture());

}

function createPlanesList(defaultOption) {
    var depCity = document.getElementById("citiesFrom");
    var cityCode = depCity.options[depCity.selectedIndex].value;
    var strDate = document.getElementById("datePickerDate").value;
    var date = new Date(strDate).getTime();
    var planesList = document.getElementById("planes-select");

    document.getElementById("planesId").disabled = false;

    var planes = [];
    var counter = 0;

    for (var i = 0, n = planesList.options.length; i < n; i++) {
        if (planesList.options[i].text && (parseInt(planesList.options[i].getAttribute("data-range")) >
            parseInt(document.getElementById('dist').value))
            && (date > planesList.options[i].getAttribute("data-free-from")) &&
            (cityCode == planesList.options[i].getAttribute("data-location"))) {


            planes[counter] = {
                plane: planesList.options[i].innerHTML,
                planeId: planesList.options[i].value,
                passengers: planesList.options[i].getAttribute("data-passengers"),
                speed: planesList.options[i].getAttribute("data-speed"),
                range: planesList.options[i].getAttribute("data-range"),
                location: planesList.options[i].getAttribute("data-location"),
                pilot: planesList.options[i].getAttribute("data-pilot"),
                technic: planesList.options[i].getAttribute("data-technic"),
                stuart: planesList.options[i].getAttribute("data-stuart"),
                freeFrom: planesList.options[i].getAttribute("data-free-from")
            };


            counter++;
        }
    }
    deleteplanes();
    var selectElement = document.getElementById("planesId");
    var opt = document.createElement("option");
    opt.innerHTML = defaultOption;
    opt.value = defaultOption;
    selectElement.appendChild(opt);

    for (var i = 0; i < planes.length; i++) {
        opt = document.createElement("option");
        opt.innerHTML = planes[i].plane;
        opt.value = planes[i].planeId;
        opt.setAttribute("data-passengers", planes[i].passengers);
        opt.setAttribute("data-speed", planes[i].speed);
        opt.setAttribute("data-range", planes[i].range);
        opt.setAttribute("data-location", planes[i].location);
        opt.setAttribute("data-pilot", planes[i].pilot);
        opt.setAttribute("data-technic", planes[i].technic);
        opt.setAttribute("data-stuart", planes[i].stuart);
        opt.setAttribute("data-free-from", planes[i].freeFrom);
        selectElement.appendChild(opt);

    }
}

function formatDate(dateToFormat) {
    var dateValue = new Date(dateToFormat);
    var monthValue = dateValue.getMonth() + 1;
    var dayValue = dateValue.getDate();
    var yearValue = dateValue.getFullYear();
    var hoursValue = dateValue.getHours();
    var minutesValue = dateValue.getMinutes();

    if (monthValue < 10)
        monthValue = '0' + monthValue;
    if (dayValue < 10)
        dayValue = '0' + dayValue;
    if (hoursValue < 10)
        hoursValue = '0' + hoursValue;
    if (minutesValue < 10)
        minutesValue = '0' + minutesValue;

    return (yearValue + '-' + monthValue + '-' + dayValue + ' ' + hoursValue + ':' + minutesValue);
}


function formatBackDeparture() {
    var date = new Date(document.getElementById("datePickerDate").value);
    var dateMillis = date.getTime() + 86400000;
    var dateNew = new Date(dateMillis);
    var dateFormatted = dateNew.toString("yyyy-MM-dd");

    return dateFormatted;

}

function enableTime() {
    document.getElementById("backDepartureTime").disabled = false;
}

function calculateArrivingTime(time) {

    var depTime = convertTime(document.getElementById("flTime").value);
    var depDate = new Date(document.getElementById("backDepartureDate").value +
        " " + document.getElementById("backDepartureTime").value).getTime();


    var arrTime = parseInt(depDate) + parseInt(depTime);
    var arrDate = new Date(arrTime);
    var inputDate = document.getElementById("backArrive").value = formatDate(arrDate);
    inputDate.disabled = false;
    var create = $('#create-button');
    create.removeClass('no-hover');
    document.getElementById("create-button").disabled = false;
    create.disabled = false;


}

function convertTime(time) {

    var a = time.split(':');
    var milliseconds = (+a[0]) * 60 * 60 * 1000 + (+a[1]) * 60 * 1000;

    return milliseconds;
}

function submitFlightsForm() {
    var form = document.getElementById("formFlightId");
    form.submit();
}
