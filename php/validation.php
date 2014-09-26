<?php

function validation($data,$string)
{
    $regex = array(
        "FirstName" => '/^[a-zA-Z]+([\s][a-zA-Z]+){0,2}$/',
        "LastName" => '/^[a-zA-Z]+$/',
         "MiddleInitial" => '/^([a-zA-Z]+$|^$)/',
         "Street"=> '/^[0-9]+\s[a-zA-Z\s]+$/',
        "City"  => '/^[a-zA-Z]+([\s][a-zA-Z]+){0,2}$/',
        "State" => '/^[a-zA-Z]{2}$/',
        "Zip"   => '/^[0-9]{5}$/',
        "Phone" => '/(^[0-9]{3}-[0-9]{3}-[0-9]{4}$|^[0-9]{10}$|^[(]{1}[0-9]{3}[)]{1}[0-9]{3}-[0-9]{4}$)/',
        "Email" => '/^[a-zA-Z_0-9]+@[a-zA-Z]+[\.][a-zA-Z]{2,3}$/',
        "CardNumber"  => array("Visa"=>'/^4[0-9]{3}$/',"Mastercard"=>'/^5[1-5]([0-9]{2})$/',"AmericanExpress"=>'',"num"=>'/^[0-9]{4}$/')
         );

  return ($data=="CardNumber")?preg_match($regex[$data][$_POST["ctype"]],trim($string)):preg_match($regex[$data],trim($string));
}

echo validation($_POST["name"],$_POST["value"]);

?>
