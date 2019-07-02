package de.uni_passau.dpss.annotation.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import de.uni_passau.dpss.annotation.Model.Text.Label;
import de.uni_passau.dpss.annotation.Model.TextDatabase;
import de.uni_passau.dpss.annotation.Model.Text.TextDao;
import de.uni_passau.dpss.annotation.Model.Text.Word;

public class TextRepository {
    private TextDao textDao;
    private LiveData<List<Label>> all_word_label;



    public TextRepository(Application application) {
        TextDatabase database = TextDatabase.getInstance(application);
        textDao = database.TextDao();
        all_word_label = textDao.getAllLabels();

    }


    public void insert(Word word) {
        new InsertWordAsyncTask(textDao).execute(word);
    }

    public void update(Word word) {
        new UpdateWordAsyncTask(textDao).execute(word);
    }

    public void delete(Word word) {
        new DeleteWordAsyncTask(textDao).execute(word);
    }

    public void deleteAllWords() {
        new DeleteAllWordsAsyncTask(textDao).execute();
    }

    public void deleteLabelWords(Label label) {
        new DeleteLabelWordsAsyncTask(textDao).execute(label);

    }

    public LiveData<List<Word>> getAllWords() {
        return textDao.getAllWords();
    }

    public LiveData<List<Word>> getLabelWords(int selected_label_id) {
        return textDao.getLabelWords(selected_label_id);
    }




    private static class InsertWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private TextDao textDao;

        private InsertWordAsyncTask(TextDao textDao) {
            this.textDao = textDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            textDao.insert(words[0]);
            return null;
        }
    }

    private static class UpdateWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private TextDao textDao;

        private UpdateWordAsyncTask(TextDao textDao) {
            this.textDao = textDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            textDao.update(words[0]);
            return null;
        }
    }

    private static class DeleteWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private TextDao textDao;

        private DeleteWordAsyncTask(TextDao textDao) {
            this.textDao = textDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            textDao.delete(words[0]);
            return null;
        }
    }

    private static class DeleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private TextDao textDao;

        private DeleteAllWordsAsyncTask(TextDao textDao) {
            this.textDao = textDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            textDao.deleteAllWords();
            return null;
        }
    }

    private static class DeleteLabelWordsAsyncTask extends AsyncTask<Label, Void, Void> {
        private TextDao textDao;

        private DeleteLabelWordsAsyncTask(TextDao textDao) {
            this.textDao = textDao;
        }

        @Override
        protected Void doInBackground(Label... labels) {
            textDao.deleteLabelWords(labels[0].getLabel_id());
            return null;
        }
    }






    public LiveData<List<Label>> getAllLabels(){
        return textDao.getAllLabels();
    }

    public void insert(Label label) {
        new InsertLabelAsyncTask(textDao).execute(label);
    }

    public void update(Label label) {
        new UpdateLabelAsyncTask(textDao).execute(label);
    }

    public void delete(Label label) {
        new DeleteLabelAsyncTask(textDao).execute(label);
    }

    public void deleteAllLabels() {
        new DeleteAllLabelsAsyncTask(textDao).execute();
    }




    private static class InsertLabelAsyncTask extends AsyncTask<Label, Void, Void> {
        private TextDao textDao;

        private InsertLabelAsyncTask(TextDao textDao) {
            this.textDao = textDao;
        }

        @Override
        protected Void doInBackground(Label... labels) {
            textDao.insert(labels[0]);
            return null;
        }
    }

    private static class UpdateLabelAsyncTask extends AsyncTask<Label, Void, Void> {
        private TextDao textDao;

        private UpdateLabelAsyncTask(TextDao textDao) {
            this.textDao = textDao;
        }

        @Override
        protected Void doInBackground(Label... labels) {
            textDao.update(labels[0]);
            return null;
        }
    }

    private static class DeleteLabelAsyncTask extends AsyncTask<Label, Void, Void> {
        private TextDao textDao;

        private DeleteLabelAsyncTask(TextDao textDao) {
            this.textDao = textDao;
        }

        @Override
        protected Void doInBackground(Label... labels) {
            textDao.delete(labels[0]);
            textDao.deleteLabelWords(labels[0].getLabel_id());
            return null;
        }
    }

    private static class DeleteAllLabelsAsyncTask extends AsyncTask<Void, Void, Void> {
        private TextDao textDao;

        private DeleteAllLabelsAsyncTask(TextDao textDao) {
            this.textDao = textDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            textDao.deleteAllLabels();
            textDao.deleteAllWords();
            return null;
        }
    }


}