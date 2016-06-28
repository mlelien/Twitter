package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emilyl on 6/27/16.
 */
public class TweetsListFragment extends Fragment {
    private TweetsArrayAdapter adapter;
    private ArrayList<Tweet> tweets;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        listView = (ListView) view.findViewById(R.id.lvTweets);
        listView.setAdapter(adapter);
        return view;
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
}
