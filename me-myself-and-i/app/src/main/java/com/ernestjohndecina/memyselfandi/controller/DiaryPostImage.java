package com.ernestjohndecina.memyselfandi.controller;

import android.content.Context;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;

import com.ernestjohndecina.memyselfandi.adapter.DiaryEntryImageAdapter;
import com.google.android.material.carousel.CarouselSnapHelper;

public class DiaryPostImage {
    private Context context;
    private RecyclerView recylerView;
    private DiaryEntryImageAdapter diaryEntryAdapter;
    private ArrayList<Integer> testDiaryInput;
    private SnapHelper snapHelper;
    private LinearLayoutManager linearLayoutManager;

    public DiaryPostImage(Context context, RecyclerView recylerView, ArrayList<Integer> testDiaryInput) {
        this.context = context;
        this.recylerView = recylerView;
        this.testDiaryInput = testDiaryInput;
        this.snapHelper = new CarouselSnapHelper();
        this.diaryEntryAdapter = new DiaryEntryImageAdapter(this.testDiaryInput, context);
        this.linearLayoutManager = new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false);

        recylerView.setLayoutManager(this.linearLayoutManager);
        this.recylerView.setAdapter(this.diaryEntryAdapter);
    } // End Constructor
}
