//package com.maps.aspect;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Modifier;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Table;
//import javax.servlet.http.HttpServletRequest;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//
//import com.maps.Utility.AppUtil;
//import com.maps.Utility.JsonUtil;
//import com.maps.api.admin.service.MenuService;
//import com.maps.api.admin.service.UserRoleService;
//import com.maps.api.admin.service.UserService;
//import com.maps.common.entity.AuditTrailLog;
//import com.maps.common.service.AuditService;
//import com.maps.exception.ApplicationException;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//
//@Aspect
//@Component
//@RequiredArgsConstructor
//@Log4j2
//public class AuditAspect {
//
//	private List<String> ignoreFields = Arrays.asList("id");
//
//	private final AuditService auditService;
//	private final UserService userService;
//	private final UserRoleService userRoleService;
//	private final MenuService menuService;
//
//	@PersistenceContext
//	private EntityManager em;
//
//	private final HttpServletRequest request;
//
//	@Around("@annotation(logAddAudit)")
//	public Object logAddAudit(ProceedingJoinPoint jp, LogAddAudit logAddAudit) throws Throwable {
//		log.info(":::::AOP Audit Around  Method call for Add check flag:::::");
//		if (!auditService.isAuditable(logAddAudit.module()))
//			return jp.proceed();
//
//		Object newObject = jp.proceed();
//
//		AuditTrailLog auditEntity = getAddAuditEntity(newObject, logAddAudit.module());
//		if (auditEntity != null)
//			auditService.logAudit(auditEntity);
//
//		return newObject;
//	}
//
//	@Around("@annotation(logDeleteAudit)")
//	public void logDeleteAudit(ProceedingJoinPoint jp, LogDeleteAudit logDeleteAudit) throws Throwable {
//		log.info(":::::AOP Audit Around  Method call for Delete check flag:::::");
//		if (!auditService.isAuditable(logDeleteAudit.module())) {
//			jp.proceed();
//			return;
//		}
//
//		Object deleteId = jp.getArgs()[0];
//
//		Object oldObject = getOldObject(logDeleteAudit.module(), (Long) deleteId);
//		em.detach(oldObject);
//
//		jp.proceed();
//
//		AuditTrailLog auditEntity = getAuditEntity(oldObject, logDeleteAudit.module());
//		if (auditEntity != null)
//			auditService.logAudit(auditEntity);
//	}
//
//	@Around("@annotation(logUpdateAudit)")
//	public Object logUpdateAudit(ProceedingJoinPoint jp, LogUpdateAudit logUpdateAudit) throws Throwable {
//
//		log.info(":::::AOP Audit Around  Method call Update check flag:::::");
//		if (!auditService.isAuditable(logUpdateAudit.module()))
//			return jp.proceed();
//
//		Object requestObject = jp.getArgs()[0];
//		Field modifiedId = requestObject.getClass().getDeclaredField("id");
//		modifiedId.setAccessible(true);
//
//		Object oldObject = getOldObject(logUpdateAudit.module(), (Long) modifiedId.get(requestObject));
//		em.detach(oldObject);
//
//		Object newObject = jp.proceed();
//
//		if (!oldObject.equals(newObject)) { // log if object changed
//
//			AuditTrailLog auditEntity = getAuditEntity(oldObject, newObject, logUpdateAudit.module());
//			if (auditEntity != null)
//				auditService.logAudit(auditEntity);
//		}
//		return newObject;
//	}
//
//	private Object getOldObject(String module, Long entityId) throws IllegalAccessException {
//
//		switch (module) {
//		case Module.USER:
//			return userService.getUser(entityId);
//		case Module.MENU:
//			return menuService.getMenu(entityId);
//		case Module.USER_ROLE:
//			return userRoleService.getUserRole(entityId);
//
//		default:
//			throw new ApplicationException("Invalid Module");
//		}
//	}
//
//	private AuditTrailLog getAuditEntity(Object oldObject, Object newObject, String module)
//			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException,
//			InstantiationException, ClassNotFoundException {
//
//		// creating new instance for changed Values
//		Object changedObject = Class.forName(newObject.getClass().getName()).newInstance();
//
//		Field[] fields = newObject.getClass().getDeclaredFields();
//		Arrays.stream(fields).forEach(field -> {
//
//			if (isAuditField(field))
//				return;
//
//			field.setAccessible(true);
//			Field oldValue;
//			try {
//				oldValue = oldObject.getClass().getDeclaredField(field.getName());
//
//				oldValue.setAccessible(true);
//
//				if ((field.get(newObject) == null && oldValue.get(oldObject) == null)
//						|| (field.get(newObject) != null && field.get(newObject).equals(oldValue.get(oldObject)))) {
//
//					field.set(changedObject, null);
//					oldValue.set(oldObject, null);
//				} else {
//					oldValue.set(changedObject, field.get(newObject));
//				}
//			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
//				log.error("Error in Audit", e);
//			}
//		});
//
//		if (JsonUtil.isEmpty(oldObject) && JsonUtil.isEmpty(changedObject))
//			return null;
//
//		AuditTrailLog auditTrailLog = new AuditTrailLog();
//		auditTrailLog.setModuleName(module);
//		auditTrailLog.setAction("UPDATE");
//		auditTrailLog.setCreatedBy(AppUtil.getCurrentUser());
//		auditTrailLog.setCreatedOn(LocalDateTime.now());
//		auditTrailLog.setTableName(oldObject.getClass().getAnnotation(Table.class).name());
//		auditTrailLog.setInstId(AppUtil.getCurrentInstIdStr());
//		auditTrailLog.setMrchId(AppUtil.getCurrentMerchantIdStr());
//		Field field = oldObject.getClass().getDeclaredField("id");
//		field.setAccessible(true);
//		auditTrailLog.setTableKey((Long) field.get(oldObject));
//		auditTrailLog.setOldValues(JsonUtil.toString(oldObject));
//		auditTrailLog.setNewValues(JsonUtil.toString(changedObject));
//
//		auditTrailLog.setLoggedIp(AppUtil.getClientIpAddr(request));
//		return auditTrailLog;
//	}
//
//	private AuditTrailLog getAuditEntity(Object oldObject, String module)
//			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException,
//			InstantiationException, ClassNotFoundException {
//
//		AuditTrailLog auditTrailLog = new AuditTrailLog();
//		auditTrailLog.setModuleName(module);
//		auditTrailLog.setAction("DELETE");
//		auditTrailLog.setCreatedBy(AppUtil.getCurrentUser());
//		auditTrailLog.setCreatedOn(LocalDateTime.now());
//		auditTrailLog.setTableName(oldObject.getClass().getAnnotation(Table.class).name());
//		auditTrailLog.setInstId(AppUtil.getCurrentInstIdStr());
//		auditTrailLog.setMrchId(AppUtil.getCurrentMerchantIdStr());
//		Field field = oldObject.getClass().getDeclaredField("id");
//		field.setAccessible(true);
//		auditTrailLog.setTableKey((Long) field.get(oldObject));
//		auditTrailLog.setOldValues(JsonUtil.toString(oldObject));
//		auditTrailLog.setLoggedIp(AppUtil.getClientIpAddr(request));
//		return auditTrailLog;
//	}
//
//	private AuditTrailLog getAddAuditEntity(Object newObject, String module)
//			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
//
//		AuditTrailLog auditTrailLog = new AuditTrailLog();
//		auditTrailLog.setModuleName(module);
//		auditTrailLog.setAction("ADD");
//		String user = AppUtil.getCurrentUser();
//		auditTrailLog.setCreatedBy(user);
//		auditTrailLog.setCreatedOn(LocalDateTime.now());
//		auditTrailLog.setTableName(newObject.getClass().getAnnotation(Table.class).name());
//		if(!user.equalsIgnoreCase("anonymousUser")){
//			auditTrailLog.setInstId(AppUtil.getCurrentInstIdStr());
//			auditTrailLog.setMrchId(AppUtil.getCurrentMerchantIdStr());
//        }
//		Field field = newObject.getClass().getDeclaredField("id");
//		field.setAccessible(true);
//		auditTrailLog.setTableKey((Long) field.get(newObject));
//		auditTrailLog.setNewValues(JsonUtil.toString(newObject));
//		auditTrailLog.setLoggedIp(AppUtil.getClientIpAddr(request));
//		return auditTrailLog;
//	}
//
//	private boolean isAuditField(Field field) {
//		return ignoreFields.contains(field.getName()) || !Modifier.isPrivate(field.getModifiers())
//				|| Modifier.isFinal(field.getModifiers());
//	}
//}
