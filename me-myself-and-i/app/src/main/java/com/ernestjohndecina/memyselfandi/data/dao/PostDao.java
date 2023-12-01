package com.ernestjohndecina.memyselfandi.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.ernestjohndecina.memyselfandi.data.entities.PostModal;

import java.util.List;

@Dao
public interface PostDao {
    @Query("SELECT * FROM posts")
    List<PostModal> getAllPosts();

    @Query("SELECT * FROM posts WHERE user_id = :userId")
    List<PostModal> getPostByUserId(int userId);

    @Query("SELECT MAX(post_id) FROM posts ")
    Integer getLatestPostId();

    @Query("SELECT * FROM posts ORDER BY post_id DESC LIMIT 1;")
    PostModal getLatestPost();

    @Insert
    void insertUser(PostModal post);

    @Delete
    void delete(PostModal post);
}
