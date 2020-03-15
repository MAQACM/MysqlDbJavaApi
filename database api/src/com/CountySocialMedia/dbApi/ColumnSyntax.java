package com.CountySocialMedia.dbApi;

import java.sql.Date;
import java.sql.Timestamp;

public class ColumnSyntax {
	private int columnIntValue;
	private  String columnStringValue;
	private boolean columnBooleanValue;
	private Date columnDateValue;
	private Timestamp columnTimeValue;
	
	
	
	public ColumnSyntax(Integer columnIntValue) {
		if(columnIntValue instanceof Integer) {
			if(columnIntValue>0) {
				this.columnIntValue=columnIntValue;
			}
			
		}
		
	}
	public ColumnSyntax(String columnStringValue) {
		this.columnStringValue=columnStringValue;
		
	}
	public ColumnSyntax(Date columnDateValue) {
		if(columnDateValue instanceof Date) {
			this.columnDateValue=columnDateValue;
		}
	}
	public ColumnSyntax(Timestamp columnTimeValue) {
		if(columnTimeValue instanceof Timestamp) {
			this.columnTimeValue=columnTimeValue;
		}
	}
	public ColumnSyntax(Boolean columnBooleanValue) {
		if(columnBooleanValue instanceof Boolean) {
			this.columnBooleanValue=columnBooleanValue;
		}
	}
	public int getcolumnIntValue() {
		return columnIntValue;
	}
	public String getcolumnStringValue () {
		return  columnStringValue;
	}
	public boolean getcolumnBooleanValue() {
		return columnBooleanValue;
	}
	public Date getcolumnDateValue() {
		return columnDateValue;
	} 
	public Timestamp getcolumnTimeValue() {
		return columnTimeValue;
	}
	
	
	
	
}
