var imgPlacaAma = new Image();
var imgPlacaBlac = new Image();
imgPlacaAma.src="./img/PlacaAmarrilla.png";
imgPlacaBlac.src="./img/PlacaBlanca.png";

var imgFrontal = new Image();
var imgTrasera = new Image();
var imgDerecha = new Image();
var imgIzquierda = new Image();
var claseVehiculo;

var oCapaP = document.getElementById("fondoBodyProccesing");	
var oCapP = document.getElementById("capaBodyProccesing");   
oCapaP.setAttribute("class", "");		
oCapP.style.display = 'none';
window.onload = function() {
	var entidad = "existenciaSession";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = "";
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respExistenciaSession', 'get', 0);
}
function respExistenciaSession(resp){
	if(resp==0){		
		optionSession.message ="Disculpe, su session a expirado por inactividad ..!";
		eModal.alert(optionSession);					
	}else{		
		var atrSeg = resp.split(";");		
		document.getElementById("nombreUsuario").innerHTML = "   " + atrSeg[1];			
		var ruta = "./imagenes/fotosEmpresa/" + atrSeg[0] + ".png";		
		document.getElementById('fotoUsuario').setAttribute('src', ruta);
		var entidad = "takeCtxPreRevision";
		var cadenaAtributos, streaming = "";
		cadenaAtributos = "";
		streaming = entidad + "|" + cadenaAtributos;	
		doAjax('./ServicioBasicoControler.do', streaming,'respTakeCtxPreRevision', 'get', 0);
    }
}
function respTakeCtxPreRevision(resp){
	var atrPreRevision = resp.split(";");
	var newCanvas = document.createElement("canvas");
	newCanvas.setAttribute('width', '80%');
	newCanvas.setAttribute('height', '40%');
	var td = document.getElementById('placaRecepcion');		
	td.appendChild(newCanvas);	
	var contexto= newCanvas.getContext("2d");
	contexto.beginPath();	
	if(atrPreRevision[2]=="Particular"){
		contexto.drawImage(imgPlacaAma,0,0,80,40);
	}else{
		contexto.drawImage(imgPlacaBlac,0,0,80,40);
	}
	var str =atrPreRevision[0].substring(0, 3) +"-"+atrPreRevision[0].substring(3, 6);	
	contexto.font="bold 15px sans-serif";
	contexto.fillText(str,10,26);
	contexto.closePath();
	if(atrPreRevision[3]=="MOTOCICLETA"){		
		imgFrontal.src="./img/frontalMoto.png";
		imgTrasera.src="./img/traseraMoto.png";
		imgDerecha.src="./img/MotoLateralDerechaMenu2.png";
		imgIzquierda.src="./img/MotoLateralIzqMenu2.png";
	}
	if( atrPreRevision[3]=="AUTOMOVIL" ){
		imgFrontal.src="./img/imgfrontal.png";
		imgTrasera.src="./img/trasera.png";
		imgDerecha.src="./img/imglateraldere1.png";
		imgIzquierda.src="./img/imglateraIzq.png";
	}

	if(atrPreRevision[3]=="CAMPERO"){
		imgFrontal.src="./img/camperoFrontal.png";
		imgTrasera.src="./img/camperoTrasera.png";
		imgDerecha.src="./img/camperoDer.png";
		imgIzquierda.src="./img/camperoIzq.png";
	}
	
	if(atrPreRevision[3]=="CAMIONETA"){
		imgFrontal.src="./img/imgCamionetafrontal.png";
		imgTrasera.src="./img/imgCamionetatrasera.png";
		imgDerecha.src="./img/imgCamionetaLatelDer.png";
		imgIzquierda.src="./img/imgCamionetaLatelIzq.png";
	}
	if(atrPreRevision[3]=="CAMION" ){
		imgFrontal.src="./img/imgFrontalPesado.png";
		imgTrasera.src="./img/imgTraseroPesado.png";
		imgDerecha.src="./img/imgpesadolateralDer.png";
		imgIzquierda.src="./img/imgpesadolateralIzq.png";
	}
	if( atrPreRevision[3]=="TRACTOCAMION"){
		imgFrontal.src="./img/Pesadofrente.png";
		imgTrasera.src="./img/TraseraPesado.png";
		imgDerecha.src="./img/imagDerechapesado.png";
		imgIzquierda.src="./img/imagIzquierdapesado.png";
	}
	if(atrPreRevision[3]=="BUS" ){
		imgFrontal.src="./img/microbusFrente.png";
		imgTrasera.src="./img/microbusTrasera.png";
		imgDerecha.src="./img/BusGrandeDerecho.png";
		imgIzquierda.src="./img/BusGrandeIzquierdo.png";
	}
	if(atrPreRevision[3]=="MICROBUS" || atrPreRevision[3]=="BUSETA"){
		imgFrontal.src="./img/microbusFrente.png";
		imgTrasera.src="./img/microbusTrasera.png";
		imgDerecha.src="./img/microbusDerecha.png";
		imgIzquierda.src="./img/microbusIzquierda.png";
	}
	if(atrPreRevision[3]=="VOLQUETA"){
		imgFrontal.src="./img/volquetaFrontal.png";
		imgTrasera.src="./img/volquetaTrasera.png";
		imgDerecha.src="./img/volquetaDerecha.png";
		imgIzquierda.src="./img/volquetaIzquierda.png";
	}
	claseVehiculo =atrPreRevision[3];
	var aRes = document.getElementById('frontal');		
	aRes.appendChild(imgFrontal);
	var aRes = document.getElementById('trasera');		
	aRes.appendChild(imgTrasera);
	var aRes = document.getElementById('derecha');		
	aRes.appendChild(imgDerecha);
	var aRes = document.getElementById('izquierda');		
	aRes.appendChild(imgIzquierda);
	
}

function retCapa(){
	var oCapaEdoVehiculo = document.getElementById("capaEdoVehiculo");
	var oCapaCamara = document.getElementById("capaCamara");
	var oCapa= document.getElementById("capaFoto");
	var video = document.getElementById("video");	
	faseCamara="0";
	oCapaCamara.style.display='none';
	oCapaEdoVehiculo.style.display='block';
	oCapa.style.display='none';
	document.getElementById("takeFoto").style.display='none';
	document.getElementById("regNovedad").style.display='block';
}

function followSing(){
	var entidad = "existenciaSession";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = "";
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respFollowSing', 'get', 0);	
}
function respFollowSing(resp){
	if(resp==0){
		optionSession.message ="Disculpe, su session a expirado por inactividad ..!";
		eModal.alert(optionSession);
		setTimeout(function() {scF()}, 1990);	
	}else{		
		if(claseVehiculo=="MOTOCICLETA"){
			window.location.href="PreIngresoMoto.html";	
		}else{
			window.location.href="PreIngreso.html";	
		}
	}		
}

function showCamara(){	
	var oCapaEdoVehiculo = document.getElementById("capaEdoVehiculo");
	var oCapaCamara = document.getElementById("capaCamara");
	var oCapa= document.getElementById("capaFoto");
	var video = document.getElementById("video");
	document.getElementById("takeFoto").style.display='block';
	document.getElementById("regNovedad").style.display='none';	
	if(faseCamara=="1"){
		faseCamara="0";
		oCapaCamara.style.display='none';
		oCapaEdoVehiculo.style.display='none';
		oCapa.style.display='block';		
		var context = canvas.getContext('2d');
		context.drawImage(video, 0, 0, 640, 480);
		return ;
	}	
	if(faseCamara=="0"){
		faseCamara="1";
		oCapaCamara.style.display='block';	
		oCapaEdoVehiculo.style.display='none';
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

var audioSelect;
function gotDevices(deviceInfos) {
	  // Handles being called several times to update labels. Preserve values.	 
	  var cam =0;
	  for (let i = 0; i !== deviceInfos.length; ++i) {		  
	    const deviceInfo = deviceInfos[i];
	    if (deviceInfo.kind === 'videoinput') {	      
	        cam = 1 +parseInt(cam);	        
	    }
	    if(cam==2){	    	
	    	videoSelect =deviceInfo.deviceId;
	    	cam =7;
	    	break;
	    }	   
	  }
	  if (window.stream) {		 
  	    window.stream.getTracks().forEach(track => {
  	      track.stop();
  	    });
  	  }	  
	  const videoSource = videoSelect;
	  var constraints = { audio: false,video: {deviceId:videoSelect,width:1920, height: 1080,frameRate: { ideal: 125, max: 155 } }};   		
	navigator.mediaDevices.getUserMedia(constraints).then(handleSuccess).catch(handleError);
	  
}	  
function handleSuccess(stream) {
	 
	  //const video = document.querySelector('video');
	  var video = document.getElementById("video");	 
	  const videoTracks = stream.getVideoTracks();	
	 // console.log('Got stream with constraints:', constraints);
	  console.log(`Using video device: ${videoTracks[0].label}`);
	  window.stream = stream; // make variable available to browser console
	  video.srcObject = stream;
	  //video.src = window.URL.createObjectURL(stream);
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

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var faseCamara="0"
var videoSelect;
var intCtxPreRevVehicular="0";
var evidencia;
var handler;
var canvas = document.getElementById("canvas");
var ctxPreRevVehicular="Frontal";
var cntFotos = 1;
var options = {
		message : "",
		title : 'Sart 2.0',
		size : 'lg',
		subtitle : ' Datos Faltantes',
		useBin : false,
		buttons: [
	        {text: 'Aceptar', style: 'info',   close: true, click:sc },	        
	    ]
	};
function sc(){		
}
var optionSession = {
		message : "",
		title : 'Sart 2.0',
		size : 'lg',
		subtitle : ' Inf. de Session',
		useBin : false,
		buttons: [
	        {text: 'OK', style: 'info',   close: true, click:scF }	        
	    ]
	};
function scF(){
	window.location.href="menuWelcome.html";
}


function registroNovedadVehicular(){	
	if(cntFotos>4){
		options.subtitle="Alerta..!";
		options.message ="Disculpe; Usted ha Excedido el LIMITE de toma de Evidencias";
		eModal.alert(options);		
		return false;
	}	
	var frm = document.getElementById("novedad");
	var isChe= false;
	var strHandler="no";
	var temp;
	$("input[type=radio]", frm)
	   .each(
		 function() {			 	
		 if($(this).is(':checked')){
			 temp =$(this).val();
			 strHandler=temp.split(";");
			 $(this).removeAttr("checked");
	     }		 
	});
	if(strHandler=="no"){		
	   options.message ="Disculpe, No  se ha SELECCIONADO ningun tipo de Novedad ";  
	   eModal.alert(options);		
	   return false;
	}
	  var oCapa = document.getElementById("fondoBodyProccesing");	
	  var oCap = document.getElementById("capaBodyProccesing");   
	  oCapa.setAttribute("class", "fondoFuncion");
	  oCap.style.display = 'block';
	  var formElement = document.getElementById("frmUploadFirma");	 
	  var formData = new FormData(formElement);
	  var data =canvas.toDataURL('image/png');
	  var content= "r";
	  var blob = new Blob([content], { type: "text/plain"});
	  formData.append("entidad",blob,"regNovedad");
	  content= "";
	  blob = new Blob([content], { type: "text/plain"});
	  formData.append("handler",blob,strHandler[0]);
	  blob = new Blob([content], { type: "text/plain"});
	  formData.append("contexto",blob,intCtxPreRevVehicular);
	  blob = new Blob([content], { type: "text/plain"});
	 
	  formData.append("obsPerpectiva",blob,"Se encontro la siguiente novedad: "+ strHandler[1]+ " en la parte "+ctxPreRevVehicular+" del Vehiculo. "+document.getElementById("obsPreReVehicular").value);	  
	  formData.append("evidencia",data);
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

function callMsg(){
  eModal.alert(options);
}

function PreRevisionFrontal(){
    document.getElementById("lblDeContexto").innerHTML ="<strong>PRE-REVISION FRONTAL DEL VEHICULO</strong>";
    intCtxPreRevVehicular="0";
    ctxPreRevVehicular="Frontal";
    clearRadio();
}
function PreRevisionLateralDer(){
    document.getElementById("lblDeContexto").innerHTML ="<strong>PRE-REVISION LATERAL DERECHA DEL VEHICULO</strong>";
    intCtxPreRevVehicular="1";
    ctxPreRevVehicular="LateralDer";
    clearRadio();
}
function PreRevisionLateralIzq(){
    document.getElementById("lblDeContexto").innerHTML ="<strong>PRE-REVISION LATERAL IZQUIERDA DEL VEHICULO</strong>";
    intCtxPreRevVehicular="2";
    ctxPreRevVehicular="LateralIzq";
    clearRadio();
}
function PreRevisionTrasera(){
    document.getElementById("lblDeContexto").innerHTML ="<strong>PRE-REVISION TRASERA DEL VEHICULO</strong>";
    intCtxPreRevVehicular="3";
    ctxPreRevVehicular="Trasera";
    clearRadio();
}
function clearRadio(){
	var frm = document.getElementById("novedad");
	$("input[type=radio]", frm)
      .each(function() {			 	
	    if($(this).is(':checked')){		  
		  $(this).removeAttr("checked");
        }
    });
}
