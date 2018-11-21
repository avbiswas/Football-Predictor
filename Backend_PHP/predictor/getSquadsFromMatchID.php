<?php
	include "db.inc.php";
	$match_id=urldecode($_GET['match_id']);
	$sql="SELECT team1_id,team2_id from `match` WHERE match_id LIKE '$match_id'";
	$result = mysqli_query($connection,$sql);
	$row = mysqli_fetch_array($result);
	$home=$row['team1_id'];
	$away=$row['team2_id'];
	$sql="SELECT * from `player` WHERE `team_id` LIKE '$home'";	
	$result = mysqli_query($connection,$sql);
	$response=array();
	while($row = mysqli_fetch_array($result)){   
	array_push($response,array("id"=>$row['player_id'],
		"n"=>$row['player_name'],
		"j"=>$row['jersey'],
		"p"=>" "
		));
	}
	echo json_encode(array("data"=>$response));
	echo "//";
	$sql="SELECT * from `player` WHERE `team_id` LIKE '$away'";	
	$result = mysqli_query($connection,$sql);
	$response=array();
	while($row = mysqli_fetch_array($result)){   
	array_push($response,array("id"=>$row['player_id'],
		"n"=>$row['player_name'],
		"j"=>$row['jersey'],
		"p"=>" "
		));
	}
	
	echo json_encode(array("data"=>$response));



?>