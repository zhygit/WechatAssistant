package com.ztech.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigUtil {
    public static final Logger log = LoggerFactory.getLogger(ConfigUtil.class);

    private static String PropFile = "/config.properties";

    public static String getValue(String key) {
        Properties prop = new Properties();
        InputStream in = null;
        try {
            in = ConfigUtil.class.getResourceAsStream(PropFile);
            prop.load(new InputStreamReader(in, "UTF-8"));
            String value = prop.getProperty(key);
            return value;
        } catch (Exception e) {
            log.error("", e);
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }

        }
    }

    public static Map<String, String> getValueMap(Map<String, String> map) {
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = ConfigUtil.class.getResourceAsStream(PropFile);
            properties.load(in);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = properties.getProperty(key);
                map.put(key, value);
            }
            return map;

        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static void setValue(String key, String value) {
        Properties properties = new Properties();
        try {
            InputStream fis = ConfigUtil.class.getResourceAsStream(PropFile);
            properties.load(fis);
            OutputStream fos = new FileOutputStream(ConfigUtil.class.getClassLoader().getResource("").getPath() + PropFile);
            properties.setProperty(key, value);
            properties.store(fos,"Update '"+key +"'value");
        } catch (Exception e) {
            log.error("",e);
        }
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("csvfile.output.path", "");
        map.put("Hello", "");
        map.put("password", "");


        ConfigUtil.setValue("Hello","World");
        map = ConfigUtil.getValueMap(map);
        log.info(map.get("Hello"));
        log.info(map.get("password"));
        log.info(map.get("csvfile.output.path"));
        log.info(map.toString());
        log.info(ConfigUtil.getValue("URL"));
    }


}
