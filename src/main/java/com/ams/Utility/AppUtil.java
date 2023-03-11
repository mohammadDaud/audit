package com.ams.Utility;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.ams.model.RoleResponse;

public class AppUtil {

	/***
	 * Spring security method to give the user Id of logged in User
	 * 
	 * @return
	 */
	public static String getCurrentUser() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
	}

	public static long getCurrentInstId() {

		RoleResponse fromString = getUserRole();
		return fromString.getInstId();
	}
	
	public static String getCurrentInstIdStr() {

		RoleResponse fromString = getUserRole();
		return fromString.getInstIdStr();
	}

	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			 ip =  request.getHeader("X-Forwarded-For");
		}
		return ip;
	}

	public static String padLeft(String string, int length) {
		return String.format("%" + length + "s", string).replace(" ", "0");
	}

	public static String mask(String value, int start, int end) {
		return value.replaceAll("\\b(\\d{" + start + "})\\d+(\\d{" + end + "})", "$1*******$2");
	}

	public static long getCurrentMerchantId() {

		RoleResponse fromString = getUserRole();
		return fromString.getMerchId();
	}
	
	public static String getCurrentMerchantIdStr() {

		RoleResponse fromString = getUserRole();
		return fromString.getMerchIdStr();
	}

	/**
	 * @return
	 */
	private static RoleResponse getUserRole() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		RoleResponse fromString = null;
		if (principal instanceof UserDetails) {
			Collection<? extends GrantedAuthority> authorities = ((UserDetails) principal).getAuthorities();

			Optional<? extends GrantedAuthority> findFirst = authorities.stream().findFirst();
			if (findFirst.isPresent()) {
				fromString = JsonUtil.fromString(findFirst.get().toString(), RoleResponse.class);
			}
		}
		return fromString;
	}

	public static boolean isMobileNumber(String mobNumber) {
		return Pattern.compile("^(\\+\\d{3}?)?(\\d{3}?){2}\\d{3}|([0]{2}[0-9]{12})$").matcher(mobNumber).matches();

	}

	public static boolean isNumericIBAN(String accNumber) {
		return Pattern.compile("^([A-Z]|\\d){8}\\d{16}$").matcher(accNumber).matches();
	}

	public static boolean isNumericAccDebit(String accNumber) {
		return Pattern.compile("^\\d{15,19}$").matcher(accNumber).matches();
	}

	public static boolean isNumeric(String number) {
		return Pattern.compile("\\d+").matcher(number).matches();
	}

	public static boolean isAlphaNumeric(String number) {
		return Pattern.compile("^[a-zA-Z0-9]+$").matcher(number).matches();
	}
	
	public static boolean isEmail(String email) {
		return Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$").matcher(email).matches();
	}

	public static List<String> getValidMonths() {
		return Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
	}

	public static List<Integer> getValidActionCode() {
		return Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	}

	public static boolean isURLValid(String url) {
		try {
			new URL(url).toURI();
			return true;
		}

		catch (Exception e) {
			return false;
		}
	}

	public static boolean luhnCheck(String cardNumber) {
	
	if(cardNumber == null || isBlank(cardNumber)) 
		return false;

	int card_num_length = cardNumber.length();
	int parity = card_num_length % 2;

    // Loop through each digit and do the maths
	
	char[] charArray = cardNumber.toCharArray();
    int total = 0;
    for (int i = 0; i < card_num_length; i++) {
        int digit = Integer.parseInt(String.valueOf(charArray[i]), 10);
        // Multiply alternate digits by two
        if (i % 2 == parity) {
            digit *= 2;
            // If the sum is two digits, add them together (in effect)
            if (digit > 9) {
                digit -= 9;
            }
        }
        // Total up the digits
        total = total + digit;
    }

    // If the total mod 10 equals 0, the number is valid
    return total % 10 == 0;
    //]]>
}
	public static boolean isOps() {
		if (getCurrentInstId() != 0 && getCurrentMerchantId() == 0 ) {
			return true;}
		return false;
	}
	public static boolean isMerchant() {
		if (getCurrentInstId() != 0 && getCurrentMerchantId() != 0 ) {
			return true;}
		return false;
	}
	public static boolean isAdmin() {
		if (getCurrentInstId() == 0 && getCurrentMerchantId() == 0 ) {
			return true;}
		return false;
	}
	
	public static boolean isUserLoggedIn() {
		if (!getCurrentUser().equals("anonymousUser")) {
			return true;
		}
		return false;
	}
}
