package com.example.listtodo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskListAdapter (var users: MutableList<Task>, val context: Context): RecyclerView.Adapter<TaskListAdapter.ViewHolder>(){

    //jest wykonywana, gdy nie ma jeszcze odpowiedniej ilości obiektów ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_row, parent, false)
        view.setOnClickListener{
            //nowa aktywnosc umożliwia podgląd zadania i może nawet edycję
            val intent = Intent(context, OneTaskActivity::class.java).apply {
                //putExtra("id",view.txt?.text)
            }
            context.startActivity(intent)
        }
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return users.size
    }
    fun setCrimes(usersUpdate: MutableList<Task> ){
        users = usersUpdate
    }
    //wywołuje się zawsze (Recykling starych obiektów)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var user = users[position]
        //ustawia wartosc textBox'ow w user_row.xml
        holder.bind(user)
        //ustawienie nasłuchiwanie kliknięcia przycisków )
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        //zwraca id textBox'ów w user_row.xml wraz z jego zawartością
        private val title = itemView.findViewById<TextView?>(R.id.txtViewTitle)
        private val priority = itemView.findViewById<TextView?>(R.id.txtViewPriority)
        private val state = itemView.findViewById<Button>(R.id.txtViewState)
        private val date = itemView.findViewById<Button>(R.id.txtViewDate)
        fun bind(task: Task){
            title?.text = task.id.toString()
            priority?.text = task.priority
            state?.text = task.state
            date?.text = task.date
        }
    }
}