package com.ernestjohndecina.memyselfandi.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.FragmentManager;


import com.ernestjohndecina.memyselfandi.R;
import com.ernestjohndecina.memyselfandi.controller.FileInputOutputController;
import com.ernestjohndecina.memyselfandi.data.Database;
import com.ernestjohndecina.memyselfandi.data.entities.PostModal;
import com.ernestjohndecina.memyselfandi.views.MainActivity;
import com.ernestjohndecina.memyselfandi.views.fragments.CreatePostFragment;
import com.ernestjohndecina.memyselfandi.views.fragments.HomeFragment;
import com.ernestjohndecina.memyselfandi.views.fragments.ProfileFragment;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class MainLayoutController {
    Context context;
    FragmentManager fragmentManager;
    ExecutorService executorService;
    Database database;
    MainActivity activity;

    // Intents
    private FileInputOutputController fileInputOutputController;
    private ActivityResultLauncher<Intent> galleryIntentActivityResultLauncher;

    private Intent homeIntent;
    private final Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
    private Intent profileIntent;

    // Fragments
    HomeFragment homeFragment;
    CreatePostFragment createPostFragement;
    ProfileFragment profileFragement;





    // Views

    Button homeButton;
    Button createPostButton;
    Button profileButton;

    // TEST
    Handler mainHandler;
    List<PostModal> testDiaryInput;

    public MainLayoutController(
            MainActivity mainActivity,
            ExecutorService executorService,
            Database database,
            Button homeButton,
            Button createPostButton,
            Button profileButton,
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
        this.setFragmentManager();
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.testDiaryInput = testDiaryInput;



        // Setup Intents

        setFileInputOutputController();

        setGalleryIntent();
        showGalleryIntent();
        setOnClickListeners();
        setProfileIntent();
        showProfileIntent();
        setHomeIntent();
        showHomeIntent();
    }

    private void setFragmentManager() {
        this.fragmentManager = activity.getSupportFragmentManager();
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
        showGalleryIntent();
    }



    // Intents
    // Home
    private void setHomeIntent() {
        if(homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
            homeFragment.setExecutorService(executorService);
            homeFragment.setDiaryInput(testDiaryInput);
        }
    }

    private void showHomeIntent() {
        this.fragmentManager
                .beginTransaction()
                .replace(
                        R.id.fragmentContainerView,
                        homeFragment
                )
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    // Gallery
    private void setFileInputOutputController() {
        this.fileInputOutputController = new FileInputOutputController(
                context,
                executorService
        );
    }

    private void setGalleryIntent() {
        if(createPostFragement != null) return;

        createPostFragement = CreatePostFragment.newInstance();
        createPostFragement.setDatabase(database);
        createPostFragement.setDiaryInput(testDiaryInput);
        createPostFragement.setExecutorService(executorService);
    }



    private void showGalleryIntent() {
        this.fragmentManager
                .beginTransaction()
                .replace(
                        R.id.fragmentContainerView,
                        createPostFragement
                )
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    // Profile
    private void setProfileIntent() {
        if(profileFragement == null) {
            profileFragement = ProfileFragment.newInstance();
            profileFragement.setDiaryInput(testDiaryInput);
            profileFragement.setExecutorService(executorService);
        }
    }

    private void showProfileIntent() {
        this.fragmentManager
                .beginTransaction()
                .replace(
                        R.id.fragmentContainerView,
                        profileFragement
                )
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }
}
