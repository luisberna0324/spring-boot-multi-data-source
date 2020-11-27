String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, "");	};
var obj=null;
var imgPlacaAma = new Image();
var imgPlacaBlac = new Image();
imgPlacaAma.src="./img/PlacaAmarrilla.png";
imgPlacaBlac.src="./img/PlacaBlanca.png";
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
	if(obj!=null){
		setTimeout(function() {asigFocus()}, 700);	
	}else{
		obj=document.getElementById("placa");
		setTimeout(function() {asigFocus()}, 700);
	}	
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
		var entidad = "servFindDatosTakeSoat";
		var cadenaAtributos, streaming = "";
		cadenaAtributos = "";
		streaming = entidad + "|" + cadenaAtributos;	
		doAjax('./ServicioBasicoControler.do', streaming,'respServFindDatosTakeSoat', 'get', 0);
	}
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
function respServFindDatosTakeSoat(resp){	
  var atrSeg = resp.split(";");  
    var newCanvas = document.createElement("canvas");
	newCanvas.setAttribute('width', '160%');
	newCanvas.setAttribute('height', '70%');
	newCanvas.setAttribute('id', 'canvas');
	var td = document.getElementById('placaRecepcion');		
	td.appendChild(newCanvas);	
	var contexto= newCanvas.getContext("2d");		
	contexto.beginPath();
	if(atrSeg[7]=="Particular"){
		contexto.drawImage(imgPlacaAma,0,0,160,70);
	}else{
		contexto.drawImage(imgPlacaBlac,0,0,160,70);
	}
	var str = atrSeg[0].substring(0, 3) +"-"+atrSeg[0].substring(3, 6);		
	contexto.font="bold 24px sans-serif";
	contexto.fillText(str,21,43);
	contexto.closePath();
  
  document.getElementById('numeroPoliza').value=atrSeg[1];  
  var fechaExpedicion = new Date()
  fechaExpedicion.setTime(atrSeg[2]);
  var mes = fechaExpedicion.getMonth()+1;
  var dia = fechaExpedicion.getDate();
  var ano = fechaExpedicion.getFullYear() ; //obteniendo año
  if(dia<10)
    dia='0'+dia; 
  if(mes<10)
    mes='0'+mes 
  document.getElementById('txtExpedicion').value=ano+"-"+mes+"-"+dia;
    
  var fechaInicio = new Date();
  fechaInicio.setTime(atrSeg[3]);
  var mes = fechaInicio.getMonth()+1;
  var dia = fechaInicio.getDate(); 
  var ano = fechaInicio.getFullYear(); 
  if(dia<10)
    dia='0'+dia; 
  if(mes<10)
    mes='0'+mes   
  document.getElementById('txtInicioVig').value=ano+"-"+mes+"-"+dia;  
  var finVig = new Date(atrSeg[4])
  finVig.setTime(atrSeg[4]);
  var mes = finVig.getMonth()+1;
  var dia = finVig.getDate(); 
  var ano = finVig.getFullYear(); 
  if(dia<10)
    dia='0'+dia; 
  if(mes<10)
    mes='0'+mes  
  document.getElementById('txtFinVig').value=ano+"-"+mes+"-"+dia;  
  document.getElementById('entidadSOAT').value=atrSeg[5];
  document.getElementById('fkRevision').value=atrSeg[6];
}

function validacionPlaca(oTxt){
	obj =oTxt;	
	   
}
function registrarDatosSoat(){
	var frm = document.getElementById("formRegistroSoat");	 
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
	if(isNaN(document.getElementById("numeroPoliza").value)){
		options.message ="Disculpe, el valor introducido para el NRO. POLIZA  debe ser numerico ";
		options.subtitle="Datos NO Valido";
	    eModal.alert(options);
	    obj =document.getElementById("numeroPoliza");
	    envio =false;
	    return false;
	}	
	if(envio==true){
		obj=null;
	   var entidad = "servConfirmDatosSoat";
		 var cadenaAtributos, streaming = "";			
		 cadenaAtributos = document.getElementById("fkRevision").value + ";"+document.getElementById("numeroPoliza").value +";"+document.getElementById("txtExpedicion").value+";"+document.getElementById("txtInicioVig").value+";"+document.getElementById("txtFinVig").value+";"+document.getElementById("entidadSOAT").value
		 streaming = entidad + "|" + cadenaAtributos;		
		 doAjax('./ServicioBasicoControler.do', streaming, 'respServConfirmDatosSoat','get', 0);	  
	}
}

function respServConfirmDatosSoat(resp){
	if(parseInt(resp) > 0){			
		options.message= " Se han Confirmado Los Datos del SOAT de una manera exitosa..!";
		options.subtitle = " Confirmacion Datos";		
	}else{
		options.message= " He presentado Problemas al momento de Confirmar los datos";
		options.subtitle = " Registro Datos";		
	}	
	eModal.alert(options);
	setTimeout(function() {followResource()}, 1400);	
	
}
function followResource(){
	window.location.href="RegistroDatosConductorUbicacion.html";
}

function asigFocus() {
	obj.focus();
	obj.select()
}
function returnMenuMain(){
	window.location.href="menuWelcome.html";	
}
