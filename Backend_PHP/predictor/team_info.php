<?php
	
	include "db.inc.php";
	$team_id=urldecode($_GET['id']);
	$sql="select t.*, (select count(*) from user_details where fav_club_id like t.team_id) as fans from teams as t where t.team_id = '$team_id'";
	$query=mysqli_query($connection,$sql);
	$resultGen=mysqli_fetch_array($query);
	$rankQuery="CREATE TEMPORARY TABLE `t` ( SELECT (@row:=@row+1) AS rank,u.* FROM team_stats as u, (select @row:=0) as t2 )";
				$resultRankQuery=mysqli_query($connection,$rankQuery);
				$getQuery="SELECT * FROM `t` where team_id LIKE '$team_id'";
				$resultRankQuery=mysqli_query($connection,$getQuery);
				$result = mysqli_fetch_array($resultRankQuery);
	echo "<div id =\"teaminfo\">";
	echo "<div id =\"generalinfo\" align=\"left\" style=\"display:inline-block\">";
	
	echo "<div class=\"heading\">GENERAL INFO</div><br><br>";
	echo "<b>Team Name:</b>&emsp;".$resultGen['team_name'];
	echo "<br>";
	
	echo "<b>Rank:</b>&emsp;".$result['rank']."<br>";
	echo "<b>Stadium:</b>&emsp;".$resultGen['stadium']."<br>";
	echo "<b>Number of PLPREDICTOR Fans:</b>&emsp;".$resultGen['fans']."<br>";
	echo "<br>";
	

echo "</div>";
echo "<img src=\"icon_pack/".$team_id.".jpg\" align=\"center\" style=\"float:right\" border=\"2\">";
	
	echo "<table>";

	echo "<tr style = \" background-color : rgb(0,0,0)\"><th style = \"font-size:150%\">STATS</th><th>Home</th><th>Away</th><th>Overall</th></tr>";
	echo "<tr><th>Played</th><td>".$result['home_matches']."</td><td>".$result['away_matches']."</td><td>".$result['matches']."</td></tr>";
	echo "<tr><th>Wins</th><td>".$result['wins_home']."</td><td>".$result['wins_away']."</td><td>".$result['wins']."</td></tr>";
	echo "<tr><th>Losses</th><td>".$result['loss_home']."</td><td>".$result['loss_away']."</td><td>".$result['losses']."</td></tr>";
	echo "<tr><th>Draws</th><td>".$result['draws_home']."</td><td>".$result['draws_away']."</td><td>".$result['draws']."</td></tr>";
	
	echo "<tr><th>Goals For</th><td>".$result['gf_h']."</td><td>".$result['gf_a']."</td><td>".$result['gf']."</td></tr>";
	echo "<tr><th>Goals Against</th><td>".$result['ga_h']."</td><td>".$result['ga_a']."</td><td>".$result['ga']."</td></tr>";
	echo "<tr><th>Goal Difference</th><td>".($result['gf_h']-$result['ga_h'])."</td><td>".($result['gf_a']-$result['ga_a'])."</td><td>".$result['gd']."</td></tr>";
	echo "<tr><th>Points</th><td>".($result['wins_home']*3+$result['draws_home'])."</td><td>".($result['wins_away']*3+$result['draws_away'])."</td><td>".$result['points']."</td></tr>";



	echo "</table>";
	echo "<br>";
	echo "<div id=\"results\">";

	echo "<div class=\"heading\">Previous Matches</div>";

	echo "<table style=\"display: inline-block;\">"; 
	echo "<th colspan=\"2\">Home</th>";
	$sql="select * from scorehistory where team1_id = '$team_id'";
	$query=mysqli_query($connection,$sql);
	while($result=mysqli_fetch_array($query)){
		
		echo "<tr><td>vs&nbsp;".$result['away']."</td><td>".$result['score1']."&nbsp;-&nbsp;".$result['score2']."</td></tr>";
		

	}
	echo "</table>";
	echo "<table style=\"float: left;\">"; 
	echo "<th colspan=\"2\">Away</th>";
	
	$sql="select * from scorehistory where team2_id = '$team_id'";
	$query=mysqli_query($connection,$sql);

	while($result=mysqli_fetch_array($query)){
		
		echo "<tr><td>vs&nbsp;".$result['home']."</td><td>".$result['score1']."&nbsp;-&nbsp;".$result['score2']."</td></tr>";

	}
	echo "</table>";
	echo "</div>";


	$sql="select (select t.team_name from teams  as t where team_id= team1_id) as home,(select t.team_name from teams  as t where team_id= team2_id) as away, date from `match` as m  where team1_id = '$team_id' or team2_id='$team_id'";
	$query=mysqli_query($connection,$sql);
	
	echo "<div id=\"fixtures\">";
	echo "<div class=\"heading\">Upcoming Fixtures</div>";
	echo "<table>";
	echo "<th>Fixture</th><th>Date</th>";
	while($result=mysqli_fetch_array($query)){
		
		echo "<tr><td>".$result['home']."&emsp;vs&emsp;".$result['away']."</td><td>".$result['date']."</td></tr>";

	}
	echo "</table>";
	echo "</div>";

	echo "</div>";
?>
<html>
<link rel="stylesheet" href="team_infostyle.css" type="text/css">
</html>