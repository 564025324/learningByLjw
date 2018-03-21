package com.lijingwen.excel.exportUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.lijingwen.po.QhibSxflog;

public class ReadExcelByPoi {

	public static void readExcelMethod(String filePath) {
		try {
			File file = new File(filePath);
			InputStream str = new FileInputStream(file);
			XSSFWorkbook workbook = null;
			// 判断文件是否存在
			if (!file.exists()) {
				System.out.println("没有文件");
			} else {
				workbook = new XSSFWorkbook(str);
				int sheetnum = workbook.getNumberOfSheets();
				// 选择Sheet页
				XSSFSheet sheet = null;
				for (int i = 0; i < sheetnum; i++) {
					if ("Sheet1".equals(workbook.getSheetName(i)))
						sheet = workbook.getSheetAt(i);
				}
				String fundid = null;
				String fundname = null;
				String sxf = null;
				// 确认代取字段所在列
				int rowLength = sheet.getPhysicalNumberOfRows(); // 行总数

				System.out.println(sheet.getRow(0).getPhysicalNumberOfCells());

				int cellLength = 0;
				String titleValue = null;
				for (int i = 0; i < rowLength; i++) { // 只取前几列即可
					XSSFRow row = sheet.getRow(i);
					cellLength = (row.getPhysicalNumberOfCells() > cellLength) ? row.getPhysicalNumberOfCells()
							: cellLength;
				}

				int cust_col = 0;
				int sxf_col = 0;
				int fundname_col = 0;
				for (int i = 0; i < rowLength; i++) {
					XSSFRow row = sheet.getRow(i);
					for (int j = 0; j < cellLength; j++) {
						XSSFCell cell = row.getCell(j);
						if (cell == null)
							continue;
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						titleValue = cell.getStringCellValue().trim();
						if ("客户代码".equals(titleValue))
							cust_col = j;
						if ("手续费".equals(titleValue))
							sxf_col = j;
						if ("客户姓名".equals(titleValue))
							fundname_col = j;
					}
				}

				// 取值并保存
				XSSFCell cell_fundid = null;
				XSSFCell cell_fundname = null;
				XSSFCell cell_sxf = null;
				for (int i = 2; i < rowLength; i++) {
					cell_fundid = sheet.getRow(i).getCell(cust_col);
					cell_fundname = sheet.getRow(i).getCell(fundname_col);
					cell_sxf = sheet.getRow(i).getCell(sxf_col);
					if (cell_fundid == null || cell_fundname == null || cell_sxf == null)
						continue;
					cell_fundid.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell_fundname.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell_sxf.setCellType(HSSFCell.CELL_TYPE_STRING);

					fundid = cell_fundid.getStringCellValue().trim();
					fundname = cell_fundname.getStringCellValue().trim();
					sxf = cell_sxf.getStringCellValue().trim();

					if (fundid.length() != 12 || fundid == null || "".equals(sxf.trim()) || sxf == null) {
						continue;
					}
					if (Float.valueOf(sxf) == 0) {
						continue;
					}
					QhibSxflog qhibInfo = new QhibSxflog();
					qhibInfo.setFundid(fundid);
					qhibInfo.setSxf(Float.valueOf(sxf));
					qhibInfo.setFundname(fundname);
					System.out.println(qhibInfo.toString());

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		String fileName = "期货中间业务统计查询 2018年2月.xlsx";
		// System.out.println(ReadExcel2.class.getResource("").getPath());
		String realPath = System.getProperty("user.dir") + "/src/fileRolder/" + fileName;
		// System.out.println(realPath);
		readExcelMethod(realPath);
	}

}
