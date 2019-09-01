<?php
	//require_once('dbConnect.php');
  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

	$sql = "select image from andimage";

	$res = mysqli_query($con,$sql);

	$result = array();

	while($row = mysqli_fetch_array($res)){
		array_push($result,array('url'=>$row['image']));
	}

	echo json_encode(array("result"=>$result));

	mysqli_close($con);

  ?>
