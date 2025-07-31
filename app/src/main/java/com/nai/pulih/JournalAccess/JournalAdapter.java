package com.nai.pulih.JournalAccess;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nai.pulih.JournalAccess.JournalEntry;
import com.nai.pulih.JournalAccess.ReadJournalActivity;
import com.nai.pulih.R;

import java.util.ArrayList;
import java.util.List;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {

    private List<JournalEntry> journals = new ArrayList<>();

    public void setJournals(List<JournalEntry> list) {
        this.journals = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_journal_card, parent, false);
        return new JournalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
        JournalEntry journal = journals.get(position);
        holder.bind(journal);
    }

    @Override
    public int getItemCount() {
        return journals.size();
    }

    class JournalViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTv, dateTv;
        private Context context;

        public JournalViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            titleTv = itemView.findViewById(R.id.journalTitle);
            dateTv = itemView.findViewById(R.id.journalDate);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    JournalEntry selected = journals.get(pos);
                    Intent intent = new Intent(context, ReadJournalActivity.class);
                    intent.putExtra("journal_id", selected.id); // pass id to read
                    context.startActivity(intent);
                }
            });
        }

        public void bind(JournalEntry journal) {
            titleTv.setText(journal.title);
            dateTv.setText(journal.date);
        }
    }
}
