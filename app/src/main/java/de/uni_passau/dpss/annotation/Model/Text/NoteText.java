package de.uni_passau.dpss.annotation.Model.Text;


import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "note_text_table")
public class NoteText {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String word;

    private String label;



    public NoteText(String word, String label) {
        this.word = word;
        this.label = label;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getLabel() {
        return label;
    }



}