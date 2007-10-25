var layerRef='null', styleSwitch='null';
var currentMenu=null;

if (navigator.appName == 'Netscape') {
  layerRef='document.layers';
  styleSwitch='';
}else{
  layerRef='document.all';
  styleSwitch='.style';
}

var leftRef = 'null', topRef = 'null';
if (navigator.appName == 'Netscape') {
  leftRef = '.left';
  topRef = '.top';
}else{
  leftRef = '.pixelLeft';
  topRef = '.pixelTop';
}

function getStyle(object){
	return object.style;
}

function moveLayer(layerName,newLeft,newTop){
  	eval(layerRef+'["'+layerName+'"]'+styleSwitch+topRef+'='+newTop);
  	eval(layerRef+'["'+layerName+'"]'+styleSwitch+leftRef+'='+newLeft);
}

function showLayer(layerName){
	menu = eval(layerRef+'["'+layerName+'"]');
	menu_st = getStyle(menu);
	menu_st.visibility="visible";
}

function hideLayer(el){
	menu = eval(layerRef+'["'+el+'"]');
	if(menu.vsb) return;
	menu_st = eval(layerRef+'["'+el+'"]'+styleSwitch);
	menu_st.visibility="hidden";
}

function isVisibleLayer(el){
	menu_st = eval(layerRef+'["'+el+'"]'+styleSwitch);
	if(menu_st.visibility == "visible") return true;
	return false;
}

//[Zhilin Mike][15 oct 2005] Bug #0001 fix begin
function resetLayer(el){
	moveLayer(el,0,0);
}
//[Zhilin Mike][15 oct 2005] Bug #0001 fix end

function showMenu(el){
	menu = eval(layerRef+'["'+el+'"]');
	if(menu == null){
		alert('Menu is NULL. Element is '+el);
	}
	menu.vsb = true;
	if(isVisibleLayer(el))return;
	var parent = event.fromElement;
	var EvX = event.clientX - event.offsetX + document.body.scrollLeft;
	var EvY = event.clientY - event.offsetY + 16 + document.body.scrollTop;
	moveLayer(el,EvX,EvY);
	
	if(currentMenu != null){
		objCurrentMenu = eval(layerRef+'["'+currentMenu+'"]');
		objCurrentMenu.vsb = false;
		hideLayer(currentMenu);
	}
	showLayer(el);
	currentMenu = el;
}

function hideMenu(el){
	menu.vsb = false;
	if(isVisibleLayer(el)) setTimeout('hideLayer("'+el+'")',700);
}

//------------
//-- Tables --
//------------

function createTable(nameTable,rowNumber,columnNumber,classTableName,classTDName,content,parent){
	
	var newTable = document.createElement('TABLE');
	
	newTable.id  = nameTable;
	newTable.className = classTableName;
	newTable.cellSpacing = 0;
	newTable.border = 0;	
	
	for( rowIndex=0 ; rowIndex < rowNumber; rowIndex++){
		var row = newTable.insertRow(rowIndex);	
		row.id  = nameTable + 'Row' + rowIndex;	
		row.height = 16;	
		for(columnIndex = 0; columnIndex < columnNumber; columnIndex++){
			var cell = row.insertCell(columnIndex);
			cell.id  = nameTable + 'Cell #' + rowIndex + '.' + columnNumber;
			cell.innerHTML = content[ rowIndex+','+columnIndex];
			cell.className = classTDName;	
		}
	}
	
	var par = document.getElementById(parent);
	if(par == null){
		alert('Parent: '+parent.id);
	}
	par.appendChild(newTable);
}

//--------------
//-- Handlers --
//--------------

function handler_showMenu(e){
	showMenu(currentMenu);
}

function handler_hideMenu(e){
	hideMenu(currentMenu);
}

//------------
//-- Layers --
//------------

function createLayer(name,parent,visibility,className){
	var layer = document.createElement('DIV');
	layer.style.visibility = visibility;
	layer.id = name;
	layer.className = className;
	layer.style.position = "absolute";
	layer.onmouseover = handler_showMenu;
	layer.onmouseout = handler_hideMenu;	
	var par = document.getElementById(parent);
	par.appendChild(layer);
}

//-----------------
//-- Menu Blocks --
//-----------------

function createContentMenu(name,ref,className){
	var content = new Array;
	var mouseEvent = "onMouseOver=\"showMenu('"+ name +"Layer')\" onMouseOut=\"hideMenu('"+ name +"Layer')\"";
	var href = "<a href=" + ref + " class=" + className + " " + mouseEvent + ">&nbsp;"+ name +"&nbsp;</a>";
	content["name"] = name;
	content["rows"] = 0;
	content["ref"] 	= href;
	return content;
}

function insertReference(cntMenu,ref,refName,className){
	var href = "<a href=" + ref + " class=" + className + ">&nbsp;"+ refName +"&nbsp;</a>";
	var order = cntMenu["rows"];
	
	cntMenu[""+order+",0"] = href;
	order++;
	cntMenu["rows"] = order;
}

//---------------
//-- Menu Bars --
//---------------

function createContentMenuBar(name,classBarTBN,classBarTDN,classMenuTBN,classMenuTDN){
	var content = new Array;
	content["name"]	= name;
	content["classMenuTableName"] 	= classMenuTBN;
	content["classMenuTDName"] 		= classMenuTDN;
	content["classBarTableName"] 	= classBarTBN;
	content["classBarTDName"]		= classBarTDN;
	content["menuItems"]	= new Array;
	content["countMenu"]	= 0;
	
	return content;
}

function insertMenu(cntBar,cntMenu){
	var items = cntBar["menuItems"];
	var order = cntBar["countMenu"];
	var ref  = cntMenu["ref"];
	items[""+order] = cntMenu;
	items["0,"+order] = ref;//TODO: Reference must be here!
	order++;
	cntBar["countMenu"] = order; 
}

function createMenuBar(cntBar,parent){
	var countMenu= cntBar["countMenu"];
	var nameBar	= cntBar["name"];
	var items 	= cntBar["menuItems"];
	
	//create table for bar
	createTable(nameBar+"Table",1,countMenu,cntBar["classBarTableName"],cntBar["classBarTDName"],items,parent);
	
	for(indexMenu=0; indexMenu<countMenu;indexMenu++){
		var cntMenu = items[""+indexMenu];
		if(cntMenu == null){
			alert("cntMenu:"+indexMenu);
		}
		var count 	= cntMenu["rows"];
		
		//TODO: Class of Layer!
		createLayer(cntMenu["name"]+"Layer",'body','hidden','dropmenu');
		//[Zhilin Mike][15 oct 2005] Bug #0001 fix begin
		resetLayer(cntMenu["name"]+"Layer");
		//[Zhilin Mike][15 oct 2005] Bug #0001 fix end
		createTable(cntMenu["name"]+"Table",count,1,cntBar["classMenuTableName"],cntBar["classMenuTDName"],cntMenu,cntMenu["name"]+"Layer");	
	}
};

//------------
//-- Menu ----
//------------

function initMenu(){
	var contentMenuBar = createContentMenuBar("TopMenuBar",'menuBar','menuBar','menu','menu');
	
	var contentProjectDev 	= createContentMenu("Main |","#","menu");
	var contentAbout 		= createContentMenu("About |","#","menu");
	var contentHelp 		= createContentMenu("Help","#","menu");
	
	insertReference(contentProjectDev,'status.jsp','Project Status','menu');
	insertReference(contentProjectDev,'http://194.85.82.201:8080/forums/list.page','Forum','menu');
	insertReference(contentProjectDev,'#','Bug Report','menu');
	insertReference(contentProjectDev,'#','Wishs','menu');
	
	insertReference(contentAbout,'#','Information','menu');	
	insertReference(contentAbout,'#','About','menu');
	
	insertMenu(contentMenuBar,contentProjectDev);
	insertMenu(contentMenuBar,contentAbout);
	insertMenu(contentMenuBar,contentHelp);
	
	createMenuBar(contentMenuBar,"TopToolBar");
}