package sheffield.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sheffield.mbg.pojo.Account;
import sheffield.mbg.pojo.Grabredpacket;
import sheffield.mbg.pojo.RedPacketBySender;
import sheffield.utils.ShiroUserUtils;

@Service("redPacketService")
public class RedPacketServiceImpl implements RedPacketService {

	@Resource
	private AccountService accountService;
	@Resource
	private UserService userService;
	@Resource
	private RedisTemplate redisTemplate3;
	@Resource
	private GrabRedPacketService grabRedPacketService;
	@Resource
	private ShiroUserUtils shiroUserUtils;

	private static final String RedisCurrentBalance = "_currentbalance";
	private static final String RedisSenderHashKey = "_redpacket_sender";
	private static final String RedisSenderListKey = "_redpacket_list";
	private static final String redissum = "sum";
	private static final String redisamount = "amount";
	private static final String redisnote = "note";
	private static final String redistime = "time";
	private static final String redistimes = "times";
	private static final String redissendername = "sendername";
	private static Logger logger = Logger.getLogger(RedPacketServiceImpl.class);

	public RedPacketServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public int sendRedPacketThenUpdate(Long userid, String redpackettotalsum, String redpacketamount,
			String redpacketnote) {
		Integer sum = Integer.parseInt(redpackettotalsum);
		Double amount = Double.parseDouble(redpacketamount);
		Account account = accountService.selectByUserId(userid);
		// User currentuser = userService.selectByPrimaryKey(userid);
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String currentdate = dateFormat.format(date);
		Double oldbalance = account.getPeanut();
		// 发红包后相应地减余额
		Double newbalance = oldbalance - amount * sum;
		account.setPeanut(newbalance);
		// 更新账户
		int result = accountService.updateByPrimaryKey(account);
		logger.info("After accountService.updateByPrimaryKey(account) 余额减去红包总价");

		// 暂时存入Redis
		String rediskeyforbalance = String.valueOf(userid) + RedisCurrentBalance;

		// SessionCallback sessionCallback2 = new SessionCallback() {
		// @Override
		// public Long execute(RedisOperations operations) throws DataAccessException {
		// String tempkey = userid + RedisSenderListKey;
		// Long listsize = null;
		// if (operations.hasKey(tempkey)) {
		// // 有key,双向链表右边加上第n次发红包,把size+1字符串放进去
		// listsize = operations.opsForList().size(tempkey);
		// operations.opsForList().rightPush(tempkey, String.valueOf(listsize + 1));
		// logger.info("链表推入listsize + 1");
		//
		// } else {
		// // 无key，双向链表左边加1 leftPush
		// operations.opsForList().rightPush(tempkey, String.valueOf(1));
		// listsize = (long) 1;
		// logger.info("链表推入1");
		// }
		// return listsize;
		// }
		// };
		// logger.info("Before redisTemplate.execute(sessionCallback2)");
		// Long listsize2 = (Long) redisTemplate.execute(sessionCallback2);

		// 存入HASH结构

		SessionCallback sessionCallback = new SessionCallback() {
			@Override
			public Object execute(RedisOperations operations) throws DataAccessException {

				String tempkey = userid + RedisSenderListKey;
				Long listsize = null;
				if (operations.hasKey(tempkey)) {
					// 有key,双向链表右边加上第n次发红包,把size+1字符串放进去
					listsize = operations.opsForList().size(tempkey);
					operations.opsForList().rightPush(tempkey, String.valueOf(listsize + 1));
					listsize = listsize + 1;
					logger.info("链表推入listsize + 1");

				} else {
					// 无key，双向链表左边加1 leftPush
					operations.opsForList().rightPush(tempkey, String.valueOf(1));
					listsize = (long) 1;
					logger.info("链表推入1");
				}
				String listsizestr = String.valueOf(listsize);
				logger.info("链表长度： " + listsizestr);
				String rediskey = String.valueOf(userid) + "_" + listsizestr + RedisSenderHashKey;
				// Redis 的CAS 不会有ABA问题，见书
				operations.watch(rediskey);
				operations.multi();
				operations.opsForHash().put(rediskey, redissum, redpackettotalsum);
				operations.opsForHash().put(rediskey, redistimes, listsizestr);
				operations.opsForHash().put(rediskey, redisamount, redpacketamount);
				operations.opsForHash().put(rediskey, redisnote, redpacketnote);
				operations.opsForHash().put(rediskey, redistime, currentdate);
				operations.opsForHash().put(rediskey, redissendername, shiroUserUtils.getCurrentUser().getUsername());
				operations.opsForValue().set(rediskeyforbalance, String.valueOf(newbalance));
				operations.exec();
				return null;
			}
		};
		// 流水线可以减少通信次数，提高效率,返回List是执行命令的返回值集合
		// redisTemplate.executePipelined(sessionCallback);
		redisTemplate3.execute(sessionCallback);
		// redisTemplate.execute(sessionCallback);
		return result;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	// 如果缓存了，新添加的红包会不会无法显示? 缓存设置了5分钟
	// @Cacheable(value = "redisCacheManager", key =
	// "'redis_cache_findallredpackets'")
	public List<RedPacketBySender> findAllRedPackets() {
		// selectAll感觉不好
		List<Long> list = userService.selectAllUserId();
		Iterator<Long> iterator = list.iterator();
		ArrayList<RedPacketBySender> arrayList = new ArrayList<>();
		SessionCallback sessionCallback = new SessionCallback() {
			@Override
			public Object execute(RedisOperations operations) throws DataAccessException {
				// 循环
				while (iterator.hasNext()) {
					Long userid = iterator.next();
					String tempkey = String.valueOf(userid) + RedisSenderListKey;
					if (operations.hasKey(tempkey)) {
						Long listsize = operations.opsForList().size(tempkey);
						logger.info(" operations.opsForList().size(tempkey): " + listsize);
						for (int i = 0; i < listsize; i++) {
							// String listnumber = (String) operations.opsForList().leftPop(tempkey);
							// 左边pop出第几次的n，拼接字符串作为哈希结构的key
							String listnumber = (String) operations.opsForList().leftPop(tempkey);
							// 还得从右边Push回去，否则下一次刷新就没了!
							// 蛋疼，早知道直接链表里存对象了，得改配置里的jdkSerializationRedisSerializer
							// 更蛋疼的是，早知如此就应该直接用Lua写了
							operations.opsForList().rightPush(tempkey, listnumber);
							logger.info("operations.opsForList().leftPop(tempkey): " + listnumber);
							String rediskey = String.valueOf(userid) + "_" + listnumber + RedisSenderHashKey;
							logger.info("rediskey: " + rediskey);
							// 事务开始
							operations.watch(rediskey);
							operations.multi();
							operations.opsForHash().get(rediskey, redissum);
							operations.opsForHash().get(rediskey, redisamount);
							operations.opsForHash().get(rediskey, redisnote);
							operations.opsForHash().get(rediskey, redistime);
							operations.opsForHash().get(rediskey, redissendername);
							operations.opsForHash().get(rediskey, redistimes);
							List listexec = operations.exec();
							// 在Redis事务提交前取不到值！
							RedPacketBySender redPacketBySender = new RedPacketBySender();
							if ((String) (listexec.get(4)) == null) {
								// 如果用户没有发过红包，跳过循环
								continue;
							}

							redPacketBySender.setTotalsum((String) listexec.get(0));
							redPacketBySender.setAmount((String) listexec.get(1));
							redPacketBySender.setNote((String) listexec.get(2));
							redPacketBySender.setTime((String) listexec.get(3));
							redPacketBySender.setSendername((String) listexec.get(4));
							redPacketBySender.setTimes((String) listexec.get(5));
							logger.info("(Should not be null) Big red packet sender name: " + (String) listexec.get(4));
							logger.info(rediskey + " Before arrayList.add(redPacketBySender)");
							arrayList.add(redPacketBySender);
						}
					} else {
						continue;
					}
				}
				// 循环结束
				return null;

			}
		};
		// 流水线可以减少通信次数，提高效率
		// List<RedPacketBySender> redislist =
		// redisTemplate.executePipelined(sessionCallback);
		redisTemplate3.execute(sessionCallback);
		// redisTemplate.executePipelined(sessionCallback);
		logger.info("findAllRedPackets() redisTemplate.executePipelined(sessionCallback);");
		return arrayList;

	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	@Async
	public Integer grabRedPacketByRecipient(Long senderid, Long recipientid, String times) {
		// String rediskey = String.valueOf(senderid) + RedisSenderHashKey;
		String rediskey = String.valueOf(senderid) + "_" + times + RedisSenderHashKey;
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String currentdate = dateFormat.format(date);
		Grabredpacket grabredpacket = new Grabredpacket();
		String rediskeyforbalance = String.valueOf(recipientid) + RedisCurrentBalance;
		// 原版,不使用pipeline或multi,exec
		// SessionCallback sessionCallback = new SessionCallback() {
		// @Override
		// public Object execute(RedisOperations operations) throws DataAccessException
		// {
		// // operations.multi();
		// String redpackettotalsum = null;
		// String redpacketamount = null;
		// String redpacketnote = null;
		// String redpacketsendername = null;
		// redpackettotalsum = (String) operations.opsForHash().get(rediskey, redissum);
		// logger.info(" grabRedPacketByRecipient redpackettotalsum: " +
		// redpackettotalsum);
		// // Redis中发送的红包库存减一
		// operations.opsForHash().increment(rediskey, redissum, -1);
		// // operations.opsForHash().put(rediskey, redissum,
		// // String.valueOf(Integer.parseInt(redpackettotalsum) - 1));
		// redpacketamount = (String) operations.opsForHash().get(rediskey,
		// redisamount);
		// redpacketnote = (String) operations.opsForHash().get(rediskey, redisnote);
		// redpacketsendername = (String) operations.opsForHash().get(rediskey,
		// redissendername);
		// grabredpacket.setReapacketnote(redpacketnote);
		// grabredpacket.setRecipientid(recipientid);
		// grabredpacket.setRecipientid(recipientid);
		// grabredpacket.setRedpacketmoney(Double.parseDouble(redpacketamount));
		// grabredpacket.setRedpackettime(currentdate);
		// grabredpacket.setSenderid(senderid);
		// // operations.exec();
		// return null;
		// }
		// };

		SessionCallback sessionCallback = new SessionCallback() {
			@Override
			public Object execute(RedisOperations operations) throws DataAccessException {
				// 同时开启事务和pipeline
				operations.watch(rediskey);
				operations.multi();
				// Redis中发送的红包库存减一
				operations.opsForHash().get(rediskey, redissum);
				Long increresult = operations.opsForHash().increment(rediskey, redissum, -1);
				logger.info("(Null?)  grabRedPacketByRecipient operations.opsForHash().increment: Return Double "
						+ increresult);
				operations.opsForHash().get(rediskey, redisamount);
				operations.opsForHash().get(rediskey, redisnote);
				operations.opsForHash().get(rediskey, redissendername);

				operations.exec();
				return null;
			}
		};

		// redisTemplate.execute(sessionCallback);
		// executePipelined返回List<ArrayList>,ArrayList里才是执行多条命令返回的结果。pipeline里应该可以执行多次事务？
		List<ArrayList> listpipeline = redisTemplate3.executePipelined(sessionCallback);
		String leftsum = (String) listpipeline.get(0).get(0);
		Integer leftsumint = Integer.parseInt(leftsum);
		if ((leftsumint <= 0) || ((leftsumint - 1) < 0)) {
			// 没有库存或减去一后小于0
			Integer result = -1;
			return result;
		} else {
			String tempmoney = (String) listpipeline.get(0).get(2);
			grabredpacket.setRedpacketmoney(Double.parseDouble(tempmoney));
			grabredpacket.setReapacketnote((String) listpipeline.get(0).get(3));
			grabredpacket.setRecipientid(recipientid);
			grabredpacket.setRedpackettime(currentdate);
			grabredpacket.setSenderid(senderid);

			// 插入抢红包记录
			Integer result = grabRedPacketService.insert(grabredpacket);
			// 更新抢红包用户账户余额
			Account recipientaccount = accountService.selectByUserId(recipientid);
			Double newbalance = recipientaccount.getPeanut() + grabredpacket.getRedpacketmoney();
			recipientaccount.setPeanut(newbalance);
			redisTemplate3.opsForValue().set(rediskeyforbalance, String.valueOf(newbalance));
			accountService.updateByPrimaryKey(recipientaccount);
			return result;

		}

	}
}
