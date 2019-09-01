<?php
  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

  header("Content-Type:text/html;charset=utf-8");

  $reviewCommentIndex = $_POST["reviewCommentIndex"];
  $reviewCommentName = $_POST["reviewCommentName"];
  $reviewCommentDate = $_POST["reviewCommentDate"];
  $reviewCommentContents = $_POST["reviewCommentContents"];
  $CommentNum = 0;


  $statement = mysqli_prepare($con, "INSERT INTO reviewComment VALUES (?, ?, ?, ?, ?)"); //
  mysqli_stmt_bind_param($statement, "sssii", $reviewCommentContents, $reviewCommentDate, $reviewCommentName, $reviewCommentIndex,   $CommentNum);
  mysqli_stmt_execute($statement);


  $response = array();
  $response["success"] = true;

  echo json_encode($response);


?>
