<?
  header("Content-Type:text/html;charset=utf-8");

  $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");


  $commentIndex = $_GET["commentIndex"];  // get방식으로 변수를 받아옴


  $result = mysqli_query($con, "SELECT * FROM Comment WHERE commentIndex = $commentIndex ORDER BY commentDate ASC;"); // 댓글이 달린 리스트의 인덱스에서 날짜순으로 데이터를 가져옴
  $response = array();

  while($row = mysqli_fetch_array($result)){
    array_push($response, array("commentContents"=>$row[0], "commentDate"=>$row[1], "commentName"=>$row[2]));
  }

  echo json_encode(array("response"=>$response));
  mysqli_close($con);


?>
