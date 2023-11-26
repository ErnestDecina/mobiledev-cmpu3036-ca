package com.ernestjohndecina.memyselfandi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ernestjohndecina.memyselfandi.controller.DiaryEntry;
import com.ernestjohndecina.memyselfandi.controller.DiaryPostImage;
import com.ernestjohndecina.memyselfandi.model.DiaryEntryModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Private Variables
    private RecyclerView recylerView;
    private ArrayList<DiaryEntryModel> testDiaryInput;
    // private DiaryPostImage diaryPostImage;
    private DiaryEntry diaryEntry;
    private DiaryPostImage diaryPostImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
        setTestData();
        setUpRecyclerView();




    }

    private void setViews() {
        this.recylerView = (RecyclerView) findViewById(R.id.diaryPost);
    } // End setViews()

    private void setTestData() {
        testDiaryInput = new ArrayList<DiaryEntryModel>();

        ArrayList<Integer> images1 = new ArrayList<>();
        images1.add(R.drawable.image_0);

        ArrayList<Float> location = new ArrayList<>();
        location.add(0.41f);
        location.add(0.41f);
        location.add(0.41f);


        testDiaryInput.add(new DiaryEntryModel("Test 1 Caption Text", images1, "Address 1", location));

        ArrayList<Integer> images2 = new ArrayList<>();
        images2.add(R.drawable.image_1);
        images2.add(R.drawable.image_2);
        ArrayList<Float> location1 = new ArrayList<>();
        location1.add(0.41f);
        location1.add(0.41f);
        location1.add(0.41f);
        testDiaryInput.add(new DiaryEntryModel("Test 2 Caption Text", images2, "Address 2", location1));

        ArrayList<Integer> images3 = new ArrayList<>();
        images3.add(R.drawable.image_0);
        images3.add(R.drawable.image_1);
        images3.add(R.drawable.image_2);
        ArrayList<Float> location2 = new ArrayList<>();
        location2.add(0.41f);
        location2.add(0.41f);
        location2.add(0.41f);
        testDiaryInput.add(new DiaryEntryModel("Test 3 Caption Text", images3, "Address 3", location2));

        ArrayList<Integer> images4 = new ArrayList<>();
        images4.add(R.drawable.image_1);
        images4.add(R.drawable.image_0);
        images4.add(R.drawable.image_2);
        ArrayList<Float> location3 = new ArrayList<>();
        location3.add(0.41f);
        location3.add(0.41f);
        location3.add(0.41f);
        testDiaryInput.add(new DiaryEntryModel("Test 4 Caption Text", images4, "Address 4", location3));
    }

    private void setUpRecyclerView() {
        diaryEntry = new DiaryEntry(this, recylerView, testDiaryInput);
    } // End setUpRecyclerView()
}