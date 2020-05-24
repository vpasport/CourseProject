$(document).ready(function () {
    console.log(1)
    $.post( "http://localhost:8080/messages/kek2", {
        message : {
            fromId : 2,
            toId : 3,
            message : "sfsdfsdf"
        }
    } )
});