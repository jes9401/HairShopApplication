<?php
  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

  header("Content-Type:text/html;charset=utf-8");

  $pleaseTitle = $_POST["pleaseTitle"];
  $pleaseName = $_POST["pleaseName"];
  $pleaseDate = $_POST["pleaseDate"];
  $pleaseContents = $_POST["pleaseContents"];
  $pleaseNum = 0;
  $access = $_POST["access"];
  $pleaseImage = $_POST["pleaseImage"];
  $pleaseImage2 = $_POST["pleaseImage2"];


  $statement = mysqli_prepare($con, "INSERT INTO PleaseList VALUES (?, ?, ?, ?, ?, ?, ?, ?)"); //
  mysqli_stmt_bind_param($statement, "issssssi", $pleaseNum, $pleaseTitle, $pleaseName, $pleaseDate, $pleaseContents, $pleaseImage, $pleaseImage2, $access);
  mysqli_stmt_execute($statement);


  $response = array();
  $response["success"] = true;

  echo json_encode($response);


?>
