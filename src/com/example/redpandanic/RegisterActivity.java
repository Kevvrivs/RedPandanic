package com.example.redpandanic;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import org.dlsunetcentriclab.redpandanic.R;

import Database.DbConnection;
import Model.Member;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity{
	private MobileServiceClient mClient;
	private EditText txtFullname;
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
		txtFullname = (EditText) findViewById(R.id.reguserFullName);
		txtUsername = (EditText)findViewById(R.id.reguserText);
		txtPassword = (EditText)findViewById(R.id.regpassText);
		txtConfirmPassword = (EditText) findViewById(R.id.confpassText);
		txtEmail = (EditText) findViewById(R.id.emailText);
		btnRegister = (Button) findViewById(R.id.registerButton);
		
		btnRegister.setOnClickListener(new RegisterListener());
	}
	
	public void Register(){
		
	}
	
	public void clearFields(){
		txtFullname.setText("");
		txtUsername.setText("");
		txtPassword.setText("");
		txtConfirmPassword.setText("");
		txtEmail.setText("");
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
						
						if(exception==null){
							createDialog().show();
							Log.e("Register", "Success");
							clearFields();
							//Go to next activity
						
							Intent i = new Intent(getApplicationContext(),GroupActivity.class);
							i.putExtra("user", newMember);
							startActivity(i);
						
						}
						else{
							Log.e("Add Member to DB", "Failure");
							exception.printStackTrace();
							
						}
						
					}
				
				});
			}else{
				Log.e("Register Status: ", "Failed");
				createDialog().show();
			}
		}
		
		public Member validateInput(){
			String membername = txtFullname.getText().toString();
			String username = txtUsername.getText().toString();
			String password = txtPassword.getText().toString();
			String confirmPassword = txtConfirmPassword.getText().toString();
			String email = txtEmail.getText().toString();
			statusMessage = "";
			
			if(membername.isEmpty()||username.isEmpty()||password.isEmpty()||confirmPassword.isEmpty()||email.isEmpty()){
				statusMessage += "Please fill out all fields. \n";
				return null;
			}
			
			if(!membername.matches("[a-zA-Z]+$")){
				statusMessage += "Member name must only contain letters. \n";
				return null;
			}
			
			if(password.length()<6){
				statusMessage += "Password must have at least 6 characters. \n";
				return null;
			}
			
			if(!confirmPassword.equals(password)){
				statusMessage += "Password fields do not match. \n";
				return null;
			}
			
			if(!email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")){
				statusMessage += "Email is not valid. \n";
				return null;
			};
			
			statusMessage = "Successfully created an account";
			return new Member(membername,username,confirmPassword,email);
		}
		
	}
}


 