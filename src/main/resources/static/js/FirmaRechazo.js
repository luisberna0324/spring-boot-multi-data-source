var color= "#000000";
var tamano= 1;
var pintura= false;
var aprobado="";
var imgPlacaAma = new Image();
var imgPlacaBlac = new Image();
imgPlacaAma.src="./img/PlacaAmarrilla.png";
imgPlacaBlac.src="./img/PlacaBlanca.png";
//var limpiar = document.getElementById("limpiar");
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
var options = {
		message : "",
		title : 'Sart 2.0',
		size : 'lg',
		subtitle : ' Datos Faltantes',
		useBin : false,
		buttons: [
	        {text: 'Finalizar', style: 'info',   close: true, click:sc },
	        
	    ]
	};
var optionSession = {
		message : "",
		title : 'Sart 2.0',
		size : 'lg',
		subtitle : ' Inf. Session',
		useBin : false,
		buttons: [
	        {text: 'OK', style: 'info',   close: true, click:scF }	        
	    ]
	};

function scF(){
	window.location.href="menuWelcome.html";
}

var oCapaP = document.getElementById("fondoBodyProccesing");	
var oCapP = document.getElementById("capaBodyProccesing");   
oCapaP.setAttribute("class", "");		
oCapP.style.display = 'none';
 
function sc(){
	window.location.href="menuWelcome.html";	
}
function limpiar() {
  dibujar = false;
  ctx.clearRect(0, 0, cw, ch);
  Trazados.length = 0;
  puntos.length = 0;
}
var click =0;
function registroFirmaRechazo(){
	if(click==0 ){
	   click=1;	   
	   var formElement = document.getElementById("frmUploadFirma");  
	   var data = canvas.toDataURL('jpg');
	   var formData = new FormData(formElement);  
		var content= "regFirma";
		var blob = new Blob([content], { type: "text/plain"});
		formData.append("entidad",blob,"regFirma");
		formData.append("firmaRaw",data);  
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {  
		if (xhr.readyState == 4) {			
		    if(xhr.responseText==1){
		       options.message ="se ha Finalizado este Procesos de una Manera Existosa";		 
		    }else{
		      options.message ="Disculpe, he presentado problemas para culminar el Proceso ";  
		    }
			options.subtitle = " ";		
			eModal.alert(options);
			setTimeout(function() {sc()}, 1377);
		 }
	   }  
	   xhr.open('POST','./ServicioMultiPartControler.do',true);
	   xhr.send(formData);		
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

function acepto(){
	var oCapa = document.getElementById("fondoBodyCondicionesAprobado");	
	var oCap = document.getElementById("capaBodyCondicionesAprobado");
	oCapa.setAttribute("class", "");		
	oCap.style.display = 'none';	
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
	aprobado =atrPreRevision[4];	
	var oCapa = document.getElementById("fondoBodyCondicionesAprobado");	
	var oCap = document.getElementById("capaBodyCondicionesAprobado");   
	if(parseInt(aprobado)==1){
		oCapa.setAttribute("class", "fondoFuncion");
		oCap.style.display = 'block';		
	}else{
		oCapa.setAttribute("class", "");		
		oCap.style.display = 'none';
	}
	var entidad = "takeCtxConductor";
	var cadenaAtributos, streaming = "";
	cadenaAtributos = "";
	streaming = entidad + "|" + cadenaAtributos;	
	doAjax('./ServicioBasicoControler.do', streaming,'respTakeCtxConductor', 'get', 0);	
}
function respTakeCtxConductor(resp){	
	var atrConductor = resp.split(";");
	var tdCedula = document.getElementById("cedula");
	tdCedula.innerHTML ="&nbsp;&nbsp;Cedula: <b>"+atrConductor[0]+"</b>";
	var tdCiudadano = document.getElementById("conductor");
	tdCiudadano.innerHTML ="Poseedor/Propietario:<b>"+atrConductor[1]+";"+atrConductor[2]+"</b>";	
}

function cambioImg(){
  var bCv= document.getElementById("cambioImg");
  var ruta= "./img/trasero1.png";
  	document.getElementById('cambioImg').setAttribute('src',ruta );	
    
  if (bCv.src==true){
     document.getElementById("cambioImg").style.display = 'block';
  }else {      
     document.getElementById("cambioImg").style.display = 'none';
  }
  
}

function beginSing(evt){	
	evt.preventDefault();
	dibujar = true;
	  //ctx.clearRect(0, 0, cw, ch);
	  puntos.length = 0;
	  ctx.beginPath();	  
}

canvas.addEventListener('mouseup', function(evt) {
  redibujarTrazados();
}, false);

canvas.addEventListener("ontouchend", function(evt) {
  redibujarTrazados();
}, false);


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


