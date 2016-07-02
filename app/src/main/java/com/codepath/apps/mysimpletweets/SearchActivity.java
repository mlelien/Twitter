package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.fragments.SearchTweetsFragment;

public class SearchActivity extends AppCompatActivity {
    private SearchTweetsFragment searchTweetsFragment;
    public final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_twitter);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        if (savedInstanceState == null) {
            searchTweetsFragment = SearchTweetsFragment.newInstance(getIntent().getStringExtra("query"));
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.searchTweetsContainer, searchTweetsFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);

//        MenuItem searchItem = menu.findItem(R.id.miSearch);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchItem.expandActionView();
//        searchView.requestFocus();
//
//        int searchImgId = android.support.v7.appcompat.R.id.search_button;
//        ImageView v = (ImageView) searchView.findViewById(searchImgId);
//        v.setImageResource(R.drawable.ic_search);
//
//        //make text black
//        int searchTextID = android.support.v7.appcompat.R.id.search_src_text;
//        TextView textView = (TextView) searchView.findViewById(searchTextID);
//        textView.setTextColor(Color.BLACK);
//        textView.setText(getIntent().getStringExtra("query"));
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                searchView.clearFocus();
//                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
//                intent.putExtra("query", query);
//                startActivity(intent);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.miCompose) {
            Intent intent = new Intent(this, ComposeActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }

        return super.onOptionsItemSelected(item);
    }
}
