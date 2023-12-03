package com.ernestjohndecina.memyselfandi.data.entities;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "posts")
public class PostModal implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "post_id")
    public int postId;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "caption")
    public String caption;

    @ColumnInfo(name = "image_paths")
    public ArrayList<String> imagePaths;

    @ColumnInfo(name = "address")
    public String address;

    // @ColumnInfo(name = "location")
    // public ArrayList<Float> location;
}
