String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, "");	};
var nroEjes;
var imgPlacaAma = new Image();
var imgPlacaBlac = new Image();
imgPlacaAma.src="./img/PlacaAmarrilla.png";
imgPlacaBlac.src="./img/PlacaBlanca.png";
var options = {
		message : "",
		title : 'Sart 2.0',
		size : 'lg',
		subtitle : ' SESSION INACTIVA',
		useBin : false,
		buttons: [
	        {text: 'OK', style: 'info',   close: true, click:sc }	        
	    ]
	};

var optionSession = {
		message : "",
		title : 'Sart 2.0',
		size : 'lg',
		subtitle : ' Inf. Session',
		useBin : false,
		buttons: [
	        {text: 'OK', style: 'info',   close: true, click:scF }	        
	    ]
	};

function scF(){
	window.location.href="menuWelcome.html";
}
function sc(){ }

function beta(e) {
	key = e.KeyCode || e.which;	
	if (key == 8 || key == 0 ) {
		return true;
	}	
	teclado = String.fromCharCode(key);
	numeros = "0123456789";
	especiales = "8-37-39-46";
	teclado_especial = false;
	for ( var i in especiales) {
		if (key === especiales[i]) {
			teclado_especial = true;
			// break;
		}
	}
	if (numeros.indexOf(teclado) === -1 && !teclado_especial) {
		//alert (key);
		return false;
	}
}


window.onload = function() {
	var entidad = "existenciaSession";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = "";
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respExistenciaSession', 'get', 0);
}

function verifCertificado(){
	SelectObject = document.getElementById("combustible");
	if(SelectObject.value == "GAS NATURAL VEHICULAR" || SelectObject.value == "GAS-GASOLINA"){
		$('#gasGasolina').modal('show');		
	}else{
		$('#gasGasolina').modal('false');
	}	
}
function closeAnulacionAdmision(){
	var oCapa = document.getElementById("fondoCapaCertificadoInstalacion");	
	var oCap = document.getElementById("capaCertificadoInstalacion"); 
	oCapa.setAttribute("class", "");
	oCap.style.display = 'none';
	document.getElementById("nroCertificado").value ="";
	document.getElementById("fechaVence").value ="";
}

function setCertificado(){
	$('#gasGasolina').modal('false');
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
	/*var entidad = "preIngreso";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = "";
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respPreIngreso', 'get', 0);*/
}
function respTakeCtxPreRevision(resp){
	if(resp==2){
		optionSession.message = " Disculpe, su session a expirado por inactividad ..!";
		eModal.alert(optionSession);
		setTimeout(function() {scF()}, 1900);	   			
	}else{
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
		if(isNaN(atrPreRevision[7])){
			document.getElementById('pesoBruto').value="0.0";		
		}else{
			document.getElementById('pesoBruto').value= atrPreRevision[7];  
		}	
		
		if( atrPreRevision[3]=="MOTOCICLETA"){
			document.getElementById('sillas').value="1";
		}
		if( atrPreRevision[3]=="MOTOCARRO"){
			document.getElementById('sillas').value="2";
		}
		if( atrPreRevision[3]=="AUTOMOVIL"){
			document.getElementById('sillas').value="3";
		}
		
		if( atrPreRevision[3]=="AUTOMOVIL"){
			document.getElementById('sillas').value="3";
		}
		
		if( atrPreRevision[3]=="BUS" || atrPreRevision[3]=="BUSETA" || atrPreRevision[3]=="MICROBUS"  ){
			document.getElementById('sillas').value= atrPreRevision[10];
		}
		
		if( atrPreRevision[3]=="CAMIONETA" || atrPreRevision[3]=="CAMPERO"  ){
			document.getElementById('sillas').value= atrPreRevision[10];
		}
		if( atrPreRevision[3]=="TRACTOCAMION" || atrPreRevision[3]=="CAMION" || atrPreRevision[3]=="VOLQUETA" ){
			document.getElementById('sillas').value="1";
		}  
		
		SelectObject = document.getElementById("combustible");		
		  for(index = 0;  index < SelectObject.length;  index++) {			  
		      if(SelectObject[index].value ==atrPreRevision[9]){
		   	     SelectObject.selectedIndex = index;
		   	     if(atrPreRevision[9] == "GAS NATURAL VEHICULAR" || atrPreRevision[9] == "GAS-GASOLINA"){
		  		     $('#gasGasolina').modal('show');		
		  	     }
		      }
		  }
	}
}

/*function respPreIngreso(resp){	
	if(resp==2){
		optionSession.message = " Disculpe, su session a expirado por inactividad ..!";
		eModal.alert(optionSession);
		setTimeout(function() {scF()}, 1700);	   			
	}else{
		var turnos = parseInt(resp);
		if(turnos< 10){
			turnos = "0"+ resp;
		}else{
			turnos = resp;
		}
		document.getElementById("turno").innerHTML ="&nbsp;&nbsp;"+turnos;		
	}
	
}*/

function registroPreIngreso(){
	var frm = document.getElementById("frmDatosGenerales");
	var streamingDatosAtrr = "";
	var streamingPsiEje = "";
	var envio =true;
	var strLegalizacion="";
	$("input[type=text]", frm)
		.each(
			function() {
				if(isNaN($(this).val())){
					var sN= $(this).attr('placeholder');
					options.message ="Disculpe,el Siguiente Atributo no es numerico: "+ sN;	
		    	    eModal.alert(options);
		    	    envio =false;
					return false;
				}
				if ($(this).val().length == 0) {
					var sd = $(this).attr('placeholder');
					options.message ="Disculpe,NO Ha especificado el Siguiente Atributo Registro: "+ sd;					
		    	    eModal.alert(options);				    
					envio =false;
					return false;
				} else {
					streamingDatosAtrr = streamingDatosAtrr + $(this).val() + ";";
				}
			});
	if(envio ==false){
		return false;
	}	
	 strLegalizacion= strLegalizacion+"false;"
	 strLegalizacion= strLegalizacion+"false;"	
	     
	var frm = document.getElementById("aplicaEnsenanza");
	$("input[type=checkbox]", frm)
	   .each(
		 function() {			 	
		 if($(this).is(':checked')){
			 strLegalizacion= strLegalizacion+"true;"		   
	     }else{
	    	 strLegalizacion= strLegalizacion+"false;"	
	     }
	 	 return false;
	});	
	//89008;3500;1;0;0;;0% ; %1020
	
	SelectObject = document.getElementById("combustible");		
	if(SelectObject.value =="GAS NATURAL VEHICULAR" || SelectObject.value == "GAS-GASOLINA"){		 
			if ($('#nroCertificado').val().length == 0 || $('#fechaVencimiento').val().length== 0) {
				 $('#gasGasolina').modal('show');
				 return false;
			}
	}  	     
	if (envio == true) {
		var entidad = "PreIngreso2Fase";	
        var cadenaAtributos, streaming = "";
        streamingDatosAtrr= streamingDatosAtrr+";"+document.getElementById("tipoVehiculo").value; 
        cadenaAtributos = streamingDatosAtrr+"%"+strLegalizacion+" ; "+document.getElementById("otrasConcideraciones").value+";"+document.getElementById("fechaVencimiento").value ;  
		
        streaming = entidad + "|" + cadenaAtributos;	
        doAjax('./ServicioBasicoControler.do', streaming,'respRegistroPreIngreso', 'get', 0);
	}
}

function doKitViajero(oCheck){
	if(oCheck.checked ==true){
		oCheck.checked = false;
	}else{
		oCheck.checked = true;
	}
}


function respRegistroPreIngreso(resp){    	
	if(resp=="0"){
		options.message = "Disculpe; he Presentado Problemas al Momento de Registrar el Pre-Ingreso del Vehiculo";
	}else{
		options.message = "He  REGISTRADO Pre-Ingreso del Vehiculo de una Manera Exitosa";				
	}	
	options.subtitle = " ";
	
    eModal.alert(options);
    setTimeout(function() {followResource()}, 1400);   
}
function followResource(){
  window.location.href="RegistroDatosConductor.html";
}

function returnMenuMain(){
	window.location.href="index.html";	
}