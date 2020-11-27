var imgPlacaAma = new Image();
var imgPlacaBlac = new Image();
imgPlacaAma.src="./img/PlacaAmarrilla.png";
imgPlacaBlac.src="./img/PlacaBlanca.png";
String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, "");	};
var obj=null;
var findCiudad=false;
var options = {
		message : "",
		title : 'Sart 2.0',
		size : 'lg',
		subtitle : ' Datos Faltantes',
		useBin : false,
		buttons: [
	        {text: 'OK', style: 'info',   close: true, click:sc }	        
	    ]
	};
function sc(){
		
}
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

function serFindPropietario() {
	if (isNaN(document.getElementById("cedula").value)) {
		options.message = "Disculpe, el valor introducido para Cedula del Propietario  debe ser numerico ";
		options.subtitle = "Datos NO Valido";
		eModal.alert(options);
		obj = document.getElementById("cedula");
		envio = false;
		return false;
	} else {
		var entidad = "serFindPropietario";
		var cadenaAtributos, streaming = "";
		cadenaAtributos = document.getElementById('cedula').value;
		streaming = entidad + "|" + cadenaAtributos;
		doAjax('./ServicioBasicoControler.do', streaming,'respSerFindPropietario', 'get', 0);
	}
}	

function respSerFindPropietario(resp) {
	if (resp == 0) {

	} else {
		var atrSeg = resp.split(";");			
		document.getElementById('direccion').value = atrSeg[3];
		document.getElementById('telefono').value = atrSeg[5];
		document.getElementById('correoElectronico').value = atrSeg[4];
		SelectObject = document.getElementById("typIndent");		
	    for(index = 0;  index < SelectObject.length;  index++) {
	        if(SelectObject[index].value ==atrSeg[2]){
	      	   SelectObject.selectedIndex = index;
	        }
	    }	    
	    if( SelectObject.selectedIndex ==2  ){
	    	document.getElementById('nombres').value = atrSeg[1]+" " +atrSeg[2];;				
	    }else{
	    	document.getElementById('apellidos').value = atrSeg[1];
			document.getElementById('nombres').value = atrSeg[2];		
	    }	    
	}
}
window.onload = function() {
	var entidad = "existenciaSession";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = "";
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respExistenciaSession', 'get', 0);
	var entidad = "servFindDepartametos";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = "";
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respServFindDepartametos', 'get', 0);
}

function respServFindDepartametos(resp){	
	var atrClase = resp.split("]");	
	var oSelect =document.getElementById('lstDepartamentos');
	var currentClass;
	for (var i = 0; i < atrClase.length-1; i++) {
		 currentClass = atrClase[i].split(";");		 
		 var option = document.createElement("option"); 
		 option.value =currentClass[0];
		 option.text = currentClass[1];
		 oSelect.appendChild(option);
	}
}

function findCiudadesxDepartamentos(){
	var oSelect =document.getElementById('lstDepartamentos');
	var entidad = "servFindCiudades";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = oSelect.value ;	
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respServFindCiudades', 'get', 0);
	
}
function respServFindCiudades(resp){	
	var atrClase = resp.split("]");	
	var oSelect =document.getElementById('lstCiudad');
	var currentClass;	
	for (var i = oSelect.length; i > 0; i--) {		
		 oSelect.remove(i); 
	}
	oSelect.remove(0);	
	for (var i = 0; i < atrClase.length-1; i++) {
		 currentClass = atrClase[i].split(";");		 
		 var option = document.createElement("option"); 
		 option.value =currentClass[0];
		 option.text = currentClass[1];
		 oSelect.appendChild(option);
	}	
	findCiudad=true;	
}
function respExistenciaSession(resp){
	if(resp==0){
		window.location.href="index.html";			
	}else{
		var atrSeg = resp.split(";");		
		document.getElementById("nombreUsuario").innerHTML = "   " + atrSeg[1];
		var ruta = "./imagenes/fotosEmpresa/" + atrSeg[0] + ".png";		
		document.getElementById('fotoUsuario').setAttribute('src', ruta);
		var entidad = "servFindDatosConductor";
		var cadenaAtributos, streaming = "";
		cadenaAtributos = "";
		streaming = entidad + "|" + cadenaAtributos;	
		doAjax('./ServicioBasicoControler.do', streaming,'respServFindDatosConductor', 'get', 0);		
	}
}
function respServFindDatosConductor(resp){	
	var atrPreRevision = resp.split(";");
	var newCanvas = document.createElement("canvas");
	newCanvas.setAttribute('width', '80%');
	newCanvas.setAttribute('height', '40%');
	var td = document.getElementById('placaRecepcion');		
	td.appendChild(newCanvas);	
	var contexto= newCanvas.getContext("2d");
	contexto.beginPath();
	if(atrPreRevision[5]=="Particular"){
		contexto.drawImage(imgPlacaAma,0,0,80,40);
	}else{
		contexto.drawImage(imgPlacaBlac,0,0,80,40);
	}
	var str =atrPreRevision[0].substring(0, 3) +"-"+atrPreRevision[0].substring(3, 6);	
	contexto.font="bold 15px sans-serif";
	contexto.fillText(str,10,26);
	contexto.closePath();
	SelectObject = document.getElementById("typIndent");
	
    for(index = 0;  index < SelectObject.length;  index++) {
        if(SelectObject[index].value ==atrPreRevision[1]){
      	   SelectObject.selectedIndex = index;
        }
    }    
    if(atrPreRevision[1]=="N"){    	                       
    	document.getElementById("lblNombres").innerHTML="Empresa:";
    	document.getElementById("lblApellidos").innerHTML=" ";    	    
    	$("#apellidos").prop("disabled",true);
    	document.getElementById('apellidos').value=" ";
    }else{
    	document.getElementById('apellidos').value=atrPreRevision[3];
    	if(document.getElementById('apellidos').value.length<3){
    		document.getElementById('apellidos').value="";
    	}
    }    
    document.getElementById('cedula').value=atrPreRevision[2];	
    document.getElementById('nombres').value=atrPreRevision[4];
    if(document.getElementById('nombres').value.length<3){
		document.getElementById('nombres').value="";
	}
    var entidad = "serFindPropietario";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = document.getElementById('cedula').value;
	streaming = entidad + "|" + cadenaAtributos;
	doAjax('./ServicioBasicoControler.do', streaming,'respSerFindPropietario', 'get', 0);
}


function registroUpdateDatos(){
	var frm = document.getElementById("formRegistroConductor");	 
	var envio =true;	
	$("input[type=text]", frm)
	.each(
		function() {			
			if ($(this).val().length == 0) {
				var sd = $(this).attr('placeholder');
				options.subtitle=' Datos Faltantes';
				options.message ="Disculpe,NO Ha especificado el Siguiente Atributo Registro: "+ sd;					
	    	    eModal.alert(options);				    
				envio =false;
				return false;
			} 
		});	
	if(isNaN(document.getElementById("cedula").value)){
		options.message ="Disculpe, el valor introducido de la IDENTIFICACION  debe ser numerico ";
		options.subtitle="Datos NO Valido";
		obj =document.getElementById("cedula");
	    eModal.alert(options);	    
	    envio =false;
	    return false;
	}
	if(findCiudad==false){
		options.message ="Disculpe, no ha especificado la Ciudad  ";
		options.subtitle="Datos NO Valido";
		obj =document.getElementById("lstCiudad");
	    eModal.alert(options);	    
	    envio =false;		
	}		
	if(envio==true){
		obj=null;
		var entidad = "servActualizacionDatosConductor";
		var cadenaAtributos, streaming = "";		   
		cadenaAtributos = document.getElementById("direccion").value+";"+ document.getElementById("telefono").value+";"+ document.getElementById("correoElectronico").value+"; "+document.getElementById("typIndent").value+"; "+document.getElementById("cedula").value+"; "+document.getElementById("apellidos").value+"; "+document.getElementById("nombres").value +";"+document.getElementById('lstDepartamentos').value+";"+document.getElementById('lstCiudad').value ;
	    streaming = entidad + "|" + cadenaAtributos;			
		doAjax('./ServicioBasicoControler.do', streaming, 'respAutorizacionPreRevision','get', 0);	  
	}
}
function scF(){
	window.location.href="menuWelcome.html";
}

function  respAutorizacionPreRevision(resp){	
	if(resp == "2"){
		option.message = " Disculpe, su session a expirado por inactividad ..!";
		eModal.alert(option);
		setTimeout(function() {scF()}, 1700);		
	}
	
	if(parseInt(resp) > 0){
		options.message= " Se han ACTUALIZADO los datos del Conductor de una manera exitosa..!";
		options.subtitle = " ";		
	}else{
		options.message= " He presentado Problemas al momento de  REGISTRAR los datos del Conductor ";
		options.subtitle = " Problemas Encontrados";		
	}
	eModal.alert(options);
	setTimeout(function() {followResource()}, 1400);
}
function followResource(){
	  window.location.href="IngFacPin.html";
}

function returnMenuMain(){
	window.location.href="index.html";	
}

