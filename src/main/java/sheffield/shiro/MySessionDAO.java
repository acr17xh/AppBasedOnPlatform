package sheffield.shiro;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.MapCache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

public class MySessionDAO extends EnterpriseCacheSessionDAO {

	public MySessionDAO() {
		setCacheManager(new AbstractCacheManager() {
			@Override
			protected Cache<Serializable, Session> createCache(String name) throws CacheException {
				return new MapCache<Serializable, Session>(name, new ConcurrentHashMap<Serializable, Session>());
			}
		});
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		return null; // should never execute because this implementation relies on parent class to
						// access cache, which
		// is where all sessions reside - it is the cache implementation that determines
		// if the
		// cache is memory only or disk-persistent, etc.
	}

	@Override
	protected void doUpdate(Session session) {
		// does nothing - parent class persists to cache.
	}

	@Override
	protected void doDelete(Session session) {
		// does nothing - parent class removes from cache.
	}

}
