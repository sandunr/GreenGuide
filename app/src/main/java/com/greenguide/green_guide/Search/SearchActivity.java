package com.greenguide.green_guide.Search;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.greenguide.green_guide.Home.MainActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.greenguide.green_guide.R;
import com.greenguide.green_guide.Utils.BottomNavigationViewHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sandu on 8/25/2017.
 */

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    private Context mContext = SearchActivity.this;
    private static final int ACTIVITY_NUM = 1;
    double lat, lng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Log.d(TAG, "onCreate: started");

        setupBottomNavigationView();
    }

    public void findReviews(View v) {
        try {

            new LongOperation().execute("");


        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            e.printStackTrace();
        }
        //Intent intent = new Intent(SearchActivity.this, ShowReviews.class);
        //startActivity(intent);
    }

    public class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            //String response = "";
            final StringBuilder json = new StringBuilder();

            try {
                //Thread.sleep(1000);

                Log.i(TAG, "Reached step 1");

                // Create URL
                URL endpoint = new URL("http://www.lovegreenguide.com/search-all_app.php?s_company=i&s_location=");

                Log.i(TAG, "Reached step 2");

                // Create connection
                HttpURLConnection myConnection =
                        (HttpURLConnection) endpoint.openConnection();

                Log.i(TAG, "Reached step 3");

                if (myConnection.getResponseCode() == 200) {

                    Log.i(TAG, "Reached step 4");

                    InputStreamReader responseBodyReader =
                            new InputStreamReader(myConnection.getInputStream());

                    // Read the JSON data into the StringBuilder
                    int read;
                    char[] buff = new char[1024];
                    while ((read = responseBodyReader.read(buff)) != -1) {
                        json.append(buff, 0, read);
                    }
//                    java.util.Scanner s = new java.util.Scanner(responseBodyReader).useDelimiter("\\A");
//                    response = s.hasNext() ? s.next() : "";

                    //JsonReader jsonReader = new JsonReader(responseBodyReader);

                    Log.i(TAG, "Reached step 5");
                    myConnection.disconnect();

                }

                Log.i(TAG, "Reached step 6");

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }

            return json.toString();
        }

        @Override
        protected void onPostExecute(String result) {

            try {

                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    // Create a marker for each city in the JSON data.
                    final JSONObject jsonObj = jsonArray.getJSONObject(i);
                    lat = jsonObj.getDouble("lat");
                    lng = jsonObj.getDouble("lng");

                    System.out.println(jsonArray.toString());
                }


            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: Setting up bottom navigation view");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottom_NavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
