package com.ztech.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.ztech.Dao.FAQDao;
import com.ztech.beans.Answer;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AIResponseService {
    private static Logger log = LoggerFactory.getLogger(AIResponseService.class);

    private static Iterable<CSVRecord> resultlist;
    static {
        FAQDao faqDao = new FAQDao();
        resultlist = faqDao.getFAQlist();
        log.info("=======  FAQ 问题集 ====================");
        for (CSVRecord record : resultlist) {
            String answer = record.get("answer");
            String keywords = record.get("keywords");
            String[] keys = keywords.split(";");
            log.info(record.getRecordNumber()+" Keywords: "+keywords+ " | answer: "+answer);
//            for (String k : keys) {
//                log.info("k"+k);
//            }
        }
        log.info("=======  FAQ问题集 加载完毕 供 "+resultlist.spliterator().getExactSizeIfKnown() +"条 ==========");
    }

    public String getAnswerByQuestion(String question) {
        List<CSVRecord> ansSet = new ArrayList<CSVRecord>();
        StringBuffer answerStr = new StringBuffer();
        Boolean isGetAnswer = false;
        for (CSVRecord record : resultlist) {
            String keywd = record.get("keywords");
                if (question.contains(keywd)) {
                    log.info(record.getRecordNumber()+ " key : "+question +" ---> 命中 : "+ keywd);
                    ansSet.add(record);
                    isGetAnswer = true;
            }
//            String[] keys = keywords.split(";");
//            for (String k : keys) {
//                if (question.contains(k)) {
//                    log.info(record.getRecordNumber()+ " key : "+question +" ---> 命中 : "+ k);
//                    answerStr = record.get("answer");
//                    Answer answer = new Answer();
//                    answer.setKeywords();
//                    ansSet.add()
//                }
//            }
        }
        if (isGetAnswer) {
            answerStr = beautifyAnswerString(ansSet);
        } else {
            answerStr.append("未找到您所提到的问题，请尝试其他问题");
        }
        return answerStr.toString();
    }

    public StringBuffer beautifyAnswerString(List<CSVRecord> answserList) {
        StringBuffer answerStr = new StringBuffer();
        String tpl1 = "\n您的问题可参考如下解决方案:\n";
        String tpl2 = "%d.关于\"%s\" 的问题:\n";
        answerStr.append(tpl1);
        for (int i=0;i<answserList.size();i++) {
            CSVRecord record = answserList.get(i);
            answerStr.append(String.format(tpl2, i + 1, record.get("keywords")));
            answerStr.append(record.get("answer")).append("\n");

        }

        return answerStr;

    }

    public static void main(String arg[]) {
        AIResponseService aiResponseService = new AIResponseService();
        String q = "清单";
        String a= aiResponseService.getAnswerByQuestion(q);
        log.info("===========================");
        log.info(a);
        log.info("===========================");

    }

}
