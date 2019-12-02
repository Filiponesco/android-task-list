package com.example.listtodo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DB_NAME, factory, VERSION){
    companion object{
        const val DB_NAME = "ListTask.Db"
        const val VERSION = 1
        const val TABLE_NAME = "Tasks"
        const val COLUMN_ID = "TaskId"
        const val COLUMN_TITLE = "Title"
        const val COLUMN_PRIORITY = "Priority"
        const val COLUMN_STATE = "State"
        const val COLUMN_DATE = "Date"
        private fun contentValues(task: Task?): ContentValues {
            val values = ContentValues()
            values.put(COLUMN_TITLE, task?.title)
            values.put(COLUMN_PRIORITY, task?.priority)
            values.put(COLUMN_STATE, task?.state)
            values.put(COLUMN_DATE, task?.date)
            return values
        }
    }
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE " +
                TABLE_NAME + "( "
                + COLUMN_ID +" integer primary key autoincrement, "+
                COLUMN_TITLE + ", " +
                COLUMN_PRIORITY + ", " +
                COLUMN_STATE + ", " +
                COLUMN_DATE +
                ")")
        db.execSQL(CREATE_PRODUCTS_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }
    fun addTask(task: Task) {
        var values = Companion.contentValues(task)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    private fun queryTasks(): TaskCursorWrapper {
        val db = this.readableDatabase
        var rawQuery = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        return TaskCursorWrapper(rawQuery)
    }
    private fun queryTask(id: Int): TaskCursorWrapper{
        val db = this.readableDatabase
        var rawQuery = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $id", null)
        return TaskCursorWrapper(rawQuery)
    }
    fun getAllUsers(): MutableList<Task>{
        var cursor = queryTasks()
        var users = mutableListOf<Task>()
        try{
            cursor.moveToFirst()
            while(!cursor.isAfterLast){
                users.add(cursor.getTask())
                cursor.moveToNext()
            }
        } catch(e: Exception){
            //
        }
        finally{
            cursor.close()
        }
        return users
    }
    fun getUser(id: Int): Task?{
        var cursor = queryTask(id)
        try{
            if(cursor.count == 0)
            {
                return null
            }
            cursor.moveToFirst()
            return cursor.getTask()
        } finally{
            cursor.close()
        }
    }
    fun update(task: Task?): Boolean{
        val db = this.writableDatabase
        val values = Companion.contentValues(task)
        val success = db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(task?.id.toString())) //czemu to działa?
        db.close()
        return Integer.parseInt("$success") != -1
    }
    fun delete(task: Task?): Boolean{
        val db = this.writableDatabase
        val success = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(task?.id.toString()))
        db.close()
        return Integer.parseInt("$success") != -1
    }
}