
<?php
	include "db.inc.php";
	$match_id=urldecode($_GET['match_id']);
	$user_id=urldecode($_GET['user_id']);
	
	$sql2="SELECT `score1`,`score2`,`home_scorers`,`away_scorers` FROM `match_result` WHERE `match_id` LIKE '$match_id'";
	
	$insertPointBreakdown="";
	$insertQueryCase="";
	$insertQueryIn="";


	$query=mysqli_query($connection,$sql2);
	while($array=mysqli_fetch_array($query)){
		$home_score=$array['score1'];
		$away_score=$array['score2'];
		echo "<b>Scoreline: ".$home_score."-".$away_score."</b><br>";
		echo "<i>Home scorers:  ";
			
		if ($array['home_scorers']=="x"){
			$scorers_h=array("x");
			echo "No Home Scorers";
	
		}
		else{
			$scorers_h=explode("|",$array['home_scorers']);
		
			$no_of_scorers_h=count($scorers_h);
			$comma_seperated="'".implode("', '",$scorers_h)."'";
			$sql3="SELECT `player_name` FROM `player` WHERE `player_id` IN ($comma_seperated)";
			$query=mysqli_query($connection,$sql3);
			while($res=mysqli_fetch_array($query))
			{
				echo $res['player_name'].".  ";
			}
			echo "</i><br>";
		}
		echo "<i>Away scorers:  ";
			
		if ($array['away_scorers']=="x"){
			$scorers_a=array("x");
			echo "No Away Scorers";
	
		}
		else{
			$scorers_a=explode("|",$array['away_scorers']);
		
			$no_of_scorers_a=count($scorers_a);
			$comma_seperated="'".implode("', '",$scorers_a)."'";
			$sql3="SELECT `player_name` FROM `player` WHERE `player_id` IN ($comma_seperated)";
			$query=mysqli_query($connection,$sql3);
			while($res=mysqli_fetch_array($query))
			{
				echo $res['player_name'].".  ";
			}
			echo "</i><br>";
		}
		$str_temp="`player_id` LIKE ";
	
		if ($home_score>$away_score){
			$result="h";
		}
		else if ($home_score<$away_score){
			$result="a";
		}
		else{
			$result="d";
		}
		$gd=$home_score-$away_score;

		echo "<br><br><br>";
	}
	$sql="SELECT u.`reg_id`,u.`user_id`, p.`team1_score` AS predict1,p.`team2_score` as predict2, p.`home_scorers` AS predicted_Scorers_h, p.`away_scorers` AS predicted_Scorers_a FROM `prediction` AS p JOIN `user` AS u ON u.`reg_id` LIKE p.`user_id`  && p.`match_id` LIKE '$match_id' where u.reg_id='$user_id'";

	$query=mysqli_query($connection,$sql);
	while($array=mysqli_fetch_array($query)){
		$points=0;
		$sql4="";
		$comma_seperated="";
		$q="";
		$res="";
		$reg_id=$array['reg_id'];
		$user_id=$array['user_id'];
		
		$P_home_score=$array['predict1'];
		$P_away_score=$array['predict2'];

		if ($array['predicted_Scorers_h']!="p" && $array['predicted_Scorers_h']!="x"){
			$P_scorers_h=explode("|",$array['predicted_Scorers_h']);
			$P_no_of_scorers_h=count($P_scorers_h);
		}
		else{
			$P_scorers_h=$array['predicted_Scorers_h'];
			
			$P_no_of_scorers_h=0;
		}	
		
		if ($array['predicted_Scorers_a']!="p" && $array['predicted_Scorers_a']!="x"){
			$P_scorers_a=explode("|",$array['predicted_Scorers_a']);
			$P_no_of_scorers_a=count($P_scorers_a);
		}
		else{
			$P_scorers_a=$array['predicted_Scorers_a'];
			$P_no_of_scorers_a=0;
		}	
		
		$P_gd=$P_home_score-$P_away_score;
		
		echo "<b>".$user_id.":</b><br>";
		echo "<i>Predicted Scoreline: ".$P_home_score."-".$P_away_score."</i><br>";
		echo "<i>Predicted Scorers (H):  ";
		
		if  ($P_no_of_scorers_h>0){
		$comma_seperated="'".implode("', '",$P_scorers_h)."'";
		$sql4="SELECT `player_name` FROM `player` WHERE `player_id` IN ($comma_seperated)";
		
		$q=mysqli_query($connection,$sql4);
		while($res=mysqli_fetch_array($q))
		{
			echo $res['player_name'].".  ";
		}
		echo "</i><br>";
		}
		else{
			if ($P_scorers_h=="x"){
				echo "<i>No scorers predicted.</i><br>";
			}
			else
			{
				echo "<i>Pass.</i><br>";
			}
		}
		
		echo "<i>Predicted Scorers (A):  ";
		
		if  ($P_no_of_scorers_a>0){
		$comma_seperated="'".implode("', '",$P_scorers_a)."'";
		$sql4="SELECT `player_name` FROM `player` WHERE `player_id` IN ($comma_seperated)";
		$q=mysqli_query($connection,$sql4);
		while($res=mysqli_fetch_array($q))
		{
			echo $res['player_name'].".  ";
		}
		echo "</i><br>";
		}
		else{
			if ($P_scorers_a=="x"){
				echo "<i>No scorers predicted.</i><br>";
			}
			else
			{
				echo "<i>Pass.</i><br>";
			}
		}
		echo "<br>";
		if ($P_home_score>$P_away_score){
		
			$P_result="h";
		}
		else if ($P_home_score<$P_away_score){
			$P_result="a";
		}
		else{
			$P_result="d";
		}
		
		if ($result==$P_result){
			$points=$points+20;
			echo "Correct Prediction Of Result, &emsp;+20"."<br>";
			if ($home_score==$P_home_score && $away_score==$P_away_score){
				$points=$points+50;
				echo "Perfect Prediction, &emsp;+50";
			}
			else{
				echo "Prediction not perfect, &emsp;+0";	
			}
			echo "<br>";
		}
		else{
			echo "Prediction incorrect <br>";
		}	

		
		$gd_h=abs($home_score-$P_home_score);
		$gd_a=abs($away_score-$P_away_score);
		
		if ($gd_h==0 && $gd_a!=0){
			echo "Correct Prediction for Home Team's Score. &emsp; +20<br>";
			$points=$points+20;
		}
		if ($gd_a==0 && $gd_h!=0){
			echo "Correct Prediction for Away Team's Score. &emsp; +20<br>";
			$points=$points+20;
		}
		if ($gd_h>2){
			$points=$points-(($gd_h-2)*10);
			echo "Home Team's goal difference off by ".$gd_h." goals. -".(($gd_h-2)*10);
			echo "<br>";
		}
		else{
			echo "Home Team's goal difference not off by more than 2 goals. No deductions.<br>";
		}
		
		if ($gd_a>2){
			$points=$points-(($gd_a-2)*10);
			echo "Away Team's goal difference off by ".$gd_a." goals. -".(($gd_a-2)*10);
			echo "<br>";
		}
		else{
			echo "Away Team's goal difference not off by more than 2 goals. No deductions.<br>";
		}
		
		$s_counts_h = array_count_values($scorers_h);
		$s_counts_a = array_count_values($scorers_a);
		
		$i=0;
		$scorer_hits=0;
		$scorer_miss=0;	
		while ($i<$P_no_of_scorers_h){
			if(in_array($P_scorers_h[$i], $scorers_h)){
				$freq = $s_counts_h[$P_scorers_h[$i]]."<br>";
				$scorer_hits+=$freq;
			}
			else{
				$scorer_miss++;
			}
			$i++;
		}
		$i=0;
		while ($i<$P_no_of_scorers_a){
			if(in_array($P_scorers_a[$i], $scorers_a)){
				$freq = $s_counts_a[$P_scorers_a[$i]]."<br>";
				$scorer_hits+=$freq;
			}
			else{
				$scorer_miss++;
			}
			$i++;
		}
		if ($P_no_of_scorers_h==0){
			if ($scorers_h=="x"){

				if ($P_scorers_h=="x"){
					$scorer_hits++;
				}
				else{
					$scorer_miss++;
				}

			}
		}
		if ($P_no_of_scorers_a==0){
			if ($scorers_a=="x"){

				if ($P_scorers_a=="x"){
					$scorer_hits++;
				}
				else{
					$scorer_miss++;
				}

			}
		}

		echo "Scorer Prediction hits = ".$scorer_hits.",&emsp;+".($scorer_hits*20)."<br>";

		echo "Scorer Prediction misses = ".$scorer_miss.",&emsp;-".($scorer_miss*10)."<br>";
		if ($P_scorers_h=="p" && $scorers_h == "x"){
			echo "User passed on predicting home scorer(s). No bonus.<br>";
		}


		if ($P_scorers_h=="a" && $scorers_a == "x"){
			echo "User passed on predicting away scorer(s). No bonus.<br>";
		}
		$points+= $scorer_hits*20-$scorer_miss*10;
		echo "<b>Final points added for this match = &emsp;".$points."</b>";
	}
?>