package com.ztech.controller;

import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.api.WechatTools;
import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.beans.RecommendInfo;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;
import cn.zhouyafeng.itchat4j.utils.enums.MsgTypeEnum;
import cn.zhouyafeng.itchat4j.utils.tools.DownloadTools;
import com.alibaba.fastjson.JSONArray;
import com.ztech.Dao.MsgDao;
import com.ztech.Util.ConfigUtil;
import com.ztech.beans.MsgLog;
import com.ztech.service.CSVWriteService;
import org.apache.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyController implements IMsgHandlerFace {
    Logger LOG = Logger.getLogger(MyController.class);
    MsgDao msgDao = new MsgDao();


    //保存微信收到的文件类地址
    String basepath = "/Users/zhy/IdeaProjects/WechatAssistant/files";
    @Override
    public String textMsgHandle(BaseMsg msg) {
        String text = msg.getText();
        String fromUserNameID = msg.getFromUserName();  //@xxxxxx
        String toUserNameID = msg.getToUserName();      //@xxxxx
        String fromNickName = WechatTools.getNickNameByUserName(fromUserNameID);  //名称
        String toNickName = WechatTools.getNickNameByUserName(toUserNameID);      //
        String selfName = Core.getInstance().getUserName();
        String selfNickName = Core.getInstance().getNickName();
        int isSend = msg.getIsSend();
        String isSendOrRev = "";
        if (isSend == 1) {
            isSendOrRev = "发送消息";

        }else {
            isSendOrRev = "收到消息";
        }
        MsgLog msgLog = new MsgLog();
        msgLog.setMsgid(msg.getMsgId());

        msgLog.setToUserID(msg.getToUserName());
        msgLog.setTousername(toNickName);
        msgLog.setMsgtype(msg.getType());
        msgLog.setIsgroupmsg(msg.isGroupMsg());
        msgLog.setContent(msg.getContent());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        msgLog.setMsgtime(date);
        msgLog.setIsSend(Integer.toString(isSend));
        msgLog.setLoginuser(Core.getInstance().getNickName());

        if (msg.isGroupMsg()) {
            String fromGroupMemberID = msg.getFromGroupMember();
            String fromGroupMemberNickName = WechatTools.getMemberDisplayNickNameByUserName(fromUserNameID, fromGroupMemberID);
            String toGroupMemberNickName = WechatTools.getMemberDisplayNickNameByUserName(fromUserNameID, toUserNameID);
            String groupNickName = "";
            String groupId = msg.getGroupId();
            String msgid = msg.getMsgId();
            groupNickName = msg.getGroupNickName();


            msgLog.setGroupnickname(groupNickName);
            if (isSend==1) { //发送至群
                msgLog.setFromUserID(fromUserNameID);  //来自本人
                msgLog.setFromusername(fromNickName); //来自本人
                msgLog.setTousername(groupNickName);  //发送至群
            } else {
                msgLog.setFromusername(fromGroupMemberNickName);
                msgLog.setFromUserID(fromGroupMemberID);
            }

            JSONArray array = WechatTools.getMemberListByGroupId(groupId);
            LOG.info("====================== "+ isSendOrRev+" =======================================");
            LOG.info("GroupMsg : "+ groupNickName +" | " + fromGroupMemberNickName);
            LOG.info("ToUser   : "+ toGroupMemberNickName);
            LOG.info("Msg      : "+ text);
//            LOG.info("Group ID     : " + groupId);
//            LOG.info("Group Member : " + msg.getFromGroupMember()+ " | "+ fromGroupMemberNickName);
//            LOG.info("FromUser     : " + fromUserNameID + " | " + fromGroupMemberNickName);
//            LOG.info("ToUser       : " + toUserNameID + " | " + toGroupMemberNickName);
//            LOG.info("SelfName     : " + selfName );
            if (groupNickName.equals("MyTestGroup")) { //开发阶段 测试获取群成员列表
                LOG.info("Member List  : " + array.toJSONString());
            }
            LOG.info("=============================================================");
        } else{
            msgLog.setGroupnickname("");
            msgLog.setFromusername(fromNickName);
            msgLog.setFromUserID(msg.getFromUserName());

            LOG.info("+++++++++++++++++++++++ "+ isSendOrRev+" ++++++++++++++++++++++++++++++++++++++");
            LOG.info("Simple Text msg : " +text);
            LOG.info("FromUser        : "+ fromUserNameID + " | "+ fromNickName);
            LOG.info("ToUser          : "+ toUserNameID + " | " + toNickName);
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }

        LOG.info("======================== MsgLog CSV ====================");
        LOG.info(msgLog.toString());
        List<String[]> list = new ArrayList<String[]>();
        list.add(new String[]{
                msgLog.getMsgid(),
                msgLog.getFromUserID(),
                String.valueOf((msgLog.isIsgroupmsg())?1:0),
                msgLog.getGroupnickname(),
                msgLog.getFromusername(),
                msgLog.getContent(),
                msgLog.getMsgtime(),
                msgLog.getIsSend(),
                msgLog.getMsgtype(),
                msgLog.getToUserID(),
                msgLog.getTousername(),
                msgLog.getLoginuser()
        });
        CSVWriteService csvWriteService = new CSVWriteService();
        String csvfile = ConfigUtil.getValue("csvfile.output.path");
        String[] fileHeader = ConfigUtil.getValue("csv.header").split(",");
        File parentDir = new File(csvfile).getParentFile();
        if(!parentDir.exists()){
            LOG.warn("CSV文件父目录不存在，即将创建 : "+parentDir);
            parentDir.mkdir();
        }
        csvWriteService.write(csvfile,fileHeader,list);
        LOG.info("Write CSV OK ： ");
        LOG.info("=======================================================");
//        LOG.info("======================== MsgLog DB ====================");
//        int result = msgDao.insertMsgLog(msgLog);
//        LOG.info("Insert OK ： "+result);
//        LOG.info("=======================================================");

        return null;
    }


    @Override
    public String picMsgHandle(BaseMsg msg) {
        String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());// 这里使用收到图片的时间作为文件名
        String picPath = basepath+"/pics"+ File.separator + fileName + ".jpg"; // 调用此方法来保存图片
        DownloadTools.getDownloadFn(msg, MsgTypeEnum.PIC.getType(), picPath); // 保存图片的路径
        return "图片保存成功";
    }

    @Override
    public String voiceMsgHandle(BaseMsg msg) {
        String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        String voicePath = basepath+"/voice" + File.separator + fileName + ".mp3";
        DownloadTools.getDownloadFn(msg, MsgTypeEnum.VOICE.getType(), voicePath);
        return "声音保存成功";
    }

    @Override
    public String viedoMsgHandle(BaseMsg msg) {
        String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        String viedoPath = basepath+"/video" + File.separator + fileName + ".mp4";
        DownloadTools.getDownloadFn(msg, MsgTypeEnum.VIEDO.getType(), viedoPath);
        return "视频保存成功";
    }

    @Override
    public String nameCardMsgHandle(BaseMsg msg) {
        return "收到名片消息";
    }

    @Override
    public void sysMsgHandle(BaseMsg msg) { // 收到系统消息
        String text = msg.getContent();
        LOG.info(text);
    }

    @Override
    public String verifyAddFriendMsgHandle(BaseMsg msg) {
        MessageTools.addFriend(msg, true); // 同意好友请求，false为不接受好友请求
        RecommendInfo recommendInfo = msg.getRecommendInfo();
        String nickName = recommendInfo.getNickName();
        String province = recommendInfo.getProvince();
        String city = recommendInfo.getCity();
        String text = "你好，来自" + province + city + "的" + nickName + "， 欢迎添加我为好友！";
        return text;
    }

    @Override
    public String mediaMsgHandle(BaseMsg msg) {
        String fileName = msg.getFileName();
        String filePath = basepath+"/file" + File.separator + fileName; // 这里是需要保存收到的文件路径，文件可以是任何格式如PDF，WORD，EXCEL等。
        DownloadTools.getDownloadFn(msg, MsgTypeEnum.MEDIA.getType(), filePath);
        return "文件" + fileName + "保存成功";
    }

}
