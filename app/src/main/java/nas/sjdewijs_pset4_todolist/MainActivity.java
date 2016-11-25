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

    String [] list = new String[] {
            "pancake",
            "powdersucker",
            "poofers"
            // tasks from database
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView tasksListView = (ListView) findViewById(R.id.tasks_listview);


        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        tasksListView.setAdapter(adapter);


        // TodoDatabaseHandler is a SQLiteOpenHelper class connecting to SQLite
        TodoDatabaseHandler handler = new TodoDatabaseHandler(this);
        // Get access to the underlying writeable database
        SQLiteDatabase db = handler.getWritableDatabase();
        // Query for items from the database and get a cursor back
        Cursor todoCursor = db.rawQuery("SELECT  * FROM todo_items", null);


        // Find ListView to populate
        ListView lvItems = (ListView) findViewById(R.id.lvItems);
        // Setup cursor adapter using cursor from last step
        TasksAdapter tasksAdapter = new TasksAdapter(this, tasksAdapter);
        // Attach cursor adapter to the ListView
        lvItems.setAdapter(todoAdapter);

    }

    Context context;
    //DBhelper DBhelper = new DBhelper(Context context)

    public void enteredTask(View view){
        // create variable of edittext with id add_task_et
        EditText taskEditText = (EditText) findViewById(R.id.add_task_et);

        if (taskEditText.length() != 0) {
            // convert entered words to separate strings
            String taskString = taskEditText.getText().toString();

            String hoi = "hoi";
            Log.d("Does it work?", hoi);
        }

        // show error message when no title is entered
        else {
            taskEditText.setError("Please enter a task");
        }
        // replace entered word with nothing, so textbox is emptied
        taskEditText.setText("");
    }


}
