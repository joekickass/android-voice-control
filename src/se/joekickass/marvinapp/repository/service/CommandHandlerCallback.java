package se.joekickass.marvinapp.repository.service;

import se.joekickass.marvinapp.vc.commands.VoiceCommand;

public interface CommandHandlerCallback {
	public void onResult(VoiceCommand vc);
}
