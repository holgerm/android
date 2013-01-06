package com.qeevee.gq.history;

import edu.bonn.mobilegaming.geoquest.GeoQuestApp;
import android.view.View;
import android.widget.TextView;

public class TextItem extends HistoryItem {

    private CharSequence text;

    public TextItem(CharSequence text) {
	super();
	this.text = text;
    }

    public View getView(View convertView) {
	TextView tv = new TextView(GeoQuestApp.getContext());
	tv.setText(text);
	return tv;
    }

}
