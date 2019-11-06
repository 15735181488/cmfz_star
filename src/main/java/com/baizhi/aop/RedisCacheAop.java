package com.baizhi.aop;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.annotation.ClearRedisCache;
import com.baizhi.annotation.RedisCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;
import java.util.Set;

/*@Configuration
@Aspect*/
public class RedisCacheAop {

    @Autowired
    private Jedis jedis;

    @Around("execution(* com.baizhi.service.impl.*.selectAll(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object target = proceedingJoinPoint.getTarget();
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Object[] args = proceedingJoinPoint.getArgs();
        Method method = methodSignature.getMethod();
        boolean b = method.isAnnotationPresent(RedisCache.class);
        if (b) {
            String className = target.getClass().getName();
            String methodName = method.getName();
            StringBuilder sb = new StringBuilder();
            sb.append(className).append(".").append(methodName).append("(");
            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]);
                if (i == args.length - 1) {
                    break;
                }
                sb.append(",");
            }
            sb.append(")");
            String key = sb.toString();
            if (jedis.exists(key)) {
                String result = jedis.get(key);
                return JSONObject.parse(result);
            } else {
                Object result = proceedingJoinPoint.proceed();
                jedis.set(key, JSONObject.toJSONString(result));
                return result;
            }

        } else {
            Object result = proceedingJoinPoint.proceed();
            return result;
        }
    }

    @After("execution(* com.baizhi.service.impl.*.*(..))&& !execution(* com.baizhi.service.impl.*.selectAll(..))")
    public void after(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        String className = target.getClass().getName();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        Method method = methodSignature.getMethod();
        boolean b = method.isAnnotationPresent(ClearRedisCache.class);
        if (b) {
            Set<String> keys = jedis.keys("*");
            for (String key : keys) {
                if (key.startsWith(className)) {
                    jedis.del(key);
                }
            }
        }
    }
}
