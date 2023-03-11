package com.ams.api.admin.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ams.Utility.AppUtil;
import com.ams.api.admin.entity.ForgotPasswordVerification;
import com.ams.api.admin.entity.PasswordHistory;
import com.ams.api.admin.entity.PasswordResetToken;
import com.ams.api.admin.entity.SecurityQuestion;
import com.ams.api.admin.entity.User;
import com.ams.api.admin.entity.UserLoginHistory;
import com.ams.api.admin.entity.UserRole;
import com.ams.api.admin.model.ChangePasswordRequest;
import com.ams.api.admin.model.ForgotPasswordVerifyRequest;
import com.ams.api.admin.model.PasswordResendRequest;
import com.ams.api.admin.model.QuestionAnswer;
import com.ams.api.admin.model.SecurityQuestionDTO;
import com.ams.api.admin.model.UserCreationRequest;
import com.ams.api.admin.model.UserDTO;
import com.ams.api.admin.model.UserUpdateRequest;
import com.ams.api.admin.model.VerifyQuestionRequest;
import com.ams.api.admin.repository.ForgotPasswordVerificationRepository;
import com.ams.api.admin.repository.PasswordHistoryRepository;
import com.ams.api.admin.repository.PasswordTokenRepository;
import com.ams.api.admin.repository.SecurityQuestionRepository;
import com.ams.api.admin.repository.UserLoginHistoryRepository;
import com.ams.api.admin.repository.UserRepository;
import com.ams.api.admin.repository.UserRoleRepository;
import com.ams.common.service.EmailService;
import com.ams.common.service.MessageSourceService;
import com.ams.constants.TokenType;
import com.ams.exception.ApplicationException;
import com.ams.exception.ResourceNotFoundException;
import com.ams.model.RoleResponse;
import com.ams.model.Status;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/*
 * Sample service to demonstrate what the API would use to get things done
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final PasswordTokenRepository passwordTokenRepository;
	private final EmailService emailService;
	private final UserLoginHistoryRepository userLoginHistoryRepository;
	private final PasswordHistoryRepository passwordHistoryRepository;
	private final ForgotPasswordVerificationRepository forgotPasswordVerificationRepository;
	private final int SECURITY_QUESTION_VERIFIED = 2;
	private final int USER_ID_VERIFIED = 1;
	private static final String RESET_PASSWORD_TEMPLATE = "resetPasswordEmail";
	private static final String USER = "all-users";
	private static final String OPS = "all-ops-users";
	private static final String MRCH = "all-mrch-users";
	private final MessageSourceService messageSource;

	@Value("${admin-portal-base-url}")
	private String adminPortalBaseUrl;

	@Value("${operation-portal-base-url}")
	private String opsPortalBaseUrl;

	@Value("${merch-portal-base-url}")
	private String merchantPortalBaseUrl;

	@Value("${forgot-password-expiry-period:1}")
	private int forgotPasswordExpiryInHr;

	@Value("${merch-portal-base-url}")
	private String merchantPortalUrl;

	@Autowired
	private TemplateEngine templateEngine;

	
	@Caching(evict = {@CacheEvict(cacheNames = USER, condition = "#userCreationRequest.getUserType() == 1", allEntries = true),
			@CacheEvict(cacheNames = OPS,condition = "#userCreationRequest.getUserType() == 3", allEntries = true),
			@CacheEvict(cacheNames = MRCH ,condition = "#userCreationRequest.getUserType() == 2", allEntries = true)}
			)
	//@LogAddAudit(module = Module.USER)
	public User createUser(UserCreationRequest userCreationRequest) {
		log.info("===========User Creation ===========");
		User user = new User(userCreationRequest);
		user.setUserRole(userRoleRepository.findById(userCreationRequest.getUserRole()).get());

		user.setUserStatus(Status.INACTIVE);
		user.setCreatedOn(LocalDateTime.now());
		user.setCreatedBy(AppUtil.getCurrentUser());
		this.saveUserPassword(userCreationRequest.getPassword(), userCreationRequest.getUserId());
		userRepository.save(user);
		sendEmailBody(userCreationRequest, user);
		
		return user;

	}

	public void sendEmailBody(UserCreationRequest userCreationRequest, User user) {
		String token = getPasswordToken(user, TokenType.USER_ACTIVATION);
	}
	@Caching(evict = {@CacheEvict(cacheNames = USER, condition = "#userUpdateRequest.getUserType() == 1", allEntries = true),
			@CacheEvict(cacheNames = OPS, condition = "#userUpdateRequest.getUserType() == 3", allEntries = true),
			@CacheEvict(cacheNames = MRCH, condition = "#userUpdateRequest.getUserType() == 2", allEntries = true)}
			)
	//@LogUpdateAudit(module = Module.USER)
	public User updateUser(UserUpdateRequest userUpdateRequest) {
		log.info("===========User Update ===========");
		User user = userRepository.findById(userUpdateRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage
						(MessageSourceService.NOT_FOUND,"User")));
		User oldUser = new User(user);
		this.setUserDetails(user, userUpdateRequest);
		User save = save(user);
		return save;
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	private void setUserDetails(User user, UserUpdateRequest userUpdateRequest) {
		BeanUtils.copyProperties(userUpdateRequest, user, "id");
		user.setUserRole(userRoleRepository.findById(userUpdateRequest.getUserRole()).get());
		user.setUserStatus(Status.valueOf(Status.class, userUpdateRequest.getUserStatus()));
	}

	public User getUser(long id) throws ResourceNotFoundException {
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage(MessageSourceService.NOT_FOUND,"User")));
	}

	public Optional<User> getUserByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}

	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByUserEmailIgnoreCase(email);
	}
	
	public Optional<User> getUserByEmailAndUserId(String email, String userId) {
		return userRepository.findByUserEmailIgnoreCaseAndUserId(email, userId);
	}
	@Cacheable(cacheNames = USER)
	public List<UserDTO> getAllUser() {
		return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream().map(UserDTO::new)
				.collect(Collectors.toList());
	}


	/* used to delete user by user Id call in MCA flow */
	@CacheEvict(cacheNames = {USER,OPS,MRCH}, allEntries = true)
	@Transactional
	public void deleteUser(String id) {
		User user = this.getUserByUserId(id)
				.orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage
						(MessageSourceService.NOT_FOUND, "User Id")));

		userRepository.deleteByUserId(id);

	}

	/* used to delete user by Id call when mca is disabled */
	@CacheEvict(cacheNames = {USER, OPS, MRCH}, allEntries = true)
	//@LogDeleteAudit(module = Module.USER)
	@Transactional
	public void deleteUser(long id) {
		User user = getUser(id);
		userRepository.deleteById(id);
	}

	public void updateToken(String userId, Date tokenExpiryDate, String token) {
		User user = this.getUserByUserId(userId).orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage(
				MessageSourceService.NOT_FOUND, "User")));
		user.setTokenExpiryDate(tokenExpiryDate);
		user.setToken(token);
		save(user);
	}

	/***
	 * Method to clear the token after user logout
	 * 
	 * @param userId
	 * @return
	 */
	public void clearToken(String userId) {
		User user = this.getUserByUserId(userId).orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage(
					MessageSourceService.NOT_FOUND,"Record")));
		user.setTokenExpiryDate(null);
		user.setToken(null);
		save(user);
		this.updateLoginHistory(userId);
	}

	public boolean hasToken(String userId, String token) {
		User user = userRepository.findByUserIdAndToken(userId, token)
				.orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage(MessageSourceService.NOT_FOUND,userId)));
		return (user.getTokenExpiryDate() != null);
	}

	/***
	 * Method to send a reset password link to the user email id.
	 * 
	 * @param email Id
	 * @return
	 */
	public void sendResetPasswordLink(String emailId, String userId, boolean isReset) {
		if (isReset) {
			Optional<Integer> stageNumber = forgotPasswordVerificationRepository.findStageNoById(userId);
			if (!stageNumber.isPresent() || stageNumber.get() != SECURITY_QUESTION_VERIFIED)
				raiseException(messageSource.getMessage
						(MessageSourceService.INVALID_STAGE,"2"));
		}
		User userFromDB = this.getUserByEmailAndUserId(emailId, userId)
				.orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage
						(MessageSourceService.INVALID, "email")));
		String token = getPasswordToken(userFromDB, TokenType.RESET_PASSWORD);
		String link = "";
		Context context = new Context();
		context.setVariable("emailId", emailId);
		context.setVariable("link", link);
		try {
			String textContext = this.templateEngine.process(RESET_PASSWORD_TEMPLATE, context);
			emailService.sendEmail("[LogibizTech] Please reset your password", textContext, emailId);
		} catch (Exception e) {
			throw new ApplicationException(messageSource.getMessage
					(MessageSourceService.ERROR_EMAIL_SEND), e);
		}
		if (isReset)
			this.updateStageNumber(userId, 0);
	}

	private String getPasswordToken(User user, TokenType tokenType) {
		Optional<PasswordResetToken> tokenOpt = passwordTokenRepository.findByUserId(user.getId(), tokenType);
		String token = UUID.randomUUID().toString();
		if (tokenOpt.isPresent()) {
			PasswordResetToken myToken = tokenOpt.get();
			myToken.setToken(token);
			myToken.setTokenDate(LocalDateTime.now());
			myToken.setTokenType(tokenType);
			passwordTokenRepository.save(myToken);
		} else
			passwordTokenRepository.save(new PasswordResetToken(token, user, LocalDateTime.now(), tokenType));
		return token;
	}

	/***
	 * Method used for forget password
	 * 
	 * @param token
	 * @return
	 */
	public void resetPassword(ChangePasswordRequest changePasswordRequest) {
		Optional<PasswordResetToken> tokenOpt = passwordTokenRepository.findByToken(changePasswordRequest.getToken(),
				TokenType.RESET_PASSWORD);

		PasswordResetToken passToken = tokenOpt
				.orElseThrow(() -> new ApplicationException(messageSource.getMessage
						(MessageSourceService.LINK_EXPIRED,"Reset password")));

		updatePassword(changePasswordRequest, passToken.getUser());
		passwordTokenRepository.delete(passToken);
	}

	/***
	 * validate the password reset link
	 * 
	 * @param token
	 */
	public void validatePasswordResetToken(String token, TokenType tokenType) {
		Optional<PasswordResetToken> tokenOpt = passwordTokenRepository.findByToken(token, tokenType);
		PasswordResetToken passToken = tokenOpt
				.orElseThrow(() -> new ApplicationException(messageSource.getMessage
						(MessageSourceService.LINK_EXPIRED, "Reset password")));
		if (isTokenExpired(passToken))
			throw new ApplicationException(messageSource.getMessage
					(MessageSourceService.LINK_EXPIRED, "Reset password"));
	}

	private Boolean isTokenExpired(PasswordResetToken passToken) {
		return passToken.getTokenDate().isBefore(LocalDateTime.now().minusHours(forgotPasswordExpiryInHr));
	}

	public void changePassword(ChangePasswordRequest changePasswordRequest) {
		User user = this.getUserByUserId(changePasswordRequest.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage
						(MessageSourceService.NOT_FOUND, "User Id")));

		if (!BCrypt.checkpw(changePasswordRequest.getOldPassword(), user.getPassword()))
			throw new ApplicationException(messageSource.getMessage
					(MessageSourceService.WRONG_PASSWORD));
		this.updatePassword(changePasswordRequest, user);
	}

	private void updatePassword(ChangePasswordRequest changePasswordRequest, User user) {
		if (changePasswordRequest.getNewPassword() == null
				|| !changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword()))
			throw new ApplicationException(messageSource.getMessage
					(MessageSourceService.WRONG_PASSWORD));
		this.checkPasswordHistory(changePasswordRequest.getUserId(), changePasswordRequest.getNewPassword());
		user.setPassword(new BCryptPasswordEncoder().encode(changePasswordRequest.getConfirmPassword()));
		save(user);
	}

	/***
	 * return the list roles & menu permission user has
	 * 
	 * @param user
	 * @return
	 */
	public RoleResponse getUserMenuPermissions(User user) {

		UserRole userRole = user.getUserRole();
		List<Map<String, String>> assignedMenu = userRole.getAssignedMenus().stream().map((menuMapped) -> {
			// return hashmap for each menu access
			Map<String, String> hashMap = new HashMap<>();
			hashMap.put(String.valueOf(menuMapped.getMenu().getId()), menuMapped.getAction());
			return hashMap;
		}).collect(Collectors.toList());

		RoleResponse roleResponse = new RoleResponse();
		roleResponse.setRoleId(userRole.getId());
		roleResponse.setRoleName(userRole.getRoleName());
		roleResponse.setAssignedMenu(assignedMenu);
		return roleResponse;
	}

	private final SecurityQuestionRepository securityQuestionRepository;

	int counter = 0;

	public List<SecurityQuestionDTO> getSecurityQuestion(String userId) {

		List<SecurityQuestionDTO> exisitngList = securityQuestionRepository.findByUserUserId(userId).stream()
				.map(SecurityQuestionDTO::new).collect(Collectors.toList());

		if (exisitngList.isEmpty())
			populateQuestions(exisitngList);

		return exisitngList;
	}

	/***
	 * Return the list of questions for security questions
	 * 
	 * @param exisitngList
	 */
	private void populateQuestions(List<SecurityQuestionDTO> exisitngList) {
		exisitngList.add(new SecurityQuestionDTO("What is the last name of your favorite musician?", ""));
		exisitngList.add(new SecurityQuestionDTO("What was the last name of your favorite teacher?", ""));
		exisitngList.add(new SecurityQuestionDTO("What was the last name of your best childhood friend?", ""));
	}

	public void saveOrUpdateSecurityQuestion(List<QuestionAnswer> questionAnswerList) {
		String userId = AppUtil.getCurrentUser();
		List<SecurityQuestion> existingQuestions = securityQuestionRepository.findByUserUserId(userId);
		User user = getUserByUserId(userId)
				.orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage
						(MessageSourceService.NOT_FOUND, "Security question")));

		if (existingQuestions.isEmpty()) {

			questionAnswerList.forEach((questionAnswer) -> {

				SecurityQuestion securityQuestion = new SecurityQuestion(questionAnswer);
				securityQuestion.setUser(user);
				securityQuestionRepository.save(securityQuestion);
			});
		} else { // Update if exist
			counter = 0;
			existingQuestions.stream().forEach((securityQesution) -> {

				QuestionAnswer questionAnswerRequest = questionAnswerList.get(counter++);
				securityQesution.setQuestion(questionAnswerRequest.getQuestion());
				securityQesution.setAnswer(questionAnswerRequest.getAnswer());
				securityQuestionRepository.save(securityQesution);
			});
		}
	}

	/***
	 * Method call if Checker reject the addition or updation
	 * 
	 * @param userId
	 */
	public void revertUser(String userId) {
		log.info("===========User reject===========");
		Optional<User> userOpt = this.getUserByUserId(userId);

		if (userOpt.isPresent()) {
			User user = userOpt.get();
			save(user);
		}
	}

	public void insertLoginHistory(String userId) {
		User user = this.getUserByUserId(userId)
				.orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage
						(MessageSourceService.NOT_FOUND, "User")));

		UserLoginHistory userLoginHistory = UserLoginHistory.builder().user(user).loggedInTime(LocalDateTime.now())
				.build();
		userLoginHistory.setInstId(AppUtil.getCurrentInstIdStr());
		userLoginHistory.setMerchId(AppUtil.getCurrentMerchantIdStr());

		this.updateLoginHistory(userLoginHistory);

	}

	public void updateLoginHistory(String userId) {
		UserLoginHistory userLoginHistory = userLoginHistoryRepository.findTop1ByUserUserIdOrderByIdDesc(userId)
				.orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage
						(MessageSourceService.NOT_FOUND,"Login record")));

		userLoginHistory.setLoggedOutTime(LocalDateTime.now());
		this.updateLoginHistory(userLoginHistory);
	}

	private void updateLoginHistory(UserLoginHistory userLoginHistory) {

		userLoginHistoryRepository.save(userLoginHistory);
	}

	public User getUserAndUserId(ForgotPasswordVerifyRequest forgotPasswordVerifyRequest) {
		User user = this.getUserByUserId(forgotPasswordVerifyRequest.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage
						(MessageSourceService.NOT_FOUND,"User")));
			this.setStageNumber(user.getUserId(), USER_ID_VERIFIED);
		return user;
	}

	public SecurityQuestion getSecurityQuestionByUser(VerifyQuestionRequest verifyQuestionRequest) {
		Optional<Integer> stageNumber = forgotPasswordVerificationRepository
				.findStageNoById(verifyQuestionRequest.getUserName());
		if (!stageNumber.isPresent() || stageNumber.get() != USER_ID_VERIFIED)
			raiseException(messageSource.getMessage
					(MessageSourceService.INVALID_STAGE, "1"));

		SecurityQuestion securityQuestion = securityQuestionRepository
				.findByQuestionAndAnswerAndUserUserId(verifyQuestionRequest.getQuestion(),
						verifyQuestionRequest.getAnswer(), verifyQuestionRequest.getUserName())
				.orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage
						(MessageSourceService.NOT_FOUND, "Answer")));
		this.updateStageNumber(verifyQuestionRequest.getUserName(), SECURITY_QUESTION_VERIFIED);
		return securityQuestion;
	}


	public void sendEmailLinks(PasswordResendRequest passwordResendRequest, boolean isResend) {

		log.info("===========Resend Email ===========");
		Arrays.stream(passwordResendRequest.getUserItems()).forEach((userItem) -> {
			User user = this.getUser(userItem);
			this.sendResetPasswordLink(user.getUserEmail(), user.getUserId(), isResend);
		});
	}


	public Boolean isSecurityQuestionUpdated(String userName) {
		//List<SecurityQuestion> questionAnswer = this.securityQuestionRepository.findByUserUserId(userName);
		//return (questionAnswer != null && !questionAnswer.isEmpty());
		return true;
	}



	private static ApplicationException raiseException(String message) {
		throw new ApplicationException(message);
	}

	private void saveUserPassword(String password, String userId) {
		PasswordHistory passwordHistory = new PasswordHistory();
		passwordHistory.setPassword(new BCryptPasswordEncoder().encode(password));
		passwordHistory.setUserId(userId);
		passwordHistoryRepository.save(passwordHistory);
	}

	private void checkPasswordHistory(String userId, String requestedPassword) {
		List<String> oldPasswords = passwordHistoryRepository.getPasswordsHistoryById(userId);
		for (int i = 0; i < oldPasswords.size(); i++) {
			if (BCrypt.checkpw(requestedPassword, oldPasswords.get(i)))
				raiseException(messageSource.getMessage
						(MessageSourceService.PASSWORD_REUSE));
		}

		if (oldPasswords.size() >= 4) {
			{
				String oldestPassword = oldPasswords.get(0);
				passwordHistoryRepository.deletePassword(oldestPassword);
			}
		}
		saveUserPassword(requestedPassword, userId);

	}

	private void setStageNumber(String userId, int stage) {
		ForgotPasswordVerification userRecord = forgotPasswordVerificationRepository.findByUserId(userId);
		if (userRecord != null) {
			userRecord.setPassedStageNo(stage);
			userRecord.setUserId(userId);
			forgotPasswordVerificationRepository.save(userRecord);
		} else {
			ForgotPasswordVerification securityStage = new ForgotPasswordVerification();
			securityStage.setPassedStageNo(stage);
			securityStage.setUserId(userId);
			forgotPasswordVerificationRepository.save(securityStage);
		}

	}

	private void updateStageNumber(String userId, int stage) {
		ForgotPasswordVerification securityStage = forgotPasswordVerificationRepository.findByUserId(userId);
		securityStage.setPassedStageNo(stage);
		forgotPasswordVerificationRepository.save(securityStage);
	}

	@CacheEvict(cacheNames = "user-menu", key = "#userId")
	public void removeUserMenuCache(String userId) {
		System.out.println("remove cache user-menu=" + userId);

	}

}
