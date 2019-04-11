package com.ztech.Dao;

import com.ztech.Util.DBUtil;
import com.ztech.beans.MsgLog;

import java.sql.Connection;

public class MsgDao {
    DBUtil dbUtil = new DBUtil();

    public int insertMsgLog(MsgLog msgLog) {
        Connection connection = dbUtil.getConnection();
        String sql = "insert into msglog(msgid,fromUserID,toUserID,fromusername,tousername,msgtype,isgroupmsg,content," +
                "groupnickname,msgtime,isSend,loginuser) values (?,?,?,?,?,?,?,?,?,?,?,?)";

        Object[] p = new Object[12];
        p[0] = msgLog.getMsgid();
        p[1] = msgLog.getFromUserID();
        p[2] = msgLog.getToUserID();
        p[3] = msgLog.getFromusername();
        p[4] = msgLog.getTousername();
        p[5] = msgLog.getMsgtype();
        p[6] = msgLog.isIsgroupmsg();
        p[7] = msgLog.getContent();
        p[8] = msgLog.getGroupnickname();
        p[9] = msgLog.getMsgtime();
        p[10] = msgLog.getIsSend();
        p[11] = msgLog.getLoginuser();
        return dbUtil.executeUpdate(sql, p);

    }
}
