package com.ztech.beans;

public class MsgLog {
    private String msgid;
    private String fromUserID;
    private String toUserID;
    private String fromusername;
    private String tousername;
    private String msgtype;
    private boolean isgroupmsg;
    private String content;
    private String groupnickname;
    private String msgtime;
    private String isSend;
    private String loginuser;

    public String getMsgid() {
        return msgid;
    }

    public String getGroupnickname() {
        return groupnickname;
    }

    public void setGroupnickname(String groupnickname) {
        this.groupnickname = groupnickname;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getFromUserID() {
        return fromUserID;
    }

    public void setFromUserID(String fromUserID) {
        this.fromUserID = fromUserID;
    }

    public String getToUserID() {
        return toUserID;
    }

    public void setToUserID(String toUserID) {
        this.toUserID = toUserID;
    }

    public String getFromusername() {
        return fromusername;
    }

    public void setFromusername(String fromusername) {
        this.fromusername = fromusername;
    }

    public String getTousername() {
        return tousername;
    }

    public void setTousername(String tousername) {
        this.tousername = tousername;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public boolean isIsgroupmsg() {
        return isgroupmsg;
    }

    public void setIsgroupmsg(boolean isgroupmsg) {
        this.isgroupmsg = isgroupmsg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgtime() {
        return msgtime;
    }

    public void setMsgtime(String msgtime) {
        this.msgtime = msgtime;
    }

    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend;
    }

    public String getLoginuser() {
        return loginuser;
    }

    public void setLoginuser(String loginuser) {
        this.loginuser = loginuser;
    }

    @Override
    public String toString() {
        return "MsgLog{" +
                "msgid='" + msgid + '\'' +
                ", fromUserID='" + fromUserID + '\'' +
                ", toUserID='" + toUserID + '\'' +
                ", fromusername='" + fromusername + '\'' +
                ", tousername='" + tousername + '\'' +
                ", msgtype='" + msgtype + '\'' +
                ", isgroupmsg=" + isgroupmsg +
                ", content='" + content + '\'' +
                ", groupnickname='" + groupnickname + '\'' +
                ", msgtime='" + msgtime + '\'' +
                ", isSend='" + isSend + '\'' +
                ", loginuser='" + loginuser + '\'' +
                '}';
    }
}
