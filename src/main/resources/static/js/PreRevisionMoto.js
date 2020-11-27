var envTrama = false;
var faseRev = 1;
var typeReg="auto";
var faseRechazo="0";
var imgPlacaAma = new Image();
var imgPlacaBlac = new Image();
imgPlacaAma.src="./img/PlacaAmarrilla.png";
imgPlacaBlac.src="./img/PlacaBlanca.png";
var oCapaP = document.getElementById("fondoBodyProccesing");	
var oCapP = document.getElementById("capaBodyProccesing");   
oCapaP.setAttribute("class", "");		
oCapP.style.display = 'none';
String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, "");	};
var option = {
		message : "",
		title : 'Sart 2.0',
		size : 'lg',
		subtitle : '',
		useBin : false,
		buttons: [
	        {text: 'ACEPTAR', style: 'info',   close: true, click:sc }	        
	    ]
	};

var optionS = {
		message : "",
		title : 'Sart 2.0',
		size : 'lg',
		subtitle : '',
		useBin : false,
		buttons: [
	        {text: 'OK', style: 'info',   close: true, click:scF }	        
	    ]
	};

function scF(){
	window.location.href="menuWelcome.html";
}
function sc(){
	
}

window.onload = function() {
	var entidad = "existenciaSession";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = "";
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respExistenciaSession', 'get', 0);
}
function respExistenciaSession(resp){
	if(resp==0){
		window.location.href="index.html";			
	}else{
		var atrSeg = resp.split(";");		
		document.getElementById("nombreUsuario").innerHTML = "   " + atrSeg[1];
		var ruta = "./imagenes/fotosEmpresa/" + atrSeg[0] + ".png";		
		document.getElementById('fotoUsuario').setAttribute('src', ruta);
		var entidad = "setCtxPreRevision";
		var cadenaAtributos, streaming = "";
		cadenaAtributos = "";
		streaming = entidad + "|" + cadenaAtributos;	
		doAjax('./ServicioBasicoControler.do', streaming,'respSetCtxPreRevision', 'get', 0);
    }
}
function respSetCtxPreRevision(resp){
    var entidad = "takeCtxPreRevision";
    var cadenaAtributos, streaming = "";
    cadenaAtributos = "";
    streaming = entidad + "|" + cadenaAtributos;	
    doAjax('./ServicioBasicoControler.do', streaming,'respTakeCtxPreRevision', 'get', 0);
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
}
function registroPreRevision(){
	var frm = document.getElementById("frmChecBox");	
	var strRechazos=" ";
	var strLegalizacion="";	
	var envio =true;
	var oCapa = document.getElementById("fondoBodyProccesing");	
	var oCap = document.getElementById("capaBodyProccesing");   
	oCapa.setAttribute("class", "fondoFuncion");
	oCap.style.display = 'block';
	$("input[type=checkbox]", frm)
	   .each(
		 function() {			 	
		 if($(this).is(':checked')){
			 faseRechazo="1";
		     strRechazos= strRechazos+"true;";		   
	     }else{
	    	 strRechazos= strRechazos+"false;";	
	     }
	});
	strRechazos = strRechazos.slice(0,strRechazos.length-1);
	var i=0;
	var num;
	if (envio == true) {
	    var entidad = "inspeccionPreRevision";
        var cadenaAtributos, streaming = "";
        cadenaAtributos = faseRechazo+";"+strRechazos;
        streaming = entidad + "|" + cadenaAtributos;	
        doAjax('./ServicioBasicoControler.do', streaming,'respCreacionPreRevision', 'get', 0);
	}
}
var resource;
function respCreacionPreRevision(resp){
	if (resp == 2) {
		optionS.message = " Disculpe, su session a expirado por inactividad ..!";
		optionS.subtitle = "  Informacion";
		eModal.alert(optionS);	
		setTimeout(function() { scF() }, 1700);
		return;
	}
	
	if(resp=="0"){
		option.message = "Disculpe; he Presentado Problemas al Momento de Registrar recepcion del Vehiculo";
	}else{
		option.message = "se ha ACEPTADO La Recepcion del Vehiculo de Una Manera Exitosa..!";
	}
	if(resp=="1"){
		resource="RegistroDatosConductor.html";
		option.message = "se ha DENEGADO el ingreso a pista al Vehiculo ..!";
	}else{
		resource="medidasPSI.html";		
	}
	var oCapa = document.getElementById("fondoBodyProccesing");	
	 var oCap = document.getElementById("capaBodyProccesing");   
	 oCapa.setAttribute("class", "");		
	 oCap.style.display = 'none';
	eModal.alert(option);
	setTimeout(function() {followResource()}, 1400);
}
function followResource(){
  window.location.href=resource;
}

function closeModal() {
	$("#registrarModalPro").click();
	clearTimeout(closeModal);
}

function returnMenuMain(){
	window.location.href="index.html";	
}