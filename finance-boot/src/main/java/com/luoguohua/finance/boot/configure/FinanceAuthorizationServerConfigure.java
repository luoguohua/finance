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
 * ?????????????????????
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
//         * 1.???????????????????????????????????????????????????????????????client_id???febs???client_secret???123456?????????????????????
//         * 2.???client_id??????password???????????????????????????????????????refresh_token????????????????????????
//         * 3.?????????client_id???febs?????????????????????scope???????????????all???????????????????????????
//         */
//        ClientsProperties[] clientsProperties = authProperties.getClients();
//        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
//        if(ArrayUtil.isNotEmpty(clients)){
//            for (ClientsProperties properties:clientsProperties){
//                if(StrUtil.isBlank(properties.getClient())){
//                    throw new Exception("client????????????");
//                }
//                if(StrUtil.isBlank(properties.getSecret())){
//                    throw new Exception("secret????????????");
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
     * ??????????????????
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        // ????????????????????? token??????????????????
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setAuthenticationKeyGenerator(oAuth2Authentication -> UUID.randomUUID().toString());
        return redisTokenStore;
    }

    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        // ??????????????????
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
