<?php
if(isset($_POST['idx']) && $_POST['idx']>0){
    $idx=$_POST['idx'];

    $file_path='./photos/'.$idx.'.jpg'; //이미지화일명은 인덱스번호로 지정
    if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $file_path)) {
        $result = array("result" => "success");
    } else{
        $result = array("result" => "error");
    }
    echo json_encode($result);
}
?>
