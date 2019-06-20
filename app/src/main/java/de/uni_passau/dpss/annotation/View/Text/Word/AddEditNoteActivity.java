package de.uni_passau.dpss.annotation.View.Text.Word;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import de.uni_passau.dpss.annotation.R;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "de.uni_passau.dpss.annotation.EXTRA_ID";
    public static final String EXTRA_WORD =
            "de.uni_passau.dpss.annotation.EXTRA_WORD";
    public static final String EXTRA_LABEL =
            "de.uni_passau.dpss.annotation.EXTRA_LABEL";

    private EditText editTextWord;
    private EditText editTextLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextWord = findViewById(R.id.edit_text_word);
        editTextLabel = findViewById(R.id.edit_text_label);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note Word");
            editTextWord.setText(intent.getStringExtra(EXTRA_WORD));
            editTextLabel.setText(intent.getStringExtra(EXTRA_LABEL));
        }else{
            setTitle("Add Note Word");
        }


    }

    private void saveNote() {
        String title = editTextWord.getText().toString();
        String description = editTextLabel.getText().toString();


        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a Word and Label", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_WORD, title);
        data.putExtra(EXTRA_LABEL, description);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        if (id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}