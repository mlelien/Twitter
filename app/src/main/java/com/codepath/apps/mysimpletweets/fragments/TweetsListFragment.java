package com.codepath.apps.mysimpletweets.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.ComposeActivity;
import com.codepath.apps.mysimpletweets.DetailedActivity;
import com.codepath.apps.mysimpletweets.ProfileActivity;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by emilyl on 6/27/16.
 */
public class TweetsListFragment extends Fragment {
    private TweetsArrayAdapter adapter;
    private ArrayList<Tweet> tweets;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        listView = (ListView) view.findViewById(R.id.lvTweets);
        listView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetailedActivity.class);
                intent.putExtra("tweet", Parcels.wrap(tweets.get(position)));
                intent.putExtra("user", Parcels.wrap(tweets.get(position).getUser()));
                getContext().startActivity(intent);
            }
        });

        return view;
    }

    public void fetchTimelineAsync (int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        TwitterClient client = TwitterApplication.getRestClient();
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // Remember to CLEAR OUT old items before appending in the new ones
                clear();
                // ...the data has come back, add new items to your adapter...
                addAll(Tweet.fromJSONArray(response));
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        tweets = new ArrayList<>();
        adapter = new TweetsArrayAdapter(getActivity(), tweets);
        super.onCreate(savedInstanceState);
    }

    public void addAll(List<Tweet> tweets) {
        adapter.addAll(tweets);
    }

    public void addToBeginning(Tweet tweet) { adapter.insert(tweet, 0); }

    public void clear() { adapter.clear(); }
}
