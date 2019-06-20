package de.uni_passau.dpss.annotation.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import de.uni_passau.dpss.annotation.Model.NoteDatabase;
import de.uni_passau.dpss.annotation.Model.Text.NoteText;
import de.uni_passau.dpss.annotation.Model.Text.NoteTextDao;

public class NoteRepository {
    private NoteTextDao noteTextDao;
    private LiveData<List<NoteText>> allLabelNotes;
    private LiveData<List<String>> allWordLabels;
    private LiveData<List<NoteText>> allWordNotes;


    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteTextDao = database.noteTextDao();
        allLabelNotes = noteTextDao.getAllWordNotes();
        allWordLabels = noteTextDao.getAllWordLabels();
    }


    public void insert(NoteText noteText) {
        new InsertNoteAsyncTask(noteTextDao).execute(noteText);
    }

    public void update(NoteText noteText) {
        new UpdateNoteAsyncTask(noteTextDao).execute(noteText);
    }

    public void delete(NoteText noteText) {
        new DeleteNoteAsyncTask(noteTextDao).execute(noteText);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(noteTextDao).execute();
    }

    public LiveData<List<NoteText>> getAllNotes() {
        return allLabelNotes;
    }

    public LiveData<List<String>> getAllWordLabels() {
        return allWordLabels;
    }

    public LiveData<List<NoteText>> getLabelWordsNotes(String selectedLabel) {
        return noteTextDao.getLabelWordsNotes(selectedLabel);
    }

    private static class InsertNoteAsyncTask extends AsyncTask<NoteText, Void, Void> {
        private NoteTextDao noteTextDao;

        private InsertNoteAsyncTask(NoteTextDao noteTextDao) {
            this.noteTextDao = noteTextDao;
        }

        @Override
        protected Void doInBackground(NoteText... noteTexts) {
            noteTextDao.insert(noteTexts[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<NoteText, Void, Void> {
        private NoteTextDao noteTextDao;

        private UpdateNoteAsyncTask(NoteTextDao noteTextDao) {
            this.noteTextDao = noteTextDao;
        }

        @Override
        protected Void doInBackground(NoteText... noteTexts) {
            noteTextDao.update(noteTexts[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<NoteText, Void, Void> {
        private NoteTextDao noteTextDao;

        private DeleteNoteAsyncTask(NoteTextDao noteTextDao) {
            this.noteTextDao = noteTextDao;
        }

        @Override
        protected Void doInBackground(NoteText... noteTexts) {
            noteTextDao.delete(noteTexts[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteTextDao noteTextDao;

        private DeleteAllNotesAsyncTask(NoteTextDao noteTextDao) {
            this.noteTextDao = noteTextDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteTextDao.deleteAllWordNotes();
            return null;
        }
    }
}