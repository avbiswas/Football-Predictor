<?php

	include "db.inc.php";
	$match=21;
	$date = date('m/d/Y h:i:s a', time());
	$datetimeSql="Select date from `match` where match_id like '$match'";
	$datetimeQ=mysqli_query($connection,$datetimeSql);
	$datetime= mysqli_fetch_array($datetimeQ);
	$matchDate=$datetime['date'];	
	
$datetime1 = date_create($date);
$datetime2 = date_create($matchDate);
$interval = date_diff($datetime1, $datetime2);
$dayDiff=$interval->format('%R%a');
$hrDiff=$interval->format('%R%h');

$minDiff=$interval->format('%R%i');
echo $dayDiff;

echo $hrDiff;
echo $minDiff;

if ($dayDiff>=0 && $hrDiff>=0 && $minDiff>=-1){
	echo "<br>allowed";
}
else{
	echo "<br>not allowed";
}


?>