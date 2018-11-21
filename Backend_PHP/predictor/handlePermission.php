<?php
	include "db.inc.php";
	$id=urldecode($_GET['permission_id']);
	$decision=urldecode($_GET['accept']);
	
	if ($decision==1){
		$sql="INSERT INTO `group_details` (`group_id`,`user_id`)  VALUES ((SELECT `group_id` FROM group_permissions WHERE `permission_id` LIKE '$id'),(SELECT `user_id` FROM group_permissions WHERE `permission_id` LIKE '$id'))";
		mysqli_query($connection,$sql);
		$sql="UPDATE `group_permissions` SET status = 1 WHERE `permission_id` LIKE '$id'";
		mysqli_query($connection,$sql);
		echo "Member added";
	}
	else{
		$sql="UPDATE `group_permissions` SET status = 2 WHERE `permission_id` LIKE '$id'";
		
		mysqli_query($connection,$sql);
		echo "Request Ignored";
	}
?>