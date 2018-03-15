package com.guosen.bj.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.util.LinkedCaseInsensitiveMap;

import com.guosen.bj.hrm.attendance.model.leave.WorkDayStatics;
import com.guosen.bj.hrm.employee.model.Employee;

public class ExportXlsTools {

	public static HSSFWorkbook WriteXls(ExportDataBean data) {
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet();
		excel.setSheetName(0, "总表");
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		for (int i=0; i<data.getTitles().length; i++){
			cell = row.createCell(i);
			cell.setCellValue(data.getTitles()[i]);
		}
		
		List<LinkedCaseInsensitiveMap> dataList = data.getList();
		LinkedCaseInsensitiveMap map = null;
		String tempstr = "";
		String key = "";
		SimpleDateFormat sdf=new SimpleDateFormat("");
		for (int i=0; i<dataList.size(); i++){
			row = sheet.createRow(i+1);
			map = dataList.get(i);
			for (int n=0; n<data.getColums().length; n++){
				cell = row.createCell(n);
				key = data.getColums()[n];
				if (key.indexOf(" as ")>=0)
					key = key.substring(key.indexOf(" as ")+4, key.length());
				if(map.get(key)!=null && map.get(key) instanceof Timestamp){
					tempstr=DateToolUtil.formatDate((Timestamp)map.get(key), true, false);
				}
				else
					tempstr = String.valueOf(map.get(key));
				if (tempstr == null || tempstr.equals("null"))
					cell.setCellValue("");
				else
					cell.setCellValue(tempstr);
			}
		}

		return excel;
	}
	
	@SuppressWarnings("unchecked")
	public static HSSFWorkbook outputXls(ExportDataBean data){
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet();
		excel.setSheetName(0, data.getSheetName());		
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		for (int i=0; i<data.getTitles().length; i++){
			cell = row.createCell(i);
			cell.setCellValue(data.getTitles()[i]);
		}
		
		List<LinkedCaseInsensitiveMap> dataList = data.getList();
		LinkedCaseInsensitiveMap map = null;
		String tempstr = "";
		String key = "";
		for (int i=0; i<dataList.size(); i++){
			row = sheet.createRow(i+1);
			map = dataList.get(i);
			for (int n=0; n<data.getColums().length; n++){
				cell = row.createCell(n);
				key = data.getColums()[n];
				tempstr = String.valueOf(map.get(key));

					cell.setCellValue(tempstr);
			}
		}
		return excel;		
	}
	
	public static void writeXls(HSSFWorkbook excel, ExportDataBean data){
		HSSFSheet sheet=excel.getSheet(data.getSheetName());
		if(sheet==null){
			sheet=excel.createSheet(data.getSheetName());
		}
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		for (int i=0; i<data.getTitles().length; i++){
			cell = row.createCell(i);
			cell.setCellValue(data.getTitles()[i]);
		}
		
		List<LinkedCaseInsensitiveMap> dataList = data.getList();
		LinkedCaseInsensitiveMap map = null;
		String tempstr = "";
		String key = "";
		for (int i=0; i<dataList.size(); i++){
			row = sheet.createRow(i+1);
			map = dataList.get(i);
			for (int n=0; n<data.getColums().length; n++){
				cell = row.createCell(n);
				key = data.getColums()[n];
				tempstr = String.valueOf(map.get(key));

					cell.setCellValue(tempstr);
			}
		}
	}
	
	
	/**
	 * 
	 * @param data
	 * @param sheetIndex 从0开始计数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static void outputXls(HSSFWorkbook excel, ExportDataBean data, int sheetIndex){
		HSSFSheet sheet = excel.createSheet();
		excel.setSheetName(sheetIndex, data.getSheetName());		
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		for (int i=0; i<data.getTitles().length; i++){
			cell = row.createCell(i);
			cell.setCellValue(data.getTitles()[i]);
		}
		
		List<LinkedCaseInsensitiveMap> dataList = data.getList();
		LinkedCaseInsensitiveMap map = null;
		String tempstr = "";
		String key = "";
		for (int i=0; i<dataList.size(); i++){
			row = sheet.createRow(i+1);
			map = dataList.get(i);
			for (int n=0; n<data.getColums().length; n++){
				cell = row.createCell(n);
				key = data.getColums()[n];
				tempstr = String.valueOf(map.get(key));

					cell.setCellValue(tempstr);
			}
		}	
	}	
	
	/**
	 * 导出多个sheet的excel
	 * @param dataList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static HSSFWorkbook outputXls(List<ExportDataBean> dataList){
		HSSFWorkbook excel = new HSSFWorkbook();
		for(int i=0;i<dataList.size();i++){
			outputXls(excel, dataList.get(i), i);
		}
		return excel;		
	}	
	
	
	
	public static InputStream ConvertHSSFWorkbookToInputStream(HSSFWorkbook wb) throws IOException{
		ByteArrayOutputStream bStream=new ByteArrayOutputStream();			
		wb.write(bStream);
		byte[] context=bStream.toByteArray();
		return new ByteArrayInputStream(context);	
	}
	
}
