package com.scribblepad.aspectjlogger;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public abstract class AbstractAspectClass {
	private static ThreadLocal<String> flag = new ThreadLocal<String>() {

		@Override
		protected String initialValue() {
			return "";
		}

	};

	@Pointcut
	public abstract void scope();

	@Around("scope()")
	public Object AroundAdvice(ProceedingJoinPoint point) throws Throwable {
		Logger logger = Logger.getRootLogger();
		long startTime = 0;
		long  endTime = 0;
		Object result = null;
		String methodSignature = point.toLongString();
		String arguments = "";
		if (!flag.get().equalsIgnoreCase(point.toLongString())) {
			arguments = point.getArgs() != null ? Arrays.deepToString(point.getArgs()) : "None";
			try {
				flag.set(point.toLongString());
				startTime = System.currentTimeMillis();
				result = point.proceed();
				endTime = System.currentTimeMillis();
			} catch (Throwable t) {

				logger.error(String.format("METHOD: %s. PARAMETERS:  %s. ERROR: %s. Execution Time(ms): %d", methodSignature, arguments,
						t.getMessage(),endTime-startTime));
				logger.error(t);
				throw t; 
			}
			logger.debug(String.format("METHOD: %s. PARAMETERS: %s. RETURN VALUE: %s.  Execution Time(ms): %d", methodSignature, arguments,
					result == null ? "None" : result,endTime-startTime));
			return result;
		} else {
			return point.proceed();
		}
	}
}
