/**
 * Implementation for the diploma thesis "Adaption in digitalen mobilen Lernspielen - Anwendung in GeoQuest"
 * 
 * @author Sabine Polko
 */
package edu.bonn.mobilegaming.geoquest.xmltools;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

public class XmlToolsMissionPool {

    private static final int dialogNumber = 5;
    private static Document doc;
   
    /**
     * Creates a MissionPool that only contains NpcTalks, with 5 dialogs, with
     * 50 words.
     */
    public static Document createMissionPoolXml(int poolNumber, int missionNumber) {
	doc = DocumentFactory.getInstance().createDocument();
	doc.setName("missionpool.xml");
	Element root = doc.addElement("missionpool");
	root.addAttribute("xmlns", "http://geoquest.qeevee.org/");
	root.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema−instance");

	for (int poolNumb = 0; poolNumb < poolNumber; poolNumb++) {
	    Element elem = root.addElement("pool");
	    elem.addAttribute("missionid", "mission_" + Integer.toString(poolNumb));

	    for (int missionsNumb = 0; missionsNumb < missionNumber; missionsNumb++) {
		Element missionEle = elem.addElement("mission");
		setMissionAttributes(poolNumb, missionsNumb, missionEle);
		for (int dialogNumber = 0; dialogNumber < XmlToolsMissionPool.dialogNumber; dialogNumber++) {
		    createDialogItem(missionEle);
		}
	    }
	}
	return doc;
    }

    public static void setMissionAttributes(int poolNumb, int missionsNumb, Element missionEle) {
	missionEle.addAttribute("id",
		"missionId_" + Integer.toString(poolNumb) + "_" + Integer.toString(missionsNumb));
	missionEle.addAttribute("type", "NPCTalk");
	missionEle.addAttribute("name",
		"missionId_" + Integer.toString(poolNumb) + "_" + Integer.toString(missionsNumb));
    }

    private static void createDialogItem(Element mission) {
	Element ele;
	ele = mission.addElement("dialogitem");
	ele.setText("Lorem ipsum dolor sit amet, consetetur sadipscing "
		+ "elitr, sed diam nonumy eirmod tempor invidunt ut "
		+ "labore et dolore magna aliquyam erat, sed diam "
		+ "voluptua. At vero eos et accusam et justo duo dolores "
		+ "et ea rebum. Stet clita kasd gubergren, no sea takimata "
		+ "sanctus est Lorem ipsum dolor sit amet.");
	ele.addAttribute("speaker", "Sprecher");
	ele.addAttribute("nextdialogbuttontext", "Weiter...");
    }

}
