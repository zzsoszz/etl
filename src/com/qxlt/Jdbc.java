package com.qxlt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Jdbc {
	
	
	public static Connection getConnectionforOracle()
	{
		Connection conn=null;
		try
		   {
		    //注册数据库驱动程序为Oracle驱动
		    Class.forName("oracle.jdbc.driver.OracleDriver");
		   }
		   catch (java.lang.ClassNotFoundException e)
		   {
		    //这样写是为了方便调试程序，出错打印mydb()就知道在什么地方出错了
		    System.err.println("oracle(): " + e.getMessage());
		   }
		   try
		   {
			    conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.201.102:1521:ora", "gscx", "gscx");
		   }
		   catch (SQLException ex)
		   {
		    System.err.println("conn:"+ex.getMessage());
		   }
		   if (conn!=null)
		   {
			   System.out.println("connection successful");		  
		   }	  
		   else
		   {
			   System.out.println("connection failure");	  
		   }	   
		   return conn;
	}
	
	public static Connection getConnectionforSqlServer()
	{
		Connection conn=null;
		try
		   {
		    //注册数据库驱动程序为Oracle驱动
		    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		   }
		   catch (java.lang.ClassNotFoundException e)
		   {
		    //这样写是为了方便调试程序，出错打印mydb()就知道在什么地方出错了
		    System.err.println("sqlserver(): " + e.getMessage());
		   }
		   try
		   {
			    conn = DriverManager.getConnection("jdbc:sqlserver://192.168.201.91:1433", "sdkuser", "nicecti");
		   }
		   catch (SQLException ex)
		   {
		    System.err.println("conn:"+ex.getMessage());
		   }
		   if (conn!=null)
		   {
			   System.out.println("connection successful");		  
		   }	  
		   else
		   {
			   System.out.println("connection failure");	  
		   }	   
		   return conn;
	}
	
	public static void main(String[] args) throws SQLException
	{
	/*	Connection conn=null;	
		conn=Jdbc.getConnection();
		String sql="insert into aaa values('59','2009-11-25 14:00:35','2009-11-25 14:01:34')";
		Statement st=conn.createStatement();
		st.executeUpdate(sql);*/
	
	}


}
