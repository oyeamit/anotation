package de.uni_passau.dpss.annotation.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import de.uni_passau.dpss.annotation.Model.TextDatabase;
import de.uni_passau.dpss.annotation.Model.Text.TextDao;
import de.uni_passau.dpss.annotation.Model.Text.Word;

public class NoteRepository {
    private TextDao textDao;
    private LiveData<List<Word>> allLabelNotes;
    private LiveData<List<String>> allWordLabels;
    private LiveData<List<Word>> allWordNotes;


    public NoteRepository(Application application) {
        TextDatabase database = TextDatabase.getInstance(application);
        textDao = database.TextDao();
        allLabelNotes = textDao.getAllWords();
        allWordLabels = textDao.getAllWordLabels();

    }


    public void insert(Word word) {
        new InsertNoteAsyncTask(textDao).execute(word);
    }

    public void update(Word word) {
        new UpdateNoteAsyncTask(textDao).execute(word);
    }

    public void delete(Word word) {
        new DeleteNoteAsyncTask(textDao).execute(word);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(textDao).execute();
    }

    public LiveData<List<Word>> getAllNotes() {
        return allLabelNotes;
    }

    public LiveData<List<String>> getAllWordLabels() {
        return allWordLabels;
    }

    public LiveData<List<Word>> getLabelWordsNotes(String selectedLabel) {
        return textDao.getLabelWords(selectedLabel);
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Word, Void, Void> {
        private TextDao textDao;

        private InsertNoteAsyncTask(TextDao textDao) {
            this.textDao = textDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            textDao.insert(words[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Word, Void, Void> {
        private TextDao textDao;

        private UpdateNoteAsyncTask(TextDao textDao) {
            this.textDao = textDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            textDao.update(words[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Word, Void, Void> {
        private TextDao textDao;

        private DeleteNoteAsyncTask(TextDao textDao) {
            this.textDao = textDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            textDao.delete(words[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private TextDao textDao;

        private DeleteAllNotesAsyncTask(TextDao textDao) {
            this.textDao = textDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            textDao.deleteAllWords();
            return null;
        }
    }
}