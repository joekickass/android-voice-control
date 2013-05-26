package se.joekickass.marvinapp.repository.service;

import se.joekickass.marvinapp.vc.commands.VoiceCommand;

/**
 * The CommandHandler is responsible for mapping VoiceCommand to
 * Service
 * 
 * @author otaino-2
 *
 */
public class CommandHandler implements CommandServiceCallback {
	
	private VoiceCommand vc;
	
	private CommandHandlerCallback callback;

	public CommandHandler(CommandHandlerCallback callback) {
		this.callback = callback;
	}

	public void handle(VoiceCommand vc) {
		this.vc = vc;
		vc.getService().checkAvailability(this);
	}

	@Override
	public void onServiceAvailable(boolean available) {
		if (available) {
			vc.getService().executeMethod(this, vc.getMethodId());
		} else {
			// TODO: Implement me!
		}
	}

	@Override
	public void onResult(String id) {
		callback.onResult(vc);
	}
}
