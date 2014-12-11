package com.example.redpandanic;

import java.util.List;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

import Adapter.MemberAdapter;
import Database.DbConnection;
import Model.Member;
import Model.WorkItem;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MemberActivity extends Activity {
	private Member member;
	private EditText username;
	private Button btnAdd;
	private MobileServiceClient mClient;
	private ListView listMember;
	private MemberAdapter memberAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member);

		Intent i = getIntent();
		member = (Member) i.getSerializableExtra("user");
		mClient = DbConnection.connectToAzureService(this);
		username = (EditText) findViewById(R.id.text);
		listMember = (ListView) findViewById(R.id.listMember);
		memberAdapter = new MemberAdapter(this, R.layout.layout_rowmember);
		listMember.setAdapter(memberAdapter);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(new AddMemberListener());
		getMembers();

	}

	public void clearFields(){
		username.setText("");
	}
	public void getMembers() {

		mClient.getTable(Member.class).where().field("groupId")
				.eq(member.getGroupId())
				.execute(new TableQueryCallback<Member>() {

					@Override
					public void onCompleted(List<Member> items, int count,
							Exception exception, ServiceFilterResponse response) {

						// TODO Auto-generated method stub
						if (exception == null) {
							for (Member item : items) {
								memberAdapter.add(item);
							}
						} else {

						}

					}

				});

	}

	class AddMemberListener implements OnClickListener {

		public void onClick(View v) {
			String user = username.getText().toString();

			mClient.getTable(Member.class).where().field("username").eq(user)
					.execute(new TableQueryCallback<Member>() {

						public void onCompleted(List<Member> members,
								int count, Exception exception,
								ServiceFilterResponse response) {

							if (exception == null) {
								if (count == 0) {
									// show a message dialog here
								} else {
									memberAdapter.add(members.get(0));
								}
							} else {

							}
							clearFields();

						}

					});

		}

	}
}
