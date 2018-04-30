package sheffield.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.coobird.thumbnailator.Thumbnails;
import sheffield.mbg.pojo.Commodity;
import sheffield.mbg.pojo.Commodityimage;
import sheffield.mbg.pojo.Review;
import sheffield.mbg.pojo.Review2;
import sheffield.mbg.pojo.ShoppingCartItem;
import sheffield.mbg.pojo.SubmitReview;
import sheffield.mbg.pojo.User;
import sheffield.service.AddressService;
import sheffield.service.CommodityService;
import sheffield.service.CommodityimageService;
import sheffield.service.ReviewService;
import sheffield.service.UserService;

@Controller
@RequestMapping(value = "/buyer")
public class BuyerController {

	public BuyerController() {
		// TODO Auto-generated constructor stub
	}

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

	private static final String ShoppingCartUserRedisKey = "_shoppingCart";
	private static final String reviewErrors = "reviewerrors";
	private static final String SingleCommodityPage = "singlecommodity";
	private static final String SingleCommodity = "commodity";
	private static final String SingleCommodityOwner = "owner";
	private static final String ReviewList = "reviewlist";
	private static final String ImagesKeysList = "imageskeyslist";
	private static final String FirstImageId = "firstimageid";
	private static final String ImagesKeysListSize = "imageskeyslistsize";
	private static final String PageInfo2 = "pageinfo2";
	private static final String ShoppingCartItemList = "commoditylist";
	private static final String CurrentUserName = "currentusername";
	private static final String BuyerName = "buyername";
	private static final String ShoppingCartPage = "shoppingcart";
	private static final String AddressesList = "addresseslist";
	private static final String ShoppingCartTotalCost = "totalcost";
	private static final String QuantityError = "quantityerror";
	private static final String QuantityErrorMessage = "Not enough commodities in stock!";
	private static final String CannotBuyError = "cannotbuyerror";
	private static final String CannotBuyErrorMessage = "You cannot buy your own commodity!";
	private static final String CannotBuyErrorMessage2 = "Please input integer.";
	private static final int pageSizeForReviews = 4;
	private static final int thumbnailsImageSize = 300;
	private static final int CommodityImageResizeWidth = 1920;
	private static final int CommodityImageResizeHeight = 1080;
	private static Logger logger = Logger.getLogger(BuyerController.class);

	@RequestMapping(value = "/commodity")
	public String viewSingleCommodity(@RequestParam(value = "commodityid", required = true) long commodityid,
			@RequestParam(value = "pageinfo2", required = false, defaultValue = "1") Integer page, ModelMap modelMap,
			@ModelAttribute(CannotBuyError) String error) {
		if (error != null) {
			modelMap.addAttribute(CannotBuyError, error);
		}
		Commodity commodity = commodityService.selectByPrimaryKey(commodityid);
		User owner = userService.selectByPrimaryKey(commodity.getCommodityownerid());
		PageHelper.startPage(page.intValue(), pageSizeForReviews);
		List<Review2> list = reviewService.selectByCommodityIdWithUserName(commodityid);
		PageInfo<Review2> pageInfo = new PageInfo<>(list);
		LinkedList<Review2> linkedList = new LinkedList<>(list);
		LinkedList<Long> linkedList2 = new LinkedList<>(commodityimageService.selectImgKeysByCommodityId(commodityid));
		long firstimgid = linkedList2.get(0);
		modelMap.put(SingleCommodity, commodity);
		modelMap.put(SingleCommodityOwner, owner);
		modelMap.put(ReviewList, linkedList);
		modelMap.put(PageInfo2, pageInfo);
		modelMap.put(ImagesKeysList, linkedList2);
		modelMap.put(ImagesKeysListSize, String.valueOf(linkedList2.size() - 1));
		modelMap.put(FirstImageId, firstimgid);
		String currentusername = (String) SecurityUtils.getSubject().getPrincipal();
		modelMap.put(CurrentUserName, currentusername);
		if (redisTemplate3.hasKey(reviewErrors)) {
			modelMap.put(reviewErrors, redisTemplate3.opsForValue().get(reviewErrors));
			redisTemplate3.delete(reviewErrors);
		}
		return SingleCommodityPage;
	}

	@RequestMapping(value = "/owneravatar")
	public void showOwnerAvatar(@RequestParam(value = "commodityownerid", required = true) long commodityownerid,
			HttpServletResponse response, HttpServletRequest request, HttpSession httpsession, ModelMap modelMap) {

		User user = userService.selectByPrimaryKey(commodityownerid);
		byte[] imagebyte = user.getUseravatar();
		response.setContentType("image/jpeg");
		response.setCharacterEncoding("UTF-8");
		try {
			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			InputStream inputStream = new ByteArrayInputStream(imagebyte);
			Thumbnails.of(new InputStream[] { inputStream }).size(thumbnailsImageSize, thumbnailsImageSize)
					.outputFormat("jpg").toOutputStream(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@RequestMapping(value = "/commodityimages")
	public void showCommodityImages(@RequestParam(value = "imgid", required = true) long imgid,
			HttpServletResponse response, HttpServletRequest request, HttpSession httpsession, ModelMap modelMap) {

		Commodityimage image = commodityimageService.selectByPrimaryKey(imgid);
		byte[] imagebyte = image.getCommodityimg();
		response.setContentType("image/jpeg");
		response.setCharacterEncoding("UTF-8");
		try {
			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			InputStream inputStream = new ByteArrayInputStream(imagebyte);
			Thumbnails.of(new InputStream[] { inputStream }).size(CommodityImageResizeWidth, CommodityImageResizeHeight)
					.outputFormat("jpg").toOutputStream(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@RequestMapping(value = "/review")
	public String submitReview(HttpServletResponse response, HttpServletRequest request, HttpSession httpsession,
			ModelMap modelMap, @Valid SubmitReview submitReview, Errors errors, RedirectAttributes redirectAttributes) {
		if (errors.hasErrors()) {
			List<FieldError> list = errors.getFieldErrors();
			Iterator<FieldError> iterator = list.iterator();
			ArrayList<String> msgs = new ArrayList<String>();
			while (iterator.hasNext()) {
				FieldError fieldError = iterator.next();
				String fie = fieldError.getField();
				String meString = fieldError.getDefaultMessage();
				String errorMessage = fie.concat(meString);
				msgs.add(errorMessage);
			}
			redisTemplate3.opsForValue().set(reviewErrors, msgs);
			// redirectAttributes.addFlashAttribute(reviewErrors, msgs);
			redirectAttributes.addAttribute("commodityid", submitReview.getReviewcommodityid());
			return "redirect:/buyer/commodity";
		}
		Review review = new Review();
		String attachedemail = submitReview.getReviewemail();
		// 在评论前附加邮箱
		review.setCustomerreview(attachedemail + ": " + submitReview.getReviewtext());
		// 打星功能等待添加，默认5
		review.setRate(Integer.parseInt(submitReview.getReviewrate()));
		String currentusername = (String) SecurityUtils.getSubject().getPrincipal();
		User currentuser = userService.selectByUserName(currentusername);
		review.setUserid(currentuser.getUserid());
		review.setCommodityid(submitReview.getReviewcommodityid());
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 可以方便地修改日期格式
		String newdate = dateFormat.format(date);
		review.setReviewdate(newdate);
		reviewService.insert(review);
		redirectAttributes.addAttribute("commodityid", submitReview.getReviewcommodityid());
		return "redirect:/buyer/commodity";
	}

	@RequestMapping(value = "shoppingcart/newcommodity")
	public String addCommodityToShoppingCart(HttpServletResponse response, HttpServletRequest request,
			HttpSession httpsession, ModelMap modelMap,
			@RequestParam(value = "commoditynumber", required = false, defaultValue = "1") String commoditynumber,
			@RequestParam(value = "addedcommodityid", required = false) String addedcommodityid) {
		String currentusername = (String) SecurityUtils.getSubject().getPrincipal();
		User currentuser = userService.selectByUserName(currentusername);
		Long currentuserid = currentuser.getUserid();

		// 1.不能自己买自己的
		if (addedcommodityid != null) {
			Long currentcommodityownerid = commodityService.selectByPrimaryKey(Long.parseLong(addedcommodityid))
					.getCommodityownerid();
			if (currentcommodityownerid == currentuserid) {
				modelMap.addAttribute(CannotBuyError, CannotBuyErrorMessage);
				return "redirect:/buyer/commodity?commodityid=" + addedcommodityid;

			}
		}

		// 2. 验证输入是否数字
		if (commoditynumber.indexOf(".") != -1) {
			modelMap.addAttribute(CannotBuyError, CannotBuyErrorMessage2);
			return "redirect:/buyer/commodity?commodityid=" + addedcommodityid;

		}

		// 使用当前登录用户id+_shoppingCart作为redis哈希结构的key
		String shoppingcartrediskey = String.valueOf(currentuser.getUserid()) + ShoppingCartUserRedisKey;
		if (addedcommodityid != null) {
			Commodity commodity = commodityService.selectByPrimaryKey(Long.parseLong(addedcommodityid));
			if (commodity.getCommodityquantity() == 0) {
				modelMap.addAttribute(QuantityError, QuantityErrorMessage);
				return SingleCommodityPage;
			} else {
				// 覆盖购物车内旧的商品
				redisTemplate3.opsForHash().put(shoppingcartrediskey, addedcommodityid, commoditynumber);
				// redisTemplate3.opsForHash().putIfAbsent(shoppingcartrediskey,
				// addedcommodityid, commoditynumber);
			}
		}

		// 找出买家地址
		LinkedList<sheffield.mbg.pojo.Address> linkedList2 = new LinkedList<>(
				addressService.selectByUserId(currentuser.getUserid()));
		modelMap.addAttribute(AddressesList, linkedList2);

		if (redisTemplate3.hasKey(shoppingcartrediskey)) {
			Map<String, String> map = redisTemplate3.opsForHash().entries(shoppingcartrediskey);
			// String lasttotalcost = (String)
			// redisTemplate.opsForHash().get(shoppingcartrediskey, ShoppingCartTotalCost);
			// double totalcost = Double.parseDouble(lasttotalcost);
			double totalcost = 0;
			LinkedList<ShoppingCartItem> linkedList = new LinkedList<>();
			for (String key : map.keySet()) {
				if (key.equals(ShoppingCartTotalCost)) {
					continue;
				}
				if (key.equals(BuyerName)) {
					continue;
				}
				ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
				shoppingCartItem.setItemid(key);
				shoppingCartItem.setItemquantity(map.get(key));
				Commodity currentcommodity = commodityService.selectByPrimaryKey(Long.parseLong(key));
				// 先计算单个价格再计算总价
				double cost = (currentcommodity.getCommodityprice()) * Double.parseDouble((map.get(key)));
				shoppingCartItem.setItemcost(cost);
				shoppingCartItem
						.setItemname(commodityService.selectByPrimaryKey(Long.parseLong(key)).getCommodityname());
				linkedList.add(shoppingCartItem);
				totalcost += cost;
			}
			modelMap.addAttribute(ShoppingCartItemList, linkedList);
			// 显示小数点后两位
			String totalcoststring = String.format("%.2f", totalcost);
			modelMap.addAttribute(ShoppingCartTotalCost, totalcoststring);
			redisTemplate3.opsForHash().put(shoppingcartrediskey, ShoppingCartTotalCost, String.valueOf(totalcost));
			redisTemplate3.opsForHash().put(shoppingcartrediskey, BuyerName, currentusername);
			// String checkoutrediskey = currentusername + "CO" +
			// currentuser.getUseremail();
			// logger.info("Try to store a linkedlist for ShoppingCartItem in Redis.");
			// redisTemplate.opsForValue().set(checkoutrediskey, linkedList);
			return ShoppingCartPage;
		}
		return ShoppingCartPage;
	}

	@RequestMapping(value = "shoppingcart/remove")
	public String removeCommodityFromShoppingCart(HttpServletResponse response, HttpServletRequest request,
			HttpSession httpsession, ModelMap modelMap,
			@RequestParam(value = "itemid", required = true) String removedcommodityid) {
		String currentusername = (String) SecurityUtils.getSubject().getPrincipal();
		User currentuser = userService.selectByUserName(currentusername);
		// 使用当前登录用户名和email作为redis哈希结构的key
		String shoppingcartrediskey = currentusername + ShoppingCartUserRedisKey + currentuser.getUseremail();
		Commodity currentcommodity = commodityService.selectByPrimaryKey(Long.parseLong(removedcommodityid));
		// 使用同一个Redis连接，节省资源。
		SessionCallback sessionCallback = new SessionCallback() {
			@Override
			public Object execute(RedisOperations operations) throws DataAccessException {
				// TODO Auto-generated method stub
				String removednumber = (String) operations.opsForHash().get(shoppingcartrediskey, removedcommodityid);
				double removedcost = (currentcommodity.getCommodityprice()) * Double.parseDouble(removednumber);
				operations.opsForHash().delete(shoppingcartrediskey, removedcommodityid);
				String lasttotalcost = (String) operations.opsForHash().get(shoppingcartrediskey,
						ShoppingCartTotalCost);
				double totalcost = Double.valueOf(lasttotalcost) - removedcost;
				operations.opsForHash().put(shoppingcartrediskey, ShoppingCartTotalCost, String.valueOf(totalcost));
				return null;

			}
		};
		redisTemplate3.execute(sessionCallback);
		return "redirect:/buyer/shoppingcart/newcommodity";
	}

}
