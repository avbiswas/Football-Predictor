<?php
	include "db.inc.php";
	$sql="select team_name,matches,wins,losses,draws,gd,points from team_stats";
	$query=mysqli_query($connection,$sql);
	echo "<div id=\"standings\">";
	echo "<table>";
	echo "<tr><th>Team</th><th>P</th><th>W</th><th>L</th><th>D</th><th>GD</th><th>PTS</th></tr>";
		
	while ($result=mysqli_fetch_array($query)){
		echo "<tr><td><b>".$result['team_name']."</b></td><td >".$result['matches']."</td><td >".$result['wins']."</td><td >".$result['losses']."</td><td>".$result['draws']."</td><td >".$result['gd']."</td><td ><b>".$result['points']."</b></td></tr>";
	}
	echo "</table></div>";
?>
<html>
<link rel="stylesheet" href="article.css" type="text/css">


</html>