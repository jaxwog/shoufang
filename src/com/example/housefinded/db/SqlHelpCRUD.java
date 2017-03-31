package com.example.housefinded.db;

import java.util.ArrayList;
import java.util.List;

import com.example.javabean.House;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SqlHelpCRUD {

	// 数据库名称
	private static String DB_NAME = "house.db";
	// 数据库版本
	private static int DB_VERSION = 1;
	private SQLiteDatabase db;
	private MyDatabaseHelper dbHelper;

	public SqlHelpCRUD(Context context) {
		dbHelper = new MyDatabaseHelper(context, DB_NAME, null, DB_VERSION);
		db = dbHelper.getWritableDatabase();
	}

	public void Close() {
		db.close();
		dbHelper.close();
	}
	
	public boolean isExistHouse(String id){
		Cursor cursor = db.rawQuery("SELECT * FROM house"  +
				  " WHERE id=?", 
				new String[]{
							 id});  
		if(cursor.moveToFirst() ){
			cursor.close();
			return true;
		}
		cursor.close();
		return false;
	}
	
	public boolean insertHouseInfo(House houseInfo){
		db.execSQL("INSERT INTO  " + "house" +
				  "(id , issee ,iscol)" +
				"VALUES(?,?,?)",new Object[]{houseInfo.getId(),houseInfo.getIssee(),houseInfo.getIscol()});
		return true;
	}
	
	public boolean updateHouseInfo(House houseInfo){
		db.execSQL("UPDATE " + "house" + " SET " +
				  "issee=? , iscol=? WHERE id=?", 
				new Object[]{
				houseInfo.getIssee(),houseInfo.getIscol(),houseInfo.getId()});  
		return true;
	}
	
	public boolean deleteHouseInfo(String id ,String iscol){
		db.execSQL("UPDATE " + "house" + " SET " +
				  " iscol=? WHERE id=?", 
				new Object[]{
				iscol,id}); 
		return true;
	}
	
	public boolean insertOrUpdate(House houseInfo){
		if(isExistHouse(houseInfo.getId())){
			return updateHouseInfo(houseInfo);
		}else{
			return insertHouseInfo(houseInfo);
		}
	}
	
	public String getHouseIssee(String id){
		Cursor cursor = db.rawQuery("SELECT issee FROM house WHERE id=?", new String[]{id});
			while(cursor.moveToNext()){
				String issee =cursor.getString(0);
				return issee;
		}
		return "0";
	}
	
	public String getHouseIscol(String id){
		Cursor cursor = db.rawQuery("SELECT iscol FROM house WHERE id=?", new String[]{id});
		while(cursor.moveToNext()){
			String iscol =cursor.getString(0);
			return iscol;
		}
		return "0";
	}
	
	public List<String> getAllSeeHouse(){
		List<String> houseList = new ArrayList<String>();
		Cursor cursor = db.rawQuery("SELECT id FROM " + "house WHERE issee=?" , 
				new String[]{"1"});  
		while(cursor.moveToNext()){
			houseList.add(cursor.getString(0));
		}
		return houseList;
	}
	public void deleteAllHouse(){
		db.execSQL("DELETE FROM house");
	}
	
	
	public List<House> getAllColHouse(){
		List<House> houseList = new ArrayList<House>();
		Cursor cursor = db.rawQuery("SELECT id FROM " + "house WHERE iscol=?", 
				new String[]{"1"});  
		while(cursor.moveToNext()){
			House house = new House();
			house.setId(cursor.getString(0));
			houseList.add(house);
		}
		return houseList;
	}
//	public boolean insertHouseInfo(House houseInfo){
//		db.execSQL("INSERT INTO  " + "house" +
//				  "(id , userId  ,createDate  ,releaseDate ,type ,buildingName " +
//				  ",buildingAddress ,buildingArea ,housePrice ,propertyfee ,houseArea " +
//				  ",houseIn ,houseLayer ,houseFloor ,houseProperty ,buildType ,houseMode " +
//				  ",houseType ,decorate ,face ,housePtss ,officeLevel ,shopsType ,isCutting " +
//				  ",shopsTarget ,title ,description ,release ,imagePath ,issee ,iscol ) " +
//				  "VALUES (?, ?, ?, ?,?,?)", 
//				new Object[]{
//				houseInfo.getId(),
//				houseInfo.getUserId(),
//				houseInfo.getCreateDate(),
//				houseInfo.getReleaseDate(),
//				houseInfo.getType(),
//				houseInfo.getBuildingName(),
//				houseInfo.getBuildingAdress(),
//				houseInfo.getBuildingArea(),
//				houseInfo.getHousePrice(),
//				houseInfo.getPropertyfee(),
//				houseInfo.getHouseArea(),
//				houseInfo.getHouseIn(),
//				houseInfo.getHouseLayer(),
//				houseInfo.getHouseFloor(),
//				houseInfo.getHouseProperty(),
//				houseInfo.getBuildType(),
//				houseInfo.getHouseMode(),
//				houseInfo.getHouseType(),
//				houseInfo.getDecorate(),
//				houseInfo.getFace(),
//				houseInfo.getHousePtss(),
//				houseInfo.getOfficeLevel(),
//				houseInfo.getShopsType(),
//				houseInfo.getIsCutting(),
//				houseInfo.getShopsTarget(),
//				houseInfo.getTitle(),
//				houseInfo.getDescription(),
//				houseInfo.getRelease(),
//				houseInfo.getImagePath(),
//				houseInfo.getIssee(),
//				houseInfo.getIscol()});  
//		return true;
//	}
}
