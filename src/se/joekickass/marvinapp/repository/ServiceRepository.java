package se.joekickass.marvinapp.repository;

import java.util.ArrayList;
import java.util.List;

import se.joekickass.marvinapp.repository.service.CommandService;
import se.joekickass.marvinapp.repository.service.local.LocalService;
import se.joekickass.marvinapp.repository.service.pi.RaspberryPi;
import se.joekickass.marvinapp.vc.commands.VoiceCommand;

public class ServiceRepository {
	
	List<CommandService> services = new ArrayList<CommandService>();
	
	public ServiceRepository() {
		LocalService local = new LocalService();
		services.add(local);
		CommandService pi = new RaspberryPi();
		services.add(pi);
	}

	public List<VoiceCommand> generateCommands() {
		List<VoiceCommand> list = new ArrayList<VoiceCommand>();
		for (CommandService service : services) {
			list.addAll(service.getAvailableCommands());
		}
		return list;
	}

}
