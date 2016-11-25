package nas.sjdewijs_pset4_todolist;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/*
*
 * Created by Sander de Wijs on 22-11-2016.
 * Retrieves the items out of DBhelper and then converts the task and inserts
 * it in to a ListView.
 * This adapter has been copied from https://github.com/codepath/android_guides/wiki/Populating-a-ListView-with-a-CursorAdapter
*/

public class TasksAdapter extends CursorAdapter {
    public TasksAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.activity_main, parent, false);
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