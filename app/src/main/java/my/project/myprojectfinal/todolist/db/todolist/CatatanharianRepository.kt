package my.project.myprojectfinal.todolist.db.todolist

import android.app.Application
import androidx.lifecycle.LiveData
import my.project.myprojectfinal.todolist.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CatatanharianRepository(application: Application) {

    private val todoListDao: CatatanharianDao
    private val todos: LiveData<List<Catatanharian>>
    private val sortDibuat: LiveData<List<Catatanharian>>
    private val sortTempo: LiveData<List<Catatanharian>>

    init {
        val database = AppDatabase.getInstance(application.applicationContext)
        todoListDao = database!!.todoDao()
        todos = todoListDao.getTodos()
        sortDibuat = todoListDao.getSortDibuat()
        sortTempo = todoListDao.getSortTempo()
    }

    fun getTodos(): LiveData<List<Catatanharian>>{
        return todos
    }

    fun getSortDibuat(): LiveData<List<Catatanharian>>{
        return sortDibuat
    }

    fun getSortTempo(): LiveData<List<Catatanharian>>{
        return sortTempo
    }

    fun insert(todo: Catatanharian) = runBlocking {
        this.launch(Dispatchers.IO) {
            todoListDao.insertTodo(todo)
        }
    }

    fun update(todo: Catatanharian) = runBlocking {
        this.launch(Dispatchers.IO) {
            todoListDao.updateTodo(todo)
        }
    }

    fun delete(todo: Catatanharian) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                todoListDao.deleteTodo(todo)
            }
        }
    }
}