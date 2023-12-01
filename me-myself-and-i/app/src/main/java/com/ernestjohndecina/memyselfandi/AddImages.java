package com.ernestjohndecina.memyselfandi;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;

import com.ernestjohndecina.memyselfandi.controller.DiaryEntry;
import com.ernestjohndecina.memyselfandi.data.Database;
import com.ernestjohndecina.memyselfandi.data.entities.PostModal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class AddImages implements ActivityResultCallback<ActivityResult> {
    private ExecutorService executorService;

    private Database database;
    private Context context;
    private Handler mainHandler;

    private DiaryEntry diaryEntryAdapter;

    private List<PostModal> diaryPostArrayList;

    AddImages(Context context, Database database, ExecutorService executorService, DiaryEntry diaryEntryAdapter, List<PostModal> diaryPostArrayList) {
        this.context = context;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.database = database;
        this.executorService = executorService;
        this.diaryPostArrayList = diaryPostArrayList;
        this.diaryEntryAdapter = diaryEntryAdapter;


    } // End PostAdd Constructor

    @Override
    public void onActivityResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            // Write Data into File System
            executorService.execute(() -> {
                try {
                    Intent data = result.getData();
                    ArrayList<Uri> imagesUri = getURIFromData(data);
                    ArrayList<String> imagesUriString = new ArrayList<>();

                    for (Uri uri: imagesUri) {
                        imagesUriString.add(uri.toString());
                    }

                    PostModal dataInsert = new PostModal();
                    dataInsert.caption = "Caption d";
                    dataInsert.address = "Address f";
                    dataInsert.imagePaths = imagesUriString;

                    database.userDao.insertUser(dataInsert);
                    dataInsert.postId = database.userDao.getLatestPostId() + 1;

                    Log.d("DEBUG", "Storing Image");
                    storeImageInDirectory(imagesUri);

                    Log.d("DEBUG", "Update Adapter");
                    diaryPostArrayList.add(dataInsert);
                    diaryEntryAdapter.updateAdataper();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });



//            CountDownLatch countDownLatch = new CountDownLatch(10);
//            executorService.execute(() -> {
//                try {
//                    countDownLatch.await(5, TimeUnit.SECONDS);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//
//                Log.d("DEBUG", "Update Adapter");
//                diaryPostArrayList.add(dataInsert);
//                diaryEntryAdapter.updateAdataper();
//            });


        }
    } // End onActivityResult()


    private ArrayList<Uri> getURIFromData(Intent data) {
        ArrayList<Uri> imagesEncodedList = new ArrayList<>();

        if(data.getData()!= null){
            Uri mImageUri = data.getData();
            imagesEncodedList.add(mImageUri);
        } else {
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();

                for (int i = 0; i < mClipData.getItemCount(); i++) {
                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri uri = item.getUri();
                    imagesEncodedList.add(uri);
                } // End for
            } // End if
        } // End else

        return imagesEncodedList;
    } // End getURIFromData()

    private void storeImageInDirectory(ArrayList<Uri> imageURIs) throws IOException {
            try {
                Integer latestIndex  = database.getLatestId().get();
                if(latestIndex == null) latestIndex = 0;
                Log.v("DEBUG", String.valueOf(latestIndex + 1));


                //
                // context.getExternalFilesDir(null);
                String root = context.getExternalFilesDir("").getAbsolutePath();
                File rootDir = new File(root + "/test"+"/posts/posts_" + (latestIndex + 1));
                rootDir.mkdir();

                int counter = 0;
                for (Uri uri: imageURIs) {


                    try {
                        Bitmap image = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                        File myDir = new File(root + "/test"+"/posts/posts_" + (latestIndex + 1) + "/", "image_" + counter + ".png");

                        try (FileOutputStream out = new FileOutputStream(myDir)) {
                            image.compress(Bitmap.CompressFormat.PNG, 100, out);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    counter++;
                }
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
    } // End storeImageInDirectory()

}



