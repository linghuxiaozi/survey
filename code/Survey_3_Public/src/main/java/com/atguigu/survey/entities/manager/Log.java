package com.atguigu.survey.entities.manager;

public class Log {
	
	private Integer logId;
	private String operator;
	private String operateTime;
	private String methodName;
	private String typeName;
	private String parameters;
	private String returnValue;
	private String exceptionType;
	private String exceptionMessage;
	
	public Log() {
		
	}

	public Log(Integer logId, String operator, String operateTime,
			String methodName, String typeName, String parameters,
			String returnValue, String exceptionType, String exceptionMessage) {
		super();
		this.logId = logId;
		this.operator = operator;
		this.operateTime = operateTime;
		this.methodName = methodName;
		this.typeName = typeName;
		this.parameters = parameters;
		this.returnValue = returnValue;
		this.exceptionType = exceptionType;
		this.exceptionMessage = exceptionMessage;
	}

	@Override
	public String toString() {
		return "Log [logId=" + logId + ", operator=" + operator
				+ ", operateTime=" + operateTime + ", methodName=" + methodName
				+ ", typeName=" + typeName + ", parameters=" + parameters
				+ ", returnValue=" + returnValue + ", exceptionType="
				+ exceptionType + ", exceptionMessage=" + exceptionMessage
				+ "]";
	}

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

}
