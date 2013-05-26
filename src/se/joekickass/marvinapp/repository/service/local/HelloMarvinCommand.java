package se.joekickass.marvinapp.repository.service.local;

import se.joekickass.marvinapp.repository.service.CommandService;
import se.joekickass.marvinapp.vc.commands.AbstractVoiceCommand;

public class HelloMarvinCommand extends AbstractVoiceCommand {
	
	public HelloMarvinCommand(CommandService service, String methodId) {
		super(service, methodId);
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
}
