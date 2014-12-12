package com.example.redpandanic;

import com.example.redpandanic.GroupActivity.CreateGroupListener;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import org.dlsunetcentriclab.redpandanic.R;

import Adapter.InventoryAdapter;
import Adapter.ItemAdapter;
import Database.DbConnection;
import Model.Item;
import Model.Member;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class InventoryActivity extends Activity {
	
	private MobileServiceClient mClient;
	
	private EditText txtItem;
	private EditText txtCost;
	private EditText txtQuantity;
	private Button btnAdd;
	private ListView inventoryList;
	
	private Member member;
	
	private InventoryAdapter adapter;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory);
		
		mClient = DbConnection.connectToAzureService(this);
		Intent i = getIntent();
		member = (Member) i.getSerializableExtra("user");
	
		txtItem = (EditText) findViewById(R.id.itemText);
		txtCost = (EditText) findViewById(R.id.costText);
		txtQuantity = (EditText) findViewById(R.id.quantityText);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		inventoryList = (ListView) findViewById(R.id.inventoryItem);
		adapter = new InventoryAdapter(this,R.layout.layout_holyiteminventory);
		inventoryList.setAdapter(adapter);
		btnAdd.setOnClickListener(new btnAddListener());
	}
	
	class btnAddListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String name = txtItem.getText().toString();
			double quantity = Double.valueOf(txtQuantity.getText().toString());
			double cost = Double.valueOf(txtCost.getText().toString());
			
			Item item = new Item();
			item.setItemName(name);
			item.setCost(cost);
			item.setQuantity(quantity);
			item.setGroupId(member.getGroupId());
			
			mClient.getTable(Item.class).insert(item,new TableOperationCallback<Item>(){

				@Override
				public void onCompleted(Item item, Exception exception,
						ServiceFilterResponse response) {
					// TODO Auto-generated method stub
					if(exception == null){
						adapter.add(item);
					} else {
						// show a message dialog
					}
					
				}
				
			});
			
		}
		
	}
}
