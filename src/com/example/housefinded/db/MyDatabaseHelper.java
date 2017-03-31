package com.example.housefinded.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
	
	public static final String CREATE_TABLE1 = "create table if not exists house(id text, userId text ,createDate text ,releaseDate text,type text,buildingName text,buildingAddress text,buildingArea text,housePrice text,propertyfee text,houseArea text,houseIn text,houseLayer text,houseFloor text,houseProperty text,buildType text,houseMode text,houseType text,decorate text,face text,housePtss text,officeLevel text,shopsType text,isCutting text,shopsTarget text,title text,description text,release text,imagePath text,issee text,iscol text)";
	public MyDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

	}
	
}
