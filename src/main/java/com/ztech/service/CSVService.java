package com.ztech.service;

import com.ztech.Dao.CSVDao;
import com.ztech.Util.ConfigUtil;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVService {
    private static final Logger LOG = LoggerFactory.getLogger(CSVService.class);

    public void write(String csvFile,String[] fileHeader,List<String[]> content) {
        try {
            CSVDao.CsvWrite(csvFile, fileHeader, content);
        } catch (IOException e) {
            LOG.error("写CSV文件异常：",e);
        }
    }


    public List<CSVRecord> read(String csvFile,String[] fileHeader) throws IOException {
        return CSVDao.CsvRead(csvFile, fileHeader, true);
    }

    public static void main(String[] args) {
        List<String[]> list = new ArrayList<String[]>();
        list.add(new String[]{"123","@asdfa","1","zhangsan","Jim","hello world","2019-11-12","1","text","@asdfa","TOM","Green"});
        CSVService csvService = new CSVService();
        String csvfile = ConfigUtil.getValue("csvfile.output.path");
        String[] fileHeader = ConfigUtil.getValue("csv.header").split(",");
        csvService.write(csvfile,fileHeader,list);
        list.add(new String[]{"456","@asdfa","1","zhangsan","Jim","hello world","2019-11-12","1","text","@asdfa","TOM","Green"});
        csvService.write(csvfile,fileHeader,list);
    }

}
