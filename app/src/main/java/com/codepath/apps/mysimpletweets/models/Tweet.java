package com.codepath.apps.mysimpletweets.models;

import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by emilyl on 6/27/16.
 */
@Parcel
public class Tweet {
    String body;
    long uid;
    String createdAt;
    User user;
    String timeStamp;
    boolean favorited;
    boolean retweeted;
    int favoritesCount;
    int retweetedCount;

    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            String createdAt = jsonObject.getString("created_at");
            tweet.createdAt = createdAt;
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
            tweet.timeStamp = getRelativeTimeAgo(createdAt);
            tweet.favorited = jsonObject.getBoolean("favorited");
            tweet.favoritesCount = jsonObject.getInt("favorite_count");
            tweet.retweeted = jsonObject.getBoolean("retweeted");
            tweet.retweetedCount = jsonObject.getInt("retweet_count");
            if (!jsonObject.isNull("retweeted_status")) {
                JSONObject jsonObject2 = jsonObject.getJSONObject("retweeted_status");
                tweet.favoritesCount = jsonObject2.getInt("favorite_count");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                Tweet tweet = Tweet.fromJSON(jsonArray.getJSONObject(i));
                if (tweet != null)
                    tweets.add(tweet);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return tweets;
    }

    private static String getRelativeTimeAgo(String rawJSONdate) {
        return TimeFormatter.getTimeDifference(rawJSONdate);
    }

    public String getBody() {
        return body;
    }public long getUid() {
        return uid;
    }public String getCreatedAt() {return createdAt;}public User getUser() {return user;}public String getTimeStamp() {
        return timeStamp;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public int getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavorited(boolean isSet) { favorited = isSet; }

    public boolean isRetweeted() {
        return retweeted;
    }

    public void setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
    }

    public int getRetweetedCount() {
        return retweetedCount;
    }

    public void setRetweetedCount(int retweetedCount) {
        this.retweetedCount = retweetedCount;
    }

    public void setFavoritesCount(int favoritesCount) {
        this.favoritesCount = favoritesCount;
    }
}
