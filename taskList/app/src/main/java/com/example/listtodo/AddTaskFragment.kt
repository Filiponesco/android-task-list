package com.example.listtodo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import android.app.DatePickerDialog
import android.graphics.Color
import java.util.*
import android.widget.DatePicker


//kontroler, ktory wspolpracuje z obiketami warstwy i modelu
class AddTaskFragment: Fragment(), DatePickerDialog.OnDateSetListener {
    lateinit var newTask: Task
    lateinit var txtTitle: EditText
    lateinit var groupPriority: RadioGroup
    lateinit var txtDate: TextView
    lateinit var chkBoxDone: CheckBox
    lateinit var btnAdd: Button
    lateinit var btnCancel: Button
    lateinit var btnSelectDate: Button
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
        txtDate = v.findViewById(R.id.txtViewDate)
        chkBoxDone = v.findViewById(R.id.checkBox)
        btnAdd = v.findViewById(R.id.btnAdd)
        btnCancel = v.findViewById(R.id.btnCancel)
        btnSelectDate = v.findViewById(R.id.btnSelectDate)

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
            //activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
            activity?.onBackPressed()
            Toast.makeText(context, "Anulowano", Toast.LENGTH_SHORT).show()
        }
        btnSelectDate.setOnClickListener {
            showDatePickerDialog(v)
        }
        return v
    }
    fun showDatePickerDialog(view: View) {
        val datePickerDialog = DatePickerDialog(
            view.context,
            this,
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val date = "$dayOfMonth.$month.$year"
        txtDate.text = date
    }
}
