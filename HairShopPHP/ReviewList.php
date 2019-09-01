<?
  header("Content-Type:text/html;charset=utf-8");

  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");
  $result = mysqli_query($con, "SELECT * FROM ReviewList ORDER BY reviewDate DESC;");
  $response = array();

  while($row = mysqli_fetch_array($result)){
    array_push($response, array("reviewNum"=>$row[0], "reviewTitle"=>$row[1], "reviewName"=>$row[2], "reviewDate"=>$row[3], "reviewContents" => $row[4], "reviewImage"=>$row[5], "reviewImage2"=>$row[6],"reviewRate"=>$row[7]));
  }

  echo json_encode(array("response"=>$response));
  mysqli_close($con);


?>
