package sheffield.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.DefaultValue;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.PageHelper;

import net.coobird.thumbnailator.Thumbnails;
import sheffield.mbg.pojo.Commodity;
import sheffield.mbg.pojo.CommodityWithRate;
import sheffield.mbg.pojo.Commodityimage;
import sheffield.mbg.pojo.PaginationStatus;
import sheffield.service.AddressService;
import sheffield.service.CommodityService;
import sheffield.service.CommodityimageService;
import sheffield.service.ReviewService;
import sheffield.service.TransactionService;
import sheffield.service.UserService;
import sheffield.utils.SearchUtils;
import sheffield.utils.ShiroUserUtils;

@Controller
@RequestMapping(value = "/search")
public class SearchController {

	@Resource
	private AddressService addressService;
	@Resource
	private CommodityService commodityService;
	@Resource
	private UserService userService;
	@Resource
	private CommodityimageService commodityimageService;
	@Resource
	private ReviewService reviewService;
	@Resource
	private RedisTemplate redisTemplate3;
	@Resource
	private TransactionService transactionService;
	@Resource
	private SearchUtils searchUtils;
	@Resource
	private ShiroUserUtils shiroUserUtils;

	// @Resource
	// private MySearchRepository2Service mySearchRepository2Service;
	private static final String CurrentTimeInUrl = "currenttimeinurl";
	private static final String SelectedCondition = "selectedcondition";
	private static final String PageStatus = "pagestatus";
	private static final String RedisSearchByPriceRange = "_redis_searchByPriceRange";
	private static final String RedisSearchPageNumber = "_redis_searchPageNumber";
	private static final String RedisSearchCondition = "_redis_searchCondition";
	private static final String RedisSearchContent = "_redis_searchContent";
	private static final String MostRecent = "1";
	private static final String MostPopular = "2";
	private static final String LowestPrice = "3";
	private static final String HighestPrice = "4";
	private static final String PriceRange = "5";
	private static final String DateWhenSearch = "datewhensearch";
	private static final String SearchText = "searchtext";
	private static final String NumberOfResults = "numberofresults";
	private static final String CategoryPage = "categorypage";
	private static final String PageInfo = "pageinfo";
	private static final String CategoryCommodityList = "commoditylist";
	private static final int pageSizeForCommoditiesInCategoryPage = 6;
	private static final String Ratelist = "ratelist";
	private static final int thumbnailsImageSize = 420;
	private static final int thumbnailsImageSize2 = 275;
	private static Logger logger = Logger.getLogger(SearchController.class);

	public SearchController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(value = "/searchforstore")
	public String searchForStore(@RequestParam(value = "searchcontent", required = false) String searchcontent2,
			@RequestParam(value = "pageinfo", required = false, defaultValue = "0") String page, ModelMap modelMap) {
		LinkedList<Commodity> commoditylist;
		LinkedList<CommodityWithRate> commoditywithratelist;
		List<Commodity> list;
		String searchcontent = searchcontent2;
		// 如果有之前的搜索条件，删除
		String searchcondition = shiroUserUtils.getCurrentUser().getUserid() + RedisSearchCondition;
		String currentuserid = String.valueOf(shiroUserUtils.getCurrentUser().getUserid());
		if (redisTemplate3.hasKey(searchcondition)) {
			redisTemplate3.delete(searchcondition);
		}
		if (redisTemplate3.hasKey(currentuserid + RedisSearchByPriceRange)) {
			redisTemplate3.delete(currentuserid + RedisSearchByPriceRange);

		}
		// 如果有以前的商品目录当前页数，设置为0
		String searchpagenumber = shiroUserUtils.getCurrentUser().getUserid() + RedisSearchPageNumber;
		if (redisTemplate3.hasKey(searchpagenumber)) {
			redisTemplate3.opsForValue().set(searchpagenumber, String.valueOf(0));
		}

		// PageHelper.startPage(page, pageSizeForCommoditiesInCategoryPage);
		if ((searchcontent == null) || (searchcontent.equals(""))) {
			// 搜索框默认按销量排序
			// String searchrediskey = shiroUserUtils.getCurrentUser().getUserid() +
			// RedisSearchContent;
			// if (redisTemplate3.hasKey(searchrediskey)) {
			// redisTemplate3.delete(searchrediskey);
			// }
			list = commodityService.selectAllBySales(0, pageSizeForCommoditiesInCategoryPage);
			commoditylist = new LinkedList<Commodity>(list);
			// com.github.pagehelper.PageInfo<Commodity> pageInfo = new
			// com.github.pagehelper.PageInfo<>(list);
			// modelMap.addAttribute(PageInfo, pageInfo);
			// 把商品星级存储在链表里
			commoditywithratelist = searchUtils.rateListForCommodities(commoditylist);
			// modelMap.addAttribute(Ratelist, ratelist);
		} else {
			// 搜索内容暂时存储到redis
			redisTemplate3.opsForValue().set(shiroUserUtils.getCurrentUser().getUserid() + RedisSearchContent,
					searchcontent);
			// 有搜索内容，按销量排序
			list = searchUtils.searchBySales(searchcontent, 0, pageSizeForCommoditiesInCategoryPage);
			// list = searchUtils.searchByDate(searchcontent, 0,
			// pageSizeForCommoditiesInCategoryPage);
			commoditylist = new LinkedList<Commodity>(list);
			// com.github.pagehelper.PageInfo<Commodity> pageInfo = new
			// com.github.pagehelper.PageInfo<>(list);
			// modelMap.addAttribute(PageInfo, pageInfo);
			// 把商品星级存储在链表里
			commoditywithratelist = searchUtils.rateListForCommodities(commoditylist);
			// modelMap.addAttribute(Ratelist, ratelist);
		}
		modelMap.addAttribute(CategoryCommodityList, commoditywithratelist);
		modelMap.addAttribute(NumberOfResults, commoditywithratelist.size());
		modelMap.addAttribute(SearchText, searchcontent);
		// 搜索时的日期
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH:mm:ss");// 可以方便地修改日期格式
		String newdate = dateFormat.format(date);
		modelMap.addAttribute(DateWhenSearch, newdate);
		modelMap.addAttribute(PageStatus, new PaginationStatus());
		Date date2 = new Date();
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String newdate2 = dateFormat2.format(date);
		// 为了解决相同URL不跳转的问题
		logger.info(newdate2);
		modelMap.addAttribute(CurrentTimeInUrl, newdate2);
		return CategoryPage;
	}

	// 显示商品缩略图，宽高:420px×275px
	@RequestMapping(value = "/commodityimage/{commodityid}")
	public void showCommodityImage(HttpServletResponse response, HttpServletRequest request, HttpSession httpsession,
			ModelMap modelMap, @PathVariable(value = "commodityid", required = false) String commodityid) {
		Long commodityid2 = Long.parseLong(commodityid);
		LinkedList<Commodityimage> linkedList = new LinkedList<>(
				commodityimageService.selectByCommodityId(commodityid2));
		byte[] imagebyte = null;
		if (linkedList.size() == 0) {
			imagebyte = null;
		} else {
			imagebyte = linkedList.get(0).getCommodityimg();
		}
		response.setContentType("image/jpeg");
		response.setCharacterEncoding("UTF-8");
		try {
			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			InputStream inputStream = new ByteArrayInputStream(imagebyte);
			Thumbnails.of(new InputStream[] { inputStream }).size(thumbnailsImageSize2, thumbnailsImageSize2)
					.outputFormat("jpg").toOutputStream(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@RequestMapping(value = "/conditions/{pageinfo}")
	public String searchOnCondtions(
			@RequestParam(value = "selectedcondition", required = false) String selectedcondition, ModelMap modelMap,
			@PathVariable(value = "pageinfo", required = false) String page,
			@RequestParam(value = "time", required = false) String currenttimeinurl) {
		// page 页数标识，0:第一页，1:上一页,2:下一页,3:最后一页
		String condition = selectedcondition;
		List<Commodity> list = null;
		// PageHelper.startPage(page, pageSizeForCommoditiesInCategoryPage);
		String searchcontent = null;
		Integer count = 0;
		String searchrediskey = shiroUserUtils.getCurrentUser().getUserid() + RedisSearchContent;
		String searchpagenumber = shiroUserUtils.getCurrentUser().getUserid() + RedisSearchPageNumber;

		// 得到存储的价格范围搜索条件
		Double fromprice = 0.0;
		Double toprice = 0.0;
		String selectedcondtion5 = null;
		String currentuserid = String.valueOf(shiroUserUtils.getCurrentUser().getUserid());
		if (redisTemplate3.hasKey(currentuserid + RedisSearchByPriceRange)) {
			String from = (String) redisTemplate3.opsForHash().get(currentuserid + RedisSearchByPriceRange,
					"fromprice");
			fromprice = Double.parseDouble(from);
			String to = (String) redisTemplate3.opsForHash().get(currentuserid + RedisSearchByPriceRange, "toprice");
			toprice = Double.parseDouble(to);
			selectedcondtion5 = (String) redisTemplate3.opsForHash().get(currentuserid + RedisSearchByPriceRange,
					"selectedcondition");
		}
		// if ((selectedcondtion5 != null) && ((selectedcondition == null) ||
		// (selectedcondition.equals("")))) {
		// selectedcondition = PriceRange;
		// }
		logger.info("selectedcondition5 " + selectedcondtion5);
		// 得到存储的搜索条件
		String searchcondition = shiroUserUtils.getCurrentUser().getUserid() + RedisSearchCondition;
		if (selectedcondition != null) {
			logger.info("selectedcondition " + selectedcondition);
		} else {
			logger.info("selectedcondition is null");
		}

		if ((selectedcondition == null) || (selectedcondition.equals(""))) {
			if ((redisTemplate3.hasKey(searchcondition)) && (selectedcondtion5 != null)) {
				logger.info("situation 1");
				condition = selectedcondtion5;

			} else if ((redisTemplate3.hasKey(searchcondition)) && (selectedcondtion5 == null)) {
				logger.info("situation 2");
				condition = (String) redisTemplate3.opsForValue().get(searchcondition);

			} else if ((!redisTemplate3.hasKey(searchcondition) && (selectedcondtion5 != null))) {
				logger.info("situation 3");
				condition = selectedcondtion5;

			} else if ((!redisTemplate3.hasKey(searchcondition) && (selectedcondtion5 == null))) {
				logger.info("situation 4");
				condition = MostPopular;
			}
		} else {
			logger.info("(selectedcondition == null) || (selectedcondition.equals(\"\"))");
			redisTemplate3.opsForValue().set(searchcondition, String.valueOf(selectedcondition));
			redisTemplate3.delete(currentuserid + RedisSearchByPriceRange);
			condition = selectedcondition;
		}

		// 分页
		int currentpageoffset = 0;
		if (redisTemplate3.hasKey(searchpagenumber)) {
			int currentpage = (int) Integer.parseInt((String) redisTemplate3.opsForValue().get(searchpagenumber));
			switch (page) {
			case "0":
				searchcontent = (String) redisTemplate3.opsForValue().get(searchrediskey);
				count = commodityService.countBySearch(searchcontent);
				currentpageoffset = 0;
				currentpage = 0;
				redisTemplate3.opsForValue().set(searchpagenumber, String.valueOf(currentpage));
				logger.info("0 Current page offset: " + currentpageoffset);
				break;
			case "1":
				searchcontent = (String) redisTemplate3.opsForValue().get(searchrediskey);
				count = commodityService.countBySearch(searchcontent);
				currentpageoffset = (currentpage - 1) * pageSizeForCommoditiesInCategoryPage;
				if (currentpageoffset <= 0) {
					currentpageoffset = 0;
				}
				if ((currentpage - 1) <= 0) {
					currentpage = 0;
				}
				logger.info("1 Current page offset: " + currentpageoffset);
				redisTemplate3.opsForValue().set(searchpagenumber, String.valueOf(currentpage));
				break;
			case "2":
				if (redisTemplate3.hasKey(searchrediskey)) {
					searchcontent = (String) redisTemplate3.opsForValue().get(searchrediskey);
					logger.info("2 Search content: " + searchcontent);
					count = commodityService.countBySearch(searchcontent);
					// logger.info("2 countBySearch: " + count);
					currentpageoffset = (currentpage + 1) * pageSizeForCommoditiesInCategoryPage;
				} else {
					count = commodityService.countAll();
					// logger.info("2 countAll: " + count);
					currentpageoffset = (currentpage + 1) * pageSizeForCommoditiesInCategoryPage;
				}
				if (currentpageoffset >= count) {
					currentpageoffset = currentpageoffset - pageSizeForCommoditiesInCategoryPage;
					redisTemplate3.opsForValue().set(searchpagenumber, String.valueOf(currentpage));
				} else {
					redisTemplate3.opsForValue().set(searchpagenumber, String.valueOf(currentpage + 1));
				}

				break;
			case "3":
				if (redisTemplate3.hasKey(searchrediskey)) {
					searchcontent = (String) redisTemplate3.opsForValue().get(searchrediskey);
					logger.info("3 Search content: " + searchcontent);
					count = commodityService.countBySearch(searchcontent);
					// logger.info("3 countBySearch: " + count);
					int temp = (int) Math.floor(count / pageSizeForCommoditiesInCategoryPage);
					if (temp * pageSizeForCommoditiesInCategoryPage < count) {
						currentpageoffset = temp * pageSizeForCommoditiesInCategoryPage;
						redisTemplate3.opsForValue().set(searchpagenumber, String.valueOf(temp));
					} else {
						currentpageoffset = (temp - 1) * pageSizeForCommoditiesInCategoryPage;
						redisTemplate3.opsForValue().set(searchpagenumber, String.valueOf(temp - 1));
					}

				} else {
					count = commodityService.countAll();
					logger.info("3 countAll: " + count);
					int temp = (int) Math.floor(count / pageSizeForCommoditiesInCategoryPage);
					if (temp * pageSizeForCommoditiesInCategoryPage < count) {
						currentpageoffset = temp * pageSizeForCommoditiesInCategoryPage;
						redisTemplate3.opsForValue().set(searchpagenumber, String.valueOf(temp));
					} else {
						currentpageoffset = (temp - 1) * pageSizeForCommoditiesInCategoryPage;
					}
					currentpageoffset = temp * pageSizeForCommoditiesInCategoryPage;
					redisTemplate3.opsForValue().set(searchpagenumber, String.valueOf(temp));
					redisTemplate3.opsForValue().set(searchpagenumber, String.valueOf(temp - 1));
				}
				break;

			default:
				break;
			}

		} else {
			redisTemplate3.opsForValue().set(searchpagenumber, String.valueOf(0));
		}

		logger.info("Current page offset: " + currentpageoffset);
		// 分页后利用LIMIT OFFSET得到的列表数据
		logger.info("Current selected condition: " + condition);
		// private static final String MostRecent = "1";
		// private static final String MostPopular = "2";
		// private static final String LowestPrice = "3";
		// private static final String HighestPrice = "4";
		// private static final String PriceRange = "5";
		if (redisTemplate3.hasKey(searchrediskey)) {
			searchcontent = (String) redisTemplate3.opsForValue()
					.get(shiroUserUtils.getCurrentUser().getUserid() + RedisSearchContent);
			switch (condition) {
			case MostRecent:
				list = searchUtils.searchByDate(searchcontent, currentpageoffset, pageSizeForCommoditiesInCategoryPage);
				break;
			case MostPopular:
				list = searchUtils.searchBySales(searchcontent, currentpageoffset,
						pageSizeForCommoditiesInCategoryPage);
				logger.info("MostPopular");
				break;
			case LowestPrice:
				list = searchUtils.searchByPriceASC(searchcontent, currentpageoffset,
						pageSizeForCommoditiesInCategoryPage);
				break;
			case HighestPrice:
				list = searchUtils.searchByPriceDESC(searchcontent, currentpageoffset,
						pageSizeForCommoditiesInCategoryPage);
				break;
			case PriceRange:
				list = searchUtils.searchByPriceRange(searchcontent, fromprice, toprice, currentpageoffset,
						pageSizeForCommoditiesInCategoryPage);
				break;
			default:
				break;
			}
		} else {
			list = commodityService.selectAllBySales(currentpageoffset, pageSizeForCommoditiesInCategoryPage);
		}

		modelMap.addAttribute(NumberOfResults, count);
		modelMap.addAttribute(SearchText, searchcontent);
		// 搜索时的日期
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH:mm:ss");// 可以方便地修改日期格式
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String newdate = dateFormat.format(date);
		String newdate2 = dateFormat2.format(date);
		modelMap.addAttribute(DateWhenSearch, newdate);
		// 为了解决相同URL不跳转的问题
		logger.info(newdate2);
		modelMap.addAttribute(CurrentTimeInUrl, newdate2);
		LinkedList<CommodityWithRate> commoditywithratelist = searchUtils.rateListForCommodities(list);
		modelMap.addAttribute(CategoryCommodityList, commoditywithratelist);
		// modelMap.addAttribute(PageStatus, new PaginationStatus());
		modelMap.addAttribute(SelectedCondition, condition);
		return CategoryPage;
	}

	@RequestMapping(value = "/searchbypricerange")
	public String searchByPriceRange(ModelMap modelMap, @RequestParam(value = "fromprice") String fromprice,
			@RequestParam(value = "toprice") String toprice, RedirectAttributes redirectAttributes) {
		HashedMap map = new HashedMap();
		map.put("fromprice", fromprice);
		map.put("toprice", toprice);
		map.put("selectedcondition", PriceRange);
		String currentuserid = String.valueOf(shiroUserUtils.getCurrentUser().getUserid());
		redisTemplate3.opsForHash().putAll(currentuserid + RedisSearchByPriceRange, map);
		// redirectAttributes.addFlashAttribute("selectedcondition", PriceRange);
		return "redirect:/search/conditions/0";
	}
}
