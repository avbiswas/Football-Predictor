<?php
	include "db.inc.php";
	$user=urldecode($_GET['user_id']);
	$match=urldecode($_GET['match_id']);
	$sql="delete from `prediction` where `match_id` like $match and `user_id` like $user ";
	$query=mysqli_query($connection,$sql);
	echo mysqli_error($connection);
	if (!(mysqli_error($connection))){
		echo "Prediction Withdrawn";
	}

	?>