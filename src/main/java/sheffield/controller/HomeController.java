package sheffield.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.json.Json;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.core.MultivaluedMap;

import com.google.common.collect.Multimap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sheffield.hexu.app2.App2Service;
import sheffield.mbg.pojo.*;
import sheffield.service.AccountService;
import sheffield.service.ActiveMQService;
import sheffield.service.CommodityService;
import sheffield.service.PermissionService;
import sheffield.service.PtransactionService;
import sheffield.service.UserService;
import sheffield.utils.SearchUtils;
import sheffield.utils.ShiroUserUtils;

@Controller
@RequestMapping(value = "/home")
public class HomeController {

	@Resource
	private UserService userService;
	@Resource
	private PermissionService permissionService;
	@Resource
	private AccountService accountService;
	@Resource
	private CommodityService commodityService;
	@Autowired
	@Qualifier("queueDestination")
	private Destination queueDestination;
	@Autowired
	private ActiveMQService activeMQService;
	@Resource
	private RedisTemplate redisTemplate;
	@Resource
	private SearchUtils searchUtils;
	@Resource
	private ShiroUserUtils shiroUserUtils;
	@Resource
	private PtransactionService ptransactionService;
	// 我 是 你 的 业 务 类！
	// App2用的service类 sheffield.hexu.app2 建议直接idea搜索。。
	@Resource
	private App2Service app2Service;

	private static final String app2Page = "app2page";
	private static final String idOfProviderHYC = "5";
	private static final String idOfProviderHX = "6";
	private static final String peautsUsed = "5";
	private static final String loginPage = "login";
	private static final String homePage = "ecommercehomepage";
	private static final String currentNumberOfCommodities = "commoditiesnumber";
	private static final String homePageCommodities = "commoditylist";
	private static final String loginErrorMessage = "loginError";
	private static final String registerErrorMessage = "registerError";
	private static final String userExistsMessage = "Username already taken!";
	private static final String registerMessage = "registerSuccess";
	private static final String registerSuccessMessage = "Register your account successfully!";
	private static final String loginErrorPage = "homeerrors";
	private static final String registerPage = "register";
	private static final String entryPage = "entry";
	private static final String entryPage2 = "entry2";
	private static final String rememberMe = "rememberme";
	private static final String remember = "remember";
	private static final String shiroException = "shiroexception";
	private static final String unknownAccount = "Unknown account";
	private static final String incorrectPassword = "Incorrect password";
	private static final String concurrentAccess = "Concurrent access exception";
	private static final String hashAlgorithmName = "MD5";
	private static final int hashIterations = 5;
	private static final double defaultCredit = 0;
	private static final double defaultPeanut = 10000;

	public HomeController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(value = "/tologin")
	public String toLogin(HttpSession httpSession, ModelMap modelMap, @RequestParam(value = "choose") Integer choose) {
		httpSession.setAttribute("appchosen", choose);
		switch (choose) {
		case 1:
			return loginPage;
		case 2:
			return loginPage;
		default:
			return loginPage;
		}

	}


	@RequestMapping(value="/app2controller")
	public String app2Controller(HttpSession httpSession, ModelMap modelMap) throws IOException {

		return app2Page;



		/*
		 * 在这里你应该调用业务类及其方法，返回比如json或者字符串或者list或者hashmap，
		 * 如果不用ajax就用modelmap返回给前端页面，同时此方法返回String，即返回的页面的名字，在view文件夹下。
		 * 我的设置是采用freemarker> jsp,但是eclipse太烂了对freemarker的提示不够好所以我没写freemarker...
		 * 见mvc-config.xml，这个是spring mvc设置里可以改的。
		 * 如果用ajax就用@ReponseBody,应该可以直接返回json，我以前写过一个返回的hashmap,给解析成json返回了
		 */

		/*
		 * @Resource (你的业务类需要加@Service或者@Component来注册，这边@Resouce才能调用，要不然你手动new一个)
		 * private 你的业务类 业务类变量; 见上头一堆private里我给@Resource了一个
		 * 
		 * @RequestMapping(value = "/app2controller")
		 * 
		 * @ReponseBody public JSONArray app2Controller(HttpSession httpSession,
		 * ModelMap modelMap,@RequestParam("searchText") String text) { JSONArray
		 * jsonArray=业务类变量.啥啥方法（前台接收的参数text）； return JSONArray;}
		 */

		// 这个app2Page就是app2page.jsp, 这是个静态String常量，见上头一堆private
	}

	@RequestMapping(value="/detail",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public HashMap aaa(@RequestParam(value="lat", required = true) String lat,
					   @RequestParam(value="lng", required = true) String lng,
					   @RequestParam(value="radius", required = true) String rad) throws IOException {

		HashMap<String,Restaurant> allRest = new HashMap();
		//This ArrayList is used for help adding Restaurant Object in to the MAP -- allRest

		try {
			//Use the getJSONString method in APP2service to transfer from URL object to String.
			App2Service app2Service = new App2Service();
			String search = app2Service.getJSONstring("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+","+lng+"&radius="+rad+"&type=restaurant&key=AIzaSyBI87YwMOnQw5W3HgQ7A-x60FBNXpR8lKU");


//			老谢菲尔德坐标 String search = app2Service.getJSONstring("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=53.3810000,-1.4790000&radius=1500&type=restaurant&key=AIzaSyBI87YwMOnQw5W3HgQ7A-x60FBNXpR8lKU");
//			悉尼达令港坐标 -33.8670522,151.1957362


			//Transfer String to JSONObject
			JSONObject jsonObject = new JSONObject(search);
			JSONArray Results = null;

			//Access the jsonArray "result" (result包含许多json餐厅对象)
			Results = jsonObject.getJSONArray("results");


			for (int i=0;i<Results.length();){
				Restaurant rest = new Restaurant();

				//Get the first item in the JSONArray, Solve the Results JSONArray
				JSONObject result = (JSONObject) Results.get(i);
				String Name = result.getString("name");
				String Address = result.getString("vicinity");
				String Icon = result.getString("icon");
				String PlaceID = result.getString("place_id");
				//Continue is used to deal the problem -- the short of one rating attribute
				Double rating = null;
				try {
					rating = result.getDouble("rating");
				}
				catch (Exception e){
					continue;
				}
				finally {
					i++;
				}

				//Resolve the lat and lng under the location(which under the geometry)
				JSONObject geo = result.getJSONObject("geometry");
				JSONObject loc = geo.getJSONObject("location");
				Double Lat = loc.getDouble("lat");
				Double Lng = loc.getDouble("lng");

				//Saved result in Restaurant Object
				rest.setName(Name);
				rest.setVicinity(Address);
				rest.setIcon(Icon);
				rest.setPlace_id(PlaceID);
				rest.setRating(rating);
				rest.setLat(Lat);
				rest.setLng(Lng);

				//Solve the photo JSONArray
				JSONArray Photos = result.getJSONArray("photos");
				for (int j=0;j<Photos.length();){
					JSONObject photo = (JSONObject) Photos.get(j);
					String Photo_reference = photo.getString("photo_reference");
					Integer Height = photo.getInt("height");
					Integer Width = photo.getInt("width");

					//saved results in the Restaurant_photo Object
					Restaurant_photo rp = new Restaurant_photo();
					rp.setPhoto_reference(Photo_reference);
					rp.setHeight(Height);
					rp.setWidth(Width);
					//save this Object as a property of Restaurant Object
					rest.setRestaurant_photo(rp);

					j++;
				}

				//Put restaurant's name as key; and put restaurant object as value
				allRest.put(rest.getName(),rest);
			}

		}
		catch (JSONException Json){
			System.out.println("语法错误或有重复的key");
			Json.printStackTrace();
		}

		return allRest;

		/*
		HashMap a = new HashMap();
		a.put("dongfang","五星");
		a.put("lamian","四星");
		return a;
		*/
	}



	@RequestMapping(value = "/turnback")
	public String turnBack(ModelMap modelMap, HttpSession httpSession, @RequestParam("choose") Integer appchosen) {
		httpSession.removeAttribute("appchosen");
		Integer chosen = appchosen;
		httpSession.setAttribute("appchosen", chosen);
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

	@RequestMapping(value = "/login")
	public String login(@Valid User user, Errors errors, ModelMap modelMap,
			@RequestParam(required = false, value = rememberMe, defaultValue = remember) String rememberme,
			HttpSession httpSession) {
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
			modelMap.addAttribute(loginErrorMessage, msgs);
			return loginErrorPage;
		} else {
			String originalPassword = user.getUserpassword();
			String credentials = user.getUsername();
			// JUST NEED TO ENCRYPT PASSWORD IN REALM CLASS
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(credentials, originalPassword);
			try {
				if (rememberme.equals(remember)) {
					token.setRememberMe(true);
				} else {
					token.setRememberMe(false);
				}
				subject.login(token);
			} catch (UnknownAccountException e) {
				// TODO: handle exception
				modelMap.addAttribute(shiroException, unknownAccount);
				return loginErrorPage;
			} catch (IncorrectCredentialsException e) {
				// TODO: handle exception
				modelMap.addAttribute(shiroException, incorrectPassword);
				return loginErrorPage;
			} catch (ConcurrentAccessException e) {
				// TODO: handle exception
				modelMap.addAttribute(shiroException, concurrentAccess);
				return loginErrorPage;
			} catch (AuthenticationException e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
			}

			Integer chose = (Integer) httpSession.getAttribute("appchosen");
			if (chose != null) {
				switch (chose) {
				case 1:
					// app1 by hongyuchen, user id:5
					Long userid = shiroUserUtils.getCurrentUser().getUserid();
					Long providerid = Long.parseLong(idOfProviderHYC);
					Account account = accountService.selectByUserId(userid);
					Account account2 = accountService.selectByUserId(providerid);
					account.setPeanut(account.getPeanut() - Double.parseDouble(peautsUsed));
					account2.setPeanut(account2.getPeanut() - Double.parseDouble(peautsUsed));
					accountService.updateByPrimaryKey(account);
					accountService.updateByPrimaryKey(account2);
					Date date = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 可以方便地修改日期格式
					String newdate = dateFormat.format(date);
					Ptransaction ptransaction = new Ptransaction();
					ptransaction.setAppname("Sheffield Secondhand Book Flea Market");
					ptransaction.setPeanutsused(Double.parseDouble("5"));
					ptransaction.setProviderid(providerid);
					ptransaction.setPtransactiondate(newdate);
					ptransaction.setUserid(userid);
					ptransactionService.insert(ptransaction);
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
					// httpSession.removeAttribute("appchosen");
					return homePage;
				case 2:
					// app 2 by xuhe, user id:6
					// 每个用户使用此app要给何旭交5花生豆的人头税
					Long userid2 = shiroUserUtils.getCurrentUser().getUserid();
					Long providerid2 = Long.parseLong(idOfProviderHX);
					Account account3 = accountService.selectByUserId(userid2);
					Account account4 = accountService.selectByUserId(providerid2);
					account3.setPeanut(account3.getPeanut() - Double.parseDouble(peautsUsed));
					account4.setPeanut(account4.getPeanut() - Double.parseDouble(peautsUsed));
					accountService.updateByPrimaryKey(account3);
					accountService.updateByPrimaryKey(account4);
					Date date2 = new Date();
					SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 可以方便地修改日期格式
					String newdate2 = dateFormat2.format(date2);
					Ptransaction ptransaction2 = new Ptransaction();
					ptransaction2.setAppname("App 2");
					ptransaction2.setPeanutsused(Double.parseDouble("5"));
					ptransaction2.setProviderid(providerid2);
					ptransaction2.setPtransactiondate(newdate2);
					ptransaction2.setUserid(userid2);
					ptransactionService.insert(ptransaction2);
					// app2 减少复杂度，这边交完人头税去重定向去另一个controller里调用业务类及其方法
					return "redirect:/home/app2controller";
				default:
					// httpSession.removeAttribute("appchosen");
					return homePage;
				}
			} else {
				return entryPage2;
			}

			// return entryPage2;
		}
	}

	@RequestMapping(value = "/register")
	public String toRegisterPage(ModelMap modelMap) {
		return registerPage;
	}

	@RequestMapping(value = "/home")
	public String returnHome(ModelMap modelMap) {
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

	@RequestMapping(value = "/loginpage")
	public String toLogin(ModelMap modelMap) {
		return loginErrorPage;
	}

	@RequestMapping(value = "/register/submission")
	public String registerSubmission(@Valid User user, Errors errors, ModelMap modelMap) {
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
			modelMap.addAttribute(registerErrorMessage, msgs);
			return registerPage;
		} else {
			Integer ifexists = (userService.selectExistsByUserName(user.getUsername()));
			if (ifexists == 1) {
				modelMap.addAttribute(registerErrorMessage, userExistsMessage);
				return registerPage;
			} else {
				activeMQService.sendRegisterMessage(queueDestination, user);
				modelMap.addAttribute(registerMessage, registerSuccessMessage);
				return loginErrorPage;

			}

		}
	}
}
