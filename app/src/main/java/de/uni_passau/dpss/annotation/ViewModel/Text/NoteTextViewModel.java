package de.uni_passau.dpss.annotation.ViewModel.Text;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import android.app.Application;


import java.util.List;

import de.uni_passau.dpss.annotation.Repository.NoteRepository;
import de.uni_passau.dpss.annotation.Model.Text.NoteText;

public class NoteTextViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<NoteText>> allWordNotes;
    private LiveData<List<String>> allWordLabels;
    private LiveData<List<NoteText>> allLabelWords;



    public NoteTextViewModel(@NonNull Application application){
        super(application);
        repository = new NoteRepository(application);
        allWordNotes = repository.getAllNotes();
        allWordLabels = repository.getAllWordLabels();
    }



    public void insert(NoteText noteText) {
        repository.insert(noteText);
    }

    public void update(NoteText noteText) {
        repository.update(noteText);
    }

    public void delete(NoteText noteText) {
        repository.delete(noteText);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<NoteText>> getAllNotes() {
        return allWordNotes;
    }

    public LiveData<List<String>> getAllWordLabels(){
        return allWordLabels;
    }




    public LiveData<List<NoteText>> getLabelWordsNotes(String selectedLabel) {
        return repository.getLabelWordsNotes(selectedLabel);
    }
}