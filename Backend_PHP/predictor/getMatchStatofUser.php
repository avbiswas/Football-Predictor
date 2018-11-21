<?php

include "db.inc.php";
$user=urldecode($_GET['user_id']);
$matchday=urldecode($_GET['matchday']);

$sql="SELECT pd.*, m.team1_id,m.team2_id,(select concat(p.team1_score,' - ',p.team2_score) from prediction as p where p.user_id=pd.user_id and p.match_id = pd.match_id ) as score FROM `points_breakdown` as pd JOIN `match` as m JOIN match_result as mr ON m.match_id=mr.match_id and m.match_id=pd.match_id WHERE user_id='$user' and pd.match_id IN (Select match_id from matchday_chart as mc where mc.matchday='$matchday')";
$query=mysqli_query($connection,$sql);
$response=array();
while($row = mysqli_fetch_array($query)){   
	array_push($response,array("id"=>$row['match_id'],
		"h"=>$row['team1_id'],
		"a"=>$row['team2_id'],
		"s"=>$row['score'],
		"pt"=>$row['point']));
	}
$sql="select deduction,bonus,cutoff,no_of_pdtcn from extra_points_table where user_id like '$user' and matchday like '$matchday'";
$query=mysqli_query($connection,$sql);
$row = mysqli_fetch_array($query);   
	array_push($response,array("id"=>"x",
		"d"=>$row['deduction'],
		"b"=>$row['bonus'],
		"m"=>$row['cutoff'],
		"p"=>$row['no_of_pdtcn']
		));

echo json_encode(array("data"=>$response));
?>