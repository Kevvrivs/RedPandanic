package Adapter;

import com.example.redpandanic.R;

import Model.WorkItem;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class WorkListAdapter extends ArrayAdapter<WorkItem>{
	Context mContext;
	int mLayoutResourceId;
	
	public WorkListAdapter(Context context, int layoutResourceId){
		
		super(context, layoutResourceId);

		mContext = context;
		mLayoutResourceId = layoutResourceId;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		View row = convertView;
		final WorkItem currentItem = getItem(position);
		
		if (row == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(mLayoutResourceId, parent, false);
		}
		
		final CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkToDoComplete);
		final TextView  textDescription = (TextView) row.findViewById(R.id.textDescription);
		checkBox.setChecked(false);
		checkBox.setEnabled(true);
		textDescription.setText(currentItem.getDescription());
		
		row.setTag(currentItem);
		
		return row;
	}
}