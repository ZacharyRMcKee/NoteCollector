package com.zacharyrmckee.notecollector;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ZacharyRMcKee on 2/11/2018.
 */

public class NoteViewHolder extends RecyclerView.ViewHolder
{
    public TextView title;
    public TextView lastUpdated;
    public TextView text;

    public NoteViewHolder(View view)
    {
        super(view);
        title = (TextView) view.findViewById(R.id.editTitle);
        lastUpdated = (TextView) view.findViewById(R.id.noteDate);
        text = (TextView) view.findViewById(R.id.editText);

    }
}
