package sheffield.utils;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import sheffield.mbg.pojo.User;
import sheffield.service.UserService;

@Component
public class ShiroUserUtils {

	@Resource
	private UserService userService;

	public ShiroUserUtils() {
		// TODO Auto-generated constructor stub
	}

	public User getCurrentUser() {
		Subject subject = SecurityUtils.getSubject();
		String currentusername = (String) subject.getPrincipal();
		User currentuser = userService.selectByUserName(currentusername);
		return currentuser;

	}

}
