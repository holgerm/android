package edu.bonn.mobilegaming.geoquest.ui;

import org.dom4j.Element;

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

    private static UIFactory instance;

    UIFactory() {
    }

    public static UIFactory getInstance() {
	if (instance == null) {
	    instance = new DefaultUIFactory();
	}
	return instance;
    }

    public abstract NPCTalkUI createUI(NPCTalk activity);

    public abstract ImageCaptureUI createUI(ImageCapture activity);

    public abstract ExternalMissionUI
	    createUI(ExternalMission activity);

    public abstract MultipleChoiceQuestionUI
	    createUI(MultipleChoiceQuestion activity);

    public abstract TextQuestionUI createUI(TextQuestion activity);

    public abstract WebTechUI createUI(WebTech activity);

    public abstract AudioRecordUI createUI(AudioRecord activity);

    public abstract VideoPlayUI createUI(VideoPlay activity);

    public abstract WebPageUI createUI(WebPage activity);

    public abstract StartAndExitScreenUI
	    createUI(StartAndExitScreen activity);

    public abstract QRTagReadingTreasureUI
	    createUI(QRTagReadingTreasure activity);

    public abstract QRTagReadingProductUI
	    createUI(QRTagReadingProduct activity);

}
