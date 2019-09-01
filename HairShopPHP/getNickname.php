<?
  header("Content-Type:text/html;charset=utf-8");

  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");


  $userID = $_GET["userID"];  // get방식으로 변수를 받아옴

//$userID = 'aaa';  //이게 맞음
// WHERE ID = $userID

  $result = mysqli_query($con, "SELECT nickname, type, gender FROM HairShop_user WHERE ID = '$userID';"); // ID와 일치하는 닉네임을 가져옴
  $response = array();

  while($row = mysqli_fetch_array($result)){
    array_push($response, array("nickname"=>$row[0], "type"=>$row[1], "gender"=>$row[2]));
  }

  echo json_encode(array("response"=>$response));
  mysqli_close($con);


?>
