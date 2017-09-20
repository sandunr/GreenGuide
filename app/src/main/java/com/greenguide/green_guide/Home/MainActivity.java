package com.greenguide.green_guide.Home;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.greenguide.green_guide.Utils.ExpandableListAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.greenguide.green_guide.R;
import com.greenguide.green_guide.Utils.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private Context mContext = MainActivity.this;
    private static final int ACTIVITY_NUM = 0;
    private MapView mMapView;
    private PoiSearch mPoiSearch = null;

    ExpandableListView listView;
    ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;

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

        //listView = (ExpandableListView) findViewById(R.id.lvExp);
        //listAdapter = new ExpandableListAdapter(MainActivity.this);
        //listView.setAdapter(listAdapter);
        //listDataHeader = new ArrayList<String>();
        //listHash = new HashMap<String, List<String>>();
        //initAboutMenuItemList();

    }

    private void poiSearch() {
        mPoiSearch = PoiSearch.newInstance();

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
                //Toast.makeText(MainActivity.this,language[position],Toast.LENGTH_SHORT).show();
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
            // Handle the camera action
        } else if (id == R.id.navigation_drawer_guidelines) {

        } //else if (id == R.id.navigation_drawer_about) {

         else if (id == R.id.navigation_drawer_search) {

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
