package se.joekickass.marvinapp.repository.service.local;

import java.util.ArrayList;
import java.util.List;

import se.joekickass.marvinapp.repository.service.CommandService;
import se.joekickass.marvinapp.repository.service.CommandServiceCallback;
import se.joekickass.marvinapp.vc.commands.VoiceCommand;

/**
 * The LocalService is used to handle the communication initiation for the
 * HelloMarvinCommand
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
