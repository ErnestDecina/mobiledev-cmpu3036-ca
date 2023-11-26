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
import com.ernestjohndecina.memyselfandi.model.DiaryEntryModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DiaryEntryAdapter extends RecyclerView.Adapter<DiaryEntryAdapter.ViewHolder> {
    private final ArrayList<DiaryEntryModel> diaryPost;
    private final Context context;
    // private final SnapHelper snapHelper;


    public DiaryEntryAdapter(ArrayList<DiaryEntryModel> diaryPost, Context context) {
        this.diaryPost = diaryPost;
        this.context = context;
        // this.snapHelper = new PagerSnapHelper();
    }


    @NonNull
    @Override
    public DiaryEntryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.diary_entry, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DiaryEntryAdapter.ViewHolder holder, int position) {
        // Set Image Index
//        int item = holder.imageRecyclerView.getChildViewHolder(holder.itemView).getAdapterPosition();
//        Log.d("Test", String.valueOf(item));

        // Set Address
        holder.diaryPostAddress.setText(diaryPost.get(position).address);

        // Set Caption
        holder.diaryPostCaption.setText(diaryPost.get(position).caption);

        // Set On Scroll Listeners
        holder.imageRecyclerView.addOnScrollListener(onScroll(holder));

        // Set Image Recycler View
        DiaryEntryImageAdapter diaryEntryImageAdapter = new DiaryEntryImageAdapter(diaryPost.get(position).images, context);

        // Set Horizontal Image Recycler View
        holder.imageRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.imageRecyclerView.setAdapter(diaryEntryImageAdapter);

        // Create a Snap Helper for Image Recycler View
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(holder.imageRecyclerView);
    }


    private RecyclerView.OnScrollListener onScroll(ViewHolder holder) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        };
    }


    @Override
    public int getItemCount() {
        return diaryPost.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // on below line we are creating variable.
        private final TextView diaryPostCaption;
        private final TextView diaryPostAddress;
        private final RecyclerView imageRecyclerView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // on below line we are initialing our variable.
            diaryPostCaption = itemView.findViewById(R.id.captionTextView);
            imageRecyclerView = itemView.findViewById(R.id.imageRectclerView);
            diaryPostAddress = itemView.findViewById(R.id.addressTextView);
        }
    }
}