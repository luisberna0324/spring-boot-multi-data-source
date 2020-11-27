//mi miAHAHlib.js

 function createREQ(){
    try{
       http = new XMLHttpRequest();
    }catch(e){
      try{
         //http= new ActiveXObject("Msxml2.XMLHTTP");
         http= new ActiveXObject("Microsoft.XMLHTTP");
      }catch(e){
         http= false;
      }
    }
    return http;
  }
  function requestGet(url,query,req){     
      var datos =query.split("|");
      var solicitud = url+'?entidad='+encodeURIComponent(datos[0])+"&cadenaAtributos="+encodeURIComponent(datos[1])+"&auxiliar="+encodeURIComponent(datos[2]);
      req.open("GET",solicitud,true);
      req.send(null);
  }

function requestGet2(url,query,req){
      var datos =query.split("|");
      var solicitud = url+'?claseObjeto='+encodeURIComponent(datos[0])+"&dato="+encodeURIComponent(datos[1])+"&auxiliar="+encodeURIComponent(datos[2]);
      req.open("GET",solicitud,true);
      req.send(null);
  }
  function requestPost(url,query,req){
   var datos =query.split("|");
   query = "entidad="+encodeURIComponent(datos[0])+"&cadenaAtributos="+encodeURIComponent(datos[1]);
   req.open("POST",url,true);
   req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
   req.send(query);
  }
   function doCallback(callback,item){
        //que ejecute la funcion       
       eval(callback +'(item)');
   }
  function doAjax(url,query,callback,reqtype,getxml){
    var myreq = createREQ(); 
    var funcion = callback;    
    myreq.onreadystatechange = function(){          
         
          if(myreq.readyState == 4){
            if(myreq.status == 200){
		 var item = myreq.responseText;
                 if(getxml==1){
                   item = myreq.responseXML;
                 }
              
              doCallback(funcion,item);              
              funcion="";
            }
          }
  }
    if(reqtype =='post'){
       requestPost(url,query,myreq);
    }else{
       requestGet(url,query,myreq);
    }
  }
  
function doAjax2(url,query,callback,reqtype,getxml){
  var myreq = createREQ();
    var funcion = callback;
    myreq.onreadystatechange = function(){
        //  alert(myreq.status )
          if(myreq.readyState == 4){
            if(myreq.status == 200){
		 var item = myreq.responseText;
                 if(getxml==1){
                   item = myreq.responseXML;
                 }
              alert(item );
              //doCallback(funcion,item); 
              return "pelo";

            }
          }
  }
    if(reqtype =='post'){
       requestPost(url,query,myreq);
    }else{
       requestGet(url,query,myreq);
    }
  
  }
  

