package com.scribblepad.aspectjlogger;

public class LoggerInfo {
	public String timestamp;
	public String parameterValues;
	public String returnValue;
	public int executionTime;
	public String methodSignature;

	public LoggerInfo() {
		super();
	}

	public void setParameterValues(String parameterValues) {
		this.parameterValues = parameterValues;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public void setExecutionTime(int executionTime) {
		this.executionTime = executionTime;
	}

	public void setMethodSignature(String methodSignature) {
		this.methodSignature = methodSignature;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return String.format("%s, %s,%s,%s,%s %n", this.timestamp, this.methodSignature, this.parameterValues,
				this.returnValue, this.executionTime);
	}

}
