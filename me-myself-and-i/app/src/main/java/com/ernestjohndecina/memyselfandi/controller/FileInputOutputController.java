package com.ernestjohndecina.memyselfandi.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.ernestjohndecina.memyselfandi.MainActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class FileInputOutputController {

    Context context;
    ExecutorService executorService;
    // Data
    // Intent data;

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    public FileInputOutputController(Context context, ExecutorService executorService) {
        this.context = context;
        this.executorService = executorService;
    }

    private ArrayList<String> getURIFromData(Intent data) {

        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        ArrayList<String> imagesEncodedList = new ArrayList<>();

        if(data.getData()!= null){
            Uri mImageUri=data.getData();
            Cursor cursor = context.getContentResolver().query(
                    mImageUri,
                    filePathColumn,
                    null,
                    null,
                    null
            );

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imagesEncodedList.add(cursor.getString(columnIndex));
            cursor.close();
        } else {
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                for (int i = 0; i < mClipData.getItemCount(); i++) {

                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri uri = item.getUri();
                    mArrayUri.add(uri);
                    // Get the cursor
                    Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imagesEncodedList.add(cursor.getString(columnIndex));
                    cursor.close();
                } // End for


            } // End if
        } // End else
        // Log.v("LOG_TAG", "Selected Images " + imagesEncodedList.size());
        return imagesEncodedList;
    }

    public void openGalleryIntent() {
        // context.registerForActivityResult()
    }
}
