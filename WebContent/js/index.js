// Waits for DOM to load up
var url = "http://localhost:9080/DemoWebApp/rest/service/"
$(document).ready(function(){
	
	//Populate DropDown Menu
	Menu("Room");
	Menu("Chair");
	
	var chairForm		= document.getElementById('chairform');
	var roomForm 		= document.getElementById('roomform');	
	var txtChair 		= document.getElementById('txtChair');
    var txtRoom 		= document.getElementById('txtRoom');
    var roomdropdown	= document.getElementById('roomdropdown');
    var chairdropdown	= document.getElementById('chairdropdown');
    
	
	$("#chair-submit").click(function(event){
		//Because of async behaviour, we need to block the browser for performing default action.
		event.preventDefault(); 
		var chair = document.getElementById('chair').value;	
		if (!chair.replace(/\s/g, '').length){	
			alert("No Name given to the chair you want to create. Please specify a name for the chair!")
			return;
		}
		var data = "{\"Name\" : \""+chair+"\", \"Attribute\": \"Chair\"}";
		$.ajax({
			url: url+"attribute/",
			data: data,
			contentType: 'application/json',
			dataType: 'text/html',
			type: 'POST',				
			
			success: function(data) {		   
				alert(data); 
			}	
		});	
			
		//User can create multiple chairs in one go.	
		document.getElementById('chair').value = ''; 
			
		//Call chair in success after removing bug. 
		Menu("Chair");
	});	 
	  
    $("#room-submit").click(function(event){	 
    	//Block the browser for performing default action because of async behaviour.
    	event.preventDefault(); 
			
		var room = document.getElementById('room').value;
		if (!room.replace(/\s/g, '').length){		
			alert("No Name given to the room you want to create. Please specify a name for the room!")
			return;
		}
		
		var data = "{\"Name\" : \""+room+"\", \"Attribute\": \"Room\"}";
		$.ajax({
			url: url+"attribute/",
			data: data,
			contentType: 'application/json',
			dataType: 'text/html',
			type: 'POST',			

			success: function(data) {		   
			alert(data); 
			}	
		});	
		
		//So that user can create muliple chairs in one go.
		document.getElementById('chair').value = ''; 
		
		//Update drop-down menu for room.	
		Menu("Room");
    });	 
    
    //Single function to get all RoomId/ChairId from db
    function Menu(name){    	
    	$.getJSON(url, {attribute:name}, results)  	
    }
    
    function results(data){        
		$.each(data, function(i,field){ 
			var option = $('<option />').val(field).text(field);
			
			//Populate the appropriate dropDown menu
			var dropDown = (i.charAt(0)=='C' ? "#Chairdropdown" : "#Roomdropdown");
		    $(dropDown).append(option);
		});
	}
 
    //Display window to enter name of resource to be created.
    $( "#create-room" ).click(function() {
    	 $("#roomform").show(100);
    });
     
    $( "#create-chair" ).click(function() {
       	 $("#chairform").show(100);
    });
    
     //Hide windows when mouse is clicked outside of the designated area of popup
    window.onclick = function(event){
    	if (event.target == chairForm) {
    		chairForm.style.display = "none";
    	}
    	else if(event.target == roomForm){
    		roomForm.style.display = "none";
    	}
    }
      
    //Populate appropriate textbox when an option from dropdown is selected
    chairdropdown.onchange = function(){
    	txtChair.value = this.value;
    }
      
    roomdropdown.onchange = function(){
    	txtRoom.value = this.value;
    }
})