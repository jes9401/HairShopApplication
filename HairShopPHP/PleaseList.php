<?
  header("Content-Type:text/html;charset=utf-8");

  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");
  $result = mysqli_query($con, "SELECT * FROM PleaseList ORDER BY pleaseDate DESC;");
  $response = array();

  while($row = mysqli_fetch_array($result)){
    array_push($response, array("pleaseNum"=>$row[0], "pleaseTitle"=>$row[1], "pleaseName"=>$row[2], "pleaseDate"=>$row[3], "pleaseContents" => $row[4],  "pleaseImage" => $row[5], "pleaseImage2" => $row[6], "access"=>$row[7]));
  }

  echo json_encode(array("response"=>$response));
  mysqli_close($con);


?>
