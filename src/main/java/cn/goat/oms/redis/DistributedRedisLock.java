package cn.goat.oms.redis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangyan
 * @version $ Id: DistributedRedisLock.java, v 0.1 2021/3/29 10:09 zhangyan Exp $
 */
@Component
@Slf4j
public class DistributedRedisLock {

    @Resource
    private RedissonClient redissonClient;

    public static Long LEASE_TIME = 3000L;

    /**
     * 公平锁
     *
     * @param lockKey
     * @return
     */
    public RLock fairLock(String lockKey) {
        RLock lock = redissonClient.getFairLock(lockKey);
        lock.lock();
        return lock;
    }

    /**
     * 公平锁
     *
     * @param lockKey
     * @return
     */
    public RLock fairLock(String lockKey, Long leaseTime) {
        RLock lock = redissonClient.getFairLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }

    /**
     * 可重入锁-需要手动解锁
     *
     * @param lockKey
     * @return
     */
    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    /**
     * 加锁以后leaseTime秒钟自动解锁
     *
     * @param lockKey   锁
     * @param leaseTime 过期时间
     * @return
     */
    public RLock lock(String lockKey, Long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, TimeUnit.MILLISECONDS);
        return lock;
    }

    /**
     * @param lockKey 锁
     * @param unit    时间单位
     * @param timeout 超时时间
     * @return
     */
    public RLock lock(String lockKey, TimeUnit unit, Long timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    /**
     * 尝试加锁-非阻塞
     * 尝试加锁，最多等待waitTime秒，上锁以后leaseTime秒自动解锁
     *
     * @param lockKey
     * @param unit
     * @param waitTime
     * @param leaseTime
     * @return
     */
    public Boolean tryLock(String lockKey, TimeUnit unit, Long waitTime, Long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            log.info("[DistributedRedisLock],tryLock InterruptedException:{}", e);
            return false;
        }
    }

    /**
     * 尝试加锁
     *
     * @param lockKey
     * @param waitTime
     * @param leaseTime
     * @return
     */
    public Boolean tryLock(String lockKey, Long waitTime, Long leaseTime) {
        RLock lock = redissonClient.getFairLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 解锁
     *
     * @param lockKey
     */
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        if(lock.isLocked()){
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

    /**
     * 解锁
     *
     * @param lock
     */
    public void unlock(RLock lock) {
        lock.unlock();
    }
}
