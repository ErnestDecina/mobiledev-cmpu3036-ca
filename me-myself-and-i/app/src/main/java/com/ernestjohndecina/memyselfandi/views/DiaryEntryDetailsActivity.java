package com.ernestjohndecina.memyselfandi.views;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.ernestjohndecina.memyselfandi.R;
import com.ernestjohndecina.memyselfandi.adapter.DiaryEntryEditDetailsImageAdapter;
import com.ernestjohndecina.memyselfandi.data.entities.PostModal;

import java.util.ArrayList;

public class DiaryEntryDetailsActivity extends AppCompatActivity {
    ArrayList<Uri> uriArrayList;
    DiaryEntryEditDetailsImageAdapter diaryEntryEditDetailsImageAdapter;

    private ActivityResultLauncher<Intent> googleMapsActivityLauncher;
    Intent googleMaps;

    RecyclerView recyclerView;
    EditText caption;
    Button postDiaryEntryButton;
    Button setLocationButton;

    PostModal new_post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_entry_details);

        uriArrayList =  (ArrayList<Uri>)getIntent().getSerializableExtra("IMAGES_URI");

        setViews();
        setRecyclerView();
        setGoogleMapsActivityLauncher();
        setOnclickListeners();

        new_post = new PostModal();
    }

    private void setViews() {
        recyclerView = findViewById(R.id.recyclerView);
        caption = findViewById(R.id.captionTextEdit);
        postDiaryEntryButton = findViewById(R.id.postDiaryEntryButton);
        setLocationButton = findViewById(R.id.setLocationButton);
    }


    private void setRecyclerView() {
        diaryEntryEditDetailsImageAdapter = new DiaryEntryEditDetailsImageAdapter(uriArrayList, this.getApplicationContext());
        // Set Horizontal Image Recycler View
        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        getApplicationContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                )
        );

        // Set Snap Helper
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(diaryEntryEditDetailsImageAdapter);
    }

    private void setOnclickListeners() {
        setLocationButton.setOnClickListener(v -> getLocation());
        postDiaryEntryButton.setOnClickListener(v -> postData());
    }

    private void postData() {
        Intent resultIntent = new Intent();
        String caption = String.valueOf(this.caption.getText());

        ArrayList<String> imagesUriString = new ArrayList<>();

        for (Uri uri: uriArrayList) {
            imagesUriString.add(uri.toString());
        }

        // PostModal new_post = new PostModal();
        new_post.caption = caption;
        new_post.imagePaths = imagesUriString;

        resultIntent.putExtra("NEW_POST_DETAILS", new_post);
        resultIntent.putExtra("NEW_POST_IMAGES", uriArrayList);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void getLocation() {
        Log.d("DEBUG", "clicked location");
        googleMapsActivityLauncher.launch(googleMaps);
    }

    private void setGoogleMapsActivityLauncher() {
        googleMaps = new Intent(this, MapsActivity.class);
        googleMapsActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                data -> {
                    Intent dataIntent = data.getData();
                    Bundle bundle = dataIntent.getExtras();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        new_post.address = bundle.getSerializable("ADDRESS", String.class);
                    }
    }
        );
    }
}