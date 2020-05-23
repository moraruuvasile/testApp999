package com.example.testapp999.model.retrofit;

import android.graphics.Movie;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

@Entity(tableName = "Ads_table")
public class Ads implements Serializable {
    public static final String id160 = "https://i.simpalsmedia.com/999.md/BoardImages/160x120/";

    @PrimaryKey
    @NonNull
    private String _id;
    private String user_id;
    private String title;

    private float price;
    private String currency;

    private String image;

//    public Ads(String image) {
//        this.image = image;
//    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public static class ObjAds {
        ArrayList<Ads> ads = new ArrayList<>();

        public ArrayList<Ads> getAds() {
            return ads;
        }
    }

    public class NameCompare implements Comparator<Ads>
    {

        public int compare(Ads m1, Ads m2)
        {
            return m1.getTitle().compareTo(m2.getTitle());
        }
    }

    public class ReversNameCompare implements Comparator<Ads>
    {

        public int compare(Ads m1, Ads m2)
        {
            return m2.getTitle().compareTo(m1.getTitle());
        }
    }

    public class PriceCompare implements Comparator<Ads>
    {

        public int compare(Ads m1, Ads m2)
        {
            if (m1.getPrice() < m2.getPrice()) return -1;
            if (m1.getPrice() > m2.getPrice()) return 1;
            else return 0;
        }
    }


    public class ReversPriceCompare implements Comparator<Ads>
    {

        public int compare(Ads m1, Ads m2)
        {
            if (m1.getPrice() < m2.getPrice()) return 1;
            if (m1.getPrice() > m2.getPrice()) return -1;
            else return 0;
        }
    }

}












