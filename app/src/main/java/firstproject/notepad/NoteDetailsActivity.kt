package firstproject.notepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_note_details.*
import org.w3c.dom.Text

class NoteDetailsActivity : AppCompatActivity() {
    companion object {
        val EXTRA_NOTE = "note"
        val EXTRA_NOTE_INDEX ="noteIndex"
    }
    lateinit var note : Note
    var noteIndex = -1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)


        note = intent.getParcelableExtra<Note>(EXTRA_NOTE)
        noteIndex = intent.getIntExtra(EXTRA_NOTE_INDEX,-1)

        val title  = findViewById<TextView>(R.id.title)
        title.text = note.title

        val text  = findViewById<TextView>(R.id.text)
        text.text = note.text as String




    }
}
