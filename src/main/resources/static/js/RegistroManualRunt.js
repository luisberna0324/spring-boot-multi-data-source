var optAlternativas = {message : "",title : 'Sart 2.0',size : 'lg',subtitle : '',useBin : false,
		buttons: [
		        {text: 'Si', style: 'info',   close: true, click:ctxFuntion},
				{text: 'No', style: 'info',   close: true, click:ctxSalirPreRev},	        
		    ]};
String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, "");	};

$(document).ready(function(){    
    
 });
	
var obj=null;
var ctxPreRevision;
var naturalezaRev;
var strRechazo;
var nroRechazo;
var callFunction;
var resource;
var options = {
		message : "",
		title : 'Sart 2.0.0',
		size : 'lg',
		subtitle : ' Datos Faltantes',
		useBin : false,
		buttons: [
	        {text: 'OK', style: 'info',   close: true, click:sc }	        
	    ]
	};
var optionS = {
		message : "",
		title : 'Sart 2.0.0',
		size : 'lg',
		subtitle : '',
		useBin : false,
		buttons: [
	        {text: 'OK', style: 'info',   close: true, click:scF }	        
	    ]
	};

function scF(){
	window.location.href="menuWelcome.html";
}

function ctxFuntion(){
	setTimeout(function(){asigFocus(),700 } )
}

function sc(){
 if (callFunction =="askSoat"){
	 optAlternativas.message = "<BR> EL ESTADO ACTUAL DEL SOAT ES <b>VIGENTE </b>  ?";
	 optAlternativas.subtitle = " INFORMACION DE VALIDACION";
	 strRechazo ="SOAT VENCIDO ";
	 nroRechazo="0";
	 eModal.alert(optAlternativas); 
   }
 callFunction ="";
	
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
		optionS.message = " Disculpe, su session a expirado por inactividad ..!";
		optionS.subtitle = "  Informacion";
		eModal.alert(optionS);	
		setTimeout(function() { scF() }, 1700);					
	}else{
		var atrSeg = resp.split(";");		
		document.getElementById("nombreUsuario").innerHTML = "   " + atrSeg[1];
		var ruta = "./imagenes/fotosEmpresa/" + atrSeg[0] + ".png";		
		document.getElementById('fotoUsuario').setAttribute('src', ruta);
		document.getElementById("placa").focus();
		document.getElementById("placa").select();
		document.getElementById("identificador").value=atrSeg[0];
		var entidad = "servFindClasesVehiculoAct";
		var cadenaAtributos, streaming = "";
		cadenaAtributos = "";
		streaming = entidad + "|" + cadenaAtributos;	
		doAjax('./ServicioBasicoControler.do', streaming,'respServFindClasesVehiculoAct', 'get', 0);
	}
}
function respServFindClasesVehiculoAct(resp){
	var atrClase = resp.split("]");	
	var oSelect =document.getElementById('claseVehiculo');
	var currentClass;
	for (var i = 0; i < atrClase.length-1; i++) {
		 currentClass = atrClase[i].split(";");		 
		 var option = document.createElement("option");		 
		 option.value =currentClass[1];
		 option.text = currentClass[1];
		 oSelect.appendChild(option);
	}
}

function validacionPlaca(oTxt){
	obj =oTxt;	
	/*if(oTxt.value.length < 6 ){
		options.message= " Disculpe, el Registro de la Placa no debe de ser de menos de 6 caracteres";
		options.subtitle = " Validacion de Datos";
		eModal.alert(options);	
	}
    if(oTxt.value.length > 6 ){
	  options.message= " Dsiculpe, el Registro de la Placa no debe de ser mas de 6 caracteres";
	  options.subtitle = " Validacion de Datos";
	  eModal.alert(options);
    }*/
   
}
function registrarDatosPrerevision(){
	var frm = document.getElementById("formRegistroManual");	 
	var envio =true;
	callFunction ="";
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
	if(isNaN(document.getElementById("modelo").value)){
		options.message ="Disculpe, el valor introducido del MODELO  debe ser numerico ";
		options.subtitle="Datos NO Valido";
	    eModal.alert(options);
	    obj =document.getElementById("modelo");
	    envio =false;
	    return false;
	}	
	if(isNaN(document.getElementById("cedula").value)){
		options.message ="Disculpe, el valor introducido de la IDENTIFICACION  debe ser numerico ";
		options.subtitle="Datos NO Valido";
		obj =document.getElementById("cedula");
	    eModal.alert(options);	    
	    envio =false;
	    return false;
	}
	
	
	if(document.getElementById("idColor").value.length<1){
		options.message ="Disculpe, no se ha SELECCIONADO ningun color ";
		options.subtitle="Datos NO Valido";		
	    eModal.alert(options);	    
	    envio =false;
	    return false;
	}
	
	if(envio==true){
		obj=null;		
		if(document.getElementById("claseVehiculo").value=="MOTOCICLETA" ){			
			resource="preRevisionMoto.html";		                      
		}else{			
			resource="preRevision.html";	  
	    }
	     var entidad = "regManualRunt";
		 var cadenaAtributos, streaming = "";			
		 cadenaAtributos = document.getElementById("placa").value + ";"+document.getElementById("combustible").value+";"+document.getElementById("modelo").value+";"+document.getElementById("typIndent").value+";"+document.getElementById("cedula").value+";"+document.getElementById("claseVehiculo").value+";"+document.getElementById("tipoServicio").value+";"+document.getElementById("nroEjes").value+";"+document.getElementById("color").value+";"+ctxPreRevision+";"+document.getElementById("nacionalidad").value ;
	
		 
		 streaming = entidad + "|" + cadenaAtributos;		
		 doAjax('./ServicioBasicoControler.do', streaming, 'respRegManualRunt','get', 0);	  
	}
}
function asigFocus() {
	document.getElementById("nacionalidad").focus();	
}



function findCtxRevision() {
	if (document.getElementById("placa").value.length >3){
	    var entidad = "getNaturalezaPreRevision";
	    var cadenaAtributos, streaming = "";			
	    cadenaAtributos = document.getElementById("placa").value;		 
	    streaming = entidad + "|" + cadenaAtributos;		
	    doAjax('./ServicioBasicoControler.do', streaming, 'respGetNaturalezaPreRevision','get', 0);
	}	
}

function respGetNaturalezaPreRevision(resp){	
	var atrrNaturalez  = resp.split(";");
	console.log("response naturaleza is "+resp); 
	if(resp!="0"){
		     naturalezaRev =atrrNaturalez[1];
			 if (atrrNaturalez[0] == "0") {
			     options.message = "PRE-REVISION: PRIMERA VEZ";
			     ctxPreRevision = "1";
			     options.subtitle = "  Contexto de PreRevision";
			     eModal.alert(options);
		      }
		      if (atrrNaturalez[0] == "1") {
			      if (atrrNaturalez[1] == "N") {
				       options.message = "REINSPECCION PARA  RTM";
			      } else {
				      options.message = "REINSPECCION  PARA PREVENTIVA";
			      }
			      ctxPreRevision = "2";
			      options.subtitle = "  Contexto de PreRevision";
			      eModal.alert(options);
			      var entidad = "servFindVehiculo";
				  var cadenaAtributos, streaming = "";	
				  console.log("placa a consultar "+document.getElementById("placa").value );
				  cadenaAtributos = document.getElementById("placa").value;		 
				  streaming = entidad + "|" + cadenaAtributos;		
				  doAjax('./ServicioBasicoControler.do', streaming, 'respServFindDatosTakeRunt','get', 0);
		      }
		     if (atrrNaturalez[0] == "3") {
			    if (atrrNaturalez[1] == "N") {
				   optAlternativas.message = "LO SENTIMOS SU RTM EXCEDIO el plazo de los 15 dias; DESEA  CONTINUAR ?";
			    } else {
				   optAlternativas.message = "LO SENTIMOS SU PREVENTIVA EXCEDIO el plazo de los 15 dias; DESEA CONTINUAR ?";
			    }
			    ctxPreRevision = "1";
			    optAlternativas.subtitle = " Contexto de PreRevision";
			    eModal.alert(optAlternativas);			    
			    strRechazo ="RECHAZADA REINSPECCION X SUPERAR LOS 15 DIAS"
			    nroRechazo="9";
			    var entidad = "servFindVehiculo";
				  var cadenaAtributos, streaming = "";	
				  console.log("placa a consultar "+document.getElementById("placa").value );
				  cadenaAtributos = document.getElementById("placa").value;		 
				  streaming = entidad + "|" + cadenaAtributos;		
				  doAjax('./ServicioBasicoControler.do', streaming, 'respServFindDatosTakeRunt','get', 0);
			   return;
		    }
		    callFunction ="askSoat";    
	}	
}

function respServFindDatosTakeRunt(resp){
	console.log("cadena "+resp );
	  var atrSeg = resp.split(";");	  
	  document.getElementById('modelo').value=atrSeg[3];  
	  document.getElementById('color').value=atrSeg[5];
	  
	  document.getElementById('idColor').value=atrSeg[5];
	 
	   
	  
	  var SelectObject = document.getElementById("combustible");
	  for(index = 0;  index < SelectObject.length;  index++) {    	   
	      if(SelectObject[index].value ==atrSeg[9]){
	   	   SelectObject.selectedIndex = index;
	      }
	  }
	  
	  var SelectObject1 = document.getElementById("claseVehiculo");
	  for(index = 0;  index < SelectObject1.length;  index++) {    	   
	      if(SelectObject1[index].value ==atrSeg[7]){
	   	   SelectObject1.selectedIndex = index;
	      }
	  }
	  
	  var SelectObject2 = document.getElementById("tipoServicio");
	  for(index = 0;  index < SelectObject2.length;  index++) {    	   
	      if(SelectObject2[index].value ==atrSeg[6]){
	   	   SelectObject2.selectedIndex = index;
	      }
	  }	  
	  var SelectObject3 = document.getElementById("nroEjes");
	  for(index = 0;  index < SelectObject3.length;  index++) {    	   
	      if(SelectObject3[index].value ==atrSeg[20]){
	   	   SelectObject3.selectedIndex = index;
	      }
	  }
	  var SelectObject4 = document.getElementById("nacionalidad");
	  for(index = 0;  index < SelectObject4.length;  index++) {    	   
	      if(SelectObject4[index].value ==atrSeg[21]){
	   	   SelectObject4.selectedIndex = index;
	      }
	  }	  
	  var SelectObject5 = document.getElementById("typIndent");
	  for(index = 0;  index < SelectObject5.length;  index++) {    	   
	      if(SelectObject5[index].value ==atrSeg[22]){
	   	   SelectObject5.selectedIndex = index;
	      }
	  }
	  
	  document.getElementById('cedula').value = atrSeg[18];   
	}

function ctxSalirPreRev(){
    var entidad = "servicioRechazo";	
	var cadenaAtributos, streaming = "";   
	cadenaAtributos =  document.getElementById("placa").value  + ";"+strRechazo+";"+nroRechazo +"; ;"+document.getElementById("identificador").value;	  
	streaming = entidad + "|" + cadenaAtributos;
	console.log("cadena "+cadenaAtributos );
	doAjax('./ServicioBasicoControler.do', streaming, 'respServRechazo','get', 0);
	console.log("cadena 1" );
}

function respServRechazo(resp){
	   console.log("RESPIS "+resp);  
		if(parseInt(resp)==0){
			options.message = "Disculpe, no Pude REGISTRAR la causal de Rechazo, Comuniquese con el equipo de Soporte Tecnico de SOLTELEC";
			options.subtitle = "Fallo del Sart";
			eModal.alert(options);
		}else{
			document.getElementById("placa").value = "";
			document.getElementById("placa").focus();
			document.getElementById("placa").select();			
		}
}
function returnMenuMain(){
	window.location.href="menuWelcome.html";	
}
function  respRegManualRunt(resp){	
	if(resp==0){
	   options.message= " He presentado Problemas al momento de Registrar los datos";
	   options.subtitle = " Registro Datos";
	   eModal.alert(options);	
	   setTimeout(function() { scF() }, 1900);
	   return;
	}
	if(resp==2){
		optionS.message = " Disculpe, su session a expirado por inactividad ..!";
		optionS.subtitle = "  Informacion";
		eModal.alert(optionS);	
		setTimeout(function() { scF() }, 1700);					
	}else{
		var frm = document.getElementById("formRegistroManual");	
		$("input[type=text]", frm)
		.each(
			function() {	
				$(this).val(" "); 
			});		
		options.message= " Se ha REGISTRADO los datos Manuales de una manera exitosa..!";
		options.subtitle = " Registro Datos";
		$('select').find('option:selected').each(function() {			
			$("select").prop('selectedIndex', 0);			 
		});	
	}	
	eModal.alert(options);
	setTimeout(function() {followResource()}, 1200);
}
function followResource(){
	window.location.href=resource;	
}

