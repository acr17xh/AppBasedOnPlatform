package sheffield.activemq;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConverter;

import sheffield.mbg.pojo.Account;
import sheffield.mbg.pojo.User;
import sheffield.service.AccountService;
import sheffield.service.UserService;
import sheffield.utils.ShiroUserUtils;

public class ConsumerUserMessageListener implements MessageListener {

	@Resource
	private UserService userService;
	@Resource
	private AccountService accountService;
	@Resource
	private MessageConverter messageConverter;
	@Resource
	private ShiroUserUtils shiroUserUtils;

	private static final String hashAlgorithmName = "MD5";
	private static final int hashIterations = 5;
	private static final double defaultCredit = 0;
	private static final double defaultPeanut = 1000;

	public ConsumerUserMessageListener() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onMessage(Message arg0) {

		if (arg0 instanceof ObjectMessage) {
			ObjectMessage objMessage = (ObjectMessage) arg0;
			try {
				// Object obj = objMessage.getObject();
				// User user = (User) obj;
				User user = (User) messageConverter.fromMessage(objMessage);
				String originalPassword = user.getUserpassword();
				String credentials = user.getUsername();
				ByteSource credentialsSalt = ByteSource.Util.bytes(credentials);
				SimpleHash encryptPassword = new SimpleHash(hashAlgorithmName, originalPassword, credentialsSalt,
						hashIterations);
				String pswd = encryptPassword.toString();
				user.setUserpassword(pswd);
				// 新建用户
				userService.insert(user);
				// 新建账户,初始信用0，货币1000
				Account account = new Account();
				account.setUserid(userService.selectByUserName(user.getUsername()).getUserid());
				account.setCredit(defaultCredit);
				account.setPeanut(defaultPeanut);
				accountService.insert(account);

			} catch (JMSException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

}
