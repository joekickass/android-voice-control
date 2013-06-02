package se.joekickass.marvinapp.vc.commands;

import se.joekickass.marvinapp.repository.service.CommandService;

/**
 * A VoiceCommand is used to map spoken word(s) to a call to a service. It 
 * contains both information on what string the command expects the user
 * to say, as well as what method to invoke on the service when that string
 * is identified.
 * 
 * @author otaino-2
 *
 */
public interface VoiceCommand {
	
	public static final int TYPE_HELLO = 10;
	public static final int TYPE_COMMAND = 11;
	
	/**
	 * Returns the string used by the voice recognizer to identify the command
	 * @return the command identifiery in lowercase 
	 */
	public String getLowerCaseIdentifier();
	/**
	 * The type of command. Can either be HELLO or COMMAND
	 * @return the type of the command
	 */
	public int getType();
	/**
	 * Get the string used by the TTS engine used to announce the result of the
	 * command
	 * @return the response string
	 */
	public String getResponse();
	/**
	 * Get the associated service that this command executes on
	 * @return the associated service
	 */
	public CommandService getService();
	/**
	 * The string identifying a particular method on the service
	 * @return the method id
	 */
	public String getMethodId();
}
