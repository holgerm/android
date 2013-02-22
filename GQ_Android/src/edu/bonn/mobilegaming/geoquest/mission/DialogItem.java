package edu.bonn.mobilegaming.geoquest.mission;

import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * Dialog Items are needed by the NPC talk mission. A dialog item is for example
 * a sentence the NPC will say.
 * 
 * @author Folker Hoffmann
 * @author Krischan Udelhoven
 */
public class DialogItem {

    /** name of the speaker */
    private String speaker;
    /** DialogItems text */
    private String text;
    /** Array of words */
    private String[] textElements;
    /** used for iterating through the textElements */
    private int counter = 0;
    private CharSequence nextDialogButtonText = null;

    public CharSequence getNextDialogButtonText() {
	return nextDialogButtonText;
    }

    private String audioFilePath;
    public boolean blocking;

    private Element xml = null;

    /**
     * Constructor gets the information from the passed xmlElement
     * 
     * @param xmlElement
     *            the dialogItem element from the XML file
     */
    public DialogItem(Element xmlElement) {
	xml = xmlElement;
	speaker = xml.attributeValue("speaker");

	// read nextdialogbuttontext:
	Attribute a = (Attribute) xml.selectSingleNode("@nextdialogbuttontext");
	if (a != null) {
	    nextDialogButtonText = a.getText();
	}

	// read sound (might be an audiofile for listenig to the text):
	a = (Attribute) xml.selectSingleNode("@sound");
	if (a != null) {
	    audioFilePath = a.getText();
	}

	// read blocking attribute:
	a = (Attribute) xml.selectSingleNode("@blocking");
	if (a != null
		&& a.getText().equals("false"))
	    blocking = false;
	else
	    blocking = true;

	text = xml.getText().replaceAll("\\s+",
					" ").trim();
	if (text.startsWith(" ")) {
	    text = text.substring(1);
	}

	textElements = text.split("\\s+"); // Split anhand der Whitespaces
    }

    /**
     * @return the speaker's name or <code>null</code> if no speaker given.
     */
    public String getSpeaker() {
	return speaker;
    }

    public String getText() {
	return text;
    }

    public String getAudioFilePath() {
	return audioFilePath;
    }

    /**
     * @return returns the number of words
     */
    public int getNumParts() {
	return textElements.length;
    }

    /**
     * get the next word
     * 
     * @return returns each word as a CharSquence or null of there is no word
     *         left
     */
    public CharSequence getNextPart() {
	if (counter >= textElements.length)
	    return null;
	if (counter == textElements.length - 1)
	    return textElements[counter++]
		    + "\n";

	return textElements[counter++]
		+ " "; // add white space for all words
		       // but the last
    }
}