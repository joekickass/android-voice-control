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
	/**
	 * The result from the executed VoiceCommand
	 * 
	 * @param vc the VoiceCommand that was executed
	 */
	public void onResult(VoiceCommand vc);
}
