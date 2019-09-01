<?
  header("Content-Type:text/html;charset=utf-8");

  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

  $commentName = $_POST["commentName"];
  $commentContents = $_POST["commentContents"];

  $result = mysqli_query($con, "DELETE FROM Comment WHERE commentName = '$commentName' AND commentContents = '$commentContents'");

  $response = array();
  $response["success"] = true;

  echo json_encode($response);

mysqli_close($con);
?>
