package nas.sjdewijs_pset4_todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    ListView tasksListView;
    DBhelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tasksListView = (ListView) findViewById(R.id.tasks_listview);

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        tasksListView.setAdapter(adapter);

    }

    public void AddTaskToList(View view) {
        // create variable of edittext with id add_task_et
        EditText taskEditText = (EditText) findViewById(R.id.add_task_et);

        if (taskEditText.getText().length() != 0) {
            // convert entered words to separate strings
            String taskString = taskEditText.getText().toString();

            dbHelper = new DBhelper(this);
            dbHelper.create(taskString);

            Cursor cursor = dbHelper.read();

            String hoi = "hoi";
            Log.d("Does it work?", hoi);
        }
        // show error message when no task is entered
        else {
            taskEditText.setError("Please enter a task");
        }
        // replace entered word with nothing, so textbox is emptied
        taskEditText.setText("");

    }
}