package sheffield.service;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sheffield.mbg.dao.CommodityMapper;
import sheffield.mbg.pojo.Commodity;

@Service(value = "commodityService")
public class CommodityServiceImpl implements CommodityService {

	@Resource
	private CommodityMapper commodityMapper;
	@Resource
	private RedisTemplate redisTemplate3;

	public CommodityServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	@Caching(evict = {
			@CacheEvict(value = "redisCacheManager", key = "'redis_selectByCommodityId_'+#commodityid", beforeInvocation = false),
			@CacheEvict(value = "redisCacheManager", key = "'redis_image_selectByCommodityId_'+#commodityid,", beforeInvocation = false) })
	// 需要把图片缓存一起删除
	public int deleteByPrimaryKey(Long commodityid) {
		return commodityMapper.deleteByPrimaryKey(commodityid);
	}

	// Cacheable用于查询，CachePut用于插入和修改，CacheEvict用于删除
	@Override
	@Transactional
	// useGeneratedKeys="true" keyProperty="commodityid"
	@CachePut(value = "redisCacheManager", key = "'redis_selectByCommodityId_'+#result.commodityid")
	public Commodity insertThenCachePut(Commodity record) {
		commodityMapper.insert(record);
		return commodityMapper.selectByPrimaryKey(record.getCommodityid());

	}

	@Override
	public Commodity selectByPrimaryKey(Long commodityid) {
		// TODO Auto-generated method stub
		return commodityMapper.selectByPrimaryKey(commodityid);
	}

	@Override
	public List<Commodity> selectAll() {
		// TODO Auto-generated method stub
		return commodityMapper.selectAll();
	}

	@Override
	@Transactional
	public int updateByPrimaryKey(Commodity record) {
		// TODO Auto-generated method stub
		return commodityMapper.updateByPrimaryKey(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	@CachePut(value = "redisCacheManager", key = "'redis_selectByCommodityId_'+#result.commodityid")
	public Commodity updateForVersionByPrimaryKeyAndVersion(Commodity record, long version) {
		record.setVersion(version + 1);
		commodityMapper.updateByPrimaryKey(record);
		return commodityMapper.selectByPrimaryKey(record.getCommodityid());
	}

	@Override
	public long returnPrimaryKeyByCommodity(Commodity record) {
		// 判断数据库表是否已有这个商品记录
		List<Commodity> list = commodityMapper.selectAll();
		Iterator<Commodity> iterator = list.iterator();
		Long findid = (long) 0;
		while (iterator.hasNext()) {
			Commodity commodity = iterator.next();
			String oldname = commodity.getCommodityname();
			String olddate = commodity.getCommoditydate();
			long oldownerid = commodity.getCommodityownerid();
			String olddes = commodity.getCommoditydescription();
			boolean condition1 = record.getCommodityname().equals(oldname);
			boolean condition2 = record.getCommoditydate().equals(olddate);
			boolean condition3 = (record.getCommodityownerid()) == oldownerid ? true : false;
			boolean condition4 = record.getCommoditydescription().equals(olddes);
			if (((condition1 && condition2) && condition3) && condition4) {
				findid = commodity.getCommodityid();
			}
		}
		return findid;
	}

	@Override
	//@Cacheable(value = "redisCacheManager", key = "'redis_selectByCommodityId_'+#commodityownerid")
	public List<Commodity> selectByOwnerId(Long commodityownerid) {
		// TODO Auto-generated method stub
		return commodityMapper.selectByOwnerId(commodityownerid);
	}

	@Override
	public List<Commodity> selectByUsingFullTextOrderBySales(String searchcontent, Integer limitoffset,
			Integer limitsize) {
		// TODO Auto-generated method stub
		return commodityMapper.selectByUsingFullTextOrderBySales(searchcontent, limitoffset, limitsize);
	}

	@Override
	public List<Commodity> selectByUsingFullTextOrderByDate(String searchcontent, Integer limitoffset,
			Integer limitsize) {
		// TODO Auto-generated method stub
		return commodityMapper.selectByUsingFullTextOrderByDate(searchcontent, limitoffset, limitsize);
	}

	@Override
	public List<Commodity> selectByUsingFullTextOrderByPriceDESC(String searchcontent, Integer limitoffset,
			Integer limitsize) {
		// TODO Auto-generated method stub
		return commodityMapper.selectByUsingFullTextOrderByPriceDESC(searchcontent, limitoffset, limitsize);
	}

	@Override
	public List<Commodity> selectByUsingFullTextOrderByPriceASC(String searchcontent, Integer limitoffset,
			Integer limitsize) {
		// TODO Auto-generated method stub
		return commodityMapper.selectByUsingFullTextOrderByPriceASC(searchcontent, limitoffset, limitsize);
	}

	@Override
	public List<Commodity> selectAllBySales(Integer limitoffset, Integer limitsize) {
		// TODO Auto-generated method stub
		return commodityMapper.selectAllBySales(limitoffset, limitsize);
	}

	@Override
	public Double selectRateBySearch(Long commodityid) {
		// TODO Auto-generated method stub
		return commodityMapper.selectRateBySearch(commodityid);
	}

	@Override
	public Integer countBySearch(String searchcontent) {
		return commodityMapper.countBySearch(searchcontent);
	}

	@Override
	//@Cacheable(value = "redisCacheManager", key = "'redis_countAll'")
	public Integer countAll() {
		// TODO Auto-generated method stub
		return commodityMapper.countAll();
	}

	@Override
	@Cacheable(value = "redisCacheManager", key = "'redis_selectForHomePage_'+#limiteoffset+#limitesize")
	public List<Commodity> selectForHomePage(Integer limitoffset, Integer limitsize) {
		// TODO Auto-generated method stub
		return commodityMapper.selectForHomePage(limitoffset, limitsize);
	}

	// 搜索条件较多的结果不适合缓存
	@Override
	public List<Commodity> searchByPriceRange(String searchcontent,Double fromprice, Double toprice, Integer limitoffset,
			Integer limitsize) {
		// TODO Auto-generated method stub
		return commodityMapper.searchByPriceRange(searchcontent,fromprice, toprice, limitoffset, limitsize);
	}

}
