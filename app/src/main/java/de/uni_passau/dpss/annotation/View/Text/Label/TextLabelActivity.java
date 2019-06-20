package de.uni_passau.dpss.annotation.View.Text.Label;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import de.uni_passau.dpss.annotation.R;
import de.uni_passau.dpss.annotation.View.Text.Word.TextWordActivity;
import de.uni_passau.dpss.annotation.ViewModel.Text.NoteTextViewModel;

public class TextLabelActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int VIEW_LABEL_REQUEST = 2;


    private NoteTextViewModel noteTextViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_label);


        RecyclerView recyclerView = findViewById(R.id.recycler_view_text_label);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);




        final NoteTextLabelAdapter adapter = new NoteTextLabelAdapter();
        recyclerView.setAdapter(adapter);

        noteTextViewModel = ViewModelProviders.of(this).get(NoteTextViewModel.class);
        noteTextViewModel.getAllWordLabels().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> noteTextLabels) {
                //update RecyclerView
                adapter.setNoteTextLabels(noteTextLabels);
            }
        });

        adapter.setOnItemClickListener(new NoteTextLabelAdapter.onItemClickListener() {
            @Override
            public void onItemClick(String label) {
                Intent intent = new Intent(TextLabelActivity.this, TextWordActivity.class);
                intent.putExtra(TextWordActivity.EXTRA_LABEL, label);
                startActivityForResult(intent, VIEW_LABEL_REQUEST);

            }
        });

    }


}
