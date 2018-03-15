package com.lijingwen.excel.exportUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel2 {

	// private static final Log log = LogFactory.getLog(ReadExcel.class);

	/**
	 * 判断后缀分批入
	 */
	private static void parseSUCCEXX(String realPath, String fileName) {

		String[] pfix = fileName.split("\\.");
		String suffix = pfix[pfix.length - 1];

		if (suffix != null && !suffix.equals("") && suffix.equals("xls")) {

			System.out.println("xls");
			// jxl方法可读取.xls格式
			jlxExcel(realPath, fileName);
		} else if (suffix.equals("xlsx")) {

			System.out.println("xlsx");
			// poi方法可读取Excel2007即.xlsx格式
			poiExcel(realPath, fileName);
		}
	}

	/**
	 * 读取 xls JXL
	 * 
	 * @param realPath
	 * @param fileName
	 */
	private static void jlxExcel(String realPath, String fileName) {
		// ===============jlx方法=================
		try {
			File fileDes = new File(realPath);
			InputStream str = new FileInputStream(fileDes);
			// 构造Workbook（工作薄）对象
			Workbook rwb = Workbook.getWorkbook(str);
			Sheet rs = rwb.getSheet(0);// 获取第一张工作表
			int rsRows = rs.getRows();// 获取Sheet表中所包含的总行数
			int rsCols = rs.getColumns();// 获取Sheet表中所包含的总列数
			// log.info("========行========"+rsRows+"=====列========"+rsCols);
			for (int i = 1; i < rsRows; i++) {// 读取行
				// log.info("========执行第========"+i+"行");
				for (int j = 0; j < rsCols; j++) {
					// log.info("========执行第========"+j+"列");
					Cell coo = rs.getCell(j, i);// 单元格定位列，再定位行
					// log.info("========coo========"+coo);
					String strc = coo.getContents();// 读取内容
					// log.info("========读取内容strc========"+strc);
					System.out.println("文件" + fileName + "的内容为：" + strc);
				}
			}
			rwb.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ==========读取excel文件内容=结束=====================

	}

	/**
	 * POI读取 xlsx
	 * 
	 * @param realPath
	 * @param fileName
	 */
	private static void poiExcel(String realPath, String fileName) {
		try {
			File fileDes = new File(realPath);
			InputStream str = new FileInputStream(fileDes);
			XSSFWorkbook xwb = new XSSFWorkbook(str); // 利用poi读取excel文件流
			xwb.getNumberOfSheets();
			String sheet = xwb.getSheetName(1);
			
			 XSSFSheet st = xwb.getSheetAt(1); // 读取sheet的第一个工作表
			 int rows = st.getLastRowNum();// 总行数
			 System.out.println("rows = "+ rows);
			// int cols;// 总列数
			// // log.info("========行========"+rows);
			// for (int i = 0; i < rows; i++) {
			// XSSFRow row = st.getRow(i);// 读取某一行数据
			// if (row != null) {
			// // 获取行中所有列数据
			// cols = row.getLastCellNum();
			// // log.info("========行========"+rows+"=====列========"+cols);
			// for (int j = 0; j < cols; j++) {
			// XSSFCell cell = row.getCell(j);
			// if (cell == null) {
			// System.out.print(" ");
			// } else {
			// // 判断单元格的数据类型
			// switch (cell.getCellType()) {
			// case XSSFCell.CELL_TYPE_NUMERIC: // 数字
			// System.out.print(cell.getNumericCellValue() + " ");
			// break;
			// case XSSFCell.CELL_TYPE_STRING: // 字符串
			// System.out.print(cell.getStringCellValue() + " ");
			// break;
			// case XSSFCell.CELL_TYPE_BOOLEAN: // Boolean
			// System.out.println(cell.getBooleanCellValue() + " ");
			// break;
			// case XSSFCell.CELL_TYPE_FORMULA: // 公式
			// System.out.print(cell.getCellFormula() + " ");
			// break;
			// case XSSFCell.CELL_TYPE_BLANK: // 空值
			// System.out.println("");
			// break;
			// case XSSFCell.CELL_TYPE_ERROR: // 故障
			// System.out.println("故障");
			// break;
			// default:
			// System.out.print("未知类型 ");
			// break;
			// }
			// }
			// }
			// }
			// }
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * test
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String fileName = "期货中间业务统计查询 2018年2月.xlsx";
		// System.out.println(ReadExcel2.class.getResource("").getPath());
		String realPath = System.getProperty("user.dir") + "/src/fileRolder/" + fileName;
		System.out.println(realPath);
		parseSUCCEXX(realPath, fileName);

	}

}