
<?php
  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

  header("Content-Type:text/html;charset=utf-8");

    $reviewNum = $_POST["reviewNum"];
    $reviewTitle = $_POST["reviewTitle"];
    $reviewDate = $_POST["reviewDate"];
    $reviewContents = $_POST["reviewContents"];
    $reviewImage = $_POST["reviewImage"];
    $reviewImage2 = $_POST["reviewImage2"];

    $reviewRate = $_POST["reviewRate"];

  $result = mysqli_query($con, "UPDATE ReviewList SET reviewTitle = '$reviewTitle', reviewDate = '$reviewDate', reviewContents = '$reviewContents', reviewImage = '$reviewImage', reviewImage2 = '$reviewImage2',reviewRate = $reviewRate WHERE reviewNum = $reviewNum;");

  $response = array();
  $response["success"] = true;

  echo json_encode($response);


?>
