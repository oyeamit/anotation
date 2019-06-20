package de.uni_passau.dpss.annotation.View.Text.Word;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import de.uni_passau.dpss.annotation.Model.Text.NoteText;
import de.uni_passau.dpss.annotation.ViewModel.Text.NoteTextViewModel;
import de.uni_passau.dpss.annotation.R;

public class TextWordActivity extends AppCompatActivity {
    public static final String EXTRA_LABEL =
            "de.uni_passau.dpss.annotation.EXTRA_LABEL";


    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;


    private NoteTextViewModel noteTextViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_word);

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TextWordActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });



        Intent intent = getIntent();
        String selectedLabel = intent.getStringExtra(EXTRA_LABEL);
        setTitle(selectedLabel);


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteTextAdapter adapter = new NoteTextAdapter();
        recyclerView.setAdapter(adapter);


        noteTextViewModel = ViewModelProviders.of(this).get(NoteTextViewModel.class);

        noteTextViewModel.getLabelWordsNotes(selectedLabel).observe(this, new Observer<List<NoteText>>() {
            @Override
            public void onChanged(@Nullable List<NoteText> noteTexts) {
                //update RecyclerView
                adapter.setNoteTexts(noteTexts);
            }
        });



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteTextViewModel.delete(adapter.getNoteTextAt(viewHolder.getAdapterPosition()));
                Toast.makeText(TextWordActivity.this,"Word deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        adapter.setOnItemClickListener(new NoteTextAdapter.onItemClickListener() {
            @Override
            public void onItemClick(NoteText noteText) {
                Intent intent = new Intent(TextWordActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, noteText.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_WORD, noteText.getWord());
                intent.putExtra(AddEditNoteActivity.EXTRA_LABEL,noteText.getLabel());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String word = data.getStringExtra(AddEditNoteActivity.EXTRA_WORD);
            String label = data.getStringExtra(AddEditNoteActivity.EXTRA_LABEL);

            NoteText noteText = new NoteText(word, label);
            noteTextViewModel.insert(noteText);

            Toast.makeText(this, "NoteText saved", Toast.LENGTH_SHORT).show();
        }else if(requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);

            if (id == -1){
                Toast.makeText(this, "Word Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String word = data.getStringExtra(AddEditNoteActivity.EXTRA_WORD);
            String label = data.getStringExtra(AddEditNoteActivity.EXTRA_LABEL);

            NoteText noteText = new NoteText(word, label);
            noteText.setId(id);
            noteTextViewModel.update(noteText);

            Toast.makeText(this, "Word Note updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "NoteText not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_words:
                noteTextViewModel.deleteAllNotes();
                Toast.makeText(this, "All words deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
