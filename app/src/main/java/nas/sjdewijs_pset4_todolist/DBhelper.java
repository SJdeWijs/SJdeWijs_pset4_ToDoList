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

/**
 * Created by Sander de Wijs on 21-11-2016.
 */

public class DBhelper extends SQLiteOpenHelper {

    // create database, buttons, EditTexts and listview
    //SQLiteDatabase tasksDB = null;
    //ListView tasksListView;
    //Button addTaskButton; deleteTaskButton;
    //EditText taskEditText;

    // onCreate
    @Override
    public void onCreate(SQLiteDatabase tasksDB) {

        // create table
        String CREATE_TABLE = "CREATE TABLE " + TABLE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                task_id + " TEXT)";
        tasksDB.execSQL(CREATE_TABLE);
    }

    // set fields of database schema
    private static final String DATABASE_NAME = "tasksDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE = "tasks";

    String task_id = "task";

    // constructor
    public DBhelper (Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase tasksDB, int i, int i1) {
        // delete current table, then create table and fill in with new data
        tasksDB.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(tasksDB);
    }

    // create task (insert in database) (Crud)
    public void create (String task){
        SQLiteDatabase tasksDB = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(task_id, task);
        tasksDB.insert(TABLE, null, values);
        tasksDB.close();
    }

    // read task (cRud)
    public ArrayList<HashMap<String, String>> read() {
        SQLiteDatabase tasksDB = getReadableDatabase();
        String query = "SELECT _id , " + task_id + "FROM " + TABLE ;
        ArrayList<HashMap<String, String>> phonebook = new ArrayList<>();
        Cursor cursor = tasksDB.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> taskdata = new HashMap<>();
                taskdata.put("id", cursor.getString(cursor.getColumnIndex("_id")));
                taskdata.put("task", cursor.getString(cursor.getColumnIndex(task_id)));
                phonebook.add(taskdata);
            } while (cursor.moveToNext());
        }
        cursor.close();
        tasksDB.close();
        return phonebook;

    }

    public Cursor fetch() {
        String[] columns = new String[] { DBhelper._ID, DBhelper.SUBJECT, DBhelper.DESC };
        Cursor cursor = tasks.query(DBhelper.TABLE_NAME, columns, null, null, null, null, null);
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


