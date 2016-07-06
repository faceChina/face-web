package com.zjlp.face.web.component.daosupport;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.IntroductionInterceptor;

public class DaoIntroductionInterceptor implements IntroductionInterceptor {

	public Object invoke(final MethodInvocation methodInvocation) throws Throwable {
		
		if (!implementsInterface(methodInvocation.getMethod().getDeclaringClass())) {
			
			return methodInvocation.proceed();
		}
		
		final GenericDao genericDao = (GenericDao) methodInvocation.getThis();
		
		String methodName = methodInvocation.getMethod().getName();
		
		final Map<String, Object> parameterMap = this.getMappedParameter(methodInvocation);
		
		if (methodName.startsWith("add") || methodName.startsWith("insert")) {
			
			Object result = genericDao.excuteInsert(methodInvocation.getMethod(), parameterMap);
			
			Object[] args = methodInvocation.getArguments();
			
			ReflectUtils.setObjectFromMap(parameterMap, args[0]);
			
			return result;
			
		} else if (methodName.startsWith("edit")||methodName.startsWith("update") ) {
			
			return genericDao.excuteUpdate(methodInvocation.getMethod(), parameterMap);
			
		} else if (methodName.startsWith("delete") || methodName.startsWith("remove") 
				|| methodName.startsWith("del")) {
			
			return genericDao.excuteDelete(methodInvocation.getMethod(), parameterMap);
			
		} else if (methodName.startsWith("select") || methodName.startsWith("get")) {
			return genericDao.excuteGet(methodInvocation.getMethod(), parameterMap);
			
		} else if (methodName.startsWith("find") || methodName.startsWith("findPage")) {
			return genericDao.excuteQuery(methodInvocation.getMethod(), parameterMap);
			
		} else {
			return methodInvocation.proceed();
			
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getMappedParameter(MethodInvocation methodInvocation) {
		
		Map<String, Object> parameterMap = null;
		
		Object[] args = methodInvocation.getArguments();
		
		if (null != args && args.length > 0) {
			
			parameterMap = new HashMap<String, Object>();
			
			Annotation[][] annotationArray = methodInvocation.getMethod().getParameterAnnotations();
			
			if (null != annotationArray && annotationArray.length > 0) {
				
				for (int i = 0; i < annotationArray.length; i++) {
					
					if (null != annotationArray[i] && annotationArray[i].length > 0) {
						
						for (Annotation annotation : annotationArray[i]) {
							
							if (null != annotation
									&& Param.class.getSimpleName().equalsIgnoreCase(
											annotation.annotationType().getSimpleName())) {
								
								Param param = (Param) annotation;
								
								if ("entity".equalsIgnoreCase(param.value())) {
									
									parameterMap.putAll(ReflectUtils.getFieldMapForClass(args[i]));
								} else if (args[i] instanceof Map) {
									
									parameterMap.putAll((Map<String, Object>) args[i]);
								} else {
									
									parameterMap.put(param.value(), args[i]);
								}
							}
						}
					}
				}
			}
		}
		return parameterMap;
	}

	@SuppressWarnings("rawtypes")
	public boolean implementsInterface(Class clazz) {
		
		return clazz.isInterface() && GenericDao.class.isAssignableFrom(clazz);
	}

}
