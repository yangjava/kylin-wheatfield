package com.rkylin.wheatfield.service.impl;

import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.util.HttpURLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.TradeRecord;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserTradeSearchRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserTradeSearchResponse;
import com.allinpay.ets.client.PaymentResult;
import com.allinpay.ets.client.SecurityUtil;
import com.allinpay.ets.client.StringUtil;
import com.rkylin.wheatfield.common.ValHasNoParam;
import com.rkylin.wheatfield.constant.Constants;
import com.rkylin.wheatfield.constant.TransCodeConst;
import com.rkylin.wheatfield.manager.TransOrderInfoManager;
import com.rkylin.wheatfield.pojo.TransOrder;
import com.rkylin.wheatfield.pojo.TransOrderInfo;
import com.rkylin.wheatfield.pojo.TransOrderInfoNew;
import com.rkylin.wheatfield.pojo.TransOrderInfoQuery;
import com.rkylin.wheatfield.response.ErrorResponse;
import com.rkylin.wheatfield.response.Response;
import com.rkylin.wheatfield.response.TransOrderResponse;
import com.rkylin.wheatfield.service.AccountManageService;
import com.rkylin.wheatfield.service.IAPIService;
import com.rkylin.wheatfield.service.OrderService;
import com.rkylin.wheatfield.utils.DateUtil;

@Service("orderService")
public class OrderServiceImpl implements OrderService,IAPIService {
	private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired
	TransOrderInfoManager transOrderInfoManager;	
	@Autowired
	AccountManageService accountManageService;	
	@Autowired
	Properties orderProperties;
	@Autowired
	Properties userProperties;
	
	
	@Override
	public int getOrderNum(String userId, String funcCode) {
		DateUtil dateUtil=new DateUtil();
		int resultNum=0;//订单条数返回值
		TransOrderInfoQuery query=new TransOrderInfoQuery();
		query.setUserId(userId);
		query.setFuncCode(funcCode);
		try {
			query.setStartTime(dateUtil.parse(dateUtil.getDate()+" 00:00:00", "yyyy-MM-dd hh:mm:ss"));
			query.setEndTime(dateUtil.parse(dateUtil.getDate()+" 23:59:59", "yyyy-MM-dd hh:mm:ss"));
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		List<TransOrderInfo> transOrderInfos=transOrderInfoManager.queryList(query);
		
		if(transOrderInfos!=null){
			resultNum=transOrderInfos.size();
		}else{
			resultNum=-1;
		}
		return resultNum;
	}

	@Override
	public Response doJob(Map<String, String[]> paramMap, String methodName) {
		String reCode = "P0";
		String reMsg = "成功";
		ErrorResponse response=new ErrorResponse();
		//判断非空项
		if("ruixue.wheatfield.ordernum.query".equals(methodName)){
			if(!ValHasNoParam.hasParam(paramMap, "userid")){			
				reCode = "P1";
				reMsg = "userid不能为空";			
			}else if(!ValHasNoParam.hasParam(paramMap, "funccode")){
				reCode = "P1";
				reMsg = "usertype不能为空";
			}
		}else if("ruixue.wheatfield.order.query".equals(methodName)){
			      if (!ValHasNoParam.hasParam(paramMap, "merchantcode")) {
					reCode="P1";
					reMsg="merchantCode不能为空";
				}
		}
		//判断是否有非空信息
		if(!reCode.equals("P0")){
			response.setCode(reCode);
			response.setMsg(reMsg);
			return response;
		}
		String userId=null;//用户ID
		String funcCode=null;//交易类型编码
		String orderNo=null;//订单号
		String merchantCode=null;//商品号/机构号
		String startTime=null;//查询开始时间
		String endTime=null;//查询结束时间
		String status=null;
		
		for(Object keyObj : paramMap.keySet().toArray()){
			String[] strs = paramMap.get(keyObj);
			for(String value : strs){
				if(keyObj.equals("userid")){
					userId=value;					
				}else if(keyObj.equals("funccode")){
					funcCode=value;
				}else if (keyObj.equals("orderno")) {
					orderNo=value;
				}else if (keyObj.equals("merchantcode")) {
					merchantCode=value;
				}else if (keyObj.equals("starttime")) {
					startTime=value;
				}else if (keyObj.equals("endtime")) {
					endTime=value;
				}else if (keyObj.equals("status")) {
					status=value;
				}
			}
		}
		if("ruixue.wheatfield.ordernum.query".equals(methodName)){//获取账户订单条数
			int orderNum=this.getOrderNum(userId, funcCode);
			if(orderNum>=0){
				//response.setOrdernumber(orderNum);
				response.setIs_success(true);
			}else{				
				response.setCode("P1");
				response.setMsg("获取用户订单条数失败");
			}
		}else if("ruixue.wheatfield.order.query".equals(methodName)){//获取订单信息
			List<TransOrder> transOrders=this.getTransOrders(orderNo, merchantCode, startTime, endTime);
			
			if(transOrders != null){
				TransOrderResponse transOrderResponse =new TransOrderResponse();
				transOrderResponse .setTransorder(transOrders);
				return transOrderResponse ;
			}else{				
				response.setCode("P1");
				response.setMsg("获取用户订单信息失败");
			}
		}else if("ruixue.wheatfield.ordern.query".equals(methodName)){//获取订单信息---新接口
			if (!ValHasNoParam.hasParam(paramMap, "merchantcode")) {
				reCode="P1";
				reMsg="merchantCode不能为空";
			}
			List<TransOrderInfoNew> transOrders=this.getTransOrdersNew(userId, orderNo, merchantCode, startTime, endTime, funcCode, status);
			if(transOrders != null && transOrders.size() > 0){
				TransOrderResponse transOrderResponse =new TransOrderResponse();
				transOrderResponse.setTransOrderInfoNews(transOrders);
				return transOrderResponse ;
			}else{				
				response.setCode("P1");
				response.setMsg("获取订单信息失败");
			}
		}
		return response;
	}

	@Override
	public List<TransOrder> getTransOrders(String orderNo,String merchantCode, String startTime, String endTime) {
		logger.info("###################交易订单查询开始###############################");
		logger.info("交易查询入参相关信息：orderNo="+orderNo+";merchantCode="+merchantCode+",startTime="+startTime+";endTime="+endTime);
		DateUtil dateUtil=new DateUtil();
		try{
			TransOrderInfoQuery query=new TransOrderInfoQuery();
			query.setOrderNo(orderNo);
			query.setMerchantCode(merchantCode);
			if(!"".equals(startTime)&&null!=startTime&&!"".equals(endTime)&&null!=endTime){
				try {
					query.setStartTime(dateUtil.parse(startTime,"yyyy-MM-dd HH:mm:ss"));
					query.setEndTime(dateUtil.parse(endTime,"yyyy-MM-dd HH:mm:ss"));
				} catch (ParseException e) {
					query.setStartTime(dateUtil.parse(startTime+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
					query.setEndTime(dateUtil.parse(endTime+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
				}
			}				
			List<TransOrderInfo> transOrderList=transOrderInfoManager.queryList(query);
			List<TransOrder> transOrders= new ArrayList<TransOrder>();
			for (TransOrderInfo transOrderInfo: transOrderList) {
				TransOrder order=new TransOrder();
				order.setOrderNo(transOrderInfo.getOrderNo());
				order.setAmount(transOrderInfo.getAmount());
				order.setOrderStatus(transOrderInfo.getStatus());
				
				order.setFuncCode(transOrderInfo.getFuncCode());
				order.setOrderPackageNo(transOrderInfo.getOrderPackageNo());
				order.setRequestNo(transOrderInfo.getRequestNo());
				order.setCreatedTime(transOrderInfo.getCreatedTime());
				transOrders.add(order);
			}
			logger.info("###################交易订单查询结束###############################");
		    return transOrders;					
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		logger.info("###################交易订单查询失败###############################");
		return null;
		
	}

	@Override
	public List<TransOrderInfoNew> getTransOrdersNew(String userId,String orderNo,String merchantCode, String startTime, String endTime,String funcCode, String status) {
		logger.info("###################交易订单查询开始###############################");
		logger.info("交易查询入参相关信息：userId="+userId+";orderNo="+orderNo+";merchantCode="+merchantCode+",startTime="+startTime+";endTime="+endTime+";funcCode="+funcCode+";status="+status);
		DateUtil dateUtil=new DateUtil();
		try{
			TransOrderInfoQuery query=new TransOrderInfoQuery();
			query.setOrderNo(orderNo);
			query.setMerchantCode(merchantCode);
			if(null != startTime && !"".equals(startTime) && null!=endTime && !"".equals(endTime)){
				try {
					query.setStartTime(dateUtil.parse(startTime,"yyyy-MM-dd HH:mm:ss"));
					query.setEndTime(dateUtil.parse(endTime,"yyyy-MM-dd HH:mm:ss"));
				} catch (ParseException e) {
					query.setStartTime(dateUtil.parse(startTime+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
					query.setEndTime(dateUtil.parse(endTime+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
				}
			}
			query.setUserId(userId);
			query.setFuncCode(funcCode);
			if(null!=status&&!"".equals(status)){
				query.setStatus(Integer.parseInt(status));
			}
			List<TransOrderInfo> transOrderList=transOrderInfoManager.selectTransOrdersAndSumId(query);
			List<TransOrderInfoNew> transOrders= new ArrayList<TransOrderInfoNew>();
			for (TransOrderInfo transOrderInfo: transOrderList) {
				TransOrderInfoNew order=new TransOrderInfoNew();
				order.setOrderNo(transOrderInfo.getOrderNo());
				order.setAmount(transOrderInfo.getAmount());
				order.setOrderStatus(transOrderInfo.getStatus());				
				order.setFuncCode(transOrderInfo.getFuncCode());
				order.setOrderPackageNo(transOrderInfo.getOrderPackageNo());
				order.setRequestNo(transOrderInfo.getRequestNo());
				order.setCreatedTime(transOrderInfo.getCreatedTime());
				order.setUpdatedTime(transOrderInfo.getUpdatedTime());
				order.setTranssumid(transOrderInfo.getTranssumid());
				if(null==transOrderInfo.getErrorCode()){
					order.setErrorMsg("");
				}else{
					order.setErrorMsg(transOrderInfo.getErrorCode());
				}
				order.setMerchantCode(transOrderInfo.getMerchantCode());
				transOrders.add(order);
			}
			logger.info("###################交易订单查询结束###############################");
		    return transOrders;					
		}catch(Exception e){
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
		logger.info("###################交易订单查询失败###############################");
		return null;
	} 
	
	
	
	@Override
	public boolean validataOrder(TransOrderInfo transOrderInfo) {
		// TODO 验证订单有效性
		boolean isOK=false;
		//通联支付类型
		if(null==transOrderInfo.getPayChannelId()||"".equals(transOrderInfo.getPayChannelId())||TransCodeConst.PAY_TL.equals(transOrderInfo.getPayChannelId())){
			logger.info("*******************该笔交易为通联支付类型*******************");
			isOK=validataTL(transOrderInfo);
		}else if(TransCodeConst.PAY_ZFB.equals(transOrderInfo.getPayChannelId())){
			logger.info("*******************该笔交易为支付宝支付类型*******************");
			isOK=validataZFB(transOrderInfo);
		}else if(TransCodeConst.PAY_WX.equals(transOrderInfo.getPayChannelId())){
			logger.info("*******************该笔交易为微信支付类型*******************");
			//isOK=validataZFB(transOrderInfo);
		}
		return isOK;
	}

	/**
	 * 通联校验充值订单状态
	 * @param transOrderInfo
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private boolean validataTL(TransOrderInfo transOrderInfo){
		boolean isOk=false;
		DateUtil dateUtil=new DateUtil();
		String serverUrl=null;
		String key=null;
		String merchantId=null;
		String version=null;
		String signType=null;
		String certPath=null;
		if(Constants.HT_ID.equals(transOrderInfo.getMerchantCode())){//会堂充值订单校验
			serverUrl=orderProperties.getProperty("HT_SERVERURL");
			key=orderProperties.getProperty("HT_KEY");
			merchantId=orderProperties.getProperty("HT_MERCHANTID");
			version=orderProperties.getProperty("HT_VERSION");
			signType=orderProperties.getProperty("HT_SIGNTYPE");
			certPath=orderProperties.getProperty("HT_CERTPATH");
		}else if(Constants.JRD_ID.equals(transOrderInfo.getMerchantCode())){//君融贷充值订单校验
			serverUrl=orderProperties.getProperty("JRD_SERVERURL");
			key=orderProperties.getProperty("JRD_KEY");
			//根据支付类型判断移动支付还是收银台支付
			if(null!=transOrderInfo.getBusiTypeId()){
				if(transOrderInfo.getBusiTypeId().equals(TransCodeConst.PAY_TYPE_B1)){
					merchantId=orderProperties.getProperty("JRD_MERCHANTID");
					logger.info("支付类型为网关支付："+transOrderInfo.getBusiTypeId());
				}else if(transOrderInfo.getBusiTypeId().equals(TransCodeConst.PAY_TYPE_B11)){
					merchantId=orderProperties.getProperty("JRD_MERCHANTID_YD");
					logger.info("支付类型为移动支付："+transOrderInfo.getBusiTypeId());
				}else{
					merchantId=orderProperties.getProperty("JRD_MERCHANTID");
					logger.info("支付类型为网关支付："+transOrderInfo.getBusiTypeId());
				}
			}else{
				merchantId=orderProperties.getProperty("JRD_MERCHANTID");
				logger.info("支付类型为网关支付："+transOrderInfo.getBusiTypeId());
			}
			
			version=orderProperties.getProperty("JRD_VERSION");
			signType=orderProperties.getProperty("JRD_SIGNTYPE");
			certPath=orderProperties.getProperty("JRD_CERTPATH");
		}else if(Constants.MZ_ID.equals(transOrderInfo.getMerchantCode())){//棉庄机构走融数协议
			serverUrl=orderProperties.getProperty("RS_SERVERURL");
			key=orderProperties.getProperty("RS_KEY");
			merchantId=orderProperties.getProperty("RS_MERCHANTID");
			version=orderProperties.getProperty("RS_VERSION");
			signType=orderProperties.getProperty("RS_SIGNTYPE");
			certPath=orderProperties.getProperty("RS_CERTPATH");
		}else{//丰年充值订单校验
			serverUrl=orderProperties.getProperty("FN_SERVERURL");
			key=orderProperties.getProperty("FN_KEY");
			merchantId=orderProperties.getProperty("FN_MERCHANTID");
			version=orderProperties.getProperty("FN_VERSION");
			signType=orderProperties.getProperty("FN_SIGNTYPE");
			certPath=orderProperties.getProperty("FN_CERTPATH");
		}
		logger.info("通联证书相关信息：serverUrl="+serverUrl+",key="+key+",merchantId="+merchantId+",version="+version+",signType="+signType+",certPath="+certPath);
		String orderNo=transOrderInfo.getRequestNo();
		String orderDatetime=dateUtil.getDateTime(transOrderInfo.getRequestTime(), "yyyyMMddHHmmss") ;
		String queryDatetime=dateUtil.getDateTime("yyyyMMddHHmmss");
		StringBuffer bufSignSrc=new StringBuffer();
		StringUtil.appendSignPara(bufSignSrc, "merchantId",merchantId);
		StringUtil.appendSignPara(bufSignSrc, "version", version);
		StringUtil.appendSignPara(bufSignSrc, "signType", signType);
		StringUtil.appendSignPara(bufSignSrc, "orderNo", orderNo);
		StringUtil.appendSignPara(bufSignSrc, "orderDatetime",orderDatetime);
		StringUtil.appendSignPara(bufSignSrc, "queryDatetime",queryDatetime);
		StringUtil.appendLastSignPara(bufSignSrc, "key", key);
		logger.info("签名字符串："+bufSignSrc.toString());
		String signMsg=SecurityUtil.MD5Encode(bufSignSrc.toString());
	
		// 提交查询请求
		Map<String, String> result = new HashMap<String, String>();
		try{
			String listenUrl=serverUrl;
			HttpClient httpclient=new HttpClient();
			PostMethod postmethod=new PostMethod(listenUrl);
			NameValuePair[] date = { new NameValuePair("merchantId",merchantId),
					new NameValuePair("version",version),
					new NameValuePair("signType",signType),
					new NameValuePair("orderNo",orderNo),
					new NameValuePair("orderDatetime",orderDatetime),
					new NameValuePair("queryDatetime",queryDatetime),
					new NameValuePair("signMsg",signMsg)};
			postmethod.setRequestBody(date);
			int responseCode=httpclient.executeMethod(postmethod);
			System.out.println("responseCode="+responseCode);
			
			if(responseCode == HttpURLConnection.HTTP_OK){
				String strResponse = postmethod.getResponseBodyAsString();
				
				// 解析查询返回结果
				strResponse = URLDecoder.decode(strResponse);
				String[] parameters = strResponse.split("&");
				for (int i = 0; i < parameters.length; i++) {
					String msg = parameters[i];
					int index = msg.indexOf('=');
					if (index > 0) {
						String name = msg.substring(0, index);
						String value = msg.substring(index + 1);
						result.put(name, value);
					}
				}
		
				// 查询结果会以Server方式通知商户(同支付返回)；
				// 若无法取得Server通知结果，可以通过解析查询返回结果，更新订单状态(参考如下).
				if (null != result.get("ERRORCODE")) {
					// 未查询到订单
					logger.error("ERRORCODE=" + result.get("ERRORCODE"));
					logger.error("ERRORMSG=" + result.get("ERRORMSG"));
		
				} else {
					// 查询到订单
					String payResult = result.get("payResult");
					if (payResult.equals("1")) {
						logger.info("订单付款成功！");
		
						// 支付成功，验证签名
						PaymentResult paymentResult = new PaymentResult();
						paymentResult.setMerchantId(result.get("merchantId"));
						paymentResult.setVersion(result.get("version"));
						paymentResult.setLanguage(result.get("language"));
						paymentResult.setSignType(result.get("signType"));
						paymentResult.setPayType(result.get("payType"));
						paymentResult.setIssuerId(result.get("issuerId"));
						paymentResult.setPaymentOrderId(result
								.get("paymentOrderId"));
						paymentResult.setOrderNo(result.get("orderNo"));
						paymentResult.setOrderDatetime(result
								.get("orderDatetime"));
						paymentResult.setOrderAmount(result.get("orderAmount"));
						paymentResult.setPayAmount(result.get("payAmount"));
						paymentResult.setPayDatetime(result.get("payDatetime"));
						paymentResult.setExt1(result.get("ext1"));
						paymentResult.setExt2(result.get("ext2"));
						paymentResult.setPayResult(result.get("payResult"));
						paymentResult.setErrorCode(result.get("errorCode"));
						paymentResult.setReturnDatetime(result.get("returnDatetime"));
						paymentResult.setKey(key);
						paymentResult.setSignMsg(result.get("signMsg"));
						paymentResult.setCertPath(certPath);		
						boolean verifyResult = paymentResult.verify();		
						if (verifyResult) {
							logger.info("订单支付成功，验签成功");
							//判断金额是否正确
							if(String.valueOf(transOrderInfo.getAmount()).equals(paymentResult.getPayAmount())){
								isOk = true;
							}else{
								logger.info("通联充值金额与该笔订单金额不符，请确认！");
							}
						} else {
							logger.error("订单支付成功，验签失败！");
						}
		
					} else {
						logger.error("订单尚未付款！");
					}
				}
		
			}
		}catch(Exception e){
		 	e.printStackTrace();
		}
		return isOk;
	}

	/**
	 * 支付宝校验充值订单状态
	 * @param transOrderInfo
	 * @return
	 */
	private boolean validataZFB(TransOrderInfo transOrderInfo){
		boolean isOK=false;
		DateUtil dateUtil=new DateUtil();
		logger.info("*****************调用 支付宝方法查询交易信息**************");
		//支付宝响应对象
		AlipayUserTradeSearchResponse res = null;
		//当前页
		int currentPage=1;
		//返回信息结构体
		List<TradeRecord> tradeRecordList = null;
		//支付宝查询交易信息请求对象
		AlipayUserTradeSearchRequest req = new AlipayUserTradeSearchRequest();
		//交易开始日期
		req.setStartTime(dateUtil.getDate()+" 00:00:00");
		//交易结束日期
		req.setEndTime(dateUtil.getDateTime(Constants.DATE_FORMAT_YYYYMMDDHHMMSS));
		//查询页数
		req.setPageNo("1");
		//每页记录条数
		req.setPageSize("10");
		//商户订单号
		req.setMerchantOrderNo(transOrderInfo.getRequestNo());
		//密钥 ... ...
		String serverUrl = userProperties.getProperty("ALIPAY_SERVER_URL");
		String appID = userProperties.getProperty("ALIPAY_APP_ID");
 		String privateKey = userProperties.getProperty("ALIPAY_PRIVATE_KEY");
		String format = "json";
		// 初始化应用
		AlipayClient client = new DefaultAlipayClient(serverUrl,appID, privateKey, format);
		//获取令牌
		String token = getMyAuthToken(client);
		System.out.println("authTokenauthTokenauthTokenauthToken:::::  " + token);
		//调用支付宝接口, 查询支付宝账户交易记录
		do {
			try {
				res = client.execute(req, token);
			} catch (AlipayApiException e) {
				logger.error(e.getMessage());
				System.out.println(e.getMessage());
			}
			if(tradeRecordList == null) {
				tradeRecordList = res.getTradeRecords();
			} else {
				tradeRecordList.addAll(res.getTradeRecords());
			}
		} while(res.getTotalPages() != null && Integer.parseInt(res.getTotalPages()) > currentPage ++);
		//判断返回数据的有效性
		if(null!=tradeRecordList&&tradeRecordList.size()==1){
			//计算金额
			Long payAmount=0l;
			try {
				payAmount=Long.parseLong(changeY2F(tradeRecordList.get(0).getTotalAmount()));
			} catch (Exception e) {
				logger.error("支付宝充值校验信息失败:"+e.getMessage());
				return false;
			}
			if(payAmount==transOrderInfo.getAmount()){
				logger.info("支付宝充值校验信息成功");
				isOK=true;
			}else{
				logger.info("支付宝充值校验信息成功，请确认支付宝返回的订单金额为:" + payAmount + "; "
						+ "提交的交易订单金额为：" + transOrderInfo.getAmount());
			}
		}else{
			logger.info("支付宝充值校验信息失败，请确认code:" + res.getSubCode() + "; msg:" + res.getSubMsg());
		}
		return isOK;
	}
	//获取支付宝tokey
    private	String getMyAuthToken(AlipayClient client){
		AlipaySystemOauthTokenRequest req = new AlipaySystemOauthTokenRequest();
		// 根据授权码取授权令牌
		req.setGrantType("authorization_code");
		// 授权码，用户对应用授权后得到。
		req.setCode(userProperties.getProperty("ALIPAY_APP_CODE"));
		
		AlipaySystemOauthTokenResponse response = null;
		try {
			response = client.execute(req);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		String token ="";
		if(response!=null){
			token = response.getAccessToken();
		}
		return token;
	}
    /**
     * 金额元转成分
     * @param amount
     * @return
     */
    private String changeY2F(String amount){    
        String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额    
        int index = currency.indexOf(".");    
        int length = currency.length();    
        Long amLong = 0l;    
        if(index == -1){    
            amLong = Long.valueOf(currency+"00");    
        }else if(length - index >= 3){    
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));    
        }else if(length - index == 2){    
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);    
        }else{    
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");    
        }    
        return amLong.toString();    
    }
	
}
