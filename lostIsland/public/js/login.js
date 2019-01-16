$(document).ready(function(){
    //Code if the user clicks log in button
    $("#loginbutton").click(function(){
      var username = $(".username").val();
      console.log(username);
      var password = $(".password").val();
      console.log(password);
      console.log("Estoy en login script");
      //Create the object that we want to pass, which is user
      var user = {
        "username": username,
        "password": password,
      };
      console.log(user);
      $.ajax({  
        headers: { 'content-type': 'application/json',"x-kii-appid": "XXXXX","x-kii-appkey":"XXXXX" },
        type: 'POST',
        url:  "http://147.83.7.205:8080/dsaApp/auth/login",
        data: JSON.stringify(user),
        dataType: 'json',
        success: function(data) {
            console.log("Log in succesfully");
            console.log(data);
        },
        error: function(error){
          if(error.status==402){
            alert("User is already connected in other device. Please log out your account first");
          }
          if(error.status==404){
            alert("User doesn't exist. Your username or password may be wrong");
          }
        }
    }); 
  });
  });