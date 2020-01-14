package com.scribblepad.aspectjlogger;

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
		Object result = null;
		if (!flag.get().equalsIgnoreCase(point.toLongString())) {

			logger.debug("Signature: " + point.toLongString());
			if (point.getArgs() != null) {

				for (Object obj : point.getArgs()) {
					logger.debug("Arg Value: " + obj);
				}
			}
			try {
				flag.set(point.toLongString());
				result = point.proceed();
			} catch (Throwable t) {
				logger.error(t);
				// Write what you want to do if exception is thrown.
				throw t; // maintain passivity with application.
			}
			logger.debug("Return Value: " + result);
			return result;
		} else {
			return point.proceed();
		}
	}
}
