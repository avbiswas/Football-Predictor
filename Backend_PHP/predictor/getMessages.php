<?php
	include "db.inc.php";
	$id=urldecode($_GET['id']);
	$sql="select * from reply_message where user_id like '$id' or user_id is null order by date desc,reply_id desc";
	$query=mysqli_query($connection,$sql);
	$array=array();
	while($result=mysqli_fetch_array($query)){
		array_push($array,array("msg"=>$result['reply'], 
			"date"=>$result['date']));
	}
	echo json_encode(array("data"=>$array));
	$sql="update reply_message set seen = 1 where user_id like '$id'";
	mysqli_query($connection,$sql);
	$sql="insert into global_message_seen `seen_id` values ('$id')";
	mysqli_query($connection,$sql);
	?>