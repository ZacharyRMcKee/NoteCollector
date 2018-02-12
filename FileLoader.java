package com.zacharyrmckee.notecollector;

import android.os.AsyncTask;
import android.util.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ZacharyRMcKee on 2/12/2018.
 */

public class FileLoader extends AsyncTask<String,Void,ArrayList<Note>> {

    private MainActivity mainActivity;
    public static boolean running = false;
    public FileLoader(MainActivity ma) { mainActivity = ma; }

    @Override
    protected ArrayList<Note> doInBackground(String... inputs) {
        ArrayList<Note> notes = new ArrayList<Note>();
        try
        {
            InputStream is = mainActivity.getApplicationContext().openFileInput("notes.json");
            JsonReader reader = new JsonReader(new InputStreamReader(is,"UTF-8"));

            reader.beginArray();
            while(reader.hasNext())
            {
                notes.add(readNote(reader));
            }
        }
        catch (FileNotFoundException e)
        {
            return notes;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return notes;
    }
    private Note readNote(JsonReader reader) throws IOException
    {
        Date date = null;
        String title = null;
        String text = null;
        reader.beginObject();
        while(reader.hasNext())
        {
            String name = reader.nextName();
            if(name.equals("title"))
            {
                title = reader.nextString();
            }
            else if(name.equals("text"))
            {
                text = reader.nextString();
            }
            else if(name.equals("date"))
            {
                try
                {
                    date = mainActivity.sdf.parse(reader.nextString());
                }catch(Exception e)
                {
                    e.getStackTrace();
                }
            }
            else
            {
                reader.skipValue();
            }
        }
        return new Note(title,text,date);
    }
    @Override
    protected void onPostExecute(ArrayList<Note> notes) {
        super.onPostExecute(notes);
        mainActivity.whenFileLoaded(notes);
    }
}
