<?php
	include "db.inc.php";
	$id=urldecode($_GET['user_id']);
	$sql="select u.fav_club_id as club , (select active_since from active_since as a_s where a_s.user_id like '$id') as active_since from user_details as u where u.user_id like '$id'";
	$query=mysqli_query($connection,$sql);
	$result=mysqli_fetch_array($query);

	$sql_latest="select predictions as latest_predictions,points as latest from points_predictions_per_matchday where user_id like '$id' and matchday LIKE ((SELECT matchday from matchday where NOW() between startDate and endDate)-1)";
	$query_latest=mysqli_query($connection,$sql_latest);
	$result_latest=mysqli_fetch_array($query_latest);

	$rankQuery="CREATE TEMPORARY TABLE `t2` ( SELECT (@row:=@row+1) AS rank,u.* FROM user_points_matches as u, (select @row:=0) as t )";
	$resultRankQuery=mysqli_query($connection,$rankQuery);
	$getQuery="SELECT * FROM `t2` where user_id LIKE '$id'";
	$resultRankQuery=mysqli_query($connection,$getQuery);
	$rowRankQuery = mysqli_fetch_array($resultRankQuery);
		
	$data_array=array();
	$a_s=$result['active_since'];
	if ($a_s==100){
		$a_s="Not Active Yet";
	}
	else{
		$a_s="Matchday ".$a_s;
	}

	array_push($data_array,array('c'=>$result['club'],'pts'=>$rowRankQuery['net_points'],'pure_pts'=>$rowRankQuery['points_from_matches']  ,'mat'=>$rowRankQuery['predictions'],'r'=>$rowRankQuery['rank'],'lat_p'=>$result_latest['latest_predictions'],'lat'=>$result_latest['latest'],'a_s'=>$a_s));
	//$data_array=array('data'=>$data_array);
	

	$sql="SELECT gs.user_id, gs.point,gs.predictions, g.* from group_stat as gs JOIN groups_overview as g ON gs.group_id=g.group_id where user_id like '$id'
	";
	$query=mysqli_query($connection, $sql);
	$group_data=array();
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

		array_push($group_data, array('gid'=>$result['group_id'],
			'gn'=>$result['group_name'],'gm'=>$result['matchday'],'gpt'=>$result['point'],'gr'=>$rank['rank']));
	}
	
	$data=array();
	array_push($data, array("u_d"=>$data_array));
	array_push($data, array("g_d"=>$group_data));
	echo json_encode(array("data"=>$data));
	


	

?>