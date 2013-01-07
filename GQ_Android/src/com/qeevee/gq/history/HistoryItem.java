package com.qeevee.gq.history;

import edu.bonn.mobilegaming.geoquest.GeoQuestActivity;
import android.view.View;

public abstract class HistoryItem {

    private long id;
    protected Class<? extends GeoQuestActivity> activityType;

    /**
     * Each HistoryItem add itself to the history list.
     */
    public HistoryItem(GeoQuestActivity activity) {
	activityType = activity.getClass();
	History.getInstance().add(this);
    }

    public long getId() {
	return id;
    }

    void setId(long id) {
	this.id = id;
    }

    public abstract View getView(View convertView);

}
