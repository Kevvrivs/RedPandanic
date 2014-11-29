package com.example.redpandanic;

import java.util.List;

import com.example.redpandanic.ItemRecommenderActivity.RecommendListener;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

import Database.DbConnection;
import Model.Member;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class LoginActivity extends Activity {
	private MobileServiceClient mClient;
	private Button btnRegister;
	private Button btnLogin;
	private EditText txtUsername;
	private EditText txtPassword;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		mClient = DbConnection.connectToAzureService(this);
		btnRegister = (Button)findViewById(R.id.registerButton);
		btnRegister.setOnClickListener(new registerListener());
		
		btnLogin = (Button)findViewById(R.id.Login);
		btnLogin.setOnClickListener(new loginListener());
		txtUsername = (EditText)findViewById(R.id.txtUser);
		txtPassword = (EditText)findViewById(R.id.txtPassword);
	}
	
	class loginListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			String username = txtUsername.getText().toString();
			String password = txtPassword.getText().toString();
			
			MobileServiceTable<Member> memberTable = mClient.getTable(Member.class);
			Log.e("HELLO", "IM IN");
			memberTable.where().field("username").eq(username).and()
			.field("password").eq(password)
			.execute(new TableQueryCallback<Member>(){

				@Override
				public void onCompleted(List<Member> member, int position,
						Exception exception, ServiceFilterResponse response) {
					if(exception==null){
						if (member.size() == 0) {
							Log.e("Login","Failure");
						}
						else{
							Log.e("Login","Success");
							Member mem = member.get(0);
							Log.e("Login",mem.getMemberId());
							if(mem.getGroupId().equals("a")){
								//Go to...
								Log.e("WHERE","GO TO GROUP PAGE");
								Intent i = new Intent(getApplicationContext(),GroupActivity.class);
								i.putExtra("user", mem);
								startActivity(i);
							}
							else{
								//Do something else
								Log.e("WHERE","GO TO HOME");
								Intent i = new Intent(getApplicationContext(),MenuActivity.class);
								i.putExtra("user", mem);
								startActivity(i);
							}
						}
					}
				}
			});
		}
		
	}
	class registerListener implements OnClickListener{

		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
			startActivity(i);
		}
		
	}
}
