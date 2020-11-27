var envTrama = false;
var faseRev = 1;
var tramaPase;
var iframe;
var doc;
var RecepcionSoat = new Object();
var Recepcion = new Object();
var typeReg="auto";
var faseRev="1";
String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, "");	};
var option = {
		message : "",
		title : 'Sart 2.0',
		size : 'lg',
		subtitle : '',
		useBin : false
	};

window.onload = function() {
	iframe = document.getElementById("iframes");    
	var url = 'https://www.runt.com.co/consultaCiudadana/#/consultaVehiculo';	
    iframe.src = "https://www.runt.com.co/consultaCiudadana/#/consultaVehiculo";
	$("#iframes").css({
		height : "760px",
		width : "99%"
	});
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
		document.getElementById("identificador").value = atrSeg[0];		
	}
	
}
$().ready(function () {
        $("#registrarModal").modal({
            backdrop: 'static',
            show: false,
            remote: "ajax.html"
        });
        $("#evaluarModal").modal({
            backdrop: 'static',show: false,  remote: "ajax.html"});
    });
function respRechazoSoat(resp){
	if(parseInt(resp)==0){
		option.message = "Disculpe, no Pude REGISTRAR la causal de Rechazo, Comuniquese con el equipo de Soporte Tecnico de SOLTELEC";
		option.subtitle = "Fallo del Sart";
		eModal.alert(option);
	}else{
		window.location.href="menuWelcome.html";	
	}	
} 
function registroManual(evt){
	evt.preventDefault();
	window.location.href="RegistroManualRunt.html";
}
function asigFocus() {
	document.getElementById("placa").focus();
	clearTimeout(asigFocus);
}
function registroDatosRunt(){
	var frm;
	 if(typeReg=="manual"){	
	    frm = document.getElementById("formP");
	 } else{
	    frm = document.getElementById("formR");
	 }	  
	streamingDatosAtrr = "";
	var envio =true;	
	if(envio==true){		
	  if(typeReg=="manual"){		  
		 var entidad = "regManualRunt";
		 var cadenaAtributos, streaming = "";			
		 cadenaAtributos = document.getElementById("placa").value + ";"+document.getElementById("combustible").value+";"+document.getElementById("modelo").value+";"+document.getElementById("typIndent").value+";"+document.getElementById("cedula").value+";"+document.getElementById("nombres").value+";"+document.getElementById("apellidos").value+";"+document.getElementById("claseVehiculo").value+";"+document.getElementById("tipoServicio").value+";"+document.getElementById("nroEjes").value+";"+document.getElementById("tipoRevision").value;		
		 streaming = entidad + "|" + cadenaAtributos;			
		 doAjax('./ServicioBasicoControler.do', streaming, 'respRegManualRunt','get', 0);
	  }else{
		   var entidad = "autorizacionPreRevision";
		   var cadenaAtributos, streaming = "";		   
		   cadenaAtributos = document.getElementById("idPreRevision").value+";"+  document.getElementById("nombreRunt").value+"; "+document.getElementById("apellidoRunt").value+";"+document.getElementById("identificador").value+";"+ document.getElementById("tipoRevision").value ;
	       streaming = entidad + "|" + cadenaAtributos;			
		   doAjax('./ServicioBasicoControler.do', streaming, 'respAutorizacionPreRevision','get', 0);
	  }
	}else{
		if(typeReg=="manual"){
			setTimeout(function() {recargaModal('manual')},700);			
		}else{			
			setTimeout(function() {recargaModal('auto')},700);	
		}		
	}
}

function  respAutorizacionPreRevision(resp){
	if(parseInt(resp) > 0){
		option.message= " Se ha AUTORIZADO  la Pre Revision una manera exitosa..!";
		option.subtitle = " Orden de Autirizacion";		
	}else{
		option.message= " He presentado Problemas al momento de  AUTORIZAR la Pre Revision";
		option.subtitle = " Problemas Encontrados";		
	}
	eModal.alert(option);
	
} 
function recargaModal(resp){	
	if(resp=="manual"){
		$("#regManual").click();
	}else{
		
		$("#regAuto").click();
	}
	clearTimeout(recargaModal);
}
function  respRegManualRunt(resp){
	if(parseInt(resp) > 0){
		option.message= " Se ha REGISTRADO los datos Manuales de una manera exitosa..!";
		option.subtitle = " Registro Datos";		
	}else{
		option.message= " He presentado Problemas al momento de Registrar los datos";
		option.subtitle = " Registro Datos";		
	}
	eModal.alert(option);
}

function followingPrerevision() {
  window.location.href="index.html";
  clearTimeout(followingPrerevision);
}

function closeModal() {
	$("#registrarModalPro").click();
	clearTimeout(closeModal);
}

function recoleccionDatosRunt(){
	window.location.href="RegistroDatosConductor.html";
}

function returnMenuMain(){
	window.location.href="menuWelcome.html";	
}
function asigFocusNombre() {
	$("#nombreRunt").focus();
	clearTimeout(asigFocusNombre);
}