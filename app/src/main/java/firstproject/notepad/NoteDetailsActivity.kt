package firstproject.notepad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
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
        val ACTION_DELETE = "firstproject.notepad.ACTIONS.ACTION_DELETE_NOTE"
        val ACTION_SAVE = "firstproject.notepad.ACTIONS.ACTION_SAVE_NOTE"
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
            R.id.action_delete -> {
                showConfirmDeleteNoteDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    private fun saveNote(){
        note.title = title.text.toString()
        note.text = text.text.toString()

        intent = Intent()
        intent.putExtra(EXTRA_NOTE,note as Parcelable)
        /*
         * Need to add as Parcelable because there is 2 putExtra methods
         * and each one takes as parameter either Parcelable or Serializable
         * I choosed Parcelable because in this case it is an object transmitted to another activity
         */
        intent.putExtra(EXTRA_NOTE_INDEX,noteIndex)
        intent.action = ACTION_SAVE

        setResult(Activity.RESULT_OK,intent)
        finish()
    }
    private fun deleteNote(){
        intent = Intent()
        intent.putExtra(EXTRA_NOTE_INDEX,noteIndex)
        intent.action = ACTION_DELETE
        setResult(Activity.RESULT_OK,intent)
        finish()
    }
    private fun showConfirmDeleteNoteDialog(){
        val dialogFragment = ConfirmDeleteDialog(note.title)
        /*
         * Can't delete a new note !
         */
        if(noteIndex != -1 ) {
            dialogFragment.listener = object : ConfirmDeleteDialog.ConfirmDeleteDialogListener {
                override fun onDialogPositiveClick() {
                    deleteNote()
                }

                override fun onDialogNegativeClick() {
                    Log.i("TAG", "cancel")
                }
            }
            dialogFragment.show(supportFragmentManager, "ConfirmDeleteDialogFragment")
        }
    }
}
