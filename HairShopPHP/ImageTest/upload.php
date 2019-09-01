<?php
$con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

	if($_SERVER['REQUEST_METHOD']=='POST'){

		$image = $_POST['image'];

//		require_once('dbConnect.php');

		$sql ="SELECT id FROM andimage ORDER BY id ASC";

		$res = mysqli_query($con,$sql);

		$id = 0;

		while($row = mysqli_fetch_array($res)){
				$id = $row['id'];
		}

		$path = "uploads/$id.png";

		$actualpath = "http://kyu9341.cafe24.com/PhotoUpload/$path";

		$sql = "INSERT INTO photos (image) VALUES ('$actualpath')";

		if(mysqli_query($con,$sql)){
			file_put_contents($path,base64_decode($image));
			echo "Successfully Uploaded";
		}

		mysqli_close($con);
	}else{
		echo "Error";
	}
