// Waits for DOM to load up
var url = "http://localhost:9080/DemoWebApp/rest/service/"
$(document).ready(function(){

	var chairForm = document.getElementById('chairform');
	var roomForm = document.getElementById('roomform');
	
	// Test get call and response
	$("#btnGet").click(function(){
		
		$.getJSON(url, results)
	});

	function results(data){
    
		$.each(data, function(i,field){ 
			alert(i+":"+field);
		});
	}
	
	$("#chair-submit").click(function(event){
			 
			event.preventDefault(); // Because of async behaviour, we need to block the browser for performing default action.
			var room = document.getElementById('chair').value;			
			var data = "{\"message\" : \""+room+"\"}";
			$.ajax({
				url : url+"chair/",
				data : data,
				contentType : 'application/json',
				dataType : 'text/html',
				type : 'PUT',			
				
				// Modify
				success: function(data) {		   
				alert(data); 
				}	
			});	
			// Since we stopped browser from performing default action, we need to explicitly set hide the form.
			//form.style.display = "none"; // If we don't want user to create mulitple chairs in one go.
			
			document.getElementById('chair').value = ''; // So that user can create muliple chairs in one go.	
	 });	 
	 
    $( "#create-chair" ).click(function() {
       	 $("#chairform").show(100);
     });
    
    $("#room-submit").click(function(event){
		 
		event.preventDefault(); // Because of async behaviour, we need to block the browser for performing default action.
		// Include empty constraint	
		var room = document.getElementById('room').value;		
		var data = "{\"Name\" : \""+room+"\", \"Attribute\": \"Room\"}";
		$.ajax({
			url : url+"room/",
			data : data,
			contentType : 'application/json',
			dataType : 'text/html',
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
 
     $( "#create-room" ).click(function() {
    	 $("#roomform").show(100);
     });
    
      window.onclick = function(event) {
    	if (event.target == chairForm) {
    		chairForm.style.display = "none";
    	}
    	else if(event.target == roomForm){
    		roomForm.style.display = "none";
    	}
      }
      var mytextbox = document.getElementById('mytext');
      var mydropdown = document.getElementById('dropdown');

      mydropdown.onchange = function(){
           mytextbox.value = this.value;
      }
})