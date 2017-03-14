package itest.elec.domain;


@SuppressWarnings("serial")
public class ElecSystemDDL implements java.io.Serializable {
	
	private Integer seqID;		//主键ID(自增长)
	private String keyword;		//数据类型
	private Integer ddlCode;	//数据项的code
	private String ddlName;		//数据项的value
	
	
	public ElecSystemDDL(){
		
	}
	
	public ElecSystemDDL (String keyword){
		this.keyword = keyword;
	}
	
	
	
	
	public Integer getSeqID() {
		return seqID;
	}
	
	public void setSeqID(Integer seqID) {
		this.seqID = seqID;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getDdlCode() {
		return ddlCode;
	}
	public void setDdlCode(Integer ddlCode) {
		this.ddlCode = ddlCode;
	}
	public String getDdlName() {
		return ddlName;
	}
	public void setDdlName(String ddlName) {
		this.ddlName = ddlName;
	}
	/******************非持久化属性*****VO对象**********************/
	//用来传递数据想的值(ddlName)
	private String [] itemname;

	//存放数据类型
	private String keywordname;
	
	/**
	 * 用来判断业务逻辑【保存】的标识
	 * 
	 * 如果typeflag值为new：新增一种新的数据类型
	 * 如果typeflag的值为add：在原有的数据类型基础上进行修改和编辑
	 * */
	private String typeflag;
	
	
	public String[] getItemname() {
		return itemname;
	}

	public void setItemname(String[] itemname) {
		this.itemname = itemname;
	}

	public String getKeywordname() {
		return keywordname;
	}

	public void setKeywordname(String keywordname) {
		this.keywordname = keywordname;
	}

	public String getTypeflag() {
		return typeflag;
	}

	public void setTypeflag(String typeflag) {
		this.typeflag = typeflag;
	}

}
