package com.example.listtodo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListTasksFragment: Fragment() {
    lateinit var adapter: TaskListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_list_tasks, container, false)
        loadDataToRecyclerView(v)
        return v
    }
    fun loadDataToRecyclerView(v:View){
        val recyclerView = v.findViewById<RecyclerView>(R.id.tasksRecyclerView)
        var dbHandler = DBHelper(v.context, null)
        recyclerView?.layoutManager = LinearLayoutManager(v.context, RecyclerView.VERTICAL,false) //co to robi?
        adapter = TaskListAdapter(dbHandler.getAllTasksOrderByPriority(), v.context)
        recyclerView?.adapter = adapter
    }
    override fun onResume() {
        super.onResume()
        var dbHandler = DBHelper(context!!, null)
        //adapter = TaskListAdapter(dbHandler.getAllTasksOrderByPriority(), context!!) //czym to się różni od tego na dole?
        adapter.setTaskList(dbHandler.getAllTasksOrderByPriority()) //bo to u góry tworzy nową klasę i nie wie, że coś się zmieniło
        /*  when{
            positionRemove != -1 -> adapter.notifyItemRemoved(positionRemove)
            positionUpdate != -1 -> adapter.notifyItemChanged(positionUpdate)
            //else -> adapter.notifyDataSetChanged()
        }*/
        //po tym pozycje ViewHOlderó będą aktualne
        adapter.notifyDataSetChanged() //czasami wykona się dwa razy, bo RecyclerView przy usuwaniu nie odejmuje pozycji ViewHolderów
        /*positionRemove = -1
        positionUpdate = -1*/
    }
    /*companion object{
        var positionRemove = -1 //pozycja usunięta z RecyclerView
        var positionUpdate = -1 //pozycja zaktualizowana z RecyclerView
    }*/
}