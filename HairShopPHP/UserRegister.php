
<?php
  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

  header("Content-Type:text/html;charset=utf-8");

  $ID = $_POST["ID"];
  $password = $_POST["password"];
  $name = $_POST["name"];
  $gender = $_POST["gender"];
  $nickname = $_POST["nickname"];
  $phone = $_POST["phone"];


  $type = 'user';
  $hairshop = 'user';
  $permission = 1;
  $hairshopTelnum = 'user';

  $statement = mysqli_prepare($con, "INSERT INTO HairShop_user VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); //
  mysqli_stmt_bind_param($statement, "sssssssssi", $ID, $password, $name, $gender, $nickname, $phone, $hairshop, $hairshopTelnum, $type, $permission);
  mysqli_stmt_execute($statement);


  $response = array();
  $response["success"] = true;

  echo json_encode($response);


?>
