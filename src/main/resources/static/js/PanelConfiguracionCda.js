var files;
var imgLoad ;	
var sendImg =true;
var obj=null;

var autorizado;
var rolPermitido="DTC";

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
		if(resp==2){
			options.message = " Disculpe, su session a expirado por inactividad ..!";
			options.subtitle = "  Informacion";
			eModal.alert(options);	
			setTimeout(function() { scF() }, 1700);
			return;		
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
				var entidad = "servFindAtrrCDA";
				var cadenaAtributos, streaming = "";
				cadenaAtributos = "";
				streaming = entidad + "|" + cadenaAtributos;	
				doAjax('./ServicioBasicoControler.do', streaming,'respServFindAtrrCDA', 'get', 0);				
			}else{
				optionSession.message = " Disculpe, usted no Posee un ROL Autorizado para entrar en este Modulo ..!";
				eModal.alert(optionSession);
				setTimeout(function() {scF()}, 1700);
			}
		}
	}
}
function respServFindAtrrCDA(resp){	
	var atrCda = resp.split("%");
	var atrCda1 = atrCda[0].split(";");
	document.getElementById("ipSicov").value = atrCda1[5];	
	var atrDT = atrCda[1].split("|");	
	var oSelect =document.getElementById('directorTecnico');
	var currentClass;
	for (var i = 0; i < atrDT.length-1; i++) {		  
		 currentClass = atrDT[i].split(";");		 
		 var option = document.createElement("option");		 
		 option.value =currentClass[0];
		 option.text = currentClass[1];
		 oSelect.appendChild(option);
	}
}

function setPanelConfiguracion(){
	var entidad = "setPanelConfiguracionCda";
	var cadenaAtributos, streaming = "";	
	cadenaAtributos = document.getElementById('directorTecnico').value +";"+document.getElementById('ipSicov').value;
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respSetPanelConfiguracionCda', 'get', 0);	
}

function respSetPanelConfiguracionCda(resp){
	if(resp==2){
	   alert("se Han Modificado los Atributos de una manera Exitosa")	
	}else{
		alert("Disculpe, he Presentado problemas ");
	}
	window.location.href="menuWelcome.html";
}

function scF(){
	window.location.href="menuWelcome.html";
}
function menuPrincipal(){
	window.location.href="menuWelcome.html";
}
var options = {
		message : " Disculpe, Login invalido, por favor intente de nuevo..! ",
		title : 'Sart 2.0.0',
		size : 'lg',
		subtitle : 'Datos Faltantes',
		useBin : false,
		 overlayColor: "#000",         
         buttons: [
 	        {text: 'Aceptar', style: 'background:#CCC;color:#000 ',close: true, click:sc }	        
 	    ]
	};

var optionSession = {
		message : "  ",
		title : 'Sart 2.0.0',
		size : 'lg',
		subtitle : 'INF. SESSION',
		useBin : false,
		 overlayColor: "#000",         
         buttons: [
 	        {text: 'OK', style: 'background:#CCC;color:#000 ',close: true, click:scF }	        
 	    ]
	};
function sc(){ 
	if(obj!=null){
		setTimeout(function() {asigFocus()}, 700);
	}		
}
function asigFocus() {
	obj.focus();
	obj.select()
}

function respFindExistUserName(resp){
	document.getElementById('list').innerHTML="";
	if(resp!="no"){		
	   var atrUserName = resp.split(";");
	   document.getElementById("btnExec").value ="Actualizar";
	   contexto="Actualizar";
	   document.getElementById("cedula").value = atrUserName[1];
	   document.getElementById("nombre").value = atrUserName[4];
	   document.getElementById("userName").value = atrUserName[3];
	   document.getElementById("contrasena").value = atrUserName[2]; 
	   SelectObject = document.getElementById("nivel");
       for(index = 0;  index < SelectObject.length;  index++) {    	   
           if(SelectObject[index].value ==atrUserName[0]){
        	   SelectObject.selectedIndex = index;
           }
       }                     
       var span = document.createElement('span');
       var ruta = "./imagenes/fotosEmpresa/"+ atrUserName[1]+".png";       
       span.innerHTML = ['<img class="thumb"  src="',ruta,'" />'].join('');
       document.getElementById('list').insertBefore(span, null);
       indentUser=atrUserName[5];
	}else{
		   document.getElementById("nombre").value ="";
		   document.getElementById("userName").value ="";
		   document.getElementById("contrasena").value = ""; 
		   SelectObject = document.getElementById("nivel");
		   SelectObject.selectedIndex = 0;
		   document.getElementById("btnExec").value ="Registar";
		   contexto="Registar";
	}			
}

var click = "0";
function registrarUsuario(){
	var frm = document.getElementById("frm");
	var envio =true;	
	$("input[type=text]", frm)
	.each(
		function() {			
			if ($(this).val().length == 0) {
				var sd = $(this).attr('placeholder');
				options.subtitle=' Datos Faltantes';
				options.message ="Disculpe,NO Ha especificado el Siguiente Atributo Registro: "+ sd;
				options.buttons= [
		 	        {text: 'Aceptar', style: 'background:#CCC;color:#000 ',close: true, click:sc }	        
		 	    ]
	    	    eModal.alert(options);				    
				envio =false;
				return false;
			} 
		});	
	if(envio ==false){
		return false;
	}
	
	if(click=="0"){
		click = "1";			
		var files = document.getElementById('foto').files;
	    if (!files.length) {
	    	options.message ="Disculpe, no ha especificado ningun Avatar ..!";
	    	options.subtitle="Validacion de Datos";
	    	eModal.alert(options);
	    	click = "0";
	        return;
	    }
	    oCapa.setAttribute("class", "fondoFuncion");
		oCap.style.display = 'block';
	   var data = canvas.toDataURL('jpg');
	   var formElement = document.getElementById("frmRegUsuario");	 
	   var formData = new FormData(formElement);
	   var content= "regUsuario";
	   var blob = new Blob([content], { type: "text/plain"});
	   if( contexto=="Registar"){
		   formData.append("entidad",blob,"regUsuario");
	   }else{
		    formData.append("entidad",blob,"updateUsuario");
		    formData.append("identUser",blob,indentUser);
	   }	  
	   formData.append("userName",blob,document.getElementById("userName").value);   
	   formData.append("contrasena",blob,document.getElementById("contrasena").value);  
	   formData.append("nivel",blob,document.getElementById("nivel").value);
	   formData.append("cedula",blob,document.getElementById("cedula").value);	   
	   formData.append("nombre",blob,document.getElementById("nombre").value);
	   formData.append("foto",imgLoad);	   
	   formData.append("firmaRaw",data);
	   var xhr = new XMLHttpRequest();
		  xhr.onreadystatechange = function() {  
		  if (xhr.readyState == 4) {	  
			  if(xhr.responseText.length>1){				 
				 oCapa = document.getElementById("fondoBodyProccesing");	
				 oCap = document.getElementById("capaBodyProccesing");   
				 oCapa.setAttribute("class", "");		
				 oCap.style.display = 'none';
				 if( contexto=="Registar"){
					 options.message ="He REGISTRADO el usuario de una manera Exitosa ..!";
				 }else{
					 options.message ="He ACTUALIZADO el usuario de una manera Exitosa ..!"; 
				 }				   
			  }else{
				  options.message ="Disculpe, he presentado problemas para culminar el Proceso ";  
			  }
			  options.subtitle = " ";	
			  eModal.alert(options);
			  setTimeout(function() {reloadResource()}, 1300);
		  }
		}
		xhr.open('POST','./ServicioMultiPartControler.do',true);
		xhr.send(formData);  	
	} 
}


function reloadResource(){
	window.location.href="PanelConfiguracionUser.html";	
}










