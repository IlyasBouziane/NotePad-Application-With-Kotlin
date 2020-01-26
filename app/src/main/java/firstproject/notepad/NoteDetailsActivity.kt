package firstproject.notepad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_note_details.*
import org.w3c.dom.Text


class NoteDetailsActivity : AppCompatActivity() {
    companion object {
        val EXTRA_NOTE = "note"
        val EXTRA_NOTE_INDEX ="noteIndex"
        val REQUEST_EDIT_NOTE = 1
    }
    lateinit var note : Note
    lateinit var title : TextView
    lateinit var text : TextView
    var noteIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)
        /*
         * Link to the customized toolbar
         */
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        /*
         * Link this activity to the main activity : NoteListActivity
         */
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        /*
         * Create intent and the parcelable object
         */
        note = intent.getParcelableExtra<Note>(EXTRA_NOTE)
        noteIndex = intent.getIntExtra(EXTRA_NOTE_INDEX,-1)

        title  = findViewById<TextView>(R.id.title)
        title.text = note.title

        text = findViewById<TextView>(R.id.text)
        text.text = note.text
    }

    fun saveNote(){
        note.title = title.text.toString()
        note.text = text.text.toString()

        intent = Intent()
        intent.putExtra(EXTRA_NOTE,note)
        intent.putExtra(EXTRA_NOTE_INDEX,noteIndex)

        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    /*
     * Menu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_note_details,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                saveNote()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
