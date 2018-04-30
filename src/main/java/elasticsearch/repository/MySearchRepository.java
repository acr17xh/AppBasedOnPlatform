//package elasticsearch.repository;
//
//import java.io.Serializable;
//import java.util.List;
//
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//import org.springframework.data.repository.NoRepositoryBean;
//import org.springframework.data.repository.PagingAndSortingRepository;
//
//@NoRepositoryBean
//public interface MySearchRepository<T, ID extends Serializable>
//		extends ElasticsearchRepository<T, ID>, PagingAndSortingRepository<T, ID> {
//	// 接口是可以多继承的，类不行；并且被继承的躲个接口中同名方法返回值需要一样
//	List<T> findByCommoditydescriptionOrCommoditynameOrCommoditycategory(String commoditydescription,
//			String commodityname, String commoditycategory);
//}
