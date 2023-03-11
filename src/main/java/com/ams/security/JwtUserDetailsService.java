package com.ams.security;
import java.util.Arrays;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ams.Utility.JsonUtil;
import com.ams.api.admin.entity.User;
import com.ams.api.admin.service.UserService;
import com.ams.model.RoleResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

	private final UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


		User userEntity = retrieveUser(username);

		if (userEntity != null ) {
			RoleResponse userMenuPermissions = userService.getUserMenuPermissions(userEntity);

			return new org.springframework.security.core.userdetails.User(userEntity.getUserId(),
					userEntity.getPassword(), Arrays.asList(new SimpleGrantedAuthority(JsonUtil.toString(userMenuPermissions))));

		}
		throw new UsernameNotFoundException("User not found with userId: " + username);

	}

	private User retrieveUser(String username) {

		User userEntity = null;
			userEntity = userService.getUserByUserId(username)
					.orElseThrow(()-> new UsernameNotFoundException("User not found with userId: " + username));
		return userEntity;
	}
}
