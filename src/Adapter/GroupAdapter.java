package Adapter;

import Adapter.ItemAdapter.checkboxListener;
import Database.DbConnection;
import Model.Group;
import Model.Item;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import org.dlsunetcentriclab.redpandanic.R;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;

public class GroupAdapter extends ArrayAdapter<Group>{
	/**
	 * Adapter context
	 */
	private Context mContext;

	/**
	 * Adapter View layout
	 */
	private int mLayoutResourceId;

	private MobileServiceClient mClient;

	public GroupAdapter(Context context, int layoutResourceId) {
		super(context, layoutResourceId);

		mContext = context;
		mLayoutResourceId = layoutResourceId;
		mClient = DbConnection.connectToAzureService(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		final Group currentItem = getItem(position);

		if (row == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(mLayoutResourceId, parent, false);
		}

		row.setTag(currentItem);
		final TextView textName = (TextView) row.findViewById(R.id.textGroupName);
		textName.setText(currentItem.getName());
		return row;
	}

	

}
