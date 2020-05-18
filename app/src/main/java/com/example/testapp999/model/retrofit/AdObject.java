package com.example.testapp999.model.retrofit;

import java.util.List;

public class AdObject {

    private Ad ad;

    public Ad getAd() {
        return ad;
    }


    //extra detail
    public static class Ad {
        List<ObjPhoto> extended_images;

        public List<ObjPhoto> getExtended_images() {
            return extended_images;
        }
    }

    public static class ObjPhoto {
        String filename;

        public String getFilename() {
            return filename;
        }
    }
}


