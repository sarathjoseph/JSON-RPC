<html>
   <head>
     
      <link rel="stylesheet" type="text/css" href="index.css">
      
   </head>
  	 <body>
	UID <br/>
	<input type="text" id="Id" name="Id" /><input type="button" value="Go" onclick='fetchData("Id")'/>
	
	<br/><br/>	
	<div id="756">
       <form>
        <div class="myform">
      First Name: <input type="text" id="FirstName" name="FirstName" oninput='validate(name,this)'/>
      <br/>
	Middle Initial: <input type="text" id="MiddleInitial" name="MiddleInitial" oninput='validate(name,this)'/>
      <br/>
      Last Name: <input type="text" id="LastName" name="LastName" oninput='validate(name,this)'/>
      <br/>   
      Street: <input type="text" id="Street" name="Street" oninput='validate(name,this)'/>
      <br/>
      City: <input type="text" id="City" name="City" oninput='validate(name,this)'/>
      <br/>
      State: <input type="text" id="State" name="State" oninput='validate(name,this)'/>
      <br/>
      Zip: <input type="text" id="Zip" name="Zip" oninput='validate(name,this)'/>
      <br/> 
      Phone Number: <input type="text" id="Phone" name="Phone" oninput='validate(name,this)'/>
      <br/>
      Email: <input type="text" id="Email" name="Email" oninput='validate(name,this)'/>
      <br/>
      </div>  
      Card Type :<input type="radio" id="CreditCardType" name="CreditCardType" value="American Express"/>American Express
      <input type="radio" name="CreditCardType" value="Mastercard"/>Mastercard
      <input type="radio" name="CreditCardType" value="Visa"/>Visa
      <br/><br/>
      Card Num : <input type="text" id="1" name="CardNumber" maxlength="4" oninput='cardVal(this)'/><input type="text" oninput='cardVal(this)' 		id="2" name="CardNumber" maxlength="4"/><input id="3" oninput='cardVal(this)' type="text" name="CardNumber" maxlength="4"/>
      <input id="4" oninput='cardVal(this)' type="text" name="CardNumber" maxlength="4"/>
      
	
	</form>
	</div>
	<input type="button" onclick="save('insert')" value="submit"/>
	<input type="button" onclick="save('update')" value="update"/>
	<input type="button" onclick="removeUser()" value="delete"/>
	<input type="button" value="Lastname" onclick='fetchData("LastName")'/>
	
   
   </body>
	 <script src="index.js"></script>
	 <script>
        var ctype = "num";
 	var card = document.getElementsByName("CreditCardType");
 	var c = document.getElementById("756").getElementsByTagName("input");
 	var creditcard = document.getElementById("1").name;
 	var credittype = document.getElementById("CreditCardType").name;
 	var Id =document.getElementById("Id").name;
      </script>
</html>
