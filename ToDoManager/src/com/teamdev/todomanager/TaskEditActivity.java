package com.teamdev.todomanager;

import java.util.Calendar;

import com.teamdev.beans.IState;
import com.teamdev.beans.Task;
import com.teamdev.database.DataHandler;
import com.teamdev.database.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

public class TaskEditActivity extends Activity {
	
	private Task task;
	private DataHandler handler;
	private Context context;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_edit);
		
		handler = new DataHandler(getApplicationContext());
		int id = getIntent().getIntExtra("taskId", -1);
		context = getApplicationContext();
		
		Button cancel = (Button)findViewById(R.id.b_cancel);
		cancel.setOnClickListener(new ButtonCancel());
		
		Button ok = (Button)findViewById(R.id.b_ok);
		ok.setOnClickListener(new ButtonOk());
		
		if (id != -1) {
			task = Utils.getInstance(handler).getTask(id);
			
			TextView title = (TextView)findViewById(R.id.title);
			title.setText(task.getTitle());
			
			TextView description = (TextView)findViewById(R.id.description);
			description.setText(task.getDescription());
			
			TextView creationDate = (TextView)findViewById(R.id.creation_date);
			creationDate.setText(creationDate.getText() + " " + (task.getCreationDate().get(Calendar.DAY_OF_MONTH) + "." + (task.getCreationDate().get(Calendar.MONTH)+1) + "." +
								 task.getCreationDate().get(Calendar.YEAR)));
			
			TextView changedDate = (TextView)findViewById(R.id.changed_date);
			changedDate.setText(changedDate.getText() + " " + (task.getLastChangedDate().get(Calendar.DAY_OF_MONTH) + "." + (task.getLastChangedDate().get(Calendar.MONTH)+1) + "." +
								 task.getLastChangedDate().get(Calendar.YEAR)));
			
			CheckBox done = (CheckBox)findViewById(R.id.chk_done);
			done.setChecked(IState.LBL_DONE.equals(task.getState()) ? true : false);
						
			Button delete = (Button)findViewById(R.id.b_delete);
			delete.setOnClickListener(new DeleteButton());
			
		}
		else {
			Button delete = (Button)findViewById(R.id.b_delete);
			delete.setEnabled(false);
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_task_edit, menu);
		return true;
	}
	
	private class ButtonCancel implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(TaskEditActivity.this, TaskListActivity.class);
			startActivity(intent);	
		}
	}
	
	private class ButtonOk implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (((TextView)findViewById(R.id.title)).getText().length() == 0 ) {
				Toast.makeText(context, "Enter title", Toast.LENGTH_LONG).show();
				return;
			}
			
			if (((TextView)findViewById(R.id.description)).getText().length() == 0) {
				Toast.makeText(context, "Enter description", Toast.LENGTH_LONG).show();
				return;
			}
			
			if (task != null) {
				task.setTitle(((TextView)findViewById(R.id.title)).getText().toString());
				task.setDescription(((TextView)findViewById(R.id.description)).getText().toString());
				task.setLastChangedDate(Calendar.getInstance());
				task.setState(((CheckBox)findViewById(R.id.chk_done)).isChecked() ? IState.LBL_DONE : IState.LBL_NOT_DONE);
				Utils.getInstance(handler).editTask(task);
			}
			else {
				task = new Task();
				task.setTitle(((TextView)findViewById(R.id.title)).getText().toString());
				task.setDescription(((TextView)findViewById(R.id.description)).getText().toString());
				task.setCreationDate(Calendar.getInstance());
				task.setLastChangedDate(Calendar.getInstance());
				task.setState(((CheckBox)findViewById(R.id.chk_done)).isChecked() ? IState.LBL_DONE : IState.LBL_NOT_DONE);
				Utils.getInstance(handler).createNewTask(task);
			}
			
			Intent intent = new Intent(TaskEditActivity.this, TaskListActivity.class);
			startActivity(intent);
		}
		
	}
	
	private class DeleteButton implements OnClickListener {

		@Override
		public void onClick(View v) {
			Utils.getInstance(handler).deleteTask(task);
			Intent intent = new Intent(TaskEditActivity.this, TaskListActivity.class);
			startActivity(intent);
		}
		
	}

}
