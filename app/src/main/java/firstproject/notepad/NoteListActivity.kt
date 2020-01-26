package firstproject.notepad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import firstproject.notepad.NoteDetailsActivity.Companion.ACTION_DELETE
import firstproject.notepad.NoteDetailsActivity.Companion.ACTION_SAVE
import firstproject.notepad.utils.deleteNote
import firstproject.notepad.utils.loadNotes
import firstproject.notepad.utils.persistNote

class NoteListActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var notes : MutableList<Note>
    lateinit var adapter : NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        /*
         * Link to the customized toolbar
         */
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        /*
         * Activate floating button listener
         */
        findViewById<FloatingActionButton>(R.id.floating_button).setOnClickListener(this)
        /*
         * Dynamic List imported for the context directory
         */
        notes = loadNotes(this)
        /*
         * Created the recyclerView
         */

        adapter = NoteAdapter(notes,this)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    /*
     * After making changes on a note / deleting a note and get back to the notes' list
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode != Activity.RESULT_OK || data == null ){
            return
        }
        when(requestCode){
            NoteDetailsActivity.REQUEST_EDIT_NOTE -> {
                when(data.action) {
                    ACTION_SAVE -> saveNote(data)
                    ACTION_DELETE -> deleteNote(data)
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }
    fun saveNote(data : Intent ) {
        val note = data.getParcelableExtra<Note>(NoteDetailsActivity.EXTRA_NOTE)
        val noteIndex = data.getIntExtra(NoteDetailsActivity.EXTRA_NOTE_INDEX,-1)
        persistNote(this,note)
        /*
         * noteIndex == -1 means there is a new note to be added
         */
        if(noteIndex < 0) {
           notes.add(0,note)
        } else {
            notes[noteIndex] = note
        }
        adapter.notifyDataSetChanged()
    }
    fun deleteNote(data : Intent) {
        val indexNoteToDelete = data.getIntExtra(NoteDetailsActivity.EXTRA_NOTE_INDEX,-1)
        val note = notes[indexNoteToDelete]
        deleteNote(this,note)
        if(indexNoteToDelete < 0)
            return;
        notes.removeAt(indexNoteToDelete)
        adapter.notifyDataSetChanged()
    }

    /*
     * onClick method defines the actions after clicking on the item or the add button
     */
    override fun onClick(v: View) {
        if(v.tag != null){
            val noteIndex = v.tag as Int
            showNoteDetails(noteIndex)
        } else {
            if(v.id == R.id.floating_button){
                createNewNote()
            }
        }
    }
    private fun showNoteDetails(noteIndex : Int){
        val note : Note
        Log.i("NoteListActivity","A note has been clicked on. The test was succesful")
        val position = noteIndex
        note = if(position < 0) Note() else notes[position]

        val intent = Intent(this,NoteDetailsActivity::class.java)
        intent.putExtra(NoteDetailsActivity.EXTRA_NOTE,note as Parcelable)
        /*
         * Need to add as Parcelable because there is 2 putExtra methods
         * and each one takes as parameter either Parcelable or Serializable
         * I choosed Parcelable because in this case it is an object transmitted to another activity
         */
        intent.putExtra(NoteDetailsActivity.EXTRA_NOTE_INDEX,position)
        startActivityForResult(intent,NoteDetailsActivity.REQUEST_EDIT_NOTE)
    }
    private fun createNewNote(){
        showNoteDetails(-1)
    }
}
