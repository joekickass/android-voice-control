package se.joekickass.marvinapp.repository.service;

/**
 * Interface to be implemented by the invoker of a CommandService.
 * 
 * @author otaino-2
 *
 */
public interface CommandServiceCallback {
	/**
	 * Returns true if the service is available
	 * @param available true if available, false otherwise
	 */
	public void onServiceAvailable(boolean available);
	/**
	 * The result of the executed method
	 * @param id the id of the executed method
	 */
	public void onResult(String id);
}
