package edu.bonn.mobilegaming.geoquest.mission;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import com.qeevee.gq.xml.XMLUtilities;

import edu.bonn.mobilegaming.geoquest.GeoQuestApp;
import edu.bonn.mobilegaming.geoquest.R;
import edu.bonn.mobilegaming.geoquest.ui.MediaController;

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
	videoView.setMediaController(mc);
	videoView.requestFocus();
	videoView.setVisibility(View.VISIBLE);
	videoView.start();
    }

    public void onBlockingStateUpdated(boolean blocking) {
	// TODO Auto-generated method stub

    }

}