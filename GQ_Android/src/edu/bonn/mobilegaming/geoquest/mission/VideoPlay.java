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

    private VideoView videoView;

    // private CharSequence mURL;

    @Override
    public void onCreate(Bundle bundle) {
	super.onCreate(bundle);
	this.setContentView(R.layout.videoplay);
	videoView = (VideoView) this.findViewById(R.id.videoplay_videoview);
	filePath = getMissionAttribute("file",
				       XMLUtilities.NECESSARY_ATTRIBUTE);
	if (filePath == null) {
	    Log.e(TAG,
		  "Attribute file missing in VideoPlay Mission " + mission.id);
	    finish();
	}
	Uri uri = Uri.fromFile(GeoQuestApp.getGameRessourceFile(filePath
		.toString()));
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
	videoView.start();
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