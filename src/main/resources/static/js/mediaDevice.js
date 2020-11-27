
var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');
//var video = document.getElementById('video');
var constraints={ audio: false, video: true }
window.addEventListener("DOMContentLoaded", function() {	 
	var p = navigator.mediaDevices.getUserMedia({ audio: false, video: true });	
	 	
}, false);
document.getElementById("snap").addEventListener("click", function() {
	context.drawImage(video, 0, 0, 640, 480);
});

function cambioImg(){
try{
	var video = document.getElementById("video");
  
	if(navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {	    
	    navigator.mediaDevices.getUserMedia(constraints).then(function(stream) {
	    
	    if(stream!= null){	    
	    	 video.src = window.URL.createObjectURL(stream);
	        video.play();
	    }else{
	    	 alert("is null")
	    }
	   
	    });
	}
  }catch(err) {
	  console.log(err.name + ": " + err.message)
}
}



