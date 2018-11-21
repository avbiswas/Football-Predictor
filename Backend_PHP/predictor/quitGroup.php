<?php
	include "db.inc.php";
	$user_id=urldecode($_GET['id']);
	$group_id=urldecode($_GET['group_id']);

	$sql="DELETE * FROM `group_details` WHERE `group_id` LIKE '$group_id' && `user_id` LIKE '$id'";
	mysqli_query($connection,$sql);
?>