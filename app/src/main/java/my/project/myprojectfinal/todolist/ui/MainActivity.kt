package my.project.myprojectfinal.todolist.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import my.project.myprojectfinal.todolist.R
import my.project.myprojectfinal.todolist.db.todolist.Catatanharian
import my.project.myprojectfinal.todolist.utilities.Constants
import my.project.myprojectfinal.todolist.utilities.Constants.INSERT
import my.project.myprojectfinal.todolist.utilities.Constants.UPDATE
import kotlinx.android.synthetic.main.activity_isicatatanharian.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CatatanharianAdapter.TodoEvents {

    private lateinit var catatanharianViewModel: CatatanharianViewModel
    private lateinit var searchView: SearchView
    private lateinit var catatanharianAdapter: CatatanharianAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.layoutManager = LinearLayoutManager(this)
        catatanharianAdapter = CatatanharianAdapter(this)
        list.adapter = catatanharianAdapter

        catatanharianViewModel = ViewModelProvider(this).get(CatatanharianViewModel::class.java)
        catatanharianViewModel.getTodos().observe(this, Observer { todo ->
            todo.let { noTodo(todo) }
        })

        new_fab.setOnClickListener {
            reset()
            val intent = Intent(this@MainActivity, MembuatActivity::class.java)
            startActivityForResult(intent, INSERT)
        }
    }

    override fun showDialog(todoList: Catatanharian){
        var dialog: AlertDialog
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Yakin akan dihapus?")
        builder.setMessage("Tidak dapat kembali jika dihapus")

        val dialogHapus = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> onDeleteClicked(todoList)
                DialogInterface.BUTTON_NEGATIVE -> ""
            }
        }

        builder.setPositiveButton("Ya", dialogHapus)
        builder.setNegativeButton("Tidak", dialogHapus)

        dialog = builder.create()
        dialog.show()
    }

    private fun noTodo(todoList: List<Catatanharian>){
        catatanharianAdapter.setTodos(todoList)
        if (todoList.isEmpty()){
            list.visibility = View.GONE
            todophoto.visibility = View.VISIBLE
            quotes.visibility = View.VISIBLE
            quotesby.visibility = View.VISIBLE
        }
        else{
            list.visibility = View.VISIBLE
            todophoto.visibility = View.GONE
            quotes.visibility = View.GONE
            quotesby.visibility = View.GONE
        }
    }

    override fun onViewClicked(todoList: Catatanharian) {
        reset()
        val intent = Intent(this@MainActivity, MemperbaruiActivity::class.java)
        intent.putExtra(Constants.OBJECT, todoList)
        startActivityForResult(intent, UPDATE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val todoList = data?.getParcelableExtra<Catatanharian>(Constants.OBJECT)!!
            when (requestCode) {
                INSERT -> {
                    catatanharianViewModel.insertTodo(todoList)
                }
                UPDATE -> {
                    catatanharianViewModel.updateTodo(todoList)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.searchlist)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                catatanharianAdapter.filter.filter(query)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                catatanharianAdapter.filter.filter(newText)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.searchlist -> true
            R.id.sortingDibuat ->{
                sortingDibuat()
                return true
            }
            R.id.sortingJatuhTempo -> {
                sortingTempo()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sortingDibuat(){
        catatanharianViewModel.getSortDibuat().observe(this, Observer {
            catatanharianAdapter.setSortDibuat(it)
        })
    }

    private fun sortingTempo(){
        catatanharianViewModel.getSortTempo().observe(this, Observer {
            catatanharianAdapter.setSortTempo(it)
        })
    }

    override fun onDeleteClicked(todoList: Catatanharian) {
        catatanharianViewModel.deleteTodo(todoList)
    }

    override fun onBackPressed() {
        reset()
        super.onBackPressed()
    }

    private fun reset() {
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }
    }
}