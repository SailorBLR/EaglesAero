

$(document).ready(function () {
    qualiSelect();
});

function qualiSelect() {

    setStatus();
    var qual = document.getElementById("current-qualification").value;
    var selects = document.getElementById('qualiField');
    var qualificationHidden = document.getElementById("hidden-qualification");
    for (var i = 0; i < selects.length; i++) {
        if (selects[i].value == qual) {
            selects[i].selected = true;
            qualificationHidden = selects[i].value;
            break;
        }
    }

}

function qualiSelectDefault() {

    var qualificationHidden = document.getElementById("hidden-qualification");
    var spec = document.getElementById("current-role").value;
    var selects = document.getElementById('qualiField');
    if (spec != "pilot") {
        selects.value = 'not specified';
    }

    if(spec=="pilot") {
        if((selects.options[selects.selectedIndex].value=="not specified")
            ||(selects.options[selects.selectedIndex].value=="0")){
            selects.value=document.getElementById("current-qualification").value;
        }
    }
    qualificationHidden.value = selects.options[selects.selectedIndex].value;
}

function setStatus() {
    var qualificationHidden = document.getElementById("hidden-qualification");
    document.getElementById("last-status").value = document.getElementById("status").value;



    if (document.getElementById("status").value == "fired") {
        document.getElementById("dismiss-button").disabled = true;
        $('#dismiss-button').addClass('no-edit-hover');
        document.getElementById("name-id").readOnly = true;
        document.getElementById("surname-id").readOnly = true;
        document.getElementById('qualiField').disabled=true;
        document.getElementById("vocation-button").disabled = true;
        $('#vocation-button').addClass('no-edit-hover');
        qualificationHidden.value=document.getElementById("current-qualification").value;
    }

}

function dismiss() {

    var status = document.getElementById("status");
    document.getElementById("last-status").value = status.value;

    status.value = "fired";

    var inputs = document.getElementsByTagName("input");
    for (var i = 0; i < inputs.length; i++) {
        if (inputs[i] != document.getElementById("adminpassword-id")) {
            inputs[i].readOnly = true;
        }
    }
    document.getElementById('qualiField').disabled = true;
    document.getElementById("dismiss-button").disabled = true;
    $('#dismiss-button').addClass('no-edit-hover');
    document.getElementById("vocation-button").disabled = true;
    $('#vocation-button').addClass('no-edit-hover');

}

function vocation() {
    if(document.getElementById("last-status").value==null){
        document.getElementById("last-status").value=document.getElementById("current-role").value;
    }

    var status = document.getElementById("status");
    document.getElementById("last-status").value = status.value;
    status.value = "vocation";
    document.getElementById("dismiss-button").disabled = true;
    $('#dismiss-button').addClass('no-edit-hover');
    document.getElementById("vocation-button").disabled = true;
    $('#vocation-button').addClass('no-edit-hover');
}

function resetForm() {

    if(document.getElementById("last-status").value==null){
        document.getElementById("last-status").value="free";
    }

    document.getElementById("status").value = "free";
    document.getElementById("name-id").readOnly = false;
    document.getElementById("surname-id").readOnly = false;
    document.getElementById('qualiField').disabled=false;
    document.getElementById("dismiss-button").disabled = false;
    $('#dismiss-button').removeClass('no-edit-hover');
    document.getElementById("vocation-button").disabled = false;
    $('#vocation-button').removeClass('no-edit-hover');
}


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
            flightList[i].getAttribute("data-status")
        ]);
    }
    $('#flights').dataTable({
        "aaData": dataSet

    });
}


function displayEdit(th) {
    document.getElementById("form-container").style.display = 'inline';
    fill();

}
function hideEdit() {
    document.getElementById("form-container").style.display = 'none';
}

