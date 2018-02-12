package com.zacharyrmckee.notecollector;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * Created by ZacharyRMcKee on 2/12/2018.
 */

public class EditNote extends AppCompatActivity {

    private EditText noteTitle;
    private EditText noteText;
    private Note note;
    private String initialTitle;
    private String initialText;
    private static final String TAG = "EditNote";
    private int notePosition;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_note);

        this.noteTitle = findViewById(R.id.editTitle);
        this.noteText = findViewById(R.id.editText);
        Intent intent = getIntent();

        note = (intent.hasExtra("NOTE"))  ?
                (Note)intent.getSerializableExtra("NOTE") : new Note();
        Log.d(TAG, "onCreate: " + note.getTitle());
        initialTitle = note.getTitle();
        initialText = note.getText();
        notePosition = intent.getIntExtra("POS",-1);
        noteTitle.setText(note.getTitle());
        noteText.setText(note.getText());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.saveNote:
                saveNote();
                break;
            default:
                break;
        }
        return true;
    }
    public void onBackPressed()
    {
        note.setText(noteText.getText().toString());
        note.setTitle(noteTitle.getText().toString());
        Log.d(TAG, "onBackPressed: " + note.getText() + "| " + note.getTitle());
        if(noteTitle.getText().toString().equals(""))
        {
            setResult(RESULT_CANCELED,new Intent());
            super.onBackPressed();
        }
        else
        {
            if(  (! note.getTitle().equals(initialTitle) || ( ! note.getText().equals(initialText))))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.drawable.ic_info_outline_black_48dp);

                builder.setPositiveButton("SAVE & EXIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saveNote();
                    }
                });
                builder.setNegativeButton("GO BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
                builder.setMessage("SAVE & EXIT back to notes menu, or GO BACK to the editor.");
                builder.setTitle("Save your note?");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else
            {
                setResult(RESULT_CANCELED);
                super.onBackPressed();
            }
        }


    }
    private void saveNote()
    {
        note.setText(noteText.getText().toString());
        note.setTitle(noteTitle.getText().toString());
        if(noteTitle.getText().toString().equals(""))
        {
            setResult(RESULT_CANCELED,new Intent());
            super.onBackPressed();
        }
        else
        {
            Intent data = new Intent();
            data.putExtra("NOTE", note);
            data.putExtra("POS",notePosition);
            setResult(RESULT_OK, data);
            EditNote.super.onBackPressed();
        }

    }
}
