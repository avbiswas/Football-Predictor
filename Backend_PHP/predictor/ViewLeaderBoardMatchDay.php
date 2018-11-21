<?php
	include "db.inc.php";
	$matchday=urldecode($_GET["matchday"]);
	$sql_match="select sum(predictions) as tot, avg(points) as avg, sum(points) as total from points_predictions_per_matchday where matchday='$matchday'";
	$query=mysqli_query($connection,$sql_match);
	$result1=mysqli_fetch_array($query);
	echo "<div id=header>";
	echo "<h1>MATCHDAY&nbsp".$matchday."&nbsp STATS</h1>";

	echo "Number of predictions: <b>".$result1[0]."</b><br>Average Points per User: <b>".round($result1[1])."</b><br>Average points per prediction: <b>".round($result1[2]/$result1[0])."</b><br>";
	echo "</div>";
	$sql="select (select u.user_id from `user` as u where u.reg_id=pm.user_id) as username, pm.points, pm.predictions from points_predictions_per_matchday as pm where matchday = '$matchday' order by points desc,predictions desc";
	$query=mysqli_query($connection,$sql);
	echo "<table>";
	echo "<tr><th>&nbsp#&nbsp</th><th>Name</th><th>Points</th><th>Matches</th></tr>";
	$i=1;
	while($result=mysqli_fetch_array($query)){
		echo "<tr ><td>".$i."</td><td>&nbsp".$result[0]."</td><td >".$result[1]."</td><td>".$result[2]."</td></tr>";
		$i++;
	}
	echo "</table>";
	echo "</body>";
	echo "</html>";
?>
<html>
<head>
<link href="article.css" rel="stylesheet" type="text/css">
</head>
</html>