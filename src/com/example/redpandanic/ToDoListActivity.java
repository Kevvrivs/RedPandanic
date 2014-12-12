package com.example.redpandanic;

import java.util.List;

import org.dlsunetcentriclab.redpandanic.R;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

import Adapter.WorkListAdapter;
import Database.DbConnection;
import Model.Group;
import Model.Member;
import Model.WorkItem;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class ToDoListActivity extends Activity {
	private MobileServiceClient mClient;
	private EditText description;
	private Button btnAdd;
	private WorkListAdapter adapter;
	private Member member;
	private Spinner spinner;
	private ArrayAdapter<String> spinnerAdapter;
	private String selectedMember;
	//private String userId;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do_list);
		Intent i = getIntent();
		member = (Member) i.getSerializableExtra("user");
		// Initialize Connections
		mClient = DbConnection.connectToAzureService(this);
		description = (EditText) findViewById(R.id.text);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(new AddListListener());
		adapter = new WorkListAdapter(this, R.layout.layout_rowtodo);
		
		spinner = (Spinner)findViewById(R.id.spinnerChooser);
		spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		spinner.setAdapter(spinnerAdapter);
		spinner.setSelection(0);
		selectedMember = spinner.getItemAtPosition(0).toString();
		getMembers();

		ListView WorkList = (ListView) findViewById(R.id.toDoListItem);
		WorkList.setAdapter(adapter);
		getTasks();

	}


	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do_list, menu);
		return true;
	}

	public void getTasks() {

		mClient.getTable(WorkItem.class).where().field("groupId")
				.eq(member.getGroupId())
				.execute(new TableQueryCallback<WorkItem>() {

					@Override
					public void onCompleted(List<WorkItem> items, int count,
							Exception exception, ServiceFilterResponse response) {

						// TODO Auto-generated method stub
						if (exception == null) {
							for (WorkItem item : items) {
								adapter.add(item);
							}
						} else {

						}

					}

				});

	}
	
	public void getMembers(){
		mClient.getTable(Member.class).where().field("groupId")
		.eq(member.getGroupId())
		.execute(new TableQueryCallback<Member>() {

			public void onCompleted(List<Member> members, int count,
					Exception exception, ServiceFilterResponse response) {
				
				if (exception == null) {
					for(Member m:members){
						spinnerAdapter.add(m.getUsername());
					}
				}
			}
		});
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class SpinnerListener implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			selectedMember = parent.getItemAtPosition(position).toString();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		
	}

	class AddListListener implements OnClickListener {

		public void onClick(View v) {
			String desc = description.getText().toString();
			WorkItem work = new WorkItem();
			work.setDescription(desc);
			work.setDone(false);
			work.setGroupId(member.getGroupId());
			
			/*
			mClient.getTable(Member.class).where().field("username").eq(selectedMember).execute(new TableQueryCallback<Member>(){

				@Override
				public void onCompleted(List<Member> members, int position,
						Exception exception, ServiceFilterResponse response) {
					
					if(exception==null){
						Member m = members.get(0);
						userId = m.getMemberId();
					}
				}
			});*/
			
			work.setMemberId(selectedMember);
			
			mClient.getTable(WorkItem.class).insert(work,
					new TableOperationCallback<WorkItem>() {

						@Override
						public void onCompleted(WorkItem item,
								Exception exception,
								ServiceFilterResponse response) {
							if (exception == null) {
								Log.e("Add Work Item", "Success");
								adapter.add(item);
							} else {
								Log.e("Add Work Item", "Failure");
								exception.printStackTrace();
							}
						}
					});
		}

	}

}
