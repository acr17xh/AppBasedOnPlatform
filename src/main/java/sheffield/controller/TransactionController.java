package sheffield.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sheffield.mbg.pojo.Commodity;
import sheffield.mbg.pojo.Transaction;
import sheffield.mbg.pojo.User;
import sheffield.service.AddressService;
import sheffield.service.CommodityService;
import sheffield.service.CommodityimageService;
import sheffield.service.ReviewService;
import sheffield.service.TransactionService;
import sheffield.service.UserService;

@Controller
@RequestMapping(value = "/transaction")
public class TransactionController {

	public TransactionController() {
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
	@Resource
	private TransactionService transactionService;

	private static final String FailedTransactions = "failedtransactions";
	private static final String FailedTransactionsCommodities = "commoditylist";
	private static final String WaitForDispatch = " Wait for dispatch ";
	private static final String BuyerName = "buyername";
	private static final String ShoppingCartTotalCost = "totalcost";
	private static final String ShoppingCartPage = "shoppingcart";
	private static final String ShoppingCartUserRedisKey = "_shoppingCart";
	private static final String TransactionConfirmationPage = "transactionconfirmation";
	private static final int createTransactionSuccessStatus = 100;
	private static Logger logger = Logger.getLogger(BuyerController.class);

	@RequestMapping(value = "/transactionpage")
	public String commitTransaction(ModelMap modelMap,
			@RequestParam(value = "shoppingcartaddress", required = true) String shoppingcartaddress) {
		String currentusername = (String) SecurityUtils.getSubject().getPrincipal();
		User currentuser = userService.selectByUserName(currentusername);
		// 使用当前登录用户名+_shoppingCart作为redis哈希结构的key，防止重复；Redis是在内存中的，应该尽量减小KEY的长度
		String shoppingcartrediskey = String.valueOf(currentuser.getUserid()) + ShoppingCartUserRedisKey;
		String transactionDescription = null;
		HashMap<String, String> cartmap = new HashMap<>();
		SessionCallback sessionCallback = new SessionCallback() {
			@Override
			public HashMap<String, String> execute(RedisOperations operations) throws DataAccessException {
				HashMap<String, String> tempmap = new HashMap<String, String>(
						operations.opsForHash().entries(shoppingcartrediskey));
				operations.delete(shoppingcartrediskey);
				return tempmap;

			}
		};
		cartmap = (HashMap<String, String>) redisTemplate3.execute(sessionCallback);
		ArrayList<String> failedTransactions = new ArrayList<>();
		ArrayList<Long> failedTransactionsid = new ArrayList<>();
		// 遍历从Redis中取出的Map,key是商品id,value是数量
		for (String commodityidkey : cartmap.keySet()) {
			if (commodityidkey.equals(ShoppingCartTotalCost)) {
				continue;
			}
			if (commodityidkey.equals(BuyerName)) {
				// 暂时不删除以buyername为field,用户名为value的哈希结构，以备其他用途
				continue;
			}
			// 判断之前库存减去要买的数量是否为0,小于等于0跳过
			Commodity tempcommodity = commodityService.selectByPrimaryKey(Long.parseLong(commodityidkey));
			if ((tempcommodity.getCommodityquantity()) - (Double.parseDouble(cartmap.get(commodityidkey))) <= 0) {
				failedTransactions.add(tempcommodity.getCommodityname());
				failedTransactionsid.add(tempcommodity.getCommodityownerid());
				continue;
			}
			// 新建Transaction，插入数据库表
			Transaction myTransaction = new Transaction();
			myTransaction.setAddressid(addressService.selectByAddressDescription(shoppingcartaddress).getAddressid());
			myTransaction.setCommodityid(Long.parseLong(commodityidkey));
			myTransaction.setTransactionamount(Double.parseDouble(cartmap.get(commodityidkey)));
			myTransaction.setUserid(userService.selectByUserName(currentusername).getUserid());
			Commodity myCommodity = commodityService.selectByPrimaryKey(Long.parseLong(commodityidkey));
			// 计算本次交易的总价
			Double sumFortransaction = (myCommodity.getCommodityprice()) * (myTransaction.getTransactionamount());
			// 保留小数点后四位
			String formattedSum = String.format("%.4f", sumFortransaction);
			sumFortransaction = Double.parseDouble(formattedSum);
			myTransaction.setTransactionsum(sumFortransaction);
			myTransaction.setTransactionname(myCommodity.getCommodityname());
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 可以方便地修改日期格式
			String newdate = dateFormat.format(date);
			myTransaction.setTransactiondate(newdate);
			myTransaction.setTransactionstatus(WaitForDispatch);
			// 拼接一下交易描述文本
			String tempDescription = "Buyer: " + currentuser.getUsername() + " Email: " + currentuser.getUseremail()
					+ " Amount: " + myTransaction.getTransactionamount() + " Check out: "
					+ myTransaction.getTransactionsum() + " Delievry address: " + shoppingcartaddress + " Status "
					+ myTransaction.getTransactionstatus();
			myTransaction.setTransactiondescription(tempDescription);
			int transactionCommitStatus = transactionService.createTransaction(myTransaction, myTransaction.getUserid(),
					myTransaction.getCommodityid());
			if (transactionCommitStatus != createTransactionSuccessStatus) {
				// 因为乐观锁重入机制失败，把失败的交易商品名称加入arraylist
				failedTransactions.add(tempcommodity.getCommodityname());
				failedTransactionsid.add(tempcommodity.getCommodityownerid());
			}

		}

		// 把失败交易的商品名称ArrayList放进modelMap传给JSP,前端页面稍后写
		ArrayList<Commodity> arrayList2 = new ArrayList<>();
		modelMap.addAttribute(FailedTransactions, failedTransactions);
		Iterator<Long> iterator = failedTransactionsid.iterator();
		while (iterator.hasNext()) {
			arrayList2.add(commodityService.selectByPrimaryKey(iterator.next()));
		}
		modelMap.addAttribute(FailedTransactionsCommodities, arrayList2);
		return TransactionConfirmationPage;
	}

}
