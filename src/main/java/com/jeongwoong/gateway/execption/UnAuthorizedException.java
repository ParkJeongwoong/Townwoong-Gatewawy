package com.jeongwoong.gateway.execption;

public class UnAuthorizedException extends RuntimeException{

	public UnAuthorizedException() {
		super("UnAuthorizedException");
	}

	public UnAuthorizedException(String message) {
		super(message);
	}

	public UnAuthorizedException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnAuthorizedException(Throwable cause) {
		super(cause);
	}

	protected UnAuthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
