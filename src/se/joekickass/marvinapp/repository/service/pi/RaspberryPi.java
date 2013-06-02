package se.joekickass.marvinapp.repository.service.pi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import se.joekickass.marvinapp.repository.service.CommandService;
import se.joekickass.marvinapp.repository.service.CommandServiceCallback;
import se.joekickass.marvinapp.vc.commands.VoiceCommand;
import android.os.AsyncTask;
import android.util.Base64;
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
	
	private static final String XBMC_PLAY = "xbmc_play";
	private static final String XBMC_PAUSE = "xbmc_pause";
	private static final String XBMC_OPEN = "xbmc_open";

	private static final String SCHEME = "http://";
	private static final String ADDRESS = "192.168.10.57";
	private static final String XBMC_PORT = "80";
	
	@Override
	public List<VoiceCommand> getAvailableCommands() {
		List<VoiceCommand> list = new ArrayList<VoiceCommand>();
		list.add(new XbmcPlayCommand(this, XBMC_PLAY));
		list.add(new XbmcPauseCommand(this, XBMC_PAUSE));
		list.add(new XbmcOpenCommand(this, XBMC_OPEN));
		return list;
	}
	
	@Override
	public void checkAvailability(final CommandServiceCallback callback, final String id) {
		new AsyncTask<Void, Void, Boolean>() {
			
			@Override
			protected Boolean doInBackground(Void... params) {
				String url = SCHEME + ADDRESS + ":" + XBMC_PORT;
				return checkXbmcAlive(url);
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				callback.onServiceAvailable(result);
			};
			
		}.execute();
	}
	
	private boolean checkXbmcAlive(String url){
        try {
            HttpClient hc = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");
            post.setHeader("Accept", "application/json");
            post.setHeader("Authorization", "Basic " + Base64.encodeToString("xbmc:xbmc".getBytes(), Base64.NO_WRAP));
            post.setEntity(new StringEntity("{\"jsonrpc\": \"2.0\", \"method\": \"Player.GetActivePlayers\", \"id\": 1}", HTTP.UTF_8));

            HttpResponse rp = hc.execute(post);
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
				if (id.equalsIgnoreCase(XBMC_PLAY) || id.equalsIgnoreCase(XBMC_PAUSE)) {
					String url = SCHEME + ADDRESS + ":" + XBMC_PORT + "/jsonrpc";
					String json = "{\"jsonrpc\": \"2.0\", \"method\": \"Player.PlayPause\", \"params\": { \"playerid\": 1 }, \"id\": 1}";
					return httpPost(url, json);
				} else if (id.equalsIgnoreCase(XBMC_OPEN)) {
					String url = SCHEME + ADDRESS + ":" + XBMC_PORT + "/jsonrpc";
					String json = "{ \"jsonrpc\": \"2.0\", \"method\": \"Player.Open\", \"params\": { \"item\": { \"movieid\": 5 } }, \"id\": 1 }";
					return httpPost(url, json);
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				callback.onResult(result);
			};
			
		}.execute();
	}
	
	private String httpPost(String url, String json) {
        String results = "ERROR";
        try
        {
        	HttpClient hc = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-type", "application/json");
            post.setHeader("Accept", "application/json");
            post.setHeader("Authorization", "Basic " + Base64.encodeToString("xbmc:xbmc".getBytes(), Base64.NO_WRAP));
            post.setEntity(new StringEntity(json.toString(), HTTP.UTF_8));

            HttpResponse rp = hc.execute(post);
            
            if(rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                results = EntityUtils.toString(rp.getEntity());
            }
            
        }catch(IOException e){
        	e.printStackTrace();
            Log.e(TAG, "Crap, error occurred");
        }
        
        return results;
	}

}
