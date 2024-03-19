package com.example.project3android.User.Data;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project3android.User.User;
import com.example.project3android.User.UserDao;

@Database(entities = {User.class}, version = 2)
public abstract class UserAppDB extends RoomDatabase {
    public abstract UserDao userDao();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE User ADD COLUMN _id TEXT");        }
    };
}
