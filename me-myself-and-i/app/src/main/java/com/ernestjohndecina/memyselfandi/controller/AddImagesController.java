package com.ernestjohndecina.memyselfandi.controller;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.ernestjohndecina.memyselfandi.data.Database;
import com.ernestjohndecina.memyselfandi.data.entities.PostModal;
import com.ernestjohndecina.memyselfandi.views.DiaryEntryDetailsActivity;
import com.ernestjohndecina.memyselfandi.views.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class AddImagesController implements ActivityResultCallback<ActivityResult> {
    private ExecutorService executorService;

    private Database database;
    private Context context;
    private Handler mainHandler;

    private List<PostModal> diaryPostArrayList;

    private MainActivity mainActivity;

    ArrayList<Uri> imagesUri;

    private Intent diaryEntryDetailsActivty;
    private ActivityResultLauncher galleryIntentActivityResultLauncher;

    public AddImagesController(
            Context context,
            Database database,
            ExecutorService executorService,
            List<PostModal> diaryPostArrayList,
            MainActivity mainActivity
    ) {
        this.context = context;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.database = database;
        this.executorService = executorService;
        this.diaryPostArrayList = diaryPostArrayList;
        this.mainActivity = mainActivity;

        setUpActivity();
    } // End PostAdd Constructor

    @Override
    public void onActivityResult(ActivityResult result) {
        if(result.getResultCode() != Activity.RESULT_OK)
            return;

        Intent data = result.getData();
        imagesUri = getURIFromData(data);
        diaryEntryDetailsActivty.putExtra("IMAGES_URI", imagesUri);
        startActivity();
    } // End onActivityResult()


    private ArrayList<Uri> getURIFromData(Intent data) {
        ArrayList<Uri> imagesEncodedList = new ArrayList<>();

        if(data.getData() != null){
            Uri mImageUri = data.getData();
            imagesEncodedList.add(mImageUri);
        }

        else {
            if (data.getClipData() == null) return imagesEncodedList;

            ClipData mClipData = data.getClipData();

            for (int i = 0; i < mClipData.getItemCount(); i++) {
                ClipData.Item item = mClipData.getItemAt(i);
                Uri uri = item.getUri();
                imagesEncodedList.add(uri);
            } // End for
        } // End else

        return imagesEncodedList;
    } // End getURIFromData()

    private void setUpActivity() {
        diaryEntryDetailsActivty = new Intent(context, DiaryEntryDetailsActivity.class);
        galleryIntentActivityResultLauncher = mainActivity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                data -> {
                    executorService.execute(() -> {
                        try {
                            Intent dataIntent = data.getData();

                            Bundle bundle = dataIntent.getExtras();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                PostModal new_entry = bundle.getSerializable("NEW_POST_DETAILS", PostModal.class);
                                ArrayList<Uri> uriArrayList = bundle.getSerializable("NEW_POST_IMAGES", ArrayList.class);

                                database.userDao.insertUser(new_entry);
                                new_entry.postId = database.userDao.getLatestPostId();

                                storeImageInDirectory(uriArrayList);
                                diaryPostArrayList.add(new_entry);
                            }
                        } catch (IOException e) {
                            Log.d("debug", "FAILED");
                            throw new RuntimeException(e);
                        }
                    });
                }
        );
    }

    private void startActivity() {
        galleryIntentActivityResultLauncher.launch(diaryEntryDetailsActivty);
    }

    private void storeImageInDirectory(ArrayList<Uri> imageURIs) throws IOException {
        try {
            Integer latestIndex  = database.getLatestId().get();
            if(latestIndex == null) latestIndex = 0;

            String root = mainActivity.getExternalFilesDir("").getAbsolutePath();
            File rootDir = new File(root + "/test"+"/posts/posts_" + (latestIndex + 1));
            rootDir.mkdir();

            int counter = 0;
            for (Uri uri: imageURIs) {
                Bitmap image = MediaStore.Images.Media.getBitmap(mainActivity.getContentResolver(), uri);
                File myDir = new File(root + "/test"+"/posts/posts_" + (latestIndex + 1) + "/", "image_" + counter + ".jpeg");

                FileOutputStream out = new FileOutputStream(myDir);
                image.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.close();

                counter++;
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    } // End storeImageInDirectory()
}



