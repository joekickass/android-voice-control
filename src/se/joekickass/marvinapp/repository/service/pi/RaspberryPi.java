package se.joekickass.marvinapp.repository.service.pi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import se.joekickass.marvinapp.repository.service.CommandService;
import se.joekickass.marvinapp.repository.service.CommandServiceCallback;
import se.joekickass.marvinapp.vc.commands.VoiceCommand;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Simple implementation of a service.
 * 
 * This one represents a web service running on a raspberry pi.
 * 
 * @author otaino-2
 *
 */
public class RaspberryPi implements CommandService {
	
	private static final String TAG = "RaspberryPiService";
	
	private static final String GET_STATUS = "get_status";
	private static final String SHUTDOWN = "shutdown";

	private String url = "http://192.168.10.57:8080/";
	
	@Override
	public List<VoiceCommand> getAvailableCommands() {
		List<VoiceCommand> list = new ArrayList<VoiceCommand>();
		list.add(new GetStatusCommand(this, GET_STATUS));
		list.add(new ShutdownCommand(this, SHUTDOWN));
		return list;
	}
	
	@Override
	public void checkAvailability(final CommandServiceCallback callback) {
		new AsyncTask<Void, Void, Boolean>() {
			
			@Override
			protected Boolean doInBackground(Void... params) {
				return checkAlive(url);
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				callback.onServiceAvailable(result);
			};
			
		}.execute();
	}
	
	private boolean checkAlive(String url){
        try {
            HttpClient hc = new DefaultHttpClient();
            HttpHead head = new HttpHead(url);

            HttpResponse rp = hc.execute(head);

            return rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
            
        } catch (IOException e){
            Log.e(TAG, "Crap, error occurred");
        }
        
        return false;
    }

	@Override
	public void executeMethod(final CommandServiceCallback callback, final String id) {
		new AsyncTask<Void, Void, String>() {
			
			@Override
			protected String doInBackground(Void... params) {
				if (id.equalsIgnoreCase(GET_STATUS)) {
					return getUrl(url);
				} else if (id.equalsIgnoreCase(SHUTDOWN)) {
					return getUrl(url + "shutdown");
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				callback.onResult(result);
			};
			
		}.execute();
	}
	
	private String getUrl(String url){
        String results = "ERROR";
        try
        {
            HttpClient hc = new DefaultHttpClient();
            HttpGet get = new HttpGet(url);

            HttpResponse rp = hc.execute(get);

            if(rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                results = EntityUtils.toString(rp.getEntity());
            }
            
        }catch(IOException e){
            Log.e(TAG, "Crap, error occurred");
        }
        
        return results;
    }
}
