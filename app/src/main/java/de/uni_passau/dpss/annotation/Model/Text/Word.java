package de.uni_passau.dpss.annotation.Model.Text;


import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String word;

    public int getLabel_id() {
        return label_id;
    }

    public void setLabel_id(int label_id) {
        this.label_id = label_id;
    }

    private int label_id;



    public Word(String word, int label_id) {
        this.word = word;
        this.label_id = label_id;
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





}