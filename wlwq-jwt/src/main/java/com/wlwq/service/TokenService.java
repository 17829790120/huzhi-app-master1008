package com.wlwq.service;

import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.vo.LoginAccount;
import com.wlwq.common.exception.ApiLoginException;
import com.wlwq.common.utils.AddressUtils;
import com.wlwq.common.utils.IpUtils;
import com.wlwq.common.utils.ServletUtils;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.utils.uuid.IdUtils;
import com.wlwq.framework.redis.RedisCache;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 *
 * @author Renbowen
 */
@Component
public class TokenService {

    /**
     * 令牌前缀
     */
    public static final String LOGIN_ACCOUNT_KEY = "login_account_key";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 令牌自定义标识
     */
    @Value("${token.header}")
    private String header;

    /**
     * 令牌秘钥
     */
    @Value("${token.secret}")
    private String secret;

    /**
     * 令牌有效期（默认30天）
     */
    @Value("${token.expireTime}")
    private int expireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IApiAccountService accountService;


    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginAccount getLoginUser(HttpServletRequest request){
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isBlank(token)) {
            throw new ApiLoginException();
        }
        Claims claims = parseToken(token);
        // 解析对应的权限以及用户信息
        String uuid = (String) claims.get(LOGIN_ACCOUNT_KEY);
        String accountKey = getTokenKey(uuid);
        LoginAccount account = redisCache.getCacheObject(accountKey);
        if (account == null){
            throw new ApiLoginException();
        }
        return account;
    }

    /**
     * 获取当前登录用户ID
     * @param request
     * @return
     */
    public String getAccountId(HttpServletRequest request){
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isBlank(token)) {
            throw new ApiLoginException();
        }
        Claims claims = parseToken(token);
        // 解析对应的权限以及用户信息
        String uuid = (String) claims.get(LOGIN_ACCOUNT_KEY);
        String accountKey = getTokenKey(uuid);
        LoginAccount account = redisCache.getCacheObject(accountKey);
        if (account == null){
            throw new ApiLoginException();
        }
        return account.getAccountId();
    }



    /**
     * 可传可不传token情况
     * @param request
     * @return
     */
    public String getNoAccountId(HttpServletRequest request){
        String accountId = "-1";
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            Claims claims = parseToken(token);
            // 解析对应的权限以及用户信息
            String uuid = (String) claims.get(LOGIN_ACCOUNT_KEY);
            String accountKey = getTokenKey(uuid);
            LoginAccount account = redisCache.getCacheObject(accountKey);
            if (account != null){
                accountId = account.getAccountId();
            }
        }
        return accountId;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginAccount loginAccount) {
        if (StringUtils.isNotNull(loginAccount) && StringUtils.isNotEmpty(loginAccount.getToken())) {
            refreshToken(loginAccount);
        }
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String accountKey = getTokenKey(token);
            redisCache.deleteObject(accountKey);
        }
    }

    /**
     * 创建令牌
     *
     * @param loginAccount 用户信息
     * @return 令牌
     */
    public String createToken(LoginAccount loginAccount) {
        String token = IdUtils.fastUUID();
        loginAccount.setToken(token);
        setUserAgent(loginAccount);
        refreshToken(loginAccount);

        Map<String, Object> claims = new HashMap<>(1);
        claims.put(LOGIN_ACCOUNT_KEY, token);
        return createToken(claims);
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginAccount 令牌
     * @return 令牌
     */
    public void verifyToken(LoginAccount loginAccount) {
        long expireTime = loginAccount.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(loginAccount);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginAccount 登录信息
     */
    public void refreshToken(LoginAccount loginAccount) {
        loginAccount.setLoginTime(System.currentTimeMillis());
        loginAccount.setExpireTime(loginAccount.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String accountKey = getTokenKey(loginAccount.getToken());
        redisCache.setCacheObject(accountKey, loginAccount, expireTime, TimeUnit.DAYS);
        // 将uuid存到数据库里
        ApiAccount account = accountService.selectApiAccountById(loginAccount.getAccountId());
        if(account != null){
            accountService.updateApiAccount(ApiAccount.builder()
                    .accountId(account.getAccountId())
                    .uuid(accountKey)
                    .build());
        }
    }


    /**
     * 删除用户token
     * @param uuid
     */
    public void delToken(String uuid){
        if(StringUtils.isNotBlank(uuid)){
            redisCache.deleteObject(uuid);
        }
    }

    /**
     * 设置用户代理信息
     *
     * @param loginAccount 登录信息
     */
    public void setUserAgent(LoginAccount loginAccount) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        loginAccount.setIpaddr(ip);
        loginAccount.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        loginAccount.setBrowser(userAgent.getBrowser().getName());
        loginAccount.setOs(userAgent.getOperatingSystem().getName());
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    public String getToken(HttpServletRequest request) {
        return request.getHeader(header);
    }

    private String getTokenKey(String uuid) {
        return LOGIN_TOKEN_KEY + uuid;
    }
}
