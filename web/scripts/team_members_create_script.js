
$(document).ready(function () {
    qualiSelect()
});

function qualiSelect (){
    var selectQualification = document.getElementById("quali");
    var selectRole = document.getElementById("role-Select");

    if(selectRole.options[selectRole.selectedIndex].value!="pilot"){
        selectQualification.value = "not specified";
        selectQualification.disabled = true;
    } else {
        selectQualification.disabled = false;
        selectQualification.value = "3";
    }
}

function pilotQuali() {
    var selectQualification = document.getElementById("quali");
    var selectRole = document.getElementById("role-Select");
    if(selectRole.options[selectRole.selectedIndex].value=="pilot"){
        if(selectQualification.value=="not specified") {
            selectQualification.value="3";
        }
    }
}
