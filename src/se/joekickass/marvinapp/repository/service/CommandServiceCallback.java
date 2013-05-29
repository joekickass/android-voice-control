package se.joekickass.marvinapp.repository.service;

/**
 * Interface to be implemented by the invoker of a CommandService.
 * 
 * @author otaino-2
 *
 */
public interface CommandServiceCallback {
	public void onServiceAvailable(boolean available);
	public void onResult(String id);
}
