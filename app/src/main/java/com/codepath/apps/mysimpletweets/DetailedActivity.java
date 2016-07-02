package com.codepath.apps.mysimpletweets;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class DetailedActivity extends AppCompatActivity {
    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_twitter);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        User user = Parcels.unwrap(getIntent().getParcelableExtra("user"));

        TextView tvName = (TextView) findViewById(R.id.tvNameDetailed);
        TextView tvUserName = (TextView) findViewById(R.id.tvProfileUsernameDetailed);
        ImageView profileImage = (ImageView) findViewById(R.id.ivProfileImageDetailed);
        TextView tweetBody = (TextView) findViewById(R.id.tvBodyDetailed);
        TextView timeStamp = (TextView) findViewById(R.id.tvTimeStampDetailed);
        ImageView replyIcon = (ImageView) findViewById(R.id.replyIconDetailed);
        TextView tvNumLikes = (TextView) findViewById(R.id.tvNumLikes);
        TextView tvNumRetweets = (TextView) findViewById(R.id.tvNumRetweets);

        tvName.setText(user.getName());
        tvUserName.setText("@" + user.getScreenName());

        Picasso.with(this).load(user.getProfileImageUrl())
                .transform(new RoundedCornersTransformation(5, 5))
                .into(profileImage);

        tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        tweetBody.setText(tweet.getBody());
        timeStamp.setText(formatTime(tweet.getCreatedAt()));

//        replyIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(, ReplyActivity.class);
//                intent.putExtra("username", tweet.getUser().getScreenName());
//                ((Activity)view.getContext()).startActivityForResult(intent, 20);
//            }
//        });

        tvNumLikes.setText(String.valueOf(tweet.getFavoritesCount()));
        tvNumRetweets.setText(String.valueOf(tweet.getRetweetedCount()));
    }

    private String formatTime(String date) {
        String[] dateArray = date.split(" ");
        String finalDate = "";

        String time = dateArray[3];
        boolean isPM = false;
        int hour = Integer.parseInt(time.substring(0, 2));
        if (hour > 12) {
            hour -= 12;
            isPM = true;
        } else if (hour == 0)
            hour = 12;
        finalDate = String.valueOf(hour) + time.substring(2, 5) + " ";
        if (isPM)
            finalDate += "PM · ";
        else
            finalDate += "AM · ";

        finalDate = finalDate + dateArray[2] + " ";

        finalDate = finalDate + dateArray[1] + " ";

        finalDate = finalDate + dateArray[5].substring(2,4);

        return finalDate;
    }
}
