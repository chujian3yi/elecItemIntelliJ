package itest.elec.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface ICommonDao<T> {
	public void save(T entry);
	void update(T entry);
	T findObjectByID(Serializable id);
	//可变参数的写法，因为需要封装成一个id能删，多个ids变成数组也能删除"Serializable..."替代Serializable[]
	void deleteObjectByIDS(Serializable... ids);
	
	void deleteObjectByCollection(List<T> list);

	List<T> findCollectionByConditionNoPage(String condition,
			Object[] params, Map<String, String> orderby);

	List<T> findCollectionByConditionNoPageCache(String condition, Object[] params,
			Map<String, String> orderby);
}
