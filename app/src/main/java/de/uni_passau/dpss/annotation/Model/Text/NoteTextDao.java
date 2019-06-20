package de.uni_passau.dpss.annotation.Model.Text;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteTextDao {

    @Insert
    void insert(NoteText noteText);

    @Update
    void update(NoteText noteText);

    @Delete
    void delete(NoteText noteText);

    @Query("DELETE FROM note_text_table")
    void deleteAllWordNotes();

    @Query("SELECT * FROM note_text_table ORDER BY word ASC")
    LiveData<List<NoteText>> getAllWordNotes();

    @Query("SELECT DISTINCT label FROM note_text_table ORDER BY label ASC")
    LiveData<List<String>> getAllWordLabels();

    @Query("SELECT * FROM note_text_table WHERE label = :selectedLabel")
    LiveData<List<NoteText>> getLabelWordsNotes(String selectedLabel);
}