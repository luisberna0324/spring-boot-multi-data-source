var envTrama = false;
var faseRev = 1;
var typeReg="auto";
var rolPermitido="PRN";
String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, "");	};
var option = {
		message : "",
		title : 'Sart 2.0',
		size : 'lg',
		subtitle : '',
		useBin : false,
		buttons: [
	        {text: 'ACEPTAR', style: 'info',   close: true, click:scF }	        
	    ]
	};

function scF(){
	window.location.href="menuWelcome.html";
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
		if(rolPermitido==atrSeg[5]){
			document.getElementById("nombreUsuario").innerHTML = "   " + atrSeg[1];
			var ruta = "./imagenes/fotosEmpresa/" + atrSeg[0] + ".png";		
			document.getElementById('fotoUsuario').setAttribute('src', ruta);			
		}else{
			option.message = " Disculpe, usted no Posee un ROL Autorizado para entrar en este Modulo ..!";
			eModal.alert(option);
			setTimeout(function() {scF()}, 1700);
		}		
    }
}
function continuarPreRevision(){	    
	    var entidad = "setNaturalezaPreRevision";
        var cadenaAtributos, streaming = "";
        var frm = document.getElementById("naturalezaRevision");
    	$("input[type=radio]", frm)
    	   .each(
    		 function() {			 	
    		 if($(this).is(':checked')){			 
    			 cadenaAtributos ="false";	   
    	     }else{
    	    	 cadenaAtributos = "true";    	    	 	
    	     }
    		 return false;
    	});    	
        streaming = entidad + "|" + cadenaAtributos;
        doAjax('./ServicioBasicoControler.do', streaming,'respSetNaturalezaPreRevision', 'get', 0);
}

function respSetNaturalezaPreRevision(resp){	
	if (resp == 2) {
		option.message = " Disculpe, su session a expirado por inactividad ..!";
		option.subtitle = "  Informacion";
		eModal.alert(option);	
		setTimeout(function() { scF() }, 1700);
		return;
	}
	window.location.href="FormRunt.html";		
}

function closeModal() {
	$("#registrarModalPro").click();
	clearTimeout(closeModal);
}
function returnMenuMain(){
	window.location.href="menuWelcome.html";	
}