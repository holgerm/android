package edu.bonn.mobilegaming.geoquest.ui;

import android.util.Log;
import edu.bonn.mobilegaming.geoquest.mission.AudioRecord;
import edu.bonn.mobilegaming.geoquest.mission.ExternalMission;
import edu.bonn.mobilegaming.geoquest.mission.ImageCapture;
import edu.bonn.mobilegaming.geoquest.mission.MultipleChoiceQuestion;
import edu.bonn.mobilegaming.geoquest.mission.NPCTalk;
import edu.bonn.mobilegaming.geoquest.mission.QRTagReadingProduct;
import edu.bonn.mobilegaming.geoquest.mission.QRTagReadingTreasure;
import edu.bonn.mobilegaming.geoquest.mission.StartAndExitScreen;
import edu.bonn.mobilegaming.geoquest.mission.TextQuestion;
import edu.bonn.mobilegaming.geoquest.mission.VideoPlay;
import edu.bonn.mobilegaming.geoquest.mission.WebPage;
import edu.bonn.mobilegaming.geoquest.mission.WebTech;

public abstract class UIFactory {

    private static final String TAG = UIFactory.class.getName();
    private static UIFactory instance;

    UIFactory() {
    }

    public static void selectUIStyle(String uistyle) {
	Class<?> factoryClass = null;
	try {
	    factoryClass = Class.forName(UIFactory.class.getPackage().getName()
		    + "."
		    + uistyle
		    + UIFactory.class.getSimpleName());
	} catch (ClassNotFoundException e) {
	    Log.e(TAG,
		  "UIFactory class for style "
			  + uistyle
			  + " not found. Using Default instead.\n"
			  + e.getMessage());
	    e.printStackTrace();
	}
	try {
	    instance = (UIFactory) factoryClass.newInstance();
	} catch (IllegalAccessException e1) {
	    Log.e(TAG,
		  e1.getMessage());
	    e1.printStackTrace();
	} catch (InstantiationException e1) {
	    Log.e(TAG,
		  e1.getMessage());
	    e1.printStackTrace();
	}

    }

    public static UIFactory getInstance() {
	if (instance == null) {
	    instance = new DefaultUIFactory();
	}
	return instance;
    }

    public abstract NPCTalkUI createUI(NPCTalk activity);

    public abstract ImageCaptureUI createUI(ImageCapture activity);

    public abstract ExternalMissionUI createUI(ExternalMission activity);

    public abstract MultipleChoiceQuestionUI
	    createUI(MultipleChoiceQuestion activity);

    public abstract TextQuestionUI createUI(TextQuestion activity);

    public abstract WebTechUI createUI(WebTech activity);

    public abstract AudioRecordUI createUI(AudioRecord activity);

    public abstract VideoPlayUI createUI(VideoPlay activity);

    public abstract WebPageUI createUI(WebPage activity);

    public abstract StartAndExitScreenUI createUI(StartAndExitScreen activity);

    public abstract QRTagReadingTreasureUI
	    createUI(QRTagReadingTreasure activity);

    public abstract QRTagReadingProductUI
	    createUI(QRTagReadingProduct activity);

}
