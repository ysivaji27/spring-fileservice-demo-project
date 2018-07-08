package com.assignment.fileapi.exception;

public class FileOperationException extends RuntimeException {

	private static final long serialVersionUID = 3689197784645132447L;

	public FileOperationException(final String aMessage) {
		super(aMessage);
	}

	public FileOperationException(final String aMessage, final Throwable aThrowable) {
		super(aMessage, aThrowable);
	}
}
