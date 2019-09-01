<?
  header("Content-Type:text/html;charset=utf-8");

  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");


  $nickname = $_GET["nickname"];  // get방식으로 변수를 받아옴

//$userID = 'aaa';  //이게 맞음
// WHERE ID = $userID

  $result = mysqli_query($con, "SELECT hairshopTelnum FROM HairShop_user WHERE nickname = '$nickname' AND type = 'designer';");
  $response = array();
  while($row = mysqli_fetch_array($result)){
    array_push($response, array("hairshopTelnum"=>$row[0]));
  }

  echo json_encode(array("response"=>$response));
  mysqli_close($con);


?>
