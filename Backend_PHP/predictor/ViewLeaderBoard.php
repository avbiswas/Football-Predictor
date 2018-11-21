<?php
	include "db.inc.php";

	$sql="SELECT upm.*, (select u.user_id from `user` as u where u.reg_id LIKE upm.user_id) as name FROM `user_points_matches` as upm ";
	$query=mysqli_query($connection,$sql);
	echo "<body>";
	echo "<div id=header><h1>LEADERBOARD</h1></div>";
	
	echo "<table  >";
	echo "<tr><th>&nbsp#&nbsp</th><th>Name</th><th>Points</th><th>Pure Points</th><th>Predictions</th></tr>";
	$i=1;
	while($result=mysqli_fetch_array($query)){
		echo "<tr><td>".$i++."</td><td >".$result['name']."</td><td >".$result['net_points']."</td><td >".$result['points_from_matches']."</td><td>".$result['predictions']."</td></tr>";

	}
	echo "</table></body>";

?>
<html>
<head>
<link href="article.css" rel="stylesheet" type="text/css">
</head>
</html>