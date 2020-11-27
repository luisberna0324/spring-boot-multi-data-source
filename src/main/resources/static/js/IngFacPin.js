String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, "");	};
var oCapaP = document.getElementById("fondoBodyProccesing");	
var oCapP = document.getElementById("capaBodyProccesing");   
oCapaP.setAttribute("class", "");		
oCapP.style.display = 'none';
var obj=null;
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
	if(obj=="nroFactura"){
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
		var entidad = "findCtxPreRevision";
		var cadenaAtributos, streaming = "";
		cadenaAtributos = "";
		streaming = entidad + "|" + cadenaAtributos;	
		doAjax('./ServicioBasicoControler.do', streaming,'respFindCtxPreRevision', 'get', 0);
	}
}
function respFindCtxPreRevision(resp){	
	var atrSeg = resp.split(";");
	if( atrSeg[0]=="2"){
		document.getElementById('divFactura').style.visibility = "visible";
		document.getElementById('nroFactura').focus();
		document.getElementById('nroFactura').value="N/A";
		document.getElementById('nroFactura').select();
		document.getElementById("facturo").checked= "visible"
	}	
}

function showNroFactura(obj){
	if (obj.checked) {
		document.getElementById('divFactura').style.visibility = "visible";
		document.getElementById('nroFactura').focus();
	}else{
		document.getElementById('divFactura').style.visibility = "hidden";
	}
}

function validacionBeforeTrasmision(){	
	var envio =true;	
	if(document.getElementById("facturo").checked=="false"){
		options.message ="Disculpe, No ha Confirmado si se elaboro la Respectiva factura de esta Revision ";
		options.subtitle=" Validacion Trasmision";
	    eModal.alert(options);	    
	    envio =false;
	    return false;
	}	
	if(document.getElementById("nroFactura").value.length<=2){
		options.message ="Disculpe, No ha especificado el Nro. de Factura para esta Revision ";
		options.subtitle=" Validacion Trasmision";
		obj="nroFactura";
	    eModal.alert(options);	    	    
	    envio =false;
	    return false;
	}	
	if(document.getElementById("pinSicov").checked==false){
		options.message ="Disculpe, No ha Confirmado si se inicio el Respectivo PIN para esta Revision ";
		options.subtitle=" Validacion Trasmision";
	    eModal.alert(options);	    
	    envio =false;
	    return false;
	}	
	var oCapa = document.getElementById("fondoBodyProccesing");	
	var oCap = document.getElementById("capaBodyProccesing");   
	oCapa.setAttribute("class", "fondoFuncion");
	oCap.style.display = 'block';
	var formElement = document.getElementById("frmAutorizacionAPista");	
	var formData = new FormData(formElement);  
	var content= "servAutorizacionVehPista";
	var blob = new Blob([content], { type: "text/plain"});
	formData.append("entidad",blob,"servAutorizacionVehPista");
	
	content= document.getElementById("nroFactura").value;	
	blob = new Blob([content], { type: "text/plain"});
	formData.append("nroFactura", blob,document.getElementById("nroFactura").value );  
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4) {
			var oCapa = document.getElementById("fondoBodyProccesing");	
			var oCap = document.getElementById("capaBodyProccesing");   
			oCapa.setAttribute("class", "");		
			oCap.style.display = 'none';
		  if(xhr.responseText==1){
		     options.message ="se ha AUTORIZADO el Vehiculo a Pista de una  Manera Existosa..!";		 
		   }else{
		     options.message ="Disculpe, he presentado problemas para culminar el Proceso ";  
		   }
			options.subtitle = " ";
			eModal.alert(options);
			setTimeout(function() {followResource()}, 1550);
		 }
	   }  
	   xhr.open('POST','./ServicioMultiPartControler.do',true);
	   xhr.send(formData);	
}

function followResource(){
	window.location.href="OrdenPreRevision.html";
}
