package com.example.listtodo

import android.database.Cursor
import android.database.CursorWrapper

class TaskCursorWrapper(cursor: Cursor?): CursorWrapper(cursor) {
    fun getTask(): Task{
        var id = getInt(getColumnIndex(DBHelper.COLUMN_ID))
        var title = getString(getColumnIndex(DBHelper.COLUMN_TITLE))
        var priority = getString(getColumnIndex(DBHelper.COLUMN_PRIORITY))
        var state = getInt(getColumnIndex(DBHelper.COLUMN_STATE))
        var date = getString(getColumnIndex(DBHelper.COLUMN_DATE))

        var task = Task(title, priority, state, date, id)
        return task
    }
}