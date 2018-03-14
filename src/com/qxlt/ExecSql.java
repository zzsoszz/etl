package com.qxlt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ExecSql {
	String sqlString;
	

	String sqlTemplate;
	String jndiName;
	ArrayList<ExecSql> execSqllist=new ArrayList<ExecSql>();
	
	public String getSqlTemplate() {
		return sqlTemplate;
	}
	public String getSqlString() {
		if(sqlString==null)
		{
			return sqlTemplate;
		}
		return sqlString;
	}
	public void setSqlTemplate(File filename) {
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
		this.sqlTemplate=result;
	}

	public void setSqlString(String execSql) {
		this.sqlString=execSql;
	}
	
	public String getJndiName() {
		return jndiName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	public ArrayList<ExecSql> getExecSqllist() {
		return execSqllist;
	}

	public void setExecSqllist(ArrayList<ExecSql> execSqllist) {
		this.execSqllist = execSqllist;
	}
}
