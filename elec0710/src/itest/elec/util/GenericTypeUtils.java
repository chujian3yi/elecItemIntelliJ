package itest.elec.util;

import java.lang.reflect.ParameterizedType;

public class GenericTypeUtils {
	// 泛型转换
	
	public static Class getGenericSuperClass(Class entity) {
		
		//泛类转换，目的是将对应的泛型，装换成真实的对象类型。
		//此时type表示，BaseAction< itest.elec.domain.ElecText>
		ParameterizedType type = (ParameterizedType)entity.getGenericSuperclass();
		//entityClass:itest.elec.domain.ElecText
		Class entityClass = (Class)type.getActualTypeArguments()[0];
		return entityClass;
	}

}
