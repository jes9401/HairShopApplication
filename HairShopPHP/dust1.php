<?
header("Content-Type: text/html; charset=UTF-8");
$con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

$url = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?serviceKey=vlg%2BdzlBn6db3PbzT7%2Fv00dNoocv1h83Y9O%2F9NFBcLgVPxkzEJwz%2FXDqeJkqMxRrvsPodSUgVVH1KTJ5zBQHuw%3D%3D&numOfRows=25&pageNo=1&sidoName=%EC%84%9C%EC%9A%B8&searchCondition=HOUR";

$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, $url);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
curl_setopt($ch, CURLOPT_HEADER, false);
curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'GET');
curl_setopt($ch, CURLOPT_USERAGENT, $agent); // 헤더항목 추가
$response = curl_exec($ch);
$object = simplexml_load_string($response);
curl_close($ch);


//$cN = (string) $object->body->items->item[1]->cityName;
//echo $cN;
//$cityName = array[25];

for ($i = 0; $i <= 25; $i++) {
  $dataTime[$i] = (string) $object->body->items->item[$i]->dataTime;
//  echo $dataTime[$i] . "<br />\n";
}
for ($i = 0; $i <= 25; $i++) {
  $cityName[$i] = (string) $object->body->items->item[$i]->cityName;
//  echo $cityName[$i] . "<br />\n";
}
for ($i = 0; $i <= 25; $i++) {
  $pm10Value[$i] = (int) $object->body->items->item[$i]->pm10Value;
//  echo $pm10Value[$i] . "<br />\n";
}
for ($i = 0; $i <= 25; $i++) {
  $pm25Value[$i] = (int) $object->body->items->item[$i]->pm25Value;
//  echo $pm25Value[$i] . "<br />\n";
}

for($i=0; $i<=25; $i++){

  $result = mysqli_query($con, "UPDATE DustData SET dataTime = '$dataTime[$i]', cityName = '$cityName[$i]', pm10Value = $pm10Value[$i], pm25Value = $pm25Value[$i] WHERE cityName = '$cityName[$i]';");


}
$cityName1 = $_GET["cityName"];
//$cityName1 = '강동구';

$result = mysqli_query($con, "SELECT dataTime, pm10Value, pm25Value FROM DustData WHERE cityName = '$cityName1';"); // cityName과 일치하는 닉네임을 가져옴
$response = array();

while($row = mysqli_fetch_array($result)){
  array_push($response, array("dataTime"=>$row[0], "pm10Value"=>$row[1], "pm25Value"=>$row[2]));
}

echo json_encode(array("response"=>$response));
mysqli_close($con);


/*
// 입력
  $statement = mysqli_prepare($con, "INSERT INTO DustData VALUES (?, ?, ?, ?)"); //
  mysqli_stmt_bind_param($statement, "ssii", $dataTime[$i], $cityName[$i], $pm10Value[$i], $pm25Value[$i]);
  mysqli_stmt_execute($statement);
*/

/*
foreach ($cityName as $cN) {
  // code...
}
*/

//var_dump($value) ;
/*
echo '<pre>';
var_dump($object);
echo '</pre>';
*/

//echo $object->response->body[0]->items[0]->item as $item;
/*
foreach($parser->document->body[0]->items[0]->item as $item)
{
	$i=0;
		echo $item->totalCount[$i]->tagData;
?>
<li <? if($areaCode==$item->code[$i]->tagData) { ?>class="active"<? } ?>><a href='<?=$PHP_SELF?>?areaCode=<?=$item->code[$i]->tagData?>&contentTypeId=<?=$contentTypeId?>'><p><?=$item->name[$i]->tagData?></p></a></li>
<?
$i++;

}

*/
?>
