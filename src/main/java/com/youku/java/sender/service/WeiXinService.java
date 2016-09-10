package com.youku.java.sender.service;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class WeiXinService {

	Logger logger = Logger.getLogger(WeiXinService.class);
	public final String APP_ID = "wxc602c335c2a66534";
	public final String APP_SECRET = "eb91fb42712fc7f553e3c475218ccd04";
	public String accessToken ;
	public long lastRequireAccessToken = 0 ;
	
	public final String filePath = System.getProperty("user.home") + "/weixintoken.txt";
	
	DefaultHttpClient httpClient = new DefaultHttpClient();
	ResponseHandler<String> responseHandler = new BasicResponseHandler();
	
	public void checkAccessToken(){
		if(lastRequireAccessToken == 0 ){
			try {
				File file = new File(filePath);
				if(file.exists()){
					String str = FileUtils.readFileToString(file);
					JSONObject jsonObject = new JSONObject(str);
					lastRequireAccessToken = jsonObject.getLong("time");
					accessToken= jsonObject.optString("access_token");
					logger.info("success load access token from user home : " + filePath + ",content:" + jsonObject.toString());
				}
			} catch (Exception e) {
				logger.warn("readFileToString", e);
			}
		}
		if(System.currentTimeMillis() - lastRequireAccessToken >= 1000 * 60 * 60 * 2){
			requireAccessToken();
		}
	}
	public void requireAccessToken(){
		if(1==1)
		throw new RuntimeException("!!!!!!!!requireAccessToken!!!!!!!!!!");
		HttpGet get = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APP_ID+"&secret="+APP_SECRET);
		try {
			String response = httpClient.execute(get, responseHandler);
			logger.info("requireAccessToken:"+response);
			JSONObject obj= new JSONObject(response);
			accessToken = obj.getString("access_token");
			lastRequireAccessToken = System.currentTimeMillis();
			obj.put("time", lastRequireAccessToken);
			FileUtils.write(new File(filePath), obj.toString());
		} catch (Exception e) {
			logger.warn("requireAccessToken", e);
		}
	}
	
	public void uploadAccessToken(){
		if(1==1)
			throw new RuntimeException("!!!!!!!!requireAccessToken!!!!!!!!!!");
		HttpGet get = new HttpGet("http://www.myroute.cn/h5me/setAccessToken.php?token="+accessToken);
		try {
			String response = httpClient.execute(get, responseHandler);
			logger.info("uploadAccessToken:"+response);
		} catch (Exception e) {
			logger.warn("uploadAccessToken", e);
		}
	}
	
	public boolean sendMessage(String message) throws Exception {
		checkAccessToken();
		HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token="+ accessToken);
		String msg = getMsgByOpenIDs(message);
		msg = new String(msg.getBytes("UTF-8"), "ISO8859_1");
		StringEntity se = new StringEntity(msg);
		httpPost.setEntity(se);
		String response  = httpClient.execute(httpPost, responseHandler);
		logger.info("messageResponse:"+response);
		JSONObject obj= new JSONObject(response);
		//String msgId = obj.optString("msg_id");
		return obj.getInt("errcode")== 0 ;
	}
	
	
	public String getMsgByOpenIDs(String content) throws Exception {
		JSONObject obj = new JSONObject();
		JSONArray touser =  new JSONArray();
		touser.put("o2pN3xIbIhkqUb4PzkcN2mzSarA4");//ME
		touser.put("o2pN3xIHWApFjM325xgpN7lAAg2M");//CHANGCHANG

		obj.put("touser", touser);
		obj.put("msgtype", "text");
		obj.put("text", new JSONObject().put("content", content));
		return obj.toString();
	}	
	
	public boolean sendTemplateMsg() throws Exception{
		checkAccessToken();
		HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+ accessToken);
		String msg = getTemplateMsg("");
		msg = new String(msg.getBytes("UTF-8"), "ISO8859_1");
		StringEntity se = new StringEntity(msg);
		httpPost.setEntity(se);
		String response  = httpClient.execute(httpPost, responseHandler);
		logger.info("messageResponse:"+response);
		JSONObject obj= new JSONObject(response);
		//String msgId = obj.optString("msg_id");
		return obj.getInt("errcode")== 0 ;
	}
	
	public String getTemplateMsg(String content) throws Exception {
		JSONObject obj = new JSONObject();
		JSONArray touser =  new JSONArray();
		touser.put("o2pN3xIbIhkqUb4PzkcN2mzSarA4");//ME
		touser.put("o2pN3xIHWApFjM325xgpN7lAAg2M");//CHANGCHANG

		//obj.put("touser", touser);
		obj.put("touser", "o2pN3xIHWApFjM325xgpN7lAAg2M");
		obj.put("template_id", "6uCRepajQ14NpVEqQgMl0LwRae9qAQR2LB4-fDme80Y");
		obj.put("url", "http://youku.com");
		
		JSONObject data = new JSONObject();
		data.put("serviceName", new JSONObject().put("value", "中文标题name").put("color", "#173177"));
		//data.put("desc", new JSONObject().put("value", "耗时或错误率连续达到服务预警阈值1分钟，发生预警!").put("color", "#173177"));
		data.put("logTime", new JSONObject().put("value", "2016090870").put("color", "#173177"));
		data.put("cost", new JSONObject().put("value", "10.30").put("color", "#173177"));
		data.put("costThreshold", new JSONObject().put("value", "10").put("color", "#173177"));
		data.put("errorRate", new JSONObject().put("value", "0.00").put("color", "#173177"));
		data.put("errorNum", new JSONObject().put("value", "0").put("color", "#173177"));
		data.put("totalNum", new JSONObject().put("value", "2000").put("color", "#173177"));
		data.put("errorThreshold", new JSONObject().put("value", "5").put("color", "#173177"));
		data.put("nowTime", new JSONObject().put("value", "2016-09-09 22:27:33").put("color", "#173177"));
		//data.put("nowState", new JSONObject().put("value", "今日[5次，10分钟]\n本周[5次，10分钟]\n本周[5次，10分钟]\n年度[5次，10分钟]").put("color", "#173177"));
	//	data.put("state", new JSONObject().put("value", "[开始时间:Sep 9, 2016 10:27:33 PM, 持续:15分钟, 当前告警状态:true]").put("color", "#173177"));
		data.put("state", new JSONObject().put("value", "本次[10分钟]\n今日[5次，10分钟]\n本周[5次，10分钟]\n本周[5次，10分钟]\n年度[5次，10分钟]").put("color", "#173177"));
		
		obj.put("data", data);
		System.out.println(obj.toString());
		return obj.toString();
	}	
	
	public static void main(String[] args) {
		WeiXinService service = new WeiXinService();
		//service.checkAccessToken();
		try {
			//boolean stat = service.sendMessage("filedb预警，请及时处理");
			boolean stat = service.sendTemplateMsg();
			System.out.println(stat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
