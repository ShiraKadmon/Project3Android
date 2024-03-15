/*
package com.example.project3android.Feed;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.Post.PostDao;

@Database(entities = {Post.class}, version = 2, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    public abstract PostDao postDao();


    // Migration from version 1 to version 2
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // SQLite doesn't support adding columns with NOT NULL constraint, so add the columns without it
            database.execSQL("ALTER TABLE post ADD COLUMN user_id TEXT");
            database.execSQL("ALTER TABLE post ADD COLUMN author_image TEXT");
            database.execSQL("ALTER TABLE post ADD COLUMN title TEXT");
            database.execSQL("ALTER TABLE post ADD COLUMN likes_count INTEGER NOT NULL DEFAULT 0");
            database.execSQL("ALTER TABLE post ADD COLUMN share_count INTEGER NOT NULL DEFAULT 0");
            database.execSQL("ALTER TABLE post ADD COLUMN commentsJson TEXT");
            database.execSQL("ALTER TABLE post ADD COLUMN postId TEXT");
        }
    };
}
*/
