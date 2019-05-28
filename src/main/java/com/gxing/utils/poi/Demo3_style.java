package com.gxing.utils.poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Demo3_style
{
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		//1.创建工作簿
		HSSFWorkbook book = new HSSFWorkbook();
		//2.由工作簿创建工作表
		HSSFSheet sheet = book.createSheet();
		/**
		 * 设置表格样式
		 * 1.边框：上，下，左，右
		 * 2.居中：水平居中，垂直居中
		 */
		//由工作簿创建样式对象,样式是作用在单元格上的
		HSSFCellStyle cellStyle = book.createCellStyle();
		//设置表边框线，枚举值BORDER_THIN：细线
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  		   //水平居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中 
		
		/**
		 * 设置单元格字体内容样式
		 * 1.字号大小，加粗
		 * 2.字体名称
		 * 3.字符颜色
		 */
		//由工作簿创建字体对象
		HSSFFont font = book.createFont();
		font.setFontName("黑体");					//设置字体名
		font.setBold(true);							//粗体
		font.setFontHeightInPoints((short) 14);		//设字号
		cellStyle.setFont(font); 					//启用字体样式
		
		//循环生成指定数量的行和列
		for (int i = 0; i < 15; i++)
		{
			HSSFRow row = sheet.createRow(i);
			row.setHeight((short) 400);				//设置行高
			for (int j = 0; j < 4; j++)
			{
				row.createCell(j).setCellStyle(cellStyle);	//创建单元格并启用样式
			}
		}
		for (int j = 0; j < 4; j++)
		{
			sheet.setColumnWidth(j, 3000);					//设置列宽
		}
		
		//获取当前时间
		String nowDateTime = sdf.format(new Date());
		//合并单元格，合并第二行的第1到第4的单元格
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 3));
		//获取行.单元格.设置内容=单前时间
		sheet.getRow(1).getCell(0).setCellValue(nowDateTime);
		HSSFCellStyle cellStyle2 = book.createCellStyle();		//样式对象
		//从某个样式处克隆样式
		cellStyle2.cloneStyleFrom(cellStyle);	
		
		HSSFFont font2 = book.createFont();
		font2.setFontName("楷体");	//字体名
		font2.setFontHeightInPoints((short) 11);	//字号
		font2.setColor(HSSFColor.RED.index);		//设置字体颜色
		System.out.println(HSSFColor.RED.index);
		cellStyle2.setFont(font2);	//启用字体样式
		
		sheet.getRow(2).getCell(0).setCellStyle(cellStyle2);//启用表格样式
		sheet.getRow(2).getCell(0).setCellValue("第一组");	//设置单元格内容
		
		book.write(new FileOutputStream("d:/Demo3_style.xls"));
		System.out.println("设置成功");
		
		book.close();
	}
}
