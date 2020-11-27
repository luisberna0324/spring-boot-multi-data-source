var rolPermitido="IPR";
var autorizado=false;
var aprobado="";
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
	        {text: 'Finalizar', style: 'info',   close: true, click:sc },
	        
	    ]
	};
var optionValidador = {
		message : "",
		title : ' Sart 2.0',
		size : 'lg',
		subtitle : ' Datos Faltantes',
		 overlayColor: "#000", 
		useBin : false,
		buttons: [
	        {text: 'Aceptar', style: 'info',   close: true, click:scN },
	        
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

function scN(){ }

function loadResource(resource){	
  window.location.href=resource+".html";// tipo myme of resource	
}

var ctxLLanta;

function mountFocus(obj){
	ctxLLanta=obj;
	setTimeout(function() {findProfundidaLabrado()}, 400);		
}

function setFocus(obj){
	if(obj=="llDent"){		
		if(document.getElementById('profLabrDD1').value.length<1 ){
	      $("#profLabrDD1").focus();
	      return;
		}
		if(document.getElementById('profLabrDD2').value.length<1 ){
		   $("#profLabrDD2").focus();
		   return;
		}
		if(document.getElementById('profLabrDD3').value.length<1 ){
		   $("#profLabrDD3").focus();
		   return;
		}
		if(document.getElementById('profLabrDD4').value.length<1 ){
			$("#profLabrDD4").focus();
			return;
		}
		if(document.getElementById('profLabrDI1').value.length<1 ){
		      $("#profLabrDI1").focus();
		      return;
			}
			if(document.getElementById('profLabrDI2').value.length<1 ){
			   $("#profLabrDI2").focus();
			   return;
			}
			if(document.getElementById('profLabrDI3').value.length<1 ){
			   $("#profLabrDI3").focus();
			   return;
			}
			if(document.getElementById('profLabrDI4').value.length<1 ){
				$("#profLabrDI4").focus();
				return;
			}
	}else{
		
		if(document.getElementById('profLabrTD1').value.length<1 ){
		      $("#profLabrTD1").focus();
		      return;
			}
			if(document.getElementById('profLabrTD2').value.length<1 ){
			   $("#profLabrTD2").focus();
			   return;
			}
			if(document.getElementById('profLabrTD3').value.length<1 ){
			   $("#profLabrTD3").focus();
			   return;
			}
			if(document.getElementById('profLabrTD4').value.length<1 ){
				$("#profLabrTD4").focus();
				return;
			}
			if(document.getElementById('profLabrTI1').value.length<1 ){
			      $("#profLabrTI1").focus();
			      return;
				}
				if(document.getElementById('profLabrTI2').value.length<1 ){
				   $("#profLabrTI2").focus();
				   return;
				}
				if(document.getElementById('profLabrTI3').value.length<1 ){
				   $("#profLabrTI3").focus();
				   return;
				}
				if(document.getElementById('profLabrTI4').value.length<1 ){
					$("#profLabrTI4").focus();
					return;
				}
	}	
} 

function scF(){
	window.location.href="menuWelcome.html";
}
 
function sc(){
	window.location.href="menuWelcome.html";	
}
function goToMenuLiviano(){
	window.location.href="menuPrincipalSART.html";	
}

window.onload = function() {
	var entidad = "existenciaSession";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = "";
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respExistenciaSession', 'get', 0);
}
function respExistenciaSession(resp){	
	console.log('V '+resp);
	if(resp==0){
		optionSession.message ="Disculpe, su session a expirado por inactividad ..!";
		optionSession.title ="Sart 2.0";
		eModal.alert(optionSession);		
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
			document.getElementById('fkUsuario').value=atrSeg[0];			
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

function respGetCtxPruebas(resp) {
	
	if (resp.length > 2) {
		var flujoCtxPrueba = resp.split(";");
		document.getElementById('nroTurno').innerHTML = flujoCtxPrueba[3];
		document.getElementById('condicion').innerHTML = flujoCtxPrueba[4];
		document.getElementById('TIPO').innerHTML = flujoCtxPrueba[5];		
								
		alert(flujoCtxPrueba[10]);
		alert(flujoCtxPrueba[11]);
		if(flujoCtxPrueba[10]=="true") {
		   alert(" si esta marcado como ensenan");
		   document.getElementById("fucEnsenanza").style.display='visible';				
		}
		
		
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
		document.getElementById('nroPrueba').value = flujoCtxPrueba[1]
		var str = flujoCtxPrueba[0].substring(0, 3) + "-"+ flujoCtxPrueba[0].substring(3, 6);
		ctxPlaca = flujoCtxPrueba[0];
		contexto.font = "bold 18px sans-serif";
		contexto.fillText(str, 8, 32);
		contexto.closePath();
		ctxHojaPrueba = flujoCtxPrueba[7];
		var entidad = "viewControlSectLiviano";
		var cadenaAtributos, streaming = "";
		cadenaAtributos = "";
		streaming = entidad + "|" + cadenaAtributos;	
		doAjax('./ServicioBasicoControler.do', streaming,'respViewControlSectLiviano', 'get', 0);
	} else {
		if (resp == "2") {
			options.message = "Disculpe; su SESSION DE USUARIO se ha cerrado Por Inactividad ..!";
			callFunction = "irIndex";
			eModal.alert(options);
			return;
		}
		if (resp == "0") {
			options.message = "Disculpe; he Presentado Problemas al momento de cargar Las hojas de Pruebas Pendientes.!";
		}
		eModal.alert(options);
	}
}
var particion1Liviano=false;
var particion2Liviano=false;
var particion3Liviano=false;
var particion4Liviano=false;
var particion5Liviano=false;
var seccionProfundidad=false;
function respViewControlSectLiviano(resp) {
	console.log('Validando finish of seccion  '+resp);
	var flujoSecction = resp.split(";");
	
	if (flujoSecction[0]=="1") {
		console.log('entro a settera seccion  '+resp);
		var secc =document.getElementById('llDentLiviano');
		var secc2=document.getElementById('llTrasLiviano');		
		secc.setAttribute("class", "st0");
		secc2.setAttribute("class", "st0");
		seccionProfundidad=true;
	}
	

	if (flujoSecction[1]=="1") {		
		var secc =document.getElementById('AlumbradoSen');
		var secc2=document.getElementById('micasTras');		
		var secc3=document.getElementById('micasDelantDer');
		var secc4=document.getElementById('micasDelantIzq');		
		var secc5=document.getElementById('interiorLiviano');		
		secc.setAttribute("class", "st0");
		secc2.setAttribute("class", "st0");
		secc3.setAttribute("class", "st0");
		secc4.setAttribute("class", "st0");
		secc5.setAttribute("class", "st0");		
		particion1Liviano=true;
	}
	
	if (flujoSecction[2]=="1") {		
		var secc =document.getElementById('direccion');		
		secc.setAttribute("class", "st0");		
		particion2Liviano=true;
	}
	
	if (flujoSecction[3]=="1") {		
		var secc =document.getElementById('sistFrenoSuspension1');				
		var secc2=document.getElementById('sistFrenoSuspension2');
		secc.setAttribute("class", "st0");
		secc2.setAttribute("class", "st0");	
		particion3Liviano=true;
	}
	
	if (flujoSecction[4]=="1") {		
		var secc =document.getElementById('Motor');				
		var secc2=document.getElementById('Motor1');
		var secc3=document.getElementById('MotorTanqCombustible');
		secc.setAttribute("class", "st0");
		secc2.setAttribute("class", "st0");	
		secc3.setAttribute("class", "st0");
		particion4Liviano=true;
	}
	
	if (flujoSecction[5]=="1") {		
		var secc =document.getElementById('exteriorLiviano');		
		secc.setAttribute("class", "st0");			
		particion5Liviano=true;
	}
	
	/*
	if (flujoSecction[2]=="1") {
		var secc =document.getElementById('exterior1');
		var secc2=document.getElementById('exterior2');		
		var secc3=document.getElementById('exterior3');
		secc.setAttribute("class", "st0");
		secc2.setAttribute("class", "st0");	
		secc3.setAttribute("class", "st0");
		
	}
	if (entidad.equalsIgnoreCase("setParticion1Liviano")) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			respuestaEntidad = "2";
		} else {
			session.setAttribute("particionLiviano1","1");
			respuestaEntidad = "entro";
		}				
	}
	if (flujoSecction[3]=="1") {
		var secc =document.getElementById('llDent');
		var secc2=document.getElementById('llTras');		
		secc.setAttribute("class", "st0");
		secc2.setAttribute("class", "st0");			
		seccionProfundidad=true;
	}*/
}

function registrarComentario(){
	var entidad = "servRegObservacionPruebas";	
	var cadenaAtributos, streaming = "";
	cadenaAtributos = document.getElementById("nroPrueba").value+";"+regProfundidad+"obs"+document.getElementById("comentarios").value;
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respServFindComentarioPruebas', 'get', 0);	
}

function respServFindComentarioPruebas(resp){	
	if (resp == "2") {
		options.message = "Disculpe; su SESSION DE USUARIO se ha cerrado Por Inactividad ..!";
		callFunction = "irIndex";
		eModal.alert(options);
		return;
	}
}


function findProfundidaLabrado(){
	var entidad = "servFindEstadoPruebas";	
	console.log('servFindEstadoPruebas  ');
	var cadenaAtributos, streaming = "";
	cadenaAtributos = document.getElementById("nroPrueba").value;
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respServFindProfundidaLabrado', 'get', 0);	
}

function respServFindProfundidaLabrado(resp){
	console.log('servFindEstadoPruebas  '+resp);	
		
	if (resp == "2") {
		options.message = "Disculpe; su SESSION DE USUARIO se ha cerrado Por Inactividad ..!";
		callFunction = "irIndex";
		eModal.alert(options);
		return;
	}
	if (resp.length > 3) {
		var flujoSecction = resp.split(";");
		var lstObs = flujoSecction[1].split("obs");		
		if (lstObs.length> 1) {
			regProfundidad= lstObs[0];			
			console.log('respLabrado  '+flujoSecction[2]);
			if (flujoSecction[2]!= "x") {
				var flujoLabrado = flujoSecction[2].split(",");
			    document.getElementById("profLabrDD1").value=flujoLabrado[0];
			    document.getElementById("profLabrDD2").value=flujoLabrado[1];
			    document.getElementById("profLabrDD3").value=flujoLabrado[2];
			    document.getElementById("profLabrDD4").value=flujoLabrado[3];			
			    document.getElementById("profLabrDI1").value=flujoLabrado[4];
			    document.getElementById("profLabrDI2").value=flujoLabrado[5];
			    document.getElementById("profLabrDI3").value=flujoLabrado[6];
			    document.getElementById("profLabrDI4").value=flujoLabrado[7];			    
			    document.getElementById("profLabrTD1").value=flujoLabrado[8];
			    document.getElementById("profLabrTD2").value=flujoLabrado[9];
			    document.getElementById("profLabrTD3").value=flujoLabrado[10];
			    document.getElementById("profLabrTD4").value=flujoLabrado[11];			
			    document.getElementById("profLabrTI1").value=flujoLabrado[12];
			    document.getElementById("profLabrTI2").value=flujoLabrado[13];
			    document.getElementById("profLabrTI3").value=flujoLabrado[14];
			    document.getElementById("profLabrTI4").value=flujoLabrado[15];			    
			    var refThis = document.getElementById("llDentLiviano");
				refThis.setAttribute("class", "st0");
				var refThis1 = document.getElementById("llTrasLiviano");
			    refThis1.setAttribute("class", "st0");
			    seccionProfundidad=true;
			}else{
				setFocus(ctxLLanta);				
			}
		}else{
			if (flujoSecction[2]!= "x") {
				var flujoLabrado = flujoSecction[2].split(",");
				document.getElementById("profLabrDD1").value=flujoLabrado[0];
			    document.getElementById("profLabrDD2").value=flujoLabrado[1];
			    document.getElementById("profLabrDD3").value=flujoLabrado[2];
			    document.getElementById("profLabrDD4").value=flujoLabrado[3];			
			    document.getElementById("profLabrDI1").value=flujoLabrado[4];
			    document.getElementById("profLabrDI2").value=flujoLabrado[5];
			    document.getElementById("profLabrDI3").value=flujoLabrado[6];
			    document.getElementById("profLabrDI4").value=flujoLabrado[7];			    
			    document.getElementById("profLabrTD1").value=flujoLabrado[8];
			    document.getElementById("profLabrTD2").value=flujoLabrado[9];
			    document.getElementById("profLabrTD3").value=flujoLabrado[10];
			    document.getElementById("profLabrTD4").value=flujoLabrado[11];			
			    document.getElementById("profLabrTI1").value=flujoLabrado[12];
			    document.getElementById("profLabrTI2").value=flujoLabrado[13];
			    document.getElementById("profLabrTI3").value=flujoLabrado[14];
			    document.getElementById("profLabrTI4").value=flujoLabrado[15];
			    var refThis = document.getElementById("llDentLiviano");
				refThis.setAttribute("class", "st0");
				var refThis1 = document.getElementById("llTrasLiviano");
			    refThis1.setAttribute("class", "st0");
			    seccionProfundidad=true;
			}else{
				setFocus(ctxLLanta);				
			}			
		}
	}
}


function findComentario(){
	var entidad = "servFindEstadoPruebas";	
	console.log('servFindEstadoPruebas  ');
	var cadenaAtributos, streaming = "";
	cadenaAtributos = document.getElementById("nroPrueba").value;
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respServFindComentarioPruebas', 'get', 0);	
}
var regProfundidad="";
function respServFindComentarioPruebas(resp){	
	console.log('servFindEstadoPruebas  '+resp);
	if (resp == "2") {
		options.message = "Disculpe; su SESSION DE USUARIO se ha cerrado Por Inactividad ..!";
		callFunction = "irIndex";
		eModal.alert(options);
		return;
	}
	if (resp.length > 3) {
		var flujoSecction = resp.split(";");
		var lstObs = flujoSecction[1].split("obs");		
		if (lstObs.length> 1) {
			regProfundidad= lstObs[0]; 
			document.getElementById("comentarios").value= lstObs[1]; 
		}
	}
}

function cerrarPrueba(){
	var envClosePrueba=true;
	if (seccionProfundidad==false) {
		envClosePrueba=false;
	}	
	
	if (particion1Liviano==false) {
		envClosePrueba=false;
	}
	
	if (particion2Liviano==false) {
		envClosePrueba=false;
	}
	
	if (particion3Liviano==false) {
		envClosePrueba=false;
	}
	if (particion4Liviano==false) {
		envClosePrueba=false;
	}
	if (particion5Liviano==false) {
		envClosePrueba=false;
	}
	
	
	if (envClosePrueba==false) {
		optionValidador.message = "Disculpe; No Puedo Concluir La Prueba Sesorial Debido a que no se han Revisado Completamente.!";
		optionValidador.subtitle = " Validacion"
		eModal.alert(optionValidador);	
	}else{
		var entidad = "setCierrePrueba";
		var cadenaAtributos, streaming = "";
		cadenaAtributos = document.getElementById("nroPrueba").value+";"+document.getElementById("fkUsuario").value;
		streaming = entidad + "|" + cadenaAtributos;
		doAjax('./ServicioBasicoControler.do', streaming,'respSetCierrePrueba', 'get', 0);
	}
}
function respSetCierrePrueba(resp) {
	optionValidador.title = "Sart 2.0";
	if (resp == 1 ) {		
		optionValidador.subtitle = "Confirmacion De Recepcion"
		optionValidador.message = "se ha CERRADO la Prueba Sensorial de una Manera Exitosa...!";
		
	} else {
		optionValidador.subtitle = "Problemas Encontrados"
		optionValidador.message = "Disculpe; he Presentado Problemas al Momento de CERRAR la Prueba Sensorial ..!";	
	}	
	eModal.alert(optionValidador);
	setTimeout(function() {goToMenuMoto()}, 800);
}

function beta(e) {
	key = e.KeyCode || e.which;	
	if (key == 8 || key == 0 ) {
		return true;
	}	
	teclado = String.fromCharCode(key);
	numeros = "0123456789";
	especiales = "8-46";
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
var validDD=false;
var validTD=false;
function cargaProfundidad(obj) {
	var refThis = document.getElementById(obj);
	
	var txtDD1 = document.getElementById("profLabrDD1");
	var txtDD2 = document.getElementById("profLabrDD2");
	var txtDD3 = document.getElementById("profLabrDD3");
	var txtDD4 = document.getElementById("profLabrDD4");
	
	var txtDI1 = document.getElementById("profLabrDI1");
	var txtDI2 = document.getElementById("profLabrDI2");
	var txtDI3 = document.getElementById("profLabrDI3");
	var txtDI4 = document.getElementById("profLabrDI4");
	
	var txtTD1 = document.getElementById("profLabrTD1");
	var txtTD2 = document.getElementById("profLabrTD2");
	var txtTD3 = document.getElementById("profLabrTD3");
	var txtTD4 = document.getElementById("profLabrTD4");
	
	var txtTI1 = document.getElementById("profLabrTI1");
	var txtTI2 = document.getElementById("profLabrTI2");
	var txtTI3 = document.getElementById("profLabrTI3");
	var txtTI4 = document.getElementById("profLabrTI4");
	
	
	if (obj == "llDentLiviano") {
		if (txtDD1.value.length == 0) {			
			refThis.setAttribute("class", "st1");
			validDD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(1) Delantera Derecha.!";
			eModal.alert(optionValidador);
			return;
		}		
		if (txtDD2.value.length == 0) {			
			refThis.setAttribute("class", "st1");
			validDD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(2) Delantera Derecha.!";
			eModal.alert(optionValidador);
			return;
		}		
		if (txtDD3.value.length == 0) {			
			refThis.setAttribute("class", "st1");
			validDD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(3) Delantera Derecha.!";
			eModal.alert(optionValidador);
			return;
		}		
		if (txtDD4.value.length == 0) {			
			refThis.setAttribute("class", "st1");
			validDD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(4) Delantera Derecha.!";
			eModal.alert(optionValidador);
			return;
		}
		
		if (txtDI1.value.length == 0) {			
			refThis.setAttribute("class", "st1");
			validDD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(1) Delantera Izquierda.!";
			eModal.alert(optionValidador);
			return;
		}		
		if (txtDI2.value.length == 0) {			
			refThis.setAttribute("class", "st1");
			validDD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(2) Delantera Izquierda.!";
			eModal.alert(optionValidador);
			return;
		}		
		if (txtDI3.value.length == 0) {			
			refThis.setAttribute("class", "st1");
			validDD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(3) Delantera Izquierda.!";
			eModal.alert(optionValidador);
			return;
		}		
		if (txtDI4.value.length == 0) {			
			refThis.setAttribute("class", "st1");
			validDD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(4) Delantera Izquierda.!";
			eModal.alert(optionValidador);
			return;
		}		
		refThis.setAttribute("class", "st0");
		validDD = true;
		
	}
		
	if (obj == "llTrasLiviano") {
		if (txtTD1.value.length == 0) {
			validTD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(1) Trasera Derecha.!";
			eModal.alert(optionValidador);
			return;
		}		
		if (txtTD2.value.length == 0) {
			validTD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(2) Trasera Derecha.!";
			eModal.alert(optionValidador);
			return;
		}		
		if (txtTD3.value.length == 0) {
			validTD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(3) Trasera Derecha.!";
			eModal.alert(optionValidador);
			return;
		}
		if (txtTD4.value.length == 0) {
			validTD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(4) Trasera Derecha.!";
			eModal.alert(optionValidador);
			return;
		}
		
		if (txtTI1.value.length == 0) {
			validTD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(1) Trasera Izquierda.!";
			eModal.alert(optionValidador);
			return;
		}		
		if (txtTI2.value.length == 0) {
			validTD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(2) Trasera Izquierda.!";
			eModal.alert(optionValidador);
			return;
		}		
		if (txtTI3.value.length == 0) {
			validTD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(3) Trasera Izquierda.!";
			eModal.alert(optionValidador);
			return;
		}
		if (txtTI4.value.length == 0) {
			validTD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(4) Trasera Izquierda.!";
			eModal.alert(optionValidador);
			return;
		}
		
		var refThis = document.getElementById(obj);
		refThis.setAttribute("class", "st0");
		validTD = true;
	}
	if (validTD == true && validDD ==true) {
		//** LOGICA DE TRASERA DERECHA
		var minTD;
		if (parseInt(txtTD1.value)< parseInt(txtTD2.value)) {
			minTD=txtTD1.value;
			console.log('txtTD  is menor '+minTD);
		}else{
			minTD=txtTD2.value;
			console.log('txtTraTD is menor '+minTD);
		}
		
		if (parseInt(minTD)> parseInt(txtTD3.value)) {
			minTD=txtTD3.value;
		}
		console.log('txtTraTD   menor is '+minTD);
		if (parseInt(minTD)> parseInt(txtTD4.value)) {
			minTD=txtTD4.value;		
		}
		console.log('txtTraTD  menor is '+minTD);
		//** FIN DE LOGICA DEL MINIMO EN TRASERA DERECHA
		
		//** LOGICA DE TRASERA IZQUIERDA
		var minTI;
		if (parseInt(txtTI1.value)< parseInt(txtTI2.value)) {
			minTI=txtTI1.value;
			console.log('txtTI  is menor '+minTI);
		}else{
			minTI=txtTI2.value;
			console.log('txtTraTI is menor '+minTI);
		}
		
		if (parseInt(minTI)> parseInt(txtTI3.value)) {
			minTI=txtTI3.value;
		}
		console.log('txtTraTI   menor is '+minTI);
		if (parseInt(minTI)> parseInt(txtTI4.value)) {
			minTI=txtTI4.value;		
		}
		console.log('txtTraTD  menor is '+minTI);
		//** FIN DE LOGICA DEL MINIMO EN TRASERA DERECHA
		
		
		//** LOGICA DE DELANTERA DERECHA
		var minDD;		
		if (parseInt(txtDD1.value)< parseInt(txtDD2.value)) {
			minDD=txtDD1.value;
		}else{
			minDD=txtDD2.value;
		}
		console.log('txtTraDD  menor is '+minDD);
		
		if (parseInt(minDD)> parseInt(txtDD3.value)) {
			minDD=txtDD3.value;		
		}
		console.log('txtTraDD  menor is '+minDD);
		if (parseInt(minDD)> parseInt(txtDD4.value)) {
			minDD=txtDD4.value;
		}
		//** FIN DE LOGICA DEL MINIMO EN DELANTERA DERECHA
		
		//** LOGICA DE DELANTERA IZQUIERDA
		var minDI;
		if (parseInt(txtDI1.value)< parseInt(txtDI2.value)) {
			minDI=txtDI1.value;
		}else{
			minDI=txtDI2.value;
		}
		console.log('txtTraDD  menor is '+minDI);
		
		if (parseInt(minDI)> parseInt(txtDI3.value)) {
			minDI=txtDI3.value;		
		}
		console.log('txtTraDI  menor is '+minDI);
		if (parseInt(minDI)> parseInt(txtDI4.value)) {
			minDI=txtDI4.value;
		}
		//** FIN DE LOGICA DEL MINIMO EN DELANTERA DERECHA
		
		console.log('txtTraDD  menor is '+minDI);		
		var entidad = "serPersitProfLabradoLiviano";
		var cadenaAtributos, streaming = "";		
		cadenaAtributos = document.getElementById("nroPrueba").value+"/"+txtDD1.value+","+txtDD2.value+","+txtDD3.value+","+txtDD4.value+","+txtDI1.value+","+txtDI2.value+","+txtDI3.value+","+txtDI4.value+","+ txtTD1.value+","+txtTD2.value+","+txtTD3.value+","+txtTD4.value +","+ txtTI1.value+","+txtTI2.value+","+txtTI3.value+","+txtTI4.value +"!"+minDD+"$"+minDI+"$"+minTD+"$"+minTI +"$";
		console.log(' is '+cadenaAtributos);
		streaming = entidad + "|" + cadenaAtributos;		
		doAjax('./ServicioBasicoControler.do', streaming,'respSerPersitProfLabrado', 'get', 0);
		
	}    
}
function respSerPersitProfLabrado(resp) {
	optionValidador.title = "Sart 2.0";
	console.log('eve 5 '+resp);
	if (resp == 1 ) {
		seccionProfundidad=true;
		var entidad = "setSeccionLabradoLiviano";
		var cadenaAtributos, streaming = "";
		cadenaAtributos = "";
		streaming = entidad + "|" + cadenaAtributos;	
		doAjax('./ServicioBasicoControler.do', streaming,'respsetSeccionLabrado', 'get', 0);
	} else {
		optionValidador.subtitle = "Problemas Encontrados"
		optionValidador.message = "Disculpe; he Presentado Problemas al Momento de Registrar la Profundidad de Labrado.!";
		eModal.alert(optionValidador);
	}		
}

function respsetSeccionLabrado(resp) {
	
}