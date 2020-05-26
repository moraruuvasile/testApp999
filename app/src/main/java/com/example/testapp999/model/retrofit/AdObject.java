package com.example.testapp999.model.retrofit;

import java.util.List;

public class AdObject {
    public static final String id900 = "https://i.simpalsmedia.com/999.md/BoardImages/900x900/";

    private Ad ad;

    public Ad getAd() {
        return ad;
    }


    //extra detail
    public static class Ad {
        List<ObjPhoto> extended_images;
        ObjCoord location_coordinates;

        public List<ObjPhoto> getExtended_images() {
            return extended_images;
        }
        public ObjCoord getLocation_coordinates() {return location_coordinates;}

    }

    public static class ObjPhoto {
        String filename;

        public String getFilename() {
            return filename;
        }
    }

    public static class ObjCoord {
        Value value;
        public Value getValue() {return value;}
    }

    public static class Value {
        private float bearing;
        private float pitch;
        private float lat;
        private float center_lon;
        private float lon;
        private float zoom;
        private float center_lat;

        public float getBearing() {
            return bearing;
        }

        public float getPitch() {
            return pitch;
        }

        public float getLat() {
            return lat;
        }

        public float getCenter_lon() {
            return center_lon;
        }

        public float getLon() {
            return lon;
        }

        public float getZoom() {
            return zoom;
        }

        public float getCenter_lat() {
            return center_lat;
        }
    }
}


