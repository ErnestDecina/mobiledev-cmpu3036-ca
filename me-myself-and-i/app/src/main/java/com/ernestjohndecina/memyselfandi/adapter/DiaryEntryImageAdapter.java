package com.ernestjohndecina.memyselfandi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ernestjohndecina.memyselfandi.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DiaryEntryImageAdapter extends RecyclerView.Adapter<DiaryEntryImageAdapter.ViewHolder> {
    private final ArrayList<Integer> diaryPost;
    private final Context context;


    public DiaryEntryImageAdapter(ArrayList<Integer> diaryPost, Context context) {
        this.diaryPost = diaryPost;
        this.context = context;
    }


    @NonNull
    @Override
    public DiaryEntryImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.diary_entry_image, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DiaryEntryImageAdapter.ViewHolder holder, int position) {
        // Drawable image = ContextCompat.getDrawable(context, R.drawable.image_0);
        // holder.getAdapterPosition();
        holder.indexNumberTextView.setText( (holder.getAdapterPosition() + 1) + "/" + getItemCount());
        holder.diaryPostImage.setImageResource(diaryPost.get(position));
    }


    @Override
    public int getItemCount() {
        return diaryPost.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // on below line we are creating variable.
        private final ImageView diaryPostImage;
        private TextView indexNumberTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            diaryPostImage = itemView.findViewById(R.id.imageView);
            indexNumberTextView = itemView.findViewById(R.id.indexNumberTextView);
        }
    }
}