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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.qxlt.xml.ConnectionFactory;
import com.qxlt.xml.Job;
import com.qxlt.xml.JobFactory;
import com.sun.xml.internal.ws.util.StringUtils;

public class DataTransfer {

	//static Logger logger = Logger.getLogger(DataTransfer.class.getName());
	
	public static void main(String args[]) {
		new DataTransfer().run();
	}
	ConnectionFactory confactory;
	public void run()
	{
		System.out.println("------��ʼ�����ݿ�����------");
		confactory= new ConnectionFactory();
		ConnectionFactory.init();
		String[]job_arr = {
				"one"   // work_eff���� 
				//"crm_bill"   // CRM_BIll���� 
				//,"tousu_bill"   // tousu_BIll���� 
		        //,"tousu_question"   // tousu_question���� 
				//"work_eff"   // work_eff���� 
                //,"pfm_ivr"   //ivr����  
				//,"pfm_pre"   // Ԥ��������
				//,"C_preRate"   // C���ϰ�Ԥ���������� 
				//,"ADSL_preRate"   // ADSL�ϰ�Ԥ���������� 
				//,"toushu_bill_QC"   // Ͷ�߹����ʼ���ȷ������
				//,"pfm_zx"   // רϯ����������
				//"toushu_30mscjc_lv" //Ͷ��30�����״νӴ���
		};
		for(String jobId:job_arr){
			System.out.println("----------��������----------");
			System.out.println("����ID��"+jobId);
			Job job_px = JobFactory.getJobById(jobId);
			ExecSql execSql_px = job_px.getExecSql();
			System.out.println("----------ִ������----------");
			transferData(execSql_px);
			System.out.println("******************************************************************");
		}
		//logger.debug("����3ִ�����");	
	}
	
	public void  transferData(ExecSql execSql) {
		
		ResultSet rs=null;
		Connection con=null;
		try {
			
			
			//Statement st=confactory.getStatementByJndiName(execSql.getJndiName());
		/////System.out.println(st);
			if(execSql.getExecSqllist().size()==0)
			{
				System.out.println("ִ�����:"+execSql.getSqlString());
				Statement st2=confactory.getStatementForUpdateByJndiName(execSql.getJndiName());
//				if(one==0)
//				{
//					System.out.println("����ɾ�����");
//					Statement st2=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//					one=1;
//				}
////				ResultSetMetaData metaData = rs.getMetaData();
////				for(int j=1;j<=metaData.getColumnCount();j++)
////				{
////					System.out.print(metaData.getColumnName(j)+":"+rs.getString(j)+"  ");
////				}
				
				
			/////System.out.println("���ղ�");
				st2.executeUpdate(execSql.getSqlString());
				//st=null;
				//System.gc();
			/////System.out.println(st2);
				return;
			}else
			{
				System.out.println("ִ�����:"+execSql.getSqlString());
				con=confactory.getConnectionForQueryByJndiName(execSql.getJndiName());
				
				Statement st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				
				rs=st.executeQuery(execSql.getSqlString());
					//System.out.println("�����ղ�");
			}
			
			
			rs.last();
			System.out.println(rs.getRow());
			rs.beforeFirst();
			
			while(rs.next())
			{
				System.out.println(rs.getRow());
				ArrayList<ExecSql> execSqlList=execSql.getExecSqllist();
		
				for(int i=0;i<execSqlList.size();i++)
				{
					ExecSql execsqlcur=execSqlList.get(i);
					
					String destSql=execsqlcur.getSqlTemplate();
					ResultSetMetaData metaData = rs.getMetaData();
				/////System.out.print("�ֶ�:");
					for(int j=1;j<=metaData.getColumnCount();j++)
					{
					/////System.out.print(metaData.getColumnName(j)+":"+rs.getString(j)+"  ");
						destSql=MyStringUtil.replaceVar(destSql, metaData.getColumnName(j).toLowerCase(),rs.getString(j));
					}
				/////System.out.println("");
					
					execsqlcur.setSqlString(destSql);
					
					transferData(execsqlcur);
					
				}
			}
			
		} catch (SQLException e) { 	
			//logger.debug(execSql.getSqlString());
			e.printStackTrace();
		}finally{
			if(con!=null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		System.out.println("aaaaaaa");
		return;
	}
	
}
