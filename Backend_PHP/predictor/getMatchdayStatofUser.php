<?php

include "db.inc.php";
$user=urldecode($_GET['user_id']);
$sql="SELECT p.*, (select count(*) from matchday_chart as m_c where m_c.matchday=p.matchday) as total FROM `points_predictions_per_matchday` as p where user_id LIKE '$user' and matchday<(select matchday from matchday where now() between startDate and endDate) ORDER BY matchday desc";
$query=mysqli_query($connection,$sql);
$response=array();
while($row = mysqli_fetch_array($query)){   
	array_push($response,array("no"=>$row['matchday'],
		"t"=>$row['total'],
		"p"=>$row['predictions'],
		"pt"=>$row['points']));
	}
	echo json_encode(array("data"=>$response));
?>