package com.qxlt;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestMain
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		   Calendar ca = Calendar.getInstance(); 
	       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	       String dateStr = sdf.format(ca.getTime()); 
	       System.out.println(dateStr); 
	}

}
