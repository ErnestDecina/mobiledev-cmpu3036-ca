package com.ernestjohndecina.memyselfandi.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;

import com.ernestjohndecina.memyselfandi.adapter.DiaryEntryAdapter;
import com.ernestjohndecina.memyselfandi.data.entities.PostModal;

public class DiaryEntryController {
    private Context context;
    private ExecutorService executorService;
    private Handler mainHandler;
    private RecyclerView recylerView;
    private DiaryEntryAdapter diaryEntryAdapter;
    private List<PostModal> testDiaryInput;
    private LinearLayoutManager linearLayoutManager;

    public DiaryEntryController(Context context, ExecutorService executorService, RecyclerView recylerView, List<PostModal> testDiaryInput) {
        this.context = context;
        this.executorService = executorService;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.recylerView = recylerView;
        this.testDiaryInput = testDiaryInput;

        // Create Adapter
        this.diaryEntryAdapter = new DiaryEntryAdapter(this.testDiaryInput, context, executorService);

        // Layout Manager
        this.linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        this.linearLayoutManager.setReverseLayout(true);
        this.linearLayoutManager.setStackFromEnd(true);

        // Set Recycler View
        recylerView.setLayoutManager(this.linearLayoutManager);
        this.recylerView.setHasFixedSize(true);
        this.recylerView.setItemViewCacheSize(5);
        this.recylerView.setAdapter(this.diaryEntryAdapter);
    } // End Constructor

    @SuppressLint("NotifyDataSetChanged")
    public void updateAdataper() {
//        context.getMainExecutor().execute(() -> {
//            this.diaryEntryAdapter.notifyItemInserted(testDiaryInput.size() + 1);
//        });

        mainHandler.post(() -> {
            this.diaryEntryAdapter.notifyItemInserted(testDiaryInput.size() + 1);
        });
    }


    public void addArrayList(PostModal post) {
        this.testDiaryInput.add(post);
    }
}
