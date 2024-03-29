package com.luoguohua.finance.boot.configure;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.luoguohua.finance.boot.properties.AuthProperties;
import com.luoguohua.finance.boot.properties.ClientsProperties;
import com.luoguohua.finance.boot.system.service.FinanceUserDetailService;
import com.luoguohua.finance.boot.system.service.RedisAuthenticationCodeService;
import com.luoguohua.finance.boot.system.service.RedisClientDetailsService;
import com.luoguohua.finance.boot.translator.FinanceWebResponseExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.UUID;

/**
 * @Version 1.0
 * @Author: luoguohua
 * @Date: 2022/3/5 14:26
 * Content:
 * 安全认证配置类
 */
@Configuration
@EnableAuthorizationServer
public class FinanceAuthorizationServerConfigure extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private FinanceUserDetailService financeUserDetailService;

    @Autowired
    private RedisAuthenticationCodeService authenticationCodeService;

    @Autowired
    private FinanceWebResponseExceptionTranslator financeWebResponseExceptionTranslator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthProperties authProperties;

    @Autowired
    private RedisClientDetailsService redisClientDetailsService;

//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        /**
//         * 1.客户端从认证服务器获取令牌的时候，必须使用client_id为febs，client_secret为123456的标识来获取；
//         * 2.该client_id支持password模式获取令牌，并且可以通过refresh_token来获取新的令牌；
//         * 3.在获取client_id为febs的令牌的时候，scope只能指定为all，否则将获取失败；
//         */
//        ClientsProperties[] clientsProperties = authProperties.getClients();
//        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
//        if(ArrayUtil.isNotEmpty(clients)){
//            for (ClientsProperties properties:clientsProperties){
//                if(StrUtil.isBlank(properties.getClient())){
//                    throw new Exception("client不能为空");
//                }
//                if(StrUtil.isBlank(properties.getSecret())){
//                    throw new Exception("secret不能为空");
//                }
//                String[] grantTypes = StrUtil.split(properties.getGrantType(), StringPool.COMMA);
//                builder.withClient(properties.getClient())
//                        .secret(passwordEncoder.encode(properties.getSecret()))
//                        .authorizedGrantTypes(grantTypes)
//                        .scopes(properties.getScope());
//            }
//        }
//    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(redisClientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore())
                .userDetailsService(financeUserDetailService)
                .authorizationCodeServices(authenticationCodeService)
                .authenticationManager(authenticationManager)
                .tokenServices(defaultTokenServices())
                .exceptionTranslator(financeWebResponseExceptionTranslator);
    }

    /**
     * 令牌存储策略
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        // 解决每次生成的 token都一样的问题
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setAuthenticationKeyGenerator(oAuth2Authentication -> UUID.randomUUID().toString());
        return redisTokenStore;
    }

    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        // 开启令牌刷新
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(redisClientDetailsService);
        return tokenServices;
    }

    @Bean
    public ResourceOwnerPasswordTokenGranter resourceOwnerPasswordTokenGranter(AuthenticationManager authenticationManager, OAuth2RequestFactory oAuth2RequestFactory) {
        DefaultTokenServices defaultTokenServices = defaultTokenServices();
        return new ResourceOwnerPasswordTokenGranter(authenticationManager, defaultTokenServices, redisClientDetailsService, oAuth2RequestFactory);
    }

    @Bean
    public DefaultOAuth2RequestFactory oAuth2RequestFactory() {
        return new DefaultOAuth2RequestFactory(redisClientDetailsService);
    }

}
