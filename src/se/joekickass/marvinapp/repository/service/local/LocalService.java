package se.joekickass.marvinapp.repository.service.local;

import java.util.ArrayList;
import java.util.List;

import se.joekickass.marvinapp.repository.service.CommandService;
import se.joekickass.marvinapp.repository.service.CommandServiceCallback;
import se.joekickass.marvinapp.vc.commands.VoiceCommand;

/**
 * Since the HelloMarvinCommand follows the same contract as a remote service,
 * there must be a receiving service on which the command can operate. The
 * LocalService represents that service for the HelloMarvinCommand, and it
 * is always available and always returns null.
 * 
 * @author otaino-2
 *
 */
public class LocalService implements CommandService {
	
	@Override
	public List<VoiceCommand> getAvailableCommands() {
		List<VoiceCommand> list = new ArrayList<VoiceCommand>();
		list.add(new HelloMarvinCommand(this, null));
		return list;
	}
	
	@Override
	public void checkAvailability(CommandServiceCallback callback, String id) {
		callback.onServiceAvailable(true);
	}

	@Override
	public void executeMethod(CommandServiceCallback callback, String id) {
		callback.onResult(null);
	}
}
