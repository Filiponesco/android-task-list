package com.example.listtodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //otwarcie fragmentu w ktorym są dwa przyciski dodaj i anuluj
        Toast.makeText(this, "Dodaj", Toast.LENGTH_SHORT).show()
        tasksRecyclerView.visibility = View.INVISIBLE
        var fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.fragment_container)
        if(fragment == null){
            fragment = AddTaskFragment()
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit()
        }
        return true
    }
    fun updateUI(){
        var dbHandler = DBHelper(this, null)
        tasksRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false) //co to robi?
        val adapter = TaskListAdapter(dbHandler.getAllTasksOrderByPriority(), this)
        tasksRecyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onBackPressed() {
        var fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.fragment_container)
        if(fragment != null){
            fm.beginTransaction().remove(fragment).commit()
            tasksRecyclerView.visibility = View.VISIBLE
        }
        else{
            super.onBackPressed()
        }
        updateUI()
    }
}
