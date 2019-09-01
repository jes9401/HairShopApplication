<?php

$con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");


	$imagedevice=$_GET['image'];
	echo $imagedevice;
	$data=base64_decode($imagedevice);
	header("Content-Type:text/html;charset=utf-8");

	echo $data;
	//$escaped_values=mysql_real_escape_string($data);
 $escaped_values=mysql_real_escape_string($data);
	echo $escaped_values;

	$r=mysql_query("insert into imagetest (IMAGE) value('$data')",$con);


	echo json_encode($r);
	mysql_close($con);

/*
?>

<?php

$base=$_REQUEST['image'];
echo $base;
// base64 encoded utf-8 string
$binary=base64_decode($base);
// binary, utf-8 bytes
header('Content-Type: bitmap; charset=utf-8');
// print($binary);
//$theFile = base64_decode($image_data);
$file = fopen('test.jpg', 'wb');
fwrite($file, $binary);
fclose($file);
echo '<img src=test.jpg>';
?>
*/
