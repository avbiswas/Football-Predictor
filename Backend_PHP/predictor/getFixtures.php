<?php
include "db.inc.php";
$matchday=urldecode($_GET['matchday']);
if ($matchday==0){
$sql="SELECT m1.match_id,d.matchday, t1.team_name as home, t2.team_name as away, t1.stadium,  DATE_FORMAT(m1.date,'%d %M %a %h:%i %p') AS date FROM  matchday as d JOIN teams AS t1 JOIN `match` AS m1 JOIN teams AS t2 ON t1.`team_id` LIKE m1.`team1_id` AND t2.`team_id` LIKE m1.`team2_id`AND m1.date between d.startDate AND d.endDate ";
}
else{
	$sql="SELECT m1.match_id,d.matchday, t1.team_name as home, t2.team_name as away, t1.stadium,  DATE_FORMAT(m1.date,'%d %M %a %h:%i %p') AS date FROM  matchday as d JOIN teams AS t1 JOIN `match` AS m1 JOIN teams AS t2 ON t1.`team_id` LIKE m1.`team1_id` AND t2.`team_id` LIKE m1.`team2_id`AND m1.date between d.startDate AND d.endDate WHERE d.`matchday` LIKE '$matchday'";
// $sql="SELECT d.matchday, t1.team_name as home, concat(r.`score1`," - ",r.`score2`) AS score, t2.team_name as away, t1.stadium,  DATE_FORMAT(m1.date,'%d %M %a %h:%i %p') AS date FROM  matchday as d JOIN teams AS t1 JOIN `match` AS m1 JOIN teams AS t2 JOIN `match_result` AS r ON t1.`team_id` LIKE m1.`team1_id` AND t2.`team_id` LIKE m1.`team2_id` AND r.`match_id` LIKE m1.`match_id` AND m1.date between d.startDate AND d.endDate"; //for scores
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
		"stadium"=>$row['stadium'],
		"date"=>$row['date']));
}
	echo json_encode(array("data"=>$response));

//echo "</table>"; 
?>