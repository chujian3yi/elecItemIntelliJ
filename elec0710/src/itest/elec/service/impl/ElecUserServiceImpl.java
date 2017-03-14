package itest.elec.service.impl;

import itest.elec.dao.IElecSystemDDLDao;
import itest.elec.dao.IElecUserDao;
import itest.elec.domain.ElecUser;
import itest.elec.service.IElecUserService;
import itest.elec.util.MD5keyBean;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service(IElecUserService.SERVICE_NAME)

/**@Transactional的作用是Spring管理事务要加上的注解，
 * 全局有效
 * 默认是可写，开发时候类级别的事务可以定义为只读
 * 在save()里面就会报错，此时可以在save()加上注解
 * @Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
 * 即方法里面改变类的属性？
 * */

@Transactional(readOnly=true)					
public class ElecUserServiceImpl implements IElecUserService {

	@Resource(name=IElecUserDao.SERVICE_NAME)
	private IElecUserDao elecUserDao;

	@Resource(name=IElecSystemDDLDao.SERVICE_NAME)
	private IElecSystemDDLDao elecSystemDDLDao;
	
	
	/**  
	* @Name: findUserListByCondition（方法的名称）
	* @Description: 指定条件查询用户信息（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-9-3 （创建日期）
	* @Parameters: ElecUser:传递参数
	* @Return: 用户集合
	*/
	
	public List<ElecUser> findUserListByCondition(ElecUser elecUser) {
		/*2:获取页面传递的姓名、所属单位、入职时间判断是否为空
		 * 为null：查询所有
		 * 不为null：就指定对应的条件查询用户表
		 * 返回List<ElecUser>
		 * */
		//组织查询条件
		String condition = "";
		List<Object> paramsList = new ArrayList<Object>();
		//获取用户姓名
		String userName =  elecUser.getUserName();
		if(StringUtils.isNotBlank(userName)){
			condition += " and o.userName like ?";
			paramsList.add("%"+userName+"%");
		}
		//所属单位
		if(StringUtils.isNotBlank(elecUser.getJctID())){
			condition += " and o.jctID = ?";
			paramsList.add(elecUser.getJctID());
		}
		//在职时间开始日期
		if(elecUser.getOnDutyDateBegin()!=null){
			condition += " and o.onDutyDate >= ?";
			paramsList.add(elecUser.getOnDutyDateBegin());
		}
		//在职时间结束日期
		if(elecUser.getOnDutyDateEnd()!=null){
			condition += " and o.onDutyDate <= ?";
			paramsList.add(elecUser.getOnDutyDateEnd());
		}
		Object [] params = paramsList.toArray();
		//排序,按照入职时间降序
		 Map<String, String> orderyby = new LinkedHashMap<String,String>();
		 orderyby.put("o.onDutyDate", "desc");
		 List<ElecUser> list = elecUserDao.findCollectionByConditionNoPage(condition, params, orderyby);
		 //3涉及到数据字典字段的时候，要将数据想的编号转换成数据项值
		 this.userPOListToVOList(list);
		 
		return list;
	}


	 //3涉及到数据字典字段的时候，要将数据想的编号转换成数据项值
	private void userPOListToVOList(List<ElecUser> list) {
		if(list!=null && list.size()>0){
			for (ElecUser elecUser : list) {
				elecUser.getSexID();//编号
				//使用数据类型和数据想的编号获取数据项的值
				elecUser.setSexID(StringUtils.isNotBlank(elecUser.getSexID())?elecSystemDDLDao.findDdlNameByKeywordAndDdlCode("性别",elecUser.getSexID()):"");
				elecUser.setJctID(StringUtils.isNotBlank(elecUser.getJctUnitID())?elecSystemDDLDao.findDdlNameByKeywordAndDdlCode("所属单位",elecUser.getJctUnitID()):"");
				elecUser.setIsDuty(StringUtils.isNotBlank(elecUser.getIsDuty())?elecSystemDDLDao.findDdlNameByKeywordAndDdlCode("是否在职",elecUser.getIsDuty()):"");
				elecUser.setPostID(StringUtils.isNotBlank(elecUser.getPostID())?elecSystemDDLDao.findDdlNameByKeywordAndDdlCode("职位",elecUser.getPostID()):"");
				//#数据字典要定义成VARCHAR,可以用于数据字典的ddlCode和ddlName之间的转换
				
			}
			
			
		}
	}

	/**  
	* @Name: checkUser（方法的名称）
	* @Description:使用登陆名作为条件，查询登录名在数据库中是否出现重复（ajax的二级联动）（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-10-01（创建日期）
	* @Parameters: 无
	* @Return:	String message ="";
	* 			message = 1:表示登录名不能为空
	* 			message = 2：表示登录名在数据库中已经存在，此时不能保存
	* 			message = 3：表示登录名不存在，此时可以保存
	*/
	public String checkUser(String logonName) {
		String message = "";
		if(StringUtils.isNotBlank(logonName)){
			String condition = " and o.logonName = ?";
			Object [] params = {logonName};
			/*以登录名作为条件查询用户表，返回List<ElecUser>
			 * .如果登录名为空，message=1
				如果list为空，返回message = 3，
				如果list不为空，说明数据库中有存在一个该值，返回message = 2.
			*/
			List<ElecUser> list = elecUserDao.findCollectionByConditionNoPage(condition, params, null);
			if(list!=null && list.size()>0){
				message = "2";
			}
			else{
				message = "3";
			}
		}else{
			message = "1";
		}
		return message;
	}
	
	/**  
	* @Name: saveUser（方法的名称）
	* @Description: 保存新增用户（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-10-2 （创建日期）
	* @Parameters: ElecUser:存放保存对象
	* @Return: 无
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveUser(ElecUser elecUser) {
		//获取用户ID
		String userID = elecUser.getUserID();
		//添加md5的密码加密
		this.md5Password(elecUser);
		//1.如果userID为空，则直接获取保存的PO对象，执行保存
		if(StringUtils.isBlank(userID)){
			elecUserDao.save(elecUser);
		}
		//2.如果userID不为空，获取更新得PO对象，执行updata()
		else{
			elecUserDao.update(elecUser);
		}
	}
		/**添加md5的密码加密，对登录名的密码进行安全的控制*/
	private void md5Password(ElecUser elecUser) {
		//获取页面输入的密码
		String logonPwd = elecUser.getLogonPwd();
		//加密后的密码
		String md5LogonPwd = "";
		//如果密码没有填写，给出默认密码123
		if(StringUtils.isBlank(logonPwd)){
			logonPwd = "123";
		}
		//是否对密码进行了修改，获取password
		String password = elecUser.getPassword();
		if(password!=null && password.equals(logonPwd)){
			md5LogonPwd = logonPwd;
		}
		else{
			//md5密码加密
			MD5keyBean md5keyBean = new MD5keyBean();
			md5LogonPwd = 	md5keyBean.getkeyBeanofStr(logonPwd);
		}
		//最后将加密后的密码放置到ElecUser中
		elecUser.setLogonPwd(md5LogonPwd);
	}


	/**  
	* @Name: findElecUserByID（方法的名称）
	* @Description: 使用主键id，查询对应的用户信息（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-10-2 （创建日期）
	* @Parameters: ElecUser:存放id	
	* * @Return: ElecUser，查询的用户信息
	*/
	public ElecUser findElecUserByID(ElecUser elecUser) {
		//获取用户id
		String userID = elecUser.getUserID();
		//使用id，查询用户信息
		ElecUser user = elecUserDao.findObjectByID(userID);
		return user;
	}
	
	/**  
	* @Name: deleteUserByIds（方法的名称）
	* @Description: 页面选择userID,使用一个或者多个id删除用户信息（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-10-2 （创建日期）
	* @Parameters: ElecUser:存放i的字符串，多个id中间用", "分隔	
	* * @Return: 无
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteUserByIds(ElecUser elecUser) {
		// 1，获取userID
		String userID = elecUser.getUserID();
		String [] ids = userID.split(", ");
		//2.底层封装的delete方法
		elecUserDao.deleteObjectByIDS(ids);
		
	}
	
	/**  
	* @Name: findElecUserByLogonName（方法的名称）
	* @Description: 使用登录名作为条件，查询对应用户
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-10-2 （创建日期）
	* @Parameters:String 登录名：name
	* * @Return: 返回用户 ElecUser对象:elecUser
	*/
	public ElecUser findElecUserByLogonName(String name) {
		// 组织查询条件
		String condition = "";
		List<Object> paramsList = new ArrayList<Object>();
		if(StringUtils.isNotBlank(name)){
			condition += " and o.logonName=?";
			paramsList.add(name);
		}
		Object[] params = paramsList.toArray();
		//查询
		List<ElecUser> list = elecUserDao.findCollectionByConditionNoPage(condition, params, null);
		ElecUser elecUser = null;
		if(list!=null && list.size()>0){
			elecUser = list.get(0);
			//方案一
			//elecUser.getElecRoles().size();
			//方案二
			Hibernate.initialize(elecUser.getElecRoles());
		}
		return elecUser;
	}
	
	/**  
	* @Name: findPopedomByLogonName（方法的名称）
	* @Description: 使用登录名作为条件，查询当前登录名具有的权限
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-10-2 （创建日期）
	* @Parameters:String 登录名：name
	* * @Return: String popedom;权限的字符串
	*/
	public String findPopedomByLogonName(String name) {
		List<Object> list = elecUserDao.findPopedomByLogonName(name);
		StringBuffer buffer = new StringBuffer();
		if(list!=null && list.size()>0){
			for(Object object:list){
				buffer.append(object.toString()).append("@");
			}
			//删除最后一个@
			buffer.deleteCharAt(buffer.length()-1);
		}
		
		return buffer.toString();
	}
}
