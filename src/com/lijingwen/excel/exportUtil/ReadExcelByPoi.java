package com.lijingwen.excel.exportUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadExcelByPoi {

	public static void readExcelMethod(String filePath) {
		File file = new File(filePath);
		try {
			InputStream is = new FileInputStream(file);
			XSSFWorkbook workbook= null;
			// 判断文件是否存在
			if (!file.exists()) {
				System.out.println("没有文件");
			}
			workbook = new XSSFWorkbook(is);
			
			System.out.println (workbook);
			//
			// // 选择Sheet页
			// String[] sheetNames = workbook.getSheetNames();
			// Sheet sheet = null;
			// for (int i = 0; i < sheetNames.length; i++) {
			// if ("Sheet1".equals(sheetNames[i].trim())) {
			// sheet = workbook.getSheet(i);
			// }
			// }
			// String fundid = null;
			// String fundname = null;
			// String sxf = null;
			// int row = 0;
			// int col = 0;
			// int cust_col = 0;
			// int sxf_col = 0;
			// int fundname_col = 0;
			// int rowLength = sheet.getRows();
			// int colLength = sheet.getColumns();// 总列数;
			// String titleValue = null;
			// for (row = 0; row < 4; row++) {
			// for (col = 0; col < colLength; col++) {
			// titleValue = sheet.getCell(col, row).getContents().trim();
			// if ("客户代码".equals(titleValue)) {
			// cust_col = col;
			// }
			// if ("手续费".equals(titleValue)) {
			// sxf_col = col;
			// }
			// if ("客户姓名".equals(titleValue)) {
			// fundname_col = col;
			// }
			// }
			// }
			// for (row = 0; row < rowLength; row++) {
			// fundid = sheet.getCell(cust_col, row).getContents().trim();
			// sxf = sheet.getCell(sxf_col, row).getContents().trim();
			// fundname = sheet.getCell(fundname_col, row).getContents()
			// .trim();
			// if (fundid.length() != 12 || fundid == null
			// || "".equals(sxf.trim()) || sxf == null) {
			// continue;
			// }
			// if (Float.valueOf(sxf) == 0) {
			// continue;
			// }
			// QhibSxflog qhibInfo = new QhibSxflog();
			// qhibInfo.setFundid(fundid);
			// qhibInfo.setSxf(Float.valueOf(sxf));
			// qhibInfo.setFundname(fundname);
			// System.out.println(qhibInfo.toString());
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testMethod() {

	}

	public static void main(String[] args) throws FileNotFoundException {
		String filePath = "/fileRolder/期货中间业务统计查询 2018年2月.xlsx";
		readExcelMethod(filePath);
	}

}
