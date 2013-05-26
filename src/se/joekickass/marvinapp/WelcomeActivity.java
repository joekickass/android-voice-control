package se.joekickass.marvinapp;

import se.joekickass.marvinapp.vc.MarvinVoiceControlCallback;
import se.joekickass.marvinapp.vc.MarvinVoiceControlFacade;
import se.joekickass.marvinapp.vc.commands.HelloMarvinCommand;
import se.joekickass.marvinapp.vc.commands.VoiceCommand;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class WelcomeActivity extends Activity implements MarvinVoiceControlCallback {

	private MarvinVoiceControlFacade mvc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		mvc = MarvinVoiceControlFacade.createInstance(this, new Handler(), this);
		mvc.registerVoiceCommand(new HelloMarvinCommand());
		mvc.startListen();
	}
	
    @Override
    protected void onDestroy() {
    	if (mvc != null) {
    		mvc.close();
    		mvc = null;
    	}
    	super.onDestroy();
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}

	@Override
	public void onCommandReceived(VoiceCommand vc) {
		String response = vc.getResponse();
    	mvc.speak(response);		
	}
}
