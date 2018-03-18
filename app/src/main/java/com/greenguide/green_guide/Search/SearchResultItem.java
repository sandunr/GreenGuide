package com.greenguide.green_guide.Search;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by sandunr on 3/4/2018.
 */

public class SearchResultItem {
    private String companyName = "";
    private String product = "";
    private String industry = "";
    private double lat = -1;
    private double lng = -1;
    private double rating = -1;
    private double num_r = -1;
    private String city = "";
    private String address = "";

    public SearchResultItem(String companyName, String product, String industry, double lat, double lng, double rating, double num_r, String city, String address) {
        this.companyName = companyName;
        this.product = product;
        this.industry = industry;
        this.lat = lat;
        this.lng = lng;
        this.rating = rating;
        this.num_r = num_r;
        this.city = city;
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

   public double getLat() { return lat; }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getNum_r() { return num_r; }

    public void setNum_r(double num_r) {
        this.num_r = num_r;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<String> getAllItems() {
        ArrayList<String> newList = new ArrayList<>();
        newList.add(this.getAddress());
        newList.add(this.getCity());
        newList.add(this.getCompanyName());
        newList.add(this.getIndustry());
        newList.add(this.getProduct());
        newList.add(String.valueOf(this.getLat()));
        newList.add(String.valueOf(this.getLng()));
        newList.add(String.valueOf(this.getNum_r()));
        newList.add(String.valueOf(this.getRating()));
        return newList;
    }
}
