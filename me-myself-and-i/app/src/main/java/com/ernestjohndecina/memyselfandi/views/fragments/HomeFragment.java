package com.ernestjohndecina.memyselfandi.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ernestjohndecina.memyselfandi.R;
import com.ernestjohndecina.memyselfandi.controller.DiaryEntryController;
import com.ernestjohndecina.memyselfandi.data.entities.PostModal;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class HomeFragment extends Fragment {
    ExecutorService executorService;
    List<PostModal> diaryInput;
    DiaryEntryController diaryEntry;


    public HomeFragment() {}

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    )
    {

        View homeFragment = inflater.inflate(R.layout.fragment_home, container, false);
        setRecyclerView(homeFragment);
        setRefreshButton(homeFragment);

        return homeFragment;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setDiaryInput(List<PostModal> diaryInput) {
        this.diaryInput = diaryInput;
    }

    private void setRecyclerView(View homeFragment) {
        RecyclerView recylerView = (RecyclerView) homeFragment.findViewById(R.id.diaryPost);

        try {
            diaryEntry = new DiaryEntryController(getContext(), executorService, recylerView, diaryInput);
        } catch (Exception error) {
            Log.e("Error", error.getMessage());
        }
    }

    private void setRefreshButton(View homeFragment) {
        SwipeRefreshLayout swipeRefreshLayout = homeFragment.findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            diaryEntry.updateAdataper();
            swipeRefreshLayout.setRefreshing(false);
        });
    }
}