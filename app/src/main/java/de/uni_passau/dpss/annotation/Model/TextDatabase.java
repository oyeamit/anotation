package de.uni_passau.dpss.annotation.Model;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import android.content.Context;
import android.os.AsyncTask;

import de.uni_passau.dpss.annotation.Model.Text.Label;
import de.uni_passau.dpss.annotation.Model.Text.Word;
import de.uni_passau.dpss.annotation.Model.Text.TextDao;


@Database(entities = {Word.class, Label.class}, version = 1)
public abstract class TextDatabase extends RoomDatabase {

    private static TextDatabase instance;

    public abstract TextDao TextDao();

    public static synchronized TextDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TextDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private TextDao TextDao;

        private PopulateDbAsyncTask(TextDatabase db) {
            TextDao = db.TextDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            TextDao.insert(new Word("Word 1", "Class 1"));
//            TextDao.insert(new Word("Word 2", "Class 2"));
//            TextDao.insert(new Word("Word 3", "Class 3"));
//            TextDao.insert(new Word("Word 4", "Class 3"));
            return null;
        }
    }
}