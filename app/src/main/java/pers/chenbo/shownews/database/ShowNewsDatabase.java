package pers.chenbo.shownews.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import pers.chenbo.shownews.model.Article;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class ShowNewsDatabase extends RoomDatabase {
    public abstract ArticleDao articleDao();
}
