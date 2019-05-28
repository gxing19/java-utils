package com.gxing.utils.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * 添加poi jar包
 * poi与excel表操作
 * 1.创建excel工作薄
 * 2.由工作薄创建工作表
 * 3.由工作表创建行
 * 4.由行创建单元格
 * 5.填写单元格的内容
 * 6.最后由工作薄输出excel文件
 * @author xing
 */

public class Demo1_xls
{
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		//1.创建工作薄
		HSSFWorkbook book = new HSSFWorkbook();
		//2.由工作薄创建工作表
		HSSFSheet sheet = book.createSheet();		
		//设置单元格的宽度：1代表一个字母的256分之一
		sheet.setColumnWidth(0, 6000);
		//3.由工作表创建行
		HSSFRow row = sheet.createRow(0);
		//4.由行创建单元格
		HSSFCell cell = row.createCell(0);
		//5.填写单元格的内容
		cell.setCellValue("第一个单元格");
		//6.最后由工作薄输出excel文件
		book.write(new FileOutputStream("d:\\Demo1_xls.xls"));
		System.out.println("输出成功");
		
		book.close();
	}
}
