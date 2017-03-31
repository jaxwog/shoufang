package com.example.housefinded.fragement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.CircleImage.LinearGridView;
import com.example.Tools.SharePreferences;
import com.example.housefinded.R;
import com.example.housefinded.activity.AboutActivity;
import com.example.housefinded.activity.DeclareActivity;
import com.example.housefinded.activity.FeedbackActivity;
import com.example.housefinded.activity.MortgageActivvity;
import com.example.housefinded.activity.ScanActivity;
import com.example.housefinded.activity.ScanDownActivity;
import com.example.housefinded.activity.TaxcalculationActivity;
import com.example.housefinded.activity.UseHelpActivity;
import com.example.housefinded.activity.UserLogin;
import com.example.housefinded.adapter.GridviewAdapt;
import com.example.housefinded.db.SqlHelpCRUD;
import com.example.housefinded.update.UpdateManager;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MoreFragment extends BaseFragement implements OnClickListener {
	private RelativeLayout rl_download, rl_cash, rl_scanApp, rl_feedback,
			rl_help, rl_declare, rl_aboutUs;
	private LinearLayout ll_scan, Mortgag_button, taxbutton;
	Intent intent;

	@Override
	public View initView() {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.frame_more, null);
		intent = new Intent();
		setOnclick(view);
		return view;
	}

	private void setOnclick(View view) {
		// TODO Auto-generated method stub
		ll_scan = (LinearLayout) view.findViewById(R.id.ll_scan);
		rl_download = (RelativeLayout) view.findViewById(R.id.rl_download);
		rl_cash = (RelativeLayout) view.findViewById(R.id.rl_cash);
		rl_scanApp = (RelativeLayout) view.findViewById(R.id.rl_scanApp);
		rl_feedback = (RelativeLayout) view.findViewById(R.id.rl_feedback);
		rl_help = (RelativeLayout) view.findViewById(R.id.rl_help);
		rl_aboutUs = (RelativeLayout) view.findViewById(R.id.rl_aboutUs);
		rl_declare = (RelativeLayout) view.findViewById(R.id.rl_declare);
		Mortgag_button = (LinearLayout) view.findViewById(R.id.Mortgag_button);
		taxbutton = (LinearLayout) view.findViewById(R.id.taxbutton);

		ll_scan.setOnClickListener(this);
		rl_cash.setOnClickListener(this);
		rl_feedback.setOnClickListener(this);
		rl_help.setOnClickListener(this);
		rl_aboutUs.setOnClickListener(this);
		rl_declare.setOnClickListener(this);
		rl_download.setOnClickListener(this);
		rl_scanApp.setOnClickListener(this);
		Mortgag_button.setOnClickListener(this);
		taxbutton.setOnClickListener(this);
	}

	@Override
	public void initDate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.Mortgag_button:
			intent.setClass(getActivity(), MortgageActivvity.class);
			startActivity(intent);
			break;
		case R.id.taxbutton:
			intent.setClass(getActivity(), TaxcalculationActivity.class);
			startActivity(intent);
			break;

		case R.id.ll_scan: // 扫一扫
			Intent intent1 = new Intent(getActivity(), ScanActivity.class);
			startActivity(intent1);
			break;
		case R.id.rl_download: // 检查更新
			// Toast.makeText(getActivity(), "当前版本已是最新版本", 0).show();
			UpdateManager manager = new UpdateManager(getActivity());
			// 检查软件更新
			manager.checkUpdate();
			break;
		case R.id.rl_cash: // 清除缓存
			AlertDialog.Builder builder = new Builder(getActivity());
			builder.setMessage("确定清除图片缓存？");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Toast.makeText(getActivity(), "图片缓存清除成功", 0).show();
							SqlHelpCRUD sqlCURD=new SqlHelpCRUD(getActivity());
							sqlCURD.deleteAllHouse();
						}
					});
			builder.setNegativeButton("取消", null);
			builder.show();
			break;
		case R.id.rl_scanApp: // 扫描下APP
			Intent intents = new Intent(getActivity(), ScanDownActivity.class);
			startActivity(intents);
			break;
		case R.id.rl_feedback: // 用户反馈
			if (SharePreferences.islogin(getActivity())) {
				Intent intent2 = new Intent(getActivity(),
						FeedbackActivity.class);
				startActivity(intent2);
			} else {
				Intent intent2 = new Intent(getActivity(), UserLogin.class);
				intent2.putExtra("jianyi", "jianyito");
				startActivity(intent2);
			}
			break;
		case R.id.rl_help: // 使用帮助
			Intent intent3 = new Intent(getActivity(), UseHelpActivity.class);
			startActivity(intent3);
			break;
		case R.id.rl_aboutUs: // 关于我们
			Intent intent4 = new Intent(getActivity(), AboutActivity.class);
			startActivity(intent4);
			break;
		case R.id.rl_declare: // 免责声明
			Intent intent5 = new Intent(getActivity(), DeclareActivity.class);
			startActivity(intent5);
			break;
		default:
			break;
		}
	}

}
