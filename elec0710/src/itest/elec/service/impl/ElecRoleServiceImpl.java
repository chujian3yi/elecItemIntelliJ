package itest.elec.service.impl;

import itest.elec.dao.IElecPopedomDao;
import itest.elec.dao.IElecRoleDao;
import itest.elec.dao.IElecRolePopedomDao;
import itest.elec.dao.IElecUserDao;
import itest.elec.domain.ElecPopedom;
import itest.elec.domain.ElecRole;
import itest.elec.domain.ElecRolePopedom;
import itest.elec.domain.ElecUser;
import itest.elec.service.IElecRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service(IElecRoleService.SERVICE_NAME)

/**@Transactional的作用是Spring管理事务要加上的注解，
 * 全局有效
 * 默认是可写，开发时候类级别的事务可以定义为只读
 * 在save()里面就会报错，此时可以在save()加上注解
 * @Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
 * 即方法里面改变类的属性？
 * */

@Transactional(readOnly=true)					
public class ElecRoleServiceImpl implements IElecRoleService {
	//角色
	@Resource(name=IElecRoleDao.SERVICE_NAME)
	private IElecRoleDao elecRoleDao;
	//权限
	@Resource(name=IElecPopedomDao.SERVICE_NAME)
	private IElecPopedomDao elecPopedomDao;
	//角色权限
	@Resource(name=IElecRolePopedomDao.SERVICE_NAME)
	private IElecRolePopedomDao elecRolePopedomDao;
	//用户
	@Resource(name=IElecUserDao.SERVICE_NAME)
	private IElecUserDao elecUserDao;
	/**  
	* @Name:findRoleList （方法的名称）
	* @Description: 查询所有的角色信息（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-10-25 （创建日期）
	* @Parameters: 无（方法的入参，如果没有，填写为“无”）
	* @Return: 跳转到system/roleIndex.jsp
	*/
	public List<ElecRole> findRoleList() {
		Map<String, String> orderby = new LinkedHashMap<String,String>();
		orderby.put("o.roleID", "asc");
		List<ElecRole> list = elecRoleDao.findCollectionByConditionNoPage("", null, orderby);
		return list;
	}
	
	/**  
	* findPopedomList （方法的名称）
	* @Description: 查询所有权限信息（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-10-25 （创建日期）
	* @Parameters: 无（方法的入参，如果没有，填写为“无”）
	* @Return: 跳转到system/roleIndex.jsp
	*/
	public List<ElecPopedom> findPopedomList() {
		Map<String, String> orderby = new LinkedHashMap<String,String>();
		orderby.put("o.mid", "asc");
		List<ElecPopedom> list = elecPopedomDao.findCollectionByConditionNoPage("", null, orderby);
		return list;
	}
	
	/**  
	* findPopedomListByRoleID （方法的名称）
	* @Description: 使用角色ID，查询所有权限信息（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-10-25 （创建日期）
	* @Parameters: roleID）
	* @Return: 返回角色关联权限集合List<ElecPopedom>
	*/
	public List<ElecPopedom> findPopedomListByRoleID(String roleID) {
		//1:  popedomList 查询所有权限
		List<ElecPopedom> popedomList = this.findPopedomList();
		//2:获取角色ID，查询角色关 联权限表，获取当前角色所有的功能权限集合List<ELecRolePopedom> list
		String condition = " and o.roleID = ?";
		Object[] params = {roleID};
		Map<String, String> orderby = new LinkedHashMap<String,String>();
		orderby.put("o.mid", "asc");
		List<ElecRolePopedom> list = elecRolePopedomDao.findCollectionByConditionNoPage(condition, params, orderby);
		//3:遍历List<ElecPopedom> popedomList,获取所有的权限
		//如果获取的每个权限与当前角色权限匹配就设置flag="1"
		//如果获取的每个权限与当前角色权限不匹配就设置flag="2"
		
		StringBuffer buffer = new StringBuffer("");
		if(list!=null && list.size()>0){
			for(ElecRolePopedom elecRolePopedom:list){
				buffer.append(elecRolePopedom.getMid()).append("@");//aa@ab@ac
			}
			buffer.deleteCharAt(buffer.length()-1);
		}
		String rolePopedom = buffer.toString();
		
		if(popedomList!=null && popedomList.size()>0){
			for(ElecPopedom elecPopedom :popedomList){
				if(rolePopedom.contains(elecPopedom.getMid())){
					//如果获取的每个权限与当前角色权限匹配就设置flag="1"
					elecPopedom.setFlag("1");
				}
				else{
					//如果获取的每个权限与当前角色权限不匹配就设置flag="2"
					elecPopedom.setFlag("2");
				}
			}
			
		}
		return popedomList;
	}
	
	/**  
	* findUserListByUserID （方法的名称）
	* @Description: 使用角色ID，查询所有用户信息（方法的描述）
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-10-25 （创建日期）
	* @Parameters: 无（方法的入参，如果没有，填写为“无”）
	* @Return: 返回角色关联用户集合
	*/
	public List<ElecUser> findUserListByUserID(String roleID) {
		//1：查询所有的用户，遍历
		String condition = " and o.isDuty = '1' ";
		Map<String, String> orderby = new LinkedHashMap<String,String>();
		orderby.put("o.onDutyDate", "asc");
		List<ElecUser> userList = elecUserDao.findCollectionByConditionNoPage(condition, null, orderby);
		//2：获取当前角色ID，查询角色表，获取角色信息userID
		//	获取当前角色下的用户ElecRole.getElecUsers();
		ElecRole elecRole = elecRoleDao.findObjectByID(roleID);
		Set<ElecUser> set = elecRole.getElecUsers();
		//使用List<>存放用户信息userID
		List<String> list = new ArrayList<>();
		if(set!=null & set.size()>0){
			for(ElecUser elecUser:set){
				list.add(elecUser.getUserID());
			}
			
		}
		
		//3：遍历所有用户userList，比较当前角色下用户set，userID是否匹配，
		if(userList!=null & userList.size()>0 ){
			for(ElecUser elecUser :userList){
				if(list.contains(elecUser.getUserID())){
						elecUser.setFlag("1");
					}else{
						elecUser.setFlag("2");
				}
			}
			
		}
		return userList ;
	}

	/**  
	* saveRole （方法的名称）
	* @Description: 保存角色和权限，保存用户和角色
	* @Author: ghq（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-10-25 （创建日期）
	* @Parameters: 无（方法的入参，如果没有，填写为“无”）
	* @Return: 无
	* 
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveRole(ElecPopedom elecPopedom) {
	//角色ID
	String roleID = elecPopedom.getRoleID();
	//选中的权限数组
	String [] selectoper = elecPopedom.getSelectoper();
	//选中的用户数组
	String [] selectuser = elecPopedom.getSelectuser();
	//1保存角色和权限
	  this.saveRolePopedom(roleID,selectoper);
	//2保存用户和角色
	  this.saveRoleUser(roleID,selectuser);
	}
	

	//保存角色和权限（未使用hibernate）
	private void saveRolePopedom(String roleID, String[] selectoper) {
		
		//1：使用角色id查询角色权限表的当前角色下所有角色权限，返回集合List<ElecRolePopedom>
		String condition = " and o.roleID = ?";
		Object[] params = {roleID};
		List<ElecRolePopedom> list = elecRolePopedomDao.findCollectionByConditionNoPage(condition, params, null);
		//2：执行删除
		elecRolePopedomDao.deleteObjectByCollection(list);
		//3：遍历数组selectoper,组织po对象，执行更新
		if(selectoper!=null && selectoper.length>0){
			for(String oper:selectoper){
				String [] arrays = oper.split("_");
				ElecRolePopedom elecRolePopedom = new ElecRolePopedom();
				elecRolePopedom.setRoleID(roleID);
				elecRolePopedom.setMid(arrays[0]);
				elecRolePopedom.setPid(arrays[1]);
				elecRolePopedomDao.save(elecRolePopedom);
			}
		}
		
	}
	
//保存用户和角色（使用hibernate）
	private void saveRoleUser(String roleID, String[] selectuser) {
		//1：使用角色id，查询角色表，获取当前角色
		ElecRole elecRole = elecRoleDao.findObjectByID(roleID);
		//2：使用当前角色，获取当前所有用户，Set<ElecUser> set = elecRole.getElecUsers();
		//3：遍历页面选中的用户数组selectuser，获取当前用户信息，放置到Set<ElecUser> set ;
		Set<ElecUser> set = new HashSet<ElecUser>();
		if(selectuser!=null && selectuser.length>0){
			for(String userID: selectuser){
				ElecUser elecUser = new ElecUser();
				elecUser.setUserID(userID);
				set.add(elecUser);
			}
		}
		//4：重新建立ElecRole对象的集合属性elecUser的关联关系
		 elecRole.setElecUsers(set);
		
	}

	/**
	 * findShowMenu （方法的名称）
	 * @Description: 保存角色和权限，保存用户和角色
	 * @Author: ghq（作者）
	 * @Version: V1.00 （版本号）
	 * @Create Date: 2016-10-25 （创建日期）
	 * @Parameters: popedom:从session中获取的当前登录名具有权限，格式为
	 * @Return: list<ElecPopedom> list:封装权限集合list、
	 *
	 */
	public List<ElecPopedom> findShowMenu(String popedom) {
		String condition = " AND o.mid IN ('"+popedom.replace("@", "','")+"') AND isMenu = ?";
		Object [] params ={true};
		Map<String,String> orderby = new LinkedHashMap<String,String>();
        orderby.put("o.mid", "asc");
        List<ElecPopedom> list = elecPopedomDao.findCollectionByConditionNoPage(condition,params,orderby );
		return list;
	}
	/**使用角色ID，子权限编号，父权限编号，查询角色权限表的所有数据*/
	public boolean findRolePopedomByID(String roleID,String mid,String pid) {
		//组织查询条件
		String condition = "";
		List<Object> paramsList = new ArrayList<Object>();
		//角色ID
		if(StringUtils.isNotBlank(roleID)){
			condition += " and o.roleID = ?";
			paramsList.add(roleID);
		}
		//子权限名称
		if(StringUtils.isNotBlank(mid)){
			condition += " and o.mid = ?";
			paramsList.add(mid);
		}
		//父权限名称
		if(StringUtils.isNotBlank(pid)){
			condition += " and o.pid = ?";
			paramsList.add(pid);
		}
		Object [] params = paramsList.toArray();
		//查询对应的角色权限信息
		List<ElecRolePopedom> list = elecRolePopedomDao.findCollectionByConditionNoPage(condition, params, null);
		boolean flag = false;
		if(list!=null && list.size()>0){
			flag = true;
		}
		return flag;
	}

}
