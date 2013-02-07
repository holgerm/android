package edu.bonn.mobilegaming.geoquest.ui;

import org.dom4j.Element;

public class DefaultUIFactory {

    private static DefaultUIFactory instance;

    private DefaultUIFactory() {

    }

    public static DefaultUIFactory getInstance() {
	if (instance == null) {
	    instance = new DefaultUIFactory();
	}
	return instance;
    }

    public NPCTalkUI createNPCTalkUI(Element xmlMissionElement) {
	return new NPCTalkUIDefault(xmlMissionElement);
    }

    public ImageCaptureUI createImageCaptureUI(Element xmlMissionElement) {
	return new ImageCaptureUIDefault(xmlMissionElement);
    }

    public ExternalMissionUI createExternalMissionUI(Element xmlMissionElement) {
	return new ExternalMissionUIDefault(xmlMissionElement);
    }

    public MultipleChoiceQuestionUI
	    createMultipleChoiceQuestionUI(Element xmlMissionElement) {
	return new MultipleChoiceQuestionDefault(xmlMissionElement);
    }

    public TextQuestionUI createTextQuestionUI(Element xmlMissionElement) {
	return new TextQuestionDefault(xmlMissionElement);
    }

    public WebTechUI createWebTechUI(Element xmlMissionElement) {
	return new WebTechUIDefault(xmlMissionElement);
    }

    public AudioRecordUI createAudioRecordUI(Element xmlMissionElement) {
	return new AudioRecordUIDefault(xmlMissionElement);
    }

    public VideoPlayUI createVideoPlayUI(Element xmlMissionElement) {
	return new VideoPlayUIDefault(xmlMissionElement);
    }

    public WebPageUI createWebPageUI(Element xmlMissionElement) {
	return new WebPageUIDefault(xmlMissionElement);
    }

    public StartAndExitScreenUI
	    createStartAndExitScreenUI(Element xmlMissionElement) {
	return new StartAndExitScreenUIDefault(xmlMissionElement);
    }

    public QRTagReadingTreasureUI
	    createQRTagReadingTreasureUI(Element xmlMissionElement) {
	return new QRTagReadingTreasureUIDefault(xmlMissionElement);
    }

    public QRTagReadingProductUI
	    createQRTagReadingProductUI(Element xmlMissionElement) {
	return new QRTagReadingProductUIDefault(xmlMissionElement);
    }

}
