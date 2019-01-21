  //Global uri
var BASE_URI = "http://147.83.7.155:8080/dsaApp/";

function myfunction(){
  $.ajax({
    type: 'GET',
    url: BASE_URI.concat("objects/allobjects"),
    dataType: 'json',
    success: function(objects){
      console.log(objects);
      console.log("Length: " +objects.length);
      for(let i=0; i<objects.length; i++)
      {
        var plan = document.getElementsByClassName("plan");
        //console.log(plan[i]);
        var nombre = plan[i].getElementsByClassName("nombre");
        //console.log(nombre);
        nombre[0].innerHTML = "<p>" + objects[i].name + "</p>";
        var precio = plan[i].getElementsByClassName("precio");
        var caracteristicas = plan[i].getElementsByClassName("caracteristicas");
        var btn = plan[i].getElementsByClassName("comprar");
        var type = caracteristicas[0].getElementsByClassName("type");
        var points = caracteristicas[0].getElementsByClassName("points");
        //console.log(caracteristicas);
        //console.log(type);
        //console.log(points);
        btn[0].innerHTML= '<a src="shop.html" type="button" id="buy_btn6" class="btn" value="Click" onclick=comprar('+objects[i].id+')>BUY</a>';
        precio[0].innerHTML = "<p>" + objects[i].cost + "€</p>";
        type[0].innerHTML = objects[i].type;
        points[0].innerHTML = objects[i].objectPoints;
      }
    }
  });
  $.ajax({
    type: 'GET',
    url: BASE_URI.concat("users/" + window.localStorage.getItem("user_id") + "/attributes"),
    dataType: 'json',
    success: function(attributes){
      console.log(attributes.points);
      var money = document.getElementsByClassName("dinero");
      money[0].innerHTML = "<h2>Money:" + attributes.points + "€</h2>";
    },
    error: function(error){
      if(error.status==404){
        alert("User doesn't exist.");
      }
    }
  });

}

function comprar(p1){
  $.ajax({  
    headers: { 'content-type': 'application/json',"x-kii-appid": "XXXXX","x-kii-appkey":"XXXXX" },
    type: 'POST',
    url:  BASE_URI.concat("users/" + window.localStorage.getItem("user_id") + "/buyobject/"+ p1),
    data: JSON.stringify(""),
    dataType: 'json',
    success: function(attributes){
      console.log("dentro");
      window.open("http://147.83.7.155:8080/html/shop.html", "_self");
    },
    error: function(error){
      if(error.status==402){
        alert("You don't have enough points to buy this object");
      }
      if(error.status==403){
        alert("Game object doesn't exist");
      }
      if(error.status==404){
        alert("User doesn't exist. Your username or password may be wrong");
      }
      if(error.status==405){
        alert("The object that you want to add is already in use");
      }
    }
  });
}