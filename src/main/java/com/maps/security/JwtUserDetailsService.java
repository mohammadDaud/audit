//package com.maps.security;
//import java.util.Arrays;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.maps.Utility.JsonUtil;
//import com.maps.api.admin.service.UserService;
//import com.maps.model.RoleResponse;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class JwtUserDetailsService implements UserDetailsService {
//
//	private final UserService userService;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//		String[] usernameInstIdUserType = StringUtils.split(username, "|");
//
//		com.maps.api.admin.entity.User userEntity = retrieveUser(usernameInstIdUserType);
//
//		if (userEntity != null && usernameInstIdUserType[0].equals(userEntity.getUserId())) {
//			RoleResponse userMenuPermissions = userService.getUserMenuPermissions(userEntity);
//
//			return new org.springframework.security.core.userdetails.User(userEntity.getUserId(),
//					userEntity.getPassword(), Arrays.asList(new SimpleGrantedAuthority(JsonUtil.toString(userMenuPermissions))));
//
//		}
//		throw new UsernameNotFoundException("User not found with userId: " + usernameInstIdUserType[0]);
//
//	}
//
//	private com.maps.api.admin.entity.User retrieveUser(String[] usernameAndInst) {
//
//		com.maps.api.admin.entity.User userEntity = null;
//		if(usernameAndInst.length == 3 && !usernameAndInst[1].equals("null") && 
//				!usernameAndInst[1].isEmpty() && usernameAndInst[2].equals("3")) {// for ops user
////			userEntity = userService.getUserByUserIdAndUserTypeAndInstId(usernameAndInst[0], 
////					usernameAndInst[2], usernameAndInst[1])
////					.orElseThrow(()-> new UsernameNotFoundException("User not found with userId: " + usernameAndInst[0]));
//		}
//		else {
//			userEntity = userService.getUserByUserId(usernameAndInst[0])
//					.orElseThrow(()-> new UsernameNotFoundException("User not found with userId: " + usernameAndInst[0]));
//		}
//		return userEntity;
//	}
//}
