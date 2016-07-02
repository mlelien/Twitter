package com.codepath.apps.mysimpletweets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by emilyl on 6/27/16.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Tweet tweet = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.tvNameTweetItem);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserNameTweetItem);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvTimeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);
        ImageView ivReplyIcon = (ImageView) convertView.findViewById(R.id.ivReplyIcon);
        final ImageView favoriteIcon = (ImageView) convertView.findViewById(R.id.favoriteIcon);
        final TextView tvFavoritedCount = (TextView) convertView.findViewById(R.id.tvFavoritedCount);
        final ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        final ImageView ivRetweetIcon = (ImageView) convertView.findViewById(R.id.ivRetweetIcon);
        final TextView tvRetweetCount = (TextView) convertView.findViewById(R.id.tvRetweetCount);

        ivProfileImage.setTag(tweet.getUser().getScreenName());
        final View finalConvertView = convertView;
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(finalConvertView.getContext(), ProfileActivity.class);
                String tag = ivProfileImage.getTag().toString();
                intent.putExtra("username", tag);
                intent.putExtra("user", Parcels.wrap(tweet.getUser()));
                finalConvertView.getContext().startActivity(intent);
            }
        });


        if (tweet.isFavorited())
            favoriteIcon.setImageResource(R.drawable.favorited_icon);
        else
            favoriteIcon.setImageResource(R.drawable.unfavorite_icon);
        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    TwitterClient client = TwitterApplication.getRestClient();

                if (!tweet.isFavorited())
                    client.postFavorite(tweet.getUid(), new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                favoriteIcon.setImageResource(R.drawable.favorited_icon);
                                tvFavoritedCount.setText(Integer.toString(tweet.getFavoritesCount() + 1));
                                tweet.setFavorited(true);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }
                    }); else {
                    client.postUnfavorite(tweet.getUid(), new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            favoriteIcon.setImageResource(R.drawable.unfavorite_icon);
                            tweet.setFavoritesCount(tweet.getFavoritesCount() - 1);
                            tvFavoritedCount.setText(Integer.toString(tweet.getFavoritesCount()));
                            tweet.setFavorited(false);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }
                    });
                }

            }
        });

        tvFavoritedCount.setText(Integer.toString(tweet.getFavoritesCount()));

        ivReplyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ReplyActivity.class);
                intent.putExtra("username", tweet.getUser().getScreenName());
                ((Activity)finalConvertView.getContext()).startActivityForResult(intent, 20);
            }
        });

        tvRetweetCount.setText(Integer.toString(tweet.getRetweetedCount()));
        if (tweet.isRetweeted())
            ivRetweetIcon.setImageResource(R.drawable.retweeted_icon);
        else
            ivRetweetIcon.setImageResource(R.drawable.retweet_icon);
        ivRetweetIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwitterClient client = TwitterApplication.getRestClient();

                if (!tweet.isRetweeted()) {
                    client.postRetweet(tweet.getUid(), new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                ivRetweetIcon.setImageResource(R.drawable.retweeted_icon);
                                tweet.setRetweeted(true);
                                tweet.setRetweetedCount(tweet.getRetweetedCount() + 1);
                                tvRetweetCount.setText(Integer.toString(tweet.getRetweetedCount()));
                            }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }
                    });
                } else {
                    client.postUnretweet(tweet.getUid(), new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            ivRetweetIcon.setImageResource(R.drawable.retweet_icon);
                            tweet.setRetweeted(false);
                            tweet.setRetweetedCount(tweet.getRetweetedCount() - 1);
                            tvRetweetCount.setText(Integer.toString(tweet.getRetweetedCount()));
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }
                    });
                }
            }
        });

        tvName.setText(tweet.getUser().getName());
        tvUserName.setText(" @" + tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        tvTimeStamp.setText(tweet.getTimeStamp());
        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl())
                .transform(new RoundedCornersTransformation(5, 5))
                .into(ivProfileImage);

        return convertView;
    }
}
