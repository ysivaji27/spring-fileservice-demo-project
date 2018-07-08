package com.assignment.fileapi.exception;

public class FilePathException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public FilePathException(String errorMessage){
		super(errorMessage);
	}

}
