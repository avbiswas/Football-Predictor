<?php
	include "db.inc.php";
	$sql="SELECT * FROM groups_overview order by max_points";
	$query=mysqli_query($connection, $sql);
	$response=array();
	while($result=mysqli_fetch_array($query)){
		array_push($response, array('group_id'=>$result['group_id'],
			'groupname'=>$result['group_name'],'max_points'=>$result['max_points'],'leader'=>$result['leader'],'avg_points'=>$result['avg_points']));
	}
	echo json_encode(array('data'=>$response));


?>