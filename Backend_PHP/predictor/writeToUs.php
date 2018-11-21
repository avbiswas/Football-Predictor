<?php
	include "db.inc.php";
	$id=urldecode($_GET['id']);
	$message=$_POST['msg'];
	$sql="INSERT into write_to_us (`msg_id`,`id`,`message`) VALUES (null,'$id','$message')";
	mysqli_query($connection,$sql);
	if (mysqli_error($connection)){
		echo "There was an error. Please try later.";
	}
	else{
		echo "Message received. Thank You.";	
	}
?>