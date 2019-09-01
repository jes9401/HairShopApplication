<?php
  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

  header("Content-Type:text/html;charset=utf-8");

  $reviewTitle = $_POST["reviewTitle"];
  $reviewName = $_POST["reviewName"];
  $reviewDate = $_POST["reviewDate"];
  $reviewContents = $_POST["reviewContents"];
  $reviewRate = $_POST["reviewRate"];
  $reviewImage = $_POST["reviewImage"];
  $reviewImage2 = $_POST["reviewImage2"];

  $reviewNum = 0;


  $statement = mysqli_prepare($con, "INSERT INTO ReviewList VALUES (?, ?, ?, ?, ?, ?, ?, ?)"); //
  mysqli_stmt_bind_param($statement, "issssssi", $reviewNum, $reviewTitle, $reviewName, $reviewDate, $reviewContents, $reviewImage, $reviewImage2, $reviewRate);
  mysqli_stmt_execute($statement);


  $response = array();
  $response["success"] = true;

  echo json_encode($response);


?>
