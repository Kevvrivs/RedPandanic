package com.example.redpandanic;

import java.util.List;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

import Adapter.WorkListAdapter;
import Database.DbConnection;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ToDoListActivity extends Activity {
	private MobileServiceClient mClient;
	private EditText description;
	private Button btnAdd;
	private WorkListAdapter adapter;
	private Member member;

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

	class AddListListener implements OnClickListener {

		public void onClick(View v) {
			String desc = description.getText().toString();
			WorkItem work = new WorkItem();
			work.setDescription(desc);
			work.setDone(false);
			work.setGroupId(member.getGroupId());
			work.setMemberId("null");
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
