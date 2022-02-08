package cn.goat.oms.aop;

import cn.goat.oms.annotation.ResubmitAnnotation;
import cn.goat.oms.constant.ResubmitConstants;
import cn.goat.oms.entity.response.ResponseResult;
import cn.goat.oms.redis.DistributedRedisLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author chengtianping
 * @date 2021/4/12 17:06
 * 防重复提交
 **/
@Component
@Aspect
@Slf4j
public class OpsWebResubmitAspect {

    @Resource
    private DistributedRedisLock distributedRedisLock;

   /* @Resource
    private AdminWebContextHolder adminWebContextHolder;*/

    @Around("@annotation(re)")
    public Object around(ProceedingJoinPoint joinPoint, ResubmitAnnotation re) throws Throwable {
        String key = null;
        try {
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            ResubmitAnnotation resubmitAnnotation = method.getAnnotation(ResubmitAnnotation.class);
            //如果注解为空，则不需要校验重复提交
            if (resubmitAnnotation == null) {
                return joinPoint.proceed();
            }

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String requestUri = request.getRequestURI();
            //用户信息
           /* AdminWebOperator adminWebLoginUser = adminWebContextHolder.getAdminWebLoginUser();
            Long staffId = adminWebLoginUser.getUserId();*/
           Long staffId = 0L;
            key = ResubmitConstants.ADMIN_RESUBMIT_TOKEN_KEY + staffId + "_" + requestUri;
            log.info("[op:PortalResubmitAspect],key:{},time:{}", key, System.currentTimeMillis());
            boolean acquireLock = distributedRedisLock.tryLock(key, TimeUnit.SECONDS, 1L, 3L);
            //锁标识为空，则表示锁已存在，当前请求是重复提交
            if (!acquireLock) {
                log.error("[op:PortalResubmitAspect],tryLock result:{}", "重复提交");
                return ResponseResult.FAIL();
            }

        } catch (Exception e) {
            log.error("[op:PortalResubmitAspect],Exception tryLock:{}", e);
        } finally {
            //释放锁
            log.info("[op:PortalResubmitAspect],unlock:{}", key);
            distributedRedisLock.unlock(key);
        }
        return joinPoint.proceed();
    }

}
