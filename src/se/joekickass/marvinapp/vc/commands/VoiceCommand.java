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
	
	public String getLowerCaseIdentifier();
	public int getType();
	public String getResponse();
	public CommandService getService();
	public String getMethodId();
}
