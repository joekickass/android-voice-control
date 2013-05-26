package se.joekickass.marvinapp.repository.service.pi;

import se.joekickass.marvinapp.vc.commands.VoiceCommand;

public class TurnLightsOnCommand implements VoiceCommand {
	
	private String serviceId;
	
	public TurnLightsOnCommand(String id) {
		serviceId = id;
	}

	@Override
	public String getLowerCaseIdentifier() {
		return "lights on";
	}

	@Override
	public int getType() {
		return TYPE_COMMAND;
	}

	@Override
	public String getResponse() {
		return "Turning lights on";
	}

	@Override
	public void execute() {
		System.out.println("!!! Raspberry Pi - turning lights on !!!");
	}

	@Override
	public String getServiceId() {
		return serviceId;
	}
}
