package Support;

import java.util.ArrayList;
import java.util.List;

import Model.WorkItem;

public class DisasterPlanSample {
	public List<WorkItem> plans;
	
	public DisasterPlanSample(){
		plans = new ArrayList<WorkItem>();
		fillData();
	}
	
	public List<WorkItem> getPlans() {
		return plans;
	}

	public void fillData(){
		WorkItem item = new WorkItem();
		item.setDescription("Prepare the important documents");
		item.setMemberId("null");
		item.setDone(false);
		plans.add(item);
		
		item = new WorkItem();
		item.setDescription("Prepare the emergency kits");
		item.setMemberId("null");
		item.setDone(false);
		plans.add(item);
		
		item = new WorkItem();
		item.setDescription("Turn off the electricity");
		item.setMemberId("null");
		item.setDone(false);
		plans.add(item);
		
		item = new WorkItem();
		item.setDescription("Turn off the gas stove");
		item.setMemberId("null");
		item.setDone(false);
		plans.add(item);
		
		item = new WorkItem();
		item.setDescription("Prepare your pet's food and medicine");
		item.setMemberId("null");
		item.setDone(false);
		plans.add(item);
	}
}
