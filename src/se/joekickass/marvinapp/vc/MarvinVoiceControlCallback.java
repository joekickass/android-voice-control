package se.joekickass.marvinapp.vc;

import se.joekickass.marvinapp.vc.commands.VoiceCommand;

/**
 * Callback for the component that invokes the MarvinVoiceControlFacade.
 * 
 * @author otaino-2
 *
 */
public interface MarvinVoiceControlCallback {
	void onCommandReceived(VoiceCommand vc);
}
