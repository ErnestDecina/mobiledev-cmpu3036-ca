package com.ernestjohndecina.memyselfandi.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ernestjohndecina.memyselfandi.R;
import com.ernestjohndecina.memyselfandi.adapter.DiaryEntryEditDetailsImageAdapter;
import com.ernestjohndecina.memyselfandi.data.entities.PostModal;

import java.util.ArrayList;

public class DiaryEntryDetailsActivity extends AppCompatActivity {
    ArrayList<Uri> uriArrayList;
    DiaryEntryEditDetailsImageAdapter diaryEntryEditDetailsImageAdapter;

    RecyclerView recyclerView;
    EditText caption;
    EditText address;
    Button postDiaryEntryButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_entry_details);

        uriArrayList =  (ArrayList<Uri>)getIntent().getSerializableExtra("IMAGES_URI");

        setViews();
        setRecyclerView();
        setOnclickListeners();
    }

    private void setViews() {
        recyclerView = findViewById(R.id.recyclerView);
        caption = findViewById(R.id.captionTextEdit);
        address = findViewById(R.id.addressLocationEditText);
        postDiaryEntryButton = findViewById(R.id.postDiaryEntryButton);
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
        postDiaryEntryButton.setOnClickListener(v -> postData());
    }

    private void postData() {
        Intent resultIntent = new Intent();
        String caption = String.valueOf(this.caption.getText());
        String address = String.valueOf(this.address.getText());

        ArrayList<String> imagesUriString = new ArrayList<>();

        for (Uri uri: uriArrayList) {
            imagesUriString.add(uri.toString());
        }

        PostModal new_post = new PostModal();
        new_post.caption = caption;
        new_post.address = address;
        new_post.imagePaths = imagesUriString;

        resultIntent.putExtra("NEW_POST_DETAILS", new_post);
        resultIntent.putExtra("NEW_POST_IMAGES", uriArrayList);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}