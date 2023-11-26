package com.ernestjohndecina.memyselfandi.model;

import java.util.ArrayList;

public class DiaryEntryModel {
    public String caption;
    public ArrayList<Integer> images;
    public String address;

    public ArrayList<Float> location;

    public DiaryEntryModel(String caption, ArrayList<Integer> images, String address, ArrayList<Float> location) {
        this.caption = caption;
        this.images = images;
        this.address = address;
        this.location = location;
    }
}
