package com.qeevee.gq.tests;

import static org.junit.Assert.fail;

import java.io.File;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import android.content.Intent;

import com.xtremelabs.robolectric.Robolectric;

import edu.bonn.mobilegaming.geoquest.GameLoader;
import edu.bonn.mobilegaming.geoquest.GeoQuestActivity;
import edu.bonn.mobilegaming.geoquest.GeoQuestApp;
import edu.bonn.mobilegaming.geoquest.Start;
import edu.bonn.mobilegaming.geoquest.mission.Mission;

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

	public static void setUpMissionTypeTest(String missionType, String missionID, GeoQuestActivity missionActivity) throws ClassNotFoundException {
		setUpMissionTypeTest(missionType + "Test", missionType, missionID, missionActivity);
	}

	public static void setUpMissionTypeTest(String gameFileName, String missionType, String missionID, GeoQuestActivity missionActivity) throws ClassNotFoundException {
		Start start = new Start();
		GeoQuestApp app = (GeoQuestApp) start.getApplication();
		app.onCreate();
		Mission.setMainActivity(start);
		GameLoader.startGame(null, TestUtils.getGameFile(gameFileName));
		Intent startMCQIntent = new Intent(start, Class.forName(Mission.getPackageBaseName() + missionType));
		startMCQIntent.putExtra("missionID", missionID);
		Robolectric.shadowOf(missionActivity).setIntent(startMCQIntent);
	}

}
