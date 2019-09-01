<?php

 $con = mysqli_connect("localhost", "kyu9341", "asas120700", "kyu9341");

  $ID = $_POST["id"];
  $password = $_POST["pwd"];
  $name = $_POST["yourname"];
  $gender = $_POST["sex"];
  $nickname = $_POST["addr"];
  $phone = $_POST["m2"];

//  $test = mysqli_query($con, "INSERT INTO HairShop_user (ID, passwor, name, gender, nickname, phone) VALUES ($ID, $password,$name, $gender, $nickname ,$phone)");

    $statement = mysqli_prepare($con, "INSERT INTO HairShop_user VALUES (?, ?, ?, ?, ?, ?)"); //
    mysqli_stmt_bind_param($statement, "ssssss", $ID, $password, $name, $gender, $nickname, $phone);
    mysqli_stmt_execute($statement);

 echo "post . <br />";

 echo "id ............. {$ID} <br />";
 echo "name ............. {$name} <br />";
 echo "password ............. {$password} <br />";
 echo "confirm password ............. {$_POST[pwd2]} <br />";
 echo "phone number.......... {$_POST[m1]} -{$phone}-{$_POST[m3]} <br />";
 echo "sex ............. {$gender} <br />";
 echo "address ............. {$nickname } <br />";
 echo "hobby / computer ............. {$_POST[com]} <br />";
 echo "hobby / sports ............. {$_POST[sports]} <br />";
 echo "hobby / shopping ............. {$_POST[shop]} <br />";
 echo "hobby / movie ............. {$_POST[mov]} <br />";

//mysqli_stmt_execute($test);


// $statement = mysqli_prepare($con, "INSERT INTO HairShop_user VALUES ($ID, $password,$name, $gender, $nickname ,$phone)"); //
 //mysqli_stmt_bind_param($statement, "ssssss", $ID, $password, $name, $gender, $nickname, $phone);
 //mysqli_stmt_execute($statement);


/*?>
<?php
$conn = mysqli_connect("localhost", "root", "111111", "opentutorials");
mysqli_query($conn, "
    INSERT INTO topic (title, description, created) VALUES ( 'MySQL','MySQL is ....', NOW() )");
?>*/
