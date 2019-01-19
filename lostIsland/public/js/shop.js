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
      document.getElementById("");
      for(let i=0; i<objects.length; i++)
      {
        $("tbody").append(`
          <tr>
            <td>${scoreboard[i].username}</td>
            <td>${scoreboard[i].points}</td>
            <td>${scoreboard[i].enemiesKilled}</td>
            <td>${scoreboard[i].level}</td>
          </tr>
        `);
      }
    }
  });
}