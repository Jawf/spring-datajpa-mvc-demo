$(document).ready(function(){
	$.getJSON("http://localhost:8080/SpringMvcDataJPA/findall", function(data){
		console.log(data.data);
	});
});

function initRecords(){
	$.getJSON("http://localhost:8080/SpringMvcDataJPA/add",{name:"企大"}, function(data){
		console.log(data.data);
	});
	$.post("http://localhost:8080/SpringMvcDataJPA/add", {name:"乐才"},function(data){
		console.log(data.data);
	});
}