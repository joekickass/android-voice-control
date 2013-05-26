package se.joekickass.marvinapp.vc.commands;

public class TurnLightsOnCommand implements VoiceCommand {

	@Override
	public String getLowerCaseIdentifier() {
		return "lights on";
	}

	@Override
	public int getType() {
		return TYPE_COMMAND;
	}

	@Override
	public String getResponse() {
		return "Turning lights on";
	}

	@Override
	public void execute() {
		
	}
}
