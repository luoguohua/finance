package com.luoguohua.finance.boot.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.luoguohua.finance.boot.properties.AuthProperties;
import com.luoguohua.finance.boot.system.service.RedisClientDetailsService;
import com.luoguohua.finance.boot.system.service.SocialLoginService;
import com.luoguohua.finance.boot.system.service.UserManager;
import com.luoguohua.finance.common.constant.GrantTypeConstant;
import com.luoguohua.finance.common.constant.ParamsConstant;
import com.luoguohua.finance.common.constant.SocialConstant;
import com.luoguohua.finance.common.exception.FinanceException;
import com.luoguohua.finance.common.pojo.dto.BindUser;
import com.luoguohua.finance.common.pojo.vo.FinanceResponse;
import com.luoguohua.finance.common.utils.FinanceUtils;
import com.luoguohua.finance.system.po.SysUser;
import com.luoguohua.finance.system.po.UserConnection;
import com.luoguohua.finance.system.service.UserConnectionService;
import com.xkcoding.justauth.AuthRequestFactory;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MrBird
 */
@Service
@RequiredArgsConstructor
public class SocialLoginServiceImpl implements SocialLoginService {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String NOT_BIND = "not_bind";
    private static final String SOCIAL_LOGIN_SUCCESS = "social_login_success";

    @Autowired
    private UserManager userManager;
    @Autowired
    private AuthRequestFactory factory;
    @Autowired
    private AuthProperties properties;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserConnectionService userConnectionService;
    @Autowired
    private ResourceOwnerPasswordTokenGranter granter;
    @Autowired
    private RedisClientDetailsService redisClientDetailsService;

    @Override
    public AuthRequest renderAuth(String oauthType) throws FinanceException {
        return factory.get(getAuthSource(oauthType));
    }

    @Override
    public FinanceResponse resolveBind(String oauthType, AuthCallback callback) throws FinanceException {
        FinanceResponse financeResponse = new FinanceResponse();
        AuthRequest authRequest = factory.get(getAuthSource(oauthType));
        AuthResponse<?> response = authRequest.login(resolveAuthCallback(callback));
        if (response.ok()) {
            financeResponse.data(response.getData());
        } else {
            throw new FinanceException(String.format("第三方登录失败，%s", response.getMsg()));
        }
        return financeResponse;
    }

    @Override
    public FinanceResponse resolveLogin(String oauthType, AuthCallback callback) throws FinanceException {
        FinanceResponse febsResponse = new FinanceResponse();
        AuthRequest authRequest = factory.get(getAuthSource(oauthType));
        AuthResponse<?> response = authRequest.login(resolveAuthCallback(callback));
        if (response.ok()) {
            AuthUser authUser = (AuthUser) response.getData();
            UserConnection userConnection = userConnectionService.selectByCondition(authUser.getSource().toString(), authUser.getUuid());
            if (userConnection == null) {
                febsResponse.message(NOT_BIND).data(authUser);
            } else {
                SysUser user = userManager.findByName(userConnection.getUserName());
                if (user == null) {
                    throw new FinanceException("系统中未找到与第三方账号对应的账户");
                }
                OAuth2AccessToken oAuth2AccessToken = getOauth2AccessToken(user);
                febsResponse.message(SOCIAL_LOGIN_SUCCESS).data(oAuth2AccessToken);
                febsResponse.put(USERNAME, user.getUsername());
            }
        } else {
            throw new FinanceException(String.format("第三方登录失败，%s", response.getMsg()));
        }
        return febsResponse;
    }

    @Override
    public OAuth2AccessToken bindLogin(BindUser bindUser, AuthUser authUser) throws FinanceException {
        SysUser systemUser = userManager.findByName(bindUser.getBindUsername());
        if (systemUser == null || !passwordEncoder.matches(bindUser.getBindPassword(), systemUser.getPassword())) {
            throw new FinanceException("绑定系统账号失败，用户名或密码错误！");
        }
        this.createConnection(systemUser, authUser);
        return this.getOauth2AccessToken(systemUser);
    }

    @Override
    public OAuth2AccessToken signLogin(BindUser registUser, AuthUser authUser) throws FinanceException {
        SysUser user = this.userManager.findByName(registUser.getBindUsername());
        if (user != null) {
            throw new FinanceException("该用户名已存在！");
        }
        String encryptPassword = passwordEncoder.encode(registUser.getBindPassword());
        SysUser systemUser = this.userManager.registerUser(registUser.getBindUsername(), encryptPassword);
        this.createConnection(systemUser, authUser);
        return this.getOauth2AccessToken(systemUser);
    }

    @Override
    public void bind(BindUser bindUser, AuthUser authUser) throws FinanceException {
        String username = bindUser.getBindUsername();
        if (isCurrentUser(username)) {
            UserConnection userConnection = userConnectionService.selectByCondition(authUser.getSource().toString(), authUser.getUuid());
            if (userConnection != null) {
                throw new FinanceException("绑定失败，该第三方账号已绑定" + userConnection.getUserName() + "系统账户");
            }
            SysUser systemUser = new SysUser();
            systemUser.setUsername(username);
            this.createConnection(systemUser, authUser);
        } else {
            throw new FinanceException("绑定失败，您无权绑定别人的账号");
        }
    }

    @Override
    public void unbind(BindUser bindUser, String oauthType) throws FinanceException {
        String username = bindUser.getBindUsername();
        if (isCurrentUser(username)) {
            this.userConnectionService.deleteByCondition(username, oauthType);
        } else {
            throw new FinanceException("解绑失败，您无权解绑别人的账号");
        }
    }

    @Override
    public List<UserConnection> findUserConnections(String username) {
        return this.userConnectionService.selectByCondition(username);
    }

    private void createConnection(SysUser systemUser, AuthUser authUser) {
        UserConnection userConnection = new UserConnection();
        userConnection.setUserName(systemUser.getUsername());
        userConnection.setProviderName(authUser.getSource().toString());
        userConnection.setProviderUserId(authUser.getUuid());
        userConnection.setProviderUserName(authUser.getUsername());
        userConnection.setImageUrl(authUser.getAvatar());
        userConnection.setNickName(authUser.getNickname());
        userConnection.setLocation(authUser.getLocation());
        this.userConnectionService.createUserConnection(userConnection);
    }

    private AuthCallback resolveAuthCallback(AuthCallback callback) {
        int stateLength = 3;
        String state = callback.getState();
        String[] strings = StringUtils.splitByWholeSeparatorPreserveAllTokens(state, FinanceUtils.getDoubleColon());
        if (strings.length == stateLength) {
            callback.setState(strings[0] + FinanceUtils.getDoubleColon() + strings[1]);
        }
        return callback;
    }

    private AuthSource getAuthSource(String type) throws FinanceException {
        if (StrUtil.isNotBlank(type)) {
            return AuthSource.valueOf(type.toUpperCase());
        } else {
            throw new FinanceException(String.format("暂不支持%s第三方登录", type));
        }
    }

    private boolean isCurrentUser(String username) {
        String currentUsername = FinanceUtils.getCurrentUsername();
        return StringUtils.equalsIgnoreCase(username, currentUsername);
    }

    private OAuth2AccessToken getOauth2AccessToken(SysUser user) throws FinanceException {
        final HttpServletRequest httpServletRequest = FinanceUtils.getHttpServletRequest();
        httpServletRequest.setAttribute(ParamsConstant.LOGIN_TYPE, SocialConstant.SOCIAL_LOGIN);
        String socialLoginClientId = properties.getSocialLoginClientId();
        ClientDetails clientDetails = null;
        try {
            clientDetails = redisClientDetailsService.loadClientByClientId(socialLoginClientId);
        } catch (Exception e) {
            throw new FinanceException("获取第三方登录可用的Client失败");
        }
        if (clientDetails == null) {
            throw new FinanceException("未找到第三方登录可用的Client");
        }
        Map<String, String> requestParameters = new HashMap<>(5);
        requestParameters.put(ParamsConstant.GRANT_TYPE, GrantTypeConstant.PASSWORD);
        requestParameters.put(USERNAME, user.getUsername());
        requestParameters.put(PASSWORD, SocialConstant.setSocialLoginPassword());

        String grantTypes = String.join(StringPool.COMMA, clientDetails.getAuthorizedGrantTypes());
        TokenRequest tokenRequest = new TokenRequest(requestParameters, clientDetails.getClientId(), clientDetails.getScope(), grantTypes);
        return granter.grant(GrantTypeConstant.PASSWORD, tokenRequest);
    }
}
