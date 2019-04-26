package com.ztech.beans;

import java.util.List;

public class Answer {
    private String id;
    private String answer;
    private String answertype;
    private List<String> keywords;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswertype() {
        return answertype;
    }

    public void setAnswertype(String answertype) {
        this.answertype = answertype;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id='" + id + '\'' +
                ", answer='" + answer + '\'' +
                ", answertype='" + answertype + '\'' +
                ", keywords=" + keywords +
                '}';
    }
}
