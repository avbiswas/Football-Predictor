<?php

	include "db.inc.php";
	$user_id=urldecode($_GET['id']);
	$matchday=urldecode($_GET['matchday']);
	
	$sql="SELECT * FROM prediction where user_id like '$user_id' and match_id IN (select match_id from matchday_chart where matchday like '$matchday')";
	$query=mysqli_query($connection,$sql);
	$response=array();
	while ($result=mysqli_fetch_array($query)){
		
		array_push($response, array("pid"=>$result['prediction_id'],
			"m"=>$result['match_id'],
			"s1"=>$result['team1_score'],
			"s2"=>$result['team2_score'],
			"hs"=>$result['home_scorers'],
			"as"=>$result['away_scorers']));
	}
	echo json_encode(array("data"=>$response));
?>