package com.dydong.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(securityManager);
        //添加过滤器
        Map<String,String> filterMap = new LinkedHashMap<String,String>();
        //授权
        filterMap.put("/chatroom","authc");
        filterMap.put("/user/welcome","authc");
        filterMap.put("/user/editor","authc");
        filterMap.put("/user/selfarticle","authc");
        filterMap.put("/user/selfinformation","authc");
        filterMap.put("/user/selfupdate","authc");
        filterMap.put("/user/superUsers","authc");
        filterMap.put("/user/superArticle","authc");
        filterMap.put("/user/superUpdateArticle","authc");
        bean.setFilterChainDefinitionMap(filterMap);
        //设置登录页
        bean.setLoginUrl("/user/login");
        //未授权页面
        bean.setUnauthorizedUrl("/user/noauth");
        return bean;
    }

    //2：创建管理用户需要用到
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //创建realm对象
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    //整合shiroDialect:用来整合shiro thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
