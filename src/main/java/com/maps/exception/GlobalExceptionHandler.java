//package com.maps.exception;
//
//import java.util.stream.Collectors;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import com.maps.api.constants.ErrorCodeEnum;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import lombok.extern.log4j.Log4j2;
//
//@ControllerAdvice
//@Log4j2
//public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//
//	@ExceptionHandler(ExpiredJwtException.class)
//	public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request,
//			HttpServletResponse response) {
//		log.error("ExpiredJwtException to RestResponse : " + ex.getMessage());
//		ex.printStackTrace();
//		RestErrorInfo body = new RestErrorInfo(ErrorCodeEnum.JWT_EXPIRED.getErrorCode(), "Session Expired");
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(new MediaType("application", "problem+json"));
//		return handleExceptionInternal(ex, body, headers, HttpStatus.UNAUTHORIZED, request);
//	}
//
//	@ExceptionHandler(ResourceNotFoundException.class)
//	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request,
//			HttpServletResponse response) {
//		log.error("ResourceNotFoundException to RestResponse : " + ex.getMessage());
//		ex.printStackTrace();
//		RestErrorInfo body = new RestErrorInfo(ErrorCodeEnum.RESOURCE_NOT_FOUND.getErrorCode(), ex.getMessage());
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(new MediaType("application", "problem+json"));
//		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
//	}
//
//	@ExceptionHandler(DataIntegrityViolationException.class)
//	public ResponseEntity<Object> handleResourceNotFoundException(DataIntegrityViolationException ex,
//			WebRequest request, HttpServletResponse response) {
//		log.error("DataIntegrityViolationException to RestResponse : " + ex.getMessage());
//		ex.printStackTrace();
//		RestErrorInfo body = new RestErrorInfo(ErrorCodeEnum.DATA_INTEGRITY_ERROR.getErrorCode(), "Record Already Exists");
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(new MediaType("application", "problem+json"));
//		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
//	}
//
//	@ExceptionHandler(BadCredentialsException.class)
//	public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request,
//			HttpServletResponse response) {
//		log.error("BadCredentialsException to RestResponse : " + ex.getMessage());
//		ex.printStackTrace();
//		RestErrorInfo body = new RestErrorInfo(ErrorCodeEnum.BAD_CREDENTIALS_ERROR.getErrorCode(), "INVALID_CREDENTIAL");
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(new MediaType("application", "problem+json"));
//		return handleExceptionInternal(ex, body, headers, HttpStatus.UNAUTHORIZED, request);
//	}
//
//	@ExceptionHandler(ApplicationException.class)
//	public ResponseEntity<Object> handleApplicationException(ApplicationException ex, WebRequest request,
//			HttpServletResponse response) {
//		log.error("ApplicationException to RestResponse : " + ex.getMessage());
//		ex.printStackTrace();
//		RestErrorInfo body = new RestErrorInfo(ErrorCodeEnum.APPLICATION_ERROR.getErrorCode(), ex.getMessage());
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(new MediaType("application", "problem+json"));
//		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
//	}
//
//	@ExceptionHandler(ForbiddenException.class)
//	public ResponseEntity<Object> handleForbiddenException(ForbiddenException ex, WebRequest request,
//			HttpServletResponse response) {
//		log.error("Converting ForbiddenException to RestResponse : " + ex.getMessage());
//		ex.printStackTrace();
//		RestErrorInfo body = new RestErrorInfo(ErrorCodeEnum.FORBIDDEN_ERROR.getErrorCode(), ex.getMessage());
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(new MediaType("application", "problem+json"));
//		return handleExceptionInternal(ex, body, headers, HttpStatus.FORBIDDEN, request);
//	}
//
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<Object> handleException(Exception ex, WebRequest request, HttpServletResponse response) {
//		log.error("Exception to RestResponse : " + ex.getMessage());
//		ex.printStackTrace();
//		RestErrorInfo body = new RestErrorInfo(ErrorCodeEnum.PROCESSING_ERROR.getErrorCode()
//				,"Error in Request Processing");
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(new MediaType("application", "problem+json"));
//		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
//	}
//	
//	@ExceptionHandler(RuntimeException.class)
//	public ResponseEntity<Object> handleRuntimeException(Exception ex, WebRequest request, HttpServletResponse response) {
//		log.error("RuntimeException to RestResponse : " + ex.getMessage());
//		ex.printStackTrace();
//		RestErrorInfo body = new RestErrorInfo(ErrorCodeEnum.PROCESSING_ERROR.getErrorCode()
//				,ex.getMessage());
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(new MediaType("application", "problem+json"));
//		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
//	}
//
//	@Override
//	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
//			HttpStatus status, WebRequest request) {
//		ex.printStackTrace();
//		String message=	ex.getBindingResult().getAllErrors()
//											.stream().map(err->err.getDefaultMessage())
//											.collect(Collectors.joining(","));
//
//		RestErrorInfo body = new RestErrorInfo(ErrorCodeEnum.INVALID_METHOD_ARGUMENT.getErrorCode(), message);
//		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
//	}
//}