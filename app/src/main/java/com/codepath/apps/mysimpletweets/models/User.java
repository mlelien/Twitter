package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by emilyl on 6/27/16.
 */

@Parcel
public class User {
     String name;
     long uid;
     String screenName;
     String profileImageUrl;
     String tagline;
     int followers;
     int following;

    public User() { super(); }

    public static User fromJSON(JSONObject jsonObject) {
        User user = new User();

        try {
            user.name = jsonObject.getString("name");
            user.uid = jsonObject.getLong("id_str");
            user.screenName = jsonObject.getString("screen_name");
            user.profileImageUrl = jsonObject.getString("profile_image_url");
            user.tagline = jsonObject.getString("description");
            user.followers = jsonObject.getInt("followers_count");
            user.following = jsonObject.getInt("friends_count");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getTagline() {
        return tagline;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }
}
