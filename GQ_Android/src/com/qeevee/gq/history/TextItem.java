package com.qeevee.gq.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import edu.bonn.mobilegaming.geoquest.GeoQuestActivity;
import edu.bonn.mobilegaming.geoquest.GeoQuestApp;
import edu.bonn.mobilegaming.geoquest.R;

/**
 * @author muegge
 *
 */
public class TextItem extends HistoryItem {

    private CharSequence text;

    /**
     * Main constructor which should directly or indirectly be called by every
     * other constructor of this class.
     * 
     * This constructor should be the only one who calls the super constructor.
     * 
     * @param textType
     *            if you do not know what to do, use {@link TextType#DEFAULT}
     *            which is {@link TextType#PLAIN}.
     */
    public TextItem(CharSequence text,
		    GeoQuestActivity activity,
		    TextType textType) {
	super(activity, textType);
	this.text = text;
    }

    public View getView(View convertView) {
	TextView tv;
	if (convertView == null) {
	    LayoutInflater inflater = (LayoutInflater) GeoQuestApp.getContext()
		    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    tv = (TextView) inflater.inflate(R.layout.history_text_item,
					     null);
	} else {
	    tv = (TextView) convertView;
	}
	tv.setText(text);
	return tv;
    }

}
