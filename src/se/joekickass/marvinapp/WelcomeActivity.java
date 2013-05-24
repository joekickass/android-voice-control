package se.joekickass.marvinapp;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import root.gast.speech.SpeechRecognitionUtil;
import root.gast.speech.tts.TextToSpeechInitializer;
import root.gast.speech.tts.TextToSpeechStartupListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.Menu;

public class WelcomeActivity extends Activity implements RecognitionListener, TextToSpeechStartupListener {

	private static final String TAG = "WelcomeActivity";

	private static final long DELAY = 100;

    private SpeechRecognizer recognizer;
    
    private TextToSpeechInitializer ttsInit;
    
    private TextToSpeech tts;
	
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
        boolean recognizerIntent = SpeechRecognitionUtil.isSpeechAvailable(this);
        if (!recognizerIntent) {
        	Log.e(TAG, "Speech not available, quitting...");
            finish();
        }
        
        boolean direct = SpeechRecognizer.isRecognitionAvailable(this);
        if (!direct) {
        	Log.e(TAG, "Direct speech not available, quitting...");
        	finish();
        }
        
        ttsInit = new TextToSpeechInitializer(this, Locale.getDefault(), this);
        
		startListen();
	}
	
    private SpeechRecognizer getSpeechRecognizer() {
        if (recognizer == null) {
            recognizer = SpeechRecognizer.createSpeechRecognizer(this);
            recognizer.setRecognitionListener(this);
        }
        return recognizer;
    }
    
    @Override
    protected void onDestroy() {
    	SpeechRecognizer recognizer = getSpeechRecognizer();
        if (recognizer != null) {
            recognizer.stopListening();
            recognizer.cancel();
            recognizer.destroy();
        }
        if (tts != null) {
            tts.shutdown();
        }
    	super.onDestroy();
    }
	
    private void setTtsListener() {
    	int listenerResult = tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onDone(String utteranceId)
                {
                    WelcomeActivity.this.onSpeekDone(utteranceId);
                }

                @Override
                public void onError(String utteranceId)
                {
                	WelcomeActivity.this.onSpeekError(utteranceId);
                }

                @Override
                public void onStart(String utteranceId)
                {
                	WelcomeActivity.this.onSpeekStart(utteranceId);
                }
            });

		if (listenerResult != TextToSpeech.SUCCESS) {
			Log.e(TAG, "failed to add utterance progress listener");
			WelcomeActivity.this.finish();
		}
    }

	private void startListen() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
    	intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "se.joekickass.marvin");
    	intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    	intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        getSpeechRecognizer().startListening(intent);
	}

	private void onSpeekStart(String utteranceId) {
		Log.d(TAG, "Speaking Start!");
    }
	
	private void onSpeekDone(String utteranceId) {
		Log.d(TAG, "Speaking done!");		
		startListenDelayed();
	}

	private void onSpeekError(String utteranceId) {
		Log.d(TAG, "Speaking Error!");
		startListenDelayed();
    }

	private void startListenDelayed() {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				startListen();				
			}
		}, DELAY);
	}
	
	private void handleListenResult(Bundle results) {
        if ((results != null) && results.containsKey(SpeechRecognizer.RESULTS_RECOGNITION)) {
            List<String> heard = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            float[] scores = results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);
            debugResults(heard, scores);
    		if (heard.contains("Marvin") || heard.contains("marvin")) {
    			Log.d(TAG, "Marvin found!");
    			HashMap<String, String> params = new HashMap<String, String>();
    			params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,"stringId");
    			tts.speak("Yes?", TextToSpeech.QUEUE_FLUSH, params);
    		} else {
    			startListenDelayed();
    		}
		} else {
			startListenDelayed();
		}
	}

	private void debugResults(List<String> heard, float[] scores) {
		if (scores == null) {
			for (String s : heard) {
				Log.d(TAG, "Heard: " + s);
			}
		} else {
			for (int i = 0; i < scores.length; i++) {
				Log.d(TAG, "Heard: " + heard.get(i) + ", score: " + scores[i]);
			}
		}
	}

	@Override
	public void onBeginningOfSpeech() {
		Log.d(TAG, "Started to listen...");
	}

	@Override
	public void onBufferReceived(byte[] buffer) {
		Log.d(TAG, "onBufferReceived");
	}

	@Override
	public void onEndOfSpeech() {
		Log.d(TAG, "Finished listening!");
	}

	@Override
	public void onError(int error) {
		Log.d(TAG, "Error when listening: " + error);
		if ((error == SpeechRecognizer.ERROR_NO_MATCH) || (error == SpeechRecognizer.ERROR_SPEECH_TIMEOUT)) {
			startListenDelayed();
		}
	}

	@Override
	public void onEvent(int eventType, Bundle params) {
		Log.d(TAG, "onEvent");
	}

	@Override
	public void onPartialResults(Bundle partialResults) {
		handleListenResult(partialResults);
	}

	@Override
	public void onReadyForSpeech(Bundle params) {
		Log.d(TAG, "Ready to start listening...");
	}

	@Override
	public void onResults(Bundle results) {
		handleListenResult(results);
	}

	@Override
	public void onRmsChanged(float rmsdB) {
		// Do nothing
	}

	@Override
	public void onSuccessfulInit(TextToSpeech tts) {
        Log.d(TAG, "Successful initialized TextToSpeech");
        this.tts = tts;
        setTtsListener();
	}

	@Override
	public void onRequireLanguageData() {
		Log.d(TAG, "Language data required for TextToSpeech to work...");
		ttsInit.installLanguageData();
	}

	@Override
	public void onWaitingForLanguageData() {
		Log.d(TAG, "Waiting for language data...");
	}

	@Override
	public void onFailedToInit() {
		Log.e(TAG, "Failed to initialize TextToSpeech, quitting...");
		finish();
	}
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}
}
