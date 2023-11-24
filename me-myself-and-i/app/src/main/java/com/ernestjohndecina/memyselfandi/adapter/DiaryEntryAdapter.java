package com.ernestjohndecina.memyselfandi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ernestjohndecina.memyselfandi.R;

import java.util.ArrayList;

public class DiaryEntryAdapter extends RecyclerView.Adapter<DiaryEntryAdapter.ViewHolder> {
    private ArrayList<String> languageRVModalArrayList;
    private Context context;

    public DiaryEntryAdapter(ArrayList<String> languageRVModalArrayList, Context context) {
        this.languageRVModalArrayList = languageRVModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DiaryEntryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.diary_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryEntryAdapter.ViewHolder holder, int position) {
        // on below line we are setting text to our text view
        holder.diaryPost.setText(languageRVModalArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return languageRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // on below line we are creating variable.
        private TextView diaryPost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // on below line we are initialing our variable.
            diaryPost = itemView.findViewById(R.id.textView);
        }
    }
}