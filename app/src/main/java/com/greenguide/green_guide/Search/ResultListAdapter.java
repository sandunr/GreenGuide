package com.greenguide.green_guide.Search;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.greenguide.green_guide.R;

import java.text.DecimalFormat;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by sandunr on 3/4/2018.
 */

public class ResultListAdapter extends BaseAdapter {

    private Context mContext;
    private List<SearchResultItem> mSearchResultList;

    public ResultListAdapter(Context mContext, List<SearchResultItem> mSearchResultList) {
        this.mContext = mContext;
        this.mSearchResultList = mSearchResultList;
    }

    @Override
    public int getCount() {
        return mSearchResultList.size();
    }

    @Override
    public SearchResultItem getItem(int i) {
        return mSearchResultList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext, R.layout.layout_result_view, null);

        TextView companyName = v.findViewById(R.id.companyName);
        TextView industry = v.findViewById(R.id.industry);
        TextView product = v.findViewById(R.id.product);
        TextView lat = v.findViewById(R.id.lat);
        TextView lng = v.findViewById(R.id.lng);
        TextView city = v.findViewById(R.id.cmpCity);
        TextView address = v.findViewById(R.id.cmpAddress);
        TextView rating = v.findViewById(R.id.rating);
        TextView num_r = v.findViewById(R.id.num_r);

        String latString = formatFigureTwoPlaces(mSearchResultList.get(i).getLat());
        String lngString = formatFigureTwoPlaces(mSearchResultList.get(i).getLng());
        String ratingString = "Rating: " + formatFigureOnePlace(mSearchResultList.get(i).getRating());
        String num_rString = formatFigureZeroPlace(mSearchResultList.get(i).getNum_r()) + (mSearchResultList.get(i).getNum_r() == 1? " Review": " Reviews");

        companyName.setText(mSearchResultList.get(i).getCompanyName());
        industry.setText(mSearchResultList.get(i).getIndustry());
        product.setText(mSearchResultList.get(i).getProduct());
        city.setText(mSearchResultList.get(i).getCity());
        address.setText(mSearchResultList.get(i).getAddress());
        rating.setText(ratingString);
        num_r.setText(num_rString);

        return v;
    }

    public String formatFigureTwoPlaces(double value) {
        DecimalFormat myFormatter = new DecimalFormat("##0.00");
        return myFormatter.format(value);
    }

    public String formatFigureOnePlace(double value) {
        DecimalFormat myFormatter = new DecimalFormat("##0.0");
        return myFormatter.format(value);
    }

    public String formatFigureZeroPlace(double value) {
        DecimalFormat myFormatter = new DecimalFormat("##0");
        return myFormatter.format(value);
    }
}
