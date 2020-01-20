package firstproject.notepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class NoteDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val note = intent.getParcelableExtra<Note>("note")



        setContentView(R.layout.activity_note_details)



    }
}
