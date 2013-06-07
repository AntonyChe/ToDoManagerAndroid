package com.teamdev.todomanager;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;

import com.teamdev.beans.Task;
import com.teamdev.database.DataHandler;
import com.teamdev.database.Utils;

public class TaskListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list_activity);
        
        DataHandler handler = new DataHandler(getApplicationContext());
                      
        List<Task> tasksList = Utils.getInstance(handler).getAllTasks();
        ArrayAdapter<Task> taskAdapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_checked, tasksList);
        setListAdapter(taskAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.task_list_activity, menu);
        return true;
    }
    
}
