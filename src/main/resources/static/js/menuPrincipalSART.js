String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, "");	};
var obj=null;
var rolPermitido="DTO;IPR";
var autorizado=false;
var ctxPista;
var tipoPista;
var imgPlacaAma = new Image();
var imgPlacaBlac = new Image();
imgPlacaAma.src="./img/PlacaAmarrilla.png";
imgPlacaBlac.src="./img/PlacaBlanca.png";
var callFunction="";
var ctxPlaca;
var ctxTurno;
var ctxCondicion;
var ctxTipo;
var ctxServicio;
var resourceFind;
var ctxHojaPrueba;

var ctxTipoVehiculo;
var ctxNroEjes;
var ctxEnsenanza;
var ctxPoseePacha; 


var options = {
		message : "",
		title : 'Sart 2.0',
		size : 'lg',
		subtitle : ' Informacion',
		useBin : false,
		buttons: [
	        {text: 'OK', style: 'info',   close: true, click:sc }	        
	    ]
	};
var optionTest = {
		message : "",
		title : 'Sart 2.0',
		size : 'lg',
		subtitle : ' Informacion',
		useBin : false,
		buttons: [
	        {text: 'OK', style: 'info',   close: true, click:sc }	        
	    ]
	};
function sc(){ 
	 eModal.alert(options);
}
function menuPrincipal(){
	window.location.href="menuWelcome.html";
}
function scF(){
	window.location.href="menuWelcome.html";
}

function verStatusPruebas0() {
    ctxPlaca= document.getElementById('txtPlaca0').value;
    ctxTurno= document.getElementById('nroTurno0').innerHTML ;    
    ctxCondicion= document.getElementById('condicion0').innerHTML ;
    ctxTipo= document.getElementById('TIPO0').innerHTML ;    
    ctxServicio= document.getElementById('ctxServicio0').value;    
    ctxTipoVehiculo =document.getElementById('ctxTipoVehiculo0').value;
    ctxNroEjes =document.getElementById('ctxNroEjes0').value;
    ctxEnsenanza =document.getElementById('ctxEnsenanza0').value ;
    ctxPoseePacha =document.getElementById('ctxPoseePacha0').value;
	var entidad = "servFindStatusPruebas";
	var cadenaAtributos, streaming = "";
	ctxHojaPrueba= document.getElementById('ctxHojaPrueba0').value;
	cadenaAtributos = document.getElementById('ctxHojaPrueba0').value;
	streaming = entidad + "|" + cadenaAtributos;
	doAjax('./ServicioBasicoControler.do', streaming, 'responseStatusPruebas','get', 0);
}
function scF(){
	window.location.href="menuWelcome.html";
}
function callResourcePrueba(idPrueba,tipoPrueba,condicion){		
	if(condicion == 1) {
		optionTest.message = " Disculpe, esta Prueba se Encuentra Cerrada ..!";
		optionTest.subtitle = "  Informacion";
		 eModal.alert(optionTest);		
		 setTimeout(function() {	sc()}, 1100);
		 return;
	}
	
	if(tipoPrueba == "3") {
		resourceFind= "SARTModuloFoto.html";
	}
	
	if(tipoPrueba == "1") {
		if(ctxPista == "4") {
		   resourceFind= "menuInspecVisualMoto.html";	
		}
		if(ctxPista == "10") {						
		   resourceFind= "menuInspecVisuaLiviano.html";	
	    }
		
	}
	var entidad = "setCtxPruebas";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = idPrueba+";"+tipoPrueba+";"+ctxPlaca +";"+ctxTurno+";"+ctxCondicion +";"+ctxTipo +";"+ctxServicio+";"+ctxHojaPrueba +";"+ ctxTipoVehiculo +";"+ ctxNroEjes +";"+ ctxEnsenanza+";"+ctxPoseePacha;
	streaming = entidad + "|" + cadenaAtributos;
	doAjax('./ServicioBasicoControler.do', streaming, 'responseSetPruebas','get', 0);	
}
function responseSetPruebas(resp){
	 if (resp == 2) {
			options.message = " Disculpe, su session a expirado por inactividad ..!";
			options.subtitle = "  Informacion";
			eModal.alert(options);			
			setTimeout(function() {	scF()}, 1700);
			return;
		}
	 window.location.href=resourceFind;
}	 

function responseStatusPruebas(resp){
 if (resp == 2) {
	 options.message = " Disculpe, su session a expirado por inactividad ..!";
	 options.subtitle = "  Informacion";
	 eModal.alert(options);		
	 setTimeout(function() {	scF()}, 1700);
	 return;
  } 
  var flujoHP = resp.split("|");
  options.subtitle = " Status Actual de Pruebas para la Placa:" +ctxPlaca;
  var strMsg;
  var statusPrueba;
  var condicion;
  var backGround;
  var observacion;
  strMsg= "&nbsp;&nbsp;&nbsp;&nbsp; <table style='display: inline-block;' class='table-responsive table-condensed' border='1' width='94%'> "
        + " <tr>  <th style='background-color:#ffffff;'>&nbsp;TIPO DE PRUEBA &nbsp;</th>"
        + " <th style='background-color:#ffffff;'>OBSERVACIONES </th>"
        + "<th style='background-color:#ffffff;'>CONDICION </th>  </tr>" 
	   for(i = 0;  i < flujoHP.length-1;  i++) {	    
		   var flujoAtrrHP = flujoHP[i].split(";");
		   if(flujoAtrrHP[1]=="N"){
		      condicion="<strong>PENDIENTE</strong>";
		      backGround="#5cb85c;";
		      statusPrueba=0;
		   }else{
		      condicion="<strong>FINALIZADA</strong>";
		      backGround="#FF0000;";
		      statusPrueba=1;
		   }
		   var tipoPrueba= parseInt(flujoAtrrHP[4]);
		   
		   switch(tipoPrueba) {
                  case 5:
                     strMsg = strMsg +"<tr> <td width='20%' id='' style='background-color:#ffffff;'><button class='btn btn-default active' type='submit' style='width:100%;margin-top:4px;'><i class='fa fa-arrows-h'></i>&nbsp;&nbsp;Frenos</button> </td> ";
                     break;
                 case 1:
                     strMsg = strMsg +"<tr> <td width='20%' id='' style='background-color:#ffffff;'><button class='btn btn-default active' onclick='callResourcePrueba("+flujoAtrrHP[3] +","+ flujoAtrrHP[4]+","+ statusPrueba+");' style='width:100%;margin-top:4px;'><i class='fa fa-eye'></i>&nbsp;&nbsp;Visual</button> </td> ";                    
                     break;
                 case 2:
                     strMsg = strMsg +"<tr> <td width='20%' id='' style='background-color:#ffffff;'><button class='btn btn-default active' type='submit' style='width:100%;margin-top:4px;'><i class='fa fa-lightbulb-o'></i>&nbsp;&nbsp;Luces</button> </td> ";
                     break;
                 case 3:
                     strMsg = strMsg +"<tr> <td width='20%' id='' style='background-color:#ffffff;'><button class='btn btn-default active' onclick='callResourcePrueba("+flujoAtrrHP[3] +","+ flujoAtrrHP[4]+","+ statusPrueba+" );' style='width:100%;margin-top:4px;'><i class='fa fa-camera'></i>&nbsp;&nbsp;Fotos</button> </td> ";
                     break;
                 case 4:
                     strMsg = strMsg +"<tr> <td width='20%' id='' style='background-color:#ffffff;'><button class='btn btn-default active' type='submit' style='width:100%;margin-top:4px;'><i class='fa fa-random'></i>&nbsp;&nbsp;Desviacion</button> </td> ";
                     break;
                 case 9:
                     strMsg = strMsg +"<tr> <td width='20%' id='' style='background-color:#ffffff;'><button class='btn btn-default active' type='submit' style='width:100%;margin-top:4px;'><i class='fa fa-tachometer'></i>&nbsp;&nbsp;Taximetro</button> </td> ";
                     break;
                 case 6:
                     strMsg = strMsg +"<tr> <td width='20%' id='' style='background-color:#ffffff;'><button class='btn btn-default active' type='submit' style='width:100%;margin-top:4px;'><i class='fa fa-arrows-v'></i>&nbsp;&nbsp;Suspension</button> </td> ";
                     break;
                 case 7:
                     strMsg = strMsg +"<tr> <td width='20%' id='' style='background-color:#ffffff;'><button class='btn btn-default active' type='submit' style='width:100%;margin-top:4px;'><i class='fa fa-podcast'></i>&nbsp;&nbsp;Ruido</button> </td> ";
                     break;
                 case 8:
                     strMsg = strMsg +"<tr> <td width='20%' id='' style='background-color:#ffffff;'><button class='btn btn-default active' type='submit' style='width:100%;margin-top:4px;'><i class='fa fa-eercast'></i>&nbsp;&nbsp;Gases</button> </td> ";
                     break; 
		    }
		    if(tipoPrueba==1) {
		    	var lstObs = flujoAtrrHP[2].split("obs");		
				if (lstObs.length> 1) {					
					observacion= lstObs[1]; 
				}		    	
		    }else{
		    	observacion=flujoAtrrHP[2];
		    }
            strMsg = strMsg +  "<td width='74%' id='' style='background-color:#ffffff;'> " +observacion + "</td> <td width='36%' id='' style='color:#ffffff; background-color:"+backGround+ "'> &nbsp;&nbsp;"+condicion +"&nbsp;&nbsp;</td>  </tr>"
        }       
       strMsg = strMsg + "</table>";
       options.message =strMsg;	  	
	   eModal.alert(options);
}
function verStatusPruebas1(){
  ctxPlaca= document.getElementById('txtPlaca1').value;
  ctxTurno= document.getElementById('nroTurno1').innerHTML ;    
  ctxCondicion= document.getElementById('condicion1').innerHTML ;
  ctxTipo= document.getElementById('TIPO1').innerHTML ;
  ctxServicio= document.getElementById('ctxServicio1').value;
  ctxHojaPrueba= document.getElementById('ctxHojaPrueba1').value;
  ctxTipoVehiculo =document.getElementById('ctxTipoVehiculo1').value;
  ctxNroEjes =document.getElementById('ctxNroEjes1').value;
  ctxEnsenanza =document.getElementById('ctxEnsenanza1').value ;
  ctxPoseePacha =document.getElementById('ctxPoseePacha1').value;
	var entidad = "servFindStatusPruebas";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = document.getElementById('ctxHojaPrueba1').value;
	streaming = entidad + "|" + cadenaAtributos;
	doAjax('./ServicioBasicoControler.do', streaming, 'responseStatusPruebas','get', 0);
}

function verStatusPruebas2(){
  ctxPlaca= document.getElementById('txtPlaca2').value;
  ctxTurno= document.getElementById('nroTurno2').innerHTML ;    
  ctxCondicion= document.getElementById('condicion2').innerHTML ;
  ctxTipo= document.getElementById('TIPO2').innerHTML ;
  ctxServicio= document.getElementById('ctxServicio2').value;
  ctxHojaPrueba= document.getElementById('ctxHojaPrueba2').value;
  ctxTipoVehiculo =document.getElementById('ctxTipoVehiculo2').value;
  ctxNroEjes =document.getElementById('ctxNroEjes2').value;
  ctxEnsenanza =document.getElementById('ctxEnsenanza2').value ;
  ctxPoseePacha =document.getElementById('ctxPoseePacha2').value;
  var entidad = "servFindStatusPruebas";
  var cadenaAtributos, streaming = "";
  cadenaAtributos = document.getElementById('ctxHojaPrueba2').value;
  streaming = entidad + "|" + cadenaAtributos;
  doAjax('./ServicioBasicoControler.do', streaming, 'responseStatusPruebas','get', 0);
}
function verStatusPruebas3(){
   ctxPlaca= document.getElementById('txtPlaca3').value;
   ctxTurno= document.getElementById('nroTurno3').innerHTML ;    
   ctxCondicion= document.getElementById('condicion3').innerHTML ;
   ctxTipo= document.getElementById('TIPO3').innerHTML ;
   ctxServicio= document.getElementById('ctxServicio3').value;
   ctxHojaPrueba= document.getElementById('ctxHojaPrueba3').value;
   ctxTipoVehiculo =document.getElementById('ctxTipoVehiculo3').value;
   ctxNroEjes =document.getElementById('ctxNroEjes3').value;
   ctxEnsenanza =document.getElementById('ctxEnsenanza3').value ;
   ctxPoseePacha =document.getElementById('ctxPoseePacha3').value;
  var entidad = "servFindStatusPruebas";
  var cadenaAtributos, streaming = "";
  cadenaAtributos = document.getElementById('ctxHojaPrueba3').value;
  streaming = entidad + "|" + cadenaAtributos;
  doAjax('./ServicioBasicoControler.do', streaming, 'responseStatusPruebas','get', 0);  
}
function verStatusPruebas4(){
  ctxPlaca= document.getElementById('txtPlaca4').value;
  ctxTurno= document.getElementById('nroTurno4').innerHTML ;    
  ctxCondicion= document.getElementById('condicion4').innerHTML ;
  ctxTipo= document.getElementById('TIPO4').innerHTML ;
  ctxServicio= document.getElementById('ctxServicio4').value;
  ctxHojaPrueba= document.getElementById('ctxHojaPrueba4').value;
  ctxTipoVehiculo =document.getElementById('ctxTipoVehiculo4').value;
  ctxNroEjes =document.getElementById('ctxNroEjes4').value;
  ctxEnsenanza =document.getElementById('ctxEnsenanza4').value ;
  ctxPoseePacha =document.getElementById('ctxPoseePacha4').value;
  var entidad = "servFindStatusPruebas";
  var cadenaAtributos, streaming = "";
  cadenaAtributos = document.getElementById('ctxHojaPrueba4').value;
  streaming = entidad + "|" + cadenaAtributos;
  doAjax('./ServicioBasicoControler.do', streaming, 'responseStatusPruebas','get', 0);
}
function verStatusPruebas5(){
  ctxPlaca= document.getElementById('txtPlaca5').value;
  ctxTurno= document.getElementById('nroTurno5').innerHTML ;    
  ctxCondicion= document.getElementById('condicion5').innerHTML ;
  ctxTipo= document.getElementById('TIPO5').innerHTML ;
  ctxServicio= document.getElementById('ctxServicio5').value;
  ctxHojaPrueba= document.getElementById('ctxHojaPrueba5').value;
  ctxTipoVehiculo =document.getElementById('ctxTipoVehiculo5').value;
  ctxNroEjes =document.getElementById('ctxNroEjes5').value;
  ctxEnsenanza =document.getElementById('ctxEnsenanza5').value ;
  ctxPoseePacha =document.getElementById('ctxPoseePacha5').value;
  var entidad = "servFindStatusPruebas";
  var cadenaAtributos, streaming = "";
  cadenaAtributos = document.getElementById('ctxHojaPrueba5').value;
  streaming = entidad + "|" + cadenaAtributos;
  doAjax('./ServicioBasicoControler.do', streaming, 'responseStatusPruebas','get', 0);  
}
function verStatusPruebas6(){
  ctxPlaca= document.getElementById('txtPlaca6').value;
  ctxTurno= document.getElementById('nroTurno6').innerHTML ;    
  ctxCondicion= document.getElementById('condicion6').innerHTML ;
  ctxTipo= document.getElementById('TIPO6').innerHTML;
  ctxServicio= document.getElementById('ctxServicio6').value;
  ctxHojaPrueba= document.getElementById('ctxHojaPrueba6').value;
  ctxTipoVehiculo =document.getElementById('ctxTipoVehiculo6').value;
  ctxNroEjes =document.getElementById('ctxNroEjes6').value;
  ctxEnsenanza =document.getElementById('ctxEnsenanza6').value ;
  ctxPoseePacha =document.getElementById('ctxPoseePacha6').value;
  
  var entidad = "servFindStatusPruebas";
  var cadenaAtributos, streaming = "";
  cadenaAtributos = document.getElementById('ctxHojaPrueba6').value;
  streaming = entidad + "|" + cadenaAtributos;
  doAjax('./ServicioBasicoControler.do', streaming, 'responseStatusPruebas','get', 0);
}
function verStatusPruebas7(){
  ctxPlaca= document.getElementById('txtPlaca7').value;
  ctxTurno= document.getElementById('nroTurno7').innerHTML ;    
  ctxCondicion= document.getElementById('condicion7').innerHTML ;
  ctxTipo= document.getElementById('TIPO7').innerHTML ;
  ctxServicio= document.getElementById('ctxServicio7').value;
  ctxHojaPrueba= document.getElementById('ctxHojaPrueba7').value;
  ctxTipoVehiculo =document.getElementById('ctxTipoVehiculo7').value;
  ctxNroEjes =document.getElementById('ctxNroEjes7').value;
  ctxEnsenanza =document.getElementById('ctxEnsenanza7').value ;
  ctxPoseePacha =document.getElementById('ctxPoseePacha7').value;
  var entidad = "servFindStatusPruebas";
  var cadenaAtributos, streaming = "";
  cadenaAtributos = document.getElementById('ctxHojaPrueba7').value;
  streaming = entidad + "|" + cadenaAtributos;
  doAjax('./ServicioBasicoControler.do', streaming, 'responseStatusPruebas','get', 0);
}


function respServFindHojaPruebasAbiertas(resp){
	
	if(resp.length> 2){
	   var flujoHP = resp.split("|");
	   for(i = 0;  i < flujoHP.length-1;  i++) {
		   var flujoAtrrHP = flujoHP[i].split(";");
		   document.getElementById('nroTurno'+i).innerHTML ="Turno:"+ flujoAtrrHP[4]
		   if(flujoAtrrHP[2] == "false"){
			   document.getElementById('TIPO'+i).innerHTML ="Tipo:PREV.";   
		   }else{
			   document.getElementById('TIPO'+i).innerHTML ="Tipo:RTM";
		   }
		   if(flujoAtrrHP[3] == "1"){
			   document.getElementById('condicion'+i).innerHTML ="CONDICION:1era. Vez";   
		   }else{
			   document.getElementById('condicion'+i).innerHTML ="CONDICION:2da. Vez";
		   }				 
		   var newCanvas = document.createElement("canvas");
			newCanvas.setAttribute('width', '120%');
			newCanvas.setAttribute('height', '58%');
			newCanvas.setAttribute('id', 'canvas'+i);			
			var td = document.getElementById('nroPlaca'+i);		
			td.appendChild(newCanvas);	
			var eve= document.getElementById('canvas'+i);			
			switch(i) {
                  case 0:
                    eve.addEventListener("click",verStatusPruebas0);
                     break;
                 case 1:
                     eve.addEventListener("click",verStatusPruebas1);
                     break;
                 case 2:
                     eve.addEventListener("click",verStatusPruebas2);
                     break;
                 case 3:
                     eve.addEventListener("click",verStatusPruebas3);
                     break;
                 case 4:
                     eve.addEventListener("click",verStatusPruebas4);
                     break;
                 case 5:
                     eve.addEventListener("click",verStatusPruebas5);
                     break;
                 case 6:
                     eve.addEventListener("click",verStatusPruebas6);
                     break;
                 case 7:
                     eve.addEventListener("click",verStatusPruebas7);
                     break;
                 case 8:
                     eve.addEventListener("click",verStatusPruebas8);
                     break;                            
                default:        
            } 
			            
			var contexto= newCanvas.getContext("2d");		
			contexto.beginPath();
		    if(flujoAtrrHP[5]=="Particular"){
		    	contexto.drawImage(imgPlacaAma,0,0,120,58);
		    }else{
			    contexto.drawImage(imgPlacaBlac,0,0,120,58);
		    }
		    document.getElementById('ctxServicio'+i).value =flujoAtrrHP[5]
		    document.getElementById('txtPlaca'+i).value =flujoAtrrHP[1]
		    document.getElementById('ctxHojaPrueba'+i).value =flujoAtrrHP[0]		    
		    document.getElementById('ctxTipoVehiculo'+i).value =flujoAtrrHP[7];    
		    document.getElementById('ctxNroEjes'+i).value =flujoAtrrHP[8];
		    document.getElementById('ctxEnsenanza'+i).value =flujoAtrrHP[9];
		    document.getElementById('ctxPoseePacha'+i).value =flujoAtrrHP[10];  
		    
		    var str = flujoAtrrHP[1].substring(0, 3) +"-"+flujoAtrrHP[1].substring(3, 6);		
		    contexto.font="bold 22px sans-serif";
		    contexto.fillText(str,12,36);
		    contexto.closePath();		 
	    }			
	}else {		
		if(resp=="2"){
			options.message = "Disculpe; su SESSION DE USUARIO se ha cerrado Por Inactividad ..!";
			callFunction="irIndex";
			eModal.alert(options);			
			return ;
		}
		if(resp=="0"){
			options.message = "Disculpe; he Presentado Problemas al momento de cargar Las hojas de Pruebas Pendientes.!";
	    }
		if(resp==""){			
			options.message = "Disculpe; No Exiten Placas Habilitadas Para la "+tipoPista+" ..!";
	    }	
		eModal.alert(options);
		setTimeout(function() {scF()}, 5000);
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
		console.log('response '+resp);
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
			console.log('response '+resp);						
			ctxPista=atrSeg[6];	
			console.log('ctxPista '+ctxPista);
						
		}else{
			options.message = " Disculpe, usted no Posee un ROL Autorizado para entrar en este Modulo ..!";
			eModal.alert(options);
			setTimeout(function() {scF()}, 1700);
		}
	}
}
function serFindCtxPista(tipoVehiculo) {	
	for(var i=0;i<9;i++ ){
		document.getElementById('nroTurno'+i).innerHTML= "";
		document.getElementById('condicion'+i).innerHTML= "";
		document.getElementById('nroPlaca'+i).innerHTML= "";	
		document.getElementById('txtPlaca'+i).value="";
		document.getElementById('ctxHojaPrueba'+i).value="";
		document.getElementById('ctxServicio'+i).value="";
		
		document.getElementById('ctxTipoVehiculo'+i).value ="";    
		document.getElementById('ctxNroEjes'+i).value ="";
		document.getElementById('ctxEnsenanza'+i).value ="";
		document.getElementById('ctxPoseePacha'+i).value ="";  
	}	
	
	if(tipoVehiculo==4){
		document.getElementById('ctxMenu').innerHTML= "MODULO PRINCIPAL DEL SART-WEB (PISTA DE MOTO)";
		tipoPista="PISTA DE MOTO";
	}			
	if(tipoVehiculo==10){
		document.getElementById('ctxMenu').innerHTML= "MODULO PRINCIPAL DEL SART-WEB (PISTA LIVIANA-MIXTA)";
		tipoPista="PISTA  LIVIANA";
	}	
	ctxPista=tipoVehiculo;	
	var entidad = "servFindHojaPruebasAbiertas";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = ctxPista;
	streaming = entidad + "|" + cadenaAtributos;
	doAjax('./ServicioBasicoControler.do', streaming,'respServFindHojaPruebasAbiertas', 'get', 0);
}

function followResource(){
	window.location.href="OrdenPreRevision.html";
}
