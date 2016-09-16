
$(document).ready(function(){
	
	
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
				}			
			}	
			return JSON.stringify(c); 	
		}
	    
//	    $("#submitChange").click(function(event){
//	    	
//	    	a = readAssociation();
//	    	console.log(a);
//	    	// a has a nice json {"r12":["chair8","chair123","chair10"],"r123":["chair8","chair123","chair10","chair567","chair154"]}	
////	    	$.ajax({
////				url: url+"associate/schema",
////				data: a,
////				contentType: 'application/json',
////				dataType: 'json',
////				type: 'POST',				
////				success: function(data) {
////					
////					alert(data.response)
////					//alert(data.response); 
////				},
////				error: function(data){
////					alert(data.responseText);
////				}
////			});	
//	    	
//	    });
	    
	    
	
	var a = '{"r12":["chair8","chair123","chair10"],"r123":["chair567","chair154"],"roomtest3":["chair9"],"room18":["chair121"],"room1":["chartist","chair56"],"room2":["chair1"],"rom90":["chair532"],"roomtest21":["chair910"]}';
	//report3(a);
    //test
	
    function report2(){
    	
    	//var rooms = data.rooms || 5;
    	for(var i=0; i<2; i++){
    		//var len = rooms[i].length;
    		
    		//<div id="room1" ondrop="drop(event)" ondragover="allowDrop(event)" class="room"><center><p>ROOM 1</p></center></div>
    		$('<div id="room1" style="float: left;width: 200px;height: 235px;margin: 10px;padding: 10px;border: 1px solid black;background-color: white;" ondrop="drop(event)" ondragover="allowDrop(event)" class="room"><center><p>ROOM 1</p></center></div>').appendTo(".room-section");
    		for(var j=0; j<2; j++){
    			$('<label for="chair" draggable="true" data-text ="chair1" id="chair1" ondragstart="drag(event)" width="336" height="69">Chair 1</label><br>').appendTo("#room1");
    		}
    	}
    }
})

//function allowDrop(ev) {
//	    ev.preventDefault();
//	}
//
//	function drag(ev) {
//	    ev.dataTransfer.setData("text", ev.target.id);
//
//	}
//
//	function drop(ev) {
//	    ev.preventDefault();
//	    var data = ev.dataTransfer.getData("text");
//	    ev.target.appendChild(document.getElementById(data));
//	}
//	