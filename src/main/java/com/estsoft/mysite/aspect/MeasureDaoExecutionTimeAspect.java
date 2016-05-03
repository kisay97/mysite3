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
		//Before(전처리)
		String taskName = pjp.getTarget().getClass() + "." + pjp.getSignature().getName(); //실행한 메서드의 이름을 받아온다
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Object result = pjp.proceed(); //pjp.proceed()를 해주면 실제 함수 실행됨
		
		//After(후처리)
		stopWatch.stop();
		
		System.out.println( taskName + " : "
							+ stopWatch.getTotalTimeMillis() + "millis");
		
		return result; //실행한 결과를 따로 돌려줘야함
	}
	
	
}