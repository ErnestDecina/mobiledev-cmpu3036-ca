package com.ernestjohndecina.memyselfandi.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ernestjohndecina.memyselfandi.data.converters.PostConverter;
import com.ernestjohndecina.memyselfandi.data.dao.PostDao;
import com.ernestjohndecina.memyselfandi.data.entities.PostModal;

@Database(
        entities = {PostModal.class},
        version = 1
)
@TypeConverters({PostConverter.class})
public abstract class AppDatabaseAbstract extends RoomDatabase {
    public abstract PostDao userDao();
}
