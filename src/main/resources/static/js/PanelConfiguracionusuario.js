var files;
var imgLoad ;	
var sendImg =true;
var oCapa = document.getElementById("fondoBodyProccesing");	
var oCap = document.getElementById("capaBodyProccesing");   
oCapa.setAttribute("class", "");		
oCap.style.display = 'none';
var obj=null;
var contexto;
var indentUser;
var canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");
var cw = canvas.width = 500,
  cx = cw / 2;
var ch = canvas.height = 280,
  cy = ch / 2;
var dibujar = false;
var factorDeAlisamiento = 2;
var Trazados = [];
var puntos = [];
ctx.lineJoin = "round";
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
	}
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
function sc(){ 
	if(obj!=null){
		setTimeout(function() {asigFocus()}, 700);
	}		
}
function asigFocus() {
	obj.focus();
	obj.select()
}
function findCedulaUsuario(oTxtUser){	
	if(isNaN(document.getElementById("cedula").value)){
		options.message ="Disculpe, el valor introducido de la IDENTIFICACION  debe ser numerico ";
		options.subtitle="Datos NO Valido";
		obj =document.getElementById("cedula");
	    eModal.alert(options);	    
	    envio =false;
	    return false;
	}	
	var entidad = "findExistCedulaUser";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = oTxtUser.value;
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respFindExistUserName', 'get', 0);	
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
function handleFileSelect(evt) {
	files = evt.target.files; // FileList object
	f = files[0];		
    if (!f.type.match('image.*')) { }	
	var reader = new window.FileReader();
	reader.onloadend = function(evt) {
      if (evt.target.readyState == FileReader.DONE) {
    	  imgLoad=evt.target.result;    	  
      }
    };    
    reader.onload = function(e) {
    	document.getElementById('list').innerHTML="";              
        var span = document.createElement('span');
        span.innerHTML = ['<img class="thumb"  src="', e.target.result,'" title="', escape(f.name), '"/>'].join('');
        document.getElementById('list').insertBefore(span, null);        
    }
    reader.readAsDataURL(f);  
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
function limpiar() {
	  dibujar = false;
	  ctx.clearRect(0, 0, cw, ch);
	  Trazados.length = 0;
	  puntos.length = 0;
}

function reloadResource(){
	window.location.href="PanelConfiguracionUser.html";	
}
canvas.addEventListener('mouseup', function(evt) {
	  redibujarTrazados();
	}, false);

	canvas.addEventListener("ontouchend", function(evt) {
	  redibujarTrazados();
	}, false);

function beginSing(evt){	
	evt.preventDefault();
	dibujar = true;
	  //ctx.clearRect(0, 0, cw, ch);
	  puntos.length = 0;
	  ctx.beginPath();	  
}
function moveSing(evt){
	evt.preventDefault();
	if (dibujar) {
	    var m = oMousePos(canvas, evt);
	    puntos.push(m);
	    ctx.lineTo(m.x, m.y);
	    ctx.stroke();
	  }
}
function reducirArray(n,elArray) {
	  var nuevoArray = [];
	  nuevoArray[0] = elArray[0];
	  for (var i = 0; i < elArray.length; i++) {
	    if (i % n == 0) {
	      nuevoArray[nuevoArray.length] = elArray[i];
	    }
	  }
	  nuevoArray[nuevoArray.length - 1] = elArray[elArray.length - 1];
	  Trazados.push(nuevoArray);
	}

	function calcularPuntoDeControl(ry, a, b) {
	  var pc = {}
	  pc.x = (ry[a].x + ry[b].x) / 2;
	  pc.y = (ry[a].y + ry[b].y) / 2;
	  return pc;
	}

	function alisarTrazado(ry) {
	  if (ry.length > 1) {
	    var ultimoPunto = ry.length - 1;
	    ctx.beginPath();
	    ctx.moveTo(ry[0].x, ry[0].y);
	    for (i = 1; i < ry.length - 2; i++) {
	      var pc = calcularPuntoDeControl(ry, i, i + 1);
	      ctx.quadraticCurveTo(ry[i].x, ry[i].y, pc.x, pc.y);
	    }
	    ctx.quadraticCurveTo(ry[ultimoPunto - 1].x, ry[ultimoPunto - 1].y, ry[ultimoPunto].x, ry[ultimoPunto].y);
	    ctx.stroke();
	  }
	}


	function redibujarTrazados(){
	  dibujar = false;
	  ctx.clearRect(0, 0, cw, ch);
	  reducirArray(factorDeAlisamiento,puntos);
	  for(var i = 0; i < Trazados.length; i++)
	  alisarTrazado(Trazados[i]);
	}

	function oMousePos(canvas, evt) {
	  var ClientRect = canvas.getBoundingClientRect();
	  return { //objeto
	    /*x: Math.round(evt.clientX - ClientRect.left),
	    y: Math.round(evt.clientY - ClientRect.top)*/
		  x: Math.round(evt.touches[0].clientX - (ClientRect.left)),
		  y: Math.round(evt.touches[0].clientY - (ClientRect.top))  
	  }
	}