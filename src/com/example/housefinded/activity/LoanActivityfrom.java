package com.example.housefinded.activity;

import com.example.housefinded.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

public class LoanActivityfrom extends Activity implements OnClickListener{
ImageView back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.loanactivity_from);
		back=(ImageView)findViewById(R.id.loan_back);
		back.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
	switch (v.getId()) {
	case R.id.loan_back:
		finish();
		break;

	default:
		break;
	}
		
	}

}
