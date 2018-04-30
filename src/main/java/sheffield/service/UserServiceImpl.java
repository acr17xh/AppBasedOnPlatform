package sheffield.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sheffield.mbg.dao.UserMapper;
import sheffield.mbg.pojo.User;

@Service("userService")
public class UserServiceImpl implements UserService {
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);
	@Resource
	private UserMapper userdao;

	public UserServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int deleteByPrimaryKey(Long userid) {
		// TODO Auto-generated method stub

		return userdao.deleteByPrimaryKey(userid);
	}

	@Override
	public int insert(User record) {
		// TODO Auto-generated method stub
		return userdao.insert(record);

	}

	@Override
	//@Cacheable(value = "redisCacheManager", key = "'redis_cache_select_user_by_id_'+#userid")
	public User selectByPrimaryKey(Long userid) {
		// TODO Auto-generated method stub
		return userdao.selectByPrimaryKey(userid);
	}

	@Override
	public List<User> selectAll() {
		// TODO Auto-generated method stub
		return userdao.selectAll();
	}

	@Override
	public int updateByPrimaryKey(User record) {
		// TODO Auto-generated method stub
		return userdao.updateByPrimaryKey(record);
	}

	@Override
	//@Cacheable(value = "redisCacheManager", key = "'redis_cache_select_user_by_name_'+#username")
	public User selectByUserName(String username) {
		// TODO Auto-generated method stub
		return userdao.selectByUserName(username);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public int updateUserWithLock(Long userid, User newuser) {
		// TODO Auto-generated method stub
		// User olduser = userdao.selectUserWithLock(userid);
		logger.info("selectUserWithLock executed");
		newuser.setUserid(userid);
		int a = userdao.updateByPrimaryKey(newuser);
		logger.info("updateByPrimaryKey executed");
		return a;
	}

	@Override
	public Integer selectExistsByUserName(String username) {
		// TODO Auto-generated method stub
		return userdao.selectExistsByUserName(username);
	}

	@Override
	public List<Long> selectAllUserId() {
		// TODO Auto-generated method stub
		return userdao.selectAllUserId();
	}

}
