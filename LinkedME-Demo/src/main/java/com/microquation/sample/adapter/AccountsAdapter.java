package com.microquation.sample.adapter;

import java.util.List;


import com.microquation.sample.R;
import com.microquation.sample.model.Account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class AccountsAdapter extends BaseAdapter{
	private List<Account> totalApps;
    private final LayoutInflater mInflater;
    public AccountsAdapter(Context context, List<Account> totalApps, GridView gridView) {
        mInflater = LayoutInflater.from(context);
        this.totalApps = totalApps;
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return totalApps.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return totalApps.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            holder = new ViewHolder();
           
                convertView = mInflater.inflate(R.layout.view_accounts_item, null);
            
              
            
            holder.iconView = (ImageView) convertView.findViewById(R.id.img_account_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.position = position;
        Account info = totalApps.get(position);
        if (info.getIconResourceId() != 0) {
            holder.iconView.setImageResource(info.getIconResourceId());
        }
		return convertView;
	}
	static class ViewHolder {
        int position;
       
        ImageView iconView;
    }
}
