package com.sohu.tv;

public class SoException extends Exception {
	private int statusCode = -1;

	public SoException(String msg) {
		super(msg);
	}

	public SoException(Exception cause) {
		super(cause);
	}

	public SoException(String msg, int statusCode) {
		super(msg);
		this.statusCode = statusCode;

	}

	public SoException(String msg, Exception cause) {
		super(msg, cause);
	}

	public SoException(String msg, Exception cause, int statusCode) {
		super(msg, cause);
		this.statusCode = statusCode;

	}

	public int getStatusCode() {
		return this.statusCode;
	}
}
