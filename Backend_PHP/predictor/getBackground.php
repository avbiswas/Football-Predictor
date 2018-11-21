<?php
	include "db.inc.php";
	$imageID=urldecode($_GET['club']);
	//$articleID=13;
	$dir="backgrounds/";
	opendir($dir);
	$filename= "backgrounds/".$imageID;
	if (file_exists($filename.".jpg")) {
		$filename= $filename.".jpg";
 		}
 		else{ 
 			if (file_exists($filename.".png")){
 			$filename= $filename.".png";	
 			}
 		}
	$image=file_get_contents($filename);
	echo $image;
?>