package edu.bonn.mobilegaming.geoquest.mission;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import edu.bonn.mobilegaming.geoquest.Globals;
import edu.bonn.mobilegaming.geoquest.R;

public class AudioRecord extends MissionActivity {

	private static final String TAG = "AudioRecord";

	private static final String LOG_TAG = "AudioRecordTest";
	private static String mFileName = null;

	private Button mRecordButton = null;
	private MediaRecorder mRecorder = null;

	private Button mPlayButton = null;
	private MediaPlayer mPlayer = null;

	private Button uploadButton = null;

	private CharSequence uploadURL;
	private boolean recorded = false;
	private boolean uploaded = false;
	private boolean mStartRecording = true;
	boolean mStartPlaying = true;

	private TextView taskTextView;

	private void onRecord(boolean start) {
		if (start) {
			startRecording();
		} else {
			stopRecording();
		}
	}

	private void onPlay(boolean start) {
		if (start) {
			startPlaying();
		} else {
			stopPlaying();
		}
	}

	private void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(mFileName);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}
	}

	private void stopPlaying() {
		mPlayer.release();
		mPlayer = null;
	}

	private void startRecording() {
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setOutputFile(mFileName);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		try {
			mRecorder.prepare();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}

		mRecorder.start();
	}

	private void stopRecording() {
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
		recorded = true;
	}

	public AudioRecord() {
		mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
		mFileName += "/audiorecordtest.3gp";
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.audiorecord);

		// init Record Button at bottom:
		mRecordButton = (Button) findViewById(R.id.audioRecordRecordButton);
		mRecordButton.setText("Start recording");
		mRecordButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				onRecord(mStartRecording);
				if (mStartRecording) {
					mRecordButton.setText("Stop recording");
					uploadButton.setEnabled(false);
				} else {
					mRecordButton.setText("Start recording");
					recorded = true;
					mPlayButton.setEnabled(true);
					uploadButton.setEnabled(true);
				}
				mStartRecording = !mStartRecording;
			}
		});

		// init Play Button at bottom:
		mPlayButton = (Button) findViewById(R.id.audioRecordPlayButton);
		mPlayButton.setEnabled(false);
		mPlayButton.setText("Start playing");
		mPlayButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				onPlay(mStartPlaying);
				if (mStartPlaying) {
					mPlayButton.setText("Stop playing");
				} else {
					mPlayButton.setText("Start playing");
				}
				mStartPlaying = !mStartPlaying;
			}
		});

		// init Upload Button at bottom:
		uploadButton = (Button) findViewById(R.id.audioRecordUploadButton);
		uploadButton.setEnabled(false);
		uploadButton.setText("Upload");
		uploadButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (recorded && !uploaded) {
					upload();
				}
				if (uploaded) {
					finish(Globals.STATUS_SUCCESS);
				}
			}
		});

		// init task description text:
		taskTextView = (TextView) findViewById(R.id.audioRecordTextView);
		taskTextView.setText(getMissionAttribute("taskdescription",
				R.string.audiorecord_taskdescription_default));

		// init upload url etc.:
		uploadURL = getMissionAttribute("uploadURL",
				R.string.audioRecordUploadURLDefault);

	}

	@Override
	public void onPause() {
		super.onPause();
		if (mRecorder != null) {
			mRecorder.release();
			mRecorder = null;
		}

		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}

	public void upload() {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(uploadURL.toString());

		try {
			// Adding data:
			MultipartEntity requestEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			requestEntity.addPart("uploaded_file", new InputStreamBody(
					new ByteArrayInputStream(getBytesFromFile(mFileName)),
					"audio/3gpp", mFileName));
			requestEntity.addPart("secret", new StringBody(TAG));
			httppost.setEntity(requestEntity);

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			CharSequence result = makeReadableResponse(response);
			if (result.equals(getText(R.string.audioRecordUploadOK))) {
				uploaded = true;
				uploadButton.setText(R.string.audioRecordFinishedButton);
			}
			taskTextView.setText(result);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static byte[] getBytesFromFile(String fileName) throws IOException {
		File file = new File(fileName);
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	private CharSequence makeReadableResponse(HttpResponse response) {
		HttpEntity responseEntity = response.getEntity();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			responseEntity.writeTo(os);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String responseText = os.toString();
		if (responseText.startsWith("OK")) {
			return getText(R.string.audioRecordUploadOK);
		} else {
			String errno = responseText.substring(0, responseText.indexOf(':'));
			return getText(R.string.audioRecordUploadERROR) + "  Error: "
					+ errno;
		}
	}

	public void onBlockingStateUpdated(boolean blocking) {
		// TODO Auto-generated method stub
		
	}

}