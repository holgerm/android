package com.qeevee.gq.items;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewFlipper;
import edu.bonn.mobilegaming.geoquest.GeoQuestApp;
import edu.bonn.mobilegaming.geoquest.R;
import edu.bonn.mobilegaming.geoquest.mission.MissionActivity;

/**
 * This class is responsible for creating the view flipper initially, binding
 * the Buttons logic and enabling together, and setting the right animations
 * when flipping.
 * 
 * @author muegge
 * 
 */
public class ItemFlipperManager {

	private static final String TAG = "ItemFlipperManager";
	private ArrayList<Item> items;
	private ViewFlipper flipper;
	private Button backButton, forwardButton, audioButton, proceedButton;
	private MediaPlayer mPlayer;
	/**
	 * Designates the most forward item that has been shown yet.
	 */
	private int currentIndex = 0;

	public ItemFlipperManager(final ArrayList<Item> items,
			MissionActivity activity, Button proceedButton) {
		this.proceedButton = proceedButton;
		this.items = items;
		this.flipper = (ViewFlipper) activity
				.findViewById(R.id.item_flipper_id);

		for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();) {
			Item item = (Item) iterator.next();
			flipper.addView(item.getView(activity));
		}

		makeButtons(activity);
		updateButtonEnabling();

	}

	private void makeButtons(Activity activity) {
		this.backButton = (Button) activity
				.findViewById(R.id.back_one_item_button_id);
		backButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				setAnimationToBackward();
				flipper.showPrevious();
				updateButtonEnabling();
				updateMediaPlayer();
			}

		});

		this.forwardButton = (Button) activity
				.findViewById(R.id.forward_one_item_button_id);
		forwardButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				setAnimationToForward();
				flipper.showNext();
				if (currentIndex < flipper.getDisplayedChild())
					currentIndex = flipper.getDisplayedChild();
				updateButtonEnabling();
				updateMediaPlayer();
			}

		});

		this.audioButton = (Button) activity.findViewById(R.id.audio_button_id);
		audioButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mPlayer != null) {
					if (mPlayer.isPlaying()) {
						mPlayer.pause();
					} else {
						mPlayer.start();
					}
				} else {
					updateMediaPlayer();
				}
			}
		});
	}

	/*
	 * Stops and releases the MediaPlayer. Plays a sound if the new item has a
	 * sound file.
	 */
	private void updateMediaPlayer() {
		String path = null;
		Uri soundUri = null;

		releaseMediaPlayer();

		path = items.get(flipper.getDisplayedChild()).getAudioPath();
		if (path != null) {
			try {
				soundUri = Uri.parse(GeoQuestApp.getGameRessourceFile(path)
						.getAbsolutePath());
			} catch (Exception e) {
				Log.d(TAG, "Path to sound file couldn't be parsed: " + path);
			}
			mPlayer = MediaPlayer.create(GeoQuestApp.getContext(), soundUri);
			mPlayer.start();
		}
	}

	public void releaseMediaPlayer() {
		if (mPlayer != null) {
			if (mPlayer.isPlaying()) {
				mPlayer.stop();
			}
			mPlayer.reset();
			mPlayer.release();
			mPlayer = null;
		}
	}

	private void updateButtonEnabling() {
		backButton.setEnabled(flipper.getDisplayedChild() > 0);
		forwardButton.setEnabled(flipper.getDisplayedChild() < flipper
				.getChildCount() - 1);
		audioButton.setEnabled(items.get(flipper.getDisplayedChild())
				.hasAudio());
		proceedButton.setEnabled(flipper.getDisplayedChild() == flipper
				.getChildCount() - 1);
	}

	private void setAnimationToBackward() {
		flipper.setInAnimation(AnimationUtils.loadAnimation(
				flipper.getContext(), R.anim.slide_in_left));
		flipper.setOutAnimation(AnimationUtils.loadAnimation(
				flipper.getContext(), R.anim.slide_out_right));
	}

	private void setAnimationToForward() {
		flipper.setInAnimation(AnimationUtils.loadAnimation(
				flipper.getContext(), R.anim.slide_in_right));
		flipper.setOutAnimation(AnimationUtils.loadAnimation(
				flipper.getContext(), R.anim.slide_out_left));
	}

}
