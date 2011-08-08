/**
 * 
 */
package de.twitterclient;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Christian
 *
 */
public class TweetAdapter extends BaseAdapter {

	private final List<Tweet> tweets;
	private final Context context;
	
	public TweetAdapter(Context context, List<Tweet> tweets) {
		this.tweets = tweets;
		this.context = context;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return tweets.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int pos) {
		return tweets.get(pos);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int pos) {
		return tweets.get(pos).getId();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		Tweet tweet = tweets.get(pos);
		TextView textView;
		if(convertView == null) {
			textView = new TextView(context);
			textView.setPadding(10, 10, 10, 10);
		} else {
			textView = (TextView) convertView;
		}
		textView.setText(tweet.getText());
		return textView;
	}

}
