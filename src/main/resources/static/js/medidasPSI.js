var envTrama = false;
var faseRev = 1;
var typeReg="auto";
var faseRechazo="0";
var imgPlacaAma = new Image();
var imgPlacaBlac = new Image();
imgPlacaAma.src="./img/PlacaAmarrilla.png";
imgPlacaBlac.src="./img/PlacaBlanca.png";
var claseVehiculo;

var rangoPesado = [90,92,94,95,97,99,100,101,102,104,105,107,109,111,112,113,116,118,119,120];
var rangoAutobuses = [56,57,58,59,60,61,63,64,66,67,69,70,71,72,74,75,77,78,79,80];
var rangoAutomovil = [28,29,30,31,32,33,34,35,36,28,29,30,31,32,33,34,35,36,30,32];
var rangoMotocicleta =[28,29,30,31,32,33,34];
var rangoMotoCarro =[30,31,32,33,34,35];
var rangoCaminioneta =[34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50];
var rangoMotoScoter =[20,21,22,23,24,25,26,27,28];


String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, "");	};
var option = {
		message : "",
		title : 'Sart 2.0',
		size : 'lg',
		subtitle : '',
		useBin : false,
		buttons: [
	        {text: 'ACEPTAR', style: 'info',   close: true, click:sc }	        
	    ]
	};
var optionSession = {
		message : "",
		title : 'Sart 2.0',
		size : 'lg',
		subtitle : ' Datos Faltantes',
		useBin : false,
		buttons: [
	        {text: 'OK', style: 'info',   close: true, click:scF }	        
	    ]
	};
function scF(){
	window.location.href="menuWelcome.html";
}

function sc(){ }


window.onload = function() {
	var entidad = "existenciaSession";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = "";
	streaming = entidad + "|" + cadenaAtributos;
	doAjax('./ServicioBasicoControler.do', streaming,'respExistenciaSession', 'get', 0);
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
}

function respTakeCtxPreRevision(resp){
	
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
	nroEjes=parseInt(atrPreRevision[1]);	
	if(nroEjes==0){		
		nroEjes=2;
	}
	SelectObject = document.getElementById("selectNroEjes");
	  for(index = 0;  index < SelectObject.length;  index++) {    	   
	      if(SelectObject[index].value ==nroEjes){
	   	   SelectObject.selectedIndex = index;
	      }
	  }		
	if(nroEjes >2){
	   $("#eje3Izq").prop("disabled", false);
	   $("#eje3Der").prop("disabled", false);
	}
	if(nroEjes>3){
	   $("#eje4Izq").prop("disabled", false);
	   $("#eje4Der").prop("disabled", false);
	}
	if(nroEjes>4){
	   $("#eje5Izq").prop("disabled", false);
	   $("#eje5Der").prop("disabled", false);
	}
	claseVehiculo =atrPreRevision[3];	
	if( atrPreRevision[3]=="MOTOCICLETA"){
		$("#eje1Izq").prop("disabled", true);
		$("#eje2Izq").prop("disabled", true);
		$("#selectNroEjes").prop("disabled", true);
		$("#rin").prop("selectedIndex", 3);
		document.getElementById('pacha').style.display='none';		
		document.getElementById('scoter').style.display='block';		
		document.getElementById("lblCtx").innerHTML="Scooter";	
		var indice = Math.floor(Math.random() * rangoMotocicleta.length);
		$("#eje2Der").val(rangoMotocicleta[indice+1]);		
		if(document.getElementById('eje2Der').value.length <= 1){
			$("#eje2Der").val(rangoMotocicleta[indice-1]);
		}
		$("#eje1Der").val(rangoMotocicleta[indice]);		
		
	}
	if( atrPreRevision[3]=="MOTOCARRO"){
		$("#eje1Izq").prop("disabled", true);		
		$("#selectNroEjes").prop("disabled", true);
		$("#rin").prop("selectedIndex", 3);
		document.getElementById('pacha').style.display='none';				
		document.getElementById("lblCtx").innerHTML="";
		var indice = Math.floor(Math.random() * rangoMotoCarro.length);
		$("#eje2Izq").val(rangoMotoCarro[indice+1]);
		if(document.getElementById('eje2Izq').value.length <= 1){
			$("#eje2Izq").val(rangoMotocicleta[indice-1]);
		}
		$("#eje2Der").val(rangoMotoCarro[indice]);
		$("#eje1Der").val(rangoMotoCarro[indice]);		
		
	}
	if( atrPreRevision[3]=="AUTOMOVIL"){
		var indice = Math.floor(Math.random() * rangoAutomovil.length);
		$("#eje2Izq").val(rangoAutomovil[indice]);
		$("#eje2Der").val(rangoAutomovil[indice+1]);
		if(document.getElementById('eje2Der').value.length <= 1){
			$("#eje2Der").val(rangoAutomovil[indice-1]);
		}
		$("#eje1Der").val(rangoAutomovil[indice-1]);
		if(document.getElementById('eje1Der').value.length <= 1){
			$("#eje1Der").val(rangoAutomovil[indice+1]);
		}
		$("#eje1Izq").val(rangoAutomovil[indice]);
		document.getElementById('pacha').style.display='none';
		document.getElementById("lblCtx").innerHTML="";
		document.getElementById('eje2IzqInt').
		$("#eje2IzqInt").prop("disabled", false);
		$("#eje2DerInt").prop("disabled", false);
		document.getElementById("lblCtx").innerHTML="";
		$("#selectNroEjes").prop("disabled", true);		
	}
	
	if( atrPreRevision[3]=="BUS" || atrPreRevision[3]=="BUSETA" || atrPreRevision[3]=="MICROBUS"  ){
		var indice = Math.floor(Math.random() * rangoAutobuses.length);
		$("#eje2Izq").val(rangoAutobuses[indice+1]);
		if(document.getElementById('eje2Izq').value.length <= 1){
			$("#eje2Der").val(rangoAutobuses[indice-2]);
		}
		$("#eje2Der").val(rangoAutobuses[indice-1]);
		if(document.getElementById('eje2Der').value.length <= 1){
			$("#eje2Der").val(rangoAutobuses[indice+2]);
		}
		$("#eje1Der").val(rangoAutobuses[indice+1]);
		if(document.getElementById('eje1Der').value.length <= 1){
			$("#eje1Der").val(rangoAutobuses[indice-2]);
		}
		$("#eje1Izq").val(rangoAutobuses[indice]);		
	}
	
	if( atrPreRevision[3]=="CAMIONETA" || atrPreRevision[3]=="CAMPERO"  ){
		var indice = Math.floor(Math.random() * rangoCaminioneta.length);
		$("#eje2Izq").val(rangoCaminioneta[indice]);
		$("#eje2Der").val(rangoCaminioneta[indice+1]);
		if(document.getElementById('eje2Der').value.length <= 1){
			$("#eje2Der").val(rangoCaminioneta[indice-2]);
		}
		$("#eje1Der").val(rangoCaminioneta[indice-1]);
		if(document.getElementById('eje1Der').value.length <= 1){
			$("#eje1Der").val(rangoCaminioneta[indice+2]);
		}		
		$("#eje1Izq").val(rangoCaminioneta[indice]);		
	}
	
	if( atrPreRevision[3]=="TRACTOCAMION" || atrPreRevision[3]=="CAMION" || atrPreRevision[3]=="VOLQUETA" ){
		var indice = Math.floor(Math.random() * rangoPesado.length);
		$("#eje2Izq").val(rangoPesado[indice+1]);
		if(document.getElementById('eje2Izq').value.length <= 1){
			$("#eje2Izq").val(rangoPesado[indice-2]);
		}
		$("#eje2Der").val(rangoPesado[indice]);
		$("#eje1Der").val(rangoPesado[indice-1]);
		if(document.getElementById('eje1Der').value.length <= 1){
			$("#eje1Der").val(rangoPesado[indice+1]);
		}
		$("#eje1Izq").val(rangoPesado[indice]);		
		if(nroEjes >2){
			$("#eje3Izq").val(rangoPesado[indice]);
			$("#eje3Der").val(rangoPesado[indice-1]);
			if(document.getElementById('eje3Der').value.length <= 1){
				$("#eje3Der").val(rangoPesado[indice+1]);
			}
		}
		if(nroEjes >3){
			$("#eje4Izq").val(rangoPesado[indice]);
			$("#eje4Der").val(rangoPesado[indice+1]);
			if(document.getElementById('eje4Der').value.length <= 1){
				$("#eje4Der").val(rangoPesado[indice-1]);
			}
		}
		if(nroEjes >4){
			$("#eje5Izq").val(rangoPesado[indice-1]);
			if(document.getElementById('eje5Izq').value.length <= 1){
				$("#eje5Izq").val(rangoPesado[indice+1]);
			}
			$("#eje5Der").val(rangoPesado[indice]);			
		}
	}	
}
         
function psiBajoScoter(oCheckBox){	
	if($(oCheckBox).is(':checked')){		
		var indice = Math.floor(Math.random() * rangoMotoScoter.length);		
		$("#eje2Der").val(rangoMotoScoter[indice+1]);
		$("#eje1Der").val(rangoMotoScoter[indice]);		
	}else{
		var indice = Math.floor(Math.random() * rangoMotocicleta.length);		
		$("#eje2Der").val(rangoMotocicleta[indice+1]);
		$("#eje1Der").val(rangoMotocicleta[indice]);	
	}	
}

function habilitarPachas(oCheckBox){	
	if($(oCheckBox).is(':checked')){
		var nroE= parseInt(document.getElementById("selectNroEjes").value);		
		
		if(nroE ==2){			
			$("#eje2IzqInt").prop("disabled", false);
			$("#eje2DerInt").prop("disabled", false);			
			$("#eje3DerInt").prop("disabled", true);
			$("#eje3IzqInt").prop("disabled", true);
			$("#eje4DerInt").prop("disabled", true);
			$("#eje4IzqInt").prop("disabled", true);			
			$("#eje5DerInt").prop("disabled", true);
			$("#eje5IzqInt").prop("disabled", true);
			$("#eje2IzqInt").val($("#eje1Der").val());
			$("#eje2DerInt").val($("#eje2Der").val());
			
		}
		
		if(nroE ==3){			
			$("#eje2IzqInt").prop("disabled", false);
			$("#eje2DerInt").prop("disabled", false);
			$("#eje3IzqInt").prop("disabled", false);
			$("#eje3DerInt").prop("disabled", false);
			$("#eje4IzqInt").prop("disabled", true);
			$("#eje4DerInt").prop("disabled", true);
			$("#eje5IzqInt").prop("disabled", true);
			$("#eje5DerInt").prop("disabled", true);
			$("#eje2IzqInt").val($("#eje1Der").val());
			$("#eje2DerInt").val($("#eje2Der").val());
			$("#eje3IzqInt").val($("#eje2Izq").val());
			$("#eje3DerInt").val($("#eje1Der").val());
		}
		if(nroE ==4){			
			$("#eje2IzqInt").prop("disabled", false);
			$("#eje2DerInt").prop("disabled", false);
			$("#eje3IzqInt").prop("disabled", false);
			$("#eje3DerInt").prop("disabled", false);
			$("#eje4IzqInt").prop("disabled", false);
			$("#eje4DerInt").prop("disabled", false);
			$("#eje5IzqInt").prop("disabled", true);
			$("#eje5DerInt").prop("disabled", true);
			$("#eje2IzqInt").val($("#eje2Der").val());
			$("#eje2DerInt").val($("#eje3Der").val());
			$("#eje3IzqInt").val($("#eje4Izq").val());
			$("#eje3DerInt").val($("#eje1Der").val());
			$("#eje4IzqInt").val($("#eje2Izq").val());
			$("#eje4DerInt").val($("#eje4Der").val());
			
		}
		if(nroE ==5){			
			$("#eje2IzqInt").prop("disabled", false);
			$("#eje2DerInt").prop("disabled", false);
			$("#eje3IzqInt").prop("disabled", false);
			$("#eje3DerInt").prop("disabled", false);
			$("#eje4IzqInt").prop("disabled", false);
			$("#eje4DerInt").prop("disabled", false);
			$("#eje5IzqInt").prop("disabled", false);
			$("#eje5DerInt").prop("disabled", false);
			$("#eje2IzqInt").val($("#eje2Der").val());
			$("#eje2DerInt").val($("#eje5Der").val());
			$("#eje3IzqInt").val($("#eje4Izq").val());
			$("#eje3DerInt").val($("#eje1Der").val());
			$("#eje4IzqInt").val($("#eje2Izq").val());
			$("#eje4DerInt").val($("#eje4Der").val());
			$("#eje4IzqInt").val($("#eje5Izq").val());
			$("#eje4DerInt").val($("#eje4Der").val());
		}
		servicioFindPsi(nroE);
	}else{
		$("#eje2IzqInt").prop("disabled", true);
		$("#eje2DerInt").prop("disabled", true);
		$("#eje3IzqInt").prop("disabled", true);
		$("#eje3DerInt").prop("disabled", true);
		$("#eje4IzqInt").prop("disabled", true);
		$("#eje4DerInt").prop("disabled", true);
		$("#eje5IzqInt").prop("disabled", true);
		$("#eje5DerInt").prop("disabled", true);
		$("#eje2IzqInt").val("");
		$("#eje2DerInt").val("");
		$("#eje3IzqInt").val("");
		$("#eje3DerInt").val("");
		$("#eje4IzqInt").val("");
		$("#eje4DerInt").val("");
		$("#eje5IzqInt").val("");
		$("#eje5DerInt").val("");
		
	}
}

function habilitarInputEjes(oSelect){
	var nroE=parseInt(oSelect.value);
	 $("#pacha").prop("checked", false);
	 nroEjes=nroE;
	if(nroE ==2){
		   $("#eje3Izq").prop("disabled", true);
		   $("#eje3Der").prop("disabled", true);
		   $("#eje4Izq").prop("disabled", true);
		   $("#eje4Der").prop("disabled", true);
		   $("#eje5Izq").prop("disabled", true);
		   $("#eje5Der").prop("disabled", true);
		   $("#eje3Izq").val("");
		   $("#eje3Der").val("");
		   $("#eje4Izq").val("");
		   $("#eje4Der").val("");
		   $("#eje5Izq").val("");
		   $("#eje5Der").val("");
		}
	if(nroE ==3){
		   $("#eje3Izq").prop("disabled", false);
		   $("#eje3Der").prop("disabled", false);
		   $("#eje4Izq").prop("disabled", true);
		   $("#eje4Der").prop("disabled", true);
		   $("#eje5Izq").prop("disabled", true);
		   $("#eje5Der").prop("disabled", true);		   
		   $("#eje4Izq").val("");
		   $("#eje4Der").val("");		   
		   
		}
		if(nroE==4){
		   $("#eje4Izq").prop("disabled", false);
		   $("#eje4Der").prop("disabled", false);
		   $("#eje3Izq").prop("disabled", false);
		   $("#eje3Der").prop("disabled", false);
		   $("#eje5Izq").prop("disabled", true);
		   $("#eje5Der").prop("disabled", true);
		   $("#eje5Izq").val("");
		   $("#eje5Der").val("");
		}
		if(nroE==5){
		   $("#eje5Izq").prop("disabled", false);
		   $("#eje5Der").prop("disabled", false);
		   $("#eje4Izq").prop("disabled", false);
		   $("#eje4Der").prop("disabled", false);
		   $("#eje3Izq").prop("disabled", false);
		   $("#eje3Der").prop("disabled", false);
		}
		$("#eje2IzqInt").prop("disabled", true);
		$("#eje2DerInt").prop("disabled", true);
		$("#eje3IzqInt").prop("disabled", true);
		$("#eje3DerInt").prop("disabled", true);
		$("#eje4IzqInt").prop("disabled", true);
		$("#eje4DerInt").prop("disabled", true);
		$("#eje5IzqInt").prop("disabled", true);
		$("#eje5DerInt").prop("disabled", true);		
		$("#eje2IzqInt").val("");
		$("#eje2DerInt").val("");
		$("#eje3IzqInt").val("");
		$("#eje3DerInt").val("");
		$("#eje4IzqInt").val("");
		$("#eje4DerInt").val("");
		$("#eje5IzqInt").val("");
		$("#eje5DerInt").val("");
}

function servicioFindPsi(nroEjes) {
	if (claseVehiculo == "BUS" || claseVehiculo == "BUSETA" || claseVehiculo == "MICROBUS") {
		var indice = Math.floor(Math.random() * rangoAutobuses.length);
		$("#eje2Izq").val(rangoAutobuses[indice + 1]);
		if (document.getElementById('eje2Izq').value.length <= 1) {
			$("#eje2Der").val(rangoAutobuses[indice - 2]);
		}
		$("#eje2Der").val(rangoAutobuses[indice - 1]);
		if (document.getElementById('eje2Der').value.length <= 1) {
			$("#eje2Der").val(rangoAutobuses[indice + 2]);
		}
		$("#eje1Der").val(rangoAutobuses[indice + 1]);
		if (document.getElementById('eje1Der').value.length <= 1) {
			$("#eje1Der").val(rangoAutobuses[indice - 2]);
		}
		$("#eje1Izq").val(rangoAutobuses[indice]);
	}
	if (claseVehiculo == "CAMIONETA" || claseVehiculo == "CAMPERO") {
		var indice = Math.floor(Math.random() * rangoCaminioneta.length);
		$("#eje2Izq").val(rangoCaminioneta[indice]);
		$("#eje2Der").val(rangoCaminioneta[indice + 1]);
		if (document.getElementById('eje2Der').value.length <= 1) {
			$("#eje2Der").val(rangoCaminioneta[indice - 2]);
		}
		$("#eje1Der").val(rangoCaminioneta[indice - 1]);
		if (document.getElementById('eje1Der').value.length <= 1) {
			$("#eje1Der").val(rangoCaminioneta[indice + 2]);
		}
		$("#eje1Izq").val(rangoCaminioneta[indice]);
	}

	if (claseVehiculo == "TRACTOCAMION" || claseVehiculo == "CAMION" || claseVehiculo == "VOLQUETA") {
		var indice = Math.floor(Math.random() * rangoPesado.length);
		$("#eje2Izq").val(rangoPesado[indice + 1]);
		if (document.getElementById('eje2Izq').value.length <= 1) {
			$("#eje2Izq").val(rangoPesado[indice - 2]);
		}
		$("#eje2Der").val(rangoPesado[indice]);
		$("#eje1Der").val(rangoPesado[indice - 1]);
		if (document.getElementById('eje1Der').value.length <= 1) {
			$("#eje1Der").val(rangoPesado[indice + 1]);
		}
		$("#eje1Izq").val(rangoPesado[indice]);
		if (nroEjes > 2) {
			$("#eje3Izq").val(rangoPesado[indice]);
			$("#eje3Der").val(rangoPesado[indice - 1]);
			if (document.getElementById('eje3Der').value.length <= 1) {
				$("#eje3Der").val(rangoPesado[indice + 1]);
			}
		}
		if (nroEjes > 3) {
			$("#eje4Izq").val(rangoPesado[indice]);
			$("#eje4Der").val(rangoPesado[indice + 1]);
			if (document.getElementById('eje4Der').value.length <= 1) {
				$("#eje4Der").val(rangoPesado[indice - 1]);
			}
		}
		if (nroEjes > 4) {
			$("#eje5Izq").val(rangoPesado[indice - 1]);
			if (document.getElementById('eje5Izq').value.length <= 1) {
				$("#eje5Izq").val(rangoPesado[indice + 1]);
			}
			$("#eje5Der").val(rangoPesado[indice]);
		}
	}
}

function registroPreRevision(){	
   var streamingPsiEje = "";
   var envio =true;	
   /*if(isNaN($('#ejeRepuesto').val())){
	  var sN= $('#ejeRepuesto').attr('placeholder');
	  option.message ="Disculpe,el Siguiente Atributo no es numerico: "+ sN;	
	  eModal.alert(option);
	  envio =false;
	  return false;
	}
	if ($('#ejeRepuesto').val().length == 0) {
		var sd = $('#ejeRepuesto').attr('placeholder');
		option.message ="Disculpe,NO Ha especificado el Siguiente Atributo: "+ sd;	
	    eModal.alert(option);
	    $('#ejeRepuesto').focus();
		envio =false;
		return false;
	} else {
		streamingPsiEje = streamingPsiEje + $('#ejeRepuesto').val() + ";";
	}*/	
	var frm = document.getElementById("frmPsi");
	var i=0;
	var ejes = parseInt(nroEjes) *2;	
	ejes =ejes +1;
	var num;
	$("input[type=text]", frm)
		.each(
			function() {			    
				if(isNaN($(this).val())){
					var sN= $(this).attr('placeholder');
					option.message ="Disculpe,el Siguiente Atributo no es numerico: "+ sN;	
		    	    eModal.alert(option);
		    	    envio =false;
					return false;
				}
				i=i+1;		
				if ($(this).val().length == 0 && $(this).prop("disabled") == false ) {
					var sd = $(this).attr('placeholder');
					option.message ="Disculpe,NO Ha especificado el Siguiente Atributo: "+ sd;	
		    	    eModal.alert(option);
				    $(this).focus();
					envio =false;
					return false;
				} else {
					if ($(this).prop("disabled") == true ){
						streamingPsiEje = streamingPsiEje + "0.0;";
					}else{
						streamingPsiEje = streamingPsiEje + $(this).val() + ";";	
					}					
				}						
			});
	var exitPacha;
	var exitScoter;
	if($("#pacha").is(':checked')){
		exitPacha="true";
	}else{
		exitPacha="false";
	}	
    if($("#scoter").is(':checked')){
    	exitScoter="true";
	}else{
		exitScoter="false";
	}   
	if (envio == true) {
	    var entidad = "registroPSI";
        var cadenaAtributos, streaming = "";
        cadenaAtributos = nroEjes +";"+document.getElementById("rin").value+";"+";"+exitPacha+";"+exitScoter+";"+streamingPsiEje;
        streaming = entidad + "|" + cadenaAtributos;	
        doAjax('./ServicioBasicoControler.do', streaming,'respRegistroPSI', 'get', 0);
	}
}
var resource;
function respRegistroPSI(resp){	
	if(resp=="0"){
		option.message = "Disculpe; he Presentado Problemas al Momento de Registrar los PSI de las LLANTAS";
	}else{
		option.message = "se ha REGISTRADO los PSI de las LLANTAS de una Manera Exitosa..!";
	}
	resource="PreReVehicular.html";	
	eModal.alert(option);
	setTimeout(function() {followResource()}, 1400);
}
function followResource(){
  window.location.href=resource;
}

function closeModal() {
	$("#registrarModalPro").click();
	clearTimeout(closeModal);
}

function returnMenuMain(){
	window.location.href="index.html";	
}