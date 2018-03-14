package com.qxlt;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.xml.internal.ws.util.StringUtils;

public class Record {

	public static void main(String args[]) {
		new Record().run();
	}

	public void run()
	{
		
		
		Connection onnoracle = Jdbc.getConnectionforOracle();
		Connection connsqlserver  = Jdbc.getConnectionforSqlServer();

		try {
		
				
				String vcnumberSql=this.getResource("src/com/qxlt/vcnumber.sql");
				//取vcnumber
				String starttime="2009-11-01 00:00:00";
				String endtime="2010-02-28 23:59:59";
				vcnumberSql=MyStringUtil.replaceVar(vcnumberSql, "starttime", starttime);
				vcnumberSql=MyStringUtil.replaceVar(vcnumberSql, "endtime", endtime);	
				System.out.println(vcnumberSql);
				
				
				ResultSet rs_vcnumber=this.getResultSet(connsqlserver, vcnumberSql);
				rs_vcnumber.last();
				System.out.println("总共查询:"+rs_vcnumber.getRow()+"个表");
				rs_vcnumber.beforeFirst();
				while(rs_vcnumber.next())
				{
					String recordSql=this.getResource("src/com/qxlt/record.sql");
					String vcsetnumber=rs_vcnumber.getString("vcsetnumber");
					System.out.println("vcnumber:"+vcsetnumber);
					
					//取录音路径
					recordSql=MyStringUtil.replaceVar(recordSql, "starttime", starttime);
					recordSql=MyStringUtil.replaceVar(recordSql, "endtime", endtime);
					recordSql=MyStringUtil.replaceVar(recordSql, "vcsetnumber",vcsetnumber);
					System.out.println(recordSql);
					ResultSet rs_record=this.getResultSet(connsqlserver, recordSql);
					rs_record.last();
					System.out.println("总共:"+rs_record.getRow()+"条");
					rs_record.beforeFirst();
					while(rs_record.next())
					{
						ResultSetMetaData metaData = rs_record.getMetaData();
						for(int i=1;i<metaData.getColumnCount();i++)
						{
							System.out.print(metaData.getColumnName(i)+":"+rs_record.getString(i)+"	");
						}
						System.out.println();
					}
					
//					
//					//storage
//					
//					String recordSql=this.getResource("src/com/qxlt/record.sql");
//					String vcsetnumber=rs_vcnumber.getString("vcsetnumber");
//					System.out.println("vcnumber:"+vcsetnumber);
//					
//					//取录音路径
//					recordSql=MyStringUtil.replaceVar(recordSql, "starttime", starttime);
//					recordSql=MyStringUtil.replaceVar(recordSql, "endtime", endtime);
//					recordSql=MyStringUtil.replaceVar(recordSql, "vcsetnumber",vcsetnumber);
//					System.out.println(recordSql);
//					ResultSet rs_record=this.getResultSet(connsqlserver, recordSql);
//					rs_record.last();
//					System.out.println("总共:"+rs_record.getRow()+"条");
//					rs_record.beforeFirst();
//					while(rs_record.next())
//					{
//						ResultSetMetaData metaData = rs_record.getMetaData();
//						for(int i=1;i<metaData.getColumnCount();i++)
//						{
//							System.out.print(metaData.getColumnName(i)+":"+rs_record.getString(i)+"	");
//						}
//						System.out.println();
//					}
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public String getResource(String filename){
		InputStream fis;
		String line = "";
		String result = "";
		try {
			fis = new FileInputStream(filename);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			try {
				while ((line = br.readLine()) != null) {
					result += line + " ";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
	

	public ResultSet getResultSet(Connection con,String execStr) {
		try {
			return con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(execStr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
