package com.ernestjohndecina.memyselfandi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ernestjohndecina.memyselfandi.R;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class DiaryEntryImageAdapter extends RecyclerView.Adapter<DiaryEntryImageAdapter.ViewHolder> {
    private final ArrayList<String> diaryPost;
    private final Context context;
    private ExecutorService executorService;


    private final Integer index;
    private  Integer position = 0;



    public DiaryEntryImageAdapter(ArrayList<String> diaryPost, Context context, ExecutorService executorService, Integer index) {
        this.diaryPost = diaryPost;
        this.context = context;
        this.executorService = executorService;
        this.index = index;

    }


    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public DiaryEntryImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.diary_entry_image, parent, false);

        ViewHolder viewHolder = new ViewHolder(view , context);
        return viewHolder;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DiaryEntryImageAdapter.ViewHolder holder, int position) {
        executorService.execute(() -> {
            Log.d("DEBUG", String.valueOf(index));
            holder.setText( (position + 1) + "/" + getItemCount() );
            BitmapDrawable image = (BitmapDrawable) Drawable.createFromPath("/storage/emulated/0/Android/data/com.ernestjohndecina.memyselfandi/files/test/posts/posts_" + (index + 1) + "/image_"+ (position) +".jpeg");
            holder.setImage(image);
        });
    }

    @Override
    public int getItemCount() {
        return diaryPost.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // on below line we are creating variable.
        private final ImageView diaryPostImage;
        private TextView indexNumberTextView;
        private Boolean state = false;
        private Context context;
        Handler mainHander;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            this.mainHander = new Handler(Looper.getMainLooper());

            diaryPostImage = itemView.findViewById(R.id.imageView);
            indexNumberTextView = itemView.findViewById(R.id.indexNumberTextView);
        }

        public void setImage(BitmapDrawable image) {
            Bitmap thumbImage = ThumbnailUtils.extractThumbnail(
                    image.getBitmap(),
                    500,
                    500
            );

            context.getMainExecutor().execute(() -> {
                diaryPostImage.setImageBitmap(thumbImage);
            });
        }

        public void setText(String text) {
            context.getMainExecutor().execute(()-> {
                indexNumberTextView.setText(text);
            });
        }
    }
}