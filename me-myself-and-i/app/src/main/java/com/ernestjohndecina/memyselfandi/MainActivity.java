package com.ernestjohndecina.memyselfandi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.ernestjohndecina.memyselfandi.adapter.DiaryEntryAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Private Variables
    private RecyclerView recylerView;
    private DiaryEntryAdapter diaryEntryAdapter;
    private ArrayList<String> testDiaryInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
        Log.d("Setup", "Views setup");
        testDiaryInput = new ArrayList<String>();
        testDiaryInput.add("Test 1");
        testDiaryInput.add("Test 2");
        Log.d("Setup", "String Array Setup");
        setUpRecyclerView();
        this.recylerView.setAdapter(this.diaryEntryAdapter);
        Log.d("Setup", "Recycler View setup");
        Log.d("Setup", testDiaryInput.get(1));
    }

    private void setViews() {
        this.recylerView = (RecyclerView) findViewById(R.id.diaryPost);
    } // End setViews()

    private void setUpRecyclerView() {
        this.diaryEntryAdapter = new DiaryEntryAdapter(testDiaryInput, this);
    } // End setUpRecyclerView()
}