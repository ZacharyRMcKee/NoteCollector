package com.zacharyrmckee.notecollector;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class MainActivity   extends AppCompatActivity
                            implements View.OnLongClickListener, View.OnClickListener
{


    private ArrayList<Note> notes = new ArrayList<>();
    private static final int NEW_NOTE_CODE = 1;
    private static final int EDIT_NOTE_CODE = 2;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private static final String TAG = "MainActivity";
    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss:SS Z yyyy",new Locale("us"));

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
                startActivity(new Intent(MainActivity.this,ShowInfo.class));
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
    public void whenFileLoaded(ArrayList<Note> notes)
    {
        this.notes = notes;
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
    public boolean onLongClick(final View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_warning_black_48dp);
        int pos = recyclerView.getChildLayoutPosition(view);
        String noteName = notes.get(pos).getTitle();
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteNote(view);
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
        builder.setTitle("Delete Note '" + noteName + "'?");
        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }
    private void loadNotes()
    {

    }
    private void saveNotes()
    {
        try
        {
            FileOutputStream fos = getApplicationContext().openFileOutput("notes.json", Context.MODE_PRIVATE);
            JsonWriter writer = new JsonWriter(new OutputStreamWriter(fos,"UTF-8"));
            writer.setIndent("  ");
            writer.beginArray();
            for(Note note : notes)
            {
                writer.beginObject();
                writer.name("title").value(note.getTitle());
                writer.name("text").value(note.getText());
                writer.name("date").value(sdf.format(note.getLastUpdated()));
                writer.endObject();
            }
            writer.endArray();
        }
        catch(Exception e)
        {
            e.getStackTrace();
        }

    }
    private void deleteNote(View view)
    {
        int pos = recyclerView.getChildLayoutPosition(view);
        notes.remove(pos);
        noteAdapter.notifyDataSetChanged();
    }
}
