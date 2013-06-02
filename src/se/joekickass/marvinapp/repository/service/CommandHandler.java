package se.joekickass.marvinapp.repository.service;

import se.joekickass.marvinapp.vc.commands.VoiceCommand;

/**
 * The CommandHandler is responsible for executing the VoiceCommand
 * on its CommandService
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

	/**
	 * Execute a VoiceCommand on the underlying CommandService
	 * 
	 * @param vc the VoiceCommand to execute
	 */
	public void handle(VoiceCommand vc) {
		this.vc = vc;
		vc.getService().checkAvailability(this, vc.getMethodId());
	}

	@Override
	public void onServiceAvailable(boolean available) {
		if (available) {
			vc.getService().executeMethod(this, vc.getMethodId());
		} else {
			// Do nothing
		}
	}

	@Override
	public void onResult(String id) {
		callback.onResult(vc);
	}
}
