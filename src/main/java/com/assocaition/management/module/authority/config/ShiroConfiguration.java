package com.assocaition.management.module.authority.config;

import com.assocaition.management.module.authority.entity.AuthSysPermission;
import com.assocaition.management.module.authority.service.IPermissionService;
import com.assocaition.management.module.authority.service.shiro.filter.PermissionAuthFilter;
import com.assocaition.management.module.authority.service.shiro.filter.TokenAuthFilter;
import com.assocaition.management.module.authority.service.shiro.realm.ShiroAuthorizingRealm;
import com.assocaition.management.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
public class ShiroConfiguration {
    @Autowired
    private RedisUtil redisUtil;

    private TokenAuthFilter tokenAuthFilter;

    private PermissionAuthFilter permissionAuthFilter;
    @Autowired
    private IPermissionService permissionService;

    private static final String CACHE_KEY = "shiro:cache:";
    private static final String SESSION_KEY = "shiro:session:";
    private static final String NAME = "custom.name";
    private static final String VALUE = "/";

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // ????????????Map??????????????????????????????Shiro???????????????filter??????
        // ?????????????????????
        Map<String, Filter> filters = new HashMap<String, Filter>();
        // <filter>
        // <filter-mapping
        this.tokenAuthFilter = new TokenAuthFilter();
        this.tokenAuthFilter.setRedisUtil(this.redisUtil);
        this.permissionAuthFilter = new PermissionAuthFilter();

        filters.put("authcToken", this.tokenAuthFilter);
        filters.put("permAuthc", this.permissionAuthFilter);

        // ???shiro??????????????????
        shiroFilterFactoryBean.setFilters(filters);

        // ???????????????????????????????????????
        Map<String,String> permChainMap = new HashMap<>();
        //permChainMap.put("/user/**", "tokenAuthc");
        List<AuthSysPermission> permissionList = this.permissionService.searchPermissions();
        if(permissionList != null){
            for(AuthSysPermission permission:permissionList){// /user/**
                permChainMap.put("/"+permission.getResources().getRscUrl()+"/**", "authcToken, permAuthc");
            }
        }
        //permChainMap.put("/user/**","anon");
//        permChainMap.put("/auth/login","anon");
        //permChainMap.put("/**/**","authc");
        permChainMap.put("/foreground/**","anon");
        permChainMap.put("/layer/**", "anon");
        permChainMap.put("/announcement/create/**", "anon");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(permChainMap);

        // ???????????????
        shiroFilterFactoryBean.setLoginUrl("/auth/login");
        //shiroFilterFactoryBean.setSuccessUrl();
        shiroFilterFactoryBean.setUnauthorizedUrl("/auth/noAuth");

        shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }

    @Bean("securityManager") // ?????????
    public DefaultWebSecurityManager createSecurityManager(@Qualifier("shiroRealm") ShiroAuthorizingRealm shiroRealm){
        // ??????SecurityMananger?????????????????????Realm??????
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager(shiroRealm);
        // ????????????????????? ??????redis
        manager.setCacheManager(cacheManager());
        // ?????????session?????? ??????redis
        manager.setSessionManager(sessionManager());

        /*
         * ??????shiro?????????session??????????????????
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);

        return manager;
    }

    /**
     * Session Manager
     * ????????????shiro-redis????????????
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        // ??????????????????Cookie???????????????id
        sessionManager.setSessionIdCookieEnabled(true);
        // ???????????????url???????????????url????????????id???
        sessionManager.setSessionIdUrlRewritingEnabled(true);

        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName(NAME);
        simpleCookie.setValue(VALUE);
        // ??????????????????id???cookie?????????
        sessionManager.setSessionIdCookie(simpleCookie);

        return sessionManager;
    }

    // ??????RedisSessionDAO
    /**
     * RedisSessionDAO shiro sessionDao???????????? ??????redis
     * ????????????shiro-redis????????????
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setExpire(86400);
        redisSessionDAO.setKeyPrefix(SESSION_KEY);
        return redisSessionDAO;
    }
    /**
     * cacheManager ?????? redis??????
     * ????????????shiro-redis????????????
     *
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        // ??????redisManager
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setExpire(86400);
        redisCacheManager.setKeyPrefix(CACHE_KEY);
        return redisCacheManager;
    }
    /**
     * ??????shiro redisManager
     * ????????????shiro-redis????????????
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("127.0.0.1");
        redisManager.setPort(6379);
        redisManager.setTimeout(0);
        // redisManager.setPassword(password);
        return redisManager;
    }
}
