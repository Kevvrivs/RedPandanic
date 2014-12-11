package com.example.redpandanic;

import Model.Member;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends Activity{

	Member user;
	String userString;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		Intent i = getIntent();
		user = (Member) i.getSerializableExtra("user");
		userString = "Welcome, "+user.getUsername();
		Button btnChecklist = (Button) findViewById(R.id.checklistButton);
		TextView tx = (TextView) findViewById(R.id.messageUser);
		tx.setText(userString);
		btnChecklist.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),ItemRecommenderActivity.class);
				i.putExtra("user", user);
				startActivity(i);
				
			}
			
		});
		Button btnTodo = (Button) findViewById(R.id.disasterButton);
		btnTodo.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),ToDoListActivity.class);
				i.putExtra("user", user);
				startActivity(i);
			}
			
		});
		
		Button btnMember = (Button) findViewById(R.id.memberButton);
		btnMember.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),MemberActivity.class);
				i.putExtra("user", user);
				startActivity(i);
			}
			
		});
		
		Button btnInventory = (Button) findViewById(R.id.inventoryButton);
		btnInventory.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),InventoryActivity.class);
				i.putExtra("user", user);
				startActivity(i);
			}
			
			
		});
		
		Button btnLogout = (Button) findViewById(R.id.logoutButton);
		btnLogout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				user = null;
				Intent i = new Intent(getApplicationContext(),LoginActivity.class);
				startActivity(i);
				
			}
			
		});
	
		
	}
	
}