package com.zacharyrmckee.notecollector;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class MainActivity   extends AppCompatActivity
                            implements View.OnLongClickListener, View.OnClickListener
{


    private ArrayList<Note> notes = new ArrayList<>();
    private static final int NEW_NOTE_CODE = 1;
    private static final int EDIT_NOTE_CODE = 2;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private static final String TAG = "MainActivity";
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        noteAdapter = new NoteAdapter(notes,this);
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.info:
                break;
            case R.id.newNote:
                Intent intent = new Intent(MainActivity.this,EditNote.class);
                if(intent.hasExtra("TITLE"))
                {
                    intent.removeExtra("TITLE");
                }
                startActivityForResult(intent,NEW_NOTE_CODE);
                break;
            default:
                break;
        }
        return true;
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data)
    {
        if (requestCode == NEW_NOTE_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                Note n = (Note)data.getSerializableExtra("NOTE");
                notes.add(n);
                Collections.sort(notes);
                noteAdapter.notifyDataSetChanged();
                return;
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this,"Untitled note was not saved.",Toast.LENGTH_LONG).show();
                return;
            }
        }
        else if (requestCode == EDIT_NOTE_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                Note n = (Note)data.getSerializableExtra("NOTE");
                int pos = data.getIntExtra("POS",-1);
                Log.d(TAG, "onActivityResult: " + n.getText() + "|" + n.getTitle());
                notes.get(pos).setText(n.getText());
                notes.get(pos).setTitle(n.getTitle());
                notes.get(pos).setLastUpdated(new Date());
                Collections.sort(notes);
                noteAdapter.notifyDataSetChanged();

            }
        }
    }


    @Override
    public void onClick(View view) {
        int pos = recyclerView.getChildLayoutPosition(view);
        Note n = notes.get(pos);
        Intent intent = new Intent(MainActivity.this,EditNote.class);
        intent.putExtra("POS",pos);
        intent.putExtra("NOTE",n);
        startActivityForResult(intent,EDIT_NOTE_CODE);
        Toast.makeText(view.getContext(),n.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onLongClick(View view) {
        int pos = recyclerView.getChildLayoutPosition(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_warning_black_48dp);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
                // do other stuff
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        //builder.setMessage("SAVE & EXIT back to notes menu, or GO BACK to the editor.");
        builder.setTitle("Delete Note?");
        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }
}
