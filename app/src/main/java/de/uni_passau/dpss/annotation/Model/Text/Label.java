package de.uni_passau.dpss.annotation.Model.Text;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "label_table")
public class Label implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int label_id;

    private String label;

    @Ignore
    public Label(){

    }

    public Label(String label) {
        this.label = label;
    }


    protected Label(Parcel in) {
        label_id = in.readInt();
        label = in.readString();
    }

    public static final Creator<Label> CREATOR = new Creator<Label>() {
        @Override
        public Label createFromParcel(Parcel in) {
            return new Label(in);
        }

        @Override
        public Label[] newArray(int size) {
            return new Label[size];
        }
    };


    public void setLabel_id(int label_id) {
        this.label_id = label_id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public int getLabel_id() {
        return label_id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(label_id);
        parcel.writeString(label);
    }


}
