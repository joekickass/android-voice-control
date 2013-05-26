package se.joekickass.marvinapp.vc;

import se.joekickass.marvinapp.vc.commands.VoiceCommand;

public interface MarvinVoiceControlCallback {
	void onCommandReceived(VoiceCommand vc);
}
