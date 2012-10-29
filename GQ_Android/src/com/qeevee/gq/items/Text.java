package com.qeevee.gq.items;


import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.qeevee.gq.xml.XMLUtilities;

import edu.bonn.mobilegaming.geoquest.mission.MissionActivity;

public class Text extends Item {

	@Override
	public View getView(MissionActivity containingActivity) {
		TextView textView = new TextView(containingActivity);
		textView.setText(Html.fromHtml(XMLUtilities.getXMLContent(xmlNode)));
		return textView;
	}

}
