package com.example.listtodo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.SortedList
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_edit_task.*
import javax.security.auth.callback.Callback

//kontroler, ktory wspolpracuje z obiketami warstwy i modelu
class AddTaskFragment: Fragment() {
    lateinit var newTask: Task
    lateinit var txtTitle: EditText
    lateinit var groupPriority: RadioGroup
    lateinit var txtDate: EditText
    lateinit var chkBoxDone: CheckBox
    lateinit var btnAdd: Button
    lateinit var btnCancel: Button
    lateinit var db: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    //OnCreateView - bardzo ważna funkcja, rozwija widok fragmentu poprzez wywołanie metody LayoutInflater.inflate(...)
    // i przekazanie do niej odpowiedniego identyfikatora pliku układu.
    //Drugim parametrem jest widok nadrzędny, który zazwyczaj jest niezbędny do prawidłowego skonf. widgetów
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_edit_task, container, false)
        txtTitle = v.findViewById(R.id.editTxtTitle)
        groupPriority = v.findViewById(R.id.rdGPriority)
        txtDate = v.findViewById(R.id.editTxtDate)
        chkBoxDone = v.findViewById(R.id.checkBox)
        btnAdd = v.findViewById(R.id.btnAdd)
        btnCancel = v.findViewById(R.id.btnCancel)


        btnAdd.setOnClickListener {
            db = DBHelper(v.context, null)
            var isDone = 0
            if(chkBoxDone.isChecked) isDone = 1

            var priority = groupPriority.findViewById<RadioButton>(groupPriority.checkedRadioButtonId).text.toString()

            newTask = Task(txtTitle.text.toString(), priority, isDone, txtDate.text.toString(), null)
            db.addTask(newTask)
            Toast.makeText(context, "Dodano", Toast.LENGTH_SHORT).show()
        }
        btnCancel.setOnClickListener {
            Toast.makeText(context, "Anulowano", Toast.LENGTH_SHORT).show()
        }
        return v
    }
}
