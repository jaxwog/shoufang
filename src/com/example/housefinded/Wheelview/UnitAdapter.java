package com.example.housefinded.Wheelview;
/**
 * Numeric Wheel adapter.
 */
public class UnitAdapter implements WheelAdapter {
	
	private static String[] unitString;
	
	
	/**
	 * Default constructor
	 */
	public UnitAdapter() {
		this(unitString);
	}

	/**
	 * Constructor
	 * @param minValue the wheel min value
	 * @param maxValue the wheel max value
	 */
	public UnitAdapter(String[] unitString) {
		this.unitString = unitString;
	}

	@Override
	public String getItem(int index) {
		if (index >= 0 && index < getItemsCount()) {
			return unitString[index];
		}
		return null;
	}

	@Override
	public int getItemsCount() {
		return unitString.length;
	}
	
	@Override
	public int getMaximumLength() {
//		int max = Math.max(Math.abs(maxValue), Math.abs(minValue));
//		int maxLen = Integer.toString(max).length();
//		if (minValue < 0) {
//			maxLen++;
//		}
		return 0;
	}
}
