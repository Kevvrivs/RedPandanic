package Support;

import java.util.ArrayList;
import java.util.List;

import Model.Item;

public class ItemTable {
	private List<Item> items;
	
	// to be replaced by data from db
	public List<Item> getListItems(){
		items = new ArrayList<Item>();
		
		Item item = new Item();
		item.setItemName("Instant Noodles");
		item.setQuantity(2);
		item.setCost(25);
		item.setImportance(1);
		items.add(item);
		
		item = new Item();
		item.setItemName("Bottled Water");
		item.setQuantity(3);
		item.setCost(7);
		item.setImportance(1);
		items.add(item);
		
		item = new Item();
		item.setItemName("Canned Sardines");
		item.setQuantity(2);
		item.setCost(20);
		item.setImportance(1);
		items.add(item);
		
		item = new Item();
		item.setItemName("Crackers");
		item.setQuantity(2);
		item.setCost(7);
		item.setImportance(1);
		items.add(item);
		
		item = new Item();
		item.setItemName("Radio");
		item.setQuantity(1);
		item.setCost(200);
		item.setImportance(2);
		items.add(item);
		
		item = new Item();
		item.setItemName("Swiss Army Knife");
		item.setQuantity(1);
		item.setCost(500);
		item.setImportance(2);
		items.add(item);
		
		item = new Item();
		item.setItemName("Blanket");
		item.setQuantity(1);
		item.setCost(200);
		item.setImportance(2);
		items.add(item);
		
		item = new Item();
		item.setItemName("Flashlight");
		item.setQuantity(1);
		item.setCost(150);
		item.setImportance(1);
		items.add(item);
		
		item = new Item();
		item.setItemName("Diapers");
		item.setQuantity(3);
		item.setCost(10);
		item.setImportance(3);
		items.add(item);
		
		return items;
	}
}
