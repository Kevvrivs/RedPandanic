package com.example.redpandanic;

import java.util.List;

import com.example.redpandanic.RegisterActivity.RegisterListener;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

import Database.DbConnection;
import Model.Group;
import Model.Member;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class GroupActivity extends Activity{
	private MobileServiceClient mClient;
	private EditText txtGroupname;
	private Button btnCreate;
	private Button btnSearch;
	private ListView groupList;
	private ArrayAdapter groupAdapter;
	private List<String> groups;
	private Member member;
	private MobileServiceTable<Member> mMemberTable;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group);
		Intent i = getIntent();
		member = (Member) i.getSerializableExtra("user");
		//Initialize Connections
		mClient = DbConnection.connectToAzureService(this);
		txtGroupname = (EditText)findViewById(R.id.txtSearchGroup);
		btnCreate = (Button) findViewById(R.id.createButton);
		btnSearch = (Button) findViewById(R.id.joinButton);
		btnCreate.setOnClickListener(new CreateGroupListener());
		btnSearch.setOnClickListener(new JoinGroupListener());
		groupList = (ListView) findViewById(R.id.groupList);
		//adapter = new ArrayAdapter();
	}
	
	public void clearFields(){
		txtGroupname.setText("");
	}
	
	public AlertDialog createDialog(String s){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage(s);
		alertDialogBuilder.setTitle("Message");
	
		alertDialogBuilder.setNeutralButton(R.string.ok,new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		
		});
		return alertDialogBuilder.create();
	}
	
	public void getGroups(){
		mClient.getTable(Group.class).execute(new TableQueryCallback<Group>(){

			@Override
			public void onCompleted(List<Group> groups, int arg1, Exception exception,
					ServiceFilterResponse response) {
				// TODO Auto-generated method stub
			
				
			}
			
		});
	}
	
	class JoinGroupListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			String groupname = txtGroupname.getText().toString();
			mClient.getTable(Group.class).where().field("name")
			.eq(groupname).execute(new TableQueryCallback<Group>(){

				public void onCompleted(List<Group> groups, int position,
						Exception exception, ServiceFilterResponse response) {
					
					if(exception == null){
						if(groups.size()>0){
							final Group g = groups.get(0);
							member.setGroupId(g.getGroupId());
							mClient.getTable(Member.class).update(member,new TableOperationCallback<Member>() {
						        public void onCompleted(Member entity, Exception exception, ServiceFilterResponse response) {
						        	if (exception == null) {
						        		Log.e("Update GroupId","Success");
						        		createDialog("Succesfully joined group " + g.getName()).show();
						        		Intent i = new Intent(getApplicationContext(),MenuActivity.class);
										i.putExtra("user", member);
										startActivity(i);
						        	}
						        	else{
										Log.e("Search Group", "Failure");
										exception.printStackTrace();
										
									}
						        }
							});
							
						}
						else{
							createDialog("Group does not exist.").show();
						}
					}else{
						Log.e("Search Group", "Failure");
						exception.printStackTrace();
						
					}
					
				}
				
			});
			
		}
		
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
				public void onCompleted(final Group item, Exception exception,
						ServiceFilterResponse service) {
					// TODO Auto-generated method stub
					if(exception == null){
						Log.e("Message (group)", "Add group successful" + item.getGroupId());
						clearFields();
						member.setGroupId(item.getGroupId());
						
						mMemberTable = mClient.getTable(Member.class);
						mMemberTable.update(member, new TableOperationCallback<Member>(){

							@Override
							public void onCompleted(Member member,
									Exception exception, ServiceFilterResponse service) {
								// TODO Auto-generated method stub
								if(exception==null){
									Log.e("Message Update", "Update is successful");
									createDialog("Succesfully created group " + item.getName()).show();
									Intent i = new Intent(getApplicationContext(),MenuActivity.class);
									i.putExtra("user", member);
									startActivity(i);
								}
							}
							
						});
						
						
					} else {
						Log.e("Message (group)", "Add group failed");
					}
					
				}
				
			});
		}
		
	}
}
