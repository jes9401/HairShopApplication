
<?php
  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

  header("Content-Type:text/html;charset=utf-8");

    $pleaseNum = $_POST["pleaseNum"];
    $pleaseTitle = $_POST["pleaseTitle"];
    $pleaseDate = $_POST["pleaseDate"];
    $pleaseContents = $_POST["pleaseContents"];
    $pleaseImage = $_POST["pleaseImage"];
    $pleaseImage2 = $_POST["pleaseImage2"];
    $access = $_POST["access"];

  $result = mysqli_query($con, "UPDATE PleaseList SET pleaseTitle = '$pleaseTitle', pleaseDate = '$pleaseDate', pleaseContents = '$pleaseContents', pleaseImage = '$pleaseImage', pleaseImage2 = '$pleaseImage2', access = $access WHERE pleaseNum = $pleaseNum;");

  $response = array();
  $response["success"] = true;

  echo json_encode($response);


?>
