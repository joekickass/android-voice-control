package se.joekickass.marvinapp.vc.commands;

public interface VoiceCommand {
	
	public static final int TYPE_HELLO = 10;
	public static final int TYPE_COMMAND = 11;
	
	public String getLowerCaseIdentifier();
	public int getType();
	public String getResponse();
	public void execute();
	public String getServiceId();
}
