package com.example.listtodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_one_task.*



class OneTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_task)

        //val position: Int = intent.getStringExtra("position").toInt()

        val taskId: Int = intent.getStringExtra("id").toInt()
        val db = DBHelper(this, null)
        val task = db.getTask(taskId)
        txtViewTitle.text = task?.title
        txtViewPriority.text = task?.priority
        txtViewDate.text = task?.date
        chkBox.isChecked = task?.state == 1 //if

        btnDelete.setOnClickListener {
            db.delete(task)
            //ListTasksFragment.positionRemove = position //dopiero teraz przypisuje mu wartość, aby wiedzieć że coś zostało usunięte
            finish()
        }
        btnSave.setOnClickListener {
            if(chkBox.isChecked) task?.state = 1
            else task?.state = 0
            db.update(task)
            //ListTasksFragment.positionUpdate = position
            finish()
        }
    }
}
