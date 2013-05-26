package se.joekickass.marvinapp.repository.service.local;

import se.joekickass.marvinapp.vc.commands.VoiceCommand;

public class HelloMarvinCommand implements VoiceCommand {
	
	private String serviceId;
	
	public HelloMarvinCommand(String id) {
		serviceId = id;
	}

	@Override
	public String getLowerCaseIdentifier() {
		return "marvin";
	}

	@Override
	public int getType() {
		return TYPE_HELLO;
	}
	
	@Override
	public String getResponse() {
		return "yes?";
	}

	@Override
	public void execute() {
		// Do nothing
	}

	@Override
	public String getServiceId() {
		return serviceId;
	}
}
