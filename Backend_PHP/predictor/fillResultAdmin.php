<?php
	include "db.inc.php";
	$match=urldecode($_GET['match_id']);
	$password=urldecode($_GET['password']);
	$score1=urldecode($_GET['s1']);
	$score2=urldecode($_GET['s2']);
	$scorers_h=urldecode($_GET['home_scorers']);

	$scorers_a=urldecode($_GET['away_scorers']);
	if ($password==1){
		$sql="INSERT INTO `match_result` (`match_id`,`score1`,`score2`,`home_scorers`,`away_scorers`) VALUES ('$match','$score1','$score2','$scorers_h','$scores_a')";
		mysqli_query($connection,$sql);
	}
	else{

		echo "A non-admin tried to update a seeded table! POLICE! POLICE!!";
	}


?>
