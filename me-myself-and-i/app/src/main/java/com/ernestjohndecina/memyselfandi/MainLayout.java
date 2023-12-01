package com.ernestjohndecina.memyselfandi;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;


import com.ernestjohndecina.memyselfandi.controller.DiaryEntry;
import com.ernestjohndecina.memyselfandi.controller.FileInputOutputController;
import com.ernestjohndecina.memyselfandi.data.Database;
import com.ernestjohndecina.memyselfandi.data.entities.PostModal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class MainLayout {
    Context context;
    ExecutorService executorService;
    Database database;
    MainActivity activity;

    // Intents
    private FileInputOutputController fileInputOutputController;
    private ActivityResultLauncher<Intent> galleryIntentActivityResultLauncher;

    private Intent homeIntent;
    private final Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
    private Intent profileIntent;





    // Views

    Button homeButton;
    Button createPostButton;
    Button profileButton;

    // TEST
    Handler mainHandler;
    DiaryEntry diaryEntry;
    List<PostModal> testDiaryInput;

    MainLayout(
            MainActivity mainActivity,
            ExecutorService executorService,
            Database database,
            Button homeButton,
            Button createPostButton,
            Button profileButton,
            DiaryEntry diaryEntry,
            List<PostModal> testDiaryInput
    ) {
        this.activity = (MainActivity) mainActivity;
        this.context = activity.getApplicationContext();
        this.executorService = executorService;
        this.database = database;
        this.homeButton = homeButton;
        this.createPostButton = createPostButton;
        this.profileButton = profileButton;

        // TEST
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.diaryEntry = diaryEntry;
        this.testDiaryInput = testDiaryInput;

        // Setup Intents
        setHomeIntent();
        setFileInputOutputController();
        setGalleryIntent();
        setOnClickListeners();
        setProfileIntent();

    }

    private void setOnClickListeners() {
        // Home Button
        homeButton.setOnClickListener(v -> showHomeIntent());

        // Create Post Button
        createPostButton.setOnClickListener(v -> setCreatePostButtonOnClick());

        // Profile Button
        profileButton.setOnClickListener(v -> showProfileIntent());
    }

    private void setCreatePostButtonOnClick() {
        try {
            showGalleryIntent();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    // Intents
    // Home
    private void setHomeIntent() {
        PackageManager pm = context.getPackageManager();
        homeIntent = pm.getLaunchIntentForPackage(context.getPackageName());


        // homeIntent = new Intent(context, MainActivity.class);
        // homeIntent.addFlags(Intent.FLA);
        // homeIntent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        //homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    private void showHomeIntent() {
        activity.startActivity(homeIntent);
        activity.finish();
    }

    // Gallery
    private void setFileInputOutputController() {
        this.fileInputOutputController = new FileInputOutputController(
                context,
                executorService
        );
    }

    private void setGalleryIntent() {
        galleryIntent.setType("image/*");
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        galleryIntentActivityResultLauncher = activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new AddImages(context, database, executorService, diaryEntry, testDiaryInput)
        );
    }



    private void showGalleryIntent() throws ExecutionException, InterruptedException {
        executorService.execute(() ->
                galleryIntentActivityResultLauncher.launch(Intent.createChooser(galleryIntent, "Select Pictures"))
        );
        activity.finish();
    }

    // Profile
    private void setProfileIntent() {
        profileIntent = new Intent(context, ProfileActivity.class);
        profileIntent.putExtra(Intent.EXTRA_INTENT, homeIntent);
    }

    private void showProfileIntent() {
        activity.startActivity(profileIntent);
        activity.finish();
    }
}
