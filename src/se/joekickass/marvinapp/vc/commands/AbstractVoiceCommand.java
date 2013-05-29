package se.joekickass.marvinapp.vc.commands;

import se.joekickass.marvinapp.repository.service.CommandService;

/**
 * Abstract VoiceCommand that keeps track of the CommandService 
 * connected to the command as well as a string identifying the
 * method to invoke on the service (if there are more than one
 * VoiceCommand supported by the service).
 * 
 * @author otaino-2
 *
 */
public abstract class AbstractVoiceCommand implements VoiceCommand {

	private CommandService service;
	private String methodId;

	public AbstractVoiceCommand(CommandService service, String methodId) {
		this.service = service;
		this.methodId = methodId;
	}

	@Override
	public CommandService getService() {
		return service;
	}

	public String getMethodId() {
		return methodId;
	}
}
