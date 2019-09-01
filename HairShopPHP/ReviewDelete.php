<?
  header("Content-Type:text/html;charset=utf-8");

  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

  $reviewName = $_POST["reviewName"];
  $reviewTitle = $_POST["reviewTitle"];

  $result = mysqli_query($con, "DELETE FROM ReviewList WHERE reviewName = '$reviewName' AND reviewTitle = '$reviewTitle'");


  $response = array();
  $response["success"] = true;

  echo json_encode($response);

mysqli_close($con);
?>
