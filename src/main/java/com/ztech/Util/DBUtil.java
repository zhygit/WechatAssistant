package com.ztech.Util;

import cn.zhouyafeng.itchat4j.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtil {

    private static Logger LOG = LoggerFactory.getLogger(DBUtil.class);
    // JDBC 驱动名及数据库 URL
//    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//    private static final String URL = "jdbc:mysql://localhost:3306/wechat?useUnicode=true&characterEncoding=utf-8";
    private static final String JDBC_DRIVER = ConfigUtil.getValue("driver");
    private static final String URL = ConfigUtil.getValue("url");

    // 数据库的用户名与密码，需要根据自己的设置
    private static final String username = ConfigUtil.getValue("username");
    private static final String password = ConfigUtil.getValue("password");


    private static Connection conn = null;
    private CallableStatement callableStatement = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;


    //对外提供一个方法来获取数据库连接
    public  Connection getConnection(){

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(URL, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
     }

    public void closeAll() {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (pst != null) {
            try {
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public int executeUpdate(String sql, Object[] params) {
        int affectedLine = 0;
        try {
            conn = this.getConnection();
            pst = conn.prepareStatement(sql);

            if (params != null) {
                for (int i=0;i<params.length;i++) {
                    pst.setObject(i+1,params[i]);
                }
            }
            affectedLine = pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return affectedLine;
    }

    public static void main(String[] args) throws SQLException {
        DBUtil dbUtil = new DBUtil();
        Connection connection = dbUtil.getConnection();
        Statement stmt = connection.createStatement();
        String sql = "select * from msglog";
        ResultSet rs = stmt.executeQuery(sql);
        List list = new ArrayList();
        ResultSetMetaData md = rs.getMetaData();
        int colcnt = md.getColumnCount();
        while (rs.next()) {
            Map row = new HashMap<>();
            for(int i=1;i<=colcnt;i++) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(row);

        }
        LOG.info(list.toString());
        stmt.close();
    }


}
