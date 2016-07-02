package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {
    EditText etMessage;
    TextView tvCharsLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        tvCharsLeft = (TextView) findViewById(R.id.tvCharsLeft);
        etMessage = (EditText) findViewById(R.id.etMessage);
        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int charsLeft = 140 - start;
                if (charsLeft < 0)
                    tvCharsLeft.setTextColor(Color.parseColor("#F21D1D"));
                else
                    tvCharsLeft.setTextColor(Color.parseColor("#808080"));

                tvCharsLeft.setText(Integer.toString(charsLeft));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void tweetPressed(View view) {
        TwitterClient client = TwitterApplication.getRestClient();
        String tweetBody = etMessage.getText().toString();

        client.postTweet(tweetBody, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Tweet tweet = Tweet.fromJSON(response);
                Intent intent = new Intent();
                intent.putExtra("tweet", Parcels.wrap(tweet));
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
