package my.project.myprojectfinal.todolist.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import my.project.myprojectfinal.todolist.R
import my.project.myprojectfinal.todolist.db.todolist.Catatanharian
import my.project.myprojectfinal.todolist.utilities.Alarm
import my.project.myprojectfinal.todolist.utilities.Constants
import my.project.myprojectfinal.todolist.utilities.Constants.waktuUnix
import kotlinx.android.synthetic.main.activity_pembuatan.*
import java.text.SimpleDateFormat
import java.util.*

class MembuatActivity : AppCompatActivity() {

    private var todoList: Catatanharian? = null
    private lateinit var alarm: Alarm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembuatan)

        tanggal_tempo.setOnClickListener {
            Constants.showDateTimePicker(this, tanggal_tempo)
        }
        title = getString(R.string.Membuat_catatan)
        alarm = Alarm()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflate = menuInflater
        menuInflate.inflate(R.menu.simpan_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.save_todo -> {
                saveTodo()
            }
        }
        return true
    }

    private fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
    }

    private fun saveTodo() {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val id = if (todoList != null) todoList?.id else null
        if(editTitle.text.toString() != "" &&
            editIsi.text.toString() != "" &&
            tanggal_tempo.text.toString() != "Tanggal dan Waktu Tidak Diset") {

            val todo = Catatanharian(
                id = id,
                judul_catatan = editTitle.text.toString(),
                notesharian = editIsi.text.toString(),
                rentang_waktu = waktuUnix,
                rentang_tanggal = tanggal_tempo.text.toString(),
                create_waktu = currentTimeToLong(),
                create_string_waktu = sdf.format(Date()),
                times_update = "",
                title_times_update = ""
            )
            val intent = Intent()
            intent.putExtra(Constants.OBJECT, todo)
            setResult(RESULT_OK, intent)
            alarm.setReminder(
                this,
                waktuUnix - 3600 * 1000,
                "Hai, ingat sebentar lagi ada yang harus anda lakukan"
            )
            finish()
        }
        else{
            var dialog: AlertDialog
            val builder = AlertDialog.Builder(this)

            builder.setTitle("Error")
            builder.setMessage("Catatan harian masih kosong!")

            val dialogHapus = DialogInterface.OnClickListener{ _, which ->
                when(which){
                    DialogInterface.BUTTON_NEGATIVE -> ""
                }
            }

            builder.setNegativeButton("Ok", dialogHapus)

            dialog = builder.create()
            dialog.show()
        }
    }
}