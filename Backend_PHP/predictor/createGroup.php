<?php

	include "db.inc.php";
	$group_name=urldecode($_GET['group_title']);
	$id=urldecode($_GET['data']);
	$array=array();
	$array=explode("/", $id);
	
	$sql="INSERT INTO `group` (`group_id`,`group_name`,`matchday`) VALUES (NULL, '$group_name',(select matchday from matchday where now() between startDate and endDate))";
	mysqli_query($connection,$sql);
	if(!mysqli_error($connection)){
	$sql="SELECT group_id as this FROM `group` where group_name like '$group_name'";
	$q=mysqli_query($connection,$sql);
	$res=mysqli_fetch_array($q);
	$group_id=$res['this'];
	$insertQuery="INSERT INTO `group_details` (`group_id`, `user_id`) VALUES ";
	foreach ($array as $members) {
			$insertQuery=$insertQuery."('$group_id', '$members'),";
		}	
	$sql2= substr($insertQuery,0,-1);
	
	$query=mysqli_query($connection,$sql2);
		echo mysqli_error($connection);
		echo "Group was created";

	}	
	else{
		echo mysqli_error($connection);
		echo "Group Name already exists...";
	}
	

?>