package de.uni_passau.dpss.annotation.View.Text.Label;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.uni_passau.dpss.annotation.R;

public class NoteTextLabelAdapter extends RecyclerView.Adapter<NoteTextLabelAdapter.NoteHolder>{

    private List<String> noteTextLabels = new ArrayList<>();
    private onItemClickListener listener;

    @NonNull
    @Override
    public NoteTextLabelAdapter.NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_text_labels_item, parent, false);
        return new NoteTextLabelAdapter.NoteHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull NoteTextLabelAdapter.NoteHolder holder, int position) {
        String currentNoteText = noteTextLabels.get(position);
        holder.textViewLabel.setText(currentNoteText);
    }

    @Override
    public int getItemCount() {
        return noteTextLabels.size();
    }

    public void setNoteTextLabels(List<String> noteTextLabels) {
        this.noteTextLabels = noteTextLabels;
        notifyDataSetChanged();
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewLabel;

        public NoteHolder(View itemView) {
            super(itemView);
            textViewLabel = itemView.findViewById(R.id.text_view_label_list);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(noteTextLabels.get(position));
                    }

                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(String label);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }

}
