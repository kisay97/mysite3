package com.estsoft.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class MeasureDaoExecutionTimeAspect {
	@Around("execution( * *..dao.*.*(..) ) || execution( * *..service.*.*(..) ) || execution( * *..controller.*.*(..) )")
	public Object around(ProceedingJoinPoint pjp) throws Throwable{
		//Before
		String taskName = pjp.getTarget().getClass() + "." + pjp.getSignature().getName();
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Object result = pjp.proceed();
		
		//After
		stopWatch.stop();
		
		System.out.println( taskName + " : "
							+ stopWatch.getTotalTimeMillis() + "millis");
		
		return result;
	}
}