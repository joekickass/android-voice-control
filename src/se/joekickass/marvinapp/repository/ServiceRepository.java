package se.joekickass.marvinapp.repository;

import java.util.ArrayList;
import java.util.List;

import se.joekickass.marvinapp.vc.commands.HelloMarvinCommand;
import se.joekickass.marvinapp.vc.commands.TurnLightsOnCommand;
import se.joekickass.marvinapp.vc.commands.VoiceCommand;

public class ServiceRepository {

	/**
	 * Checks if the service handling the command is available 
	 * @param vc the VoiceCommand to verify
	 * @return true if the service is available, false otherwise
	 */
	public boolean isServiceAvailable(VoiceCommand vc) {
		return true;
	}
	
	public static List<VoiceCommand> generateCommands() {
		List<VoiceCommand> list = new ArrayList<VoiceCommand>();
		list.add(new HelloMarvinCommand());
		list.add(new TurnLightsOnCommand());
		return list;
	}

}
