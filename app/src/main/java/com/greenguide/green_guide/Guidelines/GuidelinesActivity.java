package com.greenguide.green_guide.Guidelines;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.greenguide.green_guide.R;

public class GuidelinesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidelines);

        CharSequence[] g_Arr = getResources().getTextArray(R.array.guidelines_array);
        ((TextView) findViewById(R.id.guidelines_title)).setText(g_Arr[0]);
        ((TextView) findViewById(R.id.guide_intro_title)).setText(g_Arr[1]);
        ((TextView) findViewById(R.id.guide_intro)).setText(g_Arr[2]);
        ((TextView) findViewById(R.id.guide_body)).setText(g_Arr[3]);
        ((TextView) findViewById(R.id.guide_trust_title)).setText(g_Arr[4]);
        ((TextView) findViewById(R.id.guide_trust_body)).setText(g_Arr[5]);
        ((TextView) findViewById(R.id.guide_security_title)).setText(g_Arr[6]);
        ((TextView) findViewById(R.id.guide_rest)).setText(g_Arr[7]);
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                g_Arr);*/
    }
}
