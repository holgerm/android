package com.qeevee.gq.history;

import android.view.View;

public abstract class HistoryItem {

    private long id;
    
    /**
     * Each HistoryItem add itself to the history list.
     */
    public HistoryItem() {
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
