package com.ernestjohndecina.memyselfandi.views.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ernestjohndecina.memyselfandi.controller.AddImagesController;
import com.ernestjohndecina.memyselfandi.R;
import com.ernestjohndecina.memyselfandi.data.Database;
import com.ernestjohndecina.memyselfandi.data.entities.PostModal;
import com.ernestjohndecina.memyselfandi.views.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;


public class CreatePostFragment extends Fragment {
    private final Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
    private ActivityResultLauncher<Intent> galleryIntentActivityResultLauncher;
    private AddImagesController addImages;
    private ExecutorService executorService;
    private Database database;
    private List<PostModal> testDiaryInput;


    public CreatePostFragment() {}

    public static CreatePostFragment newInstance() {
        CreatePostFragment fragment = new CreatePostFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setGalleryIntent();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(
                R.layout.fragment_create_post,
                container,
                false
        );

        galleryIntentActivityResultLauncher.launch(galleryIntent);
        return view;
    }


    public void setGalleryIntent() {
        galleryIntent.setType("image/*");
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        addImages = new AddImagesController(
                getContext(),
                database,
                executorService,
                testDiaryInput,
                (MainActivity) getActivity()
        );

        galleryIntentActivityResultLauncher = getActivity().registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                addImages
        );
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setDiaryInput(List<PostModal> diaryInput) {
        this.testDiaryInput = diaryInput;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

}