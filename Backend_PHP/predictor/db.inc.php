<?php	
	$connection=mysqli_connect('localhost','root','');
	$db=mysqli_select_db($connection,'predictor');
 	$curl_url="http://localhost/predictor/";
 	date_default_timezone_set('Asia/Kolkata');

?>