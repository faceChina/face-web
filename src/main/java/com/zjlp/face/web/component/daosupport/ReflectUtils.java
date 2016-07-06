package com.zjlp.face.web.component.daosupport;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.axis.utils.bytecode.ChainedParamReader;

 

public final class ReflectUtils {

	private ReflectUtils() {
	}

	public static boolean isContainsMethod(Class<?> clz, Method m) {
		if (clz == null || m == null) {
			return false;
		}
		try {
			clz.getDeclaredMethod(m.getName(), m.getParameterTypes());
			return true;
		} catch (NoSuchMethodException e) {
			return false;
		}
	}

	public static Object invoke(Object target, String methodName) {
		return invoke(target, methodName, null);
	}

	public static Object invoke(Object target, String methodName, Object[] paramValues) {
		Class<?>[] parameterTypes = getClassArray(paramValues);
		return invoke(target, methodName, parameterTypes, paramValues);
	}

	public static Object invoke(Object target, String methodName, Class<?>[] paramTypes, Object[] paramValues) {
		try {
			Class<?> clazz = target.getClass();
			Method method = clazz.getDeclaredMethod(methodName, paramTypes);
			return invoke(target, method, paramValues);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public static Object invoke(Object target, Method method, Object[] paramValues) {
		boolean accessible = method.isAccessible();
		try {
			method.setAccessible(true);
			if (Modifier.isStatic(method.getModifiers())) {
				return method.invoke(null, paramValues);
			} else {
				return method.invoke(target, paramValues);
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} finally {
			method.setAccessible(accessible);
		}
	}

	public static boolean hasMethod(Object obj, String methodName, Class<?>[] paramTypes) {
		Class<?> clazz = obj.getClass();
		try {
			clazz.getDeclaredMethod(methodName, paramTypes);
			return true;
		} catch (NoSuchMethodException e) {
			return false;
		}
	}

	public static Object invokeStatic(Class<?> clazz, String methodName, Object[] paramValues) {
		try {
			Class<?>[] parameterTypes = getClassArray(paramValues);
			Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
			return invokeStatic(method, paramValues);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	public static Object invokeStatic(Class<?> clazz, String methodName, Class<?>[] paramTypes, Object[] paramValues) {
		try {
			Method method = clazz.getDeclaredMethod(methodName, paramTypes);
			return invokeStatic(method, paramValues);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	public static Object invokeStatic(Method method, Object[] paramValues) {
		boolean accessible = method.isAccessible();
		try {
			method.setAccessible(true);
			if (Modifier.isStatic(method.getModifiers())) {
				return method.invoke(null, paramValues);
			} else {
				throw new RuntimeException("can not run none static class without object");
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} finally {
			method.setAccessible(accessible);
		}
	}

	public static boolean hasStaticMethod(Class<?> clazz, String methodName, Class<?>[] paramTypes) {
		try {
			clazz.getDeclaredMethod(methodName, paramTypes);
			return true;
		} catch (NoSuchMethodException e) {
			return false;
		}
	}

	public static Class<?>[] getClassArray(Object[] objects) {
		if (objects == null) {
			return null;
		}
		int arguments = objects.length;
		Class<?>[] objectTypes = new Class[arguments];
		for (int i = 0; i < arguments; i++) {
			objectTypes[i] = objects[i].getClass();
		}
		return objectTypes;
	}

	public static Object getStaticField(Class<?> clazz, String fieldName) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.get(null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void setStaticField(Class<?> clazz, String fieldName, Object value) {
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.set(null, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Object getInstanceField(Object instance, String fieldName) {
		try {
			Field f = instance.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.get(instance);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void setInstanceField(Object instance, String fieldName, Object value) {
		try {
			Field f = instance.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			f.set(instance, value);
		} catch (Exception e) {
		}
	}
	
    @SuppressWarnings("rawtypes")
	public static Annotation getMethodAnnotation(Method method, Class AnnotationClass) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (AnnotationClass.getSimpleName().equalsIgnoreCase(annotation.annotationType().getSimpleName())) {
                return annotation;
            }
        }
        return null;
    }

    public static Map<String, Object> getFieldMapForClass(Object object) {
        Map<String, Object> parameterMap = null;
        try {
            parameterMap = new HashMap<String, Object>();
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                populateFieldMap(field, object, parameterMap);
            }
            Field[] parentFields = object.getClass().getSuperclass().getDeclaredFields();
            for (Field parentField : parentFields) {
                populateFieldMap(parentField, object, parameterMap);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return parameterMap;
    }
    
    public static void setObjectFromMap(Map<String, Object> map, Object object) {
    	try {
			Field[] fields = object.getClass().getDeclaredFields();
			for (Field field : fields) {
				setObjectFromMap(field, object, map);
			}
			Field[] parentFields = object.getClass().getSuperclass().getDeclaredFields();
			for (Field parentField : parentFields) {
				setObjectFromMap(parentField, object, map);
            }
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
    
    private static void setObjectFromMap(Field field, Object object, Map<String, Object> fieldMap) 
    		throws IllegalArgumentException, IllegalAccessException {
    	String filedName = field.getName();
    	if (fieldMap.containsKey(filedName)) {
    		boolean isStatic = Modifier.isStatic(field.getModifiers());
    		if (isStatic) {
    			return;
    		}
    		boolean isAccessible = field.isAccessible();
    		field.setAccessible(true);
    		field.set(object, fieldMap.get(filedName));
    		field.setAccessible(isAccessible);
    	}
    }

    private static void populateFieldMap(Field field, Object object, Map<String, Object> fieldMap)
            throws IllegalArgumentException, IllegalAccessException {
        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);
        fieldMap.put(field.getName(), field.get(object));
        field.setAccessible(isAccessible);
    }

//    @SuppressWarnings("rawtypes")
//	public static String[] getMethodParameterNames(Class clazz, Method method) {
//        try {
//            ChainedParamReader pr = new ChainedParamReader(clazz);
//            return pr.getParameterNames(method);
//        } catch (IOException e) {
//            return null;
//        }
//    }

    public static Class<?>[] getParameterTypes(Object[] args) throws Exception {
        if(args == null){
            return null;
        }
        Class<?>[] parameterTypes = new Class[args.length];
        for (int i = 0, j = args.length; i < j; i++) {
            if(args[i] instanceof Integer){
                parameterTypes[i] = Integer.TYPE;
            }else if(args[i] instanceof Byte){
                parameterTypes[i] = Byte.TYPE;
            }else if(args[i] instanceof Short){
                parameterTypes[i] = Short.TYPE;
            }else if(args[i] instanceof Float){
                parameterTypes[i] = Float.TYPE;
            }else if(args[i] instanceof Double){
                parameterTypes[i] = Double.TYPE;
            }else if(args[i] instanceof Character){
                parameterTypes[i] = Character.TYPE;
            }else if(args[i] instanceof Long){
                parameterTypes[i] = Long.TYPE;
            }else if(args[i] instanceof Boolean){
                parameterTypes[i] = Boolean.TYPE;
            }else if(args[i] instanceof Map){
                parameterTypes[i] = Map.class;
            }else if(args[i] instanceof List){
                parameterTypes[i] = List.class;
            }else{
                parameterTypes[i] = args[i].getClass();
            }
        }
        return parameterTypes;
    }
}
