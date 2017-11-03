package com.greenguide.green_guide.Home;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.greenguide.green_guide.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by sandunr on 9/20/17.
 */

public class GetPois extends AppCompatActivity {

    private static final String TAG = "LongOperation";

    List<JSONObject> locationList;
    private String strUrl = "http://www.lovegreenguide.com/map_point_app.php?lng=116.492394&lat=39.884462";

    public void retrieve() throws Exception {

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
                /*Log.i("RESULT", result);
                //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

                JSONArray array = new JSONArray(result);
                JSONArray resultArray = new JSONArray();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = new JSONObject(array.get(i).toString());
                    JSONObject resultObj = new JSONObject();
                    resultObj.put("Latitude", obj.get("lat"));
                    resultObj.put("Longitude", obj.get("lng"));
                    resultArray.put(resultObj);
                }
*/
//                TextView txt = (TextView) findViewById(R.id.editText4);
//                txt.setText(resultArray.toString());

                MapView mMapView = (MapView) findViewById(R.id.id_bmapView);
                BaiduMap map = mMapView.getMap();

                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    // Create a marker for each city in the JSON data.
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    Marker marker = (Marker) map.addOverlay(new MarkerOptions()
                            .perspective(false)
                            .position(new LatLng(
                                    jsonObj.getDouble("lat"),
                                    jsonObj.getDouble("lng")
                            ))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_markg_red))
                            .title(jsonObj.getString("city"))
                            .draggable(false));
                    map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            return false;
                        }});
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

    /*public void callWebService(View view) throws Exception {

        //System.out.printf("Reached step 1");
        Log.i("INFO", "Reached step 1");

        // Create URL
        URL githubEndpoint = new URL("http://www.lovegreenguide.com/map_point_app.php?lng=116.492394&lat=39.884462");

        System.out.printf("Reached step 2");

        // Create connection
        HttpURLConnection myConnection =
                (HttpURLConnection) githubEndpoint.openConnection();

        System.out.printf("Reached step 3");

        if (myConnection.getResponseCode() == 200) {

            System.out.printf("Reached step 4");

            InputStream responseBody = myConnection.getInputStream();
            InputStreamReader responseBodyReader =
                    new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);

            System.out.printf("Reached step 5");

        }

        System.out.printf("Reached step 6");
    }*/
}