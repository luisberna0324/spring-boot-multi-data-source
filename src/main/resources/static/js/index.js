
window.onload = function() {
	
}
var options = {
		message : " Disculpe, Login invalido, por favor intente de nuevo..! ",
		title : 'Sart 2.0.0',
		size : 'lg',
		subtitle : 'Datos Faltantes',
		useBin : false,
		 overlayColor: "#000",         
         buttons: [
 	        {text: 'Aceptar', style: 'background:#CCC;color:#000 ',close: true, click:sc }	        
 	    ]
	};
function sc(){
	
}
function autenti(){	
	if (document.getElementById("inputUsuario").value.length == 0) {
		message = "Disculpe,NO Ha especificado el Nombre del Usuario";
		eModal.alert(options, message);
		document.getElementById("inputUsuario").focus();
		return;
	}
	if (document.getElementById("inputPassword").value.length == 0) {
		message = "Disculpe,NO Ha especificado el Password del Usuario";
		eModal.alert(options, message);
		document.getElementById("inputPassword").focus();
		return;
	}
	var entidad = "autentificacion";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = document.getElementById("inputUsuario").value + ";"+ document.getElementById("inputPassword").value;
	streaming = entidad + "|" + cadenaAtributos;
	doAjax('./ServicioBasicoControler.do', streaming, 'respCAutentificacion','get', 0);
}
function respCAutentificacion(resp) {	
	var atrSeg = resp.split(";");
	if (atrSeg[0] == "no") {		
		eModal.alert(options);		
	} else {
		if(atrSeg[3]!= "ADM" && document.getElementById("panel").checked == true ) {
			options.message = " Disculpe, Usted no esta AUTORIZADO para Ingresar a este Modulo..!";
			options.subtitle = "Validacion de Usuario";
			eModal.alert(options);
			return ;
		}
	  if(atrSeg[3] == "ADM" && document.getElementById("panel").checked == true ) {
		  window.location.href="PanelConfiguracionUser.html";
	  }else{
		  window.location.href="menuWelcome.html";
	  }
	}	
}


