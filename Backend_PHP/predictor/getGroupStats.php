<?php
	include "db.inc.php";
	$id=urldecode($_GET['group_id']);
	$sql="SELECT user_id,username,`point`,predictions from `group_stat` Where group_id like '$id'";
	$query=mysqli_query($connection, $sql);
	$response=array();
	while($result=mysqli_fetch_array($query)){
		array_push($response, array('user_id'=>$result['user_id'],
			'user_name'=>$result['username'],'points'=>$result['point'],'predictions'=>$result['predictions'],'latest'=>"0"));
	}
	echo json_encode(array('data'=>$response));
?>