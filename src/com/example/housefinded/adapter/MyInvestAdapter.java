package com.example.housefinded.adapter;

import com.example.housefinded.R;
import com.example.javabean.InvestProject;
import com.example.javabean.InvestProjectBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyInvestAdapter extends BaseAdapter{
	private Context context;
	private InvestProjectBean bean;
	public MyInvestAdapter(InvestProjectBean bean,Context context){
		this.context=context;
		this.bean=bean;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bean.getList().size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return bean.getList().get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.investobj_activity, null);
			holder=new ViewHolder();
			holder.tv_earn=(TextView) convertView.findViewById(R.id.tv_earn);
			holder.tv_limit=(TextView) convertView.findViewById(R.id.tv_limit);
			holder.tv_amount=(TextView) convertView.findViewById(R.id.tv_amount);
			holder.tv_objname=(TextView) convertView.findViewById(R.id.tv_objname);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		InvestProject project=bean.getList().get(position);
		holder.tv_earn.setText("年华收益："+project.getAnnualIncome());
		holder.tv_limit.setText("融资期限："+project.getDeadline());
		holder.tv_amount.setText("融资金额："+project.getAmount());
		holder.tv_objname.setText("投资项目"+project.getName());
		
		return convertView;
	}
public class ViewHolder{
	TextView tv_earn;
	TextView tv_limit;
	TextView tv_amount;
	TextView tv_objname;
	
}
}
