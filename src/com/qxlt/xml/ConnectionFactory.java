package com.qxlt.xml;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

public class ConnectionFactory {
	static ArrayList<Database> databasearraylist = new ArrayList<Database>();

	static HashMap<String, ArrayList<Connection>> connectionForQueryHashmap = new HashMap<String, ArrayList<Connection>>();
	static HashMap<String, ArrayList<Connection>> connectionForUpdateHashmap = new HashMap<String, ArrayList<Connection>>();
	static int maxConnection=5;
	public static Connection getConnectionForQueryByJndiName(String jndiname) {
		
		ArrayList<Connection> conlist=connectionForQueryHashmap.get(jndiname);
		System.out.println(conlist.size());
//		if(conlist.size()<=maxConnection)
//		{
			Connection con=getNewConnection(jndiname);
			conlist.add(con);
			return con;
		//}
//		else{
////			int last=conlist.size()-1;
////			Connection con=conlist.get(last);
////			conlist.remove(last);
//			return null;
//		}
	}

	
	public static Connection getConnectionForUpdateByJndiName(String jndiname) {
		
		ArrayList<Connection> conlist=connectionForUpdateHashmap.get(jndiname);
		
		if(conlist.size()<=maxConnection)
		{
			Connection con=getNewConnection(jndiname);
			conlist.add(con);
			return con;
		}
		else{
			int last=conlist.size()-1;
			Connection con=conlist.get(last);
			//conlist.remove(last);
			return con;
		}
	}
	public static HashMap<String,Statement> stmap=new HashMap<String,Statement>();
	
	
	
	
	
	public static Statement getStatementForUpdateByJndiName(String jndiname) {
		if(stmap.containsKey(jndiname))
		{
			return stmap.get(jndiname);
		}else
		{
			try {
				
				Statement stnew=getConnectionForUpdateByJndiName(jndiname).createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				stmap.put(jndiname,stnew);
				return stnew;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void init() {
		Document document = null;
		try {
			document = Config.loadXml("src", "etl");
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}

		List databaselist = document.selectNodes("/etl/datasource/database");

		Iterator it = databaselist.iterator();
		while (it.hasNext()) {

			Element databaseele = (Element) it.next();

			String jndiName = databaseele.selectSingleNode("jndi-name")
					.getText();
			Element driverele = (Element) databaseele
					.selectSingleNode("driver");
			String type = driverele.attribute("type").getValue();
			String url = driverele.selectSingleNode("url").getText();
			String user = driverele.selectSingleNode("user").getText();
			String password = driverele.selectSingleNode("password").getText();

			Database database = new Database();
			database.setJndiName(jndiName);
			database.setType(type);
			database.setUrl(url);
			database.setUser(user);
			database.setPassword(password);

			databasearraylist.add(database);
			System.out.println(jndiName);
			System.out.println(type);
			System.out.println(url);
			System.out.println(user);
			System.out.println(password);
			
			connectionForQueryHashmap.put(jndiName,new ArrayList());
			connectionForUpdateHashmap.put(jndiName,new ArrayList());
		}

	}

	public static Connection getNewConnection(String jndiName)
	{
		//创建连接
		
		for (int i = 0; i < databasearraylist.size(); i++) {
			
			Database database = databasearraylist.get(i);
			
			if(database.getJndiName().equals(jndiName))
			{
				try {
					Class.forName(database.type);
					try {
						Connection conn = DriverManager.getConnection(database.getUrl(),
								database.getUser(), database.getPassword());
						return conn;
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return null;

	}
	public static void main(String[] args) throws SQLException {
		new ConnectionFactory();
		/*
		 * Connection conn=null; conn=Jdbc.getConnection(); Stringsql=
		 * "insert into aaa values('59','2009-11-25 14:00:35','2009-11-25 14:01:34')"
		 * ; Statement st=conn.createStatement(); st.executeUpdate(sql);
		 */
		ConnectionFactory con = new ConnectionFactory();
		ConnectionFactory.init();
		getConnectionForQueryByJndiName("runtocrm").createStatement().executeQuery("select * from user_tables");
		
		
		
		// con.getConnectionByJndiName("");
	}
}
