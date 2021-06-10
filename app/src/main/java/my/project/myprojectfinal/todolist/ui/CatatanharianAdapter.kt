package my.project.myprojectfinal.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import my.project.myprojectfinal.R
import my.project.myprojectfinal.db.todolist.Catatanharian
import kotlinx.android.synthetic.main.item_catatanharian.view.*

class CatatanharianAdapter(todoEvents: TodoEvents) : RecyclerView.Adapter<CatatanharianAdapter.TodoViewHolder>(),
    Filterable {

    private var todo: List<Catatanharian> = arrayListOf()
    private var filteredTodoList: List<Catatanharian> = arrayListOf()
    private val listener: TodoEvents = todoEvents

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_catatanharian,
            parent, false)
        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int = filteredTodoList.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(filteredTodoList[position], listener)
    }

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(todo: Catatanharian, listener: TodoEvents) {
            itemView.title_todo.text = todo.judul_catatan
            itemView.isi_todo.text = todo.notesharian
            itemView.view_jatuh_tanggal.text = todo.rentang_tanggal
            itemView.view_waktu_dibuat.text = todo.create_string_waktu
            itemView.view_waktu_update.text = todo.times_update
            itemView.time_update.text = todo.title_times_update

            itemView.delete_button.setOnClickListener {
                listener.showDialog(todo)
            }
            itemView.setOnClickListener {
                listener.onViewClicked(todo)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charString = p0.toString()
                filteredTodoList = if (charString.isEmpty()) {
                    todo
                } else {
                    val filteredList = arrayListOf<Catatanharian>()
                    for (row in todo) {
                        if (row.judul_catatan.toLowerCase().contains(charString.toLowerCase())
                            || row.notesharian.contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredTodoList
                return filterResults
            }
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredTodoList = p1?.values as List<Catatanharian>
                notifyDataSetChanged()
            }

        }
    }

    fun setTodos(todoList: List<Catatanharian>) {
        this.todo = todoList
        this.filteredTodoList = todoList
        notifyDataSetChanged()
    }

    fun setSortDibuat(todoList: List<Catatanharian>){
        this.todo = todoList
        this.filteredTodoList = todoList
        notifyDataSetChanged()
    }

    fun setSortTempo(todoList: List<Catatanharian>){
        this.todo = todoList
        this.filteredTodoList = todoList
        notifyDataSetChanged()
    }

    interface TodoEvents {
        fun onDeleteClicked(todoList: Catatanharian)
        fun onViewClicked(todoList: Catatanharian)
        fun showDialog(todoList: Catatanharian)
    }
}