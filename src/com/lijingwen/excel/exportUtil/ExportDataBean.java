package com.guosen.bj.util;

import java.util.List;

import org.springframework.util.LinkedCaseInsensitiveMap;

public class ExportDataBean {
	
	private String sheetName;

	private String[] titles;
	
	private List<LinkedCaseInsensitiveMap> list;
	
	private String[] colums;

	public String[] getTitles() {
		return titles;
	}

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	public List<LinkedCaseInsensitiveMap> getList() {
		return list;
	}

	public void setList(List<LinkedCaseInsensitiveMap> list) {
		this.list = list;
	}

	public String[] getColums() {
		return colums;
	}

	public void setColums(String[] colums) {
		this.colums = colums;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
}
