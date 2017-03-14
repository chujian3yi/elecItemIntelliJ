package itest.elec.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class ElecUser implements java.io.Serializable {
	private String userID;		//主键ID
	private String jctID;		//所属单位code
	private String jctUnitID;	//所属单位的单位名称（联动）
	private String userName;	//用户姓名
	private String logonName;	//登录名
	private String logonPwd;	//密码
	private String sexID;		//性别
	private Date birthday;		//出生日期
	private String address;		//联系地址
	private String contactTel;	//联系电话 
	private String email;		//电子邮箱
	private String mobile;		//手机
	private String isDuty;		//是否在职
	private String postID;      //职位
	private Date onDutyDate;	//入职时间
	private Date offDutyDate;	//离职时间
	private String remark;		//备注
	

	private Set<ElecRole> elecRoles = new HashSet<ElecRole>();
	
	/**映射与申请信息表的申请人ID建议一对多的关系*/
	//private Set<ElecApplication> elecApplications = new HashSet<ElecApplication>();
	
	/**映射与审核信息表的审核人ID建议一对多的关系*/
	//private Set<ElecApproveInfo> elecApproveInfos = new HashSet<ElecApproveInfo>();
	
	
	/*public Set<ElecApproveInfo> getElecApproveInfos() {
		return elecApproveInfos;
	}
	public void setElecApproveInfos(Set<ElecApproveInfo> elecApproveInfos) {
		this.elecApproveInfos = elecApproveInfos;
	}
	public Set<ElecApplication> getElecApplications() {
		return elecApplications;
	}
	public void setElecApplications(Set<ElecApplication> elecApplications) {
		this.elecApplications = elecApplications;
	}
	*/
	public Set<ElecRole> getElecRoles() {
		return elecRoles;
	}
	public void setElecRoles(Set<ElecRole> elecRoles) {
		this.elecRoles = elecRoles;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getJctID() {
		return jctID;
	}
	public void setJctID(String jctID) {
		this.jctID = jctID;
	}
	public String getJctUnitID() {
		return jctUnitID;
	}
	public void setJctUnitID(String jctUnitID) {
		this.jctUnitID = jctUnitID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLogonName() {
		return logonName;
	}
	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}
	public String getLogonPwd() {
		return logonPwd;
	}
	public void setLogonPwd(String logonPwd) {
		this.logonPwd = logonPwd;
	}
	public String getSexID() {
		return sexID;
	}
	public void setSexID(String sexID) {
		this.sexID = sexID;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIsDuty() {
		return isDuty;
	}
	public void setIsDuty(String isDuty) {
		this.isDuty = isDuty;
	}
	public String getPostID() {
		return postID;
	}
	public void setPostID(String postID) {
		this.postID = postID;
	}
	public Date getOnDutyDate() {
		return onDutyDate;
	}
	public void setOnDutyDate(Date onDutyDate) {
		this.onDutyDate = onDutyDate;
	}
	public Date getOffDutyDate() {
		return offDutyDate;
	}
	public void setOffDutyDate(Date offDutyDate) {
		this.offDutyDate = offDutyDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/********************非持久话 javabean属性*********************************/
	//入职开始时间
	private Date onDutyDateBegin;
	//在职结束时间
	private Date onDutyDateEnd;


	public Date getOnDutyDateBegin() {
		return onDutyDateBegin;
	}
	public void setOnDutyDateBegin(Date onDutyDateBegin) {
		this.onDutyDateBegin = onDutyDateBegin;
	}
	public Date getOnDutyDateEnd() {
		return onDutyDateEnd;
	}
	public void setOnDutyDateEnd(Date onDutyDateEnd) {
		this.onDutyDateEnd = onDutyDateEnd;
	}
	
	//message属性，表示ajax传递给页面校验的值
	private String message;


	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	//获取页面传递的隐藏域的值，存放添砖编辑页面之前的登陆密码
	private String password;


	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	/*在同一个页面做两种显示的标识,用于判断是否
	 * 				跳转到编辑页面:viewflag==null;
					还是查看明细页面viewflag==1;
	*/
	private String viewflag;

	public String getViewflag() {
		return viewflag;
	}
	public void setViewflag(String viewflag) {
		this.viewflag = viewflag;
	}
	
	/**
	 * 在ElecUser对象添加一个标识flag
    * 如果flag==1：此时页面的复选框被选中
    * 如果flag==2：此时页面的复选框不被选中
	 */
	private String flag;

	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}


