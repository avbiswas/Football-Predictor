<?php

include "db.inc.php";
$search_id=urldecode($_GET['search_id']);
$user=urldecode($_GET['user_id']);
$search_id="%".$search_id."%";
$sql="select u.reg_id as id, u.user_id as username, ud.name as name, ud.fav_club_id as team from user as u  JOIN user_details as ud on u.reg_id=ud.user_id where (u.user_id LIKE '$search_id' or ud.name LIKE '$search_id') and (u.reg_id NOT LIKE '$user')";
$query=mysqli_query($connection,$sql);
$response=array();
while($row = mysqli_fetch_array($query)){   
	array_push($response,array("id"=>$row['id'],
		"un"=>$row['username'],
		"n"=>$row['name'],
		"c"=>$row['team']));
	}
	echo json_encode(array("data"=>$response));
?>