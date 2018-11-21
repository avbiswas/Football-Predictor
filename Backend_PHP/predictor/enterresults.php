<?php
	include "db.inc.php";
	session_start();
	if (isset($_POST['submit']))
	{
	$admin=$_POST['name'];
	$password=$_POST['password'];
	$md=$_POST['md'];
	$_SESSION['md']=$md;
	$_SESSION["name"] = $admin;
	$_SESSION["password"] = $password;
	}
	else if (isset($_GET['back'])){
		$admin=$_SESSION['name'];
		$password=$_SESSION['password'];
		$md=$_SESSION['md'];
	}
	$str="http://localhost/predictor/getFixturesById.php?matchday=".$md;
	
	$sql="Select count(*) as count from admin where admin_name Like '$admin' and password like '$password'";
	$query=mysqli_query($connection,$sql);
	$result=mysqli_fetch_array($query);
	if ($result['count']==0){
		echo "Doesnt Exist";
		
	}
	else{
		$sql="SELECT mc.match_id, team1_id, team2_id, (select team_name from teams as t where team1_id like team_id) as home, (select team_name from teams as t where team2_id like team_id) as away, (select count(m2.match_id) from match_result as m2 where mc.match_id like m2.match_id) as updated  FROM `matchday_chart` as mc join `match` as m on m.match_id=mc.match_id where matchday='$md'";
		$query=mysqli_query($connection,$sql);
		while($result=mysqli_fetch_array($query)){
			$url="fillResults.php?admin=".$admin."&password=".$password."&match_id=".$result['match_id']."&home=".$result['home']."&away=".$result['away'];

			echo '<a href="'.$url.'">'.$result['home']." vs ".$result['away'].'</a>';
			if ($result['updated']==1){
				echo "&emsp;&emsp; <font color = 'red'>Score Entered</font>";
			}
			else{
				echo "&emsp;&emsp; <font color = 'red'>Score Not Entered</font>";	
			}
			echo "<br><br>";
		}
	}


?>