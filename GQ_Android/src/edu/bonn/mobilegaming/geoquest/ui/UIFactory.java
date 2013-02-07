package edu.bonn.mobilegaming.geoquest.ui;

import org.dom4j.Element;

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

    public abstract NPCTalkUI createNPCTalkUI(Element xmlMissionElement);

    public abstract ImageCaptureUI
	    createImageCaptureUI(Element xmlMissionElement);

    public abstract ExternalMissionUI
	    createExternalMissionUI(Element xmlMissionElement);

    public abstract MultipleChoiceQuestionUI
	    createMultipleChoiceQuestionUI(Element xmlMissionElement);

    public abstract TextQuestionUI
	    createTextQuestionUI(Element xmlMissionElement);

    public abstract WebTechUI createWebTechUI(Element xmlMissionElement);

    public abstract AudioRecordUI
	    createAudioRecordUI(Element xmlMissionElement);

    public abstract VideoPlayUI createVideoPlayUI(Element xmlMissionElement);

    public abstract WebPageUI createWebPageUI(Element xmlMissionElement);

    public abstract StartAndExitScreenUI
	    createStartAndExitScreenUI(Element xmlMissionElement);

    public abstract QRTagReadingTreasureUI
	    createQRTagReadingTreasureUI(Element xmlMissionElement);

    public abstract QRTagReadingProductUI
	    createQRTagReadingProductUI(Element xmlMissionElement);

}
