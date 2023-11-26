package com.ernestjohndecina.memyselfandi.controller;

import android.content.Context;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.ernestjohndecina.memyselfandi.adapter.DiaryEntryAdapter;
import com.ernestjohndecina.memyselfandi.model.DiaryEntryModel;

public class DiaryEntry {
    private Context context;
    private RecyclerView recylerView;
    private DiaryEntryAdapter diaryEntryAdapter;
    private ArrayList<DiaryEntryModel> testDiaryInput;
    private LinearLayoutManager linearLayoutManager;

    public DiaryEntry(Context context, RecyclerView recylerView, ArrayList<DiaryEntryModel> testDiaryInput) {
        this.context = context;
        this.recylerView = recylerView;
        this.testDiaryInput = testDiaryInput;

        this.diaryEntryAdapter = new DiaryEntryAdapter(this.testDiaryInput, context);
        this.linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        recylerView.setLayoutManager(this.linearLayoutManager);
        this.recylerView.setAdapter(this.diaryEntryAdapter);
    } // End Constructor
}
