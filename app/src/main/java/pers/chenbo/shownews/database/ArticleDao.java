package pers.chenbo.shownews.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import pers.chenbo.shownews.model.Article;

@Dao
public interface ArticleDao {

    @Insert
    void saveArticle(Article article);

    @Query("SELECT * FROM article")
    LiveData<List<Article>> getAllArticle();

    @Delete
    void deleteArticle(Article article);
}
