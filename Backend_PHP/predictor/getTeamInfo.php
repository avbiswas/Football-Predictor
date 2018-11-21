<?php
	include "db.inc.php";
	$sql="SELECT  team_id, team_name, stadium from teams";
	$query=mysqli_query($connection,$sql);
	
	while ($result=mysqli_fetch_array($query)){
		echo "team_info.add(new Team(\"".$result['team_name']."\",";
		echo  "\"".$name=$result['stadium']. "\");";
		echo "<br>";
			
	}


?>