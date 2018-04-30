package sheffield.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.coobird.thumbnailator.Thumbnails;
import sheffield.mbg.pojo.Account;
import sheffield.mbg.pojo.Commodity;
import sheffield.mbg.pojo.CommodityWithRate;
import sheffield.mbg.pojo.Commodityimage;
import sheffield.mbg.pojo.Transaction;
import sheffield.mbg.pojo.UpdateUser;
import sheffield.mbg.pojo.User;
import sheffield.mbg.pojo.Ptransaction;
import sheffield.service.AccountService;
import sheffield.service.AddressService;
import sheffield.service.CommodityService;
import sheffield.service.CommodityimageService;
import sheffield.service.PtransactionService;
import sheffield.service.TransactionService;
import sheffield.service.UserService;
import sheffield.utils.SearchUtils;
import sheffield.utils.ShiroUserUtils;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	private static final String currentNumberOfCommodities = "commoditiesnumber";
	private static final String homePage = "ecommercehomepage";
	private static final String penautBankPage = "penautbankpage";
	private static final String homePageCommodities = "commoditylist";
	private static final String NoBackInBroswer = "nobackinbroswer";
	private static final String Received = " Delivered ";
	private static final String Dispatched = " On Delivery ";
	private static final int pageSizeForTransactions = 4;
	private static final int pageSizeForCommodities = 4;
	private static final String commodityAlreadyExistsMessage = "The same commodity already exists!";
	private static final String currentCommodityName = "currentcommodityname";
	private static final String PageInfo = "pageinfo";
	private static final String commodityId = "commodityid";
	private static final String page = "page";
	private static final String currentPage = "currentpage";
	private static final String commodityList = "commoditylist";
	private static final String useremail = "useremail";
	private static final String username = "username";
	private static final String initialVersion = "1";
	private static final String initialSales = "0";
	private static final String addImagesMessage = "imagesmessage";
	private static final String addImagesSuccess = "Images uploaded successfully!";
	private static final String addImagesFailure = "Images uploaded unsuccessfully!";
	private static final String commodityErrorMessage = "commodityerror";
	private static final String firstPage = "ecommercehomepage";
	private static final String addCommodityPage = "commoditypage";
	private static final String userAddressPage = "useraddresspage";
	private static final String updateCommodityPage = "updatecommoditypage";
	private static final String addCommodityImagesPage = "commodityimages";
	private static final String updateCommodityImagesPage = "updatecommodityimages";
	private static final String userPage = "userprofile";
	private static final String dashboardPage = "dashboard";
	private static final String userBoughtPage = "userboughtpage";
	private static final String userSoldPage = "usersoldpage";
	private static final String avatarPage = "avatar";
	private static final String newAddressPage = "newaddresspage";
	private static final String updateError = "error";
	private static final String updateUserSuccessMessage = "Update user profile succesfully";
	private static final String hashAlgorithmName = "MD5";
	private static final int hashIterations = 5;
	private static final int redisTemplateListTimeout = 1;
	private static final int thumbnailsImageSize = 400;
	private static final int thumbnailsImageSize2 = 80;
	private static final String AddressesList = "addresseslist";
	private static final String TransactionList = "transactionlist";
	private static final String entryPage = "entry";
	private static final String entryPage2 = "entry2";
	private static final String app2Page = "app2page";

	private static Logger logger = Logger.getLogger(UserController.class);
	@Resource
	private SearchUtils searchUtils;
	@Resource
	private UserService userService;
	@Resource
	private CommodityService commodityService;
	@Resource
	private CommodityimageService commodityimageService;
	@Resource
	private AddressService addressService;
	@Resource
	private TransactionService transactionService;
	@Resource
	private AccountService accountService;
	@Autowired
	private RedisTemplate redisTemplate3;
	@Resource
	private ShiroUserUtils shiroUserUtils;
	@Resource
	private PtransactionService ptransactionService;

	public UserController() {
		// TODO Auto-generated constructor stub
	}

	// @RequestMapping(value = "/logout")
	// public String logout(ModelMap modelMap, HttpSession httpSession) {
	// Subject subject = SecurityUtils.getSubject();
	// subject.logout();
	// httpSession.removeAttribute("appchosen");
	// return entryPage2;
	// }

	@RequestMapping(value = "/usercommodityimages")
	public String toCommodityImagespage(ModelMap modelMap) {
		return addCommodityImagesPage;
	}

	@RequestMapping(value = "/usercommodity")
	public String toCommodityPage(ModelMap modelMap) {
		return addCommodityPage;
	}

	@RequestMapping(value = "/turnback")
	public String turnBack(ModelMap modelMap, HttpSession httpSession) {
		Integer chosen = (Integer) httpSession.getAttribute("appchosen");
		if (chosen != null) {
			switch (chosen) {
			case 1:
				return homePage;
			case 2:
				return app2Page;
			default:
				return entryPage2;
			}
		} else {
			return entryPage2;
		}
	}

	@RequestMapping(value = "/firstpage")
	public String toFirstPage(ModelMap modelMap) {
		// 有缓存
		Integer countall = commodityService.countAll();
		Integer pages = countall / 6;
		Integer random = (int) ((Math.random()) * pages);
		// 随机取6个，假装有推荐算法
		// 默认按销量排序
		List<Commodity> commoditylist = commodityService.selectForHomePage(random, 6);
		LinkedList<Commodity> commoditylinkedlist = new LinkedList<>(commoditylist);
		LinkedList<CommodityWithRate> commoditywithratelist;
		commoditywithratelist = searchUtils.rateListForCommodities(commoditylinkedlist);
		modelMap.addAttribute(homePageCommodities, commoditywithratelist);
		modelMap.addAttribute(currentNumberOfCommodities, countall);
		return homePage;
	}

	@RequestMapping(value = "/peanutbank")
	public String toPenautbank(ModelMap modelMap) {
		User currentuser = shiroUserUtils.getCurrentUser();
		Long currentid = currentuser.getUserid();
		Account account = accountService.selectByUserId(currentid);
		List<Ptransaction> list = ptransactionService.selectByUserId(currentid);
		modelMap.addAttribute("plist", list);
		modelMap.addAttribute("user", currentuser);
		modelMap.addAttribute("account", account);
		return penautBankPage;
	}

	@RequestMapping(value = "/userprofile")
	public String toUserProfile(ModelMap modelMap) {
		Subject subject = SecurityUtils.getSubject();
		String currentusername = (String) subject.getPrincipal();
		User currentuser = userService.selectByUserName(currentusername);
		HashMap<String, String> responsemap = new HashMap<>();
		responsemap.put(username, currentuser.getUsername());
		responsemap.put(useremail, currentuser.getUseremail());
		modelMap.addAllAttributes(responsemap);
		return userPage;
	}

	@RequestMapping(value = "/userdashboard", method = { RequestMethod.PUT, RequestMethod.GET, RequestMethod.POST })
	public String toUserDashboard(ModelMap modelMap,
			@RequestParam(value = "pageinfo", required = false, defaultValue = "1") Integer page) {
		Subject subject = SecurityUtils.getSubject();
		String currentusername = (String) subject.getPrincipal();
		User currentuser = userService.selectByUserName(currentusername);
		// Commodities pagehelper
		PageHelper.startPage(page.intValue(), pageSizeForCommodities);
		Long currentuserid = currentuser.getUserid();
		logger.info("Current User Id in dashboard: " + currentuserid);
		List<Commodity> commoditylist = commodityService.selectByOwnerId(currentuserid);
		PageInfo<Commodity> pageInfo = new PageInfo<>(commoditylist);
		HashMap<String, String> responsemap = new HashMap<>();
		responsemap.put(username, currentuser.getUsername());
		responsemap.put(useremail, currentuser.getUseremail());
		modelMap.addAllAttributes(responsemap);
		modelMap.addAttribute(PageInfo, pageInfo);
		modelMap.addAttribute(commodityList, commoditylist);
		modelMap.addAttribute(currentPage, page);

		return dashboardPage;
	}

	@RequestMapping(value = "/avatarpage")
	public String toAvatarPage(ModelMap modelMap) {
		return avatarPage;
	}

	@RequestMapping(value = "/useravatar")
	public void showUserAvatar(HttpServletResponse response, HttpServletRequest request, HttpSession httpsession,
			ModelMap modelMap) {
		Subject subject = SecurityUtils.getSubject();
		String avataruser = subject.getPrincipal().toString();
		User user = userService.selectByUserName(avataruser);
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

	@RequestMapping(value = "/commodityimage/{commodityid}")
	public void showCommodityImage(HttpServletResponse response, HttpServletRequest request, HttpSession httpsession,
			ModelMap modelMap, @PathVariable(value = "commodityid", required = false) String commodityid) {
		// Subject subject = SecurityUtils.getSubject();
		// String avataruser = subject.getPrincipal().toString();
		// User user = userService.selectByUserName(avataruser);
		Long commodityid2 = Long.parseLong(commodityid);
		LinkedList<Commodityimage> linkedList = new LinkedList<>(
				commodityimageService.selectByCommodityId(commodityid2));
		byte[] imagebyte = null;
		if (linkedList.size() == 0) {
			imagebyte = null;
		} else {
			imagebyte = linkedList.get(0).getCommodityimg();
		}
		// byte[] imagebyte = user.getUseravatar();
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

	@RequestMapping(value = "/logout", method = { RequestMethod.PUT, RequestMethod.GET, RequestMethod.POST })
	public String userLogout(ModelMap modelMap, HttpSession httpSession) {
		Subject subject = SecurityUtils.getSubject();
		// httpSession.removeAttribute("appchosen");
		// if (subject.isAuthenticated()) {
		// subject.logout();
		// }
		return entryPage2;

	}

	@RequestMapping(value = "/logout2", method = { RequestMethod.PUT, RequestMethod.GET, RequestMethod.POST })
	public String userLogout2(ModelMap modelMap, HttpSession httpSession) {
		Subject subject = SecurityUtils.getSubject();
		// httpSession.removeAttribute("appchosen");
		if (subject.isAuthenticated()) {
			subject.logout();
		}
		return entryPage2;

	}

	@RequestMapping(value = "/update", method = { RequestMethod.PUT })
	public @ResponseBody HashMap<String, String> updateUser(@RequestBody @Valid UpdateUser updateuser, Errors errors,
			ModelMap modelMap) {
		HashMap<String, String> responsemap = new HashMap<String, String>();
		if (errors.hasErrors()) {
			List<FieldError> list = errors.getFieldErrors();
			Iterator<FieldError> iterator = list.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				FieldError fieldError = iterator.next();
				String fie = fieldError.getField();
				String meString = fieldError.getDefaultMessage();
				String errorMessage = fie.concat(meString);
				responsemap.put(updateError + String.valueOf(i), errorMessage);
				i++;
			}
			return responsemap;
		}
		Subject subject = SecurityUtils.getSubject();
		String oldusername = (String) subject.getPrincipal();
		User olduser = userService.selectByUserName(oldusername);
		olduser.setUsername(updateuser.getUsername());
		olduser.setUseremail(updateuser.getUseremail());
		String credentials = updateuser.getUsername();
		ByteSource credentialsSalt = ByteSource.Util.bytes(credentials);
		SimpleHash encryptPassword = new SimpleHash(hashAlgorithmName, updateuser.getUserpassword(), credentialsSalt,
				hashIterations);
		String pswd = encryptPassword.toString();
		olduser.setUserpassword(pswd);
		User olduser2 = olduser;
		try {
			userService.updateUserWithLock(olduser.getUserid(), olduser2);
			logger.info("updateUserWithLock executed");
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("updateUserWithLock Exception");
		}
		responsemap.put(updateError + String.valueOf(0), updateUserSuccessMessage);
		return responsemap;
	}

	@RequestMapping(value = { "/avatar" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST,
			org.springframework.web.bind.annotation.RequestMethod.GET })
	public @ResponseBody HashMap<String, String> updateAvatar(HttpServletRequest request, ModelMap modelMap,
			@RequestParam(value = "fileinput1[]", required = false) MultipartFile[] uploadavatars,
			RedirectAttributes attrs) throws IOException {
		Subject subject = SecurityUtils.getSubject();
		String oldusername = (String) subject.getPrincipal();
		User olduser = userService.selectByUserName(oldusername);
		MultipartFile uploadavatar = uploadavatars[0];
		InputStream inputStream = uploadavatar.getInputStream();
		byte[] avatarByte = new byte[(int) uploadavatar.getSize()];
		inputStream.read(avatarByte);
		olduser.setUseravatar(avatarByte);
		this.userService.updateByPrimaryKey(olduser);
		HashMap<String, String> responsemap = new HashMap<String, String>();
		return responsemap;
	}

	@RequestMapping(value = { "/commodity" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST,
			org.springframework.web.bind.annotation.RequestMethod.GET })
	public String addCommodity(HttpServletResponse response, HttpServletRequest request, HttpSession httpSession,
			ModelMap modelMap, @Valid Commodity commodity, Errors errors) {
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
			modelMap.addAttribute(commodityErrorMessage, msgs);
			logger.info("Input errors happened.");
			return addCommodityPage;
		} else {
			Subject subject = SecurityUtils.getSubject();
			String username = (String) subject.getPrincipal();
			User user = userService.selectByUserName(username);
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 可以方便地修改日期格式
			String newdate = dateFormat.format(date);
			Commodity commodity2 = new Commodity();
			commodity2 = commodity;
			commodity2.setCommoditydate(newdate);
			commodity2.setCommodityownerid(user.getUserid());
			commodity2.setCommoditysales(Double.parseDouble(initialSales));
			commodity2.setVersion(Long.parseLong(initialVersion));
			long potentialid = commodityService.returnPrimaryKeyByCommodity(commodity2);
			logger.info("Id of existed commodity: " + String.valueOf(potentialid));
			if (String.valueOf(potentialid).equals("0")) {
				commodityService.insertThenCachePut(commodity2);
				logger.info("commodityService.insert executed");
				long findid = commodityService.returnPrimaryKeyByCommodity(commodity2);
				logger.info("returnPrimaryKeyByCommodity executed, commodity ID:" + findid);
				redisTemplate3.opsForValue().set(username, String.valueOf(findid));
				return addCommodityImagesPage;
			} else {
				ArrayList<String> msgs = new ArrayList<String>();
				msgs.add(commodityAlreadyExistsMessage);
				modelMap.addAttribute(commodityErrorMessage, msgs);
				logger.info("The new commodity that you trying to insert already exists.");
				return addCommodityPage;
			}

		}
	}

	@RequestMapping(value = { "/commodityimages" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.POST,
			org.springframework.web.bind.annotation.RequestMethod.GET })
	public @ResponseBody HashMap<String, String> addCommodityImages(HttpSession httpSession, HttpServletRequest request,
			ModelMap modelMap, @RequestParam(value = "fileinput1[]", required = false) MultipartFile[] uploadavatars,
			RedirectAttributes attrs) throws IOException {

		Subject subject = SecurityUtils.getSubject();
		String redisid = (String) redisTemplate3.opsForValue().get(subject.getPrincipal());
		long currentcommodityid = Long.parseLong(redisid);
		logger.info("key=username, currentcommodityid: " + currentcommodityid);
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 可以方便地修改日期格式
		String newdate = dateFormat.format(date);
		LinkedList<Commodityimage> list = new LinkedList<Commodityimage>();
		int i = 0;
		while (i < uploadavatars.length) {
			MultipartFile uploadavatar = uploadavatars[i];
			InputStream inputStream = new BufferedInputStream(uploadavatar.getInputStream());
			byte[] newbyte = new byte[(int) uploadavatar.getSize()];
			inputStream.read(newbyte);
			Commodityimage commodityimage = new Commodityimage();
			commodityimage.setCommodityid(currentcommodityid);
			commodityimage.setCommodityimg(newbyte);
			commodityimage.setCommodityimgname(uploadavatar.getOriginalFilename());
			commodityimage.setCommodityimgdate(newdate);
			commodityimage.setVersion(Long.parseLong(initialVersion));
			list.add(commodityimage);
			i++;
		}
		commodityimageService.insertMultipleImagesForCommodity(list, currentcommodityid);
		logger.info("insertMultipleImagesForCommodity executed");
		modelMap.addAttribute(addImagesMessage, addImagesSuccess);
		redisTemplate3.delete(subject.getPrincipal());
		HashMap<String, String> responsemap = new HashMap<String, String>();
		return responsemap;
	}

	@RequestMapping(value = { "/updatecommodity/form" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.POST,
			org.springframework.web.bind.annotation.RequestMethod.GET })
	public String updateCommodity(HttpServletResponse response, HttpServletRequest request, HttpSession httpSession,
			ModelMap modelMap, @Valid Commodity commodity, Errors errors) {
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
			modelMap.addAttribute(commodityErrorMessage, msgs);
			return updateCommodityPage;
		} else {
			Subject subject = SecurityUtils.getSubject();
			String username = (String) subject.getPrincipal();
			User user = userService.selectByUserName(username);
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 可以方便地修改日期格式
			String newdate = dateFormat.format(date);
			String currentcommodityid = (String) redisTemplate3.opsForValue()
					.get(SecurityUtils.getSubject().getPrincipal() + commodityId);
			redisTemplate3.delete(SecurityUtils.getSubject().getPrincipal() + commodityId);
			long currentcommodityid2 = Long.parseLong(currentcommodityid);
			redisTemplate3.delete(SecurityUtils.getSubject().getPrincipal() + commodityId);
			Commodity commodity2 = commodityService.selectByPrimaryKey(currentcommodityid2);
			commodity2.setCommoditycategory(commodity.getCommoditycategory());
			commodity2.setCommodityquantity(commodity.getCommodityquantity());
			commodity2.setCommodityprice(commodity.getCommodityprice());
			commodity2.setCommoditydescription(commodity.getCommoditydescription());
			commodity2.setCommoditylocation(commodity.getCommoditylocation());
			commodity2.setCommodityname(commodity.getCommodityname());
			commodity2.setCommoditydate(newdate);
			commodityService.updateForVersionByPrimaryKeyAndVersion(commodity2, commodity2.getVersion());
			logger.info("commodityService.updateForVersionByPrimaryKeyAndVersion executed");
			// Session session = subject.getSession();
			// session.setTimeout(3600000);
			// Shiro的企业级session管理需要自己去写DAO类，如果不需要就不要配置
			// 尝试放入Redis，而不是使用session
			long findid = commodityService.returnPrimaryKeyByCommodity(commodity2);
			logger.info("returnPrimaryKeyByCommodity executed, commodity ID:" + findid);
			// 考虑极端情况：单个用户在同一个(或多个)浏览器开了多个页面去更新多个商品
			// 使用双向链表，这样更新商品是有顺序区别的
			redisTemplate3.opsForList().leftPush((String) subject.getPrincipal() + "update" + commodityId,
					String.valueOf(findid));
			logger.info("redisTemplate3.opsForList().leftPush() executed");
			// redisTemplate.opsForValue().set((String) subject.getPrincipal() + "update" +
			// commodityId,
			// String.valueOf(findid));a
			// 在另一个controller里subject.getSession()得到的session ,id不同无法传值
			// 1.如果为shiro配置了SessionDAO，需要手写；不用的话不要配置
			// 2.浏览器禁用了cookie.
			// session.setAttribute("currentcommodityid", findida);
			// logger.info("shiro session id: " + session.getId());
			// logger.info("session Attribute \"currentcommodityid\" " +
			// session.getAttribute("currentcommodityid"));
			modelMap.addAttribute(currentCommodityName, commodity2.getCommodityname());
			return updateCommodityImagesPage;
		}
	}

	@RequestMapping(value = { "/updatecommodityimages" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.POST,
			org.springframework.web.bind.annotation.RequestMethod.GET })
	public @ResponseBody HashMap<String, String> updateCommodityImages(HttpSession httpSession,
			HttpServletRequest request, ModelMap modelMap,
			@RequestParam(value = "fileinput1[]", required = false) MultipartFile[] uploadavatars,
			RedirectAttributes attrs) throws IOException {
		Subject subject = SecurityUtils.getSubject();
		// 使用参数超时时间作为阻塞命令区分，等价于brpop
		String redisid = (String) redisTemplate3.opsForList().rightPop(
				(String) subject.getPrincipal() + "update" + commodityId, redisTemplateListTimeout, TimeUnit.SECONDS);
		logger.info("redisTemplate3.opsForList().rightPop executed, current commodityid: " + redisid);
		// 及时删除
		redisTemplate3.delete((String) subject.getPrincipal() + "update" + commodityId);
		// String redisid = (String) redisTemplate.opsForValue()
		// .get((String) subject.getPrincipal() + "update" + commodityId);
		// redisTemplate.delete(username + "update" + commodityId);
		long currentcommodityid = Long.parseLong(redisid);
		logger.info("key=username, currentcommodityid: " + currentcommodityid);
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 可以方便地修改日期格式
		String newdate = dateFormat.format(date);
		LinkedList<Commodityimage> list = new LinkedList<Commodityimage>();
		int i = 0;
		while (i < uploadavatars.length) {
			MultipartFile uploadavatar = uploadavatars[i];
			InputStream inputStream = new BufferedInputStream(uploadavatar.getInputStream());
			byte[] newbyte = new byte[(int) uploadavatar.getSize()];
			inputStream.read(newbyte);
			Commodityimage commodityimage = new Commodityimage();
			commodityimage.setCommodityid(currentcommodityid);
			commodityimage.setCommodityimg(newbyte);
			commodityimage.setCommodityimgname(uploadavatar.getOriginalFilename());
			commodityimage.setCommodityimgdate(newdate);
			commodityimage.setVersion(Long.parseLong(initialVersion));
			list.add(commodityimage);
			i++;
		}
		commodityimageService.deleteByCommodityIdThenInsertMultipleImagesForCommodity(currentcommodityid, list);
		logger.info("deleteByCommodityIdThenInsertMultipleImagesForCommodity executed");
		modelMap.addAttribute(addImagesMessage, addImagesSuccess);
		HashMap<String, String> responsemap = new HashMap<String, String>();
		return responsemap;
	}

	@RequestMapping(value = "/deletecommodity", method = { org.springframework.web.bind.annotation.RequestMethod.POST,
			org.springframework.web.bind.annotation.RequestMethod.GET })
	public String deletecommodity(ModelMap modelMap,
			@RequestParam(value = "commodityid", required = false) long commodityid,
			@RequestParam(value = "currentpage", required = false, defaultValue = "1") Integer currentpage,
			RedirectAttributes redirectAttributes) {
		commodityService.deleteByPrimaryKey(commodityid);
		redirectAttributes.addFlashAttribute(page, currentpage);
		return "redirect:/user/userdashboard";
	}

	@RequestMapping(value = "/updatecommodity", method = { org.springframework.web.bind.annotation.RequestMethod.POST,
			org.springframework.web.bind.annotation.RequestMethod.GET })
	public String updateCommodity(ModelMap modelMap,
			@RequestParam(value = "commodityid", required = false) long commodityid,
			RedirectAttributes redirectAttributes) {
		redisTemplate3.opsForValue().set(SecurityUtils.getSubject().getPrincipal() + commodityId,
				String.valueOf(commodityid));
		modelMap.addAttribute("commodityname", commodityService.selectByPrimaryKey(commodityid).getCommodityname());
		return updateCommodityPage;
	}

	@RequestMapping(value = "/useraddress")
	public String toUserAddress(ModelMap modelMap) {
		String currentusername = (String) SecurityUtils.getSubject().getPrincipal();
		User currentuser = userService.selectByUserName(currentusername);
		LinkedList<sheffield.mbg.pojo.Address> linkedList = new LinkedList<>(
				addressService.selectByUserId(currentuser.getUserid()));
		modelMap.addAttribute(AddressesList, linkedList);
		return userAddressPage;
	}

	@RequestMapping(value = "/useraddress/remove")
	public String removeUserAddress(@RequestParam(value = "addressid", required = true) String removedaddressid) {
		addressService.deleteByPrimaryKey(Long.parseLong(removedaddressid));
		return "redirect:/user/useraddress";
	}

	@RequestMapping(value = "/useraddress/newaddresspage")
	public String toNewAddressPage(ModelMap modelMap) {
		return newAddressPage;
	}

	@RequestMapping(value = "/useraddress/newaddress")
	public String newAddress(ModelMap modelMap,
			@RequestParam(value = "addressdescription", required = true) String addressdescription) {
		String currentusername = (String) SecurityUtils.getSubject().getPrincipal();
		User currentuser = userService.selectByUserName(currentusername);
		sheffield.mbg.pojo.Address address = new sheffield.mbg.pojo.Address();
		address.setAddressdescription(addressdescription);
		address.setUserid(currentuser.getUserid());
		addressService.insert(address);
		return "redirect:/user/useraddress";
	}

	@RequestMapping(value = "/commoditiesbought", method = { RequestMethod.PUT, RequestMethod.GET, RequestMethod.POST })
	public String viewCommoditiesBought(ModelMap modelMap,
			@RequestParam(value = "pageinfo", required = false, defaultValue = "1") Integer page) {
		Subject subject = SecurityUtils.getSubject();
		String currentusername = (String) subject.getPrincipal();
		User currentuser = userService.selectByUserName(currentusername);
		// Transactions pagehelper
		PageHelper.startPage(page.intValue(), pageSizeForTransactions);
		// 卖家的交易列表
		List<Transaction> list = transactionService.selectByUserIdOrderByTransactionDate(currentuser.getUserid());
		ArrayList<Transaction> transactionlist = new ArrayList<>(list);
		PageInfo<Transaction> pageInfo = new PageInfo<>(list);
		modelMap.addAttribute(TransactionList, transactionlist);
		modelMap.addAttribute(PageInfo, pageInfo);
		HashMap<String, String> responsemap = new HashMap<>();
		responsemap.put(username, currentuser.getUsername());
		responsemap.put(useremail, currentuser.getUseremail());
		modelMap.addAllAttributes(responsemap);
		return userBoughtPage;
	}

	@RequestMapping(value = "/commoditiessold", method = { RequestMethod.PUT, RequestMethod.GET, RequestMethod.POST })
	public String viewCommoditiesSold(ModelMap modelMap,
			@RequestParam(value = "pageinfo", required = false, defaultValue = "1") Integer page) {
		Subject subject = SecurityUtils.getSubject();
		String currentusername = (String) subject.getPrincipal();
		User currentuser = userService.selectByUserName(currentusername);
		// Transactions pagehelper
		PageHelper.startPage(page.intValue(), pageSizeForTransactions);
		// 买家的交易列表
		List<Transaction> list = transactionService
				.selectByCommodityOwnerIdOrderByTransactionDate(currentuser.getUserid());
		ArrayList<Transaction> transactionlist = new ArrayList<>(list);
		PageInfo<Transaction> pageInfo = new PageInfo<>(list);
		modelMap.addAttribute(TransactionList, transactionlist);
		modelMap.addAttribute(PageInfo, pageInfo);
		HashMap<String, String> responsemap = new HashMap<>();
		responsemap.put(username, currentuser.getUsername());
		responsemap.put(useremail, currentuser.getUseremail());
		modelMap.addAllAttributes(responsemap);
		return userSoldPage;
	}

	@RequestMapping(value = "/dispatch", method = { RequestMethod.PUT, RequestMethod.GET, RequestMethod.POST })
	public String dispatchCommodity(ModelMap modelMap,
			@RequestParam(value = "transactionid", required = true) String transactionid) {
		Subject subject = SecurityUtils.getSubject();
		String currentusername = (String) subject.getPrincipal();
		User currentuser = userService.selectByUserName(currentusername);
		Transaction currentTransaction = transactionService.selectByPrimaryKey(Long.parseLong(transactionid));
		// 设置交易状态为发货
		currentTransaction.setTransactionstatus(Dispatched);
		transactionService.updateByPrimaryKey(currentTransaction);
		return "redirect:/user/commoditiessold";
	}

	@RequestMapping(value = "/reception", method = { RequestMethod.PUT, RequestMethod.GET, RequestMethod.POST })
	public String receiveCommodity(ModelMap modelMap,
			@RequestParam(value = "transactionid", required = true) String transactionid) {
		Subject subject = SecurityUtils.getSubject();
		String currentusername = (String) subject.getPrincipal();
		User currentuser = userService.selectByUserName(currentusername);
		Transaction currentTransaction = transactionService.selectByPrimaryKey(Long.parseLong(transactionid));
		// 设置交易状态为收货
		currentTransaction.setTransactionstatus(Received);
		transactionService.updateByPrimaryKey(currentTransaction);
		return "redirect:/user/commoditiesbought";
	}

}
