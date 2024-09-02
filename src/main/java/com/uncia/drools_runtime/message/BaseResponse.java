package com.uncia.drools_runtime.message;

public class BaseResponse {

	private String message;
	private Boolean status;
	private int statusCode;

	BaseResponse() {
	}

	public BaseResponse(String message, Boolean status, int statusCode) {
		this.message = message;
		this.statusCode = statusCode;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
