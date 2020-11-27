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
		if(document.getElementById('profLabrMotoDe1').value.length<1 ){
	      $("#profLabrMotoDe1").focus();
	      return;
		}
		if(document.getElementById('profLabrMotoDe2').value.length<1 ){
		   $("#profLabrMotoDe2").focus();
		   return;
		}
		if(document.getElementById('profLabrMotoDe3').value.length<1 ){
		   $("#profLabrMotoDe3").focus();
		   return;
		}
		if(document.getElementById('profLabrMotoDe4').value.length<1 ){
			$("#profLabrMotoDe4").focus();
			return;
		}
	}else{
		if(document.getElementById('profLabrMotoTra1').value.length<1 ){
			$("#profLabrMotoTra1").focus();
			return;
		}
		if(document.getElementById('profLabrMotoTra2').value.length<1 ){
		   $("#profLabrMotoTra2").focus();
		   return;
		}
		if(document.getElementById('profLabrMotoTra3').value.length<1 ){
		   $("#profLabrMotoTra3").focus();
		   return;
		}
		if(document.getElementById('profLabrMotoTra4').value.length<1 ){
			$("#profLabrMotoTra4").focus();
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
function goToMenuMoto(){
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
			console.log('user '+atrSeg[0]);
			console.log('user value '+document.getElementById('fkUsuario').value);
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
		var str = flujoCtxPrueba[0].substring(0, 3) + "-"
				+ flujoCtxPrueba[0].substring(3, 6);
		ctxPlaca = flujoCtxPrueba[0];
		contexto.font = "bold 18px sans-serif";
		contexto.fillText(str, 8, 32);
		contexto.closePath();
		ctxHojaPrueba = flujoCtxPrueba[7];
		var entidad = "viewControlSectMoto";
		var cadenaAtributos, streaming = "";
		cadenaAtributos = "";
		streaming = entidad + "|" + cadenaAtributos;	
		doAjax('./ServicioBasicoControler.do', streaming,'respViewControlSectMoto', 'get', 0);
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
var seccionFrenoMoto=false;
var seccionProfundidad=false;
var seccionMecanizada=false;
var seccionExterior=false;
function respViewControlSectMoto(resp) {
	console.log('Validando finish of seccion  '+resp);
	var flujoSecction = resp.split(";");
	if (flujoSecction[0]=="1") {
		console.log('entro a settera seccion  '+resp);
		var secc =document.getElementById('sistFrenoSuspension');
		var secc2=document.getElementById('frenoDiscoTrasero');		
		secc.setAttribute("class", "st0");
		secc2.setAttribute("class", "st0");
		seccionFrenoMoto=true;
	}
	if (flujoSecction[1]=="1") {		
		var secc =document.getElementById('sistMecanizado');				
		secc.setAttribute("class", "st0");		
		seccionMecanizada=true;
	}
	if (flujoSecction[2]=="1") {
		var secc =document.getElementById('exterior1');
		var secc2=document.getElementById('exterior2');		
		var secc3=document.getElementById('exterior3');
		secc.setAttribute("class", "st0");
		secc2.setAttribute("class", "st0");	
		secc3.setAttribute("class", "st0");
		seccionExterior=true;
	}
	if (flujoSecction[3]=="1") {
		var secc =document.getElementById('llDent');
		var secc2=document.getElementById('llTras');		
		secc.setAttribute("class", "st0");
		secc2.setAttribute("class", "st0");			
		seccionProfundidad=true;
	}
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
			    document.getElementById("profLabrMotoDe1").value=flujoLabrado[0];
			    document.getElementById("profLabrMotoDe2").value=flujoLabrado[1];
			    document.getElementById("profLabrMotoDe3").value=flujoLabrado[2];
			    document.getElementById("profLabrMotoDe4").value=flujoLabrado[3];			
			    document.getElementById("profLabrMotoTra1").value=flujoLabrado[4];
			    document.getElementById("profLabrMotoTra2").value=flujoLabrado[5];
			    document.getElementById("profLabrMotoTra3").value=flujoLabrado[6];
			    document.getElementById("profLabrMotoTra4").value=flujoLabrado[7];
			}else{
				setFocus(ctxLLanta);				
			}
		}else{
			if (flujoSecction[2]!= "x") {
				var flujoLabrado = flujoSecction[2].split(",");
			    document.getElementById("profLabrMotoDe1").value=flujoLabrado[0];
			    document.getElementById("profLabrMotoDe2").value=flujoLabrado[1];
			    document.getElementById("profLabrMotoDe3").value=flujoLabrado[2];
			    document.getElementById("profLabrMotoDe4").value=flujoLabrado[3];			
			    document.getElementById("profLabrMotoTra1").value=flujoLabrado[4];
			    document.getElementById("profLabrMotoTra2").value=flujoLabrado[5];
			    document.getElementById("profLabrMotoTra3").value=flujoLabrado[6];
			    document.getElementById("profLabrMotoTra4").value=flujoLabrado[7];
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
	if (seccionFrenoMoto==false) {
		envClosePrueba=false;
	}
	if (seccionProfundidad==false) {
		envClosePrueba=false;
	}
	if (seccionMecanizada==false) {
		envClosePrueba=false;
	}
	if (seccionExterior==false) {
		envClosePrueba=false;
	}	
	
	console.log('estatutus of seccionFrenoMoto  '+seccionFrenoMoto);
	console.log('estatutus of seccionProfundidad  '+seccionProfundidad);
	console.log('estatutus of seccionMecanizada  '+seccionMecanizada);
	console.log('estatutus of seccionExterior '+seccionExterior);
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
	var txtDen1 = document.getElementById("profLabrMotoDe1");
	var txtDen2 = document.getElementById("profLabrMotoDe2");
	var txtDen3 = document.getElementById("profLabrMotoDe3");
	var txtDen4 = document.getElementById("profLabrMotoDe4");	
	if (obj == "llDent") {
		if (txtDen1.value.length == 0) {			
			refThis.setAttribute("class", "st1");
			validDD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(1) Delantera.!";
			eModal.alert(optionValidador);
			return;
		}		
		if (txtDen2.value.length == 0) {			
			refThis.setAttribute("class", "st1");
			validDD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(2) Delantera.!";
			eModal.alert(optionValidador);
			return;
		}		
		if (txtDen3.value.length == 0) {			
			refThis.setAttribute("class", "st1");
			validDD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(3) Delantera.!";
			eModal.alert(optionValidador);
			return;
		}		
		if (txtDen4.value.length == 0) {			
			refThis.setAttribute("class", "st1");
			validDD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(4) Delantera.!";
			eModal.alert(optionValidador);
			return;
		} 
		refThis.setAttribute("class", "st0");
		validDD = true;
		
	}
	var txtTra1 = document.getElementById("profLabrMotoTra1");
	var txtTra2 = document.getElementById("profLabrMotoTra2");
	var txtTra3 = document.getElementById("profLabrMotoTra3");
	var txtTra4 = document.getElementById("profLabrMotoTra4");	
	if (obj == "llTras") {
		if (txtTra1.value.length == 0) {
			validTD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(1) Trasera.!";
			eModal.alert(optionValidador);
			return;
		}		
		if (txtTra2.value.length == 0) {
			validTD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(2) Trasera.!";
			eModal.alert(optionValidador);
			return;
		}		
		if (txtTra3.value.length == 0) {
			validTD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(3) Trasera.!";
			eModal.alert(optionValidador);
			return;
		}
		if (txtTra4.value.length == 0) {
			validTD = false;
			optionValidador.message = "Disculpe; no ha Colocado un valor Valido a la Profundidad(4) Trasera.!";
			eModal.alert(optionValidador);
			return;
		}
		var refThis = document.getElementById(obj);
		refThis.setAttribute("class", "st0");
		validTD = true;
	}
	if (validTD == true && validDD ==true) {
		var minTr;
		if (parseInt(txtTra1.value)< parseInt(txtTra2.value)) {
			minTr=txtTra1.value;
			console.log('txtTraTD  is menor '+minTr);
		}else{
			minTr=txtTra2.value;
			console.log('txtTraTD is menor '+minTr);
		}
		
		if (parseInt(minTr)> parseInt(txtTra3.value)) {
			minTr=txtTra3.value;
		}
		console.log('txtTraTD   menor is '+minTr);
		if (parseInt(minTr)> parseInt(txtTra4.value)) {
			minTr=txtTra4.value;		
		}
		console.log('txtTraTD  menor is '+minTr);
		var minDr;
		if (parseInt(txtDen1.value)< parseInt(txtDen2.value)) {
			minDr=txtDen1.value;
		}else{
			minDr=txtDen2.value;
		}
		console.log('txtTraDD  menor is '+minDr);
		
		if (parseInt(minDr)> parseInt(txtDen3.value)) {
			minDr=txtDen3.value;		
		}
		console.log('txtTraDD  menor is '+minDr);
		if (parseInt(minDr)> parseInt(txtDen4.value)) {
			minDr=txtDen4.value;
		}
		console.log('txtTraDD  menor is '+minDr);		
		var entidad = "serPersitProfLabrado";
		var cadenaAtributos, streaming = "";		
		cadenaAtributos = document.getElementById("nroPrueba").value+"/"+txtDen1.value+","+txtDen2.value+","+txtDen3.value+","+txtDen4.value+","+txtTra1.value+","+txtTra2.value+","+txtTra3.value+","+txtTra4.value +"!"+minDr+"&"+minTr+"$";
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
		var entidad = "setSeccionLabrado";
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