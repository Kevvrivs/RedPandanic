package com.example.redpandanic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;

import Adapter.ItemAdapter;
import Database.DbConnection;
import Model.Item;
import Support.ItemTable;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ItemRecommenderActivity extends Activity {
	private MobileServiceClient mClient;
	private ItemAdapter adapter;
	private ListView listItems;
	private EditText txtQuantity;
	private Button btnEdit;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checklist);
		mClient = DbConnection.connectToAzureService(this);
		btnEdit = (Button) findViewById(R.id.btnAdd);
		btnEdit.setOnClickListener(new RecommendListener());
		txtQuantity = (EditText) findViewById(R.id.itemName);
		adapter = new ItemAdapter(this,R.layout.layout_rowitem);
		
		listItems = (ListView) findViewById(R.id.listItem);
		listItems.setAdapter(adapter);
		

	
	}

	public void clearFields(){
		txtQuantity.setText("");
	}
	class RecommendListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.e("Money", txtQuantity.getText().toString());
			String value = txtQuantity.getText().toString();
			
			Double money = 0.0;
			if(!value.isEmpty()){
				money = Double.valueOf(txtQuantity.getText().toString());
			}
			
			
			HashMap<String,Item> recommend = recommendItems(money,4);
			Set<Entry<String,Item>> itSet = recommend.entrySet();
			adapter.clear();
			for(Entry<String,Item> item: itSet){
				adapter.add(item.getValue());
			}
			clearFields();
		}
		
		public HashMap<String,Item> recommendItems(double money, int numOfMember){
			double budget = money;
			int day = 1;
			ItemTable table = new ItemTable();
			List<Item> items = table.getListItems();
			HashMap<String, Item> recommend = new HashMap<String, Item>();
			
			boolean isBuy = false;
			while(budget > 0 && !isBuy){
				isBuy = false;
				for(Item item: items){
					if(item.getImportance() == 2){
						if(day < 2){
							if(budget-(item.getCost()*numOfMember) >= 0){
								budget -= item.getCost()*numOfMember;
								recommend.put(item.getItemName(), item);
								isBuy = true;
							} 
						} 
					} else {
						if(budget-(item.getCost()*numOfMember) >= 0){
							budget -= item.getCost()*numOfMember;
							Item r = null;
							if((r = recommend.get(item.getItemName())) != null){
								r.setQuantity(r.getQuantity()+item.getQuantity());
								recommend.put(r.getItemName(), r);
							} else {
								recommend.put(item.getItemName(),item);
							}
							isBuy = true;
						} 
					}
				}
				day++;
			}
			return recommend;
			
		}
		

	}
}
