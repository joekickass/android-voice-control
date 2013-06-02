package se.joekickass.marvinapp.repository;

import java.util.ArrayList;
import java.util.List;

import se.joekickass.marvinapp.repository.service.CommandService;
import se.joekickass.marvinapp.repository.service.local.LocalService;
import se.joekickass.marvinapp.repository.service.pi.RaspberryPi;
import se.joekickass.marvinapp.vc.commands.VoiceCommand;

/**
 * The ServiceRepository handles CommandServices and the VoiceCommand they
 * support. 
 * 
 * @author otaino-2
 *
 */
public class ServiceRepository {
	
	List<CommandService> services = new ArrayList<CommandService>();
	
	/**
	 * Creates a new ServiceRepository
	 */
	public ServiceRepository() {
		LocalService local = new LocalService();
		services.add(local);
		CommandService pi = new RaspberryPi();
		services.add(pi);
	}

	/**
	 * Generate all available commands from all services handled by this
	 * repository
	 * 
	 * @return a list of VoiceCommands
	 */
	public List<VoiceCommand> generateCommands() {
		List<VoiceCommand> list = new ArrayList<VoiceCommand>();
		for (CommandService service : services) {
			list.addAll(service.getAvailableCommands());
		}
		return list;
	}

}
