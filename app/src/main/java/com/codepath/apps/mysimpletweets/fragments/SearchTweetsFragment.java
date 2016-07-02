package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by emilyl on 6/30/16.
 */
public class SearchTweetsFragment extends TweetsListFragment {
        private TwitterClient client;

        public static SearchTweetsFragment newInstance(String query) {
            SearchTweetsFragment searchTweetsFragment = new SearchTweetsFragment();
            Bundle args = new Bundle();
            args.putString("query", query);
            searchTweetsFragment.setArguments(args);
            return searchTweetsFragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            client = TwitterApplication.getRestClient();

            populateTimeline();
        }

        private void populateTimeline() {
            client.getSearchResults(getArguments().getString("query"), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("statuses");
                        addAll(Tweet.fromJSONArray(jsonArray));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("DEBUG", errorResponse.toString());
                }
            });
        }
    }

