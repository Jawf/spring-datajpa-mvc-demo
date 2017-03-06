$(document).ready(function(){
	$.getJSON("http://localhost:8080/SpringDataJPA/add",{name:"嘻嘻"}, function(data){
		console.log(data.data);
	});
	$.post("http://localhost:8080/SpringDataJPA/add", {name:"哈哈"},function(data){
		console.log(data.data);
	});
});