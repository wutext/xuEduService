package com.xuecheng.framework.utils;


import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class ZipUtil {


    /**
     * 解压zip文件
     * @param zipFilePath
     * @param targetPath
     * @throws ZipException
     */
    public static void unzip(String zipFilePath,String targetPath) throws Exception{
        ZipFile zipFile = new ZipFile(zipFilePath);
        zipFile.extractAll(targetPath);
    }

    /**
     * 解压zip文件（带密码）
     * @param zipFilePath
     * @param targetPath
     * @param password
     * @throws ZipException
     */
    public static void unzip(String zipFilePath,String password,String targetPath) throws Exception{
        ZipFile zipFile = new ZipFile(zipFilePath, password.toCharArray());
        zipFile.extractAll(targetPath);
    }

    public static void main(String[] args) throws Exception {
        ZipUtil.unzip("F:\\develop\\upload\\upload.zip","F:\\develop\\upload\\zip\\");
    }
}
