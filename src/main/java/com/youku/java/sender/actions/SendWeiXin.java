package com.youku.java.sender.actions;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.youku.java.navi.api.AbstractNaviAction;
import com.youku.java.navi.api.NaviHttpRequest;
import com.youku.java.navi.api.NaviHttpResponse;
import com.youku.java.sender.service.WeiXinService;

public class SendWeiXin extends AbstractNaviAction {
	WeiXinService weixinService ;
	private static final Logger logger = Logger.getLogger(SendWeiXin.class);

	@Override
	public void doAction(NaviHttpRequest request, NaviHttpResponse response)
			throws Exception {
		JSONObject result = new JSONObject();
		JSONObject params = request.getParams();
		logger.info("params:" + params);
		weixinService.sendMessage(params.optString("content"));
		response.setJsonData(result);
	}

	public WeiXinService getWeixinService() {
		return weixinService;
	}

	public void setWeixinService(WeiXinService weixinService) {
		this.weixinService = weixinService;
	}
	
	
}
