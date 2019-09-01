<?
  header("Content-Type:text/html;charset=utf-8");

  $ID = $_GET["ID"];

  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");
  $result = mysqli_query($con, "SELECT ID, password, name, nickname, phone, hairshop  FROM HairShop_user WHERE ID = '$ID';"); // ID와 일치하는 닉네임을 가져옴
  $response = array();

  while($row = mysqli_fetch_array($result)){
    array_push($response, array("userID"=>$row[0], "userPassword"=>$row[1], "userName"=>$row[2], "userNickname"=>$row[3], "userPhone" => $row[4], "userHairshop" => $row[5]));
  }

  echo json_encode(array("response"=>$response));
  mysqli_close($con);

?>
