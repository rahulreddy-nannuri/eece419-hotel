package ubc.eece419.pod1.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ReflectionUtils {

	public static Type getGenericParameter(Class<?> examine, Class<?> lookFor) {
		Type[] types;
		if (lookFor.isInterface()) {
			types = getInterfaceGenericParameters(examine, lookFor);
		} else {
			types = getClassGenericParameters(examine, lookFor);
		}
		return types == null ? null : types[0];
	}

	public static Type[] getInterfaceGenericParameters(Class<?> clazz, Class<?> interfaceToLookFor) {
		for (Type iface : clazz.getGenericInterfaces()) {
			if (iface instanceof ParameterizedType) {
				ParameterizedType pt = (ParameterizedType) iface;
				if (interfaceToLookFor.equals(pt.getRawType())) {
					return pt.getActualTypeArguments();
				}
			}
		}
		for (Class<?> iface : clazz.getInterfaces()) {
			Type[] types = getInterfaceGenericParameters(iface, interfaceToLookFor);
			if (types != null) return types;
		}
		if (clazz.getSuperclass() != null) {
			Type[] types = getInterfaceGenericParameters(clazz.getSuperclass(), interfaceToLookFor);
			if (types != null) return types;
		}
		return null;
	}

	public static Type[] getClassGenericParameters(Class<?> clazz, Class<?> classToLookFor) {
		Type t = clazz.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) t;
			if (classToLookFor.equals(pt.getRawType())) {
				return pt.getActualTypeArguments();
			}
		}
		if (clazz.getSuperclass() != null) {
			return getClassGenericParameters(clazz.getSuperclass(), classToLookFor);
		}
		return null;
	}

}
