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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_list_tasks, container, false)
        loadDataToRecyclerView(v)
        Toast.makeText(v.context, "fragment", Toast.LENGTH_SHORT).show()
        return v
    }
    fun loadDataToRecyclerView(v:View){
        val recyclerView = v.findViewById<RecyclerView>(R.id.tasksRecyclerView)
        var dbHandler = DBHelper(v.context, null)
        recyclerView?.layoutManager = LinearLayoutManager(v.context, RecyclerView.VERTICAL,false) //co to robi?
        val adapter = TaskListAdapter(dbHandler.getAllTasksOrderByPriority(), v.context)
        recyclerView?.adapter = adapter
        //adapter.notifyDataSetChanged()
    }
}