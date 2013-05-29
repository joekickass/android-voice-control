package se.joekickass.marvinapp.repository.service;

import se.joekickass.marvinapp.vc.commands.VoiceCommand;

/**
 * After a command has been executed, the result is returned
 * through the CommandHandlerCallback interface
 * 
 * @author otaino-2
 *
 */
public interface CommandHandlerCallback {
	public void onResult(VoiceCommand vc);
}
