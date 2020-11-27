String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, "");	};
var obj=null;
var cedula;
var apellido;
var nombre;
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
function sc(){
	if(obj!=null){
		setTimeout(function() {asigFocus()}, 700);	
	}else{
		obj=document.getElementById("placa");
		setTimeout(function() {asigFocus()}, 700);
	}	
}
function scF(){
	window.location.href="menuWelcome.html";
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
		document.getElementById("placa").focus();
		document.getElementById("placa").select();
		var entidad = "servFindDatosTakeRunt";
		var cadenaAtributos, streaming = "";
		cadenaAtributos = "";
		streaming = entidad + "|" + cadenaAtributos;	
		doAjax('./ServicioBasicoControler.do', streaming,'respServFindDatosTakeRunt', 'get', 0);
	}
}
function serFindPropietario(){
	if (isNaN(document.getElementById("cedulaPropietario").value)) {
		if (isNaN(document.getElementById("cedulaPropietario").value)) {
			options.message = "Disculpe, el valor introducido para Cedula del Propietario  debe ser numerico ";
			options.subtitle = "Datos NO Valido";
			eModal.alert(options);
			obj = document.getElementById("cedulaPropietario");
			envio = false;
			return false;
		} else {
			var entidad = "serFindPropietario";
			var cadenaAtributos, streaming = "";
			cadenaAtributos = document.getElementById('cedulaPropietario').value;
			streaming = entidad + "|" + cadenaAtributos;
			doAjax('./ServicioBasicoControler.do', streaming,'respSerFindPropietario', 'get', 0);
		}
	}
}
function respSerFindPropietario(resp) {	
	if (resp == 0) {
	} else {
		var atrSeg = resp.split(";");
		document.getElementById('apellidoPropietario').value = atrSeg[1];
		document.getElementById('nombrePropietario').value = atrSeg[2];		
	}
}
function respServFindDatosTakeRunt(resp){
	
	console.log("trama "+resp);	
  var atrSeg = resp.split(";");
  document.getElementById('placa').value=atrSeg[0];	
  document.getElementById('marca').value=atrSeg[1];
  document.getElementById('idMarca').value=atrSeg[1];
  document.getElementById('linea').value=atrSeg[2];
  document.getElementById('modelo').value=atrSeg[3];  
  document.getElementById('color').value=atrSeg[5];
  document.getElementById("idColor").value=atrSeg[5];
  document.getElementById('servicio').value=atrSeg[6];
  document.getElementById('claseVehiculo').value=atrSeg[7];  
  SelectObject = document.getElementById("combustible");
  for(index = 0;  index < SelectObject.length;  index++) {    	   
      if(SelectObject[index].value ==atrSeg[9]){
   	   SelectObject.selectedIndex = index;
      }
  }
  if(atrSeg[4]=="0"){
	  document.getElementById('cilindrada').value="";  
  }else{
	  document.getElementById('cilindrada').value=atrSeg[4];
  }
  if(atrSeg[8]=="0"){
	  document.getElementById('tipoCarroceria').value="";  
  }else{
	  document.getElementById('tipoCarroceria').value=atrSeg[8];
  }  
  
  if(atrSeg[10]=="0"){
	  document.getElementById('capacidad').value="";  
  }else{
	  document.getElementById('capacidad').value=atrSeg[10];
  }
  if(atrSeg[11]=="0"){
	  document.getElementById('nroMotor').value="";  
  }else{
	  document.getElementById('nroMotor').value=atrSeg[11];
  }
  if(atrSeg[12]=="0"){
	  document.getElementById('vin').value="";  
  }else{
	  document.getElementById('vin').value=atrSeg[12];
  }
  if(atrSeg[13]=="0"){
	  document.getElementById('nroSerie').value="";  
  }else{
	  document.getElementById('nroSerie').value=atrSeg[13];
  }
  if(atrSeg[14]=="0"){
	  document.getElementById('nroChasis').value="";  
  }else{
	  document.getElementById('nroChasis').value=atrSeg[14];
  }
  if(atrSeg[14]=="0"){
	  document.getElementById('nroLicencia').value="";  
  }else{
	  document.getElementById('nroLicencia').value=atrSeg[15];
  }      
  document.getElementById('fkRevision').value=atrSeg[23];
   
  document.getElementById('cedulaPropietario').value = atrSeg[18];  
  var fechaMatricula = new Date();
  fechaMatricula.setTime(atrSeg[19]);
  var mes = fechaMatricula.getMonth()+1;
  var dia = fechaMatricula.getDate();
  var ano = fechaMatricula.getFullYear() ; //obteniendo año
  if(dia<10)
    dia='0'+dia; 
  if(mes<10)
    mes='0'+mes 
  document.getElementById('txtFechaMatricula').value=ano+"-"+mes+"-"+dia;
  
  if (isNaN(document.getElementById("cedulaPropietario").value)) {	  
  }else{
	  var entidad = "serFindPropietario";
		var cadenaAtributos, streaming = "";
		cadenaAtributos = document.getElementById('cedulaPropietario').value;
		streaming = entidad + "|" + cadenaAtributos;
		doAjax('./ServicioBasicoControler.do', streaming,'respSerFindPropietario', 'get', 0);  
  }  
}
function validacionPlaca(oTxt){
	obj =oTxt;	
	   
}
function registrarDatosPrerevision(){
	var frm = document.getElementById("formRegistroRunt");	 
	var envio =true;	
	$("input[type=text]", frm)
	.each(
		function() {			
			if ($(this).val().length <1) {
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
	
	if(isNaN(document.getElementById("capacidad").value)){
		options.message ="Disculpe, el valor introducido para la Capacidad de Carga  debe ser numerico ";
		options.subtitle="Datos NO Valido";
	    eModal.alert(options);
	    obj =document.getElementById("capacidad");
	    envio =false;
	    return false;
	}
	if(isNaN(document.getElementById("nroLicencia").value)){
		options.message ="Disculpe, el valor introducido para Nro. Licencia del Propietario  debe ser numerico ";
		options.subtitle="Datos NO Valido";
	    eModal.alert(options);
	    obj =document.getElementById("nroLicencia");
	    envio =false;
	    return false;
	}	
	
	if(isNaN(document.getElementById("cilindrada").value)){
		options.message ="Disculpe, el valor introducido de la CILINDRADA  debe ser numerico ";
		options.subtitle="Datos NO Valido";
		obj =document.getElementById("cilindrada");
	    eModal.alert(options);	    
	    envio =false;
	    return false;
	}
	if(document.getElementById("marca").value == "SIN MARCA" ){
		options.message ="Disculpe, a la Marca del Vehiculo no se le Asignado un Atributo  Valido";
		options.subtitle="Datos NO Valido";
		obj =document.getElementById("marca");
	    eModal.alert(options);	    
	    envio =false;
	    return false;
	}
	
	if(document.getElementById("idColor").value.length <1 ){
		options.message ="Disculpe, al Color del Vehiculo no se le Asignado un Atributo  Valido";
		options.subtitle="Datos NO Valido";
		obj =document.getElementById("color");
	    eModal.alert(options);	    
	    envio =false;
	    return false;
	}
	
	if(document.getElementById("idMarca").value.length <1  ){
		options.message ="Disculpe, a la Marca del Vehiculo no se le Asignado un Atributo  Valido";
		options.subtitle="Datos NO Valido";
		obj =document.getElementById("marca");
	    eModal.alert(options);	    
	    envio =false;
	    return false;
	}
	
	
	
	if(envio==true){
		obj=null;
	   var entidad = "servConfirmDatosRunt";
		 var cadenaAtributos, streaming = "";			
		 cadenaAtributos = document.getElementById("fkRevision").value + ";"+document.getElementById("combustible").value +";"+document.getElementById("nroMotor").value+";"+document.getElementById("nroSerie").value+";"+document.getElementById("nroChasis").value+";"+document.getElementById("cilindrada").value+";"+document.getElementById("tipoCarroceria").value+";"+ document.getElementById("capacidad").value+";"+document.getElementById("vin").value +";"+document.getElementById("nroLicencia").value+";"+
		 document.getElementById("marca").value+";"+document.getElementById("linea").value
		 +";"+document.getElementById("modelo").value+";"+document.getElementById("color").value
		 +";"+document.getElementById("servicio").value+";"+document.getElementById("apellidoPropietario").value+";"+document.getElementById("nombrePropietario").value+";"+document.getElementById("cedulaPropietario").value+";"+document.getElementById("txtFechaMatricula").value;		 
		 streaming = entidad + "|" + cadenaAtributos;		
		 doAjax('./ServicioBasicoControler.do', streaming, 'respServConfirmDatosRunt','get', 0);	  
	}
}

function respServConfirmDatosRunt(resp){
	if(parseInt(resp) > 0){			
		options.message= " Se han Confirmado Los Datos del RUNT de una manera exitosa..!";
		options.subtitle = " Confirmacion Datos";		
	}else{
		options.message= " He presentado Problemas al momento de Confirmar los datos";
		options.subtitle = " Registro Datos";		
	}	
	eModal.alert(options);
	setTimeout(function() {followResource()}, 1400);	
}
function followResource(){
	window.location.href="RegistroDatosSoat.html";
}

function asigFocus() {
	obj.focus();
	obj.select()
}
function returnMenuMain(){
	window.location.href="menuWelcome.html";	
}
