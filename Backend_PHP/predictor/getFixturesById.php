<?php
include "db.inc.php";
$matchday=urldecode($_GET['matchday']);
if ($matchday==0){
	$sql="SELECT m1.match_id,d.matchday, t1.team_id as home, t2.team_id as away, DATE_FORMAT(m1.date,'%d %M %a %h:%i %p') AS date FROM  matchday as d JOIN teams AS t1 JOIN `match` AS m1 JOIN teams AS t2 ON t1.`team_id` LIKE m1.`team1_id` AND t2.`team_id` LIKE m1.`team2_id`AND m1.date between d.startDate AND d.endDate WHERE (NOW()) between d.startDate and d.endDate";
}
else{

	$sql="SELECT m1.match_id,d.matchday, t1.team_id as home, t2.team_id as away, DATE_FORMAT(m1.date,'%d %M %a %h:%i %p') AS date FROM  matchday as d JOIN teams AS t1 JOIN `match` AS m1 JOIN teams AS t2 ON t1.`team_id` LIKE m1.`team1_id` AND t2.`team_id` LIKE m1.`team2_id`AND m1.date between d.startDate AND d.endDate WHERE matchday LIKE $matchday";

}
$result = mysqli_query($connection,$sql);
//echo "<table>"; 
$response=array();
while($row = mysqli_fetch_array($result)){   
//echo "<tr><td>" . $row['home'] . "</td><td>" . $row['away']."</td><td>". $row['date'] . "</td></tr>";  
	array_push($response,array("id"=>$row['match_id'],
		"home"=>$row['home'],
		"away"=>$row['away'],
		"matchday"=>$row['matchday'],
		"date"=>$row['date']));
}
	echo json_encode(array("data"=>$response));
	return json_encode(array("data"=>$response));
	
	?>