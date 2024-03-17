package com.example.project3android.Feed.data;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project3android.Feed.Post.Post;
import com.example.project3android.Feed.Post.PostDao;


@Database(entities = {Post.class}, version = 2, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    public abstract PostDao postDao();

    // Migration from version 2 to version 3
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Add the new column userJson to the Post table
            database.execSQL("ALTER TABLE Post ADD COLUMN userJson TEXT");

            // Add the new column author_image to the Post table
            database.execSQL("ALTER TABLE Post ADD COLUMN author_image TEXT");

            database.execSQL("ALTER TABLE Post ADD COLUMN author_name TEXT");

            database.execSQL("ALTER TABLE Post ADD COLUMN content TEXT");

            database.execSQL("ALTER TABLE Post ADD COLUMN title TEXT");

            database.execSQL("ALTER TABLE Post ADD COLUMN likes_count INTEGER NOT NULL DEFAULT 0");

            database.execSQL("ALTER TABLE Post ADD COLUMN share_count INTEGER NOT NULL DEFAULT 0");

            database.execSQL("ALTER TABLE Post ADD COLUMN commentsJson TEXT");

            database.execSQL("ALTER TABLE Post ADD COLUMN postId TEXT");

            // Convert existing data in the pic column to TEXT
            database.execSQL("CREATE TABLE IF NOT EXISTS `Post_new` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userJson` TEXT, `author_image` TEXT, `author_name` TEXT, `content` TEXT, `title` TEXT, `likes_count` INTEGER NOT NULL DEFAULT 0, `share_count` INTEGER NOT NULL DEFAULT 0, `commentsJson` TEXT, `postId` TEXT, `date` TEXT, `isLiked` INTEGER NOT NULL DEFAULT 0, `commentsSize` INTEGER NOT NULL DEFAULT 0, `pic` TEXT)");

            // Copy the data from the old table to the new one
            database.execSQL("INSERT INTO `Post_new` (`id`, `userJson`, `author_image`, `author_name`, `content`, `title`, `likes_count`, `share_count`, `commentsJson`, `postId`, `date`, `isLiked`, `commentsSize`, `pic`) SELECT `id`, `userJson`, `author_image`, `author_name`, `content`, `title`, `likes_count`, `share_count`, `commentsJson`, `postId`, `date`, `isLiked`, `commentsSize`, CAST(`pic` AS TEXT) FROM `Post`");

            // Drop the old table
            database.execSQL("DROP TABLE `Post`");

            // Rename the new table to the old one
            database.execSQL("ALTER TABLE `Post_new` RENAME TO `Post`");
        }
    };



    /*public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
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
    };*/
}