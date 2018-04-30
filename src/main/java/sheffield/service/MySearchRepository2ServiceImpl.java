//package sheffield.service;
//
//import java.io.Serializable;
//import java.util.List;
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import sheffield.elasticsearch.repository.MySearchRepository2;
//import sheffield.mbg.pojo.Commodity;
//
//// 未知错误，mySearchRepository2实例无法注入，可能是版本原因，也可能是配置中base-package原因;暂时使用mysql全文搜索
//@Service("mySearchRepository2Service")
//public class MySearchRepository2ServiceImpl implements MySearchRepository2Service {
//
//	@Resource
//	private MySearchRepository2 mySearchRepository2;
//
//	public MySearchRepository2ServiceImpl() {
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//	public Commodity saveCommodityIndex(Commodity commodity) {
//		// TODO Auto-generated method stub
//		return (Commodity) mySearchRepository2.save(commodity);
//
//	}
//
//	@Override
//	public void deleteCommodityIndex(Long commodityid) {
//		// TODO Auto-generated method stub
//		mySearchRepository2.delete(commodityid);
//
//	}
//
//	@Override
//	public List<Commodity> selectAllCommodityIndex() {
//		// TODO Auto-generated method stub
//		List<Commodity> list = (List<Commodity>) mySearchRepository2.findAll();
//		return list;
//	}
//
//	@Override
//	public Commodity selectOneCommodityIndex(Long commodityid) {
//		// TODO Auto-generated method stub
//		return (Commodity) mySearchRepository2.findOne(commodityid);
//	}
//
//	@Override
//	public List<Commodity> findByCommoditydescriptionOrCommoditynameOrCommoditycategory(String commoditydescription,
//			String commodityname, String commoditycategory) {
//		// TODO Auto-generated method stub
//		return mySearchRepository2.findByCommoditydescriptionOrCommoditynameOrCommoditycategory(commoditydescription,
//				commodityname, commoditycategory);
//	}
//
//	public MySearchRepository2 getMySearchRepository2() {
//		return mySearchRepository2;
//	}
//
//	public void setMySearchRepository2(MySearchRepository2 mySearchRepository2) {
//		this.mySearchRepository2 = mySearchRepository2;
//	}
//
//}
