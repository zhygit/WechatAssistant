package com.ztech.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class FileFolderUtils {
    public static final Logger log = LoggerFactory.getLogger(FileFolderUtils.class);

    public static void InitFolders(String basepath) {
        log.info("检查输出文件夹......");
        String folderpath = "";
        if (basepath.endsWith(".jar")){
            log.info(".jar 封装模式运行中 ....");
            folderpath = basepath.substring(0, basepath.lastIndexOf(File.separator) + 1);
        }else {
            log.info("IDE 直接运行中 ....");
            folderpath = basepath;
        }
        log.info("运行目录: "+ folderpath);
        CheckExitsAndCreateFolder(folderpath);
        CheckExitsAndCreateFolder(folderpath, "loginQR");
        CheckExitsAndCreateFolder(folderpath,"files");
        CheckExitsAndCreateFolder(folderpath,"pics");
        CheckExitsAndCreateFolder(folderpath,"vidoe");
        CheckExitsAndCreateFolder(folderpath,"voice");
        log.info("输出文件夹检查完毕");

    }



    private static void CheckExitsAndCreateFolder(String basepath, String subfolder) {
        String folderpath = basepath + File.separator+"/files"+File.separator+subfolder;
        File fileFolder = new File(folderpath);
        if  (!fileFolder .exists()  && !fileFolder .isDirectory())
        {
            fileFolder .mkdir();
            log.info("创建: " + fileFolder);
        } else
        {
            log.info("检查 " +fileFolder + " | 已存在 ");
        }
    }


    private static void CheckExitsAndCreateFolder(String basepath) {
        String folderpath = basepath;
        File fileFolder = new File(folderpath);
        if  (!fileFolder .exists()  && !fileFolder .isDirectory())
        {
            fileFolder .mkdir();
            log.info("创建: " + fileFolder);
        } else
        {
            log.info("检查 " +fileFolder + " | 已存在 ");
        }
    }

}
