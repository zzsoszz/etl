package com.qxlt.xml;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.*;

import com.qxlt.ExecSql;

public class JobFactory {

	public static Job getJobById(String jobid) {
		Job job = new Job();
		Document document = null;
		try {
			document = Config.loadXml("src", "etl");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element elt = (Element) document.selectSingleNode("/etl/jobs/job[@id='"
				+ jobid + "']");
		Element execSqlXml = (Element) elt.selectSingleNode("execsql");
		ExecSql execsql=new ExecSql();
		job.setExecSql(execsql);
		loadExecSql(execsql,execSqlXml);
		return job;
	}

	public static void loadExecSql(ExecSql execsql,Element execsqlEle) {
		
		//设置当前节点
		Attribute ExecSqlattrCur = execsqlEle.attribute("jndiname");
		Attribute sqlattrCur = execsqlEle.attribute("sql");
		execsql.setJndiName(ExecSqlattrCur.getValue());
		execsql.setSqlTemplate(new File(sqlattrCur.getValue()));
		
		System.out.println(execsql.getSqlTemplate());
		System.out.println(execsql.getJndiName());
		
		//设置子节点
		List execSqlNodeList = execsqlEle.selectNodes("execsql");
		Iterator execSqlIter = execSqlNodeList.iterator();
		ArrayList<ExecSql> exeSqlList=new ArrayList<ExecSql>();
		while (execSqlIter.hasNext()) {
			Element execSqlElt = (Element) execSqlIter.next();
			Attribute ExecSqlattr = execSqlElt.attribute("jndiname");
			Attribute sqlattr = execSqlElt.attribute("sql");
			ExecSql execSql = new ExecSql();
			execSql.setSqlTemplate(new File(sqlattr.getValue()));
			execSql.setJndiName(ExecSqlattr.getValue());
			
			loadExecSql(execSql,execSqlElt);
			exeSqlList.add(execSql);
		}
		
		
		execsql.setExecSqllist(exeSqlList);
		
	}

	public static void main(String[] args) throws IOException,
			DocumentException, SQLException {
		JobFactory.getJobById("one");
		
	}

}
