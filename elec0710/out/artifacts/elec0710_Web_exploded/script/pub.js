function Pub(){ } 

Pub.submit=function(formindex) {
			
  				document.forms[formindex].submit();
  				
}

Pub.formsubmitAction=function(formindex,action) {
            
			document.forms[formindex].action=action;
  				document.forms[formindex].submit();
  				
}


Pub.submitform=function(formname){
   

   var theform =document.forms[formname];
   theform.submit();
}

Pub.reset=function(formindex){
				document.forms[formindex].reset();
}


////////////////////////////////////////////////////////////////////////////////
Pub.checkAll=function(parent, itemName)
{
  
  var parentbox = document.getElementsByName(itemName);
  
  for (var i=0; i<parentbox.length; i++){
  
  
   parentbox[i].checked = parent.checked;
   
   }
}
Pub.checkItem=function (child, allName)
{
  var all = document.getElementsByName(allName)[0];
  if(!child.checked) all.checked = false;
  else
  {
    var childbox = document.getElementsByName(child.name);
    for (var i=0; i<childbox.length; i++)
     if(!childbox[i].checked) return;
    all.checked = true;
  }
}

Pub.getAllCheckItemValue=function(storename,removevalue){


var obj_all = document.getElementsByTagName("INPUT");
var all_value="";
for(i=0;i<obj_all.length;i++){
if(obj_all[i].type=="checkbox")
   if(obj_all[i].checked ){
      if(obj_all[i].value!=removevalue)
      all_value=all_value+obj_all[i].value+",";
   }
      
   
}
var obj = document.getElementById(storename);
obj.value=all_value;

}

Pub.getAllCheckItemValueWithRemove=function(storename,removevalue){


var obj_all = document.getElementsByTagName("INPUT");
var all_value="";
for(i=0;i<obj_all.length;i++){
if(obj_all[i].type=="checkbox")
   if(obj_all[i].checked ){
      if(obj_all[i].value.indexOf("NULL")==-1){ 
      if(obj_all[i].value!=removevalue)
      all_value=all_value+obj_all[i].value+",";
   }
      }
   
}
var obj = document.getElementById(storename);
obj.value=all_value;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////
Pub.copyRole=function(paramname,action){

var obj_all = document.getElementsByTagName("INPUT");
var value="";
for(i=0;i<obj_all.length;i++){
if(obj_all[i].type=="radio")
   if(obj_all[i].checked ){
       
        value = obj_all[i].value;
      }
   
}

 if(value==""){
 return false;}
  document.forms[0].action=action+"?"+paramname+"="+value;
  document.forms[0].submit();

}
///////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**创建ajax引擎  不可公用的*/
Pub.newXMLHttpRequest=function newXMLHttpRequest() {
  var xmlreq = false;
  if (window.XMLHttpRequest) {
    xmlreq = new XMLHttpRequest();
  } else if (window.ActiveXObject) {
     
    try {
      
      xmlreq = new ActiveXObject("Msxml2.XMLHTTP");
    } catch (e1) {
       
      try {
      
        xmlreq = new ActiveXObject("Microsoft.XMLHTTP");
      } catch (e2) {
          
        alert(e2);
      }
    }
  }
  return xmlreq;
}


/**
 * 处理ajax的返回结构
 * req:ajax引擎
 * eleid：表单Form2的名称
 * responseXmHandler：Pub。handleResponse函数
 * */  

Pub.getReadyStateHandler =function getReadyStateHandler(req, eleid,responseXmlHandler) {
  
  return function () {
    
	  /**
	   * req.readyState == 4:监听服务器和客户端的状态
	   * 0：表示open方法没有调用，未初始化
	   * 1：open方法已经调用，但是send方法没有调用，正在加载
	   * 2：send方法已经调用，已经加载
	   * 3：服务器开始接收和响应数据，交互中
	   * 4：表示响应成功。完成.只有4的时候才能使用req.responseText和req.responseXML
	   * */
    if (req.readyState == 4) {
  	  /**
  	   * req.status == 200:表示没有异常
  	   * 404：路径问题
  	   * 500：服务器有问题
  	   * */
      if (req.status == 200) {
    	  /**
      	   * responseXmlHandler(req.responseText,eleid);
      	   *req.responseText和req.responseXML:表示服务器相应给客户端的响应结果
      	   * 	req.responsText:文本格式（字符床、json格式）
      	   * 	req.responseXML：xml数据文件格式<response>dd</response>
      	   * */ 
        responseXmlHandler(req.responseText,eleid);
 
      } else {
        
        alert("HTTP error: "+req.status);
        return false;
      }
    }
  }
}





Pub.getStatisticReadyStateHandler =function (req, eleid,responseXmlHandler,noteId) {
  alert(req.readyState);
  return function () {
    if (req.readyState == 4) {
       
      if (req.status == 200) {
          
        responseXmlHandler(req.responseText,eleid);
 
      } else {
        
        alert("HTTP error: "+req.status);
        return false;
      }
    }else{
   
     var ele11 =document.getElementById(noteId);
    ele11.innerHTML="Loading Data";
    }
  }
}

/**
 * data:服务器响应的数据
 * eleid：表单Form2的名称
 * */
Pub.handleResponse= function handleResponse(data,eleid){
	//获取表单Form2的对象
      var ele =document.getElementById(eleid);
     //将服务器响应的数据全部放到页面Form2的表单 
      ele.innerHTML = data;
    
}


Pub.submitAction=function(domId,action){

  var req = Pub.newXMLHttpRequest();
  
  var handlerFunction = Pub.getReadyStateHandler(req, domId,Pub.handleResponse);
  req.onreadystatechange = handlerFunction;
  req.open("POST", action, false);
  req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

   
  req.send(null);

}




Pub.submitActionWithNote=function(domId,action,noteId){


  var req = Pub.newXMLHttpRequest();
  
  var handlerFunction = Pub.getStatisticReadyStateHandler(req, domId,Pub.handleResponse,noteId);
  req.onreadystatechange = handlerFunction;
  req.open("POST", action, true);
  req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

  req.send(null);

}


/**
 * 组织表单Form1中的元素的对象和值，作为传递给服务器的参数（格式：a=8&b=9）
 * sForm:表单Form1的名称
 * */

Pub.getParams2Str=function getParams2Str(sForm){

 var strDiv="";
      
 try {
    var objForm=document.forms[sForm];
  if (!objForm)
    return strDiv;
  var elt,sName,sValue;
  for (var fld = 0; fld < objForm.elements.length; fld++) {
      elt = objForm.elements[fld];    
      sName=elt.name;
      sValue=""+elt.value;
      if(fld==objForm.elements.length-1)
          strDiv=strDiv + sName+"="+sValue+"";
       else   
          strDiv=strDiv + sName+"="+sValue+"&";
   }


  }
  catch (ex) {
    return strDiv;
	}
 
   return strDiv;
}

function doContents(){
    if (xmlhttp.readyState < 4){
        alert("a");
    }
    else if (xmlhttp.readyState == 4 && xmlhttp.status == 200){
        alert("b");
    }
}

/*
*domId:表单Form2的名称
*action：url路径
*sForm：表单Form1的名称
*调用ajax《【重点】》
*/
Pub.submitActionWithForm=function(domId,action,sForm){
  
	
	/*1，创建ajax引擎*/  
	var req = Pub.newXMLHttpRequest();
	/*2，事件处理函数，【用来坚挺服务器和客户端响应状态】*/  
  var handlerFunction = Pub.getReadyStateHandler(req, domId,Pub.handleResponse);
  req.onreadystatechange = handlerFunction;
  /*3，打开链接 (post和get)*/  
  req.open("POST", action, true);
  req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");//如果是post请求，需要添加头部信息，如果不添加，此时req.send()发送数据就不能传递参数
  /*4，向服务器传递发送数据（格式 ：a=8&b=9）组织的是Form1表单中的元素的数据*/  
  var str = Pub.getParams2Str(sForm); 
  req.send(str);
}

Pub.submitActionWithFormGet=function(domId,action,sForm){
	  
	  var req = Pub.newXMLHttpRequest();
	  
	  var handlerFunction = Pub.getReadyStateHandler(req, domId,Pub.handleResponse);
	  req.onreadystatechange = handlerFunction;
	  
	  req.open("Get", action, true);
	  req.send(null);
}

