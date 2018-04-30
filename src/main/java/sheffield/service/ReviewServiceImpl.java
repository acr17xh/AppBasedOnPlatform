package sheffield.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import sheffield.mbg.dao.ReviewMapper;
import sheffield.mbg.pojo.Review;
import sheffield.mbg.pojo.Review2;

@Service(value = "reviewService")
public class ReviewServiceImpl implements ReviewService {

	@Resource
	private ReviewMapper reviewMapper;

	public ReviewServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int deleteByPrimaryKey(Long reviewid) {
		// TODO Auto-generated method stub
		return reviewMapper.deleteByPrimaryKey(reviewid);
	}

	@Override
	public int insert(Review record) {
		// TODO Auto-generated method stub
		return reviewMapper.insert(record);
	}

	@Override
	public Review selectByPrimaryKey(Long reviewid) {
		// TODO Auto-generated method stub
		return reviewMapper.selectByPrimaryKey(reviewid);
	}

	@Override
	public List<Review> selectAll() {
		// TODO Auto-generated method stub
		return reviewMapper.selectAll();
	}

	@Override
	public int updateByPrimaryKey(Review record) {
		// TODO Auto-generated method stub
		return reviewMapper.updateByPrimaryKey(record);
	}

	@Override
	// @Cacheable(value = "redisCacheManager", key =
	// "'redis_review_selectByCommodityIdWithUserName_'+#commodityid")
	public List<Review2> selectByCommodityIdWithUserName(Long commodityid) {
		// TODO Auto-generated method stub
		return reviewMapper.selectByCommodityIdWithUserName(commodityid);
	}

}
