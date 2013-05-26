package se.joekickass.marvinapp.repository.service;

public interface CommandServiceCallback {
	public void onServiceAvailable(boolean available);
	public void onResult(String id);
}
