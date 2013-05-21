package se.joekickass.marvinapp;

import java.util.List;

import root.gast.speech.SpeechRecognizingAndSpeakingActivity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WelcomeActivity extends SpeechRecognizingAndSpeakingActivity {

	private static final String TAG = "WelcomeActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		Button btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				recognizeDirectly(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}

	@Override
	protected void speechNotAvailable() {
		Log.d(TAG, "Speech not available");
	}

	@Override
	protected void directSpeechNotAvailable() {
		Log.d(TAG, "directSpeech not available");
	}

	@Override
	protected void languageCheckResult(String languageToUse) {
		Log.d(TAG, "language check result: " + languageToUse);
	}

	@Override
	protected void receiveWhatWasHeard(List<String> heard, float[] confidenceScores) {
		if (heard.contains("marvin")) {
			Log.d(TAG, "Marvin found!");
			getTts().speak("Yes?", TextToSpeech.QUEUE_FLUSH, null);
		}
	}

	@Override
	protected void recognitionFailure(int errorCode) {
		Log.d(TAG, "Recognition failure: " + errorCode);		
	}
}
