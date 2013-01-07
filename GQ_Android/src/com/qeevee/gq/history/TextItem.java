package com.qeevee.gq.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import edu.bonn.mobilegaming.geoquest.GeoQuestActivity;
import edu.bonn.mobilegaming.geoquest.GeoQuestApp;
import edu.bonn.mobilegaming.geoquest.R;

public class TextItem extends HistoryItem {

    private CharSequence text;

    public TextItem(CharSequence text,
		    GeoQuestActivity activity) {
	super(activity);
	this.text = text;
    }

    public View getView(View convertView) {
	TextView tv;
	if (convertView == null) {
	LayoutInflater inflater = (LayoutInflater) GeoQuestApp.getContext()
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	tv = (TextView) inflater.inflate(R.layout.history_text_item,
						  null);
	}
	else {
	    tv = (TextView) convertView;
	}
	tv.setText(text);
	return tv;
    }

}
