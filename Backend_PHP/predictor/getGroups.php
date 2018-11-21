<?php
	include "db.inc.php";
	$id=urldecode($_GET['user_id']);
	$sql="SELECT gs.user_id, gs.point,gs.predictions, g.* from group_stat as gs JOIN groups_overview as g ON gs.group_id=g.group_id where user_id like '$id'
";
	$query=mysqli_query($connection, $sql);
	$response=array();
	while($result=mysqli_fetch_array($query)){
		$group_id=$result['group_id'];



		$sql_temp_table="CREATE temporary table `ranktable` as (SELECT (@row := @row + 1) AS rank, u.* FROM group_stat as u, (select @row:=0) as t where group_id LIKE '$group_id')";
		mysqli_query($connection,$sql_temp_table);
		echo mysqli_error($connection);
		
		$sql_rank_query="select rank from ranktable where user_id LIKE '$id'";
		$sql_rank=mysqli_query($connection,$sql_rank_query);
		$rank=mysqli_fetch_array($sql_rank);
		echo mysqli_error($connection);

		$sql="DROP temporary table `ranktable`";
		mysqli_query($connection,$sql);

		array_push($response, array('group_id'=>$result['group_id'],
			'group_name'=>$result['group_name'],'matchday'=>$result['matchday'],'point'=>$result['point'],'prediction'=>$result['predictions'],'rank'=>$rank['rank'],'strength'=>$result['members'],'max'=>$result['max_points'],'avg'=>$result['avg_points'], 'leader'=>$result['leader']));
	}
	echo json_encode(array('data'=>$response));
	mysqli_close($connection);
?>