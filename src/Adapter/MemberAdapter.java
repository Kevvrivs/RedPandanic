package Adapter;

import org.dlsunetcentriclab.redpandanic.R;


import Model.Member;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MemberAdapter extends ArrayAdapter<Member> {
	
	/**
	 * Adapter context
	 */
	private Context mContext;

	/**
	 * Adapter View layout
	 */
	private int mLayoutResourceId;
	
	public MemberAdapter(Context context, int layoutResourceId) {
		super(context, layoutResourceId);

		mContext = context;
		mLayoutResourceId = layoutResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		final Member currentMember = getItem(position);

		if (row == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(mLayoutResourceId, parent, false);
		}

		row.setTag(currentMember);
		final TextView Name = (TextView)row.findViewById(R.id.textName);
		final TextView Username = (TextView)row.findViewById(R.id.textUserName);
		final TextView Email = (TextView) row.findViewById(R.id.textEmail);
		Name.setText(currentMember.getMemberName());
		Username.setText(currentMember.getUsername());
		Email.setText(currentMember.getEmail());
		

		return row;
	}
}
