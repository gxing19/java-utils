package com.gxing.utils.excelutil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
/**
 * 下载工具类
 *
 */
public class ExcelUtil {
	
	/**
	 * 初始化导出excel参数
	 * @param request
	 * @param response
	 * @param fileName **.xls
	 * @param downFileName	**.xls
	 * @return
	 */
	public static FileInputStream initDownloadExcel(HttpServletRequest request, HttpServletResponse response, String fileName, String downFileName){
		initDownloadExcelToResponse(response,downFileName);
		return getFile(request,fileName);
	}
	
	/**
	 * 获取模板文件
	 * @param request
	 * @param fileName  **.xls
	 * @return
	 */
	public static FileInputStream getFile(HttpServletRequest request, String fileName) {
		String realPath = request.getServletContext().getRealPath("/");
		FileInputStream file = null;
		try {
			file = new FileInputStream(new File(realPath + fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 设置响应头
	 * @param response
	 * @param downFileName  **.xls
	 * @throws UnsupportedEncodingException 
	 */
	public static void initDownloadExcelToResponse(HttpServletResponse response, String downFileName) {
		response.reset();
		String string = "";
		try {
			string = new String(downFileName.getBytes("UTF-8"),"ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			string = "encodingErro."+downFileName.substring(downFileName.indexOf('.'));
			e.printStackTrace();
		}
		response.setHeader("Content-Disposition", "attachment;filename="+string);
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	}

}
