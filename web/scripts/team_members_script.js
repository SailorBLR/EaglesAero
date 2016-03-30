function fill() {

    var teamMembersList = document.getElementById("stuff-list").getElementsByTagName('li');

    var dataSet = [];
    for (var i = 0; i < teamMembersList.length; i++) {
        dataSet.push([
            teamMembersList[i].getAttribute("data-id"),
            teamMembersList[i].getAttribute("data-name"),
            teamMembersList[i].getAttribute("data-surname"),
            teamMembersList[i].getAttribute("data-role"),
            teamMembersList[i].getAttribute("data-qualification"),
            teamMembersList[i].getAttribute("data-dob"),
            teamMembersList[i].getAttribute("data-status")
        ]);
    }
    $('#persons').dataTable({
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
    document.getElementById("id").value = th[0];
    document.getElementById("nameId").value = th[1];
    document.getElementById("surnameId").value = th[2];
    document.getElementById("roleId").value = th[3];
    document.getElementById("classId").value = th[4];
    document.getElementById("dobId").value = th[5];
    document.getElementById("statusId").value = th[6];

}

function hideEdit() {
    document.getElementById("form-container").style.display = 'none';
}


