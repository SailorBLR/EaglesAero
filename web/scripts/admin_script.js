$(document).ready(function () {
    var flightList = document.getElementById("users-list").getElementsByTagName('li');

    var dataSet = [];
    for (var i = 0; i < flightList.length; i++) {
        dataSet.push([
            flightList[i].getAttribute("data-id"),
            flightList[i].getAttribute("data-login"),
            flightList[i].getAttribute("data-role"),
            flightList[i].getAttribute("data-name"),
            flightList[i].getAttribute("data-surname")


        ]);
    }


    $('#users').dataTable({
        "aaData": dataSet

    });
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
    document.getElementById("user-login").value = th[1];
    document.getElementById("user-name").value = th[3];
    document.getElementById("user-surname").value = th[4];
    document.getElementById("user-role").value = th[2];
}

function hideEdit() {
    document.getElementById("form-container").style.display = 'none';
}

function deleteUser() {

    var deleteName = (document.getElementById("user-name").value).concat(" ")
        .concat(document.getElementById("user-surname").value);
    var deleteId = document.getElementById("id-value").value;
   var button = location.href = 'controller?command=deleteconfirmation&id='.concat(deleteId)
       .concat('&user=').concat(deleteName);

    return button;
}