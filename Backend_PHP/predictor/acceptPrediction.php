<?php
	include "db.inc.php";
	$user=urldecode($_GET['user_id']);
	$match=urldecode($_GET['match_id']);
	$score1=urldecode($_GET['score1']);
	$score2=urldecode($_GET['score2']);
	$scorers_string_h=urldecode($_GET['hs']);
	$scorers_string_a=urldecode($_GET['as']);
	
		$date = date('m/d/Y h:i:s a', time());
	$datetimeSql="Select date from `match` where match_id like '$match'";
	$datetimeQ=mysqli_query($connection,$datetimeSql);
	$datetime= mysqli_fetch_array($datetimeQ);
	$matchDate=$datetime['date'];	
	
	$datetime1 = date_create($date);
	$datetime2 = date_create($matchDate);
	$interval = date_diff($datetime1, $datetime2);
	$dayDiff=$interval->format('%R%a');
	$hrDiff=$interval->format('%R%h');

	$minDiff=$interval->format('%R%i');
	if ($dayDiff>=0 && $hrDiff>=0 && $minDiff>=-1){
		$allowed=1;
	}
	else{
		$allowed=0;
	}
	if ($allowed==0){
		echo "Prediction Lines Closed!";
	}
	else
	{
	$sql="INSERT INTO `predictor`.`prediction` (`prediction_id`, `match_id`, `user_id`, `team1_score`, `team2_score`,`home_scorers`, `away_scorers`) VALUES (NULL, '$match', '$user', '$score1', '$score2', '$scorers_string_h', '$scorers_string_a')";
	mysqli_query($connection,$sql);
	if (mysqli_error($connection)){
		$sql="UPDATE `prediction` SET `team1_score`='$score1',`team2_score`='$score2',`home_scorers`='$scorers_string_h', `away_scorers`='$scorers_string_a' WHERE user_id Like '$user' AND match_id Like '$match'";
		mysqli_query($connection,$sql);
		if (mysqli_error($connection)){
			echo "Something terrible happened";
		}
		else{
			echo "Your prediction has been updated";
		}
	}
	else{
		echo "Your prediction was recorded";
	}
}
	
?>