package sheffield.service;

import java.security.KeyStore.PrivateKeyEntry;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sheffield.mbg.dao.AddressMapper;
import sheffield.mbg.pojo.Address;

@Service("addressService")
public class AddressServiceImpl implements AddressService {

	@Resource
	private AddressMapper addressMapper;

	public AddressServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int deleteByPrimaryKey(Long addressid) {
		// TODO Auto-generated method stub
		return addressMapper.deleteByPrimaryKey(addressid);
	}

	@Override
	public int insert(Address record) {
		// TODO Auto-generated method stub
		return addressMapper.insert(record);
	}

	@Override
	public Address selectByPrimaryKey(Long addressid) {
		// TODO Auto-generated method stub
		return addressMapper.selectByPrimaryKey(addressid);
	}

	@Override
	public List<Address> selectAll() {
		// TODO Auto-generated method stub
		return addressMapper.selectAll();
	}

	@Override
	public int updateByPrimaryKey(Address record) {
		// TODO Auto-generated method stub
		return addressMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Address> selectByUserId(long userid) {
		// TODO Auto-generated method stub
		return addressMapper.selectByUserId(userid);
	}

	@Override
	public Address selectByAddressDescription(String addressdescription) {
		// TODO Auto-generated method stub
		return addressMapper.selectByAddressDescription(addressdescription);
	}

}
