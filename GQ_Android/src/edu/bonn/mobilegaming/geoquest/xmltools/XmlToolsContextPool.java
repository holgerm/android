/**
 * Implementation for the diploma thesis "Adaption in digitalen mobilen Lernspielen - Anwendung in GeoQuest"
 * 
 * @author Sabine Polko
 */

package edu.bonn.mobilegaming.geoquest.xmltools;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

import android.util.Log;
import edu.bonn.mobilegaming.geoquest.GeoQuestApp;

public class XmlToolsContextPool {
    
    Document doc;
    
    public void runtimeTest() {
	for(int vNumb = 1; vNumb < 5; ++vNumb)
	    runtimeTest(10, vNumb);
	for(int vNumb = 5; vNumb < 320; vNumb = vNumb*2)
	    runtimeTest(10, vNumb);
    }

    public void runtimeTest(int poolNumber, int vectorNumber) {
	doc = createContextPoolXml(poolNumber, vectorNumber);
	Long start = System.currentTimeMillis();
	GeoQuestApp.adaptionEngine.createPools(doc);
	Log.d("RuntimeMeasure", poolNumber*vectorNumber + ": " + (System.currentTimeMillis() - start) + " ms");
    }
    

    public Document createContextPoolXml( int poolNumber, int vectorNumber) {
	doc = DocumentFactory.getInstance().createDocument();
	Element root = doc.addElement("contextpool");
	root.addAttribute("xmlns", "http://geoquest.qeevee.org/");
	root.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema−instance");

	for (int poolNumb = 0; poolNumb < poolNumber; poolNumb++) {
	    Element elem = root.addElement("pool");
	    elem.addAttribute("missionid", "mission_" + Integer.toString(poolNumb));
	    
	    for (int cVectorNumb = 0; cVectorNumb < vectorNumber; cVectorNumb++) {
		Element vectorEle = elem.addElement("contextvector");
		vectorEle.addAttribute("missionid", "vector_" + Integer.toString(cVectorNumb));
		initVector(vectorEle);
	    }
	}
	return doc;
    }

    private void initVector(Element vector) {
	Element ele; 
	ele = vector.addElement("age");
	ele.setText("5");
	ele = vector.addElement("knowlevel");
	ele.setText("5");
	ele = vector.addElement("activlevel");
	ele.setText("5");
	ele = vector.addElement("techlevel");
	ele.setText("5");
	ele = vector.addElement("duration");
	ele.setText("5");
	ele = vector.addElement("loclat");
	ele.setText("50.72055");
	ele = vector.addElement("loclong");
	ele.setText("7.121776");
	ele = vector.addElement("locaccuracy");
	ele.setText("5.0");
    }

}
