//Global uri
var BASE_URI = "http://147.83.7.155:8080/dsaApp/";

//Calls when the document is ready
$(document).ready(function(){
    //Code if the user clicks log in button
    $("#loginbutton").click(function(){
      var username = $(".username").val();
      console.log(username);
      var password_old = $(".password_old").val();
      console.log(password);
      var password_new = $(".password_new").val();
      console.log("Estoy en login script");
      //Create the object that we want to pass, which is user
      var user = {
        "username": username,
        "oldpassword": password_old,
        "newpassword": password_new,
      };
      console.log(user);
      $.ajax({  
        headers: { 'content-type': 'application/json',"x-kii-appid": "XXXXX","x-kii-appkey":"XXXXX" },
        type: 'PUT',
        url:  BASE_URI.concat("auth/newcredentials"),
        data: JSON.stringify(user),
        dataType: 'json',
        success: function(data) {
            console.log("Log in succesfully");
            console.log(data);
            var url = "http://147.83.7.155:8080/";
            window.open(url, "_self");
        },
        error: function(error){
          if(error.status==404){
            alert("User doesn't exist. Your username or password may be wrong");
          }
        }
    }); 
  });
});