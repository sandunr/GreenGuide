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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.greenguide.green_guide.Home.MainActivity;
import com.greenguide.green_guide.R;
import com.greenguide.green_guide.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandunr on 11/10/17.
 */

public class ShowReviews extends AppCompatActivity {

    private static final String TAG = "ShowReviewsActivity";
    private Context mContext = ShowReviews.this;
    private static final int ACTIVITY_NUM =1;
    private SlidingUpPanelLayout mLayout;
    private MapView mMapView;

    private List<SearchResultItem> resultList;
    private ResultListAdapter adapter;
    private String company = "";
    private String industry = "Industry: ";
    private String product = "Product: ";
    private String city = "City: ";
    private String address = "Address: ";
    private double rating;
    private double num_r;
    private double lat, lng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);
        Log.d(TAG, "onCreate ShowReviews: started");
        //setupBottomNavigationView();
        String results = getIntent().getStringExtra("resultsJsonArray");
        resultList = new ArrayList<>();
        final ListView resultsList = (ListView) findViewById(R.id.resultsList);
        showResults(results, resultsList);

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.isClickable();
        mLayout.setEnabled(true);
        mLayout.setParallaxOffset(2);
        resultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent("com.greenguide.green_guide.Search.ResultItemView");
                ArrayList<String> resultArray = resultList.get(i).getAllItems();
                intent.putStringArrayListExtra("SearchResultItem", resultArray);
                startActivity(intent);
            }
        });
    }

    private void showResults(String results, ListView resultsList) {

            try {
                JSONArray jsonArray = new JSONArray(results);
                JSONObject jsonObj;
                Log.d("Result", results.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    // Create a marker for each city in the JSON data.
                    jsonObj = jsonArray.getJSONObject(i);
                    company = company.concat(jsonObj.getString("company"));
                    address = address.concat(jsonObj.getString("address"));
                    city = city.concat(jsonObj.getString("city"));
                    lng = jsonObj.getDouble("lng");
                    lat = jsonObj.getDouble("lat");
                    product = product.concat(jsonObj.getString("product"));
                    industry = industry.concat(jsonObj.getString("industry"));
                    rating = jsonObj.getDouble("avg_r");
                    num_r = jsonObj.getDouble("num_r");
                    resultList.add(new SearchResultItem(company, product, industry, lat, lng, rating, num_r, city, address));
                    setMapPins(lat, lng, city);
                }
                adapter = new ResultListAdapter(getApplicationContext(), resultList);
                resultsList.setAdapter(adapter);
            }

            catch (JSONException e) {
                e.printStackTrace();
            }
    }

    private void setMapPins(double lat, double lng, String city) {
        mMapView = (MapView) findViewById(R.id.id_bmapView);
        BaiduMap map = mMapView.getMap();

        if (rating <-2){
            //m_color="red";
            Marker marker = (Marker) map.addOverlay(new MarkerOptions()
                    .perspective(false)
                    .position(new LatLng(lat, lng))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_markg_red))
                    .title(city)
                    .draggable(false));
            map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    showInfo(marker.getPosition());
                    return true;
                }});
        }
        else if(rating >=-2 && rating <-1){
            //m_color="orange";
            Marker marker = (Marker) map.addOverlay(new MarkerOptions()
                    .perspective(false)
                    .position(new LatLng(lat, lng))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_markg_orange))
                    .title(city)
                    .draggable(false));
            map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    showInfo(marker.getPosition());
                    return true;
                }});
        }else if(rating >=-1 && rating <0){
            //m_color="yellow";
            Marker marker = (Marker) map.addOverlay(new MarkerOptions()
                    .perspective(false)
                    .position(new LatLng(lat, lng))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_markg_yellow))
                    .title(city)
                    .draggable(false));
            map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    showInfo(marker.getPosition());
                    return true;
                }});
        }else if(rating ==0){
            //m_color="white";
            Marker marker = (Marker) map.addOverlay(new MarkerOptions()
                    .perspective(false)
                    .position(new LatLng(lat, lng))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_markg_white))
                    .title(city)
                    .draggable(false));
            map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    showInfo(marker.getPosition());
                    return true;
                }});
        }else if(rating >0 && rating <=1){
            //m_color="aqua";
            Marker marker = (Marker) map.addOverlay(new MarkerOptions()
                    .perspective(false)
                    .position(new LatLng(lat, lng))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_markg_aqua))
                    .title(city)
                    .draggable(false));
            map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    showInfo(marker.getPosition());
                    return true;
                }});
        }else if(rating >1 && rating <=2){
            //m_color="lime";
            Marker marker = (Marker) map.addOverlay(new MarkerOptions()
                    .perspective(false)
                    .position(new LatLng(lat, lng))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_markg_lime))
                    .title(city)
                    .draggable(false));
            map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    showInfo(marker.getPosition());
                    return true;
                }});
        }else{
            //m_color="green";
            Marker marker = (Marker) map.addOverlay(new MarkerOptions()
                    .perspective(false)
                    .position(new LatLng(lat, lng))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_markg_green))
                    .title(city)
                    .draggable(false));
            map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    showInfo(marker.getPosition());
                    return true;
                }});
        }
    }

    private void showInfo(LatLng latLng) {
        TextView markerInfo = (TextView) findViewById(R.id.id_marker_info);
        markerInfo.setVisibility(View.VISIBLE);
        markerInfo.setText("Lat = " + latLng.latitude + " lang = " + latLng.longitude);
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