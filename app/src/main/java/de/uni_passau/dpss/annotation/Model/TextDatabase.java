package de.uni_passau.dpss.annotation.Model;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import android.content.Context;
import android.os.AsyncTask;

import de.uni_passau.dpss.annotation.Model.Image.ImageObject;
import de.uni_passau.dpss.annotation.Model.Text.Label;
import de.uni_passau.dpss.annotation.Model.Text.Word;


@Database(entities = {Word.class, Label.class, ImageObject.class}, version = 3)
public abstract class TextDatabase extends RoomDatabase {

    private static TextDatabase instance;

    public abstract AnnotationDao TextDao();

    public static synchronized TextDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TextDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback).allowMainThreadQueries()
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
        private AnnotationDao AnnotationDao;

        private PopulateDbAsyncTask(TextDatabase db) {
            AnnotationDao = db.TextDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            AnnotationDao.insert(new Word("Word 1", "Class 1"));
//            AnnotationDao.insert(new Word("Word 2", "Class 2"));
//            AnnotationDao.insert(new Word("Word 3", "Class 3"));
//            AnnotationDao.insert(new Word("Word 4", "Class 3"));
            return null;
        }
    }
}