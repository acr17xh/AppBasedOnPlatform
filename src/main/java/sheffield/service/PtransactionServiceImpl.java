/**
 * 
 */
package sheffield.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sheffield.mbg.dao.PtransactionMapper;
import sheffield.mbg.pojo.Ptransaction;

/**
 * @author Redfiska
 *
 */
@Service("ptransactionService")
public class PtransactionServiceImpl implements PtransactionService {
	@Resource
	private PtransactionMapper ptransactionMapper;

	/**
	 * 
	 */
	public PtransactionServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sheffield.service.PtransactionService#selectByUserId(java.lang.Long)
	 */
	@Override
	public List<Ptransaction> selectByUserId(Long userid) {
		// TODO Auto-generated method stub
		return ptransactionMapper.selectByUserId(userid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sheffield.service.PtransactionService#deleteByPrimaryKey(java.lang.Long)
	 */
	@Override
	public int deleteByPrimaryKey(Long ptransactionid) {
		// TODO Auto-generated method stub
		return ptransactionMapper.deleteByPrimaryKey(ptransactionid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sheffield.service.PtransactionService#insert(sheffield.mbg.pojo.Ptransaction)
	 */
	@Override
	public int insert(Ptransaction record) {
		// TODO Auto-generated method stub
		return ptransactionMapper.insert(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sheffield.service.PtransactionService#selectByPrimaryKey(java.lang.Long)
	 */
	@Override
	public Ptransaction selectByPrimaryKey(Long ptransactionid) {
		// TODO Auto-generated method stub
		return ptransactionMapper.selectByPrimaryKey(ptransactionid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sheffield.service.PtransactionService#selectAll()
	 */
	@Override
	public List<Ptransaction> selectAll() {
		// TODO Auto-generated method stub
		return ptransactionMapper.selectAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sheffield.service.PtransactionService#updateByPrimaryKey(sheffield.mbg.pojo.
	 * Ptransaction)
	 */
	@Override
	public int updateByPrimaryKey(Ptransaction record) {
		// TODO Auto-generated method stub
		return ptransactionMapper.updateByPrimaryKey(record);
	}

}
