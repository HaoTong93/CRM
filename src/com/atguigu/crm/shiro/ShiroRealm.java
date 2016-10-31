package com.atguigu.crm.shiro;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atguigu.crm.entity.User;
import com.atguigu.crm.service.UserService;

@Component
public class ShiroRealm extends AuthorizingRealm{
	
	@Autowired
	private UserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken)token;
		String username = upToken.getUsername();
		
		User user = userService.getUserByName(username);
		if(user == null){
			throw new UnknownAccountException("用户不存在.");
		}
		if(user.getEnabled() != 1){
			throw new LockedAccountException("账户被锁定");
		}
		Object principal = user;
		Object hashedCredentials = user.getPassword();
		ByteSource credentialsSalt =ByteSource.Util.bytes(user.getSalt());
		String realmName = getName();
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, hashedCredentials, credentialsSalt, realmName);
		return info;
	}
	
	@PostConstruct
	private void initCredentialsMatcher() {
		System.out.println("initCredentialsMatcher...");
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("MD5");
		matcher.setHashIterations(1024);
		
		setCredentialsMatcher(matcher);
	}

}
