<?
  header("Content-Type:text/html;charset=utf-8");

  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

  $pleaseName = $_POST["pleaseName"];
  $pleaseTitle = $_POST["pleaseTitle"];

  $result = mysqli_query($con, "DELETE FROM PleaseList WHERE pleaseName = '$pleaseName' AND pleaseTitle = '$pleaseTitle'");

/*
  $statement = mysqli_prepare($con, "DELETE FROM PleaseList WHERE pleaseName = '$pleaseName' AND pleaseTitle = '$pleaseTitle'");
  mysqli_stmt_bind_param($statement, "ss", $pleaseName, $pleaseTitle);
  mysqli_stmt_execute($statement);
*/
  $response = array();
  $response["success"] = true;

  echo json_encode($response);

mysqli_close($con);
?>
