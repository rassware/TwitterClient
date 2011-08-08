/**
 * 
 */
package de.twitterclient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Christian
 *
 */
public class Tweet {
	private long id;
	private String text;
	private String userName;
	private String source;
	
	public Tweet(JSONObject object) throws JSONException {
		id = object.getLong("id");
		text = object.getString("text");
		source = object.getString("source");
		userName = object.getJSONObject("user").getString("name");
	}

	public long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public String getUserName() {
		return userName;
	}

	public String getSource() {
		return source;
	}
	
	
}
