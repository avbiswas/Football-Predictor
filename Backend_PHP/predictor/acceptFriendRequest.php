<?php
	include "db.inc.php";

	$user1=urldecode($_GET['user1']);
	$user2=urldecode($_GET['user2']);
	
	$decision=urldecode($_GET['accept']);
	
	if ($decision==1){
		$sql="UPDATE `friend_request` SET status = 1 WHERE `user_id` LIKE '$user1' and `friend_id` LIKE '$user2'";
		mysqli_query($connection,$sql);
		echo "Friends";
	}
	else{
		$sql="UPDATE `friend_request` SET status = 2 WHERE `user_id` LIKE '$user1' and `friend_id` LIKE '$user2'";
		
		mysqli_query($connection,$sql);
		echo "Request Ignored";
	}
?>