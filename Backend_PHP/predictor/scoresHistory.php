<?php
	include "db.inc.php";
	$matchday=urldecode($_GET['matchday']);
	$sql="select home,score1,score2,away from scorehistory where matchday like '$matchday'";
	$query=mysqli_query($connection,$sql);
		
	while ($result=mysqli_fetch_array($query)){
		echo "<div class=\"score\">";
	
		echo $result['home']."&emsp;".$result['score1']."&nbsp-&nbsp".$result['score2']."&emsp;".$result['away']."<br>";
		echo "</div>";
	}
?>
<html>
<link rel="stylesheet" href="article.css" type="text/css">


</html>