package se.joekickass.marvinapp.repository.service.local;

import java.util.ArrayList;
import java.util.List;

import se.joekickass.marvinapp.repository.service.CommandService;
import se.joekickass.marvinapp.repository.service.CommandServiceCallback;
import se.joekickass.marvinapp.vc.commands.VoiceCommand;

/**
 * Simple implementation of a service.
 * 
 * This one represents a web service running on a raspberry pi.
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
	public void checkAvailability(CommandServiceCallback callback) {
		callback.onServiceAvailable(true);
	}

	@Override
	public void executeMethod(CommandServiceCallback callback, String id) {
		callback.onResult(null);
	}
}
