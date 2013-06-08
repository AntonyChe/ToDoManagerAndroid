package com.teamdev.todomanager;

import java.util.Comparator;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.teamdev.beans.IState;
import com.teamdev.beans.Task;
import com.teamdev.database.DataHandler;
import com.teamdev.database.Utils;

public class TaskListActivity extends ListActivity {

	private List<Task> tasksList;
	private ArrayAdapter<Task> taskAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list_activity);
               
        DataHandler handler = new DataHandler(getApplicationContext());
                      
        tasksList = Utils.getInstance(handler).getAllTasks();
        taskAdapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_checked, tasksList);
        setListAdapter(taskAdapter);
        this.getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        for (int i = 0; i<tasksList.size(); i++) {
        	if (IState.LBL_DONE.equals(tasksList.get(i).getState())) {
        		this.getListView().setItemChecked(i, true);
        	}
        } 
        
       
        Button add = (Button)findViewById(R.id.addTask);
        add.setOnClickListener(new ButtonAdd());
        
        Button sort = (Button)findViewById(R.id.sort);
        sort.setOnClickListener(new SortButton(this.getListView()));
    }
    
    @Override
    public void onListItemClick(ListView list, View view, int position, long id) {
    	
    	Task task = (Task)list.getItemAtPosition(position);
    	Intent intent = new Intent(TaskListActivity.this, TaskEditActivity.class);
    	view.setClickable(false);
    	this.getListView().setItemChecked(position, IState.LBL_DONE.equals(task.getState()) ? true : false);
    	intent.putExtra("taskId", task.getId());
    	startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.task_list_activity, menu);
        return true;
    }
    
    private class ButtonAdd implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(TaskListActivity.this, TaskEditActivity.class);
			startActivity(intent);
		}
    	
    }
    
    private class SortButton implements OnClickListener {
    	
    	private boolean in_dec = false;
    	private ListView listView;
    	
    	public SortButton(ListView view) {
			listView = view;
		}
    	
		@Override
		public void onClick(View v) {
			
			in_dec = !in_dec;
			
			
			taskAdapter.sort(new Comparator<Task>() {
				
				@Override
				public int compare(Task lhs, Task rhs) {
					if (in_dec)
						return (lhs.getTitle().compareTo(rhs.getTitle()));
					else
						return (lhs.getTitle().compareTo(rhs.getTitle()))*(-1);
				}
			});
			
			for (int i = 0; i<tasksList.size(); i++) {
	        	if (IState.LBL_DONE.equals(tasksList.get(i).getState())) {
	        		listView.setItemChecked(i, true);
	        	}
	        	else {
	        		listView.setItemChecked(i, false);
	        	}
	        } 
		}
    }
    
        
}
