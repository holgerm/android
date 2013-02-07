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

    public abstract NPCTalkUI createNPCTalkUI(Element xmlMissionElement,
					      NPCTalk activity);

    public abstract ImageCaptureUI
	    createImageCaptureUI(Element xmlMissionElement,
				 ImageCapture activity);

    public abstract ExternalMissionUI
	    createExternalMissionUI(Element xmlMissionElement,
				    ExternalMission activity);

    public abstract MultipleChoiceQuestionUI
	    createMultipleChoiceQuestionUI(Element xmlMissionElement,
					   MultipleChoiceQuestion activity);

    public abstract TextQuestionUI
	    createTextQuestionUI(Element xmlMissionElement,
				 TextQuestion activity);

    public abstract WebTechUI createWebTechUI(Element xmlMissionElement,
					      WebTech activity);

    public abstract AudioRecordUI
	    createAudioRecordUI(Element xmlMissionElement,
				AudioRecord activity);

    public abstract VideoPlayUI createVideoPlayUI(Element xmlMissionElement,
						  VideoPlay activity);

    public abstract WebPageUI createWebPageUI(Element xmlMissionElement,
					      WebPage activity);

    public abstract StartAndExitScreenUI
	    createStartAndExitScreenUI(Element xmlMissionElement,
				       StartAndExitScreen activity);

    public abstract QRTagReadingTreasureUI
	    createQRTagReadingTreasureUI(Element xmlMissionElement,
					 QRTagReadingTreasure activity);

    public abstract QRTagReadingProductUI
	    createQRTagReadingProductUI(Element xmlMissionElement,
					QRTagReadingProduct activity);

}
