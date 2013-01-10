package com.qeevee.gq.history;

import java.util.ArrayList;
import java.util.List;

public class History {

    private static History instance;

    public static History getInstance() {
	if (History.instance == null)
	    History.instance = new History();
	return History.instance;
    }

    private long id;

    private long makeID() {
	return id++;
    }

    private List<HistoryItem> list;

    private History() {
	init();
    }

    private void init() {
	id = 1l;
	list = new ArrayList<HistoryItem>();
    }

    int add(HistoryItem item) {
	item.setId(makeID());
	list.add(item);
	return list.size() - 1;
    }

    public int numberOfItems() {
	return list.size();
    }

    public HistoryItem getItem(int position) {
	return list.get(position);
    }

    public HistoryItem getLastItem() {
	return getItem(numberOfItems() - 1);
    }

    public long getItemId(int position) {
	return list.get(position).getId();
    }

    public void clear() {
	init();
    }

    public int getIndex(HistoryItem historyItem) {
	return list.indexOf(historyItem);
    }

}
