package com.example.redpandanic;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;

import Database.DbConnection;
import Model.Member;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity{
	private MobileServiceClient mClient;
	private EditText txtUsername;
	private EditText txtPassword;
	private EditText txtConfirmPassword;
	private EditText txtEmail;
	private Button btnRegister;
	private String statusMessage;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		//Initialize Connections
		mClient = DbConnection.connectToAzureService(this);
		txtUsername = (EditText)findViewById(R.id.reguserText);
		txtPassword = (EditText)findViewById(R.id.regpassText);
		txtConfirmPassword = (EditText) findViewById(R.id.confpassText);
		txtEmail = (EditText) findViewById(R.id.emailText);
		btnRegister = (Button) findViewById(R.id.registerButton);
		
		btnRegister.setOnClickListener(new RegisterListener());
	}
	
	public void Register(){
		
	}
	
	public AlertDialog createDialog(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage(statusMessage);
		alertDialogBuilder.setTitle("Message");
	
		alertDialogBuilder.setNeutralButton(R.string.ok,new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		
		});
		return alertDialogBuilder.create();
	}
	
	class RegisterListener implements OnClickListener{

		public void onClick(View v) {
			
			Member newMember = null;
			
			if((newMember = validateInput())!=null){
				Log.e("Register Status: ", "Success");
				
				mClient.getTable(Member.class).insert(newMember,new TableOperationCallback<Member>() {

					public void onCompleted(Member newMember, Exception exception,
							ServiceFilterResponse response) {
						
						if(exception!=null){
							createDialog().show();
							Log.e("Register", "Success");
							
							//Go to next activity
							/*
							Intent i = new Intent(getApplicationContext(),MenuActivity.class);
							i.putExtra("user", registerUser);
							startActivity(i);
							*/
						}
						else{
							Log.e("Add Member to DB", "Failure");
						}
						
					}
				
				});
			}else{
				Log.e("Register Status: ", "Failed");
				createDialog().show();
			}
		}
		
		public Member validateInput(){
			String username = txtUsername.getText().toString();
			String password = txtPassword.getText().toString();
			String confirmPassword = txtConfirmPassword.getText().toString();
			String email = txtEmail.getText().toString();
			
			if(username.isEmpty()){
				statusMessage += "Username field is required \n";
				return null;
			}
			
			if(password.isEmpty()){
				statusMessage += "Password field is required \n";
				return null;
			}
			
			if(confirmPassword.isEmpty()||!confirmPassword.equals(password)){
				statusMessage += "Confirm Password field is required \n";
				return null;
			}
			
			if(email.isEmpty()){
				statusMessage += "Password required \n";
				return null;
			}
			statusMessage = "Successfully created an account";
			return new Member(username,password,confirmPassword,email);
		}
		
	}
}


