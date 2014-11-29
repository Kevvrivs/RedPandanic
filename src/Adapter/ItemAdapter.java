package Adapter;

import com.example.redpandanic.R;
import com.example.redpandanic.R.id;

import Model.Item;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ItemAdapter extends ArrayAdapter<Item> {
	
	/**
	 * Adapter context
	 */
	private Context mContext;

	/**
	 * Adapter View layout
	 */
	private int mLayoutResourceId;
	
	public ItemAdapter(Context context, int layoutResourceId) {
		super(context, layoutResourceId);

		mContext = context;
		mLayoutResourceId = layoutResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		final Item currentItem = getItem(position);

		if (row == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(mLayoutResourceId, parent, false);
		}

		row.setTag(currentItem);
		final CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkIsCompleted);
		final TextView textName = (TextView)row.findViewById(R.id.textItem);
		final TextView textQuantity = (TextView) row.findViewById(R.id.textQuantity);
		checkBox.setChecked(false);
		checkBox.setEnabled(true);
		textName.setText(currentItem.getItemName());
		textQuantity.setText(Double.toString(currentItem.getQuantity()));
		

		return row;
	}
}
