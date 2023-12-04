package com.ernestjohndecina.memyselfandi.data.converters;

import android.graphics.Bitmap;

import androidx.room.TypeConverter;

import java.lang.reflect.Type;
import java.util.ArrayList;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PostConverter {

    @TypeConverter
    public String fromArrayList(ArrayList<String> imagePaths) {
        Gson gson = new Gson();

        return gson.toJson(
                imagePaths,
                new TypeToken<ArrayList<String>>(){}.getType()
        );
    }

    @TypeConverter
    public ArrayList<String> fromString(String imagePaths) {
        Gson gson = new Gson();

        try{
            return gson.fromJson(
                    imagePaths,
                    new TypeToken<ArrayList<String>>(){}.getType()
                    );
        } catch (Exception e) {
            return null;
        }
    }

    @TypeConverter
    public static ArrayList<Double> fromStringtoFloat(String value) {
        Type listType = new TypeToken<ArrayList<Double>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayListFloat(ArrayList<Double> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}