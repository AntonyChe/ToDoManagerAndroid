package com.teamdev.todomanager;

import java.util.List;

import com.teamdev.beans.Task;
import com.teamdev.database.DataHandler;
import com.teamdev.database.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TaskListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list_activity);
        
        DataHandler handler = new DataHandler(getApplicationContext());
        List<Task> tasksList = Utils.getInstance(handler).getAllTasks();
        ArrayAdapter<Task> taskAdapter = new ArrayAdapter<Task>(getApplicationContext(), R.layout.task_list_activity, R.id.tasksList, tasksList);
        ListView taskListView = (ListView)findViewById(R.id.tasksList);
        taskListView.setAdapter(taskAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.task_list_activity, menu);
        return true;
    }
    
}
