package se.joekickass.marvinapp;

import java.util.List;

import se.joekickass.marvinapp.repository.ServiceRepository;
import se.joekickass.marvinapp.repository.service.CommandHandler;
import se.joekickass.marvinapp.repository.service.CommandHandlerCallback;
import se.joekickass.marvinapp.state.CommandState;
import se.joekickass.marvinapp.vc.MarvinVoiceControlCallback;
import se.joekickass.marvinapp.vc.MarvinVoiceControlFacade;
import se.joekickass.marvinapp.vc.commands.VoiceCommand;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class WelcomeActivity extends Activity implements MarvinVoiceControlCallback, CommandHandlerCallback {

	private MarvinVoiceControlFacade mvc;
	private CommandState state;
	private ServiceRepository serviceRepository;
	private CommandHandler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		serviceRepository = new ServiceRepository();
		state = new CommandState();
		
		handler = new CommandHandler(this);
		
		mvc = MarvinVoiceControlFacade.createInstance(this, new Handler(), this);
		registerCommandsWithVoiceControl(serviceRepository.generateCommands());
		
		mvc.startListen();
	}
	
    private void registerCommandsWithVoiceControl(List<VoiceCommand> commands) {
    	for (VoiceCommand vc : commands) {
    		mvc.registerVoiceCommand(vc);
    	}
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
		
		// Ignore command if not expected
		if (!state.updateState(vc)) {
			return;
		}
		
		// Handle command
		handler.handle(vc);
	}

	@Override
	public void onResult(VoiceCommand vc) {
		String response = vc.getResponse();
    	mvc.speak(response);
	}
}
