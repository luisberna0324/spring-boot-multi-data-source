String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, "");	};
var obj=null;
var rolPermitido="DTC";
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
function servFindFuncConsola(oText){
	var entidad = "servFindFuncConsola";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = oText.value;
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respServFindFuncConsola', 'get', 0);
}

function funcionCorregirPin(){
	var entidad = "funcCorregirPin";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = document.getElementById('consHoja').innerHTML+";"+ document.getElementById('nombreUsuario').value+";"+document.getElementById('nroPin').value;
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respServFuncCorregirPin', 'get', 0);
}

function respServFuncCorregirPin(resp){	
	if (resp == 2) {
		options.message = " Disculpe, su session a expirado por inactividad ..!";
		options.subtitle = "  Informacion";
		eModal.alert(options);	
		setTimeout(function() { scF() }, 1700);
		return;
	}
	options.message = "se ha CORREGIDO el nro. Pin de la Hoja Prueba ..!";
    eModal.alert(options);	
}

function funcionCambioPlaca(){
	var entidad = "funcCambioPlaca";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = document.getElementById('consHoja').innerHTML+";"+ document.getElementById('nombreUsuario').value+";"+document.getElementById('nroPlaca').value;
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respServFuncCambioPlaca', 'get', 0);
}

function respServFuncCambioPlaca(resp){	
	if (resp == 2) {
		options.message = " Disculpe, su session a expirado por inactividad ..!";
		options.subtitle = "  Informacion";
		eModal.alert(options);	
		setTimeout(function() { scF() }, 1700);
		return;
	}
	
	document.getElementById('ctxPlacas').value=document.getElementById('nroPlaca').value;
	options.message = "se ha CAMBIADO el nro  de Placa de una manera exitosa ..!";
	eModal.alert(options);
	
}

function funcionCmbEdoSicov(){
	var entidad = "funcCmbEdoSicov";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = document.getElementById('consHoja').innerHTML+";"+ document.getElementById('nombreUsuario').value+";"+document.getElementById('selectEdoSicov').value;
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respServFuncCmbEdoSicov', 'get', 0);
}

function respServFuncCmbEdoSicov(resp){	
	if (resp == 2) {
		options.message = " Disculpe, su session a expirado por inactividad ..!";
		options.subtitle = "  Informacion";
		eModal.alert(options);	
		setTimeout(function() { scF() }, 1700);
		return;
	}
	document.getElementById('edoSicov').innerHTML=document.getElementById('selectEdoSicov').value;
	
}
function respServFindFuncConsola(resp){	
	if (resp == 2) {
		options.message = " Disculpe, su session a expirado por inactividad ..!";
		options.subtitle = "  Informacion";
		eModal.alert(options);	
		setTimeout(function() { scF() }, 1700);
		return;
	}	
	
	document.getElementById('tipoRevision').innerHTML ="";
	document.getElementById('claseVehiculo').innerHTML ="";	
	document.getElementById('propietario').innerHTML="";
	document.getElementById('consHoja').innerHTML="";
	document.getElementById('edoSicov').innerHTML="";
	document.getElementById('fechaIngreso').innerHTML="";	
	document.getElementById('proveedorSicov').value ="";	
	document.getElementById('ctxPlacas').value = "";	
	document.getElementById('ctxServicio').value = "";
	var x = document.getElementById("canvas");
	var j = document.getElementById("placaRecepcion");	
	if(x !=null ) {
		j.removeChild(j.lastChild);		
	}
	if (resp == 0) {
		options.message = " Disculpe, la Placa SOLICITADA no se encuentra Registrada..!";
		options.subtitle = "  Informacion";
		eModal.alert(options);
		return;
	}	
	if (resp == 1) {
		options.message = " Disculpe, la Placa SOLICITADA  ha superado mas de los 15 dias desde la Ultima vez que se Registro..!";
		options.subtitle = "  Informacion";
		eModal.alert(options);
		return;
	}	 
	var atrConsola = resp.split(";");
	if(atrConsola[0]== "N"){
		document.getElementById('tipoRevision').innerHTML ="R.T.M.";	
	}else{
		document.getElementById('tipoRevision').innerHTML ="PREVENTIVA";
	}	
	document.getElementById('claseVehiculo').innerHTML =atrConsola[1];	
	document.getElementById('propietario').innerHTML=atrConsola[2] ;
	document.getElementById('consHoja').innerHTML=atrConsola[3];
	document.getElementById('edoSicov').innerHTML=atrConsola[4];	
	var fechaExpedicion = new Date();
	fechaExpedicion.setTime(atrConsola[5]);
	var mes = fechaExpedicion.getMonth()+1;
	var dia = fechaExpedicion.getDate();
	var ano = fechaExpedicion.getFullYear() ; //obteniendo año
	if(dia<10)
	    dia='0'+dia; 
	if(mes<10)
	    mes='0'+mes 	    
	    //BGX462
	document.getElementById('fechaIngreso').innerHTML=ano+"-"+mes+"-"+dia;	
	document.getElementById('proveedorSicov').value =atrConsola[7];	
	document.getElementById('ctxPlacas').value = document.getElementById('txtPlaca').value;	
	document.getElementById('ctxServicio').value = atrConsola[6];
	var newCanvas = document.createElement("canvas");
	newCanvas.setAttribute('width', '160%');
	newCanvas.setAttribute('height', '70%');
	newCanvas.setAttribute('id', 'canvas');
	var td = document.getElementById('placaRecepcion');		
	td.appendChild(newCanvas);	
	var contexto= newCanvas.getContext("2d");		
	contexto.beginPath();
	if(atrConsola[6]=="Particular"){
		contexto.drawImage(imgPlacaAma,0,0,160,70);
	}else{
		contexto.drawImage(imgPlacaBlac,0,0,160,70);
	}	
	var placa =document.getElementById('ctxPlacas').value;
	var str = placa.toUpperCase().substring(0, 3) +"-"+placa.toUpperCase().substring(3, 6);		
	contexto.font="bold 24px sans-serif";
	contexto.fillText(str,21,43);
	contexto.closePath();
}
function closeTipoRevision(){
	var oCapa = document.getElementById("fondoBodyTipoRevision");	
	var oCap = document.getElementById("capaBodyTipoRevision");   
	oCapa.setAttribute("class", "");
	oCap.style.display = 'none';		
}
function showTipoRevision(){
	var oCapa = document.getElementById("fondoBodyTipoRevision");	
	var oCap = document.getElementById("capaBodyTipoRevision");   
	oCapa.setAttribute("class", "fondoFuncion");
	oCap.style.display = 'block';	
}

function closeCambioPlaca(){
	var oCapa = document.getElementById("fondoBodyCambioPlaca");	
	var oCap = document.getElementById("capaBodyCambioPlaca");   
	oCapa.setAttribute("class", "");
	oCap.style.display = 'none';		
}
function showCmbPlaca(){
	var oCapa = document.getElementById("fondoBodyCambioPlaca");	
	var oCap = document.getElementById("capaBodyCambioPlaca");   
	oCapa.setAttribute("class", "fondoFuncion");
	oCap.style.display = 'block';	
}

function closeAnularRTM(){
	var oCapa = document.getElementById("fondoBodyAnularRTM");	
	var oCap = document.getElementById("capaBodyAnularRTM");   
	oCapa.setAttribute("class", "");
	oCap.style.display = 'none';		
}
function showAnularRTM(){
	var oCapa = document.getElementById("fondoBodyAnularRTM");	
	var oCap = document.getElementById("capaBodyAnularRTM");   
	oCapa.setAttribute("class", "fondoFuncion");
	oCap.style.display = 'block';	
}

function closeCorregirPin(){
	var oCapa = document.getElementById("fondoBodyCorregirPin");	
	var oCap = document.getElementById("capaBodyCorregirPin");   
	oCapa.setAttribute("class", "");
	oCap.style.display = 'none';
}

function showCorregirPin(){
	var oCapa = document.getElementById("fondoBodyCorregirPin");	
	var oCap = document.getElementById("capaBodyCorregirPin");   
	oCapa.setAttribute("class", "fondoFuncion");
	oCap.style.display = 'block';	
}

function closeEdoSicov(){
	var oCapa = document.getElementById("fondoBodyEdoSicov");	
	var oCap = document.getElementById("capaBodyEdoSicov");
	oCapa.setAttribute("class", "");
	oCap.style.display = 'none';
}

function showCmbEdoSicov(){
	var oCapa = document.getElementById("fondoBodyEdoSicov");	
	var oCap = document.getElementById("capaBodyEdoSicov");   
	oCapa.setAttribute("class", "fondoFuncion");
	oCap.style.display = 'block';
	SelectObject = document.getElementById("selectEdoSicov");
	  for(index = 0;  index < SelectObject.length;  index++) {    	   
	      if(SelectObject[index].value == document.getElementById('edoSicov').innerHTML ){
	   	   SelectObject.selectedIndex = index;
	      }
	  }	
}

function scF(){
	window.location.href="menuWelcome.html";
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
			document.getElementById("identificador").value = atrSeg[0];
			document.getElementById("nombreUsuario").innerHTML = "   " + atrSeg[1];
			var ruta = "./imagenes/fotosEmpresa/" + atrSeg[0] + ".png";			
			document.getElementById('fotoUsuario').setAttribute('src', ruta);						
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

function followResource(){
	window.location.href="OrdenPreRevision.html";
}
