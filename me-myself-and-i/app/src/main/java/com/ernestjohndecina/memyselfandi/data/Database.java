package com.ernestjohndecina.memyselfandi.data;

import android.content.Context;

import androidx.room.Room;

import com.ernestjohndecina.memyselfandi.data.dao.PostDao;
import com.ernestjohndecina.memyselfandi.data.entities.PostModal;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Database {

    private Context context;
    private ExecutorService executorService;

    // Database
    private AppDatabaseAbstract database;

    // Data Access Object
    public PostDao userDao;


    public Database(Context context, ExecutorService executorService) {
        this.context = context;
        this.executorService = executorService;

        createDatabase();
        createPostDao();


    } // End Database Constructor

    void createDatabase() {
        database = Room.databaseBuilder(
                context,
                AppDatabaseAbstract.class,
                "me-myself-and-i-database-test"
        ).build();
    } // End createDatabase()

    void createPostDao() {
        userDao = database.userDao();
    } // End createPostDao()

    //
    // Posts
    //
    public void insertPost(PostModal post) {
        executorService.execute(() -> userDao.insertUser(post));
    }

    public Future<List<PostModal>> selectAllPosts() {
        return  executorService.submit(()-> userDao.getAllPosts());
    }

    public Future<List<PostModal>> selectAllPostByUserId(int userId) {
        return  executorService.submit(()-> userDao.getPostByUserId(userId));
    }

    public Future<Integer> getLatestId() {
        return  executorService.submit(()-> userDao.getLatestPostId());
    }

    public Future<PostModal> getLatestPost() {
        return  executorService.submit(() -> userDao.getLatestPost());
    }
}
