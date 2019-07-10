package de.uni_passau.dpss.annotation.Model;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.uni_passau.dpss.annotation.Model.Image.ImageObject;
import de.uni_passau.dpss.annotation.Model.Text.Label;
import de.uni_passau.dpss.annotation.Model.Text.Word;

@androidx.room.Dao
public interface AnnotationDao {

    @Insert
    void insert(Word word);

    @Update
    void update(Word word);

    @Delete
    void delete(Word word);

    @Query("DELETE FROM word_table")
    void deleteAllWords();

    @Query("SELECT * FROM word_table ORDER BY LOWER(word) ASC")
    LiveData<List<Word>> getAllWords();


    @Query("SELECT * FROM word_table WHERE label_id = :selected_label_id ORDER BY LOWER(word) ASC")
    LiveData<List<Word>> getLabelWords(int selected_label_id);

    @Query("DELETE FROM word_table WHERE label_id = :selected_label_id")
    void deleteLabelWords(int selected_label_id);




    @Query("SELECT * FROM label_table ORDER BY LOWER(label) ASC")
    LiveData<List<Label>> getAllLabels();

    @Insert
    void insert(Label label);

    @Update
    void update(Label label);

    @Delete
    void delete(Label label);

    @Query("DELETE FROM label_table")
    void deleteAllLabels();



    @Insert
    void insert(ImageObject imageObject);

    @Update
    void update(ImageObject imageObject);

    @Delete
    void delete(ImageObject imageObject);

    @Query("DELETE FROM image_table")
    void deleteAllImageObjects();

    @Query("SELECT * FROM image_table ORDER BY id ASC")
    LiveData<List<ImageObject>> getAllLiveImageObjects();

    @Query("SELECT * FROM image_table ORDER BY id ASC")
    List<ImageObject> getAllImageObjects();



}