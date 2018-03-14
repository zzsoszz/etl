package com.qxlt.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Config {

	public static void main(String[] args) throws IOException,
			DocumentException, SQLException {
		Config dt = new Config();
		dt.loadXml("src", "etl");
	}

	public static Document loadXml(String path, String filename) throws IOException,
			DocumentException {
		String xmlName = path + "/" + filename + ".xml";
		SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(xmlName);
		return doc;
	}
}