// Waits for DOM to load up
var url = "http://localhost:9080/DemoWebApp/rest/service/"
$(document).ready(function(){
	
	//Populate DropDown Menu
	roomMenu();
	chairMenu();
	
	var chairForm		= document.getElementById('chairform');
	var roomForm 		= document.getElementById('roomform');	
	var txtChair 		= document.getElementById('txtChair');
    var txtRoom 		= document.getElementById('txtRoom');
    var roomdropdown	= document.getElementById('Roomdropdown');
    var chairdropdown	= document.getElementById('Chairdropdown');
    
	$("#associate").click(function(event){
		
		var data = "{\"Chair\" : \""+txtChair.value+"\", \"Room\": \""+txtRoom.value+"\"}";
		$.ajax({
			url: url+"associate/",
			data: data,
			contentType: 'application/json',
			dataType: 'application/json',
			type: 'POST',				
			success: function(data) {		   
				alert(data); 
			}	
		});	
	})
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
			url: url+"chair/",
			data: data,
			contentType: 'application/json',
			dataType: 'json',
			type: 'POST',				
			success: function(data) {		   
				location.reload();
				//updateChairMenu(chair);
			}	
		});	
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
			url: url+"room/",
			data: data,
			contentType: 'application/json',
			dataType: 'json',
			type: 'POST',			

			success: function(data) {		   
				location.reload();
			}	
		});	
    });	 
    
    function roomMenu(){    	
    	$.getJSON(url+"room", roomresults)  	
    }
    
    function roomresults(data){        
		$.each(data, function(i,field){ 			
			var option = $('<option />').val(field).text(field);			
			//Populate the appropriate dropDown menu
			var dropDown = (i.charAt(0)=='C' ? "#Chairdropdown" : "#Roomdropdown");	
			// This is appending. But we just need to add the last entry added.
		    $(dropDown).append(option);
		});
	}
    function chairMenu(){    	
    	$.getJSON(url+"chair", chairresults)  	
    }
    
    function chairresults(data){        
		$.each(data, function(i,field){ 			
			var option = $('<option />').val(field).text(field);			
			//Populate the appropriate dropDown menu
			var dropDown = (i.charAt(0)=='C' ? "#Chairdropdown" : "#Roomdropdown");	
			// This is appending. But we just need to add the last entry added.
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




//    function updateChairMenu(name){    	
//    	$.getJSON(url+"chair",{chair:name},function(data){		
//    		var option = $('<option />').val(data).text(data);			
//			$("#Chairdropdown").append(option);
//    	})  	
//    }
//    
//    function updateRoomMenu(name){    	
//    	$.getJSON(url+"room",{room:name},function(data){		
//    		var option = $('<option />').val(data).text(data);			
//			$("#Roomdropdown").append(option);
//    	})  	
//    }
//    
