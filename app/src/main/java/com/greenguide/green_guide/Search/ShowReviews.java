package com.greenguide.green_guide.Search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.greenguide.green_guide.R;
import com.greenguide.green_guide.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sandunr on 11/10/17.
 */

public class ShowReviews extends AppCompatActivity {

    private static final String TAG = "ShowReviewsActivity";
    private Context mContext = ShowReviews.this;
    private static final int ACTIVITY_NUM =0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);
        Log.d(TAG, "onCreate: started");
        String results = getIntent().getStringExtra("resultsJsonArray");
        showResults(results);
        //setupBottomNavigationView();
    }

    private void showResults(String results) {
        double lat, lng;

        TextView latitude = (TextView) findViewById(R.id.latitude);
        TextView longitude = (TextView) findViewById(R.id.longitude);
        TextView address = (TextView) findViewById(R.id.address);
        TextView city = (TextView) findViewById(R.id.city);
        TextView product = (TextView) findViewById(R.id.product);
        TextView industry = (TextView) findViewById(R.id.industry);
        TextView rating = (TextView) findViewById(R.id.rating);
        TextView company = (TextView) findViewById(R.id.company);

        try {
            JSONArray jsonArray = new JSONArray(results);
            for (int i = 0; i < jsonArray.length(); i++) {
                // Create a marker for each city in the JSON data.
                final JSONObject jsonObj = jsonArray.getJSONObject(i);
                latitude.setText(jsonObj.getString("lat"));
                latitude.setText(jsonObj.getString("lng"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: Setting up bottom navigation view");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottom_NavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }*/
}