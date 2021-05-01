// Ajax com JavaScript puro
/*
var xmlHttp;
console.log("teste");
if (window.XMLHttpRequest) {
	xmlHttp = new XMLHttpRequest;
}
else {
	xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
}

xmlHttp.onreadystatechange = function() {
	if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
		console.log(xmlHttp.responseText);
	}
}
xmlHttp.open("GET", "resources/sistema/js/arquivo.txt", true);

xmlHttp.send();
*/

//Ajax Com JQuery
$.ajax({
	method: "GET",
	url: "resources/sistema/js/arquivo.txt",
	
})
  .done(function(msg){
  	 alert("massa! " + msg);
  });