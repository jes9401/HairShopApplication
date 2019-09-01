<?php
$con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

$ID = $_POST["ID"];

$statement = mysqli_prepare($con, "SELECT * FROM HairShop_user WHERE ID = ?"); // ID 중복확인
mysqli_stmt_bind_param($statement, "s", $ID);
mysqli_stmt_execute($statement);
mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement, $ID);

$response = array();
$response["success"] = true;

while(mysqli_stmt_fetch($statement)){
  $response["success"] = false;
  $response["ID"] = $ID;
}

echo json_encode($response);



 ?>
