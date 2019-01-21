  //Global uri
var BASE_URI = "http://147.83.7.155:8080/dsaApp/";

function myfunction(){
  $.ajax({
    type: 'GET',
    url: BASE_URI.concat("users/" + window.localStorage.getItem("user_id") + "/attributes"),
    dataType: 'json',
    success: function(attributes){
      console.log(attributes);
      var points = document.getElementsByClassName("points");
      points[0].innerHTML = "<p>Points:" + attributes.points + "</p>";
      var killed = document.getElementsByClassName("killed");
      killed[0].innerHTML = "<p>Enemies Killed:" + attributes.enemiesKilled + "</p>";
      var level = document.getElementsByClassName("level");
      level[0].innerHTML = "<p>Level:" + attributes.level + "</p>";
      var money = document.getElementsByClassName("dinero");
      money[0].innerHTML = "<h2>Money:" + attributes.points + "€</h2>";
    }
  });
  $.ajax({
    type: 'GET',
    url: BASE_URI.concat("users/" + window.localStorage.getItem("user_id") + "/objects"),
    dataType: 'json',
    success: function(objects){
      console.log(objects);
      console.log("Length: " +objects.length);
      for(let i=0; i<9 || i<objects.length; i++)
      {
        var plan = document.getElementsByClassName("plan");
        //console.log(plan[i]);
        var nombre = plan[i].getElementsByClassName("nombre");
        //console.log(nombre);
        nombre[0].innerHTML = "<p>" + objects[i].name + "</p>";
        var precio = plan[i].getElementsByClassName("precio");
        var caracteristicas = plan[i].getElementsByClassName("caracteristicas");
        var imagen = plan[i].getElementsByClassName("imagen");
        var btn = plan[i].getElementsByClassName("vender");
        var type = caracteristicas[0].getElementsByClassName("type");
        var points = caracteristicas[0].getElementsByClassName("points");
        //console.log(caracteristicas);
        //console.log(type);
        //console.log(points);
        console.log(btn.onclick);
        console.log(objects[i].id);
        btn[0].innerHTML= '<a src="shop.html" type="button" class="btn" id="buy_btn6" value="Click" onclick=vender('+objects[i].id+')>SELL</a>';
        imagen[0].src = '../resources/'+ objects[i].name +'.png';
        precio[0].innerHTML = "<p>" + objects[i].cost + "€</p>";
        type[0].innerHTML = objects[i].type;
        points[0].innerHTML = objects[i].objectPoints;
      }
    }
  });
}

function vender(p1){
  $.ajax({ 
    type: 'DELETE',
    url:  BASE_URI.concat("users/" + window.localStorage.getItem("user_id") + "/sellobject/"+ p1),
    dataType: 'json',
    success: function(attributes){
      console.log("dentro");
      window.open("http://147.83.7.155:8080/html/perfil.html", "_self");
    },
    error: function(error){
      if(error.status==403){
        alert("Game object doesn't exist");
      }
      if(error.status==404){
        alert("User doesn't exist. Your username or password may be wrong");
      }
    }
  });
  myfunction();
}