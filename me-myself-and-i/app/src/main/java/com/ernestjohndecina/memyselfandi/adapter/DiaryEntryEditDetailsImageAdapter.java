package com.ernestjohndecina.memyselfandi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ernestjohndecina.memyselfandi.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class DiaryEntryEditDetailsImageAdapter extends RecyclerView.Adapter<DiaryEntryEditDetailsImageAdapter.ViewHolder> {
    private final ArrayList<Uri> uriArrayList;
    private final Context context;
    public DiaryEntryEditDetailsImageAdapter(ArrayList<Uri> uriArrayList, Context context) {
        this.uriArrayList = uriArrayList;
        this.context = context;
    }


    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.diary_entry_image, parent, false);

        ViewHolder viewHolder = new ViewHolder(view , context);
        return viewHolder;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setText( (position + 1) + "/" + getItemCount() );

        Bitmap image = null;
        try {
            image = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uriArrayList.get(position));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        holder.setImage(image);
    }

    @Override
    public int getItemCount() {
        return uriArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // on below line we are creating variable.
        private final ImageView diaryPostImage;
        private TextView indexNumberTextView;
        private Context context;
        Handler mainHander;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            this.mainHander = new Handler(Looper.getMainLooper());

            diaryPostImage = itemView.findViewById(R.id.imageView);
            indexNumberTextView = itemView.findViewById(R.id.indexNumberTextView);
        }

        public void setImage(Bitmap image) {
            context.getMainExecutor().execute(() -> {
                diaryPostImage.setImageBitmap(image);
            });
        }

        public void setText(String text) {
            context.getMainExecutor().execute(()-> {
                indexNumberTextView.setText(text);
            });
        }
    }
}