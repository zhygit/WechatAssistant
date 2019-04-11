package com.ztech.Dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class CSVDao {

    private static Logger LOG = LoggerFactory.getLogger(CSVDao.class);
    private static CSVFormat csvFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");


    public static void CsvReWrite(String csvFile, String[] fileHeader, List<String[]> content) throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvFile));
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(fileHeader));

        for (String[] c : content) {
            csvPrinter.printRecord(Arrays.asList(c));
        }
        csvPrinter.flush();
        csvPrinter.close();
        LOG.info("写入CSV文件====> "+csvFile);
    }

    public static void CsvWrite(String csvFile, String[] fileHeader, List<String[]> content) throws IOException {
        File file = new File(csvFile);
        LOG.info("File : "+ file.getAbsolutePath());
        FileWriter fileWriter = null;
        CSVPrinter csvPrinter = null;
        if (!file.exists()) {
            fileWriter = new FileWriter(csvFile, true);
            //如果文件不存在，则新建文件，并写入文件表头
            csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader(fileHeader));
        }else {
            //如果已存在则,只写数据，不写表头
            fileWriter = new FileWriter(csvFile, true);
            csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withSystemRecordSeparator());
        }
//        BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvFile));

        for (String[] c : content) {
            csvPrinter.printRecord(Arrays.asList(c));
        }
        csvPrinter.flush();
        fileWriter.flush();
        fileWriter.close();
        csvPrinter.close();
        LOG.info("写入CSV文件====> "+csvFile);
    }


}
