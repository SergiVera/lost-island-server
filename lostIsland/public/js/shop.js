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
      for(let i=0; i<3; i++)
      {
        document.getElementById('nombre[i]').value = objects[i].name;
      }
    }
  });
}