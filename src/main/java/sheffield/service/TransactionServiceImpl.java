package sheffield.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sheffield.mbg.dao.TransactionMapper;
import sheffield.mbg.pojo.Account;
import sheffield.mbg.pojo.Commodity;
import sheffield.mbg.pojo.Transaction;

@Service(value = "transactionService")
public class TransactionServiceImpl implements TransactionService, ApplicationContextAware {

	@Resource
	private TransactionMapper transactionMapper;
	@Resource
	private AccountService accountService;
	@Resource
	private UserService userService;
	@Resource
	private CommodityService commodityService;
	private ApplicationContext ctx = null;
	private static final String createTransactionRetryCount = "10";
	private static final int createTransactionSuccessStatus = 100;

	public TransactionServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int deleteByPrimaryKey(Long transactionid) {
		// TODO Auto-generated method stub
		return transactionMapper.deleteByPrimaryKey(transactionid);
	}

	@Override
	public int insert(Transaction record) {
		// TODO Auto-generated method stub
		return transactionMapper.insert(record);
	}

	@Override
	public Transaction selectByPrimaryKey(Long transactionid) {
		// TODO Auto-generated method stub
		return transactionMapper.selectByPrimaryKey(transactionid);
	}

	@Override
	public List<Transaction> selectAll() {
		// TODO Auto-generated method stub
		return transactionMapper.selectAll();
	}

	@Override
	@Transactional
	public int updateByPrimaryKey(Transaction record) {
		// TODO Auto-generated method stub
		return transactionMapper.updateByPrimaryKey(record);
	}

	@Transactional
	@Override
	public int createTransaction(Transaction record, long buyerid, long commodityid) {
		// 因为要更新商品库存，需要考虑使用乐观锁
		Commodity commodityForTransaction = commodityService.selectByPrimaryKey(commodityid);
		Long currentVersion = commodityForTransaction.getVersion();
		// 暂定10次重试，重入机制
		for (int i = 0; i < Integer.parseInt(createTransactionRetryCount); i++) {
			if (commodityForTransaction.getCommodityquantity() <= 0) {
				// 库存小于等于0，失败退出,返回商品ID
				return Integer.parseInt(String.valueOf(commodityid));
			} else {
				// 1.商品减去库存,加上销量
				commodityForTransaction.setCommodityquantity(
						commodityForTransaction.getCommodityquantity() - record.getTransactionamount());
				commodityForTransaction
						.setCommoditysales(commodityForTransaction.getCommoditysales() + record.getTransactionamount());
				// 乐观锁，更新后版本号+1
				Long oldversion = commodityForTransaction.getVersion();
				// 此处是为了调用CachePut缓存
				commodityService.updateForVersionByPrimaryKeyAndVersion(commodityForTransaction, currentVersion
						);
				// 还可以用返回的影响行数判断，如果为0则没有数据修改，重试
				if (commodityService.selectByPrimaryKey(commodityForTransaction.getCommodityid())
						.getVersion() == oldversion) {
					// 没有数据更新，重试
					continue;
				}
				// 2.买家账户扣钱
				Account transactionAccount = accountService.selectByUserId(buyerid);
				transactionAccount.setPeanut(transactionAccount.getPeanut() - record.getTransactionsum());
				int buyerUpdateStatus = accountService.updateByPrimaryKey(transactionAccount);
				// 3.卖家账户收钱
				Account sellerAccount = accountService.selectByUserId(commodityForTransaction.getCommodityownerid());
				sellerAccount.setPeanut(sellerAccount.getPeanut() + record.getTransactionsum());
				int sellerUpdateStatus = accountService.updateByPrimaryKey(sellerAccount);
				// 插入交易记录
				// 从容器中获取对象，进而自调用，因为事务是基于AOP的，不得到代理对象，事务会失效
				TransactionService myTransactionService = ctx.getBean(TransactionService.class);
				int transactionInsertStatus = myTransactionService.insert(record);
				// return createTransactionSuccessStatus;
				if (((buyerUpdateStatus != 0) && (sellerUpdateStatus != 0)) && (transactionInsertStatus != 0)) {
					break;
				}
			}

		}
		return createTransactionSuccessStatus;

	}

	// 使用生命周期的接口方法，获取IoC容器
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ctx = applicationContext;
	}

	@Override
	public List<Transaction> selectByUserIdOrderByTransactionDate(long userid) {
		// TODO Auto-generated method stub
		return transactionMapper.selectByUserIdOrderByTransactionDate(userid);
	}

	@Override
	public List<Transaction> selectByCommodityIdOrderByTransactionDate(long commodityid) {
		// TODO Auto-generated method stub
		return transactionMapper.selectByCommodityIdOrderByTransactionDate(commodityid);
	}

	@Override
	public List<Transaction> selectByCommodityOwnerIdOrderByTransactionDate(long commodityownerid) {
		// TODO Auto-generated method stub
		return transactionMapper.selectByCommodityOwnerIdOrderByTransactionDate(commodityownerid);
	}

}
