// Waits for DOM to load up
var url = "http://localhost:9080/DemoWebApp/rest/service/"
$(document).ready(function(){

	$("#btnGet").click(function(){
		
		$.getJSON(url, function(data){
            
			$.each(data, function(i,field){ // modify: make as callback
                alert(i+":"+field);
            });
        });
	});
	
	$("#btnPost").click(function(){
		
		$.ajax({
			url : url,
			data : "{\"message\" : \"hi\"}",
			dataType : 'application/json',	
			type : 'POST',
			headers: {
				"Content-Type": "application/json"
			},
			// Modify
			success : function(data) {		   
				alert(data); 
			}	
		});
	});	
})