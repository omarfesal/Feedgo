
//declare varibles
function edit(x) {
    var edit = document.getElementById("edit" + x);
    var input = document.getElementById("input" + x);
    var save = document.getElementById("save" + x);
    $(input).prop('disabled', false);
    $(input).css("background", "white");
    $(input).val("");
    $(input).focus();
    $(save).css("display", "inline");
    $(edit).css("display", "none");
}
;

function save(x) {
    var err = document.getElementById("error");
    var edit = document.getElementById("edit" + x);
    var input = document.getElementById("input" + x);
    var save = document.getElementById("save" + x);
    if (input.value === "") {
        $(err).css("display", "block");
        err.innerHTML = "you must fill input";
    } else if (input.name === "email" && !validateEmail(input.value)) {
        $(err).css("display", "block");
        err.innerHTML = "email is is not right";
    }
    else {
        $(err).css("display", "none");
        $(input).prop('disabled', true);
        $(save).css("display", "none");
        $(edit).css("display", "inline");
        $(input).css("background", "#ccc");
        var name = input.name;
        var val = input.value;
        console.log(name + val);
        startRequest(name, val);
    }
}
;

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}


var xmlHttp;// global instance of XMLHttpRequest
function createXmlHttpRequest()
{
    if (window.ActiveXObject)
    {
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    else if (window.XMLHttpRequest)
    {
        xmlHttp = new XMLHttpRequest();
    }

}
;
function startRequest(name, value)
{
    createXmlHttpRequest();
    xmlHttp.open("GET", "http://localhost:8084/feedgo/Setting_ajax?" + name + "=" + value, true);
    xmlHttp.onreadystatechange = handleStateChange(name);
    xmlHttp.send(null);

}
;
function handleStateChange(name)
{
    if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
    {
        if (name === "fname")
            document.getElementById("input1").value = xmlHttp.responseText;
        else if (name === "lname")
            document.getElementById("input2").value = xmlHttp.responseText;
        else if (name === "email")
            document.getElementById("input3").value = xmlHttp.responseText;

    } else {

        console.log(name);
    }
}
;
