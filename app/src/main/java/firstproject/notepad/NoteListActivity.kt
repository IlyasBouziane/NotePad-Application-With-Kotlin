package firstproject.notepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NoteListActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var notes : MutableList<Note>
    lateinit var adapter : NoteAdapter

    override fun onClick(v: View) {
        if(v.tag != null){
            Log.i("NoteListActivity","A note has been clicked on. The test was succesful")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)


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
