<?php
	include "db.inc.php";
	$match=urldecode($_GET["match_id"]);
	$sql_match="select home,away,score,date,predictions,avg from fixture_results where match_id like '$match'";
	$query=mysqli_query($connection,$sql_match);
	$result1=mysqli_fetch_array($query);
	echo "<div id=header>";
	echo "<h1 opacity=\"1\">".$result1[0]."  ".$result1[2]."  ".$result1[1]."</h1>";
	echo "<h2>".$result1[3]."</h2><br>";
	echo "Number of predictions: <b>".$result1[4]."</b><br>Average: <b>".round($result1[5])."</b><br><br>";
	echo "</div>";
	$sql="SELECT @row:=@row+1 as rank,u.user_id, pd.point FROM `points_breakdown` as pd JOIN `user` as u ON u.reg_id=pd.user_id,(select @row:=0) as t where match_id = '$match' order by point DESC";
	$query=mysqli_query($connection,$sql);
	echo "<table>";
	echo "<tr><th>&nbsp#&nbsp</th><th>Name</th><th>Points</th></tr>";
	while($result=mysqli_fetch_array($query)){
		echo "<tr ><td>&nbsp".$result[0]."</td><td >".$result[1]."</td><td ALIGN=>".$result[2]."</td></tr>";
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