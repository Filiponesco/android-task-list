package com.example.listtodo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskListAdapter (var tasks: MutableList<Task>, val context: Context): RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    //jest wykonywana, gdy nie ma jeszcze odpowiedniej ilości obiektów ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_row, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return tasks.size
    }
    fun setTaskList(tasksUpdate: MutableList<Task> ){
        tasks = tasksUpdate
    }
    //wywołuje się zawsze (Recykling starych obiektów)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var task = tasks[position]
        //ustawia wartosc textBox'ow w user_row.xml
        holder.bind(task)
        //ustawienie nasłuchiwanie kliknięcia w zadanie
        holder.itemView.setOnClickListener{
            val intent = Intent(context, OneTaskActivity::class.java).apply {
                putExtra("id", task.id.toString())
                //putExtra("position", position.toString()) //pozycja na liście RecyclerView liczymy od 0
            }
            context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //zwraca id textBox'ów w user_row.xml wraz z jego zawartością
        private val title = itemView.findViewById<TextView?>(R.id.txtViewTitle)
        private val priority = itemView.findViewById<TextView?>(R.id.txtViewPriority)
        private val state = itemView.findViewById<TextView?>(R.id.txtViewState)
        private val date = itemView.findViewById<TextView?>(R.id.txtViewDate)
        fun bind(task: Task){
            title?.text = task.title
            priority?.text = task.priority
            if(task.state == 1) state?.text = "Zrobione"
            else state?.text = "Nie zrobione"
            date?.text = task.date
        }
    }
}