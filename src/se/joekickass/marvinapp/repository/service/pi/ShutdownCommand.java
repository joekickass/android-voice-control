package se.joekickass.marvinapp.repository.service.pi;

import se.joekickass.marvinapp.repository.service.CommandService;
import se.joekickass.marvinapp.vc.commands.AbstractVoiceCommand;

/**
 * Shuts down the Raspberry Pi
 * 
 * @author otaino-2
 *
 */
public class ShutdownCommand extends AbstractVoiceCommand {
	
	public ShutdownCommand(CommandService service, String methodId) {
		super(service, methodId);
	}

	@Override
	public String getLowerCaseIdentifier() {
		return "shutdown";
	}

	@Override
	public int getType() {
		return TYPE_COMMAND;
	}

	@Override
	public String getResponse() {
		return "Shutdown in progress";
	}
}
