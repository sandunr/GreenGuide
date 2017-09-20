package com.greenguide.green_guide.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.Text;
import com.greenguide.green_guide.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sandunr on 9/16/17.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;

    String [] about = {"About"};
    String[] aboutSubItems = {"About Us", "Join Us", "Contact Us"};

    public ExpandableListAdapter(Context context) {
        this.context = context;
    }
    @Override
    public int getGroupCount() {
        return about.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return aboutSubItems.length;
    }

    @Override
    public Object getGroup(int i) {
        return about[i];
    }

    @Override
    public Object getChild(int i, int i1) {
        return aboutSubItems[i1];
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        TextView txtView = new TextView(context);
        txtView.setText(about[i]);
        txtView.setTypeface(null, Typeface.BOLD);
        return txtView;

        /*String headerTitle = (String) getGroup(i);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group, null);
        }
        TextView  lvListHeader = (TextView) view.findViewById(R.id.lvListHeader);
        lvListHeader.setTypeface(null, Typeface.BOLD);
        lvListHeader.setText(headerTitle);
        return view;*/
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        /*final String childText = (String) getChild(i, i1);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) view.findViewById(R.id.lvListItem);
        txtListChild.setText(childText);
        return view;*/

        final TextView txtView = new TextView(context);
        txtView.setText(aboutSubItems[i1]);

        txtView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(context, txtView.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return txtView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
