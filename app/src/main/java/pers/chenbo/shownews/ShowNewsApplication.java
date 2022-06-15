package pers.chenbo.shownews;

import android.app.Application;

import androidx.room.Room;

import pers.chenbo.shownews.database.ShowNewsDatabase;

public class ShowNewsApplication extends Application {

    private static ShowNewsDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, ShowNewsDatabase.class, "shownews_db").build();
    }

    public static ShowNewsDatabase getDatabase() {
        return database;
    }
}
