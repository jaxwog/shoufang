package com.example.housefinded.activity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.housefinded.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Browsehistory extends Activity {

	RelativeLayout titlelayout;
	TextView title;
	Button bb ;
	NetworkImageView imageview;
	ImageRequest imagequest;
	RequestQueue queue;
	ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.browsehistory);
		titlelayout = (RelativeLayout) findViewById(R.id.browhistory);
		title =(TextView) titlelayout.findViewById(R.id.titlename);
		back= (ImageView) titlelayout.findViewById(R.id.btnback);
		bb= (Button) titlelayout.findViewById(R.id.btnswitch);
		bb.setVisibility(View.GONE);
		title.setText("浏览记录");
		imageview=(NetworkImageView)findViewById(R.id.netimage);
		RequestQueue mQueue = Volley.newRequestQueue(this);  
		imagequest=new ImageRequest("", new Response.Listener<Bitmap>() {

			@Override
			public void onResponse(Bitmap arg0) {
				imageview.setImageBitmap(arg0);
				
			}
			
		}
				, 0, 0, 
				Config.RGB_565,
				new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						
						imageview.setImageResource(R.drawable.backto);  		
					}
				});
		mQueue.add(imagequest);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Browsehistory.this.finish();
			}
		});
	}

}
