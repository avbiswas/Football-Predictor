<?php
	include "db.inc.php";
	session_start();
	$admin=$_SESSION['name'];
	$password=$_SESSION['password'];
	if (isset($_GET['confirm'])){
		$match_id=$_SESSION['match_id'];
		$homescorers=$_SESSION['home_scorers'];

		$awayscorers=$_SESSION['away_scorers'];
		$score1=$_SESSION['s1'];
		$score2=$_SESSION['s2'];
		$sql="INSERT INTO `match_result` (`match_id`,`score1`,`score2`,`home_scorers`,`away_scorers`) VALUES ('$match_id','$score1','$score2','$homescorers','$awayscorers')";
		mysqli_query($connection,$sql);
		if (!(mysqli_error($connection))){

			echo "Result updated";
			echo '<form action="enterresults.php" method="GET">
			<button type="submit" name="back">BACK</button>';

		}
		else{
			echo "Error";
			echo '<form action="enterresults.php" method="GET">
			<button type="submit" name="back">BACK</button>';

		}
	}
	else if (isset($_GET['enterscore'])){
		$home_scorers=urldecode($_GET['home_scorers']);
		$home=$_SESSION['home'];
		$away=$_SESSION['away'];
		$away_scorers=urldecode($_GET['away_scorers']);
		$score1=urldecode($_GET['s1']);
		$score2=urldecode($_GET['s2']);
		echo "MATCH ID: ".$_SESSION['match_id']."<br>";
		echo $home."&emsp;".$score1." - ".$score2."&emsp;".$away."<br>";
		echo "Home Scorers: ";
		if ($home_scorers=="x"){
			$scorers_h=array("x");
			echo "No Home Scorers";
			
		}
		else{
			$scorers_h=explode("|",$home_scorers);
		
			$comma_seperated="'".implode("', '",$scorers_h)."'";
			$sql3="SELECT `player_name` FROM `player` WHERE `player_id` IN ($comma_seperated)";
			$query=mysqli_query($connection,$sql3);
			while($res=mysqli_fetch_array($query))
			{
				echo $res['player_name'].".  ";
			}
			echo "</i><br>";
		}
		echo "Away scorers:  ";
			
		if ($away_scorers=="x"){
			echo "No Away Scorers";
	
		}
		else{
			$scorers_a=explode("|",$away_scorers);
		
			$no_of_scorers_a=count($scorers_a);
			$comma_seperated="'".implode("', '",$scorers_a)."'";
			$sql3="SELECT `player_name` FROM `player` WHERE `player_id` IN ($comma_seperated)";
			$query=mysqli_query($connection,$sql3);
			while($res=mysqli_fetch_array($query))
			{
				echo $res['player_name'].".  ";
			}
			echo "<br>";
		}
		$_SESSION['s1']=$score1;

		$_SESSION['s2']=$score2;
		$_SESSION['home_scorers']=$home_scorers;
		$_SESSION['away_scorers']=$away_scorers;
		
		echo '<form action="#">
			<button type="submit" name="confirm">CONFIRM</button>';
	}
	else{
		$match_id=urldecode($_GET['match_id']);
		$home=urldecode($_GET['home']);
		$away=urldecode($_GET['away']);
		$_SESSION['match_id']=$match_id;
		$_SESSION['home']=$home;
	$_SESSION['away']=$away;
	
	$curl = curl_init();
	curl_setopt($curl, CURLOPT_URL, $curl_url."getSquadsFromMatchID.php?match_id=".$match_id);
	curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
	$squads=curl_exec($curl);
	$squad=explode("//", $squads);
	$homeSquad=$squad[0];
	$awaySquad=$squad[1];

	$homeSquad=json_decode($homeSquad);
	$awaySquad=json_decode($awaySquad);
	
	$lengthH= count($homeSquad->data);
	$lengthA= count($awaySquad->data);
	if ($lengthA>$lengthH){
		$length=$lengthA;
	}
	else{
		$length=$lengthH;
	}

	echo "<br>Match ID: ".$match_id."<br>";
	echo $home ." vs ".$away."<br>";
	echo '<form action="#" method="get">';
		echo 'home: <input type="number" name="s1"><br>

  		away: <input type="number" name="s2"><br>
  		homescorers:<input type="text" name="home_scorers"><br>

  		awayscorers:<input type="text" name="away_scorers"><br><br>';

  	echo '<input type="submit" name="enterscore" value="Submit">
		</form>';
	echo "<table style='border:  2px solid #000;'>";
	echo "<th >Home</th><th>Away</th>";
	for($i=0;$i<$length;$i++){
		echo "<tr><td>";
		if ($lengthH>$i){
		echo $homeSquad->data[$i]->id."&emsp;".$homeSquad->data[$i]->n;
		}
	echo "</td><td>";
		if ($lengthA>$i){
		echo $awaySquad->data[$i]->id."&emsp;".$awaySquad->data[$i]->n."<br>";
		}
	echo "</td></tr>";
	}
	}
?>