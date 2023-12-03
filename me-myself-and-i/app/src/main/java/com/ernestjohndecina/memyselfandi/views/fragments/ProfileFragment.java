package com.ernestjohndecina.memyselfandi.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.ernestjohndecina.memyselfandi.R;
import com.ernestjohndecina.memyselfandi.adapter.DiaryEntryImageGridAdapter;
import com.ernestjohndecina.memyselfandi.data.entities.PostModal;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    GridView gridView;
    List<PostModal> diaryInput;
    ExecutorService executorService;
    Handler mainHandler;

    public ProfileFragment() {}

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        gridView = view.findViewById(R.id.diaryPostGridyView);

        DiaryEntryImageGridAdapter diaryEntryImageGridAdapter = new DiaryEntryImageGridAdapter(
                getContext(),
                executorService,
                diaryInput
        );


        gridView.setLayoutDirection( GridView.LAYOUT_DIRECTION_LTR );
        gridView.setAdapter(diaryEntryImageGridAdapter);

        return view;
    }

    public void setDiaryInput(List<PostModal> diaryInput) {
        this.diaryInput = diaryInput;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}