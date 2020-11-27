var envTrama = false;
  $(document).ready(function() {
});
function envioTramaAlServidor() {
	if (document.getElementById("streamingDocPropiedad").value.length > 0) {
		var stream = document.getElementById("streamingDocPropiedad").value;
		var entidad = "procStreamingDocPropiedad";
		var cadenaAtributos, streaming = "";
		cadenaAtributos = stream;
		streaming = entidad + "|" + cadenaAtributos;
		document.getElementById("streamingDocPropiedad").value = "";
		doAjax('./ServicioBasicoControler.do', streaming,'respProcStreamingDocPropiedad', 'get', 0);
	}
}
function respProcStreamingDocPropiedad(resp) {
	var flujo = resp.split(";");
	setTimeout(function() { closeModal()}, 500);
	$('iframe[name=iframes]').contents().find('#nroLicencia').val(flujo[0]);
	$('iframe[name=iframes]').contents().find('#placa').val(flujo[1]);
	$('iframe[name=iframes]').contents().find('#serialChasis').val(flujo[2]);

	$('iframe[name=iframes]').contents().find('#serie').val(flujo[3]);
	// apli al numero vin
	// $('iframe[name=iframes]').contents().find('#nroVin').val(flujo[4]);
	$('iframe[name=iframes]').contents().find('#ciudadania').val(flujo[5]);
	$('iframe[name=iframes]').contents().find('#cedula').val(flujo[6]);
	$('iframe[name=iframes]').contents().find('#apellido').val(flujo[7]);
	$('iframe[name=iframes]').contents().find('#nombre').val(flujo[8]);
	$('iframe[name=iframes]').contents().find('#direccion').val(flujo[9]);

	envTrama = false;
	document.getElementById("streamingDocPropiedad").value = "";
	clearTimeout(envioTramaAlServidor);
}
function storedDataTarjPropiedad() {
	var frm = document.getElementById("form");
	streamingDatosAtrr="";
	var envio =true;
	$("input[type=text]", frm)
			.each(
					function() {
						if ($(this).val().length == 0) {
							var sd = $(this).attr('placeholder');
							var options = {
								message : "Disculpe,NO Ha especificado el Sigueinte Atributo: "
										+ sd,
								title : 'Sart 2.0',
								size : 'lg',
								subtitle : '  Datos Faltantes',
								useBin : false
							};
							eModal.alert(options);
							// $(this).css('background-color', '#FF0000');
							$(this).focus();
							envio =false;
							return false;
						}else{
							streamingDatosAtrr= streamingDatosAtrr +$(this).val()+";";
						}
					});
	var frm = document.getElementById("formP");
	$("input[type=text]", frm)
			.each(
					function() {
						if ($(this).val().length == 0) {
							var sd = $(this).attr('placeholder');
							var options = {
								message : "Disculpe,NO Ha especificado el Siguiente Atributo: "
										+ sd,
								title : 'Sart 2.0',
								size : 'lg',
								subtitle : '  Datos Faltantes',
								useBin : false
							};
							eModal.alert(options);							
							$(this).focus();
							envio =false;
							return false;
						}else{
							streamingDatosAtrr= streamingDatosAtrr +$(this).val()+";";
						}
					});	
	if(envio ==true){
		var entidad = "servPersistPaso1Recepcion";
		streaming = entidad + "|" + streamingDatosAtrr;
		doAjax('./ServicioBasicoControler.do', streaming,'respServPersistPaso1Recepcion', 'get', 0);
	}
	
}
function respServPersistPaso1Recepcion(resp){
	if(parseInt(resp)>2){
		parent.document.getElementById('identificador').value=resp;
		var options = {
				message : "Es el Propietario el mismo Conductor ?",
				title : 'Sart 2.0',
				size : 'sm',
				subtitle : ' Registro Exitoso del Paso 1.1 ',
				useBin : false,				
				buttons: [
		            {text: 'SI', style: 'info',   close: true, click:scaneoCedula },
		            {text: 'NO', style: 'info', close: true, click: scaneoCedula }
		        ]				
			};
			eModal.alert(options);			    
	}	
}
function scaneoCedula(){
	parent.document.getElementById('phase').value="2";		
	parent.even();
	var frame =	parent.document.getElementById('iframes');	
	frame.src="welcome.html";	
}
function closeModal() {
	$("#myBtn2").click();
	clearTimeout(closeModal);
}
