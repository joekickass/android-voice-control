package se.joekickass.marvinapp.state;

import se.joekickass.marvinapp.vc.commands.VoiceCommand;
import android.util.Log;

/**
 * Simple state machine for handling command transactions
 * 
 * 1. STATE_START - waiting for user to initiate contact 2.
 * STATE_COMMAND_EXPECTED - user has made contact, now waiting for command
 * 
 * @author otaino-2
 * 
 */
public class CommandState {

	private static final String TAG = "CommandState";
	
	private static final int STATE_START = 0;

	private static final int STATE_COMMAND_EXPECTED = 20;

	private int state = STATE_START;

	public CommandState() {
		this.state = STATE_START;
	}

	/**
	 * Update the state machine to the next state
	 * 
	 * @param vc the VoiceCommand that triggered the update
	 * @return true if successful, false otherwise
	 */
	public boolean updateState(VoiceCommand vc) {
		
		switch (vc.getType()) {

		case VoiceCommand.TYPE_COMMAND:
			
			// State transaction only allowed for TYPE_COMMAND commands
			if (state == STATE_COMMAND_EXPECTED) {
				Log.d(TAG, "New Marvin state START");
				state = STATE_START;
				return true;
			}
			break;

		default:
			
			// Always allow user to restart communication
			Log.d(TAG, "New Marvin state HELLO");
			state = STATE_COMMAND_EXPECTED;
			return true;
		}
		
		// Command not allowed in current state
		return false;
	}
}
