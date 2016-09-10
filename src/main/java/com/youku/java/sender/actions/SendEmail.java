package com.youku.java.sender.actions;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.youku.java.navi.api.AbstractNaviAction;
import com.youku.java.navi.api.NaviHttpRequest;
import com.youku.java.navi.api.NaviHttpResponse;

public class SendEmail extends AbstractNaviAction {
	private static final Logger logger = Logger.getLogger(SendEmail.class);

	@Override
	public void doAction(NaviHttpRequest request, NaviHttpResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		JSONObject params = request.getParams();
		logger.info("中国测试");
		logger.info("params:" + params);
		sendEmail(params);
		response.setJsonData(result);
	}

	boolean sendEmail(JSONObject params) {
		String smtp = "smtp.163.com";
		String from = "qq603275863@163.com";
		String username = "qq603275863";
		String password = "qq603275863";
		
		String copyto = from;

		String to = params.optString("to");

		String title = params.optString("title");
		String value = params.optString("value");
	//	title="猪哥 [cat第三方告警哥";
	//	value="ccc";
		try {
		//	title = javax.mail.internet.MimeUtility.encodeText(title);
		//	value = javax.mail.internet.MimeUtility.encodeText(value,"UTF-8","B");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	//	subject="subject";
	//	content="content";

		// String filename = "附件路径，如：F:\\笔记<a>\\struts2</a>与mvc.txt";
		// Mail.sendAndCc(smtp, from, to, copyto, subject, content, username,
		// password, filename);
		boolean ok = Mail.sendAndCc(smtp, from, to, copyto, title, value,
				username, password, null);
		logger.info(ok);

		return false;
	}

}
