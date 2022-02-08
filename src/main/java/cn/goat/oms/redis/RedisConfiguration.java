package cn.goat.oms.redis;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * @author zhangyan
 * @version $ Id: RedisConfiguraton.java, v 0.1 2021/3/29 10:10 zhangyan Exp $
 */
@Configuration
public class RedisConfiguration {

    @Resource
    private Environment environment;

    private Config config = new Config();

    /**
     * 单节点
     *
     * @return
     */
    @Bean
    public RedissonClient getSingleRedissonClient() {
        String host = environment.getProperty("spring.redis.host");
        String port = environment.getProperty("spring.redis.port");
        String password = environment.getProperty("spring.redis.password");
        if (StringUtils.isNotBlank(password)) {
            config.useSingleServer().setAddress("redis://" + host + ":" + port)
                    .setTimeout(1000)
                    .setRetryAttempts(3)
                    .setRetryInterval(1000)
                    .setPingConnectionInterval(1000)
                    .setPassword(password);
        } else {
            config.useSingleServer().setAddress("redis://" + host + ":" + port);
        }
        return Redisson.create(config);
    }
}
