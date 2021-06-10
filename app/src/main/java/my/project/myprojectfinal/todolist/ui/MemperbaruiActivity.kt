package my.project.myprojectfinal.todolist.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import my.project.myprojectfinal.todolist.R
import my.project.myprojectfinal.todolist.db.todolist.Catatanharian
import my.project.myprojectfinal.todolist.utilities.Alarm
import my.project.myprojectfinal.todolist.utilities.Constants
import my.project.myprojectfinal.todolist.utilities.Constants.waktuUnix
import kotlinx.android.synthetic.main.activity_memperbarui.*
import java.text.SimpleDateFormat
import java.util.*

class MemperbaruiActivity : AppCompatActivity() {

    private var todoList: Catatanharian? = null
    private lateinit var alarm : Alarm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memperbarui)

        val intent = intent
        if (intent != null && intent.hasExtra(Constants.OBJECT)) {
            val todoList: Catatanharian = intent.getParcelableExtra(Constants.OBJECT)
            this.todoList = todoList
            prePopulateData(todoList)
        }
        tanggal_tempoUpdate.setOnClickListener {
            Constants.showDateTimePicker(this, tanggal_tempoUpdate)
        }

        title = getString(R.string.edit)
        alarm = Alarm()
    }

    private fun prePopulateData(todoList: Catatanharian) {
        editTitleUpdate.setText(todoList.judul_catatan)
        editIsiUpdate.setText(todoList.notesharian)
        tanggal_tempoUpdate.setText(todoList.rentang_tanggal)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflate = menuInflater
        menuInflate.inflate(R.menu.simpan_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.save_todo -> {
                todoList?.let { saveTodo(it) }
            }
        }
        return true
    }

    private fun saveTodo(todoList: Catatanharian) {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        if(editTitleUpdate.text.toString() != todoList.judul_catatan && editIsiUpdate.text.toString() != todoList.notesharian && tanggal_tempoUpdate.text.toString() != todoList.rentang_tanggal) {
            todoList.judul_catatan = editTitleUpdate.text.toString()
            todoList.notesharian = editIsiUpdate.text.toString()
            todoList.rentang_waktu = waktuUnix
            todoList.rentang_tanggal = tanggal_tempoUpdate.text.toString()
            todoList.times_update = sdf.format(Date())
            todoList.title_times_update = "Diubah"

            val intent = Intent()
            intent.putExtra(Constants.OBJECT, todoList)
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
            builder.setMessage("Data sama seperti sebelumnya!")

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