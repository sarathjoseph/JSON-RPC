<?php
$GLOBALS['host']='localhost';
$GLOBALS['port']=8005;

$method=$_POST["method"];

if($method==="insertUser")
  insertUser();
if($method==="updateUser")
 updateUser();
if($method==="deleteUser")
 deleteUser();
if($method==="selectOnId")
 selectOnId();
if($method==="selectOnLastName")
 selectOnLastName();




function writeFile()
{
    
    $data = json_decode($_POST["data"]);
    $data = (array) $data;
    $fp   = fopen('file.csv', 'a');
    fputcsv($fp, $data);
    fclose($fp);
    echo "saved";
    
}

function writeDb()
{
    
    $data = json_decode($_POST["data"]);
    $data = (array) $data;
    $q    = '';  
    $con = mysqli_connect("localhost", "root", "root", "RIT");
    
    if (mysqli_connect_errno()) 
        echo "Failed to connect to MySQL: " . mysqli_connect_error();
    
    foreach ($data as $val) 
        $q = $q . '\'' . $val . '\'' . ',';
   
    $q = rtrim($q, ",");  
    $sql = "INSERT INTO person VALUES ($q)";
    echo $sql;
   
    if (!mysqli_query($con, $sql)) 
        die('Error: ' . mysqli_error($con));
  
    mysqli_close($con);
    
}

function insertUser(){

$data='{"method":"insert","data":'.$_POST["data"].'}';
webservice($data);

}


function updateUser(){

$data='{"method":"update","data":'.$_POST["data"].'}';
webservice($data);


}

function deleteUser(){

$data=array("method"=>"delete","data"=>$_POST["data"]);
$json=webservice(json_encode($data));
$decoded=json_decode($json,true);

if($decoded["status"]=="true")
echo '{"status":"true"}';

}

function selectOnLastName(){

$data=array("method"=>"selectOnLastName","data"=>$_POST["data"]);
$json=webservice(json_encode($data));
$decoded=json_decode($json,true);

/* check status flag. If status is true then return results */

if($decoded["status"]=="true")
echo json_encode($decoded["data"]);


}

function selectOnId(){

$data=array("method"=>"selectOnId","data"=>$_POST["data"]);
$json=webservice(json_encode($data));
$decoded=json_decode($json,true);

//check status flag. If status is true then return results

if($decoded["status"]=="true")
echo json_encode($decoded["data"]);

}

//function for reading data from excel 
function readData()
{
    
    $id = $_POST["data"];
    $f  = fopen("file.csv", "r");
    
    while (!feof($f)) {
        
	$data = fgetcsv($f, 1024);
        if ($id == $data[0]) {
            echo json_encode($data);
            break;
            
        }
    }
    
    fclose($f);
    
}

//function for reading data from db
function readDataDb()
{
    
    $id  = $_POST["data"];

    $con = mysqli_connect("localhost", "root", "root", "RIT");
    
    if (mysqli_connect_errno()) 
        echo "Failed to connect to MySQL: " . mysqli_connect_error();
    
     
   // $con=getConn();
    $result = mysqli_query($con, "SELECT * from person where Id=" . $id);
    if (!empty($result)) {
        while ($row = mysqli_fetch_array($result, MYSQLI_ASSOC))   
            echo json_encode($row);    
    			}

} 

function webservice($message){
$address=$GLOBALS['host'];
$port=$GLOBALS['port'];
$socket = socket_create(AF_INET, SOCK_STREAM, getprotobyname('tcp'));
socket_connect($socket, $address, $port);

$message .= chr(10);

$status = socket_sendto($socket, $message, strlen($message), MSG_EOF, $address, $port);
if($status !== FALSE)
{
    $message = '';
    $next = '';
    while ($next = socket_read($socket, 4096))
    {
        $message .= $next;
    }

    return $message;
}

else
{
    return "{'network':'error'}";
}

socket_close($socket);


}

?>

