  //Global uri
var BASE_URI = "http://147.83.7.155:8080/dsaApp/";

function myfunction(){
  $.ajax({
    type: 'GET',
    url: BASE_URI.concat("users/stats"),
    dataType: 'json',
    success: function(scoreboard){
      console.log(scoreboard);
      console.log("Length: " +scoreboard.length);
      for(let i=0; i<scoreboard.length; i++)
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