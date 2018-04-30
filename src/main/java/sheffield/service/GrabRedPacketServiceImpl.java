package sheffield.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sheffield.mbg.dao.GrabredpacketMapper;
import sheffield.mbg.pojo.Grabredpacket;

@Service("grabRedPacketService")
public class GrabRedPacketServiceImpl implements GrabRedPacketService {

	@Resource
	private GrabredpacketMapper grabredpacketMapper;

	public GrabRedPacketServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int deleteByPrimaryKey(Long grabredpacketid) {
		// TODO Auto-generated method stub
		return grabredpacketMapper.deleteByPrimaryKey(grabredpacketid);
	}

	@Override
	public int insert(Grabredpacket record) {
		// TODO Auto-generated method stub
		return grabredpacketMapper.insert(record);
	}

	@Override
	public Grabredpacket selectByPrimaryKey(Long grabredpacketid) {
		// TODO Auto-generated method stub
		return grabredpacketMapper.selectByPrimaryKey(grabredpacketid);
	}

	@Override
	public List<Grabredpacket> selectAll() {
		// TODO Auto-generated method stub
		return grabredpacketMapper.selectAll();

	}

	@Override
	public int updateByPrimaryKey(Grabredpacket record) {
		// TODO Auto-generated method stub
		return grabredpacketMapper.updateByPrimaryKey(record);
	}

}
