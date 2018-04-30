package sheffield.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import sheffield.mbg.pojo.Account;
import sheffield.mbg.pojo.RedPacketBySender;
import sheffield.mbg.pojo.User;
import sheffield.service.AccountService;
import sheffield.service.AddressService;
import sheffield.service.CommodityService;
import sheffield.service.CommodityimageService;
import sheffield.service.RedPacketService;
import sheffield.service.ReviewService;
import sheffield.service.TransactionService;
import sheffield.service.UserService;
import sheffield.utils.SearchUtils;
import sheffield.utils.ShiroUserUtils;

@Controller
@RequestMapping(value = "/redpacket")
public class RedPacketController {

	@Resource
	private AccountService accountService;
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
	@Resource
	private RedPacketService redPacketService;

	private static Logger logger = Logger.getLogger(RedPacketController.class);

	private static final String RedPacketPage = "redpacketpage";
	private static final String CurrentBalance = "currentbalance";
	private static final String RedisCurrentBalance = "_redis_currentBalance";
	private static final String DefaultRedPacketNote = "Kung Hey Fat Choy!";
	private static final String RedisSenderHashKey = "_redpacket_sender";
	private static final String redissum = "sum";
	private static final String redisamount = "amount";
	private static final String redisnote = "note";
	private static final String SendRedPacketError = "senderdpacketerror";
	private static final String SendRedPacketErrorMessage = "Error happened!";
	private static final String SendRedPacketSuccessMessage = "Red packet sended!";
	private static final String RedPacketList = "redpacketlist";
	private static final String GrabRedPacketError = "grabredpacketerror";
	private static final String GrabRedPacketErrorMessage = "Grabing Red Packet Fails!";
	private static final String GrabRedPacketSuccess = "grabredpacketsuccess";
	private static final String GrabRedPacketSuccessMessage = "Grab Red Packet Successfully!";

	public RedPacketController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(value = "/redpacketpage")
	public String toRedPacketPage(ModelMap modelMap,
			@RequestParam(value = SendRedPacketError, required = false) String sendpacketerrormessage) {
		Long userid = shiroUserUtils.getCurrentUser().getUserid();
		String rediskeyforbalance = String.valueOf(userid) + RedisCurrentBalance;
		// 将抢红包模块中登陆用户的账户余额存入或取出Redis
		BoundValueOperations<String, String> boundValueOperations = redisTemplate3.boundValueOps(rediskeyforbalance);
		Double currentbalance = null;
		if (redisTemplate3.hasKey(rediskeyforbalance)) {
			currentbalance = Double.parseDouble(boundValueOperations.get());

		} else {
			Account account = accountService.selectByUserId(userid);
			currentbalance = account.getPeanut();
			String currentbalancestr = String.valueOf(currentbalance);
			StringBuffer stringBuffer = new StringBuffer(currentbalancestr);
			int endindex = stringBuffer.indexOf(".");
			if (endindex == -1) {
				currentbalancestr = stringBuffer.toString();
			} else {
				currentbalancestr = stringBuffer.substring(0, endindex);
			}
			boundValueOperations.set(currentbalancestr);

		}
		modelMap.addAttribute(CurrentBalance, String.valueOf(currentbalance));
		if (sendpacketerrormessage != null) {
			modelMap.addAttribute(SendRedPacketError, SendRedPacketErrorMessage);
		}

		logger.info("Before findAllRedPackets()");
		// 显示红包表格,放入红包列表
		List<RedPacketBySender> redpacketlist = redPacketService.findAllRedPackets();
		logger.info("/redpacketpage findAllRedPackets() success, size: " + redpacketlist.size());
		modelMap.addAttribute(RedPacketList, redpacketlist);

		return RedPacketPage;
	}

	@RequestMapping(value = "/grab")
	public String grabRedPacket(ModelMap modelMap,
			@RequestParam(value = "sendername", required = true) String sendername,
			@RequestParam(value = "sendertimes", required = true) String sendertimes,
			RedirectAttributes redirectAttributes) {
		Long userid = shiroUserUtils.getCurrentUser().getUserid();
		// 对userName字段加了索引，可能搜索起来效率高一点
		Long senderid = userService.selectByUserName(sendername).getUserid();
		// 得到Redis Hash结构的Key
		Integer result = redPacketService.grabRedPacketByRecipient(senderid, userid, sendertimes);
		if (result == null) {
			redirectAttributes.addFlashAttribute(GrabRedPacketSuccess, GrabRedPacketSuccessMessage);
			return "redirect:/redpacket/redpacketpage";
		} else if ((result == 0) || (result == -1)) {
			redirectAttributes.addFlashAttribute(GrabRedPacketError, GrabRedPacketErrorMessage);
			return "redirect:/redpacket/redpacketpage";
		} else {
			redirectAttributes.addFlashAttribute(GrabRedPacketSuccess, GrabRedPacketSuccessMessage);
			return "redirect:/redpacket/redpacketpage";
		}

	}

	@RequestMapping(value = "/sending")
	public String sendRedPacket(ModelMap modelMap,
			@RequestParam(value = "redpackettotalsum", required = true) String redpackettotalsum,
			@RequestParam(value = "redpacketamount", required = true) String redpacketamount,
			@RequestParam(value = "redpacketnote", required = false, defaultValue = DefaultRedPacketNote) String redpacketnote,
			RedirectAttributes redirectAttributes) {
		Long userid = shiroUserUtils.getCurrentUser().getUserid();
		int result = redPacketService.sendRedPacketThenUpdate(userid, redpackettotalsum, redpacketamount,
				redpacketnote);

		// 发送成功，数据库账户更新并将发送者红包信息存到Redis Hash结构中
		if (result != 0) {
			// redirectAttributes.addFlashAttribute(SendRedPacketError,
			// SendRedPacketErrorMessage);
			logger.info("/sending sendRedPacketThenUpdate success");
			return "redirect:/redpacket/redpacketpage";
		} else {
			redirectAttributes.addFlashAttribute(SendRedPacketError, SendRedPacketErrorMessage);
			logger.info("/sending sendRedPacketThenUpdate failure");
			return "redirect:/redpacket/redpacketpage";
		}

	}

}
