package sheffield.mbg.dao;

import java.util.List;

import sheffield.mbg.pojo.Review;
import sheffield.mbg.pojo.Review2;

public interface ReviewMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review
     *
     * @mbg.generated Mon Dec 11 17:36:45 GMT 2017
     */
    int deleteByPrimaryKey(Long reviewid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review
     *
     * @mbg.generated Mon Dec 11 17:36:45 GMT 2017
     */
    int insert(Review record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review
     *
     * @mbg.generated Mon Dec 11 17:36:45 GMT 2017
     */
    Review selectByPrimaryKey(Long reviewid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review
     *
     * @mbg.generated Mon Dec 11 17:36:45 GMT 2017
     */
    List<Review> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review
     *
     * @mbg.generated Mon Dec 11 17:36:45 GMT 2017
     */
    int updateByPrimaryKey(Review record);
    
    List<Review2> selectByCommodityIdWithUserName(Long commodityid);
    
    
}