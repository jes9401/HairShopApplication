<?php

header("Content-Type:text/html;charset=utf-8");
$con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");


	$result=mysql_query("select * from imagetest",$con);
	$cnt=0;
	$arr=array();

	while($row=mysql_fetch_array($result)){

		$count=$cnt;
		$arr[$count]['IMAGE']=base64_encode($row[1]);
		$arr[$count]['NO']=$row[0];
		$cnt++;
	}

	print(json_encode($arr));
	mysql_close($con);
?>
