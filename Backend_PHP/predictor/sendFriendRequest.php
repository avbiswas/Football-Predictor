<?php
include "db.inc.php";
$user=urldecode($_GET['user_id']);
$friend=urldecode($_GET['friend_id']);

$sql="INSERT INTO `friend_request` (`user_id`, `friend_id`, `date`,`status`) VALUES ('$user','$friend',NOW(), '0')";
mysqli_query($connection,$sql);
if (mysqli_error($connection)){
	echo "You have already sent a request";
}
else {
	echo "Request Sent";
}
?>