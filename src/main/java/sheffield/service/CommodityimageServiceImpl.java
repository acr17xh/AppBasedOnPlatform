package sheffield.service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sheffield.mbg.dao.CommodityimageMapper;
import sheffield.mbg.pojo.Commodityimage;

@Service(value = "commodityimageService")
public class CommodityimageServiceImpl implements CommodityimageService {

	@Resource
	private CommodityimageMapper commodityimageMapper;

	public CommodityimageServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int deleteByPrimaryKey(Long commodityimgid) {
		// TODO Auto-generated method stub
		return commodityimageMapper.deleteByPrimaryKey(commodityimgid);
	}

	@Override
	public int insert(Commodityimage record) {
		// TODO Auto-generated method stub
		return commodityimageMapper.insert(record);
	}

	@Override
	public Commodityimage selectByPrimaryKey(Long commodityimgid) {
		// TODO Auto-generated method stub
		return commodityimageMapper.selectByPrimaryKey(commodityimgid);
	}

	@Override
	public List<Commodityimage> selectAll() {
		// TODO Auto-generated method stub
		return commodityimageMapper.selectAll();
	}

	@Override
	public int updateByPrimaryKey(Commodityimage record) {
		// TODO Auto-generated method stub
		return commodityimageMapper.updateByPrimaryKey(record);
	}

	@Override
	@Transactional
	@CachePut(value = "redisCacheManager", key = "'redis_image_selectByCommodityId_'+#commodityid")
	public List<Commodityimage> insertMultipleImagesForCommodity(LinkedList<Commodityimage> list, Long commodityid) {
		// TODO Auto-generated method stub
		Iterator<Commodityimage> iterator = list.iterator();
		while (iterator.hasNext()) {
			Commodityimage commodityimage = iterator.next();
			commodityimageMapper.insert(commodityimage);
		}
		return list;
	}

	@Override
	@Cacheable(value = "redisCacheManager", key = "'redis_image_selectByCommodityId_'+#commodityid")
	public List<Commodityimage> selectByCommodityId(Long commodityid) {
		// TODO Auto-generated method stub
		return commodityimageMapper.selectByCommodityId(commodityid);
	}

	@Override
	@Transactional
	@CachePut(value = "redisCacheManager", key = "'redis_image_selectByCommodityId_'+#commodityid")
	public List<Commodityimage> deleteByCommodityIdThenInsertMultipleImagesForCommodity(Long commodityid,
			LinkedList<Commodityimage> list) {
		commodityimageMapper.deleteByCommodityId(commodityid);
		Iterator<Commodityimage> iterator = list.iterator();
		while (iterator.hasNext()) {
			Commodityimage commodityimage = iterator.next();
			commodityimageMapper.insert(commodityimage);
		}
		return list;

	}

	@Override
	public List<Long> selectImgKeysByCommodityId(Long commodityid) {
		// TODO Auto-generated method stub
		return commodityimageMapper.selectImgKeysByCommodityId(commodityid);
	}

}
