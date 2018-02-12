package com.zacharyrmckee.notecollector;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by ZacharyRMcKee on 2/11/2018.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private ArrayList<Note> notes;
    private MainActivity mainActivity;
    private static final String TAG = "NoteAdapter";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public NoteAdapter(ArrayList<Note> notes, MainActivity ma)
    {
        this.mainActivity = ma;
        this.notes = notes;
    }
    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_item,parent,false);
/*        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onClick: ");

                Intent intent = new Intent(MainActivity.this,EditNote.class);

                int pos = mainActivity.getRecyclerView().getChildLayoutPosition(view);
                Note n = notes.get(pos);

                Toast.makeText(v.getContext(),n.toString(),Toast.LENGTH_LONG).show();
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d(TAG, "onLongClick: ");
                return true;
            }
        });*/
        itemView.setOnLongClickListener(mainActivity);
        itemView.setOnClickListener(mainActivity);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.title.setText(note.getTitle());
        holder.lastUpdated.setText(sdf.format(note.getLastUpdated()));
        String truncated = (note.getText().length() > 80) ? note.getText().substring(0,80) + "..."
                : note.getText();

        holder.text.setText(truncated);

    }

    @Override
    public int getItemCount() {
        return this.notes.size();
    }
}
