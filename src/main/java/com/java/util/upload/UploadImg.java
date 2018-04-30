package com.java.util.upload;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.java.util.file.FileSuffix;


public class UploadImg {

	/**
	 * 文件上传
	 * @param request
	 * @param uploadImage
	 * @return
	 * @throws Exception
	 */
	public static String uploadImage(HttpServletRequest request, MultipartFile uploadImage) throws Exception {
		// 存放上传文件的绝对路路径
		String path = request.getServletContext().getRealPath("updown/images");

		// 上传的文件名
		String filename = uploadImage.getOriginalFilename();

		// 获取后缀
		String suffix = FileSuffix.getSuffix(filename);

		// 以当前时间毫秒值加随机数重新命令文件名
		String newFileName = RandomID.getRandomID() + suffix;

		// 保存文件
		uploadImage.transferTo(new File(path + File.separator + newFileName));

		// 存到数据库的相对路径
		String imgSavePath = File.separator + "updown" + File.separator + "images" + File.separator + newFileName;

		return imgSavePath;
	}
}
class RandomID{

	public static String getRandomID() {
		// TODO Auto-generated method stub
		return null;
	}}
