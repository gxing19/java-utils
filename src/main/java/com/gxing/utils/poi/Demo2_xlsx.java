package com.gxing.utils.poi;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Demo2_xlsx
{
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		// 1.创建工作薄
		XSSFWorkbook book = new XSSFWorkbook();
		// 2.由工作薄创建工作表
		XSSFSheet sheet = book.createSheet();
		// 由工作表设置单元格宽度;1代表一个字母的256分之一
		sheet.setColumnWidth(0, 6000);
		// 3.由工作表创建行
		XSSFRow row = sheet.createRow(0);
		// 4.由行创建单元格
		XSSFCell cell = row.createCell(0);
		// 5.填写单元格的内容
		cell.setCellValue("xlsx文档");
		// 6.最后由工作薄输出excel文件
		book.write(new FileOutputStream("d:\\Demo2_xlsx.xlsx"));

		System.out.println("输出成功");
		
		book.close();
	}
}
