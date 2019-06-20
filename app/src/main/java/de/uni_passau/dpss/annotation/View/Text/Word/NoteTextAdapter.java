package de.uni_passau.dpss.annotation.View.Text.Word;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.uni_passau.dpss.annotation.Model.Text.NoteText;
import de.uni_passau.dpss.annotation.R;

public class NoteTextAdapter extends RecyclerView.Adapter<NoteTextAdapter.NoteHolder> {
    private List<NoteText> noteTexts = new ArrayList<>();
    private onItemClickListener listener;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        NoteText currentNoteText = noteTexts.get(position);
        holder.textViewWord.setText(currentNoteText.getWord());
        holder.textViewLabel.setText(currentNoteText.getLabel());
    }

    @Override
    public int getItemCount() {
        return noteTexts.size();
    }

    public void setNoteTexts(List<NoteText> noteTexts) {
        this.noteTexts = noteTexts;
        notifyDataSetChanged();
    }

    public NoteText getNoteTextAt(int position){
        return noteTexts.get(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewWord;
        private TextView textViewLabel;

        public NoteHolder(View itemView) {
            super(itemView);
            textViewWord = itemView.findViewById(R.id.text_view_word);
            textViewLabel = itemView.findViewById(R.id.text_view_label);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION)
                    listener.onItemClick(noteTexts.get(position));
                }
            });
        }
    }

    public interface onItemClickListener{
        void onItemClick(NoteText noteText);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }

}