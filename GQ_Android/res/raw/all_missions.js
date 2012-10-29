function findClass(className) {
	var allElems = document.getElementsByTagName('*');
	var thisElem;
	for (var i = 0; i < allElems.length; i++) {
		thisElem = allElems[i];
		if (thisElem.className && thisElem.className == className) {
			return thisElem;
		}
	}
}

function loadJSFile(fileName) {
	var script = document.createElement('script');
	script.type = 'text/javascript';
	script.src = fileName;
	document.getElementsByTagName('head').item(0).appendChild(script);
	//npcTalk_JSI.logDebug("loaded customer javascript file: " + fileName);
}
