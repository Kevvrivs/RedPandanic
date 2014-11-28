package com.example.redpandanic;

import java.net.MalformedURLException;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;

import Database.DbConnection;
import Model.Item;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class ItemRecommenderActivity extends Activity{
	private MobileServiceClient mClient;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checklist);
		mClient = DbConnection.connectToAzureService(this);
		addItem();

	}
	
	public void addItem(){
		Item item = new Item();
		item.setItemName("Bottled Water");
		item.setQuantity(2);
		item.setImportance(1);
		
		mClient.getTable(Item.class).insert(item,new TableOperationCallback<Item>() {

			@Override
			public void onCompleted(Item item, Exception exception,
					ServiceFilterResponse response) {
				// TODO Auto-generated method stub
				if(exception == null){
					Log.e("Adding Item", "Successful");
				} else {
					Log.e("Adding Item", "Failed");
				}
				
			}

			});
	}

}
