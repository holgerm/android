package edu.bonn.mobilegaming.geoquest.gameaccess;

import org.dom4j.Element;

import android.util.Log;
import edu.bonn.mobilegaming.geoquest.GeoQuestApp;
import edu.bonn.mobilegaming.geoquest.R;

public class GameItem {

//	private String xmlVersion = "";
	private String name = "";
	private String fileName = "";
	private RepositoryItem repositoryItem = null;
	private double lastmodifiedServerSide = 0l;
	private double lastmodifiedClientSide = 0l;
	private double longitude = 0.0d;
	private double latitude = 0.0d;
//	private boolean updateAvailable = false;
	private boolean onServer = false;
	private boolean onClient = false;

	public boolean isOnServer() {
		return onServer;
	}

	public void setOnServer(boolean onServer) {
		this.onServer = onServer;
	}

	public boolean isOnClient() {
		return onClient;
	}

	public void setOnClient(boolean onClient) {
		this.onClient = onClient;
	}

	public double getLatitude() {
		return latitude;
	}

	private void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	private void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLastmodifiedServerSide() {
		return lastmodifiedServerSide;
	}

	private void setLastmodifiedServerSide(double lastmodified) {
		this.lastmodifiedServerSide = lastmodified;
	}

	public double getLastmodifiedClientSide() {
		return lastmodifiedClientSide;
	}

	public void setLastmodifiedClientSide(long lastmodifiedClientSide) {
		this.lastmodifiedClientSide = lastmodifiedClientSide;
	}

	private GameItem(String name, RepositoryItem repositoryItem) {
		this.name = name;
		setRepositoryItem(repositoryItem);
	}

	private void setRepositoryItem(RepositoryItem repositoryItem) {
		this.repositoryItem = repositoryItem;
	}

	/**
	 * Create a GameItem from server-side information (repolist.php).
	 * 
	 * @param gameNode
	 *            the xml node for game from the result of repolist.php. This is
	 *            different to the game node in game.xml which is not suitable
	 *            here!
	 * @param repoItem
	 *            where the new game item will be put into the list.
	 * @return
	 */
	public static GameItem createFromRepoListGameNode(Element gameNode,
			RepositoryItem repoItem) {
		String name = gameNode.attributeValue("name");
		String gameXMLFormat = gameNode.attributeValue("xmlformat");
		String fileName = gameNode.attributeValue("file");
		String lastmodifiedAsString = gameNode.attributeValue("lastmodified");
		// set game file size

		if (name == null
				|| gameXMLFormat == null
				|| gameXMLFormat.compareToIgnoreCase(GeoQuestApp.getInstance()
						.getString(R.string.xmlformat)) < 0)
			return null;
		// i.e. name must be given and version number must match

		GameItem gameItem = new GameItem(name, repoItem);
//		gameItem.setXMLVersion(gameXMLFormat);
		gameItem.setFileName(fileName);

		if (lastmodifiedAsString == null) {
			gameItem.setLastmodifiedServerSide(0);
		} else {
			gameItem.setLastmodifiedServerSide(Double.parseDouble(lastmodifiedAsString));
		}

		String tmp = gameNode.attributeValue("latitude");
		if (tmp == null) {
			gameItem.setLatitude(0);
		} else {
			gameItem.setLatitude(Double.parseDouble(tmp));
		}

		tmp = gameNode.attributeValue("longitude");
		if (tmp == null) {
			gameItem.setLongitude(0);
		} else {
			gameItem.setLongitude(Double.parseDouble(tmp));
		}
		
		gameItem.setOnServer(true);

		return gameItem;
	}

	/**
	 * Create a GameItem from client-side information (local file game.xml).
	 * 
	 * @param gameNode
	 * @param localGameLastModified
	 * @param gameFileName
	 * @param repoItem
	 * @return
	 */
	public static GameItem createFromGameFileGameNode(Element gameNode,
			long localGameLastModified, String gameFileName,
			RepositoryItem repoItem) {
		String name = gameNode.attributeValue("name");
		String gameXMLFormat = gameNode.attributeValue("xmlformat");

		if (name == null
				|| gameXMLFormat == null
				|| gameXMLFormat.compareToIgnoreCase(GeoQuestApp.getInstance()
						.getString(R.string.xmlformat)) < 0)
			return null;
		// i.e. name must be given and version number must match

		GameItem gameItem = new GameItem(name, repoItem);
		gameItem.setLastmodifiedClientSide(localGameLastModified);
		gameItem.setFileName(gameFileName);
		gameItem.setOnClient(true);

		return gameItem;
	}

	private void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the relative path on the game server to this game. This path
	 *         starts within the folder containing repositories, e.g. {@code
	 *         geoquest.qeevee.org/repositories}
	 */
	public String getGamePath() {
		return (repositoryItem.getName() + "/games/" + this.fileName);
	}

//	private void setXMLVersion(String gameXMLFormat) {
//		this.xmlVersion = gameXMLFormat;
//	}

	public String getName() {
		return this.name;
	}

//	public void setUpdateAvailable() {
//		this.updateAvailable = true;
//	}

	public boolean isUpdateAvailable() {
		Log.i("Timestamp check", "cl: " + getLastmodifiedClientSide() + "; sr: " + getLastmodifiedServerSide());
		return (getLastmodifiedClientSide() < getLastmodifiedServerSide());
	}
	
	public boolean isDownloadNeeded() {
		return (!isOnClient() || isUpdateAvailable());
	}

	public String getFileName() {
		return this.fileName;
	}

}
