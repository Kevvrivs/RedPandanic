package com.example.redpandanic;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import Database.DbConnection;
import android.app.Activity;
import android.os.Bundle;


public class ItemRecommenderActivity extends Activity{
	private MobileServiceClient mClient;
	private DbConnection dbConnection;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbConnection = new DbConnection();
		mClient = dbConnection.connectToAzure(this);

	}

}
