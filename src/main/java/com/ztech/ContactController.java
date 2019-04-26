package com.ztech;

import cn.zhouyafeng.itchat4j.api.WechatTools;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.thread.CheckLoginStatusThread;
import cn.zhouyafeng.itchat4j.utils.SleepUtils;
import cn.zhouyafeng.itchat4j.utils.tools.CommonTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 登陆控制器
 * 
 * @author https://github.com/yaphone
 * @date 创建时间：2017年5月13日 下午12:56:07
 * @version 1.0
 *
 */
public class ContactController {
	private static Logger LOG = LoggerFactory.getLogger(ContactController.class);
	private ContactUtil contactUtil = new ContactUtil();
	private static Core core = Core.getInstance();

	public void login(String qrPath) {
		if (core.isAlive()) { // 已登陆
			LOG.info("itchat4j已登陆");
			return;
		}
		while (true) {
			for (int count = 0; count < 10; count++) {
				LOG.info("获取UUID");
				while (contactUtil.getUuid() == null) {
					LOG.info("1. 获取微信UUID");
					while (contactUtil.getUuid() == null) {
						LOG.warn("1.1. 获取微信UUID失败，两秒后重新获取");
						SleepUtils.sleep(2000);
					}
				}
				LOG.info("2. 获取登陆二维码图片");
				if (contactUtil.getQR(qrPath)) {
					break;
				} else if (count == 10) {
					LOG.error("2.2. 获取登陆二维码图片失败，系统退出");
					System.exit(0);
				}
			}
			LOG.info("3. 请扫描二维码图片，并在手机上确认");
			if (!core.isAlive()) {
				contactUtil.login();
				core.setAlive(true);
				LOG.info(("登陆成功"));
				break;
			}
			LOG.info("4. 登陆超时，请重新扫描二维码图片");
		}

		LOG.info("5. 登陆成功，微信初始化");
		if (!contactUtil.webWxInit()) {
			LOG.info("6. 微信初始化异常");
			System.exit(0);
		}

//		LOG.info("6. 开启微信状态通知");
//		contactUtil.wxStatusNotify();

		LOG.info("7. 清除。。。。");
		CommonTools.clearScreen();
		LOG.info(String.format("欢迎回来， %s", core.getNickName()));

		LOG.info("8. 获取联系人信息");
		contactUtil.webWxGetContact();

		LOG.info("9. 获取群好友及群好友列表");
//		contactUtil.WebWxBatchGetContact();
		List<String> targetlist = new ArrayList<>();
		for(int i=0;i<3;i++) {
			targetlist.add(core.getGroupIdList().get(i));
			LOG.info("targetList: "+i+ " "+targetlist.get(i));
		}

		contactUtil.updateGroupContact(targetlist);



//		LOG.info("10. 开始接收消息");
//		contactUtil.startReceiving();
//
//		LOG.info("11. 缓存本次登陆好友相关消息");
//		WechatTools.setUserInfo(); // 登陆成功后缓存本次登陆好友相关消息（NickName, UserName）
//
//		LOG.info("12.开启微信状态检测线程");
//		new Thread(new CheckLoginStatusThread()).start();
	}

	public static void main(String[] args) {

		String qrPath = "/Users/zhy/IdeaProjects/WechatAssistant/loginQR";
		ContactController contactController = new ContactController();
		contactController.login(qrPath);
	}

}