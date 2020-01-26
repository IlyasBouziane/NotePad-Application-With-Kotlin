package firstproject.notepad.utils

import android.content.Context
import firstproject.notepad.Note
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

fun persistNote(context : Context, note : Note){
    /*
     * unique name the note file
     */
    note.filename = UUID.randomUUID().toString()+".note"
    /*
     * Mode private : to keep the access to the stored file only for the application
     */
    val fileOutput = context.openFileOutput(note.filename,Context.MODE_PRIVATE)
    /*
     * Open the outputStream
     */
    val outputStream = ObjectOutputStream(fileOutput)
    /*
     * Writes the note
     */
    outputStream.writeObject(note)

    outputStream.close()
}

private fun loadNote(context : Context,filename : String) : Note{
    val fileInput = context.openFileInput(filename)
    val inputStream = ObjectInputStream(fileInput)
    val note = inputStream.readObject() as Note
    inputStream.close()
    return note
}
fun loadNotes(context: Context) : MutableList<Note>{
    var notes = mutableListOf<Note>()
    val notesDir = context.filesDir
    for(filename in notesDir.list()){
        notes.add(loadNote(context,filename))
    }
    return notes
}
fun deleteNote(context: Context,note : Note){
    context.deleteFile(note.filename)
}