package com.greenguide.green_guide.Search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.greenguide.green_guide.R;

import java.util.ArrayList;

/**
 * Created by sandunr on 3/18/2018.
 */

public class ResultItemView extends AppCompatActivity {
    private static final String TAG = "ShowReviewsActivity";
    private Context mContext = ResultItemView.this;
    private static final int ACTIVITY_NUM =1;

    ArrayList<String> results;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_item_view);
        Log.d(TAG, "onCreate ResultItemView: started");
        //setupBottomNavigationView();
        results = getIntent().getStringArrayListExtra("SearchResultItem");

        populateList();
    }

    private void populateList() {

    }
}
