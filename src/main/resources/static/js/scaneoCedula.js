var envTrama = false;
$(document).ready(function() {
	$("#").click();
});

function storedDataCedula() {
	var frm = document.getElementById("formP");
	streamingDatosAtrr = "";
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
							$(this).focus();
							envio =false;
							return false;
						} else {
							streamingDatosAtrr = streamingDatosAtrr
									+ $(this).val() + ";";
						}
					});
	if (envio == true) {
		var entidad = "servPersistRegistroCedula";
		streaming = entidad + "|" + streamingDatosAtrr;
		doAjax('./ServicioBasicoControler.do', streaming,'respServPersistRegistroCedula', 'get', 0);
	}
}
function respServPersistRegistroCedula(resp) {
}
