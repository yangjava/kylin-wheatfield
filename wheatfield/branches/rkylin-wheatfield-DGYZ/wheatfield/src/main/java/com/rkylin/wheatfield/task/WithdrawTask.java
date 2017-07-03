package com.rkylin.wheatfield.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rkylin.common.RedisIdGenerator;
import com.rkylin.utils.RkylinMailUtil;
import com.rkylin.wheatfield.common.DateUtils;
import com.rkylin.wheatfield.constant.Constants;
import com.rkylin.wheatfield.constant.SettleConstants;
import com.rkylin.wheatfield.constant.TransCodeConst;
import com.rkylin.wheatfield.exception.AccountException;
import com.rkylin.wheatfield.manager.AccountInfoManager;
import com.rkylin.wheatfield.manager.TransDaysSummaryManager;
import com.rkylin.wheatfield.manager.TransOrderInfoManager;
import com.rkylin.wheatfield.service.GenerationPaymentService;
import com.rkylin.wheatfield.service.OperationServive;
import com.rkylin.wheatfield.service.PaymentAccountService;
import com.rkylin.wheatfield.service.SettlementServiceThr;
import com.rkylin.wheatfield.settlement.SettlementLogic;
import com.rkylin.wheatfield.utils.DateUtil;
@Transactional
public class WithdrawTask {

	@Autowired
	TransOrderInfoManager transOrderInfoManager;
	@Autowired
	GenerationPaymentService generationPaymentService;
	@Autowired
	PaymentAccountService paymentAccountService;
	@Autowired
	OperationServive operationServive;
	@Autowired
	TransDaysSummaryManager transDaysSummaryManager;
	@Autowired
	RedisIdGenerator redisIdGenerator;
	@Autowired
	AccountInfoManager accountInfoManager;
	@Autowired
	SettlementServiceThr settlementServiceThr;
	@Autowired
	SettlementLogic settlementLogic;
	
	DateUtil dateUtil=new DateUtil();
	/** 日志对象 */
	private static final Logger logger = LoggerFactory.getLogger(WithdrawTask.class);
	
	 /**
     * 通信运维提现
     */ 
    @Transactional(rollbackFor = AccountException.class, propagation = Propagation.NESTED)
    public void withdrawTXYW(){
        settlementServiceThr.paymentGeneration(
                TransCodeConst.WITHDROW, Constants.TXYW_ID,
                SettleConstants.ORDER_WITHDRAW,
                SettleConstants.WITHHOLD_CODE,1);
    }
	
	
	 /**
     * 客栈提现
     */ 
    @Transactional(rollbackFor = AccountException.class, propagation = Propagation.NESTED)
    public void withdrawKZ(){
        settlementServiceThr.paymentGeneration(
                TransCodeConst.WITHDROW, Constants.KZ_ID,
                SettleConstants.ORDER_WITHDRAW,
                SettleConstants.WITHHOLD_CODE,0);
    }	

	/**
	 * 展酷提现
	 */	
	@Transactional(rollbackFor = AccountException.class, propagation = Propagation.NESTED)
	public void withdrawZK(){
		settlementServiceThr.paymentGeneration(
				TransCodeConst.WITHDROW, Constants.ZK_ID,
				SettleConstants.ORDER_WITHDRAW,
				SettleConstants.WITHHOLD_CODE,1);
	}
	
	/**
	 * 指尖代言提现
	 */	
	@Transactional(rollbackFor = AccountException.class, propagation = Propagation.NESTED)
	public void withdrawZJDY(){
		settlementServiceThr.paymentGeneration(
				TransCodeConst.WITHDROW, Constants.ZJDY_ID,
				SettleConstants.ORDER_WITHDRAW,
				SettleConstants.WITHHOLD_CODE,1);
	}
	
	/**
	 * 会堂提现汇总
	 */	
	@Transactional(rollbackFor = AccountException.class, propagation = Propagation.NESTED)
	public void withdrawHT(){
		settlementServiceThr.paymentGeneration(
				TransCodeConst.WITHDROW, Constants.HT_ID,
				SettleConstants.ORDER_WITHDRAW,
				SettleConstants.WITHHOLD_CODE,1);
	}
	
	/**
	 * 丰年提现
	 */	
	@Transactional(rollbackFor = AccountException.class, propagation = Propagation.NESTED)
	public void withdraw(){
		settlementServiceThr.paymentGeneration(
				TransCodeConst.WITHDROW, Constants.FN_ID,
				SettleConstants.ORDER_WITHDRAW,
				SettleConstants.WITHHOLD_CODE,1);
	}
	
	/**
	 * 君融贷提现
	 */	
	@Transactional(rollbackFor = AccountException.class, propagation = Propagation.NESTED)
	public void withdrawJRD(){
		settlementServiceThr.paymentGeneration(TransCodeConst.WITHDROW, Constants.JRD_ID,SettleConstants.ORDER_WITHDRAW,
				SettleConstants.WITHHOLD_CODE,1);
//		settlementServiceThr.paymentGeneration(TransCodeConst.WITHDROW, Constants.JRD_ID,SettleConstants.ORDER_WITHDRAW,
//				SettleConstants.WITHHOLD_CODE_OLD,1);
	}
	
	/**
	 * 棉庄提现
	 */	
	@Transactional(rollbackFor = AccountException.class, propagation = Propagation.NESTED)
	public void withdrawMZ(){
		settlementServiceThr.paymentGeneration(TransCodeConst.WITHDROW, Constants.MZ_ID,SettleConstants.ORDER_WITHDRAW,
				SettleConstants.WITHHOLD_CODE,1);
//		settlementServiceThr.paymentGeneration(TransCodeConst.WITHDROW, Constants.MZ_ID,SettleConstants.ORDER_WITHDRAW,
//				SettleConstants.WITHHOLD_CODE_OLD,1);
	}
	/**
	 * 食全食美提现
	 */	
	@Transactional(rollbackFor = AccountException.class, propagation = Propagation.NESTED)
	public void withdrawSQSM(){
		settlementServiceThr.paymentGeneration(TransCodeConst.WITHDROW, Constants.SQSM_ID,SettleConstants.ORDER_WITHDRAW,
				SettleConstants.WITHHOLD_CODE,1);
//		settlementServiceThr.paymentGeneration(TransCodeConst.WITHDROW, Constants.SQSM_ID,SettleConstants.ORDER_WITHDRAW,
//				SettleConstants.WITHHOLD_CODE_OLD,1);
	}
	
	/**
	 * 丰年T+0提现
	 * @throws ParseException 
	 */	
	@Transactional(rollbackFor = AccountException.class, propagation = Propagation.NESTED)
	public void withdrawFNT0() throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date date0903 = sdf.parse("2015-09-03");
		Date date0904 = sdf.parse("2015-09-04");
		Date dateCur = sdf.parse(sdf.format(new Date()));
		if(dateCur.compareTo(date0903)!=0 && dateCur.compareTo(date0904)!=0){
			settlementServiceThr.paymentGeneration(
					TransCodeConst.WITHDROW, Constants.FN_ID,
					SettleConstants.ORDER_WITHDRAW,
//					SettleConstants.WITHHOLD_CODE_OLD,0);
					SettleConstants.WITHHOLD_CODE,0);
			//生成丰年T+0提现文件并上传
			try {
				String accountDate=generationPaymentService.getAccountDate();
				String batch=settlementLogic.getBatchNo(DateUtils.getAccountDate(Constants.DATE_FORMAT_YYYYMMDD, accountDate),SettleConstants.ROP_PAYMENT_BATCH_CODE, Constants.FN_ID);
				logger.info("-------丰年T+0提现批次号------"+batch);
//				generationPaymentService.uploadPaymentFile(7,batch, Constants.FN_ID,DateUtils.getDate(accountDate, Constants.DATE_FORMAT_YYYYMMDD));
				generationPaymentService.submitToRecAndPaySys(7, Constants.FN_ID,accountDate,DateUtils.getDate(accountDate, Constants.DATE_FORMAT_YYYYMMDD));
			} catch (AccountException e) {
				logger.error("生成丰年T+0提现到代收付系统失败"+e.getMessage());
				RkylinMailUtil.sendMailThread("丰年T+0提现提交到代收付", "生成丰年T+0提现提交到代收付"+e.getMessage(), TransCodeConst.GENERATION_PAY_ERROR_TOEMAIL);
			}
		}
	}	
}
