var dialogs;
var speakers;
var buttons;
var currentDialog = 0;
var dNumb;

/*
 * Set the url of the NPCImage in the HTML file.
 * 
 * npcTalk_JSI.getNPCImgUrl(): see NPCTalkJSInterface
 */
function loadNPCImg() {
	var tag = findClass('charimage');
	var imgUrl = npcTalk_JSI.getNPCImgUrl()
	if (imgUrl && tag) {
		tag.src = imgUrl;
	} 
}

/*
 * Determines the nodes with class equal to 'dialogitem' or 'speaker'. 
 * If the first dialog has to be shown, the data of the nodes will be overwritten.
 * In the other case the nodes will be cloned and inserted behind the last dialog node. At last the 
 * data in the new nodes will be updated and the button will be created.
 */

// TODO -- Sabine -- WebTech Was ist wenn eine ID geklont wird? .. 
function loadNextNPCDialog() { 
	var speaker = speakers[currentDialog];
	var dialog = dialogs[currentDialog];
	var tagDia = findClass('dialogitem');
	var tagSp = findClass('speaker');
	
	if(currentDialog == 0) {	
		if(speaker && tagSp) {
			tagSp.firstChild.data = speaker + ":";
		}
		if(dialog && tagDia) {
			tagDia.firstChild.data = dialog;
		}
	} else {
		var clone;
		if(speaker && tagSp) {
			clone = tagSp.cloneNode(true);
			clone.firstChild.data = speaker + ":";
			tagSp.parentNode.appendChild(clone);
		}
		if(dialog && tagDia) {
			clone = tagDia.cloneNode(true);
			clone.firstChild.data = dialog;
			tagDia.parentNode.appendChild(clone);
		}
	}
	if(!tagSp && speaker) {
		npcTalk_JSI.logInfo("The speaker can't be shown, because the class " +
								"\"speaker\" wasn't found in the HTML file!");
	}
	if(!dialog) {
		npcTalk_JSI.logInfo("The " + currentDialog + ". dialog was empty!");
	}
	if(!tagDia) {
		npcTalk_JSI.logError("The needed class \"dialogitem\" wasn't found in the HTML file!");
	}
	createButton();
	++currentDialog;
}

/*
 * Creates the next button or the end button.
 */
function createButton() {
	var tag = findClass('nextdialogbuttontext');
	tag.value = buttons[currentDialog];
	if(currentDialog < dNumb - 1) {		
		tag.onclick = loadNextNPCDialog;
	} else {
		tag.onclick = endMission;
	}
}

function endMission() {
	npcTalk_JSI.endMission();
} 

/*
 * Saves all speakers, dialogs and buttons in arrays.
 */
function initDialogs() {
	dNumb = npcTalk_JSI.getDialogNumb();
	dialogs = new Array(dNumb);
	speakers = new Array(dNumb);
	buttons = new Array(dNumb);
	for(i = 0; i < dNumb; i++) {
		dialogs[i] = npcTalk_JSI.getDialog(i);
		speakers[i] = npcTalk_JSI.getSpeaker(i);
		buttons[i] = npcTalk_JSI.getButtonText(i);
	}	
}

/*
 * Loads all information about the mission and creates the HTML site.
 * 
 * HTML-Datei anpassen: JS-Dateien direkt laden.
 * 
 */
function init() {
	npcTalk_JSI.readDialogs();
	initDialogs();
	
	loadNPCImg();
	loadNextNPCDialog(true);
}

init();