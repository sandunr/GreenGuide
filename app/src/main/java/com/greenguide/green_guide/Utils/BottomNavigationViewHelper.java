package com.greenguide.green_guide.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.greenguide.green_guide.Home.MainActivity;
import com.greenguide.green_guide.R;
import com.greenguide.green_guide.Review.ReviewActivity;
import com.greenguide.green_guide.Search.SearchActivity;

/**
 * Created by sandu on 8/25/2017.
 */

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx) {
        Log.d(TAG, "setupBottomNavigationView: Setting up bottom navigation view");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(true);
        int textSize =20;
        bottomNavigationViewEx.setTextSize(textSize);
        int color = Color.rgb(86, 101, 115);
        int textColor = Color.rgb(253, 254, 254);
        bottomNavigationViewEx.setBackgroundColor(color);
        bottomNavigationViewEx.setItemTextColor(ColorStateList.valueOf(textColor));
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view) {
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_review:
                        Intent intent1 = new Intent(context, MainActivity.class);
                        context.startActivity(intent1);
                        break;
                    case R.id.ic_search:
                        Intent intent2 = new Intent(context, SearchActivity.class);
                        context.startActivity(intent2);
                        break;
                }
                return false;
            }
        });
    }
}
