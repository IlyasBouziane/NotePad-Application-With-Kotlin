package firstproject.notepad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NoteListActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var notes : MutableList<Note>
    lateinit var adapter : NoteAdapter

    override fun onClick(v: View) {
        if(v.tag != null){
            val note : Note
            Log.i("NoteListActivity","A note has been clicked on. The test was succesful")
            val intent = Intent(this,NoteDetailsActivity::class.java)
            val position = v.tag as Int
            note = notes[position]
            intent.putExtra(NoteDetailsActivity.EXTRA_NOTE,note)
            intent.putExtra(NoteDetailsActivity.EXTRA_NOTE_INDEX,position)
            startActivityForResult(intent,NoteDetailsActivity.REQUEST_EDIT_NOTE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode != Activity.RESULT_OK || data == null ){
            return
        }
        when(requestCode){
            NoteDetailsActivity.REQUEST_EDIT_NOTE -> saveNote(data)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }

    }

    fun saveNote(data : Intent ) {
        val note = data.getParcelableExtra<Note>(NoteDetailsActivity.EXTRA_NOTE)
        val noteIndex = data.getIntExtra(NoteDetailsActivity.EXTRA_NOTE_INDEX,-1)
        notes[noteIndex] = note
        adapter.notifyDataSetChanged()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        notes = mutableListOf()
        notes.add(Note("Note 1 ","Premier test est un succés !"))
        notes.add(Note("Note 2","There are lot of things to do this holiday , but inchallah I can do all of them because I believe better in Allah Hamdulilah :)"))
        notes.add(Note("Note 3","YOU CAN DO IT !"))


        adapter = NoteAdapter(notes,this)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter



    }
}
