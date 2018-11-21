<?php
	include "db.inc.php";
	$id=urldecode($_GET['user_id']);
	$group_id=urldecode($_GET['group_id']);
	
	$sql="INSERT INTO `group_permissions` (`permission_id`,`user_id`,`group_id`,
		`time`) VALUES (NULL,'$id','$group_id',NOW())";
	mysqli_query($connection,$sql);
	if(mysqli_error($connection)){
		$sql2="UPDATE `group_permissions` SET time = NOW(), status = 0 WHERE `group_id` LIKE '$group_id' && `user_id` LIKE '$id'";
		mysqli_query($connection,$sql2);
		if (mysqli_error($connection)){
			echo "Something went wrong...";
		}
		else{
			echo "Request Updated";
		}
	}
	else{
		echo "Joining Request Sent";
	}
?>