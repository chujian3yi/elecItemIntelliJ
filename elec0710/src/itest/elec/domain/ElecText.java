package itest.elec.domain;

import java.util.Date;

@SuppressWarnings("serial")//此处是注解？

public class ElecText implements java.io.Serializable{
	
	private String textID;		//测试id	
	private String textName;	//测试name
	private Date textDate;		//测试时间
	private String textRemark;	//测试备注
	
	public String getTextID() {
		return textID;
	}
	public void setTextID(String textID) {
		this.textID = textID;
	}
	
	public String getTextName() {
		return textName;
	}
	public void setTextName(String textName) {
		this.textName = textName;
	}
	public Date getTextDate() {
		return textDate;
	}
	public void setTextDate(Date textDate) {
		this.textDate = textDate;
	}
	public String getTextRemark() {
		return textRemark;
	}
	public void setTextRemark(String textRemark) {
		this.textRemark = textRemark;
	}	
}
