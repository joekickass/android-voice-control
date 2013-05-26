package se.joekickass.marvinapp.vc.commands;

public class HelloMarvinCommand implements VoiceCommand {

	@Override
	public String getLowerCaseIdentifier() {
		return "marvin";
	}

	@Override
	public int getType() {
		return TYPE_HELLO;
	}
	
	@Override
	public String getResponse() {
		return "yes?";
	}

	@Override
	public void execute() {
		// Do nothing
	}
}
