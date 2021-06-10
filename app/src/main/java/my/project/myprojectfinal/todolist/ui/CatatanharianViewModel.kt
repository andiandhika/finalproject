package my.project.myprojectfinal.todolist.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import my.project.myprojectfinal.todolist.db.todolist.Catatanharian
import my.project.myprojectfinal.todolist.db.todolist.CatatanharianRepository

class CatatanharianViewModel(application: Application) : AndroidViewModel(application) {

    private var todolistRepository = CatatanharianRepository(application)
    private var todos: LiveData<List<Catatanharian>> = todolistRepository.getTodos()
    private var sortDibuat: LiveData<List<Catatanharian>> = todolistRepository.getSortDibuat()
    private var sortTempo: LiveData<List<Catatanharian>> = todolistRepository.getSortTempo()

    fun insertTodo(todo: Catatanharian) {
        todolistRepository.insert(todo)
    }

    fun getTodos(): LiveData<List<Catatanharian>> {
        return todos
    }

    fun getSortDibuat(): LiveData<List<Catatanharian>>{
        return sortDibuat
    }

    fun getSortTempo(): LiveData<List<Catatanharian>>{
        return sortTempo
    }

    fun deleteTodo(todo: Catatanharian) {
        todolistRepository.delete(todo)
    }

    fun updateTodo(todo: Catatanharian) {
        todolistRepository.update(todo)
    }

}