package com.CountySocialMedia.dbApi;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

//deals with insert statement
public class InsertUpdateDeleteStatement {
	String insertSql="";
    String updateSql="";
    DbConnection con=new DbConnection();
    SqlCommonPart commonSql=new SqlCommonPart();
	/* INSERT INTO MyDB.myTable(col1,col2,...) * VALUES (Val_1, Val_2, ...);*/
	public String defaultinsertSqlStatement(String tableName,LinkedHashMap<String,ColumnSyntax>columnValues) throws SQLException {
	  String columnsSql1 =commonSql.sqlCommonColumn(columnValues);
	  String valuesSql1=commonSql.sqlCommonValues(columnValues);
	  insertSql ="INSERT INTO " +tableName+columnsSql1+ " VALUES" + valuesSql1;	  
	  return insertSql;
	  
  }
	//asssigning values to insert prepared statements
	public PreparedStatement insertValuesToInsertPreparedStatement(PreparedStatement insertPreparedStatement,LinkedHashMap<String,ColumnSyntax>insertcolumnValues) throws SQLException {
		LinkedHashMap<String,ColumnSyntax>columnValues=new LinkedHashMap<String,ColumnSyntax>(); 
		PreparedStatement insert=insertPreparedStatement;
		columnValues=insertcolumnValues;
		int i=1;
		  Iterator<Entry<String,ColumnSyntax>> itrPstmt =
		  columnValues.entrySet().iterator(); while(itrPstmt.hasNext()) {
		  Entry<String,ColumnSyntax>entry=itrPstmt.next(); ColumnSyntax
		  values=entry.getValue(); if(values.getcolumnStringValue() !=null) {
		  insert.setString(i,values.getcolumnStringValue()); }else
		  if(values.getcolumnDateValue() != null) { insert.setDate(i,
		  values.getcolumnDateValue()); }else if(values.getcolumnTimeValue() != null) {
		  insert.setTimestamp(i,values.getcolumnTimeValue()); }else{
		  insert.setInt(i,values.getcolumnIntValue() ); } i++; }
		  return insert;
		
		}

	/*
	 * UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE
	 * condition;
	 */
  public String defaulUpdateSqlStatement(String tableName,LinkedHashMap<String,ColumnSyntax>setClause,LinkedHashMap<String,ColumnSyntax>whereClause) {
	  if(!setClause.isEmpty() && !whereClause.isEmpty()) {
	   updateSql="UPDATE "+tableName+ " SET " + commonSql.sqlCommonEquals(setClause) + " WHERE " +commonSql.sqlCommonEquals(whereClause);
	  }  
	   return updateSql;
  }
  //assign values to update prepared statement
  public PreparedStatement assignValuesToUpdatePreparedStatement(PreparedStatement updatePreparedStatement,LinkedHashMap<String,ColumnSyntax>setClause,LinkedHashMap<String,ColumnSyntax>whereClause) {
	PreparedStatement update=updatePreparedStatement;
	try {
		 int i=1;
		  Iterator<Entry<String,ColumnSyntax>> itrPstmt = setClause.entrySet().iterator();
		  while(itrPstmt.hasNext()) {
			  Entry<String,ColumnSyntax>setEntry=itrPstmt.next();
			  ColumnSyntax setValues=setEntry.getValue();
			  
			  if(setValues.getcolumnStringValue() !=null) {
				update.setString(i,setValues.getcolumnStringValue()); 
			  }else if(setValues.getcolumnDateValue() != null) {
				  update.setDate(i, setValues.getcolumnDateValue());
			  }else if(setValues.getcolumnTimeValue() != null) {
				  update.setTimestamp(i,setValues.getcolumnTimeValue());
			  }else{
				  update.setInt(i,setValues.getcolumnIntValue() );
			  }
			  }
			  i++;
			  
		  Iterator<Entry<String,ColumnSyntax>> itrPstmt2 = whereClause.entrySet().iterator();
		  while(itrPstmt2.hasNext()) {
			  Entry<String,ColumnSyntax>whereEntry=itrPstmt2.next();
			  ColumnSyntax whereValues=whereEntry.getValue();
			  if(whereValues.getcolumnStringValue() !=null) {
				update.setString(i,whereValues.getcolumnStringValue()); 
			  }else if(whereValues.getcolumnDateValue() != null) {
				  update.setDate(i, whereValues.getcolumnDateValue());
			  }else if(whereValues.getcolumnTimeValue() != null) {
				  update.setTimestamp(i,whereValues.getcolumnTimeValue());
			  }else{
				  update.setInt(i,whereValues.getcolumnIntValue() );
			  }
			  i++;
		  }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  
	
	  return update;  
  }
	/* DELETE FROM table_name WHERE condition; */
  public String delete(String tableName,String deleteWhereClause) throws SQLException {
	  String deleteStatement="DELETE FROM "+tableName+" WHERE "+ deleteWhereClause;
	  return deleteStatement;
  }


}
