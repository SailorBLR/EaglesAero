/**
 * Created by Anton on 03.03.2016.
 */


function submitForm() {
    clearErrorForms();
    if (validationLoginPassed()) {
        $('form#loginform').submit();


    }
}

function submitEditForm() {
    clearErrorForms();
    if (validationEditPassed()) {

        if (confirm(message)) {
            $('form#edit').submit();
        }
    }
}

function submitCreate() {
    clearErrorForms();
    if (validationCreatePassed()) {

        $('form#createForm').submit();
    }
}


function submitDelete() {
    clearErrorForms();
    if (validatePassword()){
        $('form#confirmform').submit();
    } else {
        document.getElementById("error-panel-password").innerHTML = passwordError;
    }

}


function validationCreatePassed() {
    if (!validationLoginPassed()) {
        return false;
    }
    if (!validateName()) {
        document.getElementById("error-panel-name").innerHTML = nameError;
        return false;
    }
    if (!validateSurname()) {
        document.getElementById("error-panel-surname").innerHTML = nameError;
        return false;
    }
    return true;
}

function validationLoginPassed() {
    if (!validateLogin()) {
        document.getElementById("error-panel-login").innerHTML = loginError;
        return false;
    }
    if (!validatePassword()) {
        document.getElementById("error-panel-password").innerHTML = passwordError;
        return false;
    }
    return true;

}

function validationEditPassed() {
    if (!validationLoginPassed()) {
        return false;
    }
    if (!validateName()) {
        document.getElementById("error-panel-name").innerHTML = nameError;
        return false;
    }
    if (!validateSurname()) {
        document.getElementById("error-panel-surname").innerHTML = nameError;
        return false;
    }

    if (!validateAdminPassword()) {
        document.getElementById("error-panel-admin-password").innerHTML = passwordError;
        return false;
    }

    return true;

}


function validateLogin() {
    var element = document.getElementById("login-input");
    var usernameRegex = /^[a-zA-Z0-9]{4,10}$/;
    var validfirstUsername = element.value.match(usernameRegex);
    if (validfirstUsername == null) {
        element.focus();
        return false;
    }
    return true;
}

function validatePassword() {
    var element = document.getElementById("password-input");
    var usernameRegex = /^[a-zA-Z0-9]{4,10}$/;
    var validPassword = element.value.match(usernameRegex);
    if (validPassword == null) {
        element.focus();
        return false;
    }
    return true;
}

function validateName() {
    var element = document.getElementById("name-input");
    var usernameRegex = /^[A-ZА-ЯЁ]{1}[a-zа-яё]{2,15}$/;
    var validName = element.value.match(usernameRegex);
    if (validName == null) {
        element.focus();
        return false;
    }
    return true;
}

function validateSurname() {
    var element = document.getElementById("surname-input");
    var usernameRegex = /^[A-ZА-ЯЁ]{1}[a-zа-яё]{2,15}$/;
    var validName = element.value.match(usernameRegex);
    if (validName == null) {
        element.focus();
        return false;
    }
    return true;
}

function validateAdminPassword() {
    var element = document.getElementById("adminPassword-input");
    var usernameRegex = /^[a-zA-Z0-9]{4,10}$/;
    var validName = element.value.match(usernameRegex);
    if (validName == null) {
        element.focus();
        return false;
    }
    return true;
}


function clearErrorForms() {
    var errorFields = document.getElementsByClassName("error");
    for (var i = 0; i < errorFields.length; i++) {
        errorFields[i].innerHTML = null;
    }
}