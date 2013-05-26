package se.joekickass.marvinapp.vc.commands;

import se.joekickass.marvinapp.repository.service.CommandService;

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
