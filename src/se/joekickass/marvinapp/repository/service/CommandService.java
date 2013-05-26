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
	public List<VoiceCommand> getAvailableCommands();
	public void checkAvailability(CommandServiceCallback callback);
	public void executeMethod(CommandServiceCallback callback, String id);
}
