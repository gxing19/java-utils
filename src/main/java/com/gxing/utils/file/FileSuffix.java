package com.gxing.utils.file;

import java.io.File;

public class FileSuffix {

    /**
     * 获取文件名后缀,包含.
     *
     * @param filename
     * @return
     */
    public static String getSuffix(String filename) {
        File file = new File(filename);
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        return suffix;
    }

    /**
     * 获取文件后缀名，.后面的名称,不包含点
     *
     * @param filename
     * @return
     */
    public static String getSuffixWithoutPoint(String filename) {
        File file = new File(filename);
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return suffix;
    }
}
