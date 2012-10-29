package com.qeevee.gq.tests;

import static org.junit.Assert.fail;

import java.io.File;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import edu.bonn.mobilegaming.geoquest.GeoQuestApp;

public class TestUtils {

	public static Document loadTestGame(String gameName) {
		Document document = null;
		SAXReader reader = new SAXReader();
		try {
			File gameFile = getGameFile(gameName);
			document = reader.read(gameFile);
			GeoQuestApp.setRunningGameDir(new File(gameFile.getParent()));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}

	public static File getGameFile(String gameName) {
		URL xmlFileURL = TestUtils.class.getResource("/testgames/" + gameName
				+ "/game.xml");
		if (xmlFileURL == null)
			fail("Resource file not found for game: " + gameName);
		return new File(xmlFileURL.getFile());
	}

}
