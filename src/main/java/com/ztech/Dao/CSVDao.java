package com.ztech.Dao;

import com.ztech.Util.ConfigUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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
        LOG.info("写入CSV文件====> " + csvFile);
    }

    public static void CsvWrite(String csvFile, String[] fileHeader, List<String[]> content) throws IOException {
        File file = new File(csvFile);
        LOG.info("File : " + file.getAbsolutePath());
        FileWriter fileWriter = null;
        CSVPrinter csvPrinter = null;
        if (!file.exists()) {
            fileWriter = new FileWriter(csvFile, true);
            //如果文件不存在，则新建文件，并写入文件表头
            csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader(fileHeader));
        } else {
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
        LOG.info("写入CSV文件====> " + csvFile);
    }


    /***
     *
     * @param csvFile
     * @param fileHeader
     * @param skipHeader
     * @return
     * @throws IOException
     *
     * FAQ.csv 文件格式
     * id,keywords,answer,answertype
     * id:序号
     * keywords: 用";"分割，一个回答可以有多个keywords
     * answer: 答复内容
     * answertype：数据字典
     *     text: 文字答复
     *     pic: 图片答复
     *     link: 图文答复
     *
     */


    public static List<CSVRecord> CsvRead(final String csvFile, final String[] fileHeader, boolean skipHeader) throws IOException {
        CSVFormat format;
        if (skipHeader) {
            // 这里显式地配置一下CSV文件的Header，然后设置跳过Header（要不然读的时候会把头也当成一条记录）
            format = CSVFormat.DEFAULT.withHeader(fileHeader).withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim();
//          format = CSVFormat.DEFAULT.withHeader(fileHeader).withIgnoreHeaderCase().withTrim().withSkipHeaderRecord();
        } else {
            format = CSVFormat.DEFAULT.withHeader(fileHeader).withIgnoreHeaderCase().withTrim();
        }
        Reader reader = Files.newBufferedReader(Paths.get(csvFile));
        CSVParser csvParser = new CSVParser(reader, format);
        return csvParser.getRecords();
    }


    public static List<CSVRecord> CsvRead(final String csvFile, final String[] fileHeader) throws IOException {
        return CsvRead(csvFile, fileHeader, true);
    }


    public static void main(String args[]) {
        String csvfile = ConfigUtil.getValue("csvfile.FAQ");
        String[] faqHeader = ConfigUtil.getValue("FAQ.header").split(",");

        try {
            Iterable<CSVRecord> resultlist = CSVDao.CsvRead(csvfile, faqHeader);
            LOG.info(resultlist.toString());
            LOG.info("==========================================");
            for (CSVRecord record : resultlist) {
//                LOG.info(record.toString());
                String answer = record.get("answer");
                String keywords = record.get("keywords");
                String[] keys = keywords.split(";");
                LOG.info("Keywords: "+keywords+ " | answer: "+answer);
                for (String k : keys) {
                    LOG.info("k"+k);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}




