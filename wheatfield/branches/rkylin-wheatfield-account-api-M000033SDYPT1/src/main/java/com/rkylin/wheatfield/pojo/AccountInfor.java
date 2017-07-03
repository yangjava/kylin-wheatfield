/*
 * Powered By rkylin-code-generator
 * Web Site: http://www.chanjetpay.com
 * Since 2014 - 2015
 */

package com.rkylin.wheatfield.pojo;

import java.io.Serializable;
import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * AccountInfo
 * @author code-generator
 *
 */
public class AccountInfor implements Serializable{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer accountId;
	private java.lang.String finAccountId;
	private java.lang.String accountName;
	private java.lang.String accountTypeId;
	private java.lang.String accountPurpose;
	private java.lang.String accountNumber;
	private java.lang.String accountProperty;
	private java.util.Date openAccountDate;
	private java.lang.String openAccountDescription;
	private java.lang.String currency;
	private Integer statusId;
	private java.lang.String bankHead;
	private java.lang.String bankHeadName;
	private java.lang.String bankBranch;
	private java.lang.String bankBranchName;
	private java.lang.String bankProvince;
	private java.lang.String bankCity;
	private java.lang.String certificateType;
	private java.lang.String certificateNumber;
	private java.lang.String accountRealName;
	private java.util.Date createdTime;
	private java.util.Date updatedTime;
	private String rootInstCd;
	private String userId;
    private String whetherRealName;//是否实名(0：非实名，1：已实名)默认0
    private String accountCode;
    private Date startTime;
    private Date endTime;
    private Integer PageSize;
    private Integer PageNum;
    
	public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWhetherRealName() {
        return whetherRealName;
    }

    public void setWhetherRealName(String whetherRealName) {
        this.whetherRealName = whetherRealName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getPageSize() {
        return PageSize;
    }

    public void setPageSize(Integer pageSize) {
        PageSize = pageSize;
    }

    public Integer getPageNum() {
        return PageNum;
    }

    public void setPageNum(Integer pageNum) {
        PageNum = pageNum;
    }

    /**
	 * 账户信息ID
	 * @param accountId
	 */
	public void setAccountId(java.lang.Integer accountId) {
		this.accountId = accountId;
	}
	
	/**
	 * 账户信息ID
	 * @return
	 */
	public java.lang.Integer getAccountId() {
		return this.accountId;
	}
	/**
	 * 账户ID
	 * @param finAccountId
	 */
	public void setFinAccountId(java.lang.String finAccountId) {
		this.finAccountId = finAccountId;
	}
	
	/**
	 * 账户ID
	 * @return
	 */
	public java.lang.String getFinAccountId() {
		return this.finAccountId;
	}
	/**
	 * 账户名称
	 * @param accountName
	 */
	public void setAccountName(java.lang.String accountName) {
		this.accountName = accountName;
	}
	
	/**
	 * 账户名称
	 * @return
	 */
	public java.lang.String getAccountName() {
		return this.accountName;
	}
	/**
	 * 账户类型ID,1信用卡;2借记卡
	 * @param accountTypeId
	 */
	public void setAccountTypeId(java.lang.String accountTypeId) {
		this.accountTypeId = accountTypeId;
	}
	
	/**
	 * 账户类型ID,1信用卡;2借记卡
	 * @return
	 */
	public java.lang.String getAccountTypeId() {
		return this.accountTypeId;
	}
	/**
	 * 账户目的,1结算卡;2其他卡
	 * @param accountPurpose
	 */
	public void setAccountPurpose(java.lang.String accountPurpose) {
		this.accountPurpose = accountPurpose;
	}
	
	/**
	 * 账户目的,1结算卡;2其他卡
	 * @return
	 */
	public java.lang.String getAccountPurpose() {
		return this.accountPurpose;
	}
	/**
	 * 账号
	 * @param accountNumber
	 */
	public void setAccountNumber(java.lang.String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	/**
	 * 账号
	 * @return
	 */
	public java.lang.String getAccountNumber() {
		return this.accountNumber;
	}
	/**
	 * 账户属性,1对公;2对私
	 * @param accountProperty
	 */
	public void setAccountProperty(java.lang.String accountProperty) {
		this.accountProperty = accountProperty;
	}
	
	/**
	 * 账户属性,1对公;2对私
	 * @return
	 */
	public java.lang.String getAccountProperty() {
		return this.accountProperty;
	}
	/**
	 * 开户日期
	 * @param openAccountDate
	 */
	public void setOpenAccountDate(java.util.Date openAccountDate) {
		this.openAccountDate = openAccountDate;
	}
	
	/**
	 * 开户日期
	 * @return
	 */
	public java.util.Date getOpenAccountDate() {
		return this.openAccountDate;
	}
	/**
	 * 账号用途
	 * @param openAccountDescription
	 */
	public void setOpenAccountDescription(java.lang.String openAccountDescription) {
		this.openAccountDescription = openAccountDescription;
	}
	
	/**
	 * 账号用途
	 * @return
	 */
	public java.lang.String getOpenAccountDescription() {
		return this.openAccountDescription;
	}
	/**
	 * 币种
	 * @param currency
	 */
	public void setCurrency(java.lang.String currency) {
		this.currency = currency;
	}
	
	/**
	 * 币种
	 * @return
	 */
	public java.lang.String getCurrency() {
		return this.currency;
	}
	public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    /**
	 * 开户行总行
	 * @param bankHead
	 */
	public void setBankHead(java.lang.String bankHead) {
		this.bankHead = bankHead;
	}
	
	/**
	 * 开户行总行
	 * @return
	 */
	public java.lang.String getBankHead() {
		return this.bankHead;
	}
	/**
	 * 开户行总行名称
	 * @param bankHeadName
	 */
	public void setBankHeadName(java.lang.String bankHeadName) {
		this.bankHeadName = bankHeadName;
	}
	
	/**
	 * 开户行总行名称
	 * @return
	 */
	public java.lang.String getBankHeadName() {
		return this.bankHeadName;
	}
	/**
	 * 开户行支行
	 * @param bankBranch
	 */
	public void setBankBranch(java.lang.String bankBranch) {
		this.bankBranch = bankBranch;
	}
	
	/**
	 * 开户行支行
	 * @return
	 */
	public java.lang.String getBankBranch() {
		return this.bankBranch;
	}
	/**
	 * 开户行支行名称
	 * @param bankBranchName
	 */
	public void setBankBranchName(java.lang.String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	
	/**
	 * 开户行支行名称
	 * @return
	 */
	public java.lang.String getBankBranchName() {
		return this.bankBranchName;
	}
	/**
	 * 开户行所在省
	 * @param bankProvince
	 */
	public void setBankProvince(java.lang.String bankProvince) {
		this.bankProvince = bankProvince;
	}
	
	/**
	 * 开户行所在省
	 * @return
	 */
	public java.lang.String getBankProvince() {
		return this.bankProvince;
	}
	/**
	 * 开户行所在市
	 * @param bankCity
	 */
	public void setBankCity(java.lang.String bankCity) {
		this.bankCity = bankCity;
	}
	
	/**
	 * 开户行所在市
	 * @return
	 */
	public java.lang.String getBankCity() {
		return this.bankCity;
	}
	/**
	 * 开户证件类型
	 * @param certificateType
	 */
	public void setCertificateType(java.lang.String certificateType) {
		this.certificateType = certificateType;
	}
	
	/**
	 * 开户证件类型
	 * @return
	 */
	public java.lang.String getCertificateType() {
		return this.certificateType;
	}
	/**
	 * 证件号
	 * @param certificateNumber
	 */
	public void setCertificateNumber(java.lang.String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}
	
	/**
	 * 证件号
	 * @return
	 */
	public java.lang.String getCertificateNumber() {
		return this.certificateNumber;
	}
	/**
	 * 账户真实名称
	 * @param accountRealName
	 */
	public void setAccountRealName(java.lang.String accountRealName) {
		this.accountRealName = accountRealName;
	}
	
	/**
	 * 账户真实名称
	 * @return
	 */
	public java.lang.String getAccountRealName() {
		return this.accountRealName;
	}
	/**
	 * 记录创建时间
	 * @param createdTime
	 */
	public void setCreatedTime(java.util.Date createdTime) {
		this.createdTime = createdTime;
	}
	
	/**
	 * 记录创建时间
	 * @return
	 */
	public java.util.Date getCreatedTime() {
		return this.createdTime;
	}
	/**
	 * 记录更新时间
	 * @param updatedTime
	 */
	public void setUpdatedTime(java.util.Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	
	/**
	 * 记录更新时间
	 * @return
	 */
	public java.util.Date getUpdatedTime() {
		return this.updatedTime;
	}

	public String getRootInstCd() {
		return rootInstCd;
	}

	public void setRootInstCd(String rootInstCd) {
		this.rootInstCd = rootInstCd;
	}
}