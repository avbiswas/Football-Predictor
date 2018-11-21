<?php
include "db.inc.php";
if($connection)		
{              
			$name=urldecode($_GET['name']);
			$email=urldecode($_GET['mail']);
			$club=urldecode($_GET['fav_club']);
			$user_id=urldecode($_GET['username']);
			$password=urldecode($_GET['password']);

			$nationality=1;
			$query="SELECT * FROM  `user` WHERE  `email` LIKE  '$email'";	
			$result=mysqli_query($connection,$query);	
			$row=mysqli_num_rows($result);	
			if($row==0){
			$query="SELECT * FROM  `user` WHERE  `user_id` LIKE  '$user_id'";	
			$result=mysqli_query($connection,$query);	
			$row2=mysqli_num_rows($result);	
			if ($row2==0){
			$query1 ="INSERT INTO `predictor`.`user` (`reg_id`, `user_id`, `email`,`password`) VALUES (NULL, '$user_id', '$email',  '$password')";

				if(mysqli_query($connection,$query1))	
				{
					$q="Select reg_id from `user` where user_id like '$user_id'";
					$query=mysqli_query($connection,$query);
					$result=mysqli_fetch_array($query);
					$reg_id=$result['reg_id'];
					$query2 ="INSERT INTO `predictor`.`user_details` (`user_id`, `name`,`fav_club_id`,`indian`) VALUES ('$reg_id', '$name', '$club','nationality')";

					if(mysqli_query($connection,$query2))	
					{
						echo "Registration Successful";
					}
				else
				{
					echo "Error";
				}
			
				}
				else
				{
					echo "Error";
				}
			

			}
		else
		{
			echo "Username already taken";
		}
	}
			else
			{
				echo "Email ID exists";
			}
		
		}
		else
		{
			echo "Connection Error";
		}

?>