package se.joekickass.marvinapp.repository.service.pi;

import se.joekickass.marvinapp.repository.service.CommandService;
import se.joekickass.marvinapp.vc.commands.AbstractVoiceCommand;

/**
 * Play current video in XBMC
 * 
 * @author otaino-2
 *
 */
public class XbmcPlayCommand extends AbstractVoiceCommand {
	
	public XbmcPlayCommand(CommandService service, String methodId) {
		super(service, methodId);
	}

	@Override
	public String getLowerCaseIdentifier() {
		return "play";
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
