package sheffield.shiro;

import java.util.LinkedHashMap;

public class MyFilterChainDefinitionMapBuilder {

	public MyFilterChainDefinitionMapBuilder() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 自定义 FilterChainDefinition
	 * 
	 * @return
	 */
	public LinkedHashMap<String, String> buildFilterChainDefinitionMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		// 此处声明关系也可是已配置在数据库中的
		// map.put("/login/toLogin", "anon");
		// map.put("/login/loginVal", "anon");
		// map.put("/login/logout ", "logout");
		// map.put("/example/admin", "authc,roles[admin]");
		// map.put("/example/admin", "authc,perms[admin:view:*]");
		// map.put("/example/user", "authc,roles[user]");
		// map.put("/example/user", "authc,perms[user:view:*]");
		// map.put("/**", "authc");
		map.put("/transaction/**", "user");
		map.put("/user/**", "user");
		map.put("/search/**", "anon");
		map.put("/buyer/**", "anon");
		map.put("/home/**", "anon");
		map.put("/**", "anon");
		return map;
	}
}
