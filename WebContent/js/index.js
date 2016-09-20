// Waits for DOM to load up

//var url = "http://chairallocation.mybluemix.net/DemoWebApp/rest/"	
//var url = "http://localhost:9080/DemoWebApp/rest/"
var url = "http://saumilsqlapp.mybluemix.net/DemoWebApp/rest/"
	
	
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
    
  //Populate DropDown Menus on load
	roomMenu();
	chairMenu();
	
	$("select").each(function(){
		IBMCore.common.widget.selectlist.init(this);
		});  
    
    function roomMenu(){    	
    	$.getJSON(url+"rooms", roomresults)  	
    }
    
    function roomresults(data){
		$.each(data, function(i,field){
			if(field!=200){
				var option = $('<option />').val(field).text(field);			
				$("#Roomdropdown").append(option);
			}
		});
	}
    
    function chairMenu(){    	
    	$.getJSON(url+"chairs", chairresults)  	
    }
    
    function chairresults(data){        
		$.each(data, function(i,field){ 
			if(field!=200){
				var option = $('<option />').val(field).text(field);			
				$("#Chairdropdown").append(option);
			}
		});
	}
    	
    // Function to create a new chair
	$("#chair-submit").click(function(event){
		//Because of async behaviour, we need to block the browser for performing default action.
		event.preventDefault(); 
    	
		var chair = document.getElementById("chair").value;
		if (!chair.replace(/\s/g, '').length){		
			alertify.alert("No Name given to the chair you want to create. Please specify a name for the chair!")
			return;
		}
		if (!chair.replace(/\s/g, '').length){		
			alertify.alert("No Name given to the chair you want to create. Please specify a name for the chair!")
			return;
		}
		
		var regex = new RegExp("^[a-zA-Z0-9]+$");
		if (!regex.test(chair)) {
			alertify.alert("Use only alphanumerics characters in name!");
			return;
	    }
		var data = "{\"Name\" : \""+chair+"\"}";
		$.ajax({
			url: url+"chairs/",
			data: data,
			contentType: 'application/json',
			dataType: 'json',
			type: 'POST',			
			success: function(data) {
				alertify.alert(data.response);
				var option = $('<option />').val(chair).text(chair);			
				$("#Chairdropdown").append(option);
			},
			error: function(data){
				alertify.alert(data.responseText);
			}
		});	
    });
	  
	// Function to create a new room
    $("#room-submit").click(function(event){	 
    	//Block the browser for performing default action because of async behaviour.
    	event.preventDefault(); 
    	
		var room = document.getElementById("room").value;
		if (!room.replace(/\s/g, '').length){		
			alertify.alert("No Name given to the room you want to create. Please specify a name for the room!")
			return;
		}
		if (!room.replace(/\s/g, '').length){		
			alertify.alert("No Name given to the room you want to create. Please specify a name for the room!")
			return;
		}
		
		var regex = new RegExp("^[a-zA-Z0-9]+$");
		if (!regex.test(room)) {
			alertify.alert("Use only alphanumerics characters in name!");
			return;
	    }
		
		var data = "{\"Name\" : \""+room+"\"}";
		$.ajax({
			url: url+"rooms/",
			data: data,
			contentType: 'application/json',
			dataType: 'json',
			type: 'POST',			
			success: function(data) {
				alertify.alert(data.response);
				var option = $('<option />').val(room).text(room);			
				$("#Roomdropdown").append(option);
			},
			error: function(data){			
				alertify.alert(data.responseText);
			}
		});	
    });
    
    // create association between room and chair
    $("#associate").click(function(event){
		
    	if (roomdropdown.value == "Select Room"){	
			alertify.alert("Room Name missing!");
			return;
		}
    	
		if (chairdropdown.value=="Select Chair"){	
			alertify.alert("Chair name missing!");
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
				//alertify.alert(data.response); 
			},
			error: function(data){
				alertify.alert(data.responseText);
			}
		});	
	})
	
	
	function associateSuccessResponse(data){
    	
    	if(data.response=="Association exists"){
    		
    		var retVal = confirm("This chair is already associated with a room. Do you want to continue and reallocate ?");
            if( retVal == true ){
               reallocate();// call delete function
            }
    	}
    	else{  		
    		alertify.alert(data.response); 
    	}
    }
	
    // Update allocation
	function reallocate(){	
		
    	if (roomdropdown.value == "Select Room"){	
			alertify.alert("Room Name missing!");
			return;
		}
    	
		if (chairdropdown.value=="Select Chair"){	
			alertify.alert("Chair name missing!");
			return;
		}

		$.ajax({
			url: url+"associate/reassociate/"+chairdropdown.value+"/"+roomdropdown.value,
			contentType: 'application/json',
			dataType: 'json',
			type: 'DELETE',				
			success: function(data) {	
				alertify.alert(data.response);
			},
			error: function(data){
				alertify.alert(data.response);
			}
		});	
	}

	//Display report  
    $("#report").click(function(event){    		
    	$.getJSON(url+"report", report);
    })
    
    $("#deleteChair").click(function(event){
    	
    	var chairdrop = document.getElementById("Chairdropdown");
    	if(chairdropdown.selectedIndex==0 || chairdropdown.selectedIndex==0){
    		alertify.alert("No chair selected");
    		return;
    	}
    	$.ajax({
			url: url+"chairs/"+chairdropdown.value,
			contentType: 'application/json',
			dataType: 'json',
			type: 'DELETE',				
			success: function(data) {	
				alertify.alert(data.response);
				var x = document.getElementById("Chairdropdown");
				x.remove(x.selectedIndex);
				
			},
			error: function(data){
				alertify.alert("here" + data.response);
			}
		});	
    	
    })
    
     $("#deleteRoom").click(function(event){
    	
    	 var roomdrop = document.getElementById("Roomdropdown");
    	if(roomdrop.selectedIndex==0 || roomdrop.selectedIndex==1){
    		alertify.alert("No room selected");
    		return;
    	}
    	$.ajax({
			url: url+"rooms/"+roomdropdown.value,
			contentType: 'application/json',
			dataType: 'json',
			type: 'DELETE',				
			success: function(data) {	
				alertify.alert(data.response);
				var x = document.getElementById("Roomdropdown");
				x.remove(x.selectedIndex);
				
			},
			error: function(data){
				alertify.alert("here" + data.response);
			}
		});	
    	
    })
    
    function report(data){
   	
    	$("#test1table tbody").remove();
    	document.getElementById('test1table').style.display = "block";    	
    	var div = document.getElementById('test1table');
    	
    	$.each(data, function(i,field){
    		//console.log("avx");
    		if(i!="status"){
    			//$('<div id='+i+' style="float: left;width: 200px;height: 235px;margin: 10px;padding: 10px;border: 1px solid black;" ondrop="drop(event)" ondragover="allowDrop(event)" class="room ibm-col-4-1 ibm-background-neutral-white-20"><center><p>'+i+'</p></center></div>').appendTo(".room-section");
    			for(j=0;j<field.length;j++){
    				//$('<button class="dragme" draggable="true" id='+field[j]+' ondragstart="drag(event)" width="336" height="69">'+field[j]+'</button><br>').appendTo("#"+i);
    			}
    			// To generate a report
    		div.innerHTML = div.innerHTML + "<tr><td>"+i+"</td><td>"+field+"</td></tr>";
    		}
    			
    	})
    	$("#submitChange").hide();
    	$(".room-section").hide();
    	//readAssociation(); 	
    } 
    
    
    $("#showArrangement").click(function(event){
    	//location.reload();
    	$(".room-section").empty();
    	$.getJSON(url+"rooms", roomDisplay);
    })
    
    function roomDisplay(data){
    	var deffered =$.deffered;
    	console.log("roomData");
    	$('<div id= "pool" ondrop="drop(event)" ondragover="allowDrop(event)" class="room ibm-col-4-1 ibm-background-neutral-white-20"><center><p>Unallocated Chairs</p></center></div>').appendTo(".room-section");
		$.each(data, function(i,field){
			if(i!="status" && field){
				//Creates Room Displays
    			$('<div id='+field+' ondrop="drop(event)" ondragover="allowDrop(event)" class="room ibm-col-4-1 ibm-background-neutral-white-20"><center><p>'+field+'</p></center></div>').appendTo(".room-section");
    		 }
		});	
		getArrangement();	
    }	
    
    function chairDisplay(data){        
		$.each(data, function(i,field){ 
			//check if chair has already been allocated. If yes, then don not populate pool.
			if(field!=200 && $.inArray(field, allocatedChairs)==-1){
				$('<button class="dragme" draggable="true" id='+field+' ondragstart="drag(event)" width="336" height="69">'+field+'</button><br>').appendTo("#pool");
			}
		});
	}
    
	function getArrangement(){
		$.getJSON(url+"report", showArrangement);
	}
	var allocatedChairs = [];
    function showArrangement(data){
       	
    	
    	// Chairs placed in the rooms
    	$.each(data, function(i,field){
    		if(i!="status"){
    			for(j=0;j<field.length;j++){
    				$('<button class="dragme" draggable="true" id='+field[j]+' ondragstart="drag(event)" width="336" height="69">'+field[j]+'</button><br>').appendTo("#"+i);
    				allocatedChairs.push(field[j]);
    			}
    		}			
    	})
    	$("#test1table").hide();
    	$(".room-section").show();
    	$("#submitChange").show();  	 
    	displayChair();
    } 
    
    function displayChair(){
    	$.getJSON(url+"chairs", chairDisplay);   	
    }
    
    function roomData(rId) {
    	var a = [];
    	node = document.getElementById(rId);
    	children = node.childNodes;

    	for (child in children) {
    		if (children[child].id != null && children[child].id){
    			a.push(children[child].id);
    		}
    	}
    	return a;
    }
    // Send schema data back to server
    
    function readAssociation(){
		c={}
		node = document.getElementById("rooms"); 
		children1 = node.childNodes;	
		b = [];
		for(child in children1){
			if (children1[child].id != null && children1[child].id){
				var a= children1[child].id;
				b = b.concat(roomData(children1[child].id));
				c[a] = b;
				b=[];
			}			
		}	
		return JSON.stringify(c); 	
	}
    
    $("#submitChange").click(function(event){
    	
    	a = readAssociation();
    	console.log(a);
    	// a has a nice json {"r12":["chair8","chair123","chair10"],"r123":["chair8","chair123","chair10","chair567","chair154"]}	
    	$.ajax({
			url: url+"associate/schema",
			data: a,
			contentType: 'application/json',
			dataType: 'json',
			type: 'POST',				
			success: function(data) {			
				alertify.alert(data.response)
				//alertify.alert(data.response); 
			},
			error: function(data){
				alertify.alert(data.responseText);
			}
		});	
    	
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
    chairdropdown.onchange = function(event){
    	
    	if(this.value == "[CREATE CHAIR]"){
    		$("#chairform").show(100);
    		var x = document.getElementById("Chairdropdown");
    		x.selectedIndex=0;
    	}
    }
    
    roomdropdown.onchange = function(event){
    	
    	if(this.value == "[CREATE ROOM]"){
    		$("#roomform").show(100);
    		event.preventDefault(); 
    		var x = document.getElementById("Roomdropdown");
    		x.selectedIndex=0;
    	}    		
    }
})

 function allowDrop(ev) {
        ev.preventDefault();
    }

    function drag(ev) {
        ev.dataTransfer.setData("text", ev.target.id);

    }

    function drop(ev) {
        ev.preventDefault();
        var data = ev.dataTransfer.getData("text");
        ev.target.appendChild(document.getElementById(data));
    }