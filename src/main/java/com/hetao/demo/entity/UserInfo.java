package com.hetao.demo.entity;

import java.util.Date;

/**
 * 表everstar.UserInfo的xml文件resultMap实体类 
 * 
 * @author 杨云飞
 *
 */
public class UserInfo {
	
    private Integer id;
    private String userName;
    private String password;
    private Integer accessCount;
    private String phoneID;
    private Date registerForTime;
    private Date lastLoginForTime;
    private Integer lastCount;
    private Integer accountLevel;
    private Date promoteVipTime;
    private Integer parter;
    private String productType;
    private String macAddress;
    private Integer sendMessageCount;
    private Integer remainMoney;
    private Integer paymentMoney;
    private String paymentedForTest;
    private String difProductType;
    private String phoneNum;
    private Integer unbindNum;
    private String muserName;
    
    // 构造
    public UserInfo() {}
    
    public UserInfo(String userName, String password, Date registerForTime,
                    Date lastLoginForTime, String phoneNum) {
    	super();
    	this.userName = userName;
    	this.password = password;
    	this.registerForTime = registerForTime;
    	this.lastLoginForTime = lastLoginForTime;
    	this.phoneNum = phoneNum;
    }
    
    // get set
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getAccessCount() {
		return accessCount;
	}
	public void setAccessCount(Integer accessCount) {
		this.accessCount = accessCount;
	}
	public String getPhoneID() {
		return phoneID;
	}
	public void setPhoneID(String phoneID) {
		this.phoneID = phoneID;
	}
	public Date getRegisterForTime() {
		return registerForTime;
	}
	public void setRegisterForTime(Date registerForTime) {
		this.registerForTime = registerForTime;
	}
	public Date getLastLoginForTime() {
		return lastLoginForTime;
	}
	public void setLastLoginForTime(Date lastLoginForTime) {
		this.lastLoginForTime = lastLoginForTime;
	}
	public Integer getLastCount() {
		return lastCount;
	}
	public void setLastCount(Integer lastCount) {
		this.lastCount = lastCount;
	}
	public Integer getAccountLevel() {
		return accountLevel;
	}
	public void setAccountLevel(Integer accountLevel) {
		this.accountLevel = accountLevel;
	}
	public Date getPromoteVipTime() {
		return promoteVipTime;
	}
	public void setPromoteVipTime(Date promoteVipTime) {
		this.promoteVipTime = promoteVipTime;
	}
	public Integer getParter() {
		return parter;
	}
	public void setParter(Integer parter) {
		this.parter = parter;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public Integer getSendMessageCount() {
		return sendMessageCount;
	}
	public void setSendMessageCount(Integer sendMessageCount) {
		this.sendMessageCount = sendMessageCount;
	}
	public Integer getRemainMoney() {
		return remainMoney;
	}
	public void setRemainMoney(Integer remainMoney) {
		this.remainMoney = remainMoney;
	}
	public Integer getPaymentMoney() {
		return paymentMoney;
	}
	public void setPaymentMoney(Integer paymentMoney) {
		this.paymentMoney = paymentMoney;
	}
	public String getPaymentedForTest() {
		return paymentedForTest;
	}
	public void setPaymentedForTest(String paymentedForTest) {
		this.paymentedForTest = paymentedForTest;
	}
	public String getDifProductType() {
		return difProductType;
	}
	public void setDifProductType(String difProductType) {
		this.difProductType = difProductType;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public Integer getUnbindNum() {
		return unbindNum;
	}
	public void setUnbindNum(Integer unbindNum) {
		this.unbindNum = unbindNum;
	}
	public String getMuserName() {
		return muserName;
	}
	public void setMuserName(String muserName) {
		this.muserName = muserName;
	}
}
