package com.example.redpandanic;

import com.example.redpandanic.ItemRecommenderActivity.RecommendListener;

import Database.DbConnection;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class LoginActivity extends Activity {
	private Button btnRegister;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		btnRegister = (Button)findViewById(R.id.registerButton);
		btnRegister.setOnClickListener(new registerListener());
	}
	class registerListener implements OnClickListener{

		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
			startActivity(i);
		}
		
	}
}
