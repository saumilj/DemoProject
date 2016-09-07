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
		
		if (!txtChair.value.replace(/\s/g, '').length){	
			alert("No Name given to the chair. Please specify a name for the chair!");
			return;
		}
		if (!txtRoom.value.replace(/\s/g, '').length){	
			alert("No Name given to the room. Please specify a name for the room!");
			return;
		}
		var data = "{\"Chair\" : \""+txtChair.value+"\", \"Room\": \""+txtRoom.value+"\"}";
		$.ajax({
			url: url+"associate/",
			data: data,
			contentType: 'application/json',
			dataType: 'json',
			type: 'POST',				
			success: function(data) {		   
				alert(data.response); 
			},
		error: function(data){
			alert(data.responseText);
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
		var data = "{\"Name\" : \""+chair+"\"}";
		$.ajax({
			url: url+"chair/",
			data: data,
			contentType: 'application/json',
			dataType: 'json',
			type: 'POST',
			
			success: function(data) {
				alert(data.response);
				location.reload();
			},
			error: function(data){
			alert(data.responseText);
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
		var data = "{\"Name\" : \""+room+"\"}";
		$.ajax({
			url: url+"room/",
			data: data,
			contentType: 'application/json',
			dataType: 'json',
			type: 'POST',			
			success: function(data) {
				alert(data.response);
				location.reload();
			},
			error: function(data){
				alert(data.responseText);
			}
		});	
    });
    
    $("#report").click(function(event){
    	
    	$.getJSON(url+"report", report);
    	
    })
    
    function report(data){
    	
    	document.getElementById('table').style.display = "block";
    	var div = document.getElementById('table');
    	$.each(data, function(i,field){
    		div.innerHTML = div.innerHTML + "<tr><td>"+i+"</td><td>"+field+"</td></tr>";
    	})
    }
    
    function roomMenu(){    	
    	$.getJSON(url+"room", roomresults)  	
    }
    
    function roomresults(data){        
		$.each(data, function(i,field){ 			
			var option = $('<option />').val(field).text(field);			
		    $("#Roomdropdown").append(option);
		});
	}
    
    function chairMenu(){    	
    	$.getJSON(url+"chair", chairresults)  	
    }
    
    function chairresults(data){        
		$.each(data, function(i,field){ 			
			var option = $('<option />').val(field).text(field);			
		    $("#Chairdropdown").append(option);
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
