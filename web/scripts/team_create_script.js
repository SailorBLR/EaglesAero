$(document).ready(function () {
    $('#persons').dataTable({});
    var create = $('#add-button');
    create.addClass('no-hover');
    var clear = $('#clear-button');
    clear.addClass('no-hover');
    var createTeam = $('#create-team');
    createTeam.addClass('no-hover');

    createTeamInputs(pilots, stuarts, technics);
});
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
            for (var i = 0; i < cells.length - 1; i++) {
                data.push(cells[i].innerHTML);
            }
        }
    };
}

function insRow(countPilots, countTechnics, countStuarts) {
    document.getElementById("clear-table").disabled = false;
    var clear = $('#clear-button');
    clear.removeClass('no-hover');

    var table = $('#persons').DataTable();

    var staffId = document.getElementById("id-value").value;
    var name = document.getElementById("name").value;
    var surname = document.getElementById("surname").value;
    var role = document.getElementById("role").value;
    var qualification = document.getElementById("qualification").value;

    var dataSetRow = [];
    dataSetRow.push(staffId);
    dataSetRow.push(name);
    dataSetRow.push(surname);
    dataSetRow.push(role);
    dataSetRow.push(qualification);
    deleteSelected();

    var rowNode = table
        .row.add(dataSetRow)
        .draw()
        .node();


    var limitTechnics = parseInt(countPilots)  + parseInt(countTechnics);
    var limitStuarts = parseInt(countPilots) + parseInt(countTechnics) + parseInt(countStuarts);

    var rowCount = table.data().length;

    if (rowCount == countPilots) {
        document.getElementById("pilotSelect").disabled = true;
        document.getElementById("technicSelect").disabled = false;
    }

    if (rowCount == limitTechnics) {
        document.getElementById("technicSelect").disabled = true;
        document.getElementById("stuartSelect").disabled = false;

    }
    if (rowCount == limitStuarts) {
        document.getElementById("stuartSelect").disabled = true;
        document.getElementById("add-button").disabled = true;
        var add = $('#add-button');
        add.addClass('no-hover');

        document.getElementById("create-team").disabled = false;
        var create = $('#create-team');
        create.removeClass('no-hover');
    }
    document.getElementById("add-button").disabled = true;
    var addButton = $('#add-button');
    addButton.addClass('no-hover');
}

function createTeamInputs() {
    var targetPilots = document.getElementById("pilots-sp");
    var targetTechnics = document.getElementById("technic-sp");
    var targetStuarts = document.getElementById("stuart-sp");
    var teamMembersList = document.getElementById("stuff-list").getElementsByTagName('li');

    var objects = [];

    for (var j = 0; j < teamMembersList.length; j++) {
        objects[j] = {
            id: teamMembersList[j].getAttribute("data-id"),
            name: teamMembersList[j].getAttribute("data-name"),
            surname: teamMembersList[j].getAttribute("data-surname"),
            role: teamMembersList[j].getAttribute("data-role"),
            qualification: teamMembersList[j].getAttribute("data-qualification"),
            dob: teamMembersList[j].getAttribute("data-dob")
        };
    }


    var selpilot = "pilotSelect";
    $("<select></select>", {id: selpilot}).appendTo(targetPilots);
    var selectPilots = $('#pilotSelect');
    selectPilots.addClass("input-field");

    fillPilotsSelect(objects, selpilot);
    document.getElementById(selpilot).onchange = function () {
        showDetails(this);
    };
    document.getElementById(selpilot).name = "pilot-select";

    var seltechnic = "technicSelect";
    $("<select></select>", {id: seltechnic}).appendTo(targetTechnics);
    var selectTechnics = $('#technicSelect');
    selectTechnics.addClass("input-field");

    fillTechnicSelect(objects, seltechnic);
    document.getElementById(seltechnic).disabled = true;
    document.getElementById(seltechnic).onchange = function () {
        showDetails(this);
    };
    document.getElementById(seltechnic).name = "technic-select";

    var selstuarts = "stuartSelect";
    $("<select></select>", {id: selstuarts}).appendTo(targetStuarts);
    var selectStuart = $('#stuartSelect');
    selectStuart.addClass("input-field");

    fillStuartSelect(objects, selstuarts);
    document.getElementById(selstuarts).disabled = true;
    document.getElementById(selstuarts).onchange = function () {
        showDetails(this);
    };
    document.getElementById(selstuarts).name = "stuart-select";
}


function fillPilotsSelect(objects, selectID) {

    var targetSelect = document.getElementById(selectID);
    var opt = document.createElement("option");
    opt.innerHTML = "DEFAULT";
    opt.value = "default";
    targetSelect.appendChild(opt);
    for (var i = 0; i < objects.length; i++) {
        if (objects[i].role == "pilot") {
            opt = document.createElement("option");
            opt.innerHTML = objects[i].name + " " + objects[i].surname + ". "
                + " " + objects[i].qualification + " class.";
            opt.value = objects[i].id;
            opt.setAttribute("data-name", objects[i].name);
            opt.setAttribute("data-surname", objects[i].surname);
            opt.setAttribute("data-role", objects[i].role);
            opt.setAttribute("data-qualification", objects[i].qualification);

            targetSelect.appendChild(opt);
        }
    }

}

function fillTechnicSelect(objects, selectID) {
    var targetSelect = document.getElementById(selectID);
    var opt = document.createElement("option");
    opt.innerHTML = "DEFAULT";
    opt.value = "default";
    targetSelect.appendChild(opt);
    for (var i = 0; i < objects.length; i++) {
        if (objects[i].role == "technic") {
            opt = document.createElement("option");
            opt.innerHTML = objects[i].name + " " + objects[i].surname + ". "
                + " " + objects[i].qualification + " class.";
            opt.value = objects[i].id;

            opt.setAttribute("data-name", objects[i].name);
            opt.setAttribute("data-surname", objects[i].surname);
            opt.setAttribute("data-role", objects[i].role);
            opt.setAttribute("data-qualification", objects[i].qualification);

            targetSelect.appendChild(opt);
        }
    }
}

function fillStuartSelect(objects, selectID) {
    var targetSelect = document.getElementById(selectID);

    var opt = document.createElement("option");
    opt.innerHTML = "DEFAULT";
    opt.value = "default";
    targetSelect.appendChild(opt);
    for (var i = 0; i < objects.length; i++) {
        if (objects[i].role == "stuart") {
            opt = document.createElement("option");
            opt.innerHTML = objects[i].name + " " + objects[i].surname + ". "
                + " " + objects[i].qualification + " class.";
            opt.value = objects[i].id;
            opt.setAttribute("data-name", objects[i].name);
            opt.setAttribute("data-surname", objects[i].surname);
            opt.setAttribute("data-role", objects[i].role);
            opt.setAttribute("data-qualification", objects[i].qualification);


            targetSelect.appendChild(opt);
        }
    }
}

function showDetails(select) {

    document.getElementById("id-value").value = select.options[select.selectedIndex].value;
    document.getElementById("name").value = select.options[select.selectedIndex].getAttribute("data-name");
    document.getElementById("surname").value = select.options[select.selectedIndex].getAttribute("data-surname");
    document.getElementById("role").value = select.options[select.selectedIndex].getAttribute("data-role");
    document.getElementById("qualification").value = select.options[select.selectedIndex]
        .getAttribute("data-qualification");
    var add = $('#add-button');
    add.removeClass('no-hover');
    add.disabled = false;
    document.getElementById("add-button").disabled = false;
}

function clearForm() {

    var selects = document.getElementsByTagName("select");
    for (index = selects.length - 1; index >= 0; index--) {
        selects[index].parentNode.removeChild(selects[index]);
    }
    var table = $('#persons').DataTable();
    table
        .clear()
        .draw();


    document.getElementById("add-button").disabled = true;
    var add = $('#add-button');
    add.addClass('no-hover');
    document.getElementById("clear-table").disabled = true;
    var clear = $('#clear-table');
    clear.addClass('no-hover');
    document.getElementById("create-team").disabled = true;
    var create = $('#create-team');
    create.addClass('no-hover');
    document.getElementById("id-value").value = null;
    document.getElementById("name").value = null;
    document.getElementById("surname").value = null;
    document.getElementById("role").value = null;
    document.getElementById("qualification").value = null;
    createTeamInputs();
}

function deleteSelected() {

    var selPilots = document.getElementById('pilotSelect');
    var selTechnics = document.getElementById('technicSelect');
    var selStuarts = document.getElementById('stuartSelect');
    if ((!selPilots.disabled) && (selPilots.options[selPilots.selectedIndex].value != "default")) {
        $('#pilotSelect :selected').remove();
    }
    if ((!selTechnics.disabled) && (selTechnics.options[selTechnics.selectedIndex].value != "default")) {
        $('#technicSelect :selected').remove();
    }
    if ((!selStuarts.disabled) && (selStuarts.options[selStuarts.selectedIndex].value != "default")) {
        $('#stuartSelect :selected').remove();
    }

}

function createTeam() {
    var tBody = document.getElementsByTagName("tbody").item(0);
    var rows = tBody.getElementsByTagName("tr");
    var teamMembersId = "";
    var separator = ",";
    for (var i = 0; i < rows.length; i++) {
        teamMembersId = teamMembersId.concat(rows[i].firstChild.innerHTML).concat(separator);
    }

    document.getElementById("listId").value = teamMembersId;
    document.getElementById("flightId").value = document.getElementById("id-flight-value").value;
    document.getElementById("backflightId").value = document.getElementById("id-backflight-value").value;

}

