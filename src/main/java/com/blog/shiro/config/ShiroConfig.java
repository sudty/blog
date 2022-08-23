package com.blog.shiro.config;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author 31676
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
//        设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        /*
         * anon: 无需认证即可访问
         * authc：必须认证才能访问
         * user：必须拥有 记住我 功能才能使用
         * perms: 拥有对某个资源的权限才能访问("perms[权限名]")
         * roles：拥有某个角色权限才能使用（“roles[角色名]）
         */
        HashMap<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/admin/toLogin","anon");
        filterMap.put("/admin/login","anon");
        filterMap.put("/admin/**","authc");

        bean.setSuccessUrl("/admin/home");
        bean.setFilterChainDefinitionMap(filterMap);
        bean.setLoginUrl("/admin/toLogin");
        bean.setUnauthorizedUrl("/error/to404");
        return bean;
    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

//        // 关闭shiro自带的session
//        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
//        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
//        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
//        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
//        securityManager.setSubjectDAO(subjectDAO);

       // 关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     *     创建 realm 对象
      */
    @Bean
    public UserRealm userRealm(CacheManager cacheManager){
        UserRealm userRealm = new UserRealm();
        userRealm.setCacheManager(cacheManager);
        //开启全局缓存
        userRealm.setCachingEnabled(true);
        //开启授权缓存
        userRealm.setAuthorizationCachingEnabled(true);
        userRealm.setAuthorizationCacheName("authorizationCache");
        //开启认证缓存
        userRealm.setAuthenticationCachingEnabled(true);
        userRealm.setAuthenticationCacheName("authenticationCache");
        return userRealm;
    }

}
