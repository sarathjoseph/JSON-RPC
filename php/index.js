
 function validate(name, obj) {

     var elem = document.getElementById(name);
     var value = obj.value;
     var url = "validation.php"
     var xmlhttp = getXMLHttpRequestObject()
     xmlhttp.open("POST", url, true);
     xmlhttp.onreadystatechange = function () {

         if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
             obj.style.backgroundColor = (xmlhttp.responseText == 0) ? 'red' : 'white';

     };

     xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
     if (name === creditcard)
         xmlhttp.send("name=" + name + "&value=" + value + "&ctype=" + ctype);
     else
         xmlhttp.send("name=" + name + "&value=" + value);

 }

 function getXMLHttpRequestObject() {

     if (window.XMLHttpRequest)
         return (new XMLHttpRequest());
     else
         return (new ActiveXObject("Microsoft.XMLHTTP"));

 }

 function cardVal(obj) {
     var cardtype;
     for (i = 0; i < card.length; i++) {

         if (card[i].checked) {
             cardtype = card[i].value;
             ctype = (obj.id == 1) ? cardtype : "num";
         }
     }

     if (cardtype == undefined) {
         alert("Please select a card type");
         obj.value = "";
     }

     nextid = parseInt(obj.id) + 1;
     validate(creditcard, obj);

     if (obj.value.length === 4 && nextid !== 5)
         document.getElementById(nextid).focus();

 }

 function save(action) {

     var sel = false;
     var count = 0;
     var cardtype;
     var cardnum = "";

     for (i = 0; i < card.length; i++) {
         if (card[i].checked) {
             sel = true;
             cardtype = card[i].value;
         }
     }
     if (!sel) {
         alert("Please select a card type");
         return;
     }
     a = [];
     for (i = 0; i < c.length; i++) {
         if (c[i].name == creditcard) {
             a.push(c[i]);
             cardnum += c[i].value
         } else {
             if (c[i].name != credittype)
                 validate(c[i].name, c[i]);

         }
     }


     for (k = 0; k < a.length; k++)
         cardVal(a[k]);

     setTimeout(function () {


         for (i = 0; i < c.length; i++) {

             if (c[i].style.backgroundColor == 'red')
                 count += 1
         }
         if (count)
             console.log("failed");
         else
             storeVal(c, cardnum, cardtype,action);


     }, 600);

 }


 function storeVal(obj, cardnum, type, action) {
     json = {};
     var url="client.php";
     var method;
     json[Id] = document.getElementById(Id).value;

     for (i = 0; i < obj.length; i++) {


         if (obj[i].name !== credittype && obj[i].name != creditcard)
             json[obj[i].name] = obj[i].value;

     }
     
     json[credittype] = type;
     json[creditcard] = cardnum;
     json = JSON.stringify(json);
     console.log(json);

     if(action==="insert")
     method ="insertUser";
     if(action==="update")
     method="updateUser";

     var xmlhttp = getXMLHttpRequestObject()
     xmlhttp.open("POST", url, true);
     xmlhttp.onreadystatechange = function () {

         if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
             console.log(xmlhttp.responseText);

     };
 
     xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
     xmlhttp.send("data=" + json+"&method=" + method);



 }

 function removeUser(){

   obj = document.getElementById(Id);
   var url="client.php";
   var xmlhttp = getXMLHttpRequestObject()
     xmlhttp.open("POST", url, true);
     xmlhttp.onreadystatechange = function () {

         if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
             console.log(xmlhttp.responseText);

     };

     xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
     xmlhttp.send("data=" + obj.value+"&method=deleteUser");



 }




 function fetchData(param) {
	var url="client.php";
	var method;
	if(param==="Id"){
         method="selectOnId";
	 obj = document.getElementById(Id);
        }
        if(param==="LastName"){
         method="selectOnLastName";
	 obj = document.getElementById("LastName");
	}
     
    // var url = "readfile.php";
     var xmlhttp = getXMLHttpRequestObject();
     xmlhttp.open("POST", url, true);
     xmlhttp.onreadystatechange = function () {

         if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
             if (xmlhttp.responseText.length > 0){
			console.log(xmlhttp.responseText);
                        loadData(JSON.parse(xmlhttp.responseText));

                    }
             else
                 alert("No Data found!");


         }

     };

     xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
     xmlhttp.send("data=" + obj.value+"&method="+method);


 }

 function slicer(a, n) {
     b = new Array();
     var j = 0;
     a = a.split("");
     for (i = 0; i < a.length; i++) {
         b[j] = a.slice(i, i + n);
         b[j] = b[j].join("");
         i += n - 1;
         j++;
     }
     return b;

 }


 function loadData(obj) {

     for (var key in obj) {
         if (key !== credittype && key !== creditcard)
             document.getElementById(key).value = obj[key];
     }

     for (i = 0; i < card.length; i++) {
         if (card[i].value === obj[credittype])
             card[i].checked = true;
     }

     cardarray = slicer(obj[creditcard], 4);
     var temp = document.getElementsByName(creditcard);

     for (s = 0; s < cardarray.length; s++)
         temp[s].value = cardarray[s];
 }
