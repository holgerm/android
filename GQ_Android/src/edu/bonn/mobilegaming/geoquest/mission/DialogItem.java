package edu.bonn.mobilegaming.geoquest.mission;

import org.dom4j.Attribute;
import org.dom4j.Element;

import android.text.Html;

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
	/** name of the speaker in bold font */
	private CharSequence formattedSpeaker;
	/** used for iterating through the textElements */
	private int counter = 0;
	public CharSequence nextDialogButtonText = null;
	private String audioFilePath;
	public boolean blocking;

	/**
	 * Constructor gets the information from the passed xmlElement
	 * 
	 * @param xmlElement
	 *            the dialogItem element from the XML file
	 */
	public DialogItem(Element xmlElement) {
		speaker = xmlElement.attributeValue("speaker");

		// read nextdialogbuttontext:
		Attribute a = (Attribute) xmlElement
				.selectSingleNode("@nextdialogbuttontext");
		if (a != null) {
			nextDialogButtonText = a.getText();
		}

		// read sound (might be an audiofile for listenig to the text):
		a = (Attribute) xmlElement.selectSingleNode("@sound");
		if (a != null) {
			audioFilePath = a.getText();
		}

		// read blocking attribute:
		a = (Attribute) xmlElement.selectSingleNode("@blocking");
		if (a != null && a.getText().equals("false"))
			blocking = false;
		else
			blocking = true;

		text = xmlElement.getText().replaceAll("\\s+", " ").trim();
		if (text.startsWith(" ")) {
			text = text.substring(1);
		}

		if (speaker != null)
			// just in case a speaker is given: add it in bold face before the
			// text:
			formattedSpeaker = Html.fromHtml("<b>" + speaker + ": </b>");
		else
			formattedSpeaker = "";

		textElements = text.split("\\s+"); // Split anhand der Whitespaces
	}

	/**
	 * @return name of the speaker in bold font (HTML tags)
	 */
	public CharSequence getFormattedSpeaker() {
		return formattedSpeaker;
	}

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
			return textElements[counter++] + "\n";

		return textElements[counter++] + " "; // add white space for all words
												// but the last
	}
}