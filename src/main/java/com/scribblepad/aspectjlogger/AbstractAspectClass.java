package com.scribblepad.aspectjlogger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	private static List<String> argumentsList = new ArrayList<>();
	private static LoggerInfo logger = new LoggerInfo();
	static {
		LoggerUtil.logHeader();
	}

	@Pointcut
	public abstract void scope();

	@Around("scope()")
	public Object AroundAdvice(ProceedingJoinPoint point) throws Throwable {

		long startTime = 0;
		long endTime = 0;
		Object result = null;
		String methodSignature = point.getSignature().toLongString().replace(",", "|");
		logger.setMethodSignature(methodSignature);
		logger.setParameterValues("");
		if (!flag.get().equalsIgnoreCase(methodSignature)) {
			argumentsList.clear();
			if (point.getArgs() != null) {

				for (Object arg : point.getArgs()) {

					argumentsList.add(objToString(arg));
				}
			}
			logger.setParameterValues(String.join("|", argumentsList));

			try {
				flag.set(methodSignature);
				startTime = new Date().getTime();
				result = point.proceed();
				endTime = new Date().getTime() - startTime;
				logger.setReturnValue(objToString(result));
				logger.setExecutionTime((int) endTime);

			} catch (Throwable t) {
				logger.setReturnValue("ERROR: " + t.getMessage() + ", " + t.getStackTrace()[0]);
				endTime = new Date().getTime() - startTime;
				logger.setExecutionTime((int) endTime);
				throw t;
			}
			LoggerUtil.logMessage(logger);
			return result;
		} else {
			return point.proceed();
		}
	}

	String objToString(Object o) {
		if (o == null) {
			return "null";
		}
		return (o.toString().contains(o.getClass().getName() + "@" + Integer.toHexString(o.hashCode()))
				? o.getClass().getSimpleName()
				: o.toString());
	}
}
