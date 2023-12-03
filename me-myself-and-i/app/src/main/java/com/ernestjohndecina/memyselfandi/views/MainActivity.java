package com.ernestjohndecina.memyselfandi.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.ernestjohndecina.memyselfandi.controller.MainLayoutController;
import com.ernestjohndecina.memyselfandi.R;
import com.ernestjohndecina.memyselfandi.controller.DiaryEntryController;
import com.ernestjohndecina.memyselfandi.data.Database;
import com.ernestjohndecina.memyselfandi.data.entities.PostModal;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    // Variables
    private List<PostModal> testDiaryInput;
    private DiaryEntryController diaryEntry;

    private ExecutorService executorService;

    private Database database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // setFragmentManager();
        createDirectory();
        setThreadService();
        setDatabase();
        loadDiaryPosts();
        setViews();
    }


    private void createDirectory() {
        try {
            getExternalFilesDir(null);
            String root = getExternalFilesDir("").getAbsolutePath();//get external storage
            Log.d("Debug", root);

            File myDir = new File(root + "/test"+"/posts");//create directory and subfolder
            File dir= new File(root + "/test"+"/users"); //create subfolder

            if(myDir.exists()) {
                Log.d("Debug", "Folder /test/posts Exists");
                return;
            };

            if(dir.exists()) {
                Log.d("Debug", "Folder /test/users Exists");
                return;
            };

            myDir.mkdirs();
            dir.mkdirs();
        } catch (Exception e) {
            Log.d("Debug", e.toString());
        }

    }

    private void setThreadService() {
        executorService = new ThreadPoolExecutor(
                4,
                10,
                5L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>()
        );
    }

    private void setDatabase() {
        database = new Database(
                this.getApplicationContext(),
                executorService
        );
    }

    public void loadDiaryPosts() {
        try {
            testDiaryInput = database.selectAllPosts().get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void setViews() {
        Button homeButton = (Button) findViewById(R.id.homeButton);
        Button profileButton = (Button) findViewById(R.id.profileButton);
        Button createPostButton = (Button) findViewById(R.id.createPostButton);

        // Private Variables
        MainLayoutController mainLayout = new MainLayoutController(
                this,
                executorService,
                database,
                homeButton,
                createPostButton,
                profileButton,
                testDiaryInput
        );

    } // End setViews()
}