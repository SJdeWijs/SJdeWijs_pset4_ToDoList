package nas.sjdewijs_pset4_todolist;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;
/*
*
* Created by Sander de Wijs on 21-11-2016.
*/

public class DBhelper extends SQLiteOpenHelper {

    // set fields of database schema
    private static final String DATABASE_NAME = "tasksDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE = "tasks";

    String task_id = "task";

    // constructor
    public DBhelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // create table
        String CREATE_TABLE = "CREATE TABLE " + TABLE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                task_id + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // delete current table, then create table and fill in with new data
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(sqLiteDatabase);
    }

    // create task and insert in database (Crud)
    public void create (String task){
        SQLiteDatabase tasksDB = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(task_id, task);
        tasksDB.insert(TABLE, null, values);
        tasksDB.close();
    }

/*    // read task (cRud)
    public ArrayList<String> fetch() {
        String query = "SELECT _id , " + task_id + "FROM " + TABLE ;
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase tasksDB = getReadableDatabase();
        Cursor cursor = tasksDB.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String taskData;
                taskData.setId(cursor.getString(cursor.getColumnIndex("_id")));


                HashMap<String, String> taskdata = new HashMap<>();
                taskdata.put("id", cursor.getString(cursor.getColumnIndex("_id")));
                taskdata.put("task", cursor.getString(cursor.getColumnIndex(task_id)));
                taskList.add(taskData);
            } while (cursor.moveToNext());
        }
        cursor.close();
        tasksDB.close();
        return taskList;

    }*/


    public Cursor read() {
        SQLiteDatabase tasksDB = getReadableDatabase();
        String[] columns = new String[] { "_id", "task_id" };
        String query = "SELECT _id , " + task_id + "FROM " + TABLE ;
        // Query for items from the database and get a cursor back
        //Cursor todoCursor = db.rawQuery("SELECT  * FROM todo_items", null);
        Cursor cursor = tasksDB.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // update task (crUd)
    public void update(String task) {
        SQLiteDatabase tasksDB = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(task_id, task);
        tasksDB.update(TABLE, values, "_id = ? ", new String[]{String.valueOf(task_id)});
        tasksDB.close();
    }

    // delete task (cruD)
    public void delete(int id) {
        SQLiteDatabase tasksDB = getWritableDatabase();
        tasksDB.delete(TABLE, " _id = ? ", new String[]{String.valueOf(id)});
        tasksDB.close();
    }

}