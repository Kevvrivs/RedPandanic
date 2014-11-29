package com.example.redpandanic;

import com.example.redpandanic.RegisterActivity.RegisterListener;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;

import Database.DbConnection;
import Model.Group;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class GroupActivity extends Activity{
	private MobileServiceClient mClient;
	private EditText txtGroupname;
	private Button btnCreate;
	private Button btnSearch;

	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group);
		
		//Initialize Connections
		mClient = DbConnection.connectToAzureService(this);
		txtGroupname = (EditText)findViewById(R.id.txtGroupName);
		btnCreate = (Button) findViewById(R.id.btnCreate);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		
		btnCreate.setOnClickListener(new CreateGroupListener());
	}
	
	class CreateGroupListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String groupname = txtGroupname.getText().toString();
			Group group = new Group();
			group.setName(groupname);
			mClient.getTable(Group.class).insert(group, new TableOperationCallback<Group>(){

				@Override
				public void onCompleted(Group item, Exception exception,
						ServiceFilterResponse service) {
					// TODO Auto-generated method stub
					if(exception == null){
						Log.e("Message (group)", "Add group successful");
					} else {
						Log.e("Message (group)", "Add group failed");
					}
					
				}
				
			});
		}
		
	}
}
