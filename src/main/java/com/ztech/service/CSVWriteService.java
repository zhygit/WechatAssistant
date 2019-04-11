package com.ztech.service;

import cn.zhouyafeng.itchat4j.utils.Config;
import com.ztech.Dao.CSVDao;
import com.ztech.Util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CSVWriteService {
    private static final Logger LOG = LoggerFactory.getLogger(CSVWriteService.class);
//    private static String csvFile = ConfigUtil.getValue("csvfile.output.path");
//    private String[] fileHeader = null;


//    static {
//        Properties prop = new Properties();
//        InputStream in = CSVDao.class.getResourceAsStream("/config.properties");
//        try {
//            prop.load(new InputStreamReader(in, "UTF-8"));
//            String root = CSVDao.class.getResource("/").getPath();
//            csvFile = prop.getProperty("csvfile.output.path");
//            String header = prop.getProperty("csv.header");
//            fileHeader = header.split(",");
//        } catch (Exception e) {
//            LOG.error("",e);
//        }finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    LOG.error("",e);
//                }
//            }
//
//        }
//
//        File parentDir = new File(csvFile).getParentFile();
//
//        if(!parentDir.exists()){
//            LOG.warn("CSV文件父目录不存在，即将创建 : "+parentDir);
//            parentDir.mkdir();
//        }
//    }

    public void write(String csvFile,String[] fileHeader,List<String[]> content) {
        try {
            CSVDao.CsvWrite(csvFile, fileHeader, content);
        } catch (IOException e) {
            LOG.error("写CSV文件异常：",e);
        }
    }

    public static void main(String[] args) {
        List<String[]> list = new ArrayList<String[]>();
        list.add(new String[]{"123","@asdfa","1","zhangsan","Jim","hello world","2019-11-12","1","text","@asdfa","TOM","Green"});
        CSVWriteService csvWriteService = new CSVWriteService();
        String csvfile = ConfigUtil.getValue("csvfile.output.path");
        String[] fileHeader = ConfigUtil.getValue("csv.heade").split(",");
        csvWriteService.write(csvfile,fileHeader,list);
        list.add(new String[]{"456","@asdfa","1","zhangsan","Jim","hello world","2019-11-12","1","text","@asdfa","TOM","Green"});
        csvWriteService.write(csvfile,fileHeader,list);
    }

}
