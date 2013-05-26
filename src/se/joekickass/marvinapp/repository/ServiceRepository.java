package se.joekickass.marvinapp.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.joekickass.marvinapp.repository.service.CommandService;
import se.joekickass.marvinapp.repository.service.local.LocalService;
import se.joekickass.marvinapp.repository.service.pi.RaspberryPi;
import se.joekickass.marvinapp.vc.commands.VoiceCommand;

public class ServiceRepository {
	
	private Map<String, CommandService> services = new HashMap<String, CommandService>();
	
	public ServiceRepository() {
		LocalService local = new LocalService();
		services.put(local.getId(), local);
		CommandService pi = new RaspberryPi();
		services.put(pi.getId(), pi);
	}

	/**
	 * Checks if the service handling the command is available 
	 * @param vc the VoiceCommand to verify
	 * @return true if the service is available, false otherwise
	 */
	public boolean isServiceAvailable(VoiceCommand vc) {
		String id = vc.getServiceId();
		return services.get(id).isAvailable();
	}
	
	public List<VoiceCommand> generateCommands() {
		List<VoiceCommand> list = new ArrayList<VoiceCommand>();
		for (CommandService service : services.values()) {
			list.addAll(service.getAvailableCommands());
		}
		return list;
	}

}
