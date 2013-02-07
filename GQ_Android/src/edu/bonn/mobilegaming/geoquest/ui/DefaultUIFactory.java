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

public class DefaultUIFactory extends UIFactory {

    DefaultUIFactory() {
	super();
    }

    public NPCTalkUI createNPCTalkUI(Element xmlMissionElement,
				     NPCTalk activity) {
	return new NPCTalkUIDefault(xmlMissionElement, activity);
    }

    public ImageCaptureUI createImageCaptureUI(Element xmlMissionElement,
					       ImageCapture activity) {
	return new ImageCaptureUIDefault(xmlMissionElement);
    }

    public ExternalMissionUI createExternalMissionUI(Element xmlMissionElement,
						     ExternalMission activity) {
	return new ExternalMissionUIDefault(xmlMissionElement);
    }

    public MultipleChoiceQuestionUI
	    createMultipleChoiceQuestionUI(Element xmlMissionElement,
					   MultipleChoiceQuestion activity) {
	return new MultipleChoiceQuestionDefault(xmlMissionElement);
    }

    public TextQuestionUI createTextQuestionUI(Element xmlMissionElement,
					       TextQuestion activity) {
	return new TextQuestionDefault(xmlMissionElement);
    }

    public WebTechUI createWebTechUI(Element xmlMissionElement,
				     WebTech activity) {
	return new WebTechUIDefault(xmlMissionElement);
    }

    public AudioRecordUI createAudioRecordUI(Element xmlMissionElement,
					     AudioRecord activity) {
	return new AudioRecordUIDefault(xmlMissionElement);
    }

    public VideoPlayUI createVideoPlayUI(Element xmlMissionElement,
					 VideoPlay activity) {
	return new VideoPlayUIDefault(xmlMissionElement);
    }

    public WebPageUI createWebPageUI(Element xmlMissionElement,
				     WebPage activity) {
	return new WebPageUIDefault(xmlMissionElement);
    }

    public StartAndExitScreenUI
	    createStartAndExitScreenUI(Element xmlMissionElement,
				       StartAndExitScreen activity) {
	return new StartAndExitScreenUIDefault(xmlMissionElement);
    }

    public QRTagReadingTreasureUI
	    createQRTagReadingTreasureUI(Element xmlMissionElement,
					 QRTagReadingTreasure activity) {
	return new QRTagReadingTreasureUIDefault(xmlMissionElement);
    }

    public QRTagReadingProductUI
	    createQRTagReadingProductUI(Element xmlMissionElement,
					QRTagReadingProduct activity) {
	return new QRTagReadingProductUIDefault(xmlMissionElement);
    }

}
