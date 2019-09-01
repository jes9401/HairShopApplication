<?
  header("Content-Type:text/html;charset=utf-8");

  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

  $reviewCommentName = $_POST["reviewcommentName"];
  $reviewCommentContents = $_POST["reviewcommentContents"];

  $result = mysqli_query($con, "DELETE FROM reviewComment WHERE reviewCommentName = '$reviewCommentName' AND reviewCommentContents = '$reviewCommentContents'");

  $response = array();
  $response["success"] = true;

  echo json_encode($response);

mysqli_close($con);
?>
