package com.qxlt.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.qxlt.ExecSql;

public class Job {
	ExecSql execSql;

	public ExecSql getExecSql() {
		return execSql;
	}

	public void setExecSql(ExecSql execSql) {
		this.execSql = execSql;
	}
}