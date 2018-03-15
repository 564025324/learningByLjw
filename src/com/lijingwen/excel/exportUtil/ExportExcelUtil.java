package com.lijingwen.excel.exportUtil;

import java.io.*;
import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.T;

/**
 * 利用开元组建POI3.2动态导出excel文档 转载时注意请保留一下信息，注明出处
 * 
 * @param 应用泛型代表任意一个符合javabean风格的类
 *            byte[]表jpg格式的图片数据
 */
public class ExportExcelUtil {

	public void exportExcel(Collection dataset, OutputStream out) {
		exportExcel("测试POI导出Excel文档", null, dataset, out, "yyyy-MM-dd");
	}

	public void exportExcel(String[] headers, Collection dataset,
			OutputStream out) {
		exportExcel("测试POI导出Excel文档", headers, dataset, out, "yyyy-MM-dd");
	}

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL的形式输出的制定IO设备上。
	 * 
	 * @param title
	 *            表格标题名
	 * @param headers
	 *            表格属性列名数组
	 * @param dataset
	 *            需要显示的数据集合，集合中一定要放置符合Javabean风格的类的对象
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间格式，设定输出格式。默认为"yyyy-MM-dd"
	 * 
	 */
	// @SuppressWarning("unchecked")
	public void exportExcel(String title, String[] headers,
			Collection<T> dataset, OutputStream out, String pattern) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		// sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		// HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		// style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		// style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成字体
		// HSSFFont font = workbook.createFont();
		// font.setColor(HSSFColor.VIOLET.index);
		// font.setFontHeightInPoints((short)12);

		// 生成表头
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		// 遍历数据集合，产生数据行
		Iterator it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();

			for (int j = 0; j < fields.length; j++) {
				HSSFCell cell = row.createCell(j);
				Field field = fields[j];
				String fieldName = field.getName();
				String getMethodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName,
							new Class[] {});
					Object value = getMethod.invoke(tCls, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					// if (value instanceof Integer) {
					// int intValue = (Integer) value;
					// cell.setCellValue(intValue);
					// } else if (value instanceof Float) {
					// float fValue = (Float) value;
					// textValue = new HSSFRichTextString(
					// String.valueOf(fValue));
					// cell.setCellValue(textValue);
					// } else if (value instanceof Double) {
					// double dValue = (Double) value;
					// textValue = new HSSFRichTextString(
					// String.valueOf(dValue));
					// cell.setCellValue(textValue);
					// } else if (value instanceof Long) {
					// long longValue = (Long) value;
					// cell.setCellValue(longValue);
					// }
					if (value instanceof Boolean) {
						boolean bvalue = (Boolean) value;
						textValue = "男";
						if (!bvalue) {
							textValue = "女";
						} else if (value instanceof Date) {
							Date date = (Date) value;
							SimpleDateFormat sdf = new SimpleDateFormat(pattern);
							textValue = sdf.format(date);
						} else if (value instanceof byte[]) {
							// 有图片时，设置行高为60px;
							row.setHeightInPoints(60);
							// sheet.setColumnWidth(j, (short) (35.7 * 80));
							// sheet.autoSizeColumn(i);
							byte[] bsValue = (byte[]) value;
							// HSSFClientAnchor anchor = new HSSFClientAnchor(0,
							// 0,
							// 1023, 255, (short) 6, index, (short) 6, index);
							// anchor.setAnchorType(2);
							// patriarch.createPicture(anchor,
							// workbook.addPicture(
							// bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
						} else {
							// 其它数据类型都当作字符串简单处理
							textValue = value.toString();
						}
						// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
						if (textValue != null) {
							Pattern p = Pattern.compile("^//d+(//.//d+)?$");
							Matcher matcher = p.matcher(textValue);
							if (matcher.matches()) {
								// 是数字当作double处理
								cell.setCellValue(Double.parseDouble(textValue));
							} else {
								HSSFRichTextString richString = new HSSFRichTextString(
										textValue);
								// HSSFFont font3 = workbook.createFont();
								// font3.setColor(HSSFColor.BLUE.index);
								// richString.applyFont(font3);
								cell.setCellValue(richString);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}
		}
		try {
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	// --------------------------------
	public static void main(String[] args) {

		  // 测试学生

		  ExportExcel<Student> ex = new ExportExcel<Student>();

		  String[] headers = { "学号", "姓名", "年龄", "性别", "出生日期" };

		  List<Student> dataset = new ArrayList<Student>();

		  dataset.add(new Student(10000001, "张三", 20, true, new Date()));

		  dataset.add(new Student(20000002, "李四", 24, false, new Date()));

		  dataset.add(new Student(30000003, "王五", 22, true, new Date()));

		  try {

		     BufferedInputStream bis = new BufferedInputStream(

		            new FileInputStream("book.jpg"));

		     byte[] buf = new byte[bis.available()];

		     while ((bis.read(buf)) != -1) {

		        //

		     }

		     dataset2.add(new Book(1, "jsp", "leno", 300.33f, "1234567",

		            "清华出版社", buf));

		     dataset2.add(new Book(2, "java编程思想", "brucl", 300.33f, "1234567",

		            "阳光出版社", buf));

		     dataset2.add(new Book(3, "DOM艺术", "lenotang", 300.33f, "1234567",

		            "清华出版社", buf));

		     dataset2.add(new Book(4, "c++经典", "leno", 400.33f, "1234567",

		            "清华出版社", buf));

		     dataset2.add(new Book(5, "c#入门", "leno", 300.33f, "1234567",

		            "汤春秀出版社", buf));



		     OutputStream out = new FileOutputStream("E://a.xls");

		     OutputStream out2 = new FileOutputStream("E://b.xls");

		     ex.exportExcel(headers, dataset, out);

		     ex2.exportExcel(headers2, dataset2, out2);

		     out.close();

		     JOptionPane.showMessageDialog(null, "导出成功!");

		     System.out.println("excel导出成功！");

		  } catch (FileNotFoundException e) {

		     // TODO Auto-generated catch block

		     e.printStackTrace();

		  } catch (IOException e) {

		     // TODO Auto-generated catch block

		     e.printStackTrace();

		  }

		}

		} 
	
	
}
