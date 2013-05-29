package se.joekickass.marvinapp.vc;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import se.joekickass.marvinapp.vc.commands.VoiceCommand;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

/**
 * MarvinVoiceControlFacade abstracts away the complexity of voice recognition and
 * text to speech from the rest of the application.
 * 
 * After instantiation, it is possible to register VoiceCommands with the
 * MarvinVoiceControlFacade. The commands are used to identify spoken words
 * as commands. When a command is identified, the application is notified through
 * the MarvinVoiceControlCallback.
 * 
 * @author otaino-2
 *
 */
public class MarvinVoiceControlFacade implements RecognitionListener, OnInitListener{

	private static final String TAG = "MarvinVoiceControlFacade";

	private static final long DELAY = 200;
	
	// Note: IETF BCP 47 language tag
	private static final String SUPPORTED_LANG = "en-GB";
	
	private static final Locale SUPPORTED_LOCALE = Locale.UK;

    private SpeechRecognizer recognizer;
    
    private TextToSpeech tts;
	
	private static MarvinVoiceControlFacade instance;
	
	private MarvinVoiceControlCallback callback;
	
	private Handler handler;

	private HashMap<String, VoiceCommand> commands;
	
	public static MarvinVoiceControlFacade createInstance(Context context, Handler handler, MarvinVoiceControlCallback callback) {
		if (instance == null) {
	        boolean direct = SpeechRecognizer.isRecognitionAvailable(context);
	        if (!direct) {
	        	Log.e(TAG, "Direct speech not available, quitting...");
	            throw new VoiceControlNotSupportedException();
	        }
			instance = new MarvinVoiceControlFacade(context, handler, callback);
		}
		return instance;
	}

	private MarvinVoiceControlFacade(Context context, Handler handler, MarvinVoiceControlCallback callback) {
		this.commands = new HashMap<String, VoiceCommand>();
		this.tts = new TextToSpeech(context, this);
        this.recognizer = SpeechRecognizer.createSpeechRecognizer(context);
        this.recognizer.setRecognitionListener(this);
        this.handler = handler;
        this.callback = callback;
	}

	public void close() {
        if (recognizer != null) {
            recognizer.stopListening();
            recognizer.cancel();
            recognizer.destroy();
        }
        if (tts != null) {
            tts.shutdown();
        }
    }
	
	public void startListen() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, SUPPORTED_LANG);
    	intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "se.joekickass.marvin");
    	intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    	intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        recognizer.startListening(intent);
	}
	
	public void registerVoiceCommand(VoiceCommand vc) {
		commands.put(vc.getLowerCaseIdentifier(), vc);
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
            
        	// Get results
        	List<String> heard = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            float[] scores = results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);
            debugResults(heard, scores);
            
            // Check if a suitable command is registered
            VoiceCommand vc = tryGetCommand(heard);
            
            // Handle command
            if (vc != null) {
            	callback.onCommandReceived(vc);
            	return;
            }
		}
        // Make sure we return to listening if no command was identified
        startListenDelayed();
	}

	private VoiceCommand tryGetCommand(List<String> heard) {
		for (String sound : heard) {
			VoiceCommand vc = commands.get(sound.toLowerCase(SUPPORTED_LOCALE));
			if (vc != null) {
				return vc;
			}
		}
		return null;
	}
	
	public void speak(String sound) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,"stringId");
		int res = tts.speak(sound, TextToSpeech.QUEUE_FLUSH, params);
		Log.d(TAG, "Spoken sound:" + sound + ", result: " + res);
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
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			setTtsLocale(SUPPORTED_LOCALE);
            setTtsListener();
        } else {
            Log.e(TAG, "error creating text to speech");
            throw new VoiceControlNotSupportedException();
        }
	}

	private void setTtsLocale(Locale supportedLocale) {
		switch (tts.isLanguageAvailable(supportedLocale)) {
            case TextToSpeech.LANG_AVAILABLE:
            case TextToSpeech.LANG_COUNTRY_AVAILABLE:
            case TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE:
                tts.setLanguage(supportedLocale);
                Log.d(TAG, "Text to speech successfully using locale " + supportedLocale.getDisplayName());
                break;
            default:
                Log.e(TAG, "Text to speech is missing language support for locale " + supportedLocale.getDisplayName());
                throw new VoiceControlNotSupportedException();
        }		
	}
	
	private void setTtsListener() {
    	int listenerResult = tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onDone(String utteranceId)
                {
                    MarvinVoiceControlFacade.this.onSpeekDone(utteranceId);
                }

                @Override
                public void onError(String utteranceId)
                {
                	MarvinVoiceControlFacade.this.onSpeekError(utteranceId);
                }

                @Override
                public void onStart(String utteranceId)
                {
                	MarvinVoiceControlFacade.this.onSpeekStart(utteranceId);
                }
            });

		if (listenerResult != TextToSpeech.SUCCESS) {
			Log.e(TAG, "failed to add utterance progress listener");
			throw new VoiceControlNotSupportedException();
		}
    }

}	
