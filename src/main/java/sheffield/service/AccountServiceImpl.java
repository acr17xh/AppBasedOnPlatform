package sheffield.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sheffield.mbg.dao.AccountMapper;
import sheffield.mbg.pojo.Account;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Resource
	private AccountMapper accountMapper;

	public AccountServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int deleteByPrimaryKey(Long accountid) {
		// TODO Auto-generated method stub
		return accountMapper.deleteByPrimaryKey(accountid);
	}

	@Override
	public int insert(Account record) {
		// TODO Auto-generated method stub
		return accountMapper.insert(record);
	}

	@Override
	public Account selectByPrimaryKey(Long accountid) {
		// TODO Auto-generated method stub
		return accountMapper.selectByPrimaryKey(accountid);
	}

	@Override
	public List<Account> selectAll() {
		// TODO Auto-generated method stub
		return accountMapper.selectAll();
	}

	@Override
	public int updateByPrimaryKey(Account record) {
		// TODO Auto-generated method stub
		return accountMapper.updateByPrimaryKey(record);
	}

	@Override
	public Account selectByUserId(long userid) {
		// TODO Auto-generated method stub
		return accountMapper.selectByUserId(userid);
	}

}
