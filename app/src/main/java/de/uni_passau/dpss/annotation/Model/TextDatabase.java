package de.uni_passau.dpss.annotation.Model;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import android.content.Context;
import android.os.AsyncTask;

import de.uni_passau.dpss.annotation.Model.Text.Word;
import de.uni_passau.dpss.annotation.Model.Text.NoteTextDao;


@Database(entities = {Word.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteTextDao noteTextDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
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
        private NoteTextDao noteTextDao;

        private PopulateDbAsyncTask(NoteDatabase db) {
            noteTextDao = db.noteTextDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteTextDao.insert(new Word("Word 1", "Class 1"));
            noteTextDao.insert(new Word("Word 2", "Class 2"));
            noteTextDao.insert(new Word("Word 3", "Class 3"));
            noteTextDao.insert(new Word("Word 4", "Class 3"));
            return null;
        }
    }
}