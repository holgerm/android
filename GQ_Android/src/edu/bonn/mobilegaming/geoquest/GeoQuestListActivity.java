package edu.bonn.mobilegaming.geoquest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edu.bonn.mobilegaming.geoquest.gameaccess.GameItem;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

public abstract class GeoQuestListActivity extends ListActivity {

	/**
	 * Declare Menu ID Constants
	 */
	static final protected int QUIT_MENU_ID = Menu.FIRST;
	static final protected int END_GAME_MENU_ID = Menu.FIRST + 1;
	static final protected int PREFS_MENU_ID = Menu.FIRST + 2;
	protected static final int MENU_ID_OFFSET = Menu.FIRST + 3;

	/**
	 * This map is used by all GeoQuest-Activities to enable or disable
	 * the default menu items. This map is initialized with all items
	 * enabled by default. This map just handles the default items. It is
	 * not possible to add new items outside GeoQuestActivity.
	 * 
	 * @see 
	 */
	private Map<Integer, Boolean> menuItemStatusMap = new HashMap<Integer, Boolean>();
	
	private ProgressDialog downloadAndStartGameDialog;
	private ProgressDialog startLocalGameDialog;
	
	public void setMenuItemStatus(int menuItemId, boolean enabled) {
		if (menuItemStatusMap.containsKey(menuItemId)) {
			menuItemStatusMap.put(menuItemId, enabled);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((GeoQuestApp) getApplication()).addActivity(this);
		
		// set default status value (enabled) to all default menu items
		menuItemStatusMap.put(QUIT_MENU_ID, true);
		menuItemStatusMap.put(END_GAME_MENU_ID, true);
		menuItemStatusMap.put(PREFS_MENU_ID, true);
		
		// Init progress dialogs:
		downloadAndStartGameDialog = new ProgressDialog(this);
		downloadAndStartGameDialog
				.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		downloadAndStartGameDialog.setCancelable(false);

		startLocalGameDialog = new ProgressDialog(this);
		startLocalGameDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		startLocalGameDialog.setCancelable(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);		
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		Iterator<Integer> i = menuItemStatusMap.keySet().iterator();
		while (i.hasNext()) {
			int menuItem = i.next();
			if (menu.findItem(menuItem) == null && menuItemStatusMap.get(menuItem) == true) {
				/*
				 * TODO: not that sexy - better create a new MenuItem structure
				 * which incorporates ID and String value and get rid of this ugly
				 * switch-statement.
				 */
				switch (menuItem) {
				case QUIT_MENU_ID:
					menu.add(0, menuItem, 0, R.string.quitMenu);
					break;
				case END_GAME_MENU_ID:
					menu.add(0, menuItem, 0, R.string.endGameMenu);
					break;
				case PREFS_MENU_ID:
					menu.add(0, menuItem, 0, R.string.menuPrefs);
					break;
				default:
					break;
				}
			}
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case QUIT_MENU_ID:
			GeoQuestApp.getInstance().terminateApp();
			break;
		case END_GAME_MENU_ID:
			GeoQuestApp.getInstance().endGame();
			break;
		case PREFS_MENU_ID:
			Intent settingsActivity = new Intent(getBaseContext(),
					Preferences.class);
			startActivity(settingsActivity);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
//	@Override
//	protected void onResume() {
//		super.onResume();
//		GeoQuestApp.setCurrentActivity(this);
//	}

	protected void onDestroy() {
		if (isFinishing()) {
			GeoQuestApp.getInstance().removeActivity(this);
		}
		super.onDestroy();
	}
	
	/*
	 * TODO move to GameListActivity class. Also downloadAndStartGameDialog and startLocalGameDialog.
	 */
	protected void startGame(final GameItem gameItem, final String repoName) {
		final boolean gameMustBeDownloaded = gameItem.isDownloadNeeded();
		final Handler downloadGameHandler;
		final Handler startGameHandler;
		
		if (gameMustBeDownloaded) {
			downloadAndStartGameDialog.setProgress(0);
			downloadAndStartGameDialog.setMessage(GeoQuestApp.getContext()
					.getText(R.string.webupdate_downloadingGameFromServer));
			showDialog(GeoQuestApp.DIALOG_ID_DOWNLOAD_GAME);
			downloadGameHandler = new GeoQuestProgressHandler(
					downloadAndStartGameDialog,
					GeoQuestProgressHandler.FOLLOWED_BY_OTHER_PROGRESS_DIALOG);
			startGameHandler = new GeoQuestProgressHandler(
					downloadAndStartGameDialog,
					GeoQuestProgressHandler.LAST_IN_CHAIN);
		} else {
			startLocalGameDialog.setProgress(0);
			startLocalGameDialog.setMessage(GeoQuestApp.getContext().getText(
					R.string.webupdate_startingGameFromExternalStorage));
			showDialog(GeoQuestApp.DIALOG_ID_START_GAME);
			downloadGameHandler = null; // unused in this case.
			startGameHandler = new GeoQuestProgressHandler(
					startLocalGameDialog, GeoQuestProgressHandler.LAST_IN_CHAIN);
		}
		
		final String gameFileName = gameItem.getFileName();
		final String gameName = gameItem.getName();
		GeoQuestApp.singleThreadExecutor.execute(new Runnable() {

			public void run() {
				if (gameMustBeDownloaded) {
					// if selected game is only on server or newer there:
					// (re-)load it from server:
					GameLoader.loadGame(downloadGameHandler, repoName,
							gameFileName);
				}
				GameLoader.startGame(startGameHandler,
						GeoQuestApp.getGameXMLFile(repoName, gameFileName));
				GeoQuestApp.setRecentGame(repoName, gameName, gameFileName);
			}

		});
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case GeoQuestApp.DIALOG_ID_DOWNLOAD_GAME:
			return downloadAndStartGameDialog;
		case GeoQuestApp.DIALOG_ID_START_GAME:
			return startLocalGameDialog;
		default:
			return null;
		}
	}

}
