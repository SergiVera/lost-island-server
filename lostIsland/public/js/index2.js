//Global uri
var BASE_URI = "http://147.83.7.155:8080/dsaApp/";

//Calls when the document is ready
$(document).ready(function(){
    //Code if the user clicks log out button
  $("#logout_btn").click(function(){
    var user_id = window.localStorage.getItem("user_id");    
    console.log(user_id);
    $.ajax({  
      headers: { 'content-type': 'application/json',"x-kii-appid": "XXXXX","x-kii-appkey":"XXXXX" },
      type: 'PUT',
      url:  BASE_URI.concat("auth/logout"),
      data: JSON.stringify(user_id),
      dataType: 'json',
      success: function(data) {
          console.log("Log in succesfully");
          window.open("http://147.83.7.155:8080","_self");
      },
      error: function(error){
        if(error.status==404){
          alert("User doesn't exist. Your username or password may be wrong");
        }
      }
    });
  });
});