package edu.bonn.mobilegaming.geoquest.gameaccess;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;
import org.dom4j.io.SAXReader;

import android.content.SharedPreferences;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import edu.bonn.mobilegaming.geoquest.GeoQuestApp;
import edu.bonn.mobilegaming.geoquest.GeoQuestProgressHandler;
import edu.bonn.mobilegaming.geoquest.Preferences;
import edu.bonn.mobilegaming.geoquest.R;

/**
 * Singleton class representing the GeoQuest server.
 * 
 * @author muegge
 *
 */
public class GeoQuestServerProxy implements IGeoQuestServerProxy {

	private static GeoQuestServerProxy instance;

	public static GeoQuestServerProxy getInstance() {
		if (instance == null) 
			instance = new GeoQuestServerProxy();
		return instance;
	}
	
	private GeoQuestServerProxy() {};

	/**
	 * Reads repository and game metadata from the geoquest server currently set
	 * in the app preferences.
	 * 
	 * In case of an error (malformed URL, io problem, problem with the
	 * retrieved document) the progress handler is informed and null is returned.
	 * 
	 * @param progressHandler
	 * @param prefs
	 * @return the XML document containing the current metadata about
	 *         repositories and games on the given geoquest server. The server
	 *         is specified by the preferences c.f.
	 *         {@link Preferences#PREF_KEY_SERVER_URL} or the default values set
	 *         in {@link R.string#geoquest_server_url} or null in case of an
	 *         error.
	 */
	public Document getRepoMetadata(
			final GeoQuestProgressHandler progressHandler) {
	
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(GeoQuestApp.getContext());
		URL url = null;
		String urlString = prefs.getString(Preferences.PREF_KEY_SERVER_URL,
				GeoQuestApp.getContext().getString(R.string.geoquest_server_url))
				+ GeoQuestApp.getContext().getString(R.string.geoquest_repolist_relpath);
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			Log.e(GeoQuestApp.class.getSimpleName(), "MalformedURLException: "
					+ urlString);
			progressHandler.handleError(R.string.error_fetching_remote_repo_list_url);
			return null;
		}
	
		// TODO: fetch actual size from server
		int size = 0;
		InputStream stream;
		try {
			stream = url.openStream();
			size = stream.available();
			stream.close();
		} catch (IOException e) {
			Log.e(GeoQuestApp.class.getSimpleName(), "IOException: "
					+ e.getMessage());
			progressHandler.handleError(R.string.error_fetching_remote_repo_list_io);
			return null;
		}
	
		Message maxDownloadMessage = new Message();
		maxDownloadMessage.arg1 = size;
		maxDownloadMessage.what = GeoQuestProgressHandler.MSG_TELL_MAX;
		progressHandler.sendMessage(maxDownloadMessage);
	
		SAXReader xmlReader = new SAXReader();
		ElementHandler downloadHandler = new ElementHandler() {
	
			public void onStart(ElementPath arg0) {
			}
	
			public void onEnd(ElementPath arg0) {
				progressHandler
						.sendEmptyMessage(GeoQuestProgressHandler.MSG_PROGRESS);
			}
		};
		xmlReader.setDefaultHandler(downloadHandler);
	
		Document doc = null;
		try {
			doc = xmlReader.read(url);
		} catch (DocumentException e) {
			Log.e(GeoQuestApp.class.getSimpleName(), "DocumentException: "
					+ e.getMessage());
			progressHandler.handleError(R.string.error_fetching_remote_repo_list_document);
			return null;
		}
		progressHandler.sendEmptyMessage(GeoQuestProgressHandler.MSG_FINISHED);
	
		progressHandler
				.sendEmptyMessage(GeoQuestProgressHandler.MSG_RESET_PROGRESS);
		return doc;
	}

}
