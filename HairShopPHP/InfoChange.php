
<?php
  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

  header("Content-Type:text/html;charset=utf-8");

  $ID = $_POST["ID"];
  $password = $_POST["password"];
  $name = $_POST["name"];
  $nickname = $_POST["nickname"];
  $phone = $_POST["phone"];
  $hairshop = $_POST["hairshop"];
  $temp = $_POST["temp"];

  $result = mysqli_query($con, "UPDATE HairShop_user SET password = '$password', name = '$name', nickname = '$nickname', phone = '$phone', hairshop = '$hairshop' WHERE ID = '$ID';");

  $response = array();
  $response["success"] = true;

  echo json_encode($response);


?>
