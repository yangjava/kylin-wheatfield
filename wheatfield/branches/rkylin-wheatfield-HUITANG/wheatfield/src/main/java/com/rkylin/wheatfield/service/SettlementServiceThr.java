package com.rkylin.wheatfield.service;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;


public interface SettlementServiceThr {
	/**
	 * 查询费率模版
	 * @return 状态代码 状态信息
	 * */
	@Transactional
	 Map<String,String> getCreditInfo(String type,String accountDate,String batch);
	
	/**
	 * 代收付汇总
	 * 参数:
	 *   generationType:代收付类型  代收，提现等
	 *   merchantId:商户ID 会堂，丰年等
	 *   orderType:订单类型  代收，代付等
	 *   bussinessCoded：To通联业务代码
	 *   dateType:汇总日期类型  例：0：当日数据汇总 1：t+1日数据汇总 2：t+2日数据汇总 ...
	 * */
	@Transactional
	 void paymentGeneration(String generationType,String merchantId,int orderType,
			        		String bussinessCoded,int dateType);
	
	/**
	 * 通过代收（课栈向学生）  发起代付（课栈向JRD）
	 * 参数：
	 * @return 状态代码 状态信息
	 * */ 
	@Transactional
	 Map<String,String> withholdToP2P();
	
	/**
	 * 把代收付结果更新订单系统
	 * @return 状态代码 状态信息
	 * */
	@Transactional
	Map<String,String> updateOrder();
	
	/**
	 * 把代收付结果更新
	 * @return 状态代码 状态信息
	 * */
	@Transactional
	Map updateCreditAccountSec();
	/**
	 * 上传债券包文件至P2P
	 * @return 状态代码 状态信息
	 * */
	@Transactional
	Map<String, String> uploadDeductFile(String rootInstId, String providerId);
}
	
