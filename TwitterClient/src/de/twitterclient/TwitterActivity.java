package de.twitterclient;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class TwitterActivity extends Activity {

	private static final Uri twitterUri = Uri
			.parse("http://api.twitter.com/1/statuses/user_timeline.json?screen_name=InsertEffect");
	private HttpClient client;
	private ListView listView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter);

		client = new DefaultHttpClient();
		listView = (ListView) findViewById(R.id.list);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				Tweet tweet = (Tweet) adapterView.getItemAtPosition(position);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri
						.parse("http://twitter.com/#!/InsertEffect/status/"
								+ tweet.getId()));
				startActivity(intent);
			}
		});
		Button updateButton = (Button) findViewById(R.id.button);
		updateButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				loadTweets(twitterUri);
			}
		});
		loadTweets(twitterUri);
	}
	
	private void loadTweets(Uri uri) {
		AsyncTask<Uri,Object,List<Tweet>> task = new TwitterTask();
		task.execute(uri);
	}
	
	private class TwitterTask extends AsyncTask<Uri,Object,List<Tweet>>{

		protected void onPostExecute(List<Tweet> result){
			listView.setAdapter(new TweetAdapter(TwitterActivity.this, result));
		}
		
		@Override
		protected List<Tweet> doInBackground(Uri... params) {
			try {
				HttpGet get = new HttpGet(params[0].toString());
				HttpResponse response = client.execute(get);
				String result = EntityUtils.toString(response.getEntity());
				
				JSONArray array = new JSONArray(result);
				ArrayList<Tweet> tweets = new ArrayList<Tweet>(array.length());
				
				for (int i = 0; i < array.length(); i++) {
					JSONObject object = array.getJSONObject(i);
					Tweet tweet = new Tweet(object);
					tweets.add(tweet);
				}
				
				return tweets;
			} catch(Exception e){
				throw new RuntimeException(e);
			}
		}
	}
}