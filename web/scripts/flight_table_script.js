

function fill() {

    var flightList = document.getElementById("flight-list").getElementsByTagName('li');

    var dataSet = [];
    for (var i = 0; i < flightList.length; i++) {
        dataSet.push([
            flightList[i].getAttribute("data-id"),
            flightList[i].getAttribute("data-departurePoint"),
            flightList[i].getAttribute("data-arrivingPoint"),
            flightList[i].getAttribute("data-depTime"),
            flightList[i].getAttribute("data-arrTime"),
            flightList[i].getAttribute("data-plane"),
            flightList[i].getAttribute("data-status"),
            defineTeamStatus(flightList[i].getAttribute("data-teamStatus")),
            flightList[i].getAttribute("data-direction")
        ]);
    }
    $('#flights').dataTable({
        "aaData": dataSet

    });

}


function selectFunction() {

    var table = document.getElementsByTagName("table")[0];
    var tbody = table.getElementsByTagName("tbody")[0];
    tbody.onclick = function (e) {
        e = e || window.event;
        var data = [];
        var target = e.srcElement || e.target;
        while (target && target.nodeName !== "TR") {
            target = target.parentNode;
        }
        if (target) {
            var cells = target.getElementsByTagName("td");
            for (var i = 0; i < cells.length; i++) {

                data.push(cells[i].innerHTML);

            }
        }
        displayEdit(data);
    };
}

function displayEdit(th) {

    document.getElementById("form-container").style.display = 'inline';


    document.getElementById("id-value").value = th[0];
    document.getElementById("flight-from").value = th[1];
    document.getElementById("flight-to").value = th[2];
    document.getElementById("departure").value = th[3];
    document.getElementById("arriving").value = th[4];
    document.getElementById("plane").value = th[5];
    document.getElementById("team-status").value = th[7];
    if (statusFalse==th[7]) {
        document.getElementById("edit-team").disabled = true;
    } else {
        document.getElementById("create-button").disabled = true;
    }
    if (th[6] != "future") {
        $('#create-button').addClass("no-hover");
        document.getElementById("create-button").disabled = true;
        $('#edit-button').addClass("no-hover");
        document.getElementById("edit-button").disabled = true;
        $('#edit-team').addClass("no-hover");
        document.getElementById("edit-team").disabled = true;
    }
    if (th[8] == "back") {
        $('#create-button').addClass("no-hover");
        document.getElementById("create-button").disabled = true;
        $('#edit-team').addClass("no-hover");
        document.getElementById("edit-team").disabled = true;
        document.getElementById("info-direction").style.display = 'inline';
        document.getElementById("back-id").value = th[0];
        document.getElementById("direct-id").value = formBackId(th[0]);
    } else {
        document.getElementById("back-id").value = formBackId(th[0]);
        document.getElementById("direct-id").value = th[0];
    }
}

function hideEdit() {

    $('#create-button').removeClass("no-hover");
    $('#edit-button').removeClass("no-hover");
    $('#edit-team').removeClass("no-hover");
    document.getElementById("create-button").disabled = false;
    document.getElementById("edit-button").disabled = false;
    document.getElementById("edit-team").disabled = false;
    document.getElementById("form-container").style.display = 'none';
    document.getElementById("info-direction").style.display = 'none';
}

function getEdit() {


    var button = location.href = 'controller?command=editflightform&directFlight='
        .concat(document.getElementById("direct-id").value).concat('&backFlight=')
        .concat(document.getElementById("back-id").value).concat('&teamStatus=')
        .concat(document.getElementById("team-status").value);
    return button;
}

function defineTeamStatus(status) {
    var refactored;
    if (status == "true") {
        refactored = statusTrue;
    } else {
        refactored = statusFalse;
    }
    return refactored;
}

function formBackId(direct) {
    var backId = "";
    var length = direct.length;

    backId = backId.concat(direct.substring((length - 2), (length)));
    backId = backId.concat(direct.substring(2, (length - 2)));
    backId = backId.concat(direct.substring(0, 2));
    return backId;
}

function editTeam() {
    var button = location.href = 'controller?command=editteamform&directFlight='
        .concat(document.getElementById("direct-id").value).concat('&backFlight=')
        .concat(document.getElementById("back-id").value).concat('&teamStatus=')
        .concat(document.getElementById("team-status").value);
    return button;
}

function refresh() {
    var button = location.href = 'controller?command=refreshflight';

    return button;
}

function highliteText () {
    var tBody = document.getElementsByTagName("tbody").item(0);
    var rows = tBody.getElementsByTagName("tr");
    var cell;
    for (var i = 0; i < rows.length; i++) {
        cell=rows[i].getElementsByTagName("td").item(6).innerHTML;
        switch (cell) {
            case "future" :
                rows[i].getElementsByTagName("td").item(6).style.color = "green";
                rows[i].getElementsByTagName("td").item(6).style.fontWeight = "900";
                break;
            case "landed" :
                rows[i].getElementsByTagName("td").item(6).style.color ="yellow";
                rows[i].getElementsByTagName("td").item(6).style.fontWeight = "900";
                break;
            case "cancelled" :
                rows[i].getElementsByTagName("td").item(6).style.color = "red";
                rows[i].getElementsByTagName("td").item(6).style.fontWeight = "900";
                break;
            case "in progress" :
                rows[i].getElementsByTagName("td").item(6).style.color = "blue";
                rows[i].getElementsByTagName("td").item(6).style.fontWeight = "900";
                break;
        }
    }
}
