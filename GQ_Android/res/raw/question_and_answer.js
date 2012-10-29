
function MyParseXml(xmlString) {	
	questionAndAnswer_JSI.print("MyParseXml");
	if (window.DOMParser) {
		questionAndAnswer_JSI.print("DOMParser");
	  parser = new DOMParser();
	  xmlDoc = parser.parseFromString(text,"text/xml");
	  x = xmlDoc.getElementsByTagName("mission");
	  questionAndAnswer_JSI.print("DOMParser: " + x);
	} else {
		questionAndAnswer_JSI.print("No DOMParser");
	}  
	questionAndAnswer_JSI.print("No If DOMParser");
}

function init() {
	questionAndAnswer_JSI.print("Init Q&A");
	var missionXml = questionAndAnswer_JSI.getXmlDocument();
	//questionAndAnswer_JSI.print(missionXml);
	MyParseXml(missionXml);
}

init();