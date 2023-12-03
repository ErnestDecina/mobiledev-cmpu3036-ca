package com.ernestjohndecina.memyselfandi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ernestjohndecina.memyselfandi.R;
import com.ernestjohndecina.memyselfandi.data.entities.PostModal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class DiaryEntryImageGridAdapter extends ArrayAdapter<PostModal> {
    ExecutorService executorService;
    Handler mainHandler;

    public DiaryEntryImageGridAdapter(
            @NonNull Context context,
            ExecutorService executorService,
            List<PostModal> courseModelArrayList
    ) {
        super(context, 0, courseModelArrayList);

        this.executorService = executorService;
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        @SuppressLint("ViewHolder") View listitemView = LayoutInflater.from(getContext()).inflate(R.layout.diary_entry_image_grid, parent, false);
        listitemView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 400));
        Log.d("DEBUG GRID", String.valueOf(position));
        executorService.execute(() -> {
            ImageView courseIV = listitemView.findViewById(R.id.imageView);
            Drawable image = Drawable.createFromPath("/storage/emulated/0/Android/data/com.ernestjohndecina.memyselfandi/files/test/posts/posts_" + (position + 2) + "/image_0.jpeg");

            mainHandler.post(() -> {
                courseIV.setImageDrawable(image);
            });
        });

        return listitemView;

    }
}