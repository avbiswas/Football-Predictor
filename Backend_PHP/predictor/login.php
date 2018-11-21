<?php
include "db.inc.php";
$user=$_GET['id'];
$password=$_GET['password'];
$query="SELECT COUNT(*) FROM `user` WHERE `user_id` LIKE '$user' && `password` LIKE '$password'";

			$result=mysqli_query($connection,$query);
			$row=mysqli_fetch_array($result);
			$count=$row['COUNT(*)'];
			$response= array();
			if($count==1)
			{
				$query="SELECT u2.`user_id` AS username, u1.* FROM `user_details` AS u1 JOIN `user` AS u2 ON u1.`user_id` = u2.`reg_id`  WHERE u2.`user_id` LIKE '$user'";
				$result=mysqli_query($connection,$query);
				
				$row = mysqli_fetch_array($result);
				$user_id=$row[1];
				$sql_latest="select predictions as latest_predictions,points as latest, ((SELECT matchday from matchday where NOW() between startDate and endDate)-1) as md from points_predictions_per_matchday where user_id like '$user_id' and matchday LIKE ((SELECT matchday from matchday where NOW() between startDate and endDate)-1)";
				$query_latest=mysqli_query($connection,$sql_latest);
				$result_latest=mysqli_fetch_array($query_latest);
				
				$msg="select count(*) as count from reply_message where (user_id like '$user_id' and seen like '0') or (user_id is null and '$user_id' NOT IN (Select seen_id from global_message_seen))
";
				$msgq=mysqli_query($connection,$msg);
				$msg_array=mysqli_fetch_array($msgq);
				if ($msg_array['count']>0){
					$pending="1";
				}
				else{
					$pending="0";
				}


				$rankQuery="CREATE TEMPORARY TABLE `t2` ( SELECT (@row:=@row+1) AS rank,u.* FROM user_points_matches as u, (select @row:=0) as t )";
				$resultRankQuery=mysqli_query($connection,$rankQuery);
				$getQuery="SELECT * FROM `t2` where user_id LIKE '$user_id'";
				$resultRankQuery=mysqli_query($connection,$getQuery);
				$rowRankQuery = mysqli_fetch_array($resultRankQuery);
				
				
				array_push($response,
				array('user_name'=>$row[0],
					'user_id'=>$row[1],
					'name'=>$row[2],
					
				'fav_club_id'=>$row[3],
				'indian'=>$row[4],
				'pt_match'=>$rowRankQuery['points_from_matches'],
				'points'=>$rowRankQuery['net_points'],
				'rank'=>$rowRankQuery['rank'],
				'matches'=>$rowRankQuery['predictions'],

				'lat'=>$result_latest['latest'],
				'lat_md'=>$result_latest['md'],
				'msg'=>$pending
				));
			
	$sql_freq="SELECT fr.user_id, (Select u.user_id from `user` as u where u.reg_id LIKE fr.user_id) as username, (Select ud.name from `user_details` as ud where ud.user_id LIKE fr.user_id) as name from friend_request as fr where friend_id LIKE '$user_id' and status LIKE 0";
	$query=mysqli_query($connection,$sql_freq);
	$requestArray=array();
	while ($result_freq=mysqli_fetch_array($query)){
		array_push($requestArray,array('id'=>$result_freq[0],'n'=>$result_freq[1],'un'=>$result_freq[2]));
	}	
 	$sql_friends="SELECT * FROM `friends` where user_id LIKE '$user_id' or friend_id LIKE '$user_id'";
	$query=mysqli_query($connection,$sql_friends);
	$friendsArray=array();
	while ($result_fr=mysqli_fetch_array($query)){
		if ($result_fr['user_id'] == $user_id){
			array_push($friendsArray,array('id'=>$result_fr['friend_id'],'un'=>$result_fr['friendname'],'n'=>$result_fr['name2']));
		}
		else{
			array_push($friendsArray,array('id'=>$result_fr['user_id'],'un'=>$result_fr['username'],'n'=>$result_fr['name']));
		}
	}	
		$data=array();
		array_push($data,array("u_d"=>$response));
		array_push($data,array("fr"=>$friendsArray));
		array_push($data,array("req"=>$requestArray));
			
		echo json_encode(array("data"=>$data));
			}
			else{
				echo "Invalid Login";
			}
?>
