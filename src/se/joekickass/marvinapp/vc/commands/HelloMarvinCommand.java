package se.joekickass.marvinapp.vc.commands;

public class HelloMarvinCommand implements VoiceCommand {

	@Override
	public String getLowerCaseIdentifier() {
		return "marvin";
	}

	@Override
	public String getResponse() {
		return "yes?";
	}

}
