package nas.sjdewijs_pset4_todolist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView tasksListView;
    DBhelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tasksListView = (ListView) findViewById(R.id.tasks_listview);
        showTasks();
    }

    public class TasksAdapter extends CursorAdapter {

        public TasksAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        // The newView method is used to inflate a new view and return it,
        // you don't bind any data to the view at this point.
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.listview_textview_layout, parent, false);
        }
        // The bindView method is used to bind all data to a given view
        // such as setting the text on a TextView.
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Find fields to populate in inflated template
            TextView taskTextView = (TextView) view.findViewById(R.id.task_textview);
            // Extract properties from cursor
            String taskString = cursor.getString(cursor.getColumnIndexOrThrow("task"));
            // Populate fields with extracted properties
            taskTextView.setText(taskString);
        }
    }

    public void AddTaskToList(View view) {
        // create variable of edittext with id add_task_et
        EditText taskEditText = (EditText) findViewById(R.id.add_task_et);

        DBhelper dbHelp = new DBhelper(this);

        if (taskEditText.getText().length() != 0) {
            // convert entered words to separate strings
            String taskString = taskEditText.getText().toString();

            dbHelp = new DBhelper(this);
            dbHelp.create(taskString);
            dbHelp.close();
        }
        // show error message when no task is entered
        else {
            taskEditText.setError("Please enter a task");
        }
        // replace entered word with nothing, so textbox is emptied
        taskEditText.setText("");
        showTasks();
    }

    public void showTasks() {
        final DBhelper dbHelp = new DBhelper(this);
        Cursor cursor = dbHelp.read();

        TasksAdapter tasksAdapter = new TasksAdapter(this, cursor);
        tasksListView.setAdapter(tasksAdapter);

        dbHelp.close();

        tasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Object taskObject = tasksListView.getItemAtPosition(position);

                TextView task = (TextView) findViewById(R.id.task_textview);
                task.setPaintFlags(task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                //dbHelp = new DBhelper(this);
                //dbHelp.update();
                //dbHelp.close();

                Toast.makeText(MainActivity.this, "Task done!", Toast.LENGTH_SHORT).show();
            }
        });


        tasksListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Item Deleted", Toast.LENGTH_SHORT).show();

                TextView task = (TextView) findViewById(R.id.task_textview);

                LinearLayout linearLayoutTV = (LinearLayout) findViewById(R.id.linearlayout_textview);
                linearLayoutTV.removeView(task);

                //String taskString = TextView.getText().toString();
                //dbHelp.delete(task);
                //dbHelp.close();

                return true;
            }
        });
    }
}