package se.joekickass.marvinapp.repository.service.pi;

import se.joekickass.marvinapp.repository.service.CommandService;
import se.joekickass.marvinapp.vc.commands.AbstractVoiceCommand;
import se.joekickass.marvinapp.vc.commands.VoiceCommand;

public class XbmcOpenCommand extends AbstractVoiceCommand implements VoiceCommand {

	public XbmcOpenCommand(CommandService service, String methodId) {
		super(service, methodId);
	}

	@Override
	public String getLowerCaseIdentifier() {
		return "start movie";
	}

	@Override
	public int getType() {
		return TYPE_COMMAND;
	}

	@Override
	public String getResponse() {
		return "Command received";
	}
}
