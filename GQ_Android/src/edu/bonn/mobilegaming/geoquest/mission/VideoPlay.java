package edu.bonn.mobilegaming.geoquest.mission;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.qeevee.gq.xml.XMLUtilities;

import edu.bonn.mobilegaming.geoquest.GeoQuestApp;
import edu.bonn.mobilegaming.geoquest.Globals;
import edu.bonn.mobilegaming.geoquest.R;

public class VideoPlay extends InteractiveMission {

    private static final String TAG = "VideoPlay";

    CharSequence filePath = null;
    CharSequence uriString = null;
    Uri uri = null;

    private VideoView videoView;

    // private CharSequence mURL;

    @Override
    public void onCreate(Bundle bundle) {
	super.onCreate(bundle);
	this.setContentView(R.layout.videoplay);
	videoView = (VideoView) this.findViewById(R.id.videoplay_videoview);
	filePath = getMissionAttribute("file",
				       XMLUtilities.OPTIONAL_ATTRIBUTE);
	if (filePath == null) {
	    // attribute FILE NOT given => try to use URL:
	    try {
		uriString = getMissionAttribute("url",
						XMLUtilities.NECESSARY_ATTRIBUTE);
	    } catch (IllegalArgumentException iae) {
		Log.e(TAG,
		      "Both attributes file and url missing in VideoPlay Mission "
			      + mission.id + ". At least one must be given.",
		      iae);
		finish();
	    }
	    uri = Uri.parse(uriString.toString());
	} else {
	    // attribute FILE given:
	    uri = Uri.fromFile(GeoQuestApp.getGameRessourceFile(filePath
		    .toString()));
	}
	videoView.setVideoURI(uri);
	MediaController mc = new MediaController(this);
	mc.setPrevNextListeners(finishButtonClickListener,
				null);
	videoView.setMediaController(mc);
	videoView.requestFocus();
	videoView.setVisibility(View.VISIBLE);
	videoView
		.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

		    public void onCompletion(MediaPlayer mp) {
			showExitAlertDialog();
		    }

		});
	videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

	    public boolean onError(MediaPlayer mp,
				   int what,
				   int extra) {
		Log.e(TAG,
		      "Problem occurred when trying to play the video: "
			      + uri.toString());
		showErrorAlertDialog();
		return true;
	    }
	});
	videoView.start();
    }

    private void showErrorAlertDialog() {
	AlertDialog exitDialog = new AlertDialog.Builder(VideoPlay.this)
		.setTitle(R.string.error_dialog_title)
		.setMessage(R.string.videoplay_errordialog_message)
		.setNegativeButton(R.string.button_text_proceed,
				   new DialogInterface.OnClickListener() {
				       public void
					       onClick(DialogInterface dialog,
						       int which) {
					   finish(Globals.STATUS_SUCCESS);
				       }
				   }).show();
	exitDialog
		.getButton(AlertDialog.BUTTON_NEGATIVE)
		.setCompoundDrawablesWithIntrinsicBounds(null,
							 null,
							 getResources()
								 .getDrawable(R.drawable.icon_leave),
							 null);
    }

    private void showExitAlertDialog() {
	AlertDialog exitDialog = new AlertDialog.Builder(VideoPlay.this)
		.setTitle(R.string.videoplay_finishdialog_title)
		.setMessage(R.string.videoplay_finishdialog_message)
		.setPositiveButton(R.string.videoplay_finishdialog_keepwatching,
				   null)
		.setNegativeButton(R.string.videoplay_finishdialog_leave,
				   new DialogInterface.OnClickListener() {
				       public void
					       onClick(DialogInterface dialog,
						       int which) {
					   finish(Globals.STATUS_SUCCESS);
				       }
				   }).show();
	exitDialog
		.getButton(AlertDialog.BUTTON_POSITIVE)
		.setCompoundDrawablesWithIntrinsicBounds(getResources()
								 .getDrawable(R.drawable.icon_again),
							 null,
							 null,
							 null);
	exitDialog
		.getButton(AlertDialog.BUTTON_NEGATIVE)
		.setCompoundDrawablesWithIntrinsicBounds(null,
							 null,
							 getResources()
								 .getDrawable(R.drawable.icon_leave),
							 null);
    }

    public void onBlockingStateUpdated(boolean blocking) {
	// TODO Auto-generated method stub

    }

    private View.OnClickListener finishButtonClickListener = new View.OnClickListener() {

	public void onClick(View v) {
	    showExitAlertDialog();
	}
    };

}