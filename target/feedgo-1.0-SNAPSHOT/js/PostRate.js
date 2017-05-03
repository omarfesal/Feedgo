/* Javascript */
var rating;
var post_id = document.getElementById("post_id").value,
        user_id = document.getElementById("user_id").value;


$(function () {
 
      var $rateYo = $("#rateYo").rateYo();
      $("#rateYo").click(function () {
 
            /* get rating */
            rating = $rateYo.rateYo("rating");
            console.log(rating, post_id, user_id);
            startRequest(rating, user_id, post_id);
            
            $rateYo.css("display","none");
            $("#rate").css("display","none");
            $("#rateNum").css("display","block");
            
      });
 
});


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
function startRequest(value, user_id, post_id)
{
    createXmlHttpRequest();
    xmlHttp.open("GET", "http://localhost:8084/feedgo/Rate_ajax?rate=" + value + "&user_id=" + user_id + "&post_id=" + post_id, true);
    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.send(null);
}
;
function handleStateChange()
{
    if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
    {
        document.getElementById("rateNum").innerHTML = "Users Rate: "+ xmlHttp.responseText;
    } else {

        console.log("saad");
    }
}
;
