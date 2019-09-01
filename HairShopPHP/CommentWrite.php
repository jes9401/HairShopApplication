<?php
  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

  header("Content-Type:text/html;charset=utf-8");

  $commentIndex = $_POST["commentIndex"];
  $commentName = $_POST["commentName"];
  $commentDate = $_POST["commentDate"];
  $commentContents = $_POST["commentContents"];
  $CommentNum = 0;


  $statement = mysqli_prepare($con, "INSERT INTO Comment VALUES (?, ?, ?, ?, ?)"); //
  mysqli_stmt_bind_param($statement, "sssii", $commentContents, $commentDate, $commentName, $commentIndex, $CommentNum);
  mysqli_stmt_execute($statement);


  $response = array();
  $response["success"] = true;

  echo json_encode($response);


?>
