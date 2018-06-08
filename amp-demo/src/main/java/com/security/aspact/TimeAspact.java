package com.security.aspact;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @category Class description
 * @author heyingcheng
 * @email horay_hyc@qq.com
 * @date 2017/12/18 22:13
 */
@Aspect
@Component
public class TimeAspact {
	@Around("execution(* com.security.rest.HelloApi.*(..))")
	public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("time aspact start");

		Object[] args = pjp.getArgs();
		for (Object arg : args) {
			System.out.println("arg is :" + arg);
		}

		Long startTime = System.currentTimeMillis();
		Object object = pjp.proceed();
		Long endTime = System.currentTimeMillis();
		System.out.println("time aspact 耗时：" + (endTime - startTime));
		System.out.println("time aspact end");
		return object;
	}

}
