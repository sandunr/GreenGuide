package com.greenguide.green_guide.Home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.lbsapi.MKGeneralListener;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.greenguide.green_guide.Guidelines.GuidelinesActivity;
import com.greenguide.green_guide.Review.MyReviewsActivity;
import com.greenguide.green_guide.Search.SearchActivity;
import com.greenguide.green_guide.Search.ShowReviews;
import com.greenguide.green_guide.UserGuide.UserGuideActivity;
import com.greenguide.green_guide.Utils.ExpandableListAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.greenguide.green_guide.R;
import com.greenguide.green_guide.Utils.BottomNavigationViewHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.name;
import static android.R.attr.visible;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private Context mContext = MainActivity.this;
    private static final int ACTIVITY_NUM = 0;
    private MapView mMapView;
    private BMapManager bMapManager;

    ExpandableListView listView;
    ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;
    private GetPois initLocations;
    private String strUrl = "http://www.lovegreenguide.com/map_point_app.php?lng=116.492394&lat=39.884462";
    double lat;
    double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Log.d(TAG, "onCreate: Starting.");
        initView();

        setSpinner(navigationView);
        poiSearch();
        try {
            setMarkers();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //listView = (ExpandableListView) findViewById(R.id.lvExp);
        //listAdapter = new ExpandableListAdapter(MainActivity.this);
        //listView.setAdapter(listAdapter);
        //listDataHeader = new ArrayList<String>();
        //listHash = new HashMap<String, List<String>>();
        //initAboutMenuItemList();

    }

    private void setMarkers() throws Exception{
        try {

            new LongOperation().execute("");


        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            e.printStackTrace();
        }
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
                URL githubEndpoint = new URL(strUrl);

                Log.i(TAG, "Reached step 2");

                // Create connection
                HttpURLConnection myConnection =
                        (HttpURLConnection) githubEndpoint.openConnection();

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

                BaiduMap map = mMapView.getMap();


                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    // Create a marker for each city in the JSON data.
                    final JSONObject jsonObj = jsonArray.getJSONObject(i);
                    lat = jsonObj.getDouble("lat");
                    lng = jsonObj.getDouble("lng");
                    String m_color;
                    //设置marker图标为水滴
                    if (jsonObj.getDouble("avg_r") <-2){
                        //m_color="red";
                        Marker marker = (Marker) map.addOverlay(new MarkerOptions()
                                .perspective(false)
                                .position(new LatLng(lat, lng))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_markg_red))
                                .title(jsonObj.getString("city"))
                                .draggable(false));
                        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                showInfo(marker.getPosition());
                                return true;
                            }});
                    }
                    else if(jsonObj.getDouble("avg_r") >=-2 && jsonObj.getDouble("avg_r")<-1 ){
                        //m_color="orange";
                        Marker marker = (Marker) map.addOverlay(new MarkerOptions()
                                .perspective(false)
                                .position(new LatLng(lat, lng))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_markg_orange))
                                .title(jsonObj.getString("city"))
                                .draggable(false));
                        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                showInfo(marker.getPosition());
                                return true;
                            }});
                    }else if(jsonObj.getDouble("avg_r")>=-1 && jsonObj.getDouble("avg_r")<0){
                        //m_color="yellow";
                        Marker marker = (Marker) map.addOverlay(new MarkerOptions()
                                .perspective(false)
                                .position(new LatLng(lat, lng))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_markg_yellow))
                                .title(jsonObj.getString("city"))
                                .draggable(false));
                        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                showInfo(marker.getPosition());
                                return true;
                            }});
                    }else if(jsonObj.getDouble("avg_r")==0){
                        //m_color="white";
                        Marker marker = (Marker) map.addOverlay(new MarkerOptions()
                                .perspective(false)
                                .position(new LatLng(lat, lng))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_markg_white))
                                .title(jsonObj.getString("city"))
                                .draggable(false));
                        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                showInfo(marker.getPosition());
                                return true;
                            }});
                    }else if(jsonObj.getDouble("avg_r")>0 && jsonObj.getDouble("avg_r")<=1){
                        //m_color="aqua";
                        Marker marker = (Marker) map.addOverlay(new MarkerOptions()
                                .perspective(false)
                                .position(new LatLng(lat, lng))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_markg_aqua))
                                .title(jsonObj.getString("city"))
                                .draggable(false));
                        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                showInfo(marker.getPosition());
                                return true;
                            }});
                    }else if(jsonObj.getDouble("avg_r")>1 && jsonObj.getDouble("avg_r")<=2){
                        //m_color="lime";
                        Marker marker = (Marker) map.addOverlay(new MarkerOptions()
                                .perspective(false)
                                .position(new LatLng(lat, lng))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_markg_lime))
                                .title(jsonObj.getString("city"))
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
                                .title(jsonObj.getString("city"))
                                .draggable(false));
                        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                showInfo(marker.getPosition());
                                return true;
                            }});
                    }
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

    private void showInfo(LatLng latLng) {
        TextView markerInfo = (TextView) findViewById(R.id.id_marker_info);
        markerInfo.setVisibility(View.VISIBLE);
        markerInfo.setText("Lat = " + latLng.latitude + " lang = " + latLng.longitude);
    }

    private void poiSearch() {
        //mPoiSearch = PoiSearch.newInstance();
    }

    // Initiate About Item List in Navigation Drawer
    private void initAboutMenuItemList() {

        listDataHeader.add("About");

        List<String> about = new ArrayList<>();
        about.add("About Us");
        about.add("Join Us");
        about.add("Contact Us");

        listHash.put(listDataHeader.get(0), about);
    }

    // Initialize spinner menu list in navigation drawer
    private void setSpinner(NavigationView navigationView) {
        Spinner spinner = (Spinner) navigationView.getMenu().findItem(R.id.navigation_drawer_about).getActionView();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.about_array, android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,language));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navigation_drawer_my_reviews) {
            // Handle the My Reviews Item
            Intent intent = new Intent(this, MyReviewsActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_drawer_guidelines) {
            Intent intent = new Intent(this, GuidelinesActivity.class);
            startActivity(intent);
        }
         else if (id == R.id.navigation_drawer_user_guide) {
            Intent intent = new Intent(this, UserGuideActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_drawer_login) {

        } else if (id == R.id.navigation_drawer_signup) {

        } else {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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


    private void initView() {

        setupBottomNavigationView();
        mMapView = (MapView) findViewById(R.id.id_bmapView);
        GeoPoint geoPoint = new GeoPoint((int) (39.915*1E6), (int) (116.404*1E6));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
}
