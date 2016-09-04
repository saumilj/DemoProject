// Waits for DOM to load up
var url = "http://localhost:9080/DemoWebApp/rest/service/"
$(document).ready(function(){

	var form = document.getElementById('myform');
	// Test call and response
	$("#btnGet").click(function(){
		
		$.getJSON(url, results)
	});

	function results(data){
    
		$.each(data, function(i,field){ // modify: make as callback
			alert(i+":"+field);
		});
	}
	
	$("#chair-submit").click(function(e){
			 
			e.preventDefault(); // Because of async behaviour, we need to block the browser for performing default action.
			var room = document.getElementById('chair').value;			
			var data = "{\"message\" : \""+room+"\"}";
			$.ajax({
				url : url,
				data : data,
				contentType : 'application/json',	
				type : 'POST',			
				// Modify
				success: function(data) {		   
				alert(data); 
				}	
			});	
			// Since we stopped browser from performing default action, we need to explicitly set hide the form.
			//form.style.display = "none"; // If we don't want user to create mulitple chairs in one go.
			document.getElementById('chair').value = ''; // So that user can create muliple chairs in one go.	
	 });
	 
	 
    $( "#create-user" ).click(function() {
       	 $("#myform").show(100);
     });
    
      window.onclick = function(event) {
    	if (event.target == form) {
    		form.style.display = "none";
    	}
    }
})