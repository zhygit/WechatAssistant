package com.ztech;

import cn.zhouyafeng.itchat4j.Wechat;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;
import com.ztech.Util.ConfigUtil;
import com.ztech.Util.FileFolderUtils;
import com.ztech.controller.MyController;
import org.apache.log4j.Logger;

import java.io.File;

public class WechatAssistant {
    public static void main(String[] args) {
        Logger log = Logger.getLogger(WechatAssistant.class);
        String qrPath = "/Users/zhy/IdeaProjects/WechatAssistant/loginQR"; // 保存登陆二维码图片的路径，这里需要在本地新建目录
//      配置文件方式指定输出路径
//      String basepath = ConfigUtil.getValue("basepath");
//      jar包执行时，使用相对路径
        String basepath = WechatAssistant.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        log.info("jar path : "+basepath);

        FileFolderUtils.InitFolders(basepath);
        IMsgHandlerFace msgHandler = new MyController(); // 实现IMsgHandlerFace接口的类
        Wechat wechat = new Wechat(msgHandler, qrPath); // 【注入】
        wechat.start(); // 启动服务，会在qrPath下生成一张二维码图片，扫描即可登陆，注意，二维码图片如果超过一定时间未扫描会过期，过期时会自动更新，所以你可能需要重新打开图片
    }
}
