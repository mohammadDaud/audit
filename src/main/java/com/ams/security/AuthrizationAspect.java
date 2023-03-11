package com.ams.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.ams.Utility.JsonUtil;
import com.ams.Utility.XssSanitizerUtil;
import com.ams.api.constants.ErrorCodeEnum;
import com.ams.exception.ApplicationException;
import com.ams.exception.ForbiddenException;
import com.ams.exception.RestErrorInfo;
import com.ams.model.RoleResponse;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@Aspect
public class AuthrizationAspect {

	@Before("allMethods()")
	public void doFilter(JoinPoint joinPoint)
			throws IOException, ServletException {

		final HttpServletRequest request =
				((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

		final HttpServletResponse response =
				((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

		Object[] args = joinPoint.getArgs();
		
			for (Object arg : args) {
				try {
				if(arg instanceof MultipartFile)
					continue;
				String requestString= JsonUtil.toString(arg);
				if(XssSanitizerUtil.sniffForXss(requestString)) 
					throw new ApplicationException("Invalid Input");
				}catch(Exception e) {
					//Methods parsing errors will be logged  
					log.error("JSON Parse Error/Invalid Input");
					continue;
				}

			}
		String method = request.getMethod();

		log.info("Starting a Authorization check for req : {}", request.getRequestURI());

		boolean authRequired = true;
		String operation = map.get(method);

		if (operation == null)
			return;

		String menuId = request.getParameter("menuId");
		try {
			if (menuId == null || menuId.isEmpty()) {
				//throw new ForbiddenException("Access Denied"); Todo pass menu Id from frontend request for which auth should be checked.
				return;
			}

			if (authRequired && !isAuthorized(menuId, operation))
				//throw new ForbiddenException("Access Denied");
				return;

		} catch (ForbiddenException forbiddenException) {
			handleForbiddenException(forbiddenException, response);
		}
	}

	private Map<String,String> map = new HashMap<>();
	{
		map.put("POST","A");
		map.put("PUT","E");
		map.put("DELETE","D");
	}
	private boolean isAuthorized(String menuId, String operation) {
		RoleResponse permissions = getPermissions();
		return permissions.getAssignedMenu().stream().anyMatch(map -> {
			String permission = map.get(menuId);
			if (permission == null || permission.isEmpty())
				return false;

			String[] split = permission.split(",");
			return Arrays.asList(split).contains(operation);
		});
	}

	private RoleResponse getPermissions() {
		String permissionJson = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

		return JsonUtil.fromString(permissionJson, RoleResponse.class);
	}

	public void handleForbiddenException(ForbiddenException ex, HttpServletResponse response) throws IOException {
		log.error("ForbiddenException  : " + ex.getMessage());

		RestErrorInfo body = new RestErrorInfo(ErrorCodeEnum.FORBIDDEN_ERROR.getErrorCode(), ex.getMessage());
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.getWriter().write(JsonUtil.toString(body));
	}

	@Pointcut("execution(* com.maps.api.**.rest..*(..))")
	public void allMethods() {

	}
}