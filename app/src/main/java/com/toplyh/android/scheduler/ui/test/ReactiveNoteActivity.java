package com.toplyh.android.scheduler.ui.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscriptionList;

public class ReactiveNoteActivity extends AppCompatActivity {


    private EditText mEditText;
    private android.view.View addNoteButton;

    private Box<Note> notesBox;

    private Query<Note> notesQuery;

    private NotesAdapter notesAdapter;

    private DataSubscriptionList mSubscriptionList=new DataSubscriptionList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        setUpViews();

        notesBox=((App)getApplication()).getBoxStore().boxFor(Note.class);

        notesQuery=notesBox.query().order(Note_.text).build();

        mSubscriptionList.add(notesQuery.subscribe().on(AndroidScheduler.mainThread())
        .observer(new DataObserver<List<Note>>() {
            @Override
            public void onData(List<Note> data) {
                notesAdapter.setNotes(data);
            }
        }));
        /*notesQuery.subscribe(mSubscriptionList).on(AndroidScheduler.mainThread())
                .observer(new DataObserver<List<Note>>() {
                    @Override
                    public void onData(List<Note> data) {
                        notesAdapter.setNotes(data);
                    }
                });*/

    }

    @Override
    protected void onDestroy() {
        mSubscriptionList.cancel();
        super.onDestroy();
    }

    protected void setUpViews(){
        ListView listView=(ListView)findViewById(R.id.listViewNotes);
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

        mEditText=(EditText)findViewById(R.id.editTextNote);
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_DONE){
                    addNote();
                    return true;
                }
                return false;
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                boolean enable=s.length()!=0;
                addNoteButton.setEnabled(enable);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void addNote(){
        String noteText=mEditText.getText().toString();
        mEditText.setText("");

        DateFormat df=DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
        String comment="Add on "+ df.format(new Date());

        Note note=new Note();
        note.setText(noteText);
        note.setComment(comment);
        note.setDate(new Date());
        notesBox.put(note);
        Log.d(App.TAG,"Inserted new note, ID:"+note.getId());
    }

    AdapterView.OnItemClickListener noteClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
            Note note=notesAdapter.getItem(position);
            notesBox.remove(note);
            Log.d(App.TAG,"Deleted note,ID: "+note.getId());
        }
    };
}
