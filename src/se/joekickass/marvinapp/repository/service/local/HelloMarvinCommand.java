package se.joekickass.marvinapp.repository.service.local;

import se.joekickass.marvinapp.repository.service.CommandService;
import se.joekickass.marvinapp.vc.commands.AbstractVoiceCommand;

/**
 * Local command used by the application for responding to the communication
 * initiation. The user says "Marvin" to initiate a new voice command, and 
 * Marvin responds by saying "Yes" when ready to receive the command.
 * 
 * @author otaino-2
 *
 */
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
