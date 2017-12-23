package com.toplyh.android.scheduler.ui.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.app.App;
import com.toplyh.android.scheduler.service.entity.Note;
import com.toplyh.android.scheduler.service.entity.Note_;
import com.toplyh.android.scheduler.service.view.View;

import java.util.Date;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

public class NoteActivity extends AppCompatActivity {

    private EditText editText;
    private android.view.View addNoteButton;

    private Box<Note> notesBox;
    private Query<Note> notesQuery;
    private NotesAdapter notesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        setUpViews();

        BoxStore boxStore=((App)getApplication()).getBoxStore();
        notesBox=boxStore.boxFor(Note.class);
        notesQuery=notesBox.query().order(Note_.text).build();
        updateNotes();
    }

    private void updateNotes(){
        List<Note> notes=notesQuery.find();
        notesAdapter.setNotes(notes);
    }

    protected void setUpViews(){
        ListView listView=(ListView) findViewById(R.id.listViewNotes);
        listView.setOnItemClickListener(noteClickListener);

        notesAdapter=new NotesAdapter();
        listView.setAdapter(notesAdapter);

        addNoteButton=findViewById(R.id.buttonAdd);
        addNoteButton.setEnabled(false);
        addNoteButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                addNote();
            }
        });

        editText=(EditText) findViewById(R.id.editTextNote);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_DONE){
                    addNote();
                    return true;
                }
                return false;
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enable=s.length()!=0;
                addNoteButton.setEnabled(enable);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void addNote(){
        String noteText=editText.getText().toString();
        editText.setText("");

        java.text.DateFormat df= java.text.DateFormat.
                getDateTimeInstance(java.text.DateFormat.MEDIUM,java.text.DateFormat.MEDIUM);
        String comment="Added on "+df.format(new Date());

        Note note = new Note();
        note.setText(noteText);
        note.setComment(comment);
        note.setDate(new Date());
        notesBox.put(note);
        Log.d(App.TAG,"Inserted new note, ID: "+note.getId());

        updateNotes();
    }

    AdapterView.OnItemClickListener noteClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
            Note note = notesAdapter.getItem(position);
            notesBox.remove(note);
            Log.d(App.TAG,"Deleted note,ID: "+note.getId());
            updateNotes();
        }
    };
}
