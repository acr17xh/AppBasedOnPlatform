package sheffield.mbg.dao;

import java.util.List;
import sheffield.mbg.pojo.Ptransaction;

public interface PtransactionMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table ptransaction
	 *
	 * @mbg.generated Thu Feb 22 15:00:07 GMT 2018
	 */
	int deleteByPrimaryKey(Long ptransactionid);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table ptransaction
	 *
	 * @mbg.generated Thu Feb 22 15:00:07 GMT 2018
	 */
	int insert(Ptransaction record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table ptransaction
	 *
	 * @mbg.generated Thu Feb 22 15:00:07 GMT 2018
	 */
	Ptransaction selectByPrimaryKey(Long ptransactionid);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table ptransaction
	 *
	 * @mbg.generated Thu Feb 22 15:00:07 GMT 2018
	 */
	List<Ptransaction> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table ptransaction
	 *
	 * @mbg.generated Thu Feb 22 15:00:07 GMT 2018
	 */
	int updateByPrimaryKey(Ptransaction record);

	List<Ptransaction> selectByUserId(Long userid);

}