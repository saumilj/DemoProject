// Waits for DOM to load up

//var url = "http://chairallocation.mybluemix.net/DemoWebApp/rest/service/"	
var url = "http://localhost:9080/DemoWebApp/rest/service/"
	
	
	var mainObj = {};
	mainObj.roomMenu =  function(){	
	}

$(document).ready(function(){
	
	var chairForm		= document.getElementById('chairform');
	var roomForm 		= document.getElementById('roomform');	
	var txtChair 		= document.getElementById('txtChair');
    var txtRoom 		= document.getElementById('txtRoom');
    var roomdropdown	= document.getElementById('Roomdropdown');
    var chairdropdown	= document.getElementById('Chairdropdown');
    
  //Populate DropDown Menu
	roomMenu();
	chairMenu();
    
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
    	
	$("#chair-submit").click(function(event){
		//Because of async behaviour, we need to block the browser for performing default action.
		event.preventDefault(); 
    	
		var chair = document.getElementById("chair").value;
		if (!chair.replace(/\s/g, '').length){		
			alert("No Name given to the chair you want to create. Please specify a name for the chair!")
			return;
		}
		if (!chair.replace(/\s/g, '').length){		
			alert("No Name given to the chair you want to create. Please specify a name for the chair!")
			return;
		}
		
		var regex = new RegExp("^[a-zA-Z0-9]+$");
		if (!regex.test(chair)) {
			alert("Use only alphanumerics characters in name!");
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
    	
		var room = document.getElementById("room").value;
		if (!room.replace(/\s/g, '').length){		
			alert("No Name given to the room you want to create. Please specify a name for the room!")
			return;
		}
		if (!room.replace(/\s/g, '').length){		
			alert("No Name given to the room you want to create. Please specify a name for the room!")
			return;
		}
		
		var regex = new RegExp("^[a-zA-Z0-9]+$");
		if (!regex.test(room)) {
			alert("Use only alphanumerics characters in name!");
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
    
    $("#associate").click(function(event){
		
    	if (roomdropdown.value == "Select Room"){	
			alert("Room Name missing!");
			return;
		}
    	
		if (chairdropdown.value=="Select Chair"){	
			alert("Chair name missing!");
			return;
		}
		 	
		var data = "{\"Chair\" : \""+chairdropdown.value+"\", \"Room\": \""+roomdropdown.value+"\"}";
		$.ajax({
			url: url+"associate/",
			data: data,
			contentType: 'application/json',
			dataType: 'json',
			type: 'POST',				
			success: function(data) {
				
				associateSuccessResponse(data);
				//alert(data.response); 
			},
			error: function(data){
				alert(data.responseText);
			}
		});	
	})
	
	function associateSuccessResponse(data){
    	
    	if(data.response=="exists"){
    		
    		var retVal = confirm("This chair is already associated with a room. Do you want to continue and reallocate ?");
            if( retVal == true ){
               reallocate();// call delete function
            }
    	}
    	else{  		
    		alert(data.response); 
    	}
    }
	
	function reallocate(){	
		
    	if (roomdropdown.value == "Select Room"){	
			alert("Room Name missing!");
			return;
		}
    	
		if (chairdropdown.value=="Select Chair"){	
			alert("Chair name missing!");
			return;
		}

		$.ajax({
			url: url+"remove/"+chairdropdown.value+"/"+roomdropdown.value,
			contentType: 'application/json',
			dataType: 'json',
			type: 'DELETE',				
			success: function(data) {	
				
			},
			error: function(data){
				alert(data.responseText);
			}
		});	
	}

	//Display report  
    $("#report").click(function(event){    		
    	$.getJSON(url+"report", report);
    })
    
    function report(data){
    	
    	$("#test1table tbody").remove();
    	document.getElementById('test1table').style.display = "block";
    	var div = document.getElementById('test1table');
    	
    	$.each(data, function(i,field){
    		div.innerHTML = div.innerHTML + "<tr><td>"+i+"</td><td>"+field+"</td></tr>";
    	})
    } 
    
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
    	
    	if(this.value == "[CREATE CHAIR]"){
    		$("#chairform").show(100);
    		chairdropdown.value = "Select Chair";	
    	}
    }
    
    roomdropdown.onchange = function(){
    	
    	if(this.value == "[CREATE ROOM]"){
    		$("#roomform").show(100);
    		roomdropdown.value = "Select Room"; // change
    	}    		
    }
})