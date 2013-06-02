package se.joekickass.marvinapp.repository.service;

import java.util.List;

import se.joekickass.marvinapp.vc.commands.VoiceCommand;

/**
 * Represents a service.
 * 
 * Services are most likely network services and might not always be available.
 * Each service supports different types of VoiceCommands.
 * 
 * @author otaino-2
 * 
 */
public interface CommandService {
	
	/**
	 * Get all commands supported by the service
	 * 
	 * @return a list of all available VoiceCommands
	 */
	public List<VoiceCommand> getAvailableCommands();
	
	/**
	 * Check if the service is up and running
	 * 
	 * @param callback the callback used for returning the result
	 * @param id the method id
	 */
	public void checkAvailability(CommandServiceCallback callback, String id);
	
	/**
	 * Execute a method on the service
	 * 
	 * @param callback the callback used for returning the result
	 * @param id the method id
	 */
	public void executeMethod(CommandServiceCallback callback, String id);
}
