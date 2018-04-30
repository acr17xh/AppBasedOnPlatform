package sheffield.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sheffield.mbg.dao.PermissionMapper;
import sheffield.mbg.pojo.Permission;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

	@Resource
	private PermissionMapper permissiondao;

	public PermissionServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int deleteByPrimaryKey(Long userid) {
		// TODO Auto-generated method stub
		return permissiondao.deleteByPrimaryKey(userid);
	}

	@Override
	public int insert(Permission record) {
		// TODO Auto-generated method stub
		return permissiondao.insert(record);
	}

	@Override
	public Permission selectByPrimaryKey(Long userid) {
		// TODO Auto-generated method stub
		return permissiondao.selectByPrimaryKey(userid);
	}

	@Override
	public List<Permission> selectAll() {
		// TODO Auto-generated method stub
		return permissiondao.selectAll();
	}

	@Override
	public int updateByPrimaryKey(Permission record) {
		// TODO Auto-generated method stub
		return permissiondao.updateByPrimaryKey(record);
	}

	@Override
	public List<String> findRoles(Long userid) {
		// TODO Auto-generated method stub
		return permissiondao.findRoles(userid);
	}

	@Override
	public List<String> findPermissions(Long userid) {
		// TODO Auto-generated method stub
		return permissiondao.findPermissions(userid);
	}

}
