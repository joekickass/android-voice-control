package se.joekickass.marvinapp.repository.service.pi;

import se.joekickass.marvinapp.repository.service.CommandService;
import se.joekickass.marvinapp.vc.commands.AbstractVoiceCommand;

/**
 * Pause current video in XBMC
 * 
 * @author otaino-2
 *
 */
public class XbmcPauseCommand extends AbstractVoiceCommand {
	
	public XbmcPauseCommand(CommandService service, String methodId) {
		super(service, methodId);
	}

	@Override
	public String getLowerCaseIdentifier() {
		return "pause";
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
