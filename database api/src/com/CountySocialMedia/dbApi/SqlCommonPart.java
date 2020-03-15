package com.CountySocialMedia.dbApi;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class SqlCommonPart {
	public String[] firstLastValues(LinkedHashMap<String,ColumnSyntax>Values) {
		String first=null;
		String last=null;
		for (String key :Values.keySet()) {
		    first= key;
		    break;
		  }
		  for (String key : Values.keySet()) {
		    last= key;
		  }
		return new String[] {first,last};
	}
//creates and adds column names (col1,col2,...) ad creates (?,?,?,...)
	public String sqlCommonColumn(LinkedHashMap<String,ColumnSyntax>columnValues) {
		String columnsSql1="(";
		String [] firstLast=firstLastValues(columnValues);
		Iterator<Entry<String,ColumnSyntax>> itr = columnValues.entrySet().iterator();
		  while (itr.hasNext()) {
			  Entry<String,ColumnSyntax> entry=itr.next();
			  String key=entry.getKey();
			  columnsSql1=columnsSql1.concat(key);
			  if(!key.equals(firstLast[1])) {
				  columnsSql1=columnsSql1.concat(",");
			  }
			  if(key.equals(firstLast[1])) {
				  columnsSql1=columnsSql1.concat(")");
			  }
			 
		
	}
		  return columnsSql1;
		  
	}
	//creates and adds ? to enable prepared statements
	
	public String sqlCommonValues(LinkedHashMap<String,ColumnSyntax>columnValues) {
		String valuesSql="(";
		String [] firstLast=firstLastValues(columnValues);
		Iterator<Entry<String,ColumnSyntax>> itr = columnValues.entrySet().iterator();
		  while (itr.hasNext()) {
			  Entry<String,ColumnSyntax> entry=itr.next();
			  String key=entry.getKey();
			  valuesSql=valuesSql.concat("?");
			  if(!key.equals(firstLast[1])) {
				  valuesSql=valuesSql.concat(",");
			  }
			  if(key.equals(firstLast[1])) {
				  valuesSql=valuesSql.concat(")");
			  }
			 
		
	}
		  return  valuesSql;
		  
	}
	public String sqlCommonEquals(LinkedHashMap<String,ColumnSyntax>equalsValues) {
		String equalsSql1="";
		String [] firstLast=firstLastValues(equalsValues);
		Iterator<Entry<String,ColumnSyntax>> itr = equalsValues.entrySet().iterator();
		  while (itr.hasNext()) {
			  Entry<String,ColumnSyntax> entry=itr.next();
			  String key=entry.getKey();
			  equalsSql1=equalsSql1.concat(key +"=?");
			  if(!key.equals(firstLast[1])) {
				  equalsSql1=equalsSql1.concat(",");
			  }
			 
		
	}
		  return  equalsSql1;
	}
	public String sqlSelect(String andOr ,LinkedHashMap<String,ColumnSyntax>conditionValues) {
		String equalsSql1="";
		String [] firstLast=firstLastValues(conditionValues);
		Iterator<Entry<String,ColumnSyntax>> itr = conditionValues.entrySet().iterator();
		  while (itr.hasNext()) {
			  Entry<String,ColumnSyntax> entry=itr.next();
			  String key=entry.getKey();
			  equalsSql1=equalsSql1.concat(key +"? ");
			  if(!key.equals(firstLast[1])) {
				      equalsSql1=equalsSql1.concat(andOr +" ");
			  }
			 
		
	}
		  return  equalsSql1;
	}
	
}
