package com.CountySocialMedia.dbApi;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;


//select statements should be added with <=,>=,NOT operants where necessary

//remember to limit the number of records fetched by adding limit at the end of select clause 
public class SelectSqlStatements {
	String selectSql="";
	
    SqlCommonPart commonSql=new SqlCommonPart();
	/* generates prepared statement SELECT column1, column2, ... FROM table_name;  dynamically*/
    public String selectDefault (ArrayList<String>columnNames,ArrayList<String>tableNames,String addClause) {
    	String selectDefaultSql=null;
    	
		
			if(tableNames.isEmpty()) {
				throw new NullPointerException("no columns specified");
			}else {
				if(columnNames.isEmpty()) {
					selectDefaultSql="SELECT * FROM "+ addFieldNames(tableNames) +addClause;
				}else {
					selectDefaultSql="SELECT " + addFieldNames(columnNames) +" From " + addFieldNames(tableNames) +" "+ addClause;
				}
			}
			
		
		
    	return selectDefaultSql;
    	
    }
   // SELECT column1, column2, ... FROM table_name WHERE col=?;
   
    public String selectWhere(ArrayList<String>columnNames,ArrayList<String>tableNames,String columnWhere,ColumnSyntax columnValue,String addClause) {
    	String selectWhereSql=null;
    	ColumnSyntax setValue=columnValue;
    	if(!tableNames.isEmpty()) {
    		
    	    if(columnNames.isEmpty()) {
    		  selectWhereSql="SELECT * FROM " + addFieldNames(tableNames) +" WHERE "+columnWhere + "? " + addClause;
    	   }
    	    else {
    		  selectWhereSql="SELECT " + addFieldNames(columnNames) + " FROM " + addFieldNames(tableNames) +" WHERE "+columnWhere + "? " + addClause;
    	}
    	
    		
    		if(setValue.getcolumnStringValue().equalsIgnoreCase("IS NOT NULL") || setValue.getcolumnStringValue().equalsIgnoreCase("IS NULL")) {
				 selectWhereSql=selectWhereSql.substring(0,selectWhereSql.indexOf(columnWhere))+columnWhere +" "+setValue.getcolumnStringValue() + " "+addClause;
				 
			  }
    	}
		
    	return selectWhereSql;
    }
    //assigns values to above prepared statement
    public PreparedStatement assignValuesToSelectWhere(PreparedStatement selectWhere,ColumnSyntax columnValue,ArrayList<String>columnNames) throws SQLException {
    	ColumnSyntax setValue=columnValue;
    	if(!setValue.getcolumnStringValue().equalsIgnoreCase("IS NOT NULL") || !setValue.getcolumnStringValue().equalsIgnoreCase("IS NULL")) { 
		  if(setValue.getcolumnStringValue() !=null) {
			  selectWhere.setString(1,setValue.getcolumnStringValue()); 
		  }else if(setValue.getcolumnDateValue() != null) {
			  selectWhere.setDate(1, setValue.getcolumnDateValue());
		  }else if(setValue.getcolumnTimeValue() != null) {
			  selectWhere.setTimestamp(1,setValue.getcolumnTimeValue());
		  }else{
			  selectWhere.setInt(1,setValue.getcolumnIntValue() );
		  }
		  }
		  
		return selectWhere;
    	
    }

	/*
	 * generates 
	 * 1:SELECT column1, column2, ...FROM table_name WHERE condition1 AND condition2 AND condition3 ...;
	 * 2:SELECT column1, column2, ...FROM table_name WHERE condition1 OR condition2 OR condition3 ...;
	 * 3:SELECT column1, column2, ...FROM table_name WHERE condition1 and (condition2 OR condition3 ...);
	 * to generate either of the above statements parse empty values to the necessarry arguments
	 * columns in the condition clauses should be added with the appropriate operant
	 */
    public String selectWhereAndJoinerOr(ArrayList<String>selectColumn,ArrayList<String>tableNames,LinkedHashMap<String,ColumnSyntax>andClauseCondition,String addOrJoiner,LinkedHashMap<String,ColumnSyntax>orClauseCondition,String additionalClause) throws SQLException {
    	String selectWhereAndOr="SELECT ";
    	if(!tableNames.isEmpty() && (andClauseCondition.isEmpty() || orClauseCondition.isEmpty())) {
    		if(selectColumn.isEmpty()) {
    			selectWhereAndOr=selectWhereAndOr + "* FROM " + addFieldNames(tableNames);
    		}else {
    			selectWhereAndOr=selectWhereAndOr + addFieldNames(selectColumn) + " FROM " + addFieldNames(tableNames);
    		}
    		if(!andClauseCondition.isEmpty() && orClauseCondition.isEmpty()) {
    			selectWhereAndOr=selectWhereAndOr + " WHERE " + commonSql.sqlSelect("AND",andClauseCondition)+" "+additionalClause;
    		 	
    		}else if(andClauseCondition.isEmpty() && !orClauseCondition.isEmpty()) {
    			selectWhereAndOr=selectWhereAndOr + " WHERE " + commonSql.sqlSelect("OR",orClauseCondition)+" "+additionalClause;
    		}else if(!andClauseCondition.isEmpty() && !orClauseCondition.isEmpty()) {
    		  selectWhereAndOr=selectWhereAndOr + " WHERE " + commonSql.sqlSelect("AND",andClauseCondition)+" "+addOrJoiner+" ("+ commonSql.sqlSelect("OR",orClauseCondition)+") "+additionalClause;  		
    	}else {
    		throw new NullPointerException("no table specified or no and/Or clause specified");
    	}
    	}
    	return selectWhereAndOr;
    }
    //assign Values to above select Statement
    public PreparedStatement assignValuesToSelectWhereAndJoinerOr(PreparedStatement selectWhereAndOrSql,LinkedHashMap<String,ColumnSyntax>andClauseCondition,String addOrJoiner,LinkedHashMap<String,ColumnSyntax>orClauseCondition) throws SQLException {
    	if(!andClauseCondition.isEmpty() && orClauseCondition.isEmpty()) {			
			  int i=1;
			  Iterator<Entry<String,ColumnSyntax>> itrPstmt = andClauseCondition.entrySet().iterator();
			  while(itrPstmt.hasNext()) {
				  
				  Entry<String,ColumnSyntax>entry=itrPstmt.next();
				  ColumnSyntax values=entry.getValue();
				  if(values.getcolumnStringValue() !=null) {
					  selectWhereAndOrSql.setString(i,values.getcolumnStringValue()); 
				  }else if(values.getcolumnDateValue() != null) {
					  selectWhereAndOrSql.setDate(i, values.getcolumnDateValue());
				  }else if(values.getcolumnTimeValue() != null) {
					  selectWhereAndOrSql.setTimestamp(i,values.getcolumnTimeValue());
				  }else{
					  selectWhereAndOrSql.setInt(i,values.getcolumnIntValue() );
				  }
				  i++;
			  }	
		 	
		}else if(andClauseCondition.isEmpty() && !orClauseCondition.isEmpty()) {
			int i=1; 
			Iterator<Entry<String,ColumnSyntax>> itrPstmtOr = orClauseCondition.entrySet().iterator();
	  			  while(itrPstmtOr.hasNext()) {
	  				  Entry<String,ColumnSyntax>entryOr=itrPstmtOr.next();
	  				  ColumnSyntax valuesOr=entryOr.getValue();
	  				  if(valuesOr.getcolumnStringValue() !=null) {
	  					  selectWhereAndOrSql.setString(i,valuesOr.getcolumnStringValue()); 
	  				  }else if(valuesOr.getcolumnDateValue() != null) {
	  					  selectWhereAndOrSql.setDate(i, valuesOr.getcolumnDateValue());
	  				  }else if(valuesOr.getcolumnTimeValue() != null) {
	  					  selectWhereAndOrSql.setTimestamp(i,valuesOr.getcolumnTimeValue());
	  				  }else{
	  					  selectWhereAndOrSql.setInt(i,valuesOr.getcolumnIntValue() );
	  				  }
	  				  i++;
			  }
		}else if(!andClauseCondition.isEmpty() && !orClauseCondition.isEmpty()) {
			  int i=1;
			  Iterator<Entry<String,ColumnSyntax>> itrPstmtAnd = andClauseCondition.entrySet().iterator();
			  while(itrPstmtAnd.hasNext()) {
				
				  Entry<String,ColumnSyntax>entry=itrPstmtAnd.next();
				  ColumnSyntax valuesAnd=entry.getValue();
				  if(valuesAnd.getcolumnStringValue() !=null) {
					  selectWhereAndOrSql.setString(i,valuesAnd.getcolumnStringValue()); 
				  }else if(valuesAnd.getcolumnDateValue() != null) {
					  selectWhereAndOrSql.setDate(i, valuesAnd.getcolumnDateValue());
				  }else if(valuesAnd.getcolumnTimeValue() != null) {
					  selectWhereAndOrSql.setTimestamp(i,valuesAnd.getcolumnTimeValue());
				  }else{
					  selectWhereAndOrSql.setInt(i,valuesAnd.getcolumnIntValue() );
				  }
				  i++;
			  }
			  
				 Iterator<Entry<String,ColumnSyntax>> itrPstmtOr = orClauseCondition.entrySet().iterator();
	  			  while(itrPstmtOr.hasNext()) {
	  				  Entry<String,ColumnSyntax>entryOr=itrPstmtOr.next();
	  				  ColumnSyntax valuesOr=entryOr.getValue();
	  				  if(valuesOr.getcolumnStringValue() !=null) {
	  					  selectWhereAndOrSql.setString(i,valuesOr.getcolumnStringValue()); 
	  				  }else if(valuesOr.getcolumnDateValue() != null) {
	  					  selectWhereAndOrSql.setDate(i, valuesOr.getcolumnDateValue());
	  				  }else if(valuesOr.getcolumnTimeValue() != null) {
	  					  selectWhereAndOrSql.setTimestamp(i,valuesOr.getcolumnTimeValue());
	  				  }else{
	  					  selectWhereAndOrSql.setInt(i,valuesOr.getcolumnIntValue() );
	  				  }
	  				  i++;
			  }
		
		}
		
	
		return selectWhereAndOrSql;
    }
	/* SELECT COUNT(column_name) FROM table_name WHERE condition 
	 * 
	*/
    public String selectCount(String countColumn,String tableName,String whereClause) throws SQLException {
    	String selectCount="SELECT COUNT(" +countColumn+ ") FROM "+ tableName + " WHERE " + whereClause;
    	return selectCount;
    }
   
    
    public String addFieldNames(ArrayList<String>fieldName) {
    	String fieldNames="";
    	for(int i=0;i<fieldName.size();i++) {
			if(i!=fieldName.size()-1) {
				fieldNames=fieldNames.concat(fieldName.get(i)+",");
			}else {
				fieldNames=fieldNames.concat(fieldName.get(i));
			}
		}
		return fieldNames;
    	
    }
    
    
}
