package sheffield.utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import sheffield.mbg.pojo.Commodity;
import sheffield.mbg.pojo.CommodityWithRate;
import sheffield.service.CommodityService;

@Component(value = "searchUtils")
public class SearchUtils {

	public SearchUtils() {
		// TODO Auto-generated constructor stub
	}

	@Resource
	private CommodityService commodityService;
	private static final int endIndexOfShortCommodityDescription = 30;

	public List<Commodity> searchFromHomePage(String searchcontent, Integer limitoffset, Integer limitsize) {
		return commodityService.selectByUsingFullTextOrderByDate(searchcontent, limitoffset, limitsize);
	}

	public List<Commodity> searchByDate(String searchcontent, Integer limitoffset, Integer limitsize) {
		return commodityService.selectByUsingFullTextOrderByDate(searchcontent, limitoffset, limitsize);
	}

	public List<Commodity> searchBySales(String searchcontent, Integer limitoffset, Integer limitsize) {
		return commodityService.selectByUsingFullTextOrderBySales(searchcontent, limitoffset, limitsize);
	}

	public List<Commodity> searchByPriceDESC(String searchcontent, Integer limitoffset, Integer limitsize) {
		return commodityService.selectByUsingFullTextOrderByPriceDESC(searchcontent, limitoffset, limitsize);
	}

	public List<Commodity> searchByPriceASC(String searchcontent, Integer limitoffset, Integer limitsize) {
		return commodityService.selectByUsingFullTextOrderByPriceASC(searchcontent, limitoffset, limitsize);
	}

	public List<Commodity> searchByPriceRange(String searchcontent, Double fromprice, Double toprice,
			Integer limitoffset, Integer limitsize) {
		return commodityService.searchByPriceRange(searchcontent, fromprice, toprice, limitoffset, limitsize);
	}

	public Double selectRateBySearch(Long commodityid) {
		return commodityService.selectRateBySearch(commodityid);
	}

	public LinkedList<CommodityWithRate> rateListForCommodities(List<Commodity> commoditylist) {
		// 把商品星级存储在链表里
		LinkedList<CommodityWithRate> list = new LinkedList<>();
		LinkedList<CommodityWithRate> list2 = new LinkedList<>();
		LinkedList<Integer> ratelist = new LinkedList<>();
		Iterator<Commodity> iterator = commoditylist.iterator();
		while (iterator.hasNext()) {
			Commodity commodity = iterator.next();
			CommodityWithRate commodityWithRate = new CommodityWithRate();
			commodityWithRate.setRate(0);
			commodityWithRate.setCommoditycategory(commodity.getCommoditycategory());
			commodityWithRate.setCommoditydate(commodity.getCommoditydate());
			String tempdes = commodity.getCommoditydescription();
			if (tempdes.length() <= endIndexOfShortCommodityDescription) {
				commodityWithRate.setCommoditydescription(tempdes);
			} else {
				commodityWithRate.setCommoditydescription(tempdes.substring(0, endIndexOfShortCommodityDescription));
			}
			commodityWithRate.setCommodityid(commodity.getCommodityid());
			commodityWithRate.setCommoditylocation(commodity.getCommoditylocation());
			commodityWithRate.setCommodityname(commodity.getCommodityname());
			commodityWithRate.setCommodityownerid(commodity.getCommodityownerid());
			commodityWithRate.setCommodityprice(commodity.getCommodityprice());
			commodityWithRate.setCommodityquantity(commodity.getCommodityquantity());
			commodityWithRate.setCommoditysales(commodity.getCommoditysales());
			commodityWithRate.setVersion(commodity.getVersion());
			list.add(commodityWithRate);
			long currentid = commodity.getCommodityid();
			// 判断是否非空，因为有的商品没有评论
			Double currentrate = this.selectRateBySearch(currentid);
			if (currentrate == null) {
				currentrate = Double.parseDouble("0");
			}
			Double currentrateceil = Math.ceil(currentrate);
			Integer currentrateint = currentrateceil.intValue();
			ratelist.add(currentrateint);
		}

		Iterator<Integer> iterator2 = ratelist.iterator();
		Iterator<CommodityWithRate> iterator3 = list.iterator();
		while (iterator2.hasNext() && iterator3.hasNext()) {
			Integer currentrate = (Integer) iterator2.next();
			CommodityWithRate commodityWithRate = iterator3.next();
			commodityWithRate.setRate(currentrate);
			list2.add(commodityWithRate);
		}
		return list2;
	}

}
