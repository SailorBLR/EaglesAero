$(document).ready(function () {
    var select = document.getElementById("locale-select");
    if ("en_US"==locale) {
        $("#locale-select").val("en_US");
    } else {
        $("#locale-select").val("ru_RU");
    }
});

function submitLanguage(form) {

    form.submit()
}
