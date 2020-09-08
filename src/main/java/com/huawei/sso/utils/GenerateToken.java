package com.huawei.sso.utils;

import java.util.UUID;

import com.huawei.sso.entity.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class GenerateToken {
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 生成令牌
     *
     * @param prefix
     *            令牌key前缀
     * @param redisValue
     *            redis存放的值
     * @return 返回token
     */
    public String createToken(String keyPrefix, String redisValue) {
        return createToken(keyPrefix, redisValue, null);
    }

    /**
     * 生成令牌
     *
     * @param prefix
     *            令牌key前缀
     * @param redisValue
     *            redis存放的值
     * @param time
     *            有效期
     * @return 返回token
     */
    public String createToken(String keyPrefix, String redisValue, Long time) {
        if (StringUtils.isEmpty(redisValue)) {
            new Exception("redisValue Not nul");
        }
        String token = keyPrefix + UUID.randomUUID().toString().replace("-", "");
        redisUtils.setString(token, redisValue, time);
        return token;
    }

    /**
     * 根据token获取redis中的value值
     *
     * @param token
     * @return
     */
    public String getToken(User token) {
        if (StringUtils.isEmpty(String.valueOf(token))) {
            return null;
        }
        String value = redisUtils.getString(token);
        return value;
    }

    /**
     * 移除token
     *
     * @param token
     * @return
     */
    public Boolean removeToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return redisUtils.delKey(token);

    }

}