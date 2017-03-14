package itest.elec.domain;



@SuppressWarnings("serial")
public class ElecPopedom implements java.io.Serializable {
	
	
	private String mid;			//权限Code（主键ID）
	private String pid;			//父级权限code，如果已经是根节点则为0
	private String name;		//权限名称
	private String url;			//权限在系统中执行访问地址的URL
	private String icon;		//如果是菜单，则为显示图片的URL
	private String target;		//如果是菜单，链接执行的Frame区域名称
	private boolean isParent;	//是否是父节点，父节点为true，子节点为false
	private boolean isMenu;		//是否是系统菜单结构
	
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}
	public boolean getIsMenu() {
		return isMenu;
	}
	public void setIsMenu(boolean isMenu) {
		this.isMenu = isMenu;
	}
	/****************非持久化javabean属性**************************************/
	private String flag;
	
	private String roleID;


	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getRoleID() {
		return roleID;
	}
	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}
	
	//保存时，选中的权限，存放的格式：aa_0,ab_aa
	private String [] selectoper;


	public String[] getSelectoper() {
		return selectoper;
	}
	public void setSelectoper(String[] selectoper) {
		this.selectoper = selectoper;
	}
	
	
	//保存时，选中的用户信息，存放格式：用户id
	
	private String [] selectuser;


	public String[] getSelectuser() {
		return selectuser;
	}
	public void setSelectuser(String[] selectuser) {
		this.selectuser = selectuser;
	}
	
}
