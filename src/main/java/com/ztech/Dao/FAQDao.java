package com.ztech.Dao;

import com.ztech.Util.ConfigUtil;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class  FAQDao {
    private static Logger LOG = LoggerFactory.getLogger(FAQDao.class);
    private  List<CSVRecord> resultlist=null;

//    public Iterable<CSVRecord> getFAQlist(){
//
//        Iterable<CSVRecord> resultlist=null;
//        String csvfile = ConfigUtil.getValue("csvfile.FAQ");
//        String[] faqHeader = ConfigUtil.getValue("FAQ.header").split(",");
//        try {
//            resultlist = CSVDao.CsvRead(csvfile, faqHeader);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return resultlist;
//    }
public List<CSVRecord> getFAQlist(){
    String csvfile = ConfigUtil.getValue("csvfile.FAQ");
    String[] faqHeader = ConfigUtil.getValue("FAQ.header").split(",");
    try {
        resultlist = CSVDao.CsvRead(csvfile, faqHeader);

    } catch (IOException e) {
        e.printStackTrace();
    }
    return resultlist;
}


    public static void main(String args[]) {
        FAQDao faqDao = new FAQDao();
        List<CSVRecord> resultlist = faqDao.getFAQlist();
        LOG.info(resultlist.toString());
        LOG.info("==========================================");
        for (CSVRecord record : resultlist) {
            String answer = record.get("answer");
            String keywords = record.get("keywords");
            String[] keys = keywords.split(";");
            LOG.info(record.getRecordNumber()+" Keywords: "+keywords+ " | answer: "+answer);
//            for (String k : keys) {
//                LOG.info("k"+k);
//            }
        }


    }
}
