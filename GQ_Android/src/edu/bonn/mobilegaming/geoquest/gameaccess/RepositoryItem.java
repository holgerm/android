package edu.bonn.mobilegaming.geoquest.gameaccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a data object class representing a repository as runtime object.
 * 
 * @author muegge
 * 
 */
public class RepositoryItem {

	private String name;
	private boolean onServer = false;
	private boolean onClient = false;
	private Map<String, GameItem> games = new HashMap<String, GameItem>();

	public String getName() {
		return name;
	}

	public RepositoryItem(String repoName) {
		this.name = repoName;
		games.clear();
	}

	public void setOnServer() {
		this.onServer = true;
	}

	public void setOnClient() {
		this.onClient = true;
	}

	public void addGame(GameItem gameItem) {
		games.put(gameItem.getName(), gameItem);
	}

	public List<String> gameNames() {
		return new ArrayList<String>(games.keySet());
	}

	public GameItem getGameItem(String gameName) {
		return games.get(gameName);
	}

}
