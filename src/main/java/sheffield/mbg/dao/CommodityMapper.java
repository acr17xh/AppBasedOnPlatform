package sheffield.mbg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import sheffield.mbg.pojo.Commodity;

public interface CommodityMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table commodity
	 *
	 * @mbg.generated Thu Dec 14 14:37:53 GMT 2017
	 */
	int deleteByPrimaryKey(Long commodityid);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table commodity
	 *
	 * @mbg.generated Thu Dec 14 14:37:53 GMT 2017
	 */
	int insert(Commodity record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table commodity
	 *
	 * @mbg.generated Thu Dec 14 14:37:53 GMT 2017
	 */
	Commodity selectByPrimaryKey(Long commodityid);

	Commodity selectByCommodityName(String commodityname);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table commodity
	 *
	 * @mbg.generated Thu Dec 14 14:37:53 GMT 2017
	 */
	List<Commodity> selectAll();

	List<Commodity> selectByOwnerId(Long commodityownerid);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table commodity
	 *
	 * @mbg.generated Thu Dec 14 14:37:53 GMT 2017
	 */
	int updateByPrimaryKey(Commodity record);

	int updateForVersionByPrimaryKeyAndVersion(double commodityquantity, long commodityid, long version);

	List<Commodity> selectByUsingFullTextOrderBySales(@Param("searchcontent") String searchcontent,
			@Param("limitoffset") Integer limitoffset, @Param("limitsize") Integer limitsize);

	List<Commodity> selectByUsingFullTextOrderByDate(@Param("searchcontent") String searchcontent,
			@Param("limitoffset") Integer limitoffset, @Param("limitsize") Integer limitsize);

	List<Commodity> selectByUsingFullTextOrderByPriceDESC(@Param("searchcontent") String searchcontent,
			@Param("limitoffset") Integer limitoffset, @Param("limitsize") Integer limitsize);

	List<Commodity> selectByUsingFullTextOrderByPriceASC(@Param("searchcontent") String searchcontent,
			@Param("limitoffset") Integer limitoffset, @Param("limitsize") Integer limitsize);

	List<Commodity> selectAllBySales(@Param("limitoffset") Integer limitoffset, @Param("limitsize") Integer limitsize);

	List<Commodity> selectForHomePage(@Param("limitoffset") Integer limitoffset, @Param("limitsize") Integer limitsize);

	Double selectRateBySearch(Long commodityid);

	Integer countBySearch(String searchcontent);

	Integer countAll();

	List<Commodity> searchByPriceRange(@Param("searchcontent") String searchcontent,
			@Param("fromprice") Double fromprice, @Param("toprice") Double toprice,
			@Param("limitoffset") Integer limitoffset, @Param("limitsize") Integer limitsize);

}