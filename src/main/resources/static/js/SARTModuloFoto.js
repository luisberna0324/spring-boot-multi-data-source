String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, "");	};
var obj=null;
var rolPermitido="IPR";
var autorizado=false;
var imgPlacaAma = new Image();
var imgPlacaBlac = new Image();
imgPlacaAma.src="./img/PlacaAmarrilla.png";
imgPlacaBlac.src="./img/PlacaBlanca.png";
var callFunction="";
var ctxPlaca;
var ctxHojaPrueba;
var faseCamara=1;
var posCamera=1;
var changeCamera=2;
var options = {
		message : "",
		title : 'Sart 2.0',
		size : 'lg',
		subtitle : ' Informacion',
		useBin : false,
		buttons: [{text: 'OK', style: 'info',   close: true, click:sc }]
	};

window.onload = function() {
	var entidad = "existenciaSession";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = "";
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respExistenciaSession', 'get', 0);
}

function trasmitirFotos(){
	 var formElement = document.getElementById("frmUploadPicture");	 
	  var formData = new FormData(formElement);
	  var ctxCanvas = document.getElementById("canvas1");
	  var data =ctxCanvas.toDataURL('image/png');
	  var content= "r";
	  var blob = new Blob([content], { type: "text/plain"});
	  formData.append("entidad",blob,"regPicturePrueba");
	  content= "";	 
	  blob = new Blob([content], { type: "text/plain"});
	  formData.append("idPrueba",blob,document.getElementById('nroPrueba').value);	  
	  blob = new Blob([content], { type: "text/plain"});
	  formData.append("hojaPrueba",blob,ctxHojaPrueba);
	  blob = new Blob([content], { type: "text/plain"});
	  formData.append("condicion",blob,ctxHojaPrueba); 
	  
	  blob = new Blob([content], { type: "text/plain"});
	  formData.append("fotoPrueba1",data);
	  var xhr = new XMLHttpRequest();
	  xhr.onreadystatechange = function() {  
	  if (xhr.readyState == 4) {	  
		  if(xhr.responseText.length>1){
			 var oCapa = document.getElementById("fondoBodyProccesing");	
			 var oCap = document.getElementById("capaBodyProccesing");   
			 oCapa.setAttribute("class", "");		
			 oCap.style.display = 'none';
			 cntFotos++;
			 options.subtitle="Registro Exitoso..!"
			 options.message ="se ha REGISTRADO  la novedad del "+ctxPreRevVehicular+" de una Manera Existosa";
			 document.getElementById("obsPreReVehicular").value=" ";			  
		  }else{
			  options.message ="Disculpe, he presentado problemas para culminar el Proceso ";  
		  }	  	
		  callMsg();	 	  
	  }
	}
	xhr.open('POST','./ServicioMultiPartControler.do',true);
	xhr.send(formData);
	
}
function respExistenciaSession(resp){
	if(resp==0){
		window.location.href="index.html";			
	}else{
		var atrSeg = resp.split(";");
		var arrRol = rolPermitido.split(";");
		for(index = 0;  index < arrRol.length;  index++) {			
	        if(arrRol[index]==atrSeg[5]){
	        	autorizado=true;
	        }
	    }		
		if(autorizado==true){
			document.getElementById("nombreUsuario").innerHTML = "   " + atrSeg[1];
			var ruta = "./imagenes/fotosEmpresa/" + atrSeg[0] + ".png";		
			document.getElementById('fotoUsuario').setAttribute('src', ruta);
			var entidad = "getCtxPruebas";
			var cadenaAtributos, streaming = "";
			cadenaAtributos = "";
			streaming = entidad + "|" + cadenaAtributos;	
			doAjax('./ServicioBasicoControler.do', streaming,'respGetCtxPruebas', 'get', 0);			
		}else{
			options.message = " Disculpe, usted no Posee un ROL Autorizado para entrar en este Modulo ..!";
			eModal.alert(options);
			setTimeout(function() {scF()}, 1700);
		}
	}
}

function sc(){ 
	if(callFunction=="irIndex"){
		window.location.href="index.html";
	}
}
function menuPrincipal(){
	window.location.href="menuWelcome.html";
}
function scF(){
	window.location.href="menuWelcome.html";
}
var audioSelect;
function gotDevices(deviceInfos) {
	  // Handles being called several times to update labels. Preserve values.	 
	  var cam =0;
	  for (let i = 0; i !== deviceInfos.length; ++i) {		  
	    const deviceInfo = deviceInfos[i];	  
	    if (deviceInfo.kind === 'videoinput') {	      
	        cam = 1 +parseInt(cam);	        
	        videoSelect =deviceInfo.deviceId;	    	
	    }	       
	  }	  
	  if (window.stream) {
		  console.log('entre windows streams ')
  	    window.stream.getTracks().forEach(track => {
  	      track.stop();
  	    });
  	  }	  
	  const videoSource = videoSelect;
	  console.log(' voy a videoSource  ')
	  var constraints = { audio: false,video: {deviceId:videoSelect,width:1920, height:1280,frameRate: { ideal: 125, max: 155 } }};
	  console.log(' voy a constraints ')
	navigator.mediaDevices.getUserMedia(constraints).then(handleSuccess).catch(handleError);	  
}

function handleSuccess(stream) {	  
	  var video = document.getElementById("video"+posCamera);	 
	  const videoTracks = stream.getVideoTracks();	
	 // console.log('Got stream with constraints:', constraints);
	  console.log(`Using video device: ${videoTracks[0].label}`);
	  window.stream = stream; // make variable available to browser console
	  video.srcObject = stream;
	  // video.src = window.URL.createObjectURL(stream);
    video.play();
}
function handleError(error) {
	  if (error.name === 'ConstraintNotSatisfiedError') {
	    let v = constraints.video;
	    errorMsg(`The resolution ${v.width.exact}x${v.height.exact} px is not supported by your device.`);
	  } else if (error.name === 'PermissionDeniedError') {
	    errorMsg('Permissions have not been granted to use your camera and ' +
	      'microphone, you need to allow the page access to your devices in ' +
	      'order for the demo to work.');
	  }
	  errorMsg(`getUserMedia error: ${error.name}`, error);
	}

	function errorMsg(msg, error) {
	  const errorElement = document.querySelector('#errorMsg');
	  errorElement.innerHTML += `<p>${msg}</p>`;
	  if (typeof error !== 'undefined') {
	    console.error(error);
	  }
	}
	
function changeCamaras(){	
	if(posCamera==1){
		posCamera=2;
		changeCamera=1;
	}else{
		posCamera=1;
		changeCamera=2;
	}		
}

function showCamara(){	
	var oCapaCamara = document.getElementById("capaCamara"+posCamera);
	var oCapa= document.getElementById("capaFoto"+posCamera);
	var video = document.getElementById("video"+posCamera);
	//document.getElementById("takeFoto").style.display='block';
	//document.getElementById("regNovedad").style.display='none';	
	if(faseCamara=="2"){
		faseCamara="1";		
		oCapaCamara.style.display='none';
		var ctxCanvas = document.getElementById("canvas"+posCamera);
		oCapa.style.display='block';
		var fondo = document.getElementById("fondo"+changeCamera);
		fondo.style.display='block';		
		var context = ctxCanvas.getContext('2d');
		context.drawImage(video, 0, 0,540, 480);
		var entidad = "servCapturaFecha";
		var cadenaAtributos, streaming = "";
		cadenaAtributos = "";
		streaming = entidad + "|" + cadenaAtributos;	
		doAjax('./ServicioBasicoControler.do', streaming,'respServCapturaFecha', 'get', 0);
		return ;
	}	
	if(faseCamara=="1"){
		faseCamara="2";		
		var fondo = document.getElementById("fondo"+changeCamera);
		fondo.style.display='none';
		oCapaCamara.style.display='block';		
		oCapa.style.display='none';
		try{			  
			navigator.mediaDevices.enumerateDevices().then(gotDevices).catch(handleError);			
		  }catch(err) {
			  console.log(err.name + ": " + err.message)
			  alert(err.name + ": " + err.message)
		}
		return ;
	}
}
function respServCapturaFecha(resp){	
	var ctxCanvas = document.getElementById("canvas"+posCamera);
	var context = ctxCanvas.getContext('2d');
	var fecha = new Date()
	fecha.setTime(resp);
	var mes = fecha.getMonth()+1;
	var dia = fecha.getDate();
	var hor = fecha.getHours();
	var min = fecha.getMinutes();
	var ano = fecha.getFullYear() ; //obteniendo año
	if(dia<10)
	   dia='0'+dia; 
	if(mes<10)
	   mes='0'+mes
	if(min<10)
	   min='0'+min
	if(hor <10)
		hor ='0'+hor    
	   context.font="bold 17px serif";
	   context.fillStyle = '#58D3F7';
	   context.fillText(" "+ano+"-"+mes+"-"+dia+" Hora: "+hor+":"+min+" "+ctxPlaca,5,435);
	   context.closePath();	
}

function respGetCtxPruebas(resp){
		if (resp.length > 2) {
		var flujoCtxPrueba = resp.split(";");
		document.getElementById('nroTurno').innerHTML =  flujoCtxPrueba[3];
		document.getElementById('condicion').innerHTML = flujoCtxPrueba[4];
		document.getElementById('TIPO').innerHTML =flujoCtxPrueba[5];
		var newCanvas = document.createElement("canvas");
		newCanvas.setAttribute('width', '100%');
		newCanvas.setAttribute('height', '50%');
		newCanvas.setAttribute('id', 'canvas');
		var td = document.getElementById('nroPlaca');
		td.appendChild(newCanvas);
		var contexto = newCanvas.getContext("2d");
		contexto.beginPath();
		if (flujoCtxPrueba[6] == "Particular") {
			contexto.drawImage(imgPlacaAma, 0, 0, 100, 50);
		} else {
			contexto.drawImage(imgPlacaBlac, 0, 0, 100, 50);
		}
		document.getElementById('nroPrueba').value = flujoCtxPrueba[2]
		var str = flujoCtxPrueba[0].substring(0, 3) + "-"+ flujoCtxPrueba[0].substring(3, 6);
		ctxPlaca= flujoCtxPrueba[0];
		contexto.font = "bold 18px sans-serif";
		contexto.fillText(str, 8, 32);
		contexto.closePath();
		ctxHojaPrueba = flujoCtxPrueba[7]; 
	} else {		
		if(resp=="2"){
			options.message = "Disculpe; su SESSION DE USUARIO se ha cerrado Por Inactividad ..!";
			callFunction="irIndex";
			eModal.alert(options);			
			return ;
		}
		if(resp=="0"){
			options.message = "Disculpe; he Presentado Problemas al momento de cargar Las hojas de Pruebas Pendientes.!";
	    }	
		eModal.alert(options);		
	}
}




function followResource(){
	window.location.href="OrdenPreRevision.html";
}
