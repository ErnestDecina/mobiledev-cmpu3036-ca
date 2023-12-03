package com.ernestjohndecina.memyselfandi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.ernestjohndecina.memyselfandi.R;
import com.ernestjohndecina.memyselfandi.data.entities.PostModal;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class DiaryEntryAdapter extends RecyclerView.Adapter<DiaryEntryAdapter.ViewHolder> {
    private static List<PostModal> diaryPost = null;
    private final Context context;

    private ExecutorService executorService;
    // private final SnapHelper snapHelper;


    public DiaryEntryAdapter(List<PostModal> diaryPost, Context context, ExecutorService executorService) {
        this.diaryPost = diaryPost;
        this.context = context;
        this.executorService = executorService;
    }


    @NonNull
    @Override
    public DiaryEntryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.diary_entry, parent, false);
        return new ViewHolder(view, context, executorService);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DiaryEntryAdapter.ViewHolder holder, int position) {
        // Set Address
        holder.diaryPostAddress.setText(diaryPost.get(position).address);

        // Set Caption
        holder.diaryPostCaption.setText(diaryPost.get(position).caption);


        // Set Image Recycler View
        DiaryEntryImageAdapter diaryEntryImageAdapter = new DiaryEntryImageAdapter(
                diaryPost.get(position).imagePaths,
                context,
                executorService,
                diaryPost.get(position).postId
        );

        holder.imageRecyclerView.setAdapter(diaryEntryImageAdapter);
    }




    @Override
    public int getItemCount() {
        return diaryPost.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        //
        Context context;

        ExecutorService executorService;


        // on below line we are creating variable.
        private TextView diaryPostCaption;
        private TextView diaryPostAddress;
        private RecyclerView imageRecyclerView;
        private Boolean state = false;


        public ViewHolder(@NonNull View itemView, Context context, ExecutorService executorService) {
            super(itemView);
            //
            this.context = context;
            this.executorService = executorService;

            // Set Views
            setViews();

            // Set Horizontal Image Recycler View
            imageRecyclerView.setLayoutManager(
                    new LinearLayoutManager(
                            this.context,
                            LinearLayoutManager.HORIZONTAL,
                            false
                    )
            );

            // Set Snap Helper
            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(imageRecyclerView);
        }

        private void setViews() {
            diaryPostCaption = itemView.findViewById(R.id.captionTextView);
            imageRecyclerView = itemView.findViewById(R.id.imageRectclerView);
            diaryPostAddress = itemView.findViewById(R.id.addressTextView);
        }

    }
}