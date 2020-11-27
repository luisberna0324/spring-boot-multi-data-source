var envTrama = false;
var faseRev = 1;
var tramaPase;
var eve;
var iframe;
window.onload = function() {
	/*iframe = document.getElementById("iframes");
	iframe.src = "welcome.html";
	$("#iframes").css({
		height : "590px",
		width : "90%"
	});*/
    
	var entidad = "existenciaSession";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = "";
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respExistenciaSession', 'get', 0);	
}


function findPlaca(){
  if(document.getElementById("nroPlacas").value.length>3){
	  var entidad = "exitPlaca";
	  var cadenaAtributos, streaming = "";
	  cadenaAtributos = document.getElementById("nroPlacas").value;
	  streaming = entidad + "|" + cadenaAtributos;	
	  doAjax('./ServicioProducto.do', streaming,'respPlacaFind', 'get', 0);
  }else{	  
	  alert("Disculpe, La Placa Digitada no esta COMPLETA");
	  document.getElementById("nroPlacas").focus();
  }    
}
function respPlacaFind(resp){
	if(parseInt(resp) > 0){
	   var formElement = document.getElementById("findPlaca");
	   document.getElementById("data").value= document.getElementById("nroPlacas").value.toUpperCase();
	   document.getElementById("entidad").value="preRevision";
	   formElement.submit();
	}else{
	  alert("Disculpe, La Placa Digitada no se encuentra Registrada");
	  document.getElementById("nroPlacas").focus();
	}	
}
function viewAnaliTest(){	
	   var formElement = document.getElementById("frmViewAnaliTest");      
	   formElement.submit();	
	}

function viewEstaRtm(){	
   var formElement = document.getElementById("frmViewEstadistica");      
   formElement.submit();	
}

function viewMovRTM(){	
	   var formElement = document.getElementById("frmViewMovRTM");      
	   formElement.submit();	
	}

function viewInforRevi(){	
   var formElement = document.getElementById("frmViewInforRevi");      
	formElement.submit();
}

function viewRecPista(){	
   var formElement = document.getElementById("frmViewInforRecPista");      
   formElement.submit();
}

function resp(resp){
	
}

function menuPrincipalSART(){
	window.location.href="menuPrincipalSART.html";
}

function servFindFunciones(){
	window.location.href="funcionesConsolaEntrada.html";
}

function viewFrmCda(){
	window.location.href="PanelConfiguracionCda.html";
}

function naturalezaRevision(){
	window.location.href="naturalezaRevision.html";
}

function respExistenciaSession(resp){
	if(resp==0){
		window.location.href="index.html";		
	}else{
		var atrSeg = resp.split(";");
		document.getElementById("nombreUsuario").innerHTML = "   " + atrSeg[1];				
		if (atrSeg[3] == "Y") {
			document.getElementById("nivel").innerHTML = "  Administrador";
		} else {
			document.getElementById("nivel").innerHTML = "  Estandar";
		}
		document.getElementById("nick").innerHTML = " " + atrSeg[2];
		document.getElementById("identificador").value = atrSeg[0];		
		var ruta = "./imagenes/fotosEmpresa/" + atrSeg[0] + ".png";		
		document.getElementById('fotoUsuario').setAttribute('src', ruta);
		}
}
function fixBinary (bin) {
	var length = bin.length;
	var buf = new ArrayBuffer(length);
	var arr = new Uint8Array(buf);
	for (var i = 0; i < length; i++) {
    	arr[i] = bin.charCodeAt(i);
	}
	return buf;
}
function respFindAvatar (resp){
	alert(resp)
	console.log("raw "+resp);	
}

function beginProcesoPreRevision(){
	//window.location.href="PreReVehicular.html";
	window.location.href="OrdenPreRevision.html";
}
function respBeginProcesoPreRevision(resp){
	if(parseInt(resp)!=0){
	   window.location.href="FormRunt.html";
	}
}

function resp(resp) {
	try {
		iframe = document.getElementById("iframes");		
		var doc = iframe.contentWindow.document;
		var jah = doc.getElementsByClassName("col-xs-12 col-md-3 col-sm-3 show-grande ng-binding");		
		var i;
		for (i = 0; i < 40; i++) {
			alert(jah[i].innerHTML);
		}
	} catch (e) {
		alert(e); // Security Error (another origin)
	}
}

function cierreSession(){
	var entidad = "closeSession";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = "";
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respCloseSession', 'get', 0);
}
function respCloseSession(resp){	
	window.location.href="index.html";		
}

function callResourceRunt() {
	try {
		var streaming = "tt";		
		doAjax('./ServicioBasicoControler.do', streaming, 'resp', 'get', 0);
	} catch (e) {
		alert(e); // Security Error (another origin)
	}
}
function asigFocus() {
	if ($("#phase").val() == 1) {
		var streaming = document.getElementById("streamingDocPropiedad");
		streaming.focus();
	}
	if ($("#phase").val() == 2) {
		var streaming = document.getElementById("streamingDocPase");
		streaming.focus();
	}
	clearTimeout(asigFocus);
}

function regEventSend() {
	if (envTrama == false) {
		envTrama = true;
		setTimeout(function() {envioTramaAlServidor()}, 5000);
	}
}
function regEventSend2() {
	if (document.getElementById("streamingDocPase").value.length == 110) {
		tramaPase = document.getElementById("streamingDocPase").value;
		setTimeout(function() {envioTramaAlServidor2()}, 100);
	}
	if (document.getElementById("streamingDocPase").value.length > 99) {
		document.getElementById("streamingDocPase").value = document.getElementById("streamingDocPase").value.replace(/[^a-zA-Z 0-9.]+/g, ' ');
		document.getElementById("streamingDocPase").value = "";
	}
}
function asig() {
	setTimeout(function() {asigFocus()}, 1000);
}
function asigAux() {
	if (document.getElementById("streamingDocPropiedad").value.length == 0) {
		setTimeout(function() { asigFocus()	}, 500);
	}
}
function even() {
	if ($("#phase").val() == 1) {
		$("#registrarModal").modal({
			show : false
		});
	}
	if ($("#phase").val() == 2) {
		$("#registrarModal02").modal({
			show : true
		});
		asig();
	}
}


function autenti(){
	var options = {
		message : " ",
		title : 'Sart 2.0',
		size : 'sm',
		subtitle : 'Datos Faltantes',
		useBin : false
	};
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
	// alert("Response is "+resp);
	var atrSeg = resp.split(";");
	if (atrSeg[0] == "no") {
		var options = {
			message : "Disculpe, los Datos de Autenticacion de Usuario no son los correctos",
			title : 'Sart 2.0',
			size : 'sm',
			subtitle : 'Autenticacion Fallida',
			useBin : false,
                        overlayColor: "#ffffff",
                        top: 1000
		};
		eModal.alert(options);
		document.getElementById("inputUsuario").focus();
	} else {
		var oCapa = document.getElementById("fondoAutentificacion");
		oCapa.setAttribute("class", "");    capaAutentificacion
		var oCap = document.getElementById("capaAutentificacion");
		oCap.style.display = 'none';
		/*
		 * var ruta ="./imagenes/logoEmpresa"+fkIdentificador[3];
		 * document.getElementById('logoEmpresa').setAttribute('src',ruta);
		 */
		document.getElementById("nombreUsuario").innerHTML = "  " + atrSeg[1];
		var ruta = "./imagenes/fotosEmpresa/" + atrSeg[0] + ".png";
		document.getElementById('fotoUsuario').setAttribute('src', ruta);
		/*
		 * document.getElementById('fotoUsuario').setAttribute("style",
		 * "width:545px");
		 * document.getElementById('fotoUsuario').setAttribute("style",
		 * "height:70px");
		 */
		if (atrSeg[3] == "Y") {
			document.getElementById("nivel").innerHTML = "  Administrador";
		} else {
			document.getElementById("nivel").innerHTML = "  Estandar";
		}
		document.getElementById("nick").innerHTML = " " + atrSeg[2];
		document.getElementById("identificador").value = atrSeg[0];				
		/*
		 * var x = document.getElementById("iframes"); x.src = "revision.html";
		 */
	}	
}

function doEstudio() {
	var formElement = document.getElementById("estudioTiempo");	
	document.getElementById("entidad").value = "estudioTiempoMuerto";
	formElement.submit();
}

function closeModal() {
	$("#myBtn2").click();
	clearTimeout(closeModal);
}
