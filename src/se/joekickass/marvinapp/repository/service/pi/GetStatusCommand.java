package se.joekickass.marvinapp.repository.service.pi;

import se.joekickass.marvinapp.repository.service.CommandService;
import se.joekickass.marvinapp.vc.commands.AbstractVoiceCommand;

/**
 * Gets the status from the Raspberry Pi service.
 * 
 * @author otaino-2
 *
 */
public class GetStatusCommand extends AbstractVoiceCommand {
	
	public GetStatusCommand(CommandService service, String methodId) {
		super(service, methodId);
	}

	@Override
	public String getLowerCaseIdentifier() {
		return "get status";
	}

	@Override
	public int getType() {
		return TYPE_COMMAND;
	}

	@Override
	public String getResponse() {
		return "Status received";
	}
}
