package com.example.redpandanic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;

import Database.DbConnection;
import Model.Item;
import Support.ItemTable;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class ItemRecommenderActivity extends Activity {
	private MobileServiceClient mClient;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checklist);
		mClient = DbConnection.connectToAzureService(this);
		//addItem();
		List<Item> recommend = recommendItems(2000,4);
		double sum = 0;
		
		for(Item i: recommend){
			//Log.e(i.getItemName(), Double.toString(i.getCost()));
			sum += i.getCost();
		}
		
		//Log.e("Sum", Double.toString(sum));

	}

	public List<Item> recommendItems(double money, int numOfMember){
		double budget = money;
		int day = 1;
		ItemTable table = new ItemTable();
		List<Item> items = table.getListItems();
		List<Item> recommend = new ArrayList<Item>();
		boolean isBuy = false;
		while(budget > 0 && !isBuy){
			isBuy = false;
			for(Item item: items){
				if(item.getImportance() == 2){
					if(day < 2){
						if(budget-(item.getCost()*numOfMember) >= 0){
							recommend.add(item);
							isBuy = true;
						} 
					} 
				} else {
					if(budget-(item.getCost()*numOfMember) >= 0){
						recommend.add(item);
						isBuy = true;
					} 
				}
			}
		}
		return recommend;
		
	}
	public void addItem() {

		Item item = new Item();
		item.setItemName("Instant Noodles");
		item.setQuantity(3);
		item.setCost(7);
		item.setImportance(1);

		mClient.getTable(Item.class).insert(item,
				new TableOperationCallback<Item>() {

					@Override
					public void onCompleted(Item item, Exception exception,
							ServiceFilterResponse response) {
						// TODO Auto-generated method stub
						if (exception == null) {
							Log.e("Adding Item", "Successful");
						} else {
							Log.e("Adding Item", "Failed");
						}

					}

				});

	}
}
