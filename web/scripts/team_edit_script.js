$(document).ready(function () {
    var teamMembersList = document.getElementById("persons-list").getElementsByTagName('li');
    var dataSet = [];


    for (var i = 0; i < teamMembersList.length; i++) {
        dataSet.push([
            teamMembersList[i].getAttribute("data-id"),
            teamMembersList[i].getAttribute("data-name"),
            teamMembersList[i].getAttribute("data-surname"),
            teamMembersList[i].getAttribute("data-role"),
            teamMembersList[i].getAttribute("data-qualification")
        ]);
    }
    if (dataSet.length == 0) {

        $('#persons').dataTable({});

    } else {
        $('#persons').dataTable({
            "aaData": dataSetTest
        });
    }
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
        displayEdit(data);
    };
}
function displayEdit(th) {
    var create = $('#add-person');
    create.addClass('no-hover');
    create.disabled = true;
    document.getElementById("form-container").style.display = 'inline';

    var select = document.getElementById("team-member");
    $('#team-member')
        .find('option')
        .remove()
        .end()
        .val('whatever')
    ;

    var opt = document.createElement("option");

    opt.innerHTML = defaultOption;
    opt.value = "default";
    select.appendChild(opt);
    document.getElementById("personToReplaceId").value = th[0];
    var speciality = th[3];
    var name = th[1];

    var personSet = formSet(speciality, name);
    for (var i = 0; i < personSet.length; i++) {
        opt = document.createElement("option");
        select.appendChild(opt);
        opt.value = personSet[i].id;
        opt.setAttribute("data-role", personSet[i].role);
        opt.setAttribute("data-name", personSet[i].name);
        opt.setAttribute("data-surname", personSet[i].surname);
        opt.setAttribute("data-qualification", personSet[i].qualification);
        opt.innerHTML = (personSet[i].name + " " + personSet[i].surname + ". "
        + personSet[i].role + " " + personSet[i].qualification + " class.");
    }
}

function hideEdit() {
    document.getElementById("form-container").style.display = 'none';
    document.getElementById("id-value").value = "";
    document.getElementById("name").value = "";
    document.getElementById("surname").value = "";
    document.getElementById("role").value = "";
    document.getElementById("qualification").value = "";

}

function formSet(role, name) {
    var set = [];
    var count = 0;
    for (var i = 0; i < availableTest.length; i++) {
        if (availableTest[i][3] == role && availableTest[i][1]!== name) {

            set[count] = {
                id: availableTest[i][0],
                name: availableTest[i][1],
                surname: availableTest[i][2],
                role: availableTest[i][3],
                qualification: availableTest[i][4]
            };
            count++;
        }
    }
    return set;
}

function showDetails() {
    var select = document.getElementById("team-member");
    var selectedItem = select.options[select.selectedIndex];
    document.getElementById("id-value").value = selectedItem.value;
    document.getElementById("name").value = selectedItem.getAttribute("data-name");
    document.getElementById("surname").value = selectedItem.getAttribute("data-surname");
    document.getElementById("role").value = selectedItem.getAttribute("data-role");
    document.getElementById("qualification").value = selectedItem.getAttribute("data-qualification");
    document.getElementById("add-person").disabled = false;
    var create = $('#add-person');
    create.removeClass('no-hover');

}

function insertRow() {
    var staffId = document.getElementById("id-value").value;
    var name = document.getElementById("name").value;
    var surname = document.getElementById("surname").value;
    var role = document.getElementById("role").value;
    var qualification = document.getElementById("qualification").value;

    dataSetTest.push([
        staffId,
        name,
        surname,
        role,
        qualification
    ]);
    deleteRow(document.getElementById("personToReplaceId").value);

    $('#persons').DataTable({
        "aaData": dataSetTest
    });
    hideEdit();
}

function deleteRow(deleteId) {
    var table = $('#persons').DataTable();
    table
        .clear()
        .draw();

    table.destroy();

    for (var i = 0; i < dataSetTest.length; i++) {

        if (dataSetTest[i][0] == deleteId) {
            availableTest.push ([
                dataSetTest[i][0],
                dataSetTest[i][1],
                dataSetTest[i][2],
                dataSetTest[i][3],
                dataSetTest[i][4]
            ]);
            dataSetTest.splice(i,1);
            break;
        }
    }
}

function editTeam() {
    var tBody = document.getElementsByTagName("tbody").item(0);
    var rows = tBody.getElementsByTagName("tr");
    var teamMembersId = "";
    var separator = ",";
    for (var i = 0; i < rows.length; i++) {
        teamMembersId = teamMembersId.concat(rows[i].firstChild.innerHTML).concat(separator);
    }

    document.getElementById("stuff-list").value = teamMembersId;
}