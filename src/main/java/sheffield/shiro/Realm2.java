package sheffield.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import sheffield.mbg.pojo.User;
import sheffield.service.PermissionService;
import sheffield.service.UserService;
import sheffield.service.UserServiceImpl;

public class Realm2 extends AuthorizingRealm {

	private static Logger logger = Logger.getLogger(Realm2.class);

	@Resource
	private UserService userService;
	@Resource
	private PermissionService permissionServie;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		// TODO Auto-generated method stub
		String username = arg0.getPrimaryPrincipal().toString();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		User user = userService.selectByUserName(username);
		List<String> list = permissionServie.findRoles(user.getUserid());
		Set<String> roles = new HashSet<String>();
		roles.addAll(list);
		List<String> list2 = permissionServie.findPermissions(user.getUserid());
		Set<String> permissions = new HashSet<String>();
		permissions.addAll(list2);
		info.setRoles(roles);
		info.setStringPermissions(permissions);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		// TODO Auto-generated method stub
		UsernamePasswordToken token = (UsernamePasswordToken) arg0;
		String username = token.getUsername();
		User user = userService.selectByUserName(username);
		if (user == null) {
			return null;
		} else {
			Object principal = user;
			String credentials = user.getUserpassword();
			ByteSource credentialsSalt = ByteSource.Util.bytes(username);
			SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), credentials,
					credentialsSalt, this.getName());
			logger.info("Realm2 doGetAuthenticationInfo, return SimpleAuthenticationInfo");
			return authenticationInfo;

		}

	}

	// SimpleHash 可以盐值加密
	// ByteSource.Util.bytes("username");

}
